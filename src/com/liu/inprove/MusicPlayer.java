```
package com.liu;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;

// 第二题 // 曲库系统。
// 初始化时给定容量为 capacity
// 操作： // //添加。 //当歌曲已经在曲库中，返回-1；
// 当容量没满时，直接添加，返回0；
// 当容量满时，选取一曲删除，放入新曲，返回被删除曲号。
// 删除规则为：
// 1）. 选取播放次数最少的删除；
// 2）. 若播放次数一样少（非零），选取第一次播放早的曲删除
// 3）. 若播放次数一样少（零），选取添加进曲库早的删除
//
////播放 //指定的曲子在曲库，播放一次，返回true；否则返回false
//
////删除 //指定曲在系统，删除该曲及其播放记录，返回true；否则返回false。
public class MusicPlayer {

    class Music {
        int musicId; // music id
        int intime; // add time
        int count; // play count
        int firstPlay; // first play time

        public Music(int musicId, int intime, int count) {
            this.musicId = musicId;
            this.intime = intime;
            this.count = count;
        }
    }

    int capacity;
    int time;
    Map<Integer, Music> database;
    PriorityQueue<Music> heap;
    MusicPlayer(int capacity) {
        this.capacity = capacity;
        this.time = 0;
        this.database = new LinkedHashMap<>(capacity);
        this.heap = new PriorityQueue<>(new Comparator<Music>() {
            @Override
            public int compare(Music m1, Music m2) {
                if (m1.count != m2.count) {
                    return m1.count - m2.count;
                } else {
                    if (m1.count != 0) {
                        return m1.firstPlay - m2.firstPlay;
                    } else {
                        return m1.intime - m2.intime;
                    }
                }
            }
        });
    }

    public int addMusic(int musicId) {
        this.time++;
        // 1. contain the music
        if (database.containsKey(musicId)) {
            return -1;
        }

        Music newMusic = new Music(musicId, this.time, 0);
        // 2. database is not full
        if (database.size() < this.capacity) {
            database.put(musicId, newMusic);
            heap.offer(newMusic);
            return 0;
        } else {
            // 3. database full
            Music oldMusic = heap.poll();
            database.remove(oldMusic);
            // add the new music
            database.put(musicId, newMusic);

            return oldMusic.musicId;
        }
    }

    public boolean playMusic(int musicId) {
        this.time++;
        if (database.containsKey(musicId)) {
            Music music = database.get(musicId);
            heap.remove(music);
            // play music
            music.count++;
            if (music.count == 1) {
                music.firstPlay = this.time;
            }

            database.put(musicId, music);
            heap.offer(music);
            return true;
        }

        return false;
    }

    public boolean deleteMusic(int musicId) {
        this.time++;
        if (database.containsKey(musicId)) {
            Music music = database.remove(musicId);
            heap.remove(music);
            return true;
        }
        return false;
    }
}
```
