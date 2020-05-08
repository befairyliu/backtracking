package com.liu.redleaf;

import java.util.*;

public class DPLeaf {

    //1. leetcode 面试题 08.11. 硬币
    // DP 状态转移方程 dp[i][j]=dp[i-1][j]+dp[i][j-coins[i]]
    // dp[i][j] 为加入第i种硬币时，组成金额 j 的方法数目
    public int waysToChange(int n) {
        if (n < 5)
            return 1;
        if (n == 5)
            return 2;
        int[] coins = {1, 5, 10, 25};
        int[][] dp = new int[4][n + 1];
        // 当数量为0，1时，有1种表示法
        for (int i = 0; i < 4; ++i) {
            dp[i][0] = 1;
            dp[i][1] = 1;
        }
        // 当只有一种硬币时，只有1种表示法
        for (int i = 0; i <= n; ++i)
            dp[0][i] = 1;
        /*
         * 状态：dp[i][j]表示[0...i]种硬币能组合为j的所有不同种数
         * 状态转移：取 或 不取 当前硬币coins[i]
         */
        for (int i = 1; i < 4; ++i) { //硬币种类
            for (int j = 2; j <= n; ++j) { // 容量（总价值）
                if (j >= coins[i])
                    dp[i][j] = (dp[i][j - coins[i]] + dp[i - 1][j]) % 1000000007;
                else
                    dp[i][j] = dp[i - 1][j];
            }
        }
        //输出DP表看变化
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j <= n; j++) {
                System.out.print(dp[i][j] + " ");
            }
            System.out.println("");
        }

        return dp[3][n];
    }

    // 方法2：
    public int waysToChangeII(int n) {
        int[] dp = new int[n + 1];
        int[] coins = {1, 5, 10, 25};
        Arrays.fill(dp, 1);
        for (int c = 1; c < 4; c++) {
            for (int i = 1; i < n + 1; i++) {
                if (i >= coins[c]) {
                    dp[i] = (dp[i] + dp[i - coins[c]]) % 1000000007;
                }
            }
        }
        return dp[n];
    }

    //2. leetcode 72. 编辑距离
    // dp[i][j]表示word1的前i个字母转换成word2的前j个字母所使用的最少操作。
    // 若 A 和 B 的最后一个字母相同：
    // dp[i][j] = dp[i - 1][j - 1];
    // 若 A 和 B 的最后一个字母不同： 取（插入增加+删除+替换）三个操作的最小值+1
    // D[i][j] = 1 + min(D[i][j−1],D[i−1][j],D[i−1][j−1])
    public int minDistance(String word1, String word2) {

        int n = word1.length();
        int m = word2.length();

        // DP数组, 多开一行一列是为了保存边界条件，即字符长度为 0 的情况，这一点在字符串的动态规划问题中比较常见
        int[][] dp = new int[n + 1][m + 1];

        // 边界状态初始化
        // 初始化：当 word 2 长度为 0 时，将 word1 的全部删除
        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;
        }

        // 当 word1 长度为 0 时，就插入所有 word2 的字符
        for (int j = 0; j <= m; j++) {
            dp[0][j] = j;
        }

        // 计算所有dp的值
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {

                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // 1、插入一个字符
                    int insert = dp[i][j - 1] + 1;
                    // 2、替换一个字符
                    int replace = dp[i - 1][j - 1] + 1;
                    // 3、删除一个字符
                    int delete = dp[i - 1][j] + 1;
                    dp[i][j] = Math.min(Math.min(insert, replace), delete);

                    //dp[i][j] = 1 + Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i-1][j-1]);
                }
            }
        }

        return dp[n][m];
    }


    // 3. leetcode 322. 零钱兑换
    // 给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。
    // 如果没有任何一种硬币组合能组成总金额，返回 -1。
    // 输入: coins = [1, 2, 5], amount = 11
    // 输出: 3
    // 解释: 11 = 5 + 5 + 1
    // dp[i] = min(dp[i - coins[c1]], dp[i - coins[c2]]...dp[i - coins[cn]]) + 1;
    public int coinChange(int[] coins, int amount) {
        // dp[i]：总金额为i时的最小硬币个数
        int[] dp = new int[amount + 1];
        // 初始化数组，最大肯定是amount个，都为1
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) { //i金额数
            //j 表示第j种硬币
            for (int j = 0; j < coins.length; j++) {
                if (coins[j] <= i) { // 硬币面值小于总金额数时
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }

        return dp[amount] > amount ? - 1: dp[amount];
    }

    //4. leetcode 983. 最低票价
    // 输入：days = [1,4,6,7,8,20], costs = [2,7,15]
    // 输出：11
    // 输入：days = [1,2,3,4,5,6,7,8,9,10,30,31], costs = [2,7,15]
    // 输出：17
    // 提示：
    // 1 <= days.length <= 365
    // 1 <= days[i] <= 365
    // days 按顺序严格递增
    // costs.length == 3
    // 1 <= costs[i] <= 1000
    public int mincostTickets(int[] days, int[] costs) {
        // check input params
        if (days == null || days.length == 0
            || costs == null || costs.length == 0) {
            return 0;
        }

        // dp[i]表示第i天花费的最低票价
        int[] dp = new int[days[days.length - 1] + 1];

        // init
        dp[0] = 0;
        // 标记接下来需要买票的日子
        for (Integer day : days ) {
            dp[day] = Integer.MAX_VALUE;
        }

        // 通过转换方程计算dp
        for (int i = 1; i < dp.length; i++) {
            // 不需要买票时, 花费就是前一天的花费
            if (dp[i] == 0 ) {
                dp[i] = dp[i - 1];
                continue;
            }

            // 当天需要买票时
            // 一天
            int n1 = dp[i - 1] + costs[0];
            // 当前日子超过7天
            int n7 = i > 7 ? dp[i - 7] + costs[1] : costs[1];
            // 当前日子超过30天
            int n30 = i > 30 ? dp[i - 30] + costs[2] : costs[2];
            // 取三者最小值赋值给dp[i]
            dp[i] = Math.min(Math.min(n1, n7), n30);
        }

        return dp[dp.length - 1];
        //return dp[days[days.length - 1]];
    }

    
    // leetcode 221. 最大正方形
    // 在一个由 0 和 1 组成的二维矩阵内，找到只包含 1 的最大正方形，并返回其面积。
    public int maximalSquare(char[][] matrix) {
        int maxSide = 0;
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return maxSide;
        }
        int rows = matrix.length, columns = matrix[0].length;
        // dp[i][j]表示以matrix[i][j]为右下角正方形的最长边长
        int[][] dp = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j] == '1') {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                    }
                    maxSide = Math.max(maxSide, dp[i][j]);
                }
            }
        }
        int maxSquare = maxSide * maxSide;
        return maxSquare;
    }  

    
    public static final void main(String[] args) {

        DPLeaf instance = new DPLeaf();
        int n = 10;
        System.out.println("The res is: " + instance.waysToChange(n));

    }
}
