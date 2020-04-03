package com.liu.vip.studio;

import java.util.*;

public class ArrayLeaf {
    // 1. leetcode vpi -- 253 会议室II
    // 给定一个会议时间安排的数组，每个会议时间都会包括开始和结束的时间 [[s1,e1],[s2,e2],...] (si < ei)，为避免会议冲突，同时要考虑充分利用会议室资源，请你计算至少需要多少间会议室，才能满足这些会议安排。
    // 输入: [[0, 30],[5, 10],[15, 20]]
    // 输出: 2
    
    // 静态内部类，存储会议的开始时间和结束时间
    public static class Interval {
        int start;
        int end;

        Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
    
    public int minMeetingRooms(int[][] intervals) {
        int length = intervals.length;
        Interval[] inters = new Interval[length];
        for(int i = 0; i < length; i++){
            inters[i] = new Interval(intervals[i][0], intervals[i][1]);
        }
        return roomsHelper(inters);
    }

    // 方法一：优先队列
    // 1.按照 开始时间 对会议进行排序。
    // 2.初始化一个新的 最小堆，将第一个会议的结束时间加入到堆中。我们只需要记录会议的结束时间，告诉我们什么时候房间会空。
    // 3.对每个会议，检查堆的最小元素（即堆顶部的房间）是否空闲。
    //  3.1若房间空闲，则从堆顶拿出该元素，将其改为我们处理的会议的结束时间，加回到堆中。
    //  3.2若房间不空闲。开新房间，并加入到堆中。
    // 4.处理完所有会议后，堆的大小即为开的房间数量。这就是容纳这些会议需要的最小房间数。

    public int roomsHelper(Interval[] intervals) {
        // Check for the base case. If there are no intervals, return 0
        if (intervals.length == 0) {
            return 0;
        }

        // Min heap
        PriorityQueue<Integer> allocator =
                new PriorityQueue<Integer>(
                        intervals.length,
                        new Comparator<Integer>() {
                            public int compare(Integer a, Integer b) {
                                return a - b;
                            }
                        });

        // Sort the intervals by start time
        Arrays.sort(intervals, new Comparator<Interval>(){
            @Override
            public int compare(Interval o1, Interval o2) {
                return o1.start - o2.start;
            }
        });

        // Add the first meeting
        allocator.add(intervals[0].end);

        // Iterate over remaining intervals
        for (int i = 1; i < intervals.length; i++) {
            // If the room due to free up the earliest is free, assign that room to this meeting.
            if (intervals[i].start >= allocator.peek()) {
                allocator.poll();
            }

            // If a new room is to be assigned, then also we add to the heap,
            // If an old room is allocated, then also we have to add to the heap with updated end time.
            allocator.add(intervals[i].end);
        }

        // The size of the heap tells us the minimum rooms required for all the meetings.
        return allocator.size();
    }

    // 方法二：有序化
    // 1.分别将开始时间和结束时间存进两个数组。
    // 2.分别对开始时间和结束时间进行排序。请注意，这将打乱开始时间和结束时间的原始对应关系。它们将被分别处理。
    // 3.考虑两个指针： s_ptr 和 e_ptr，分别代表开始指针和结束指针。开始指针遍历每个会议，结束指针帮助我们跟踪会议是否结束。
    // 4.当考虑 s_ptr 指向的特定会议时，检查该开始时间是否大于 e_ptr 指向的会议。若如此，则说明 s_ptr 开始时，已经有会议结束。于是我们可以重用房间。否则，我们就需要开新房间。
    // 5.若有会议结束，换而言之， start[s_ptr] >= end[e_ptr]，则自增 e_ptr。
    // 6.重复这一过程，直到 s_ptr 处理完所有会议
    public int roomsHelperII(Interval[] intervals) {

        // Check for the base case. If there are no intervals, return 0
        if (intervals.length == 0) {
            return 0;
        }

        Integer[] start = new Integer[intervals.length];
        Integer[] end = new Integer[intervals.length];

        for (int i = 0; i < intervals.length; i++) {
            start[i] = intervals[i].start;
            end[i] = intervals[i].end;
        }

        // Sort the intervals by end time
        Arrays.sort( end, new Comparator<Integer>() {
                    public int compare(Integer a, Integer b) {
                        return a - b;
                    }
                });

        // Sort the intervals by start time
        Arrays.sort( start, new Comparator<Integer>() {
                    public int compare(Integer a, Integer b) {
                        return a - b;
                    }
                });

        // The two pointers in the algorithm: e_ptr and s_ptr.
        int startPointer = 0, endPointer = 0;

        // Variables to keep track of maximum number of rooms used.
        int usedRooms = 0;

        // Iterate over intervals.
        while (startPointer < intervals.length) {

            // If there is a meeting that has ended by the time the meeting at `start_pointer` starts
            if (start[startPointer] >= end[endPointer]) {
                usedRooms -= 1;
                endPointer += 1;
            }

            // We do this irrespective of whether a room frees up or not.
            // If a room got free, then this used_rooms += 1 wouldn't have any effect. used_rooms would
            // remain the same in that case. If no room was free, then this would increase used_rooms
            usedRooms += 1;
            startPointer += 1;
        }

        return usedRooms;
    }
    
    
    // 2. leetcode 289. 生命游戏
    //给定一个包含 m × n 个格子的面板，每一个格子都可以看成是一个细胞。每个细胞都具有一个初始状态：1 即为活细胞（live），或 0 即为死细胞（dead）。每个细胞与其八个相邻位置（水平，垂直，对角线）的细胞都遵循以下四条生存定律：

