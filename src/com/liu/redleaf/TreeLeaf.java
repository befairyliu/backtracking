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
    //test case1
    //[1,-2,-3,1,3,-2,null,-1]
    //-1
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

//3.根据前序和中序遍历序列构造二叉树
//3.3 method3
    int preIndex = 0;//save the pre order start index
    public TreeNode buildTree3(int[] preorder, int[] inorder){
        if(preorder == null || inorder == null
                || preorder.length != inorder.length){
            return null;
        }

        //save the inoder key -> index
        int index = 0;
        HashMap<Integer, Integer> inMap = new HashMap<Integer, Integer>();
        for (Integer key: inorder) {
            inMap.put(key, index++);
        }

        return treeHelper(inMap, preorder, 0, inorder.length -1);
    }

    // build tree helper for inorder
    public TreeNode treeHelper(final HashMap<Integer, Integer> inMap, final int[] preOrder, int inStart, int inEnd){
        //check null
        if(inStart > inEnd){
            return null;
        }

        //find the root element
        int currentRoot = preOrder[preIndex];
        TreeNode node = new TreeNode(currentRoot);

        //root split the inorder list to left and right subtree
        int inOrderIndex = inMap.get(currentRoot);

        //recursion
        preIndex++;
        System.out.println("The preIndex: "+preIndex);
        //build the left subtree
        node.left = treeHelper(inMap, preOrder, inStart, inOrderIndex-1);
        node.right = treeHelper(inMap, preOrder,inOrderIndex+1, inEnd);

        return node;
    }


    //4.从中序和后序构造二叉树
    int postIndex = 0;//save the post order start index
    public TreeNode buildTree4(int[] inorder, int[] postorder){
        if(postorder == null || inorder == null
                || postorder.length != inorder.length){
            return null;
        }

        //init the postIndex
        postIndex = postorder.length -1;
        //save the inoder key -> index
        int index = 0;
        HashMap<Integer, Integer> inMap = new HashMap<Integer, Integer>();
        for (Integer key: inorder) {
            inMap.put(key, index++);
        }

        return treeHelper2(inMap, postorder, 0, inorder.length -1);
    }

    // build tree helper for inorder
    public TreeNode treeHelper2(final HashMap<Integer, Integer> inMap, final int[] postorder, int inStart, int inEnd){
        //check null
        if(inStart > inEnd){
            return null;
        }

        //find the root element
        int currentRoot = postorder[postIndex];
        TreeNode node = new TreeNode(currentRoot);

        //root split the inorder list to left and right subtree
        int inOrderIndex = inMap.get(currentRoot);

        //recursion
        postIndex--;
        System.out.println("The postIndex: "+postIndex);
        //build the left subtree
        node.right = treeHelper(inMap, postorder,inOrderIndex+1, inEnd);
        node.left = treeHelper(inMap, postorder, inStart, inOrderIndex-1);

        return node;
    }


    //5.给定一个整数 n，生成所有由 1 ... n 为节点所组成的二叉搜索树。
    //5.1 递归法 recursion method
    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return new LinkedList<TreeNode>();
        }
        return buildTrees(1, n);
    }

    public LinkedList<TreeNode> buildTrees(int start, int end) {
        LinkedList<TreeNode> allTrees = new LinkedList<TreeNode>();
        if (start > end) {
            allTrees.add(null);
            return allTrees;
        }

        // pick up a root
        for (int i = start; i <= end; i++) {
            // all possible left subtrees if i is choosen to be a root
            LinkedList<TreeNode> leftTrees = buildTrees(start, i - 1);
            // all possible right subtrees if i is choosen to be a root
            LinkedList<TreeNode> rightTrees = buildTrees(i + 1, end);

            // connect left and right trees to the root i
            for (TreeNode leftTree : leftTrees) {
                for (TreeNode rightTree : rightTrees) {
                    TreeNode root = new TreeNode(i);
                    root.left = leftTree;
                    root.right = rightTree;
                    allTrees.add(root);
                }
            }
        }
        return allTrees;
    }

    //5.2 动态规划
    public List<TreeNode> generateTreesDP(int n){
        //saved the pre all result 保存上一次的所有解
        List<TreeNode> pre = new ArrayList<>();
        if(n == 0){
            return pre;
        }

        pre.add(null);
        //每次增加一个数字
        for(int i= 1; i <= n; i++){
            List<TreeNode> current = new ArrayList<TreeNode>();
            //遍历之前的所有解
            for(TreeNode root: pre){
                //插入到根节点
                TreeNode insert = new TreeNode(i);
                insert.left = root;
                current.add(insert);
                //插入到右孩子，右孩子的右孩子，...最多找N次孩子
                for(int j = 0; j <= n; j++){
                    TreeNode rootCopy = treeCopy(root);//复制当前的树
                    TreeNode right = rootCopy;//找到要插入右孩子的位置
                    int k = 0;
                    //遍历j次找右孩子
                    for (; k < j; k++) {
                        if(right == null) {
                            break;
                        }
                        right = right.right;
                    }

                    //达到null提前结束
                    if(right == null){
                        break;
                    }
                    //保存当前右孩子的位置的子树作为插入节点的左孩子
                    TreeNode rightTree = right.right;
                    insert = new TreeNode(i);
                    right.right = insert; //右孩子是插入的节点
                    insert.left = rightTree; //插入节点的左孩子更新为插入位置之前的子树
                    //加入结果中
                    current.add(rootCopy);
                }
            }
            pre = current;
        }
        return pre;
    }

    private TreeNode treeCopy(TreeNode root){
        if(root == null){
            return null;
        }
        TreeNode newRoot = new TreeNode(root.val);
        newRoot.left = treeCopy(root.left);
        newRoot.right = treeCopy(root.right);
        return newRoot;
    }
