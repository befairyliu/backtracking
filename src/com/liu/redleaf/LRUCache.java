package com.liu.redleaf;

import java.util.HashMap;
import java.util.Map;

// leetcode 146. LRU缓存机制
// 哈希链表：查找快，插入快，删除快，有序
public class LRUCache {
    int capacity;
    Map<Integer, Node> cacheMap;
    DoublyLinkedList cacheList;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cacheMap = new HashMap<>();
        this.cacheList = new DoublyLinkedList();
    }

    public int get(int key) {
        if (!cacheMap.containsKey(key)) {
            return -1;
        }

        // change this node position to the list first
        int val = cacheMap.get(key).value;
        put(key, val);

        return val;
    }

    public void put(int key, int value) {
        Node node = new Node(key, value);

        if (cacheMap.containsKey(key)) {
            // delete the old
            cacheList.remove(cacheMap.get(key));
        } else {
            if (capacity == cacheList.size()) {
                // delete the last
                Node last = cacheList.removeLast();
                cacheMap.remove(last.key);
            }
        }

        // add to the list header
        cacheList.addFirst(node);
        cacheMap.put(key, node);
    }


    // list node
    static class Node {
        int key;
        int value;
        Node pre;
        Node next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    // doubly list
    static class DoublyLinkedList {
        int size;
        Node head;
        Node tail;

        public DoublyLinkedList() {
            this.head = new Node(0, 0);
            this.tail = new Node(0, 0);
            this.head.next = this.tail;
            this.tail.pre = this.head;
            this.size = 0;
        }

        // add node at list first
        public void addFirst(Node node) {
            node.next = head.next;
            node.pre = head;
            head.next.pre = node;
            head.next = node;
            size++;
        }

        // delete the node
        public void remove(Node node) {
            node.pre.next = node.next;
            node.next.pre = node.pre;
            size--;
        }

        // delete the last node and return
        public Node removeLast() {
            if (size == 0) {
                return null;
            }

            Node last = tail.pre;
            remove(last);
            return last;
        }

        public int size() {
            return size;
        }
    }
}
