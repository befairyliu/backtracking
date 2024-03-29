package com.liu.redleaf;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

//树的遍历算法
//其他算法方式(Morris解法)详解参考
//https://blog.csdn.net/weixin_42322309/article/details/104177275
public class TreeTraversalLeaf {

    // Definition for a binary tree node.
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    //1. BFS 层序优先遍历
    public static void levelOrder(TreeNode root){
        if(root == null){
            return;
        }

        LinkedList<TreeNode> list = new LinkedList<>();
        list.add(root);
        while(!list.isEmpty()){
            TreeNode current = list.remove();
            //do something
            if(current.left != null){
                list.add(current.left);
            }
            if(current.right != null){
                list.add(current.right);
            }
        }
    }

    //2. DFS 深度优先遍历

    //3. 统计Tree的节点
    // 使用前序遍历
    static ArrayList<Integer> list = new ArrayList<>();

    public static int[] countOrder(TreeNode root){
        if(root == null){
            return new int[] {};
        }

        // call the preOrder
        preOrderRecursion(root);
        //preOrderIteration(root);

        int[] res = new int[list.size()];
        for(int i = 0; i < list.size(); i++){
            res[i] = list.get(i);
        }
        return res;
    }

    // 3.1 前序遍历 -- recursion method
    public static void preOrderRecursion(TreeNode root){
        if(root == null){
            return;
        }

        //前序加入root,然后是左右子树
        list.add(root.val);
        preOrderRecursion(root.left);
        preOrderRecursion(root.right);
    }

    //3.2 前序遍历 非递归 -- iteration method
    public static List<Integer> preOrderIteration(TreeNode root){
        List<Integer> list = new ArrayList<>();
        if(root == null){
            return list;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode current = stack.pop();
            list.add(current.val);
            // 先放右节点进栈，再放左节点进栈
            if(current.right != null){
                stack.push(current.right);
            }
            if(current.left != null){
                stack.push(current.left);
            }
        }

        return list;
    }

    //4. 统计Tree的节点
    //4.1 使用中序遍历 -- recursion method
    public static void midOrderRecursion(TreeNode root){
        if(root == null){
            return;
        }

        //中序先左子树，然后是根和右子树
        midOrderRecursion(root.left);
        list.add(root.val);
        midOrderRecursion(root.right);
    }

    // 4.2 中序 非递归
    // 1、申请一个栈stack，初始时令current = root
    //2、先把current压入栈中，依次把左边界压入栈中，即不停的令current=current.left，重复步骤2
    //3、不断重复2，直到为null，从stack中弹出一个节点，记为node，记录node的值，并令current=node.right,重复步骤2
    //4、当stack为空且current为空时，整个过程停止
    public static List<Integer> midOrderIteration(TreeNode root){
        List<Integer> list = new ArrayList<>();
        if(root == null){
            return list;
        }

        Stack<TreeNode> stack = new Stack<>();
        //stack.push(root);
        TreeNode current = root;
        while(current != null || !stack.isEmpty()){
            while (current != null) {
                // 把左边界压入栈中,直到为左字树null
                stack.push(current);
                current = current.left;
            } 
            
            // 记为node，记录node的值，并令current=node.right,重复步骤2
            current = stack.pop();
            list.add(current.val);
            current = current.right;
        }
        return list;
    }
    
    // 方法三：Morris 中序遍历
    // 思路与算法
    // Morris 遍历算法是另一种遍历二叉树的方法，它能将非递归的中序遍历空间复杂度降为 O(1)。
    // Morris 遍历算法整体步骤如下（假设当前遍历到的节点为 x）：
    // 如果 x 无左孩子，先将 x 的值加入答案数组，再访问 x 的右孩子，即 x = x.right。
    // 如果 x 有左孩子，则找到 x 左子树上最右的节点（即左子树中序遍历的最后一个节点，x 在中序遍历中的前驱节点），我们记为 predecessor。根据 predecessor 的右孩子是否为空，进行如下操作。
    // 如果 predecessor 的右孩子为空，则将其右孩子指向 x，然后访问 x 的左孩子，即 x = x.left。
    // 如果 predecessor 的右孩子不为空，则此时其右孩子指向 x，说明我们已经遍历完x 的左子树，我们将 predecessor 的右孩子置空，将 x 的值加入答案数组，然后访问 x 的右孩子，即 x = x.right。
    // 重复上述操作，直至访问完整棵树。
    public List<Integer> midOrderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        TreeNode predecessor = null;

        while (root != null) {
            if (root.left != null) {
                // predecessor 节点就是当前 root 节点向左走一步，然后一直向右走至无法走为止
                predecessor = root.left;
                while (predecessor.right != null && predecessor.right != root) {
                    predecessor = predecessor.right;
                }
                
                // 让 predecessor 的右指针指向 root，继续遍历左子树
                if (predecessor.right == null) {
                    predecessor.right = root;
                    root = root.left;
                }
                // 说明左子树已经访问完了，我们需要断开链接
                else {
                    res.add(root.val);
                    predecessor.right = null;
                    root = root.right;
                }
            }
            // 如果没有左孩子，则直接访问右孩子
            else {
                res.add(root.val);
                root = root.right;
            }
        }
        return res;
    }
    

    //5. 统计Tree的节点
    // 5.1 使用后序遍历 -- recursion method
    public static void postOrderRecursion(TreeNode root){
        if(root == null){
            return;
        }

        //中序先左子树，然后是右子树和根
        postOrderRecursion(root.left);
        postOrderRecursion(root.right);
        list.add(root.val);
    }


    //5.2 使用后续遍历非递归 -- iteration method
    // 1、申请一个栈s1，然后将头节点压入栈s1中。
    //2、从s1中弹出的节点记为cur，然后依次将cur的左孩子节点和右孩子节点压入s1中。
    //3、在整个过程中，每一个从s1中弹出的节点都放进s2中。
    //4、不断重复步骤2和步骤3，直到s1为空，过程停止。
    //5、从s2中依次弹出节点并打印，打印的顺序就是后序遍历的顺序。
    public static List<Integer> postOrderIteration(TreeNode root){
        List<Integer> list = new ArrayList<>();
        if(root == null){
            return list;
        }

        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        //将头节点压入栈s1中
        stack1.add(root);
        while(!stack1.isEmpty()){
            TreeNode current = stack1.pop();
            stack2.push(current);

            if(current.left != null){
                stack1.push(current.left);
            }
            if(current.right != null){
                stack1.push(current.right);
            }
        }

        while(!stack2.isEmpty()){
            list.add(stack2.pop().val);
        }

        return list;
    }
}
