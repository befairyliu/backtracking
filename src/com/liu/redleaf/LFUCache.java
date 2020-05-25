package com.liu.redleaf;

import java.util.HashMap;
import java.util.Map;

// leetcode 460. LFU缓存机制
// 哈希链表：查找快，插入快，删除快，有序
public class LFUCache {
    int capacity;
    int size;
    Map<Integer, Node> cacheMap;
    // firstLinkedList.post 是频次最大的双向链表
    DoublyLinkedList firstLinkedList;
    // lastLinkedList.pre 是频次最小的双向链表，
    // 满了之后删除 lastLinkedList.pre.tail.pre 这个Node即为频次最小且访问最早的Node
    DoublyLinkedList lastLinkedList;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.cacheMap = new HashMap<>(capacity);
        this.firstLinkedList = new DoublyLinkedList();
        this.lastLinkedList = new DoublyLinkedList();
        this.firstLinkedList.next = this.lastLinkedList;
        this.lastLinkedList.pre = this.firstLinkedList;
    }

    public int get(int key) {
        if (!cacheMap.containsKey(key)) {
            return -1;
        }

        Node node = cacheMap.get(key);
        freqInc(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0) {
            return;
        }

        Node node = cacheMap.get(key);
        // 若存在key值，则更新value，访问频次+1
        if (node != null) {
            node.value = value;
            freqInc(node);
        } else {
            // 若key不存在
            if (size == capacity) {
                // 如果缓存满了，删除lastLinkedList.pre这个链表中的tail.pre这个Node
                cacheMap.remove(lastLinkedList.pre.tail.pre.key);
                lastLinkedList.remove(lastLinkedList.pre.tail.pre);
                size--;
                if (lastLinkedList.pre.head.next == lastLinkedList.pre.tail) {
                    removeDoublyLinkedList(lastLinkedList.pre);
                }
            }

            // cacheMap中put新的key-value node
            // 并将新node加入表示freq为1的DoublyLinkedList中，若不存在freq为1的DoublyLinkedList则新建
            Node newNode = new Node(key, value);
            cacheMap.put(key, newNode);
            if (lastLinkedList.pre.freq != 1) {
                DoublyLinkedList newLinkedList = new DoublyLinkedList(1);
                addDoublyLinkedList(newLinkedList, lastLinkedList.pre);
                newLinkedList.addFirst(newNode);
            } else {
                lastLinkedList.pre.addFirst(newNode);
            }
            size++;
        }
    }

    // node的频次+1
    // 将node从原freq对应的双向链表里移除, 如果链表空了则删除链表。
    public void freqInc(Node node) {
        DoublyLinkedList currentList = node.doublyLinkedList;
        DoublyLinkedList preList = currentList.pre;
        currentList.remove(node);
        if (currentList.head.next == currentList.tail) {
            removeDoublyLinkedList(currentList);
        }

        // 将node加入新freq对应的双向链表，若该链表不存在，则先创建该链表
        node.freq++;
        if (preList.freq != node.freq) {
            DoublyLinkedList newList = new DoublyLinkedList(node.freq);
            addDoublyLinkedList(newList, preList);
            newList.addFirst(node);
        } else {
            preList.addFirst(node);
        }
    }

    // 增加代表某1频次的双向链表
    public void addDoublyLinkedList(DoublyLinkedList newList, DoublyLinkedList preList) {
        newList.next = preList.next;
        newList.next.pre = newList;
        newList.pre = preList;
        preList.next = newList;
    }

    // 删除某一频次的双向链表
    public void removeDoublyLinkedList(DoublyLinkedList doublyLinkedList) {
        doublyLinkedList.pre.next = doublyLinkedList.next;
        doublyLinkedList.next.pre = doublyLinkedList.pre;
    }


    // list node
    static class Node {
        int key;
        int value;
        int freq = 1;
        Node pre; // 双向链表的前继节点
        Node next; // 双向链表的后继节点
        // node所在频次的双向链表
        DoublyLinkedList doublyLinkedList;

        public Node() {}

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    // doubly list
    static class DoublyLinkedList {
        int freq; // 该双向链表所存储节点的频次
        Node head; // 该双向链表的头节点，新节点从头部加入，表示最近访问
        Node tail; // 该双向链表的尾巴节点，删除节点时从尾部删除，表示最久访问
        DoublyLinkedList pre; // 该双向链表的前继链表
        DoublyLinkedList next; // 该双向链表的后继链表

        public DoublyLinkedList() {
            this.head = new Node(0, 0);
            this.tail = new Node(0, 0);
            this.head.next = this.tail;
            this.tail.pre = this.head;
        }

        public DoublyLinkedList(int freq) {
            this.head = new Node(0, 0);
            this.tail = new Node(0, 0);
            this.head.next = this.tail;
            this.tail.pre = this.head;
            this.freq = freq;
        }

        // add node at list first
        public void addFirst(Node node) {
            node.next = head.next;
            node.pre = head;
            head.next.pre = node;
            head.next = node;
            node.doublyLinkedList = this;
        }

        // delete the node
        public void remove(Node node) {
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }

        // delete the last node and return
        public Node removeLast() {
            if (tail.pre == head) {
                return null;
            }

            Node last = tail.pre;
            remove(last);
            return last;
        }
    }
}
