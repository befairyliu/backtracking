package com.liu.vip207;

import java.util.*;

public class BinaryTree {
    // Definition for a binary tree node.
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    
    
    //1. leetcode 156. 上下翻转二叉树（树旋转了一下，不是翻转）
    // 规律是：左子节点变父节点；父节点变右子节点；右子节点变左节点。
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if(root == null || (root.left == null)){
            return root;
        }

        // 其中所有的右节点要么是具有兄弟节点（拥有相同父节点的左节点）的叶节点，要么为空
        // 所以递归旋转树，只转换左子树
        TreeNode node = upsideDownBinaryTree(root.left);

        //旋转树
        root.left.right = root;
        root.left.left = root.right;
        // 断关系, 防止形成环
        root.left = null;
        root.right = null;

        return node;
    }

    // leetcode 156 迭代方法
    public TreeNode upsideDownBinaryTreeII(TreeNode root) {
        if(root == null || (root.left == null)){
            return root;
        }

        // 其中所有的右节点要么是具有兄弟节点（拥有相同父节点的左节点）的叶节点，要么为空
        // 所以递归旋转树，只转换左子树
        Stack<TreeNode> stack = new Stack<>();
        TreeNode target = null;
        TreeNode current = root;
        // 找最左子树
        while(current != null){
            stack.push(current);
            current = current.left;
        }

        target = stack.pop();
        current = target;
        //开始旋转
        while(!stack.isEmpty()){
            //当前节点之前的父节点
            TreeNode temp = stack.pop();
            //父节点变右节点
            current.right = temp;
            //前父节点的右节点变左节点
            current.left = temp.right;
            // 把之前的父节点赋值给当前节点，继续循环旋转下去
            current = temp;
        }

        // 防止成环，断关系
        current.left = null;
        current.right = null;

        return target;
    }
    
  
}
