package com.liu.redleaf;

import javafx.util.Pair;
import java.util.*;

public class TreeLeaf {
    
    // Definition for a binary tree node.
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    //1.isSymmetric tree 对称二叉树
    public static boolean isSymmetric(TreeNode root){
        if(root == null){
            return true;
        }

        return isSymmetricTree(root.left, root.right);
    }

    public static boolean isSymmetricTree(TreeNode left, TreeNode right){
        if(left == null && right == null){
            return true;
        } else if ((left == null && right != null)
                || (left != null && right == null)){
            return false;
        } else {
            // todo
            if(left.val == right.val){
                return isSymmetricTree(left.left, right.right)
                        && isSymmetricTree(left.right, right.left);
            } else {
                return false;
            }
        }
    }

    //2. 二叉树的最大深度
    //2.1 recursion 递归方法
    public static int maxDepth(TreeNode root){
        if(root == null){
            return 0;
        }
        //return depth(root.left, root.right);
        return Math.max(maxDepth(root.left), maxDepth(root.right))+1;
    }

    //2.2 BFS method
    public int maxDepthBFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int level = 0;
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            level++;
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.remove();
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
        }
        return level;
    }

    // or method
    public int maxDepthBFS2(TreeNode root) {
        Queue<Pair<TreeNode, Integer>> stack = new LinkedList<>();
        if (root != null) {
            stack.add(new Pair(root, 1));
        }

        int depth = 0;
        while (!stack.isEmpty()) {
            Pair<TreeNode, Integer> current = stack.poll();
            root = current.getKey();
            int current_depth = current.getValue();
            if (root != null) {
                depth = Math.max(depth, current_depth);
                stack.add(new Pair(root.left, current_depth + 1));
                stack.add(new Pair(root.right, current_depth + 1));
            }
        }
        return depth;
    }


    //2.3 DFS method
    int maxLevel = 0;
    public int maxDepthDFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        DFS(root, 1);
        return maxLevel;
    }

    public void DFS(TreeNode root, int level) {
        if (root == null)
            return;
        if (level > maxLevel) maxLevel = level;
        DFS(root.left, level + 1);
        DFS(root.right, level + 1);
    }

    //3. 构造二叉树
    //test case1:
    //前序遍历 preorder = [3,9,20,15,7]
    //中序遍历 inorder = [9,3,15,20,7]
    //3.根据前序和中序遍历序列构造二叉树
    // 3.1 start from first preorder element
    int pre_idx = 0;
    int[] preorder;
    int[] inorder;
    HashMap<Integer, Integer> idx_map = new HashMap<Integer, Integer>();

    public TreeNode helper(int in_left, int in_right) {
        // if there is no elements to construct subtrees
        if (in_left == in_right)
            return null;

        // pick up pre_idx element as a root
        int root_val = preorder[pre_idx];
        TreeNode root = new TreeNode(root_val);

        // root splits inorder list
        // into left and right subtrees
        int index = idx_map.get(root_val);

        // recursion
        pre_idx++;
        // build left subtree
        root.left = helper(in_left, index);
        // build right subtree
        root.right = helper(index + 1, in_right);
        return root;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        this.preorder = preorder;
        this.inorder = inorder;

        // build a hashmap value -> its index
        int idx = 0;
        for (Integer val : inorder)
            idx_map.put(val, idx++);
        return helper(0, inorder.length);
    }

    //3.2 method2
    // 找出前序序列preorder的第一个数，这就是整棵树的根节点，并且这个数将中序序列inorder分为左子树的中序序列和右子树的中序序列，把这作为递归中的inorder；
    // 递归中的preorder怎么找？preorder数组的排列是[根节点的值,左子树的前序序列,右子树的前序序列]，因此只要取出左右子树的前序序列作为递归参数传下去就行；
    //这个分解的位置就是根节点值在inorder中位置；
    public TreeNode buildTree2(int[] preorder, int[] inorder) {
        if(preorder.length==0) return null;
        int val = preorder[0], i=0;
        TreeNode node = new TreeNode(val);
        while(inorder[i]!=val) i++;
        node.left = buildTree2(Arrays.copyOfRange(preorder,1,i+1),Arrays.copyOfRange(inorder,0,i));
        node.right = buildTree2(Arrays.copyOfRange(preorder,i+1,preorder.length), Arrays.copyOfRange(inorder,i+1,inorder.length));
        return node;
    }

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

    //6. leetcode 543 二叉树的直径
    // 给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过根结点。
    // 解题思路：
    // 二叉树的直径不一定过根节点，因此需要去搜一遍所有子树(例如以root，root.left, root.right...为根节点的树)对应的直径，取最大值。
    //root的直径 = root左子树高度 + root右子树高度
    //root的高度 = max {root左子树高度, root右子树高度} + 1
    int diameter = 0; // save the max diameter
    public int diameterOfBinaryTree(TreeNode root) {
        nodeDepth(root);
        return diameter;
    }

    //计算一个节点的深度
    private int nodeDepth(TreeNode node){
        if(node == null){
            return 0;
        }

        //左儿子为根的子树的深度
        int leftDepth = nodeDepth(node.left);
        //右儿子为根的子树的深度
        int rightDepth = nodeDepth(node.right);
        //将每个节点最大直径(左子树深度+右子树深度)当前最大值比较并取大者
        diameter = Math.max(leftDepth + rightDepth, diameter);

        //返回节点的深度(左/右子树中最大的深度 + 节点自己)
        return Math.max(leftDepth, rightDepth) + 1;
    }

    // 7. leetcode 124 二叉树中的最大路径和
    // 给定一个非空二叉树，返回其最大路径和。
    // 路径被定义为一条从树中任意节点出发，达到任意节点的序列。该路径至少包含一个节点，且不一定经过根节点。
    int maxPath = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        nodeMaxPath(root);
        return maxPath;
    }

    private int nodeMaxPath(TreeNode root) {
        if (root == null) {
            return 0;
        }

        // max sum on the left and right sub-trees of node
        int leftSum = Math.max(0, nodeMaxPath(root.left)); // 和上题唯一的区别在这里，如果左右孩子的结果是负数的话就舍弃。
        int rightSum = Math.max(0, nodeMaxPath(root.right));

        // the price to start a new path where `node` is a highest node
        int priceNewPath = root.val + leftSum + rightSum;
        // update max_sum if it's better to start a new path
        maxPath = Math.max(maxPath, priceNewPath);
        // for recursion :
        // return the max gain if continue the same path
        return Math.max(leftSum, rightSum) + root.val;
    }

    // 8. leetcode   最长同值路径
    int res = 0;
    public int longestUnivaluePath(TreeNode root) {
        univalueHelper(root);
        return res;
    }

    public int univalueHelper(TreeNode root){
        if(root == null){
            return 0;
        }

        int left = univalueHelper(root.left);
        int right = univalueHelper(root.right);

        if((root.left != null) && (root.left.val == root.val)){
            left = left + 1;
        } else {
            left = 0;
        }

        if((root.right != null) && (root.right.val == root.val) ){
            right = right + 1;
        } else {
            right = 0;
        }

        res = Math.max(res, left + right);

        return Math.max(left, right);
    }

    //9. leetcode  面试题32 - I. 从上到下打印二叉树
    // BFS
    public static int[] levelOrder(TreeNode root){
        // check param
        if(root == null){
            return new int[]{};
        }

        Queue<TreeNode> queue = new LinkedList<>();
        List<Integer> list = new ArrayList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            //remove the head
            TreeNode current = queue.remove();
            list.add(current.val);

            if(current.left != null){
                queue.add(current.left);
            }

            if(current.right != null){
                queue.add(current.right);
            }
        }

        int[] res = new int[list.size()];
        for(int i=0; i < list.size(); i++){
            res[i] = list.get(i);
        }

        return res;
    }

    //10. leetcode 面试题 04.06. 后继者
    // 如果结点 p 的值大于等于 root 的值，说明 p 的后继结点在 root 右子树中，那么就递归到右子树中查找。
    //如果结点 p 的值小于 root 的值，说明 p 在 root 左子树中，而它的后继结点有两种可能，要么也在左子树中，要么就是 root：
    //--如果左子树中找到了后继结点，那就直接返回答案。
    //--如果左子树中没有找到后继结点，那就说明 p 的右儿子为空，那么 root 就是它的后继结点。
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if(root == null){
            return null;
        }

        if(root.val <= p.val){
            return inorderSuccessor(root.right, p);
        } else {
            TreeNode left = inorderSuccessor(root.left, p);
            return left == null ? root : left;
        }
    }
    
    // 11. leetcode 297 面试题37. 序列化及反序列化二叉树
    // 序列化为 "[1,2,3,null,null,4,5]"
    public String serialize(TreeNode root) {
        if(root == null) return "[]";
        String res = "[";
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            TreeNode cur = queue.poll();
            if(cur!=null){
                res += cur.val+",";
                queue.offer(cur.left);
                queue.offer(cur.right);
            } else {
                res += "null,";
            }
        }
        //去除最后的一个逗号,
        res = res.substring(0, res.length() - 1);
        res += "]";
        return res;
    }
    
    // 或者下面的方法
    public String serializeII(TreeNode root) {
        if(root == null) return "[]";
        String res = "[";
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        res += root.val;
        while(!queue.isEmpty()){
            TreeNode current = queue.poll();
            if(current.left != null){
                res += "," + current.left.val;
                queue.offer(current.left);
            } else {
                res += ",null";
            }

            if(current.right != null){
                res += "," + current.right.val;
                queue.offer(current.right);
            } else {
                res += ",null";
            }
        }
        res += "]";
        return res;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if(data == null || "[]".equals(data)) return null;
        String res = data.substring(1,data.length()-1);
        String[] values = res.split(",");
        int index = 0;
        TreeNode root = buildTreeNode(values[index++]);
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode current = root;
        queue.offer(root);
        while(!queue.isEmpty()){
            current = queue.poll();
            current.left = buildTreeNode(values[index++]);
            current.right = buildTreeNode(values[index++]);
            if(current.left != null){
                queue.offer(current.left);
            }
            if(current.right!=null){
                queue.offer(current.right);
            }
        }
        return root;
    }

    public TreeNode buildTreeNode(String value){
        if("null".equals(value)) return null;
        return new TreeNode(Integer.valueOf(value));
    }
    
     //12 leetcode 面试题33. 二叉搜索树的后序遍历序列
    // 判断一个二叉树是否是后续遍历序列
    // 左子树 < 根节点 < 右子树
    public boolean verifyPostorder(int[] postorder) {
        //check params
        if(postorder == null || postorder.length < 2){
            return true;
        }

        return isPostorder(postorder, 0, postorder.length - 1);
    }

    public boolean isPostorder(int[] order, int start, int end){
        if(start >= end){
            //说明只有一个节点
            return true;
        }

        //1.根节点
        int root = order[end];
        //2.寻找理论左右子树分割点
        int i = start;
        while(order[i] < root){
            i++;
        }

        // 理论上的分割点i已找到
        int divider = i;
        // 3.检查数组后面的数是否满足二叉搜索树的特点（右子树 > 根节点）
        for(; i < end; i++){
            if(order[i] < root){
                // 如果右子树节点 小于 根节点，那明显不满足条件
                return false;
            }
        }

        //4. 本层满足条件后，再递归检查左右子树
        boolean left = isPostorder(order, start, divider - 1);
        boolean right = isPostorder(order, divider, end - 1);

        return  left && right;
    }

    // 13 leetcode 面试题34. 二叉树中和为某一值的所有路径
    List<List<Integer>> result = new LinkedList<>();
    LinkedList<Integer> path = new LinkedList<>();
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        recursionPathSum(root, sum);
        return result;
    }

    //Recursion
    public void recursionPathSum(TreeNode current, int sum){
        if(current == null){
            return;
        }
        // 添加来链表中
        path.add(current.val);
        // 叶子节点
        if(current.left == null && current.right == null && current.val == sum){
            result.add(new LinkedList<>(path));
        }

        // 非叶子节点, 左右子树查找
        int target = sum - current.val;
        recursionPathSum(current.left, target);
        recursionPathSum(current.right, target);
        // 回溯，去掉之前添加进来的节点
        path.removeLast();
    }
    
    
    //14. leetcode 156. 上下翻转二叉树（树旋转了一下，不是翻转）
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

    // 15. leetcode 199. 二叉树的右视图
    // 给定一棵二叉树，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
    // 方法1： BFS
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        //check input params
        if (root == null) {
            return res;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                // 每层的最后一个节点入链表
                if (i == size - 1) {
                    res.add(node.val);
                }
            }
        }

        return res;
    }

    // 方法2： DFS
    // 根节点 -> 右子树节点 -> 左子树节点
    List<Integer> rightSideList = new ArrayList<>();
    public List<Integer> rightSideViewII(TreeNode root) {
        rightSideDFS(root, 0);
        return rightSideList;
    }

    private void rightSideDFS (TreeNode root, int depth) {
        if (root == null) {
            return;
        }
        //每层第一个访问的节点
        if (depth == rightSideList.size()) {
            rightSideList.add(root.val);
        }

        depth++;
        rightSideDFS(root.right, depth);
        rightSideDFS(root.left, depth);
    }

