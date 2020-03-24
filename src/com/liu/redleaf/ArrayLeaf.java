package com.liu.redleaf;

import java.util.*;

public class ArrayLeaf {
  
    //1.merge two array
    //1. merge two array test case
    //输入:
    //A = [1,2,3,0,0,0], m = 3
    //B = [2,5,6],       n = 3
    //
    //输出: [1,2,2,3,5,6]
    public static void mergeArray(int[] A, int m, int[] B, int n){

        int i = m -1;
        int j = n -1;
        int k = m + n -1;
        //case1: i >= 0 && j >= 0
        while(i>=0 && j>=0){
            if(A[i] > B[j]){
                A[k--] = A[i--];
            } else {
                A[k--] = B[j--];
            }
        }

        //case2: i > 0 && j == 0, not need to move
        //case3: i == 0 && j > 0
        while(j >= 0){
            A[k--] = B[j--];
        }
    }

    //2. leetcode 386 字典排序, 给定一个整数 n, 返回从 1 到 n 的字典顺序。
    //给定 n =1 3，返回 [1,10,11,12,13,2,3,4,5,6,7,8,9] 。
    //请尽可能的优化算法的时间复杂度和空间复杂度。 输入的数据 n 小于等于 5,000,000。
    //2.1 recursion method
    public static List<Integer> lexicalOrder(int n){
        List<Integer> result = new ArrayList<>();

        lexicalOrder(result, null, n);
        return result;
    }

    public static void lexicalOrder(List<Integer> result, Integer currentValue, int maxNum){
        if(currentValue != null && currentValue > maxNum){
            return;
        }

        if(currentValue != null) {
            result.add(currentValue);
        }

        for(int nextBit = 0; nextBit <= 9; nextBit++){
            if(currentValue == null ){
                if(nextBit == 0) {
                    continue;
                } else {
                    currentValue = 0;
                }
            }

            lexicalOrder(result,currentValue * 10 + nextBit, maxNum);
        }
    }

    //2.2 DFS深度遍历 10叉树方法
    public static List<Integer> lexicalOrderDFS(int n) {
        ArrayList<Integer> res = new ArrayList<>();
        //每个节点都遍历查找子节点
        for (int i = 1; i <= 9; i++) {
            DFS(res, i, n);
        }

        return res;
    }

    private static void DFS(List<Integer> list, int target, int n) {
        //check number
        if (target > n) {
            return;
        }

        //add the current number
        list.add(target);
        target = target * 10;

        //遍历子节点
        for (int i = 0; i <= 9; i++){
            DFS(list, target + i, n);
        }
    }

    //3. leetcode 440. 字典序的第K小数字
    //给定整数 n 和 k，找到 1 到 n 中字典序第 k 小的数字。
    //
    //注意：1 ≤ k ≤ n ≤ 109。
    //解题思路
    //获取以1开头和以2开头数值之间的 数值个数，和k值比较
    //如果大于k,说明在以1开头之下的某一层中，即可增加层数继续在下一层10…11之间的数值个数，缩短k的路径
    //如果小于k,那么需要更换子树，更改为以 2 开头的字典树
    //计算的数值个数需要在n的范围内，同时每当层数递增时需缩减k的数值，相当于重新找到了计算起点

    public static int findKthNumber(int n, int k){
        long  L = 1;
        long  R = 9;

        while(k > 0){
            for(long  i = L; i <= R; i++){
                // 获取[i,i+1]之间的字典数的个数，并和k进行比较
                int sum = getLexicalSum(i, n);
                //发现K大于上一个子树中字典树的数值个数，那么更换子树i
                if(sum < k){
                    k = k - sum;
                } else {
                    //否则，继续深度遍历搜索，缩减k的范围并进入到当前子树的下一层
                    k--;
                    if(k == 0){
                        return (int)i;
                    }

                    L = i*10 + 0;
                    R = i*10 + 9;
                    break;
                }
            }
        }

        return 0;
    }

    private static int getLexicalSum(long  t, int max) {
        int res =  1;
        long  left = t * 10 + 0;//t下一层的第一个数
        long  right = t * 10 + 9; // t下一层的第10个数

        while (left <= max){
            if(max <= right){
                //max 在[t, t+1]之间（子节点中）时，统计当前t的子节点
                res += (max - left + 1);
            } else {
                // max在t的子节点之外时，统计当前t的子节点
                res += (right - left + 1);
            }

            //继续递增层数 遍历子节点的子节点
            left = left * 10 + 0;
            right = right *10 + 9;
        }

        return res;
    }