    // 如果活细胞周围八个位置的活细胞数少于两个，则该位置活细胞死亡；
    // 如果活细胞周围八个位置有两个或三个活细胞，则该位置活细胞仍然存活；
    // 如果活细胞周围八个位置有超过三个活细胞，则该位置活细胞死亡；
    // 如果死细胞周围正好有三个活细胞，则该位置死细胞复活；
    // 根据当前状态，写一个函数来计算面板上所有细胞的下一个（一次更新后的）状态。下一个状态是通过将上述规则同时应用于当前状态下的每个细胞所形成的，其中细胞的出生和死亡是同时发生的。
    
    // 方法一：复制原数组进行模拟，
    // 时间复杂度：O(mn)，
    // 空间复杂度：O(mn)   为复制数组占用的空间
    public void gameOfLife(int[][] board) {
        //相邻细胞的坐标位置向量
        int[] neighbors = {-1, 0, 1};
        int rows = board.length;
        int cols = board[0].length;

        //build copy board
        int[][] copyBoard = new int[rows][cols];
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                copyBoard[row][col] = board[row][col];
            }
        }

        //iterate the cell of board
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                //对于每个细胞统计其八个相邻位置里的活细胞数量
                int liveNeighbors = 0;
                for(int i = 0; i < 3; i++){
                    for(int j= 0; j < 3; j++){
                        // neighbors[i] == 0 && neighbors[j] == 0的时候i == 0 && j == 0
                        // 也就是细胞自己的位置向量
                        if(!(neighbors[i] == 0 && neighbors[j] == 0)){
                            int r = (row + neighbors[i]);
                            int c = (col + neighbors[j]);

                            // 查看相邻的细胞是否是活细胞
                            if((r < rows && r >= 0) && (c < cols && c >= 0)
                                    && (copyBoard[r][c] == 1)){
                                liveNeighbors += 1;
                            }
                        }
                    }
                }

                // 规则1 或 规则2
                if((copyBoard[row][col] == 1) && (liveNeighbors < 2 || liveNeighbors > 3)){
                    board[row][col] = 0;
                }
                //规则4
                if(copyBoard[row][col] == 0 && liveNeighbors == 3){
                    board[row][col] = 1;
                }
            }
        }
    }

    //方法二：使用额外的状态
    // 原数组细胞状态使用额外的状态存储
    // 2： 0 -> 1 死细胞变成活细胞
    // -1: 1 -> 0 活细胞变成死细胞
    public void gameOfLifeII(int[][] board) {
        //相邻细胞的坐标位置向量
        int[] neighbors = {-1, 0, 1};

        int rows = board.length;
        int cols = board[0].length;

        // 遍历面板每一个格子里的细胞
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                // 对于每一个细胞统计其八个相邻位置里的活细胞数量
                int liveNeighbors = 0;

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {

                        if (!(neighbors[i] == 0 && neighbors[j] == 0)) {
                            // 相邻位置的坐标
                            int r = (row + neighbors[i]);
                            int c = (col + neighbors[j]);

                            // 查看相邻的细胞是否是活细胞
                            if ((r < rows && r >= 0) && (c < cols && c >= 0) && (Math.abs(board[r][c]) == 1)) {
                                liveNeighbors += 1;
                            }
                        }
                    }
                }

                // 规则 1 或规则 3
                if ((board[row][col] == 1) && (liveNeighbors < 2 || liveNeighbors > 3)) {
                    // -1 代表这个细胞过去是活的现在死了
                    board[row][col] = -1;
                }
                // 规则 4
                if (board[row][col] == 0 && liveNeighbors == 3) {
                    // 2 代表这个细胞过去是死的现在活了
                    board[row][col] = 2;
                }
            }
        }

        // 遍历 board 得到一次更新后的状态
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col] > 0) {
                    board[row][col] = 1;
                } else {
                    board[row][col] = 0;
                }
            }
        }
    }
}