//    当节点 B 为空：说明树 B 已匹配完成（越过叶子节点），因此返回 truetrue ；
//    当节点 A 为空：说明已经越过树 A 的叶节点，即匹配失败，返回 falsefalse ；
//    当节点 A 和 B 的值不同：说明匹配失败，返回 falsefalse ；

    public boolean isSubStructure(TreeNode A, TreeNode B) {
        return (A != null && B != null) && (recur(A, B)
            || isSubStructure(A.left, B)
            || isSubStructure(A.right, B));
    }

    boolean recur(TreeNode A, TreeNode B) {
        if(B == null) return true;
        if(A == null || A.val != B.val) return false;
        return recur(A.left, B.left) && recur(A.right, B.right);
    }


    public TreeNode mirrorTree(TreeNode root) {
        TreeNode target = recursionTree(root);
        return target;
    }

    private TreeNode recursionTree (TreeNode root){
        // 终止条件
        if (root == null) {
            return null;
        }

        TreeNode target = new TreeNode(root.val);
        target.left = recursionTree(root.right);
        target.right = recursionTree(root.left);

        return target;
    }

    public boolean isSymmetricII(TreeNode root) {
        // 1.终止条件
        if (root == null) {
            return true;
        }

        // 2.递归
        return isSymmetricTreeII (root.left, root.right);
    }

    private boolean isSymmetricTreeII (TreeNode left, TreeNode right){
        if (left == null && right == null) {
            return true;
        } else if ((left == null && right != null)
            || (left != null && right == null)) {
            return false;
        } else {
            if (left.val == right.val) {
                return isSymmetricTreeII(left.left, right.right)
                    && isSymmetricTreeII(left.right, right.left);
            } else {
                return false;
            }
        }
    }

    public int[] levelOrderII(TreeNode root) {
        if(root == null){
            return new int[] {};
        }

        Queue<TreeNode> queue = new LinkedList<>();
        Queue<TreeNode> temp = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            TreeNode current = queue.poll();
            temp.offer(current);

            if (current.left != null) queue.offer(current.left);
            if (current.right != null) queue.offer(current.right);
        }

        int[] res = new int[temp.size()];
        for(int i = 0; i < res.length; i++) {
            res[i] = temp.poll().val;
        }

        return res;
    }

    public List<List<Integer>> levelOrderIII(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();
        if(root == null){
            res.add(new ArrayList<>());
            return res;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            List<Integer> temp = new LinkedList<>();
            for (int i = 0; i < queue.size(); i++) {
                TreeNode current = queue.poll();
                temp.add(current.val);

                if (current.left != null) queue.offer(current.left);
                if (current.right != null) queue.offer(current.right);
            }

            res.add(temp);
        }

        return res;
    }

    public TreeNode lowestCommonAncestorII(TreeNode root, TreeNode p, TreeNode q) {
        // 1. 递归终止条件
        if (root == null || root == p || root == q) {
            return root;
        }

        // 2. 递归 - 后序遍历
        TreeNode left = lowestCommonAncestorII(root.left, p, q);
        TreeNode right = lowestCommonAncestorII(root.right, p, q);

        // 3. 根据不同的情况返回结果
        // 3.1 当left == null && right == null
        if (left == null && right == null) {
            return null;
        } else if (left == null) {
            // 3.2 当left == null && right != null
            return right;
        } else if (right == null) {
            // 3.3 当left != null && right == null
            return left;
        } else {
            // 3.4 default, 当left != null && right != null
            return root;
        }
    }
    
    //=====================================================================

    public static void main(String[] args){

//        int[] preorder = {3, 9, 20, 15, 7};
//        int[] inorder = {9, 3, 15, 20, 7};
//
//        TreeLeaf instance = new TreeLeaf();
//        TreeNode node = instance.buildTree3(preorder, inorder);
//        System.out.println("======node========");
//        System.out.println("node:"+node.toString());

        Integer[] aa = {9, 3, 15, 20, 7};
        Arrays.sort(aa, new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2) {
                if(o1 < o2){
                    return -1;
                } else if(o1 > o2){
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        for(int i = 0; i < aa.length; i ++) {
            System.out.print(aa[i] + " ");
        }
    }
}