    // 4. leetcode 169 多数元素
    public static int majorityElement(int[] nums) {
        if(nums == null){
            return -1;
        }

        // Map存储数组的频次
//        int target = nums[0];
//        int maxCount = 1;
//        HashMap<Integer, Integer> map = new HashMap<>();
//        for (Integer key: nums) {
//            if(!map.containsKey(key)){
//                map.put(key, 1);
//            } else {
//                map.put(key, map.get(key)+1);
//                if(map.get(key) + 1 > maxCount){
//                    target = key;
//                    maxCount = map.get(key) + 1;
//                }
//            }
//        }

        int target = nums[0];
        HashMap<Integer, Integer> map = new HashMap<>();
        for (Integer key: nums) {
            if(!map.containsKey(key)){
                map.put(key, 1);
            } else {
                map.put(key, map.get(key)+1);
                if(map.get(key) > nums.length /2){
                    target = key;
                }
            }
        }

        return target;
    }

    //5. leetcode 1027 最长等差数列
    //DP method
    public int longestArithSeqLength(int[] A) {
        //check condition
        if(A == null){
            return 0;
        }

        // A.length >= 3
        int[][] dp = new int[A.length][20001];
        //save the max length
        int res = 1;
        for(int i = 0; i < A.length; i++){
            for(int j = i + 1; j < A.length; j++){
                int v = A[j] - A[i] + 10000;
                //dp[j][v]存储以A[j]为等差数列最后一个节点，等差等于v的数列的最大长度
                dp[j][v] = Math.max(dp[i][v]+1, dp[j][v]);
                //[1,2,3,4,5]中，以[5]结尾的可能有[1,2,3,4,5],还有[1,3,5],所以取最大值
                res = Math.max(res, dp[j][v]);
            }
        }
        //每个元素自己也算一个
        return res + 1;
    }

    //Method 2: 知道最后一个往前找等差数
    public int longestArithSeqLengthII(int[] A) {
        //check condition
        if(A == null){
            return 0;
        }

        int[][] dp = new int[A.length][20001];
        int res = 0;
        for (int i = 1 ; i < A.length; i++) {
            for (int j = 0; j < i; j++) {
                int step = A[i] - A[j] + 10000;
                if (dp[j][step] > 0) {
                    dp[i][step] = Math.max(dp[j][step] + 1, dp[i][step]);
                } else {
                    dp[i][step] = 2;
                }
                res = Math.max(res, dp[i][step]);
            }
        }
        return res;
    }


    //6. leetcode 665. 非递减数列

    //一，当数组长度小于3时，最多需要调整一次就能满足条件
    //二，当数组长度大于等于3时，出现前一个元素y大于后一个元素z时，
    //如果y的前元素x不存在，让y=z即可；若x存在，会有以下情况
    // x    y   z
    // 1    3   2
    // 2    3   1
    // 3    3   1
    // 2    3   2
    // 要满足条件，需要如下调整：
    // 1，当x<z,让y=z
    // 2，当x>z,让z=y
    // 3, 当x=z,让y=z
    // 综合以上可以得到：当x存在且x>z，就让z=y，否则让y=z
    public boolean checkPossibility(int[] nums) {
        if(nums.length < 3){
            return true;
        }

        int count = 0;
        for(int i=0; i < nums.length-1; i++){
            if(nums[i] > nums[i+1]){
                count++;
                if(count > 1){
                    return false;
                }

                if(i > 0 && nums[i-1] > nums[i+1]){
                    nums[i+1] = nums[i];
                }
                // 遍历过的不会再遍历，优化之处
                // else {
                //    nums[i] = nums[i+1];
                // }
            }
        }

        return count <= 1;
    }

    //7. leetcode 1046. 最后一块石头的重量
    public int lastStoneWeight(int[] stones) {
        // check condition
        if(stones == null || stones.length < 0){
            return 0;
        }

        int len = stones.length;
        if(len == 1){
            return stones[0];
        } else if(len == 2) {
            return Math.abs(stones[0] - stones[1]);
        }
        //排序后每次计算最大的两个值
        Arrays.sort(stones);
        //递归循环结束检查
        if(stones[len - 3] == 0){
            return stones[len -1] -stones[len -2];
        }
        // 最大的两个值相减，差值赋给stones[len-1], stones[len-2]=0，
        stones[len-1] = stones[len-1] - stones[len-2];
        stones[len-2] = 0;

        return lastStoneWeight(stones);
    }


