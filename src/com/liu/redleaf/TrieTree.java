package com.liu.redleaf;

import java.util.HashMap;
import java.util.Map;

public class TrieTree {

    class TrieNode {
        boolean isWord;
        Map<Character, TrieNode> map;
        public TrieNode() {
            this.map = new HashMap<>();
        }
    }

    private TrieNode root;
    /** Initialize your data structure here. */
    public TrieTree() {
        this.root = new TrieNode();
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode current = this.root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (current.map.get(c) == null) {
                current.map.put(c, new TrieNode());
            }

            current = current.map.get(c);
        }

        current.isWord = true;
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {

        TrieNode current = this.root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (current.map.get(c) == null) {
                return false;
            }
            current = current.map.get(c);
        }

        return current.isWord;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {

        TrieNode current = this.root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (current.map.get(c) == null) {
                return false;
            }
            current = current.map.get(c);
        }

        return true;
    }
}
