package com.liu.redleaf;

import javafx.util.Pair;
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