    //8. leetcode 1049. 最后一块石头的重量 II
    //0-1背包问题
    public int lastStoneWeightII(int[] stones){
        int len = stones.length;
        // 获取石头总的重量
        int sum = 0;
        for(int stone: stones){
            sum += stone;
        }

        int maxCapacity = sum/2;
        int[] dp = new int[maxCapacity + 1];
        for(int i = 0; i < len; i++){
            int curStone = stones[i];
            for(int j = maxCapacity; j >= curStone; j--){
                int a = dp[j];
                int b = dp[j-curStone] + curStone;
                dp[j] = Math.max(a, b);
                //dp[j] = Math.max(dp[j], dp[j-curStone] + curStone);
            }
        }

        int result = sum - 2 * dp[maxCapacity];
        return result;
    }


    // 9. leetcode 1160. 拼写单词
    public int countCharacters(String[] words, String chars) {
        //1. 存储chars中字母出现的次数
        int[] charArray = charCount(chars);

        //2. 检查
        int res = 0;
        for(String word : words){
            //2.1 统计单词的字母出现次数
            int[] wordArray = charCount(word);
            //2.2 检查字母表的字母出现的次数是否覆盖单词的字母出现的次数
            boolean flag = true;
            for(int i=0; i < word.length(); i++){
                char c = (char) (word.charAt(i) - 'a');
                if(wordArray[c] > charArray[c]){
                    flag = false;
                    break;
                }
            }

            //2.3 如果能够覆盖，这把单词长度加上
            if(flag){
                res += word.length();
            }
        }

        return res;
    }

    //统计存储chars中字母出现的次数
    public int[] charCount(String chars){
        int[] charArray = new int[26];
        for(char c : chars.toCharArray()){
            charArray[c-'a']++;
        }
        return charArray;
    }


    //10. leetcode 面试题 最小的K个数
    public int[] getLeastNumbers(int[] arr, int k) {
        //check condition
        if(arr == null || arr.length == 0
                || (arr.length == k) || (arr.length < k)){
            return arr;
        }

//        // method 1 -- sort
//        Arrays.sort(arr);
//        return Arrays.copyOfRange(arr, 0, k);

        // method 2 -- stream sort
        return Arrays.stream(arr).sorted().limit(k).toArray();
    }

    //11. leetcode 面试题 17.14. 最小K个数
    public int[] smallestK(int[] arr, int k) {
        //check condition
        if(arr == null || arr.length == 0
                || (arr.length == k) || (arr.length < k)){
            return arr;
        }

        Queue<Integer> queue = new PriorityQueue<>();
        for (int n : arr){
            queue.offer(n);
        }

        int count = 0;
        int[] res = new int[k];
        while(count < k){
            res[count++] = queue.poll();
        }

        return res;
    }

