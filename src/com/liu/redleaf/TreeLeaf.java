package com.liu.redleaf;

import java.util.Stack;

public class TreeLeaf {

    // Definition for a binary tree node.
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public static void main(String[] args){
        System.out.println("Hello Red Leaf !");
    }

    //1. leetcode 112 -- hasPathSum
    public boolean hasPathSum(TreeNode root, int sum) {
        if(root == null){
            return false;
        }

        //1. Leaf node
        if(root.left == null && root.right == null){
            if(root.val == sum){
                return true;
            }
        } else {
            return hasPathSum(root.left, sum - root.val)
                    || hasPathSum(root.right, sum - root.val);
        }

        return false;
    }
}

//test case1
//[1,-2,-3,1,3,-2,null,-1]
//-1