    // 12. leetcode 695. 岛屿的最大面积
    public int maxAreaOfIsland(int[][] grid) {
        int res = 0;
        for(int i=0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                if(grid[i][j] == 1){
                    res = Math.max(res, dfs(i,j, grid));
                }
            }
        }
        return res;
    }

    // 每次调用的时候默认num为1，进入后判断如果不是岛屿，则直接返回0，就可以避免预防错误的情况。
    // 每次找到岛屿，则直接把找到的岛屿改成0，这是传说中的沉岛思想，就是遇到岛屿就把他和周围的全部沉默。
    private int dfs(int i, int j, int[][] grid) {
        if (i < 0 || j < 0 || i >= grid.length
                || j >= grid[i].length || grid[i][j] == 0) {
            return 0;
        }
        grid[i][j] = 0;
        int num = 1;
        num += dfs(i + 1, j, grid);
        num += dfs(i - 1, j, grid);
        num += dfs(i, j + 1, grid);
        num += dfs(i, j - 1, grid);

        return num;
    }

    //13. leetcode  连续差相同的数字
    // 一个N位的数字可以看作N-1位数字加上最后一个数字。
    // 如果N-1位数字以数字d结尾，则N位数字将以d−K
    // 或d+K结尾（前提是这些数字在[0,9]范围内）
    public int[] numsSameConsecDiff(int N, int K){
        Set<Integer> set = new HashSet<>();
        for(int i = 1; i <= 9; i++){
            set.add(i);
        }

        for(int step = 1; step < N-1; step++){
            Set<Integer> set2 = new HashSet<>();
            for(int n : set){
                int d = n %10;
                if(d-K >= 0){
                    set2.add(10*n + (d-K));
                }

                if(d+K <= 9){
                   set2.add(10*n + (d+K));
                }
            }

            set = set2;
        }

        if(N == 1){
            set.add(0);
        }

        int[] res = new int[set.size()];
        int i = 0;
        for(int value: set){
            res[i++] = value;
        }

        return res;
    }

    //循环判断个位数加减 K 是否在 0 ～ 9 范围内，直到数字的位数达到 N。
    public int[] numsSameConsecDiffII(int N, int K){

        Set<Integer> set = new HashSet<>();
        for(int i = 1; i <= 9; i++){
            set.add(i);
        }

        //数字位数count
        int count = 1;
        while(count < N){
            // 保存临时数据
            Set<Integer> temp = new HashSet<>();
            // 循环添加最后一位数
            for (int n: set){
                int d = n % 10;
                if(d-K >= 0){
                    temp.add(10*n + (d-K));
                }

                if(d+K <= 9){
                    temp.add(10*n + (d+K));
                }
            }
            set = temp;
            count++;
        }

        if(N == 1){
            set.add(0);
        }

        int[] res = new int[set.size()];
        int i = 0;
        for(int value: set){
            res[i++] = value;
        }

        return res;
    }


    //14. leetcode 4. 寻找两个有序数组的中位数
    // 普通遍历找到中位数 Time -- O(m+n)
    public double findMedianSortedArrays(int[] A, int[] B) {
        int m = A.length;
        int n = B.length;
        int len = m + n;
        // 记录遍历的前一个数和后一个数
        int pre = -1, current = -1;
        // 遍历A、B数组的当前位置
        int aIndex = 0, bIndex = 0;
        // 遍历
        for (int i = 0; i <= len / 2; i++) {
            pre = current;
            if (aIndex < m
                    && (bIndex >= n || A[aIndex] < B[bIndex])) {
                current = A[aIndex++];
            } else {
                current = B[bIndex++];
            }
        }

        if ((len & 1) == 0) {
            return (pre + current) / 2.0;
        } else {
            return current;
        }
    }

    // Method2 recursion Time -- O(log(m+n)) -- 2分查找
    public double findMedianSortedArraysII(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        int left = (n+m+1)/2;
        int right = (n+m+2)/2;
        //将偶数和奇数的情况合并，如果是奇数，会求两次同样的 k 。
        return (getKth(nums1, 0, n - 1, nums2, 0, m - 1, left) + getKth(nums1, 0, n - 1, nums2, 0, m - 1, right)) * 0.5;

    }

    // 找第K小的数字
    private int getKth(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;
        //让 len1 的长度小于 len2，这样就能保证如果有数组空了，一定是 len1
        if (len1 > len2) return getKth(nums2, start2, end2, nums1, start1, end1, k);
        if (len1 == 0) return nums2[start2 + k - 1];

        if (k == 1) return Math.min(nums1[start1], nums2[start2]);

        int i = start1 + Math.min(len1, k / 2) - 1;
        int j = start2 + Math.min(len2, k / 2) - 1;

        // 每次循环排除掉 k/2 个数
        if (nums1[i] > nums2[j]) {
            return getKth(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));
        } else {
            return getKth(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));
        }
    }
  
  

    //===============================================
    public static final void main(String[] args){

        //System.out.println("The order:"+ lexicalOrderDFS(13));
        //System.out.println("The order:"+findKthNumber(13, 3));

        //int[] nums = {6,6,6,7,7};
        //System.out.println("The majorty element: "+ majorityElement(nums));


        //test case7
        int[] stones = {2,7,4,1,8,1};
        ArrayLeaf instance = new ArrayLeaf();
        //System.out.println("The lastStoneWeightII: "+ instance.lastStoneWeightII(stones));

        System.out.println("The lastStoneWeight: "+ instance.lastStoneWeight(stones));
    }
}
