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
  
    
  //15. leetcode 面试题 17.16. 按摩师
    //动态规划
    public int massage(int[] nums){
        int len = nums.length;
        if(len == 0){
            return 0;
        }

        if(len == 1){
            return nums[0];
        }

        int[] dp = new int[len];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for(int i=2; i< len; i++){
            dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i]);
        }

        return dp[len-1];
    }

    //16. leetcode 213. 打家劫舍 II
    //动态规划
    public int rob(int[] nums) {
        if(nums.length == 0) return 0;
        if(nums.length == 1) return nums[0];
        if(nums.length == 2) return Math.max(nums[0], nums[1]);

        //换成2次单排列
        return Math.max(robHelper(Arrays.copyOfRange(nums, 0, nums.length - 1)),
                robHelper(Arrays.copyOfRange(nums, 1, nums.length)));
    }

    public static int robHelper(int[] nums){
        int len = nums.length;
        int[] dp = new int[len];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        for(int i=2; i< len; i++){
            //状态转移方程
            dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i]);
        }

        return dp[len-1];
    }

    //17. leetcode 892. 三维形体的表面积
    public int surfaceArea(int[][] grid) {
        //check params
        if(grid == null ){
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;
        // 立方体总个数
        int sum = 0;
        // 垂直重叠个数
        int verticalOverlap = 0;
        // 行重叠个数
        int rowOverlap = 0;
        // 列重叠个数
        int colOverlap = 0;

        // 遍历二维数组
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum += grid[i][j];

                if (grid[i][j] > 1) {
                    verticalOverlap += (grid[i][j] - 1);
                }

                if (j > 0) {
                    rowOverlap += Math.min(grid[i][j - 1], grid[i][j]);
                }

                if (i > 0) {
                    colOverlap += Math.min(grid[i - 1][j], grid[i][j]);
                }
            }
        }
        return sum * 6 - (verticalOverlap + rowOverlap + colOverlap) * 2;
    }

    // 18. leetcode 999. 车的可用捕获量
    public int numRookCaptures(char[][] board){
        //定义方向数组
        int[][] directions = {{-1,0}, {1,0},{0,1},{0,-1}};
        //遍历
        for(int i = 0; i < 8 ; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j] == 'R'){
                    int res = 0;
                    //上下左右四个方向遍历
                    for(int[] direction : directions){
                        if(burnout(board, i, j, direction)){
                            res++;
                        }
                    }
                    return res;
                }
            }
        }
        return 0;
    }

    public static boolean burnout(char[][] board, int x, int y, int[] direction){
        int i = x;
        int j = y;
        //判断是否在棋盘范围内
        while(inArea(i, j)){
            // 找到友军
            if(board[i][j] == 'B'){
                break;
            }
            // 找到敌军
            if(board[i][j] == 'p'){
                return true;
            }
            // 找不到的时候该方向继续往下找
            i += direction[0];
            j += direction[1];
        }

        return false;
    }

    public static boolean inArea(int i, int j){
        return i >= 0 && i < 8 && j >= 0 && j < 8;
    }

    //19. leetcode 面试题 08.10. 颜色填充
    public static int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        int x = image.length;
        int y = image[0].length;
        if((sr < 0 || sr >= x) || (sc < 0 || sc >= y)){
            return image;
        }

        int oldColor = image[sr][sc];
        floodDFS(image, sr, sc, oldColor, newColor);
        return image;
    }

    public static void floodDFS(int[][] image, int sr, int sc, int oldColor, int newColor){
        // 检查条件
        if(0 <= sr && sr < image.length
                && 0 <= sc && sc < image[0].length
                && image[sr][sc] == oldColor && oldColor != newColor){

            //将找到的val标注标注为newColor
            image[sr][sc] = newColor;
            // 上下左右深度搜索遍历
            floodDFS(image, sr, sc + 1, oldColor, newColor);
            floodDFS(image, sr - 1, sc, oldColor, newColor);
            floodDFS(image, sr + 1, sc, oldColor, newColor);
            floodDFS(image, sr, sc - 1, oldColor, newColor);
        }
    }

    //20. leetcode 820. 单词的压缩编码
    // 单词后缀法
    public int minimumLengthEncoding(String[] words){
        //check params
        if(words == null || words.length == 0){
            return 0;
        }

        Set<String> set = new HashSet<>();
        set.addAll(Arrays.asList(words));
        for(String word: words){
            for(int i = 1; i < word.length(); i++){
                set.remove(word.substring(i));
            }
        }

        int res = 0;
        for(String word : set){
            res = res + word.length() + 1;
        }

        return res;
    }

    // 字典树方法
    // 字典树的叶子节点（没有孩子的节点）就代表没有后缀的单词，统计叶子节点代表的单词长度加一的和即为我们要的答案。
    public int minimumLengthEncodingII(String[] words) {
        TrieNode trie = new TrieNode();
        Map<TrieNode, Integer> nodes = new HashMap();

        for (int i = 0; i < words.length; ++i) {
            String word = words[i];
            TrieNode cur = trie;
            for (int j = word.length() - 1; j >= 0; --j) {
                cur = cur.build(word.charAt(j));
            }
            nodes.put(cur, i);
        }

        int ans = 0;
        for (TrieNode node: nodes.keySet()) {
            if (node.count == 0) {
                ans += words[nodes.get(node)].length() + 1;
            }
        }
        return ans;

    }

    class TrieNode {
        TrieNode[] children;
        int count;

        TrieNode() {
            children = new TrieNode[26];
            count = 0;
        }

        public TrieNode build(char c) {
            if (children[c - 'a'] == null) {
                children[c - 'a'] = new TrieNode();
                count++;
            }
            return children[c - 'a'];
        }
    }

    // 21. leetcode 914. 卡牌分组
    public boolean hasGroupsSizeX(int[] deck){
        int len = deck.length;
        if(len < 2){
            return false;
        }

        // 计数数组, 每个数字出现的次数
        int[] cnt = new int[10000];
        for(int num: deck){
            cnt[num]++;
        }

        //init
        int x = cnt[deck[0]];

        for(int i = 0; i < 10000; i++){
            if(cnt[i] == 1){
                return false;
            }

            if(cnt[i] > 1){
                x = gcd(x, cnt[i]);
                //排除1
                if(x == 1){
                    return false;
                }
            }
        }

        return true;
    }

    private int gcd(int a, int b){
        if(b == 0){
            return a;
        }

        return gcd(b, a % b);
    }


    //22. leetcode 912. 排序数组, 升序排列
    public int[] sortArray(int[] nums) {
        return null;
    }

    // 23. leetcode 面试题57 - II. 和为s的连续正数序列
    public int[][] findContinuousSequence(int target) {
        List<int[]> result = new ArrayList<>();
        for(int i = 1; i <= target/2; i++){
            int sum = 0;
            int j = i;
            while(sum < target){
                sum += j;
                j++;
            }

            if(sum == target){
                int[] arr = new int[j - i];
                for(int k = 0; k < j - i; k++){
                    arr[k] = k + i;
                }

                result.add(arr);
            }
        }

        return result.toArray(new int[result.size()][]);
    }

    // 24. leetcode vpi -- 253 会议室II
    // 方法一：优先队列
    public int minMeetingRooms(int[][] intervals) {
        int length = intervals.length;
        Interval[] inters = new Interval[length];
        for(int i = 0; i < length; i++){
            inters[i] = new Interval(intervals[i][0], intervals[i][1]);
        }
        return roomsHelper(inters);
    }

    public static class Interval {
        int start;
        int end;

        Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    // 1.按照 开始时间 对会议进行排序。
    // 2.初始化一个新的 最小堆，将第一个会议的结束时间加入到堆中。我们只需要记录会议的结束时间，告诉我们什么时候房间会空。
    // 3.对每个会议，检查堆的最小元素（即堆顶部的房间）是否空闲。
    //  3.1若房间空闲，则从堆顶拿出该元素，将其改为我们处理的会议的结束时间，加回到堆中。
    //  3.2若房间不空闲。开新房间，并加入到堆中。
    // 4.处理完所有会议后，堆的大小即为开的房间数量。这就是容纳这些会议需要的最小房间数。

    public int roomsHelper(Interval[] intervals) {
        // Check for the base case. If there are no intervals, return 0
        if (intervals.length == 0) {
            return 0;
        }

        // Min heap
        PriorityQueue<Integer> allocator =
                new PriorityQueue<Integer>(
                        intervals.length,
                        new Comparator<Integer>() {
                            public int compare(Integer a, Integer b) {
                                return a - b;
                            }
                        });

        // Sort the intervals by start time
        Arrays.sort(intervals, new Comparator<Interval>(){
            @Override
            public int compare(Interval o1, Interval o2) {
                return o1.start - o2.start;
            }
        });

        // Add the first meeting
        allocator.add(intervals[0].end);

        // Iterate over remaining intervals
        for (int i = 1; i < intervals.length; i++) {
            // If the room due to free up the earliest is free, assign that room to this meeting.
            if (intervals[i].start >= allocator.peek()) {
                allocator.poll();
            }

            // If a new room is to be assigned, then also we add to the heap,
            // If an old room is allocated, then also we have to add to the heap with updated end time.
            allocator.add(intervals[i].end);
        }

        // The size of the heap tells us the minimum rooms required for all the meetings.
        return allocator.size();
    }

    // 方法二：有序化
    // 1.分别将开始时间和结束时间存进两个数组。
    // 2.分别对开始时间和结束时间进行排序。请注意，这将打乱开始时间和结束时间的原始对应关系。它们将被分别处理。
    // 3.考虑两个指针： s_ptr 和 e_ptr，分别代表开始指针和结束指针。开始指针遍历每个会议，结束指针帮助我们跟踪会议是否结束。
    // 4.当考虑 s_ptr 指向的特定会议时，检查该开始时间是否大于 e_ptr 指向的会议。若如此，则说明 s_ptr 开始时，已经有会议结束。于是我们可以重用房间。否则，我们就需要开新房间。
    // 5.若有会议结束，换而言之， start[s_ptr] >= end[e_ptr]，则自增 e_ptr。
    // 6.重复这一过程，直到 s_ptr 处理完所有会议
    public int roomsHelperII(Interval[] intervals) {

        // Check for the base case. If there are no intervals, return 0
        if (intervals.length == 0) {
            return 0;
        }

        Integer[] start = new Integer[intervals.length];
        Integer[] end = new Integer[intervals.length];

        for (int i = 0; i < intervals.length; i++) {
            start[i] = intervals[i].start;
            end[i] = intervals[i].end;
        }

        // Sort the intervals by end time
        Arrays.sort( end, new Comparator<Integer>() {
                    public int compare(Integer a, Integer b) {
                        return a - b;
                    }
                });

        // Sort the intervals by start time
        Arrays.sort( start, new Comparator<Integer>() {
                    public int compare(Integer a, Integer b) {
                        return a - b;
                    }
                });

        // The two pointers in the algorithm: e_ptr and s_ptr.
        int startPointer = 0, endPointer = 0;

        // Variables to keep track of maximum number of rooms used.
        int usedRooms = 0;

        // Iterate over intervals.
        while (startPointer < intervals.length) {

            // If there is a meeting that has ended by the time the meeting at `start_pointer` starts
            if (start[startPointer] >= end[endPointer]) {
                usedRooms -= 1;
                endPointer += 1;
            }

            // We do this irrespective of whether a room frees up or not.
            // If a room got free, then this used_rooms += 1 wouldn't have any effect. used_rooms would
            // remain the same in that case. If no room was free, then this would increase used_rooms
            usedRooms += 1;
            startPointer += 1;
        }

        return usedRooms;
    }


    // 25. leetcode 289. 生命游戏
    // 方法一：复制原数组进行模拟，
    // 时间复杂度：O(mn)，
    // 空间复杂度：O(mn)   为复制数组占用的空间
    public void gameOfLife(int[][] board) {
        //相邻细胞的坐标位置向量
        int[] neighbors = {-1, 0, 1};
        int rows = board.length;
        int cols = board[0].length;

        //build copy board
        int[][] copyBoard = new int[rows][cols];
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                copyBoard[row][col] = board[row][col];
            }
        }

        //iterate the cell of board
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                //对于每个细胞统计其八个相邻位置里的活细胞数量
                int liveNeighbors = 0;
                for(int i = 0; i < 3; i++){
                    for(int j= 0; j < 3; j++){
                        // neighbors[i] == 0 && neighbors[j] == 0的时候i == 0 && j == 0
                        // 也就是细胞自己的位置向量
                        if(!(neighbors[i] == 0 && neighbors[j] == 0)){
                            int r = (row + neighbors[i]);
                            int c = (col + neighbors[j]);

                            // 查看相邻的细胞是否是活细胞
                            if((r < rows && r >= 0) && (c < cols && c >= 0)
                                    && (copyBoard[r][c] == 1)){
                                liveNeighbors += 1;
                            }
                        }
                    }
                }

                // 规则1 或 规则2
                if((copyBoard[row][col] == 1) && (liveNeighbors < 2 || liveNeighbors > 3)){
                    board[row][col] = 0;
                }
                //规则4
                if(copyBoard[row][col] == 0 && liveNeighbors == 3){
                    board[row][col] = 1;
                }
            }
        }
    }

    //方法二：使用额外的状态
    // 原数组细胞状态使用额外的状态存储
    // 2： 0 -> 1 死细胞变成活细胞
    // -1: 1 -> 0 活细胞变成死细胞
    public void gameOfLifeII(int[][] board) {
        //相邻细胞的坐标位置向量
        int[] neighbors = {-1, 0, 1};

        int rows = board.length;
        int cols = board[0].length;

        // 遍历面板每一个格子里的细胞
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                // 对于每一个细胞统计其八个相邻位置里的活细胞数量
                int liveNeighbors = 0;

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {

                        if (!(neighbors[i] == 0 && neighbors[j] == 0)) {
                            // 相邻位置的坐标
                            int r = (row + neighbors[i]);
                            int c = (col + neighbors[j]);

                            // 查看相邻的细胞是否是活细胞
                            if ((r < rows && r >= 0) && (c < cols && c >= 0) && (Math.abs(board[r][c]) == 1)) {
                                liveNeighbors += 1;
                            }
                        }
                    }
                }

                // 规则 1 或规则 3
                if ((board[row][col] == 1) && (liveNeighbors < 2 || liveNeighbors > 3)) {
                    // -1 代表这个细胞过去是活的现在死了
                    board[row][col] = -1;
                }
                // 规则 4
                if (board[row][col] == 0 && liveNeighbors == 3) {
                    // 2 代表这个细胞过去是死的现在活了
                    board[row][col] = 2;
                }
            }
        }

        // 遍历 board 得到一次更新后的状态
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col] > 0) {
                    board[row][col] = 1;
                } else {
                    board[row][col] = 0;
                }
            }
        }
    }

     //26. leetcode 面试题 01.07. 旋转矩阵
    // 方法一：原地旋转
    // matrix[col][n−row−1] = matrix[row][col]
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n / 2; ++i) {
            for (int j = 0; j < (n + 1) / 2; ++j) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - j - 1][i];
                matrix[n - j - 1][i] = matrix[n - i - 1][n - j - 1];
                matrix[n - i - 1][n - j - 1] = matrix[j][n - i - 1];
                matrix[j][n - i - 1] = temp;
            }
        }
    }


    // 方法二：用翻转代替旋转
    // 水平翻转 + 对角线翻转
    // 水平翻转： matrix[row][col] → matrix[n−row−1][col]
    // 对角线翻转：matrix[row][col] → matrix[col][row]
    public void rotateII(int[][] matrix) {
        int n = matrix.length;
        // 水平翻转
        for (int i = 0; i < n/2; i++) {
            for (int j = 0; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - i - 1][j];
                matrix[n - i - 1][j] = temp;
            }
        }

        //主对角线翻转
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }

    // 27. leetcode 56 合并区间
    // 给出一个区间的集合，请合并所有重叠的区间。
    // 输入: [[1,3],[2,6],[8,10],[15,18]]
    // 输出: [[1,6],[8,10],[15,18]]
    public int[][] merge(int[][] intervals) {
        int length = intervals.length;
        if (length <= 1) {
            return intervals;
        }

        // sort the intervals
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });

        List<int[]> list = new ArrayList<>();
        int currentStart = intervals[0][0];
        int currentEnd = intervals[0][1];

        for (int i = 1; i < length; i++) {
            int newStart = intervals[i][0];
            int newEnd = intervals[i][1];

            if(currentEnd >= newStart){
                if (currentEnd < newEnd) currentEnd = newEnd;
            } else {
                list.add(new int[] {currentStart, currentEnd});
                currentStart = newStart;
                currentEnd = newEnd;
            }
        }

        // 最后一个
        list.add(new int[] {currentStart, currentEnd});

        return list.toArray(new int[list.size()][]);

//        int[][] results = new int[list.size()][2];
//        for (int j = 0; j < list.size(); j++) {
//            results[j] = list.get(j);
//        }
//
//        return results;
    }
  
    // 28. leetcode 542. 01 矩阵
    // 给定一个由 0 和 1 组成的矩阵，找出每个元素到最近的 0 的距离。两个相邻元素间的距离为 1 。
    // 输入:
    //
    //0 0 0
    //0 1 0
    //1 1 1
    //输出:
    //
    //0 0 0
    //0 1 0
    //1 2 1
    //
    // 注意：
    // 给定矩阵的元素个数不超过 10000。
    // 给定矩阵中至少有一个元素是 0。
    // 矩阵中的元素只在四个方向上相邻: 上、下、左、右。
    public int[][] updateMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        //build copy board
        int[][] copyMatrix = new int[rows][cols];;
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                copyMatrix[row][col] = matrix[row][col];
            }
        }

        Deque<int[]> queue = new LinkedList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // BFS 上下左右查找
                queue.offer(new int[] {i, j});
                matrix[i][j] = distanceBFS(copyMatrix, queue);
                queue.clear();
            }
        }

        return matrix;
    }

    private int distanceBFS (int[][] copyMatrix, Deque<int[]> queue) {
        // init
        int rows = copyMatrix.length;
        int cols = copyMatrix[0].length;
        // 上下左右
        int[] dx = new int[] {0, 0, -1, 1 };
        int[] dy = new int[] {-1, 1, 0, 0 };

        int dis = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++){
                int[] dot = queue.pop();
                int X = dot[0];
                int Y = dot[1];
                if (copyMatrix[X][Y] == 0) {
                    queue.clear();
                    return dis;
                } else {
                    // 该位置还有周边元素时，添加到队列中
                    for (int k = 0; k < 4; k++) {
                        int x = X + dx[k];
                        int y = Y + dy[k];
                        // 边界检查
                        if (0 <= x && x < rows && 0 <= y && y < cols) {
                            queue.offer(new int[] {x, y});
                        }
                    }
                }

                // 这一轮循环完毕，未找到0距离+1，继续循环
                if (i == size - 1) {
                    dis++;
                }
            }
        }

        return dis;
    }

    // 方法2：BFS方法优化
    public int[][] updateMatrixII(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        // 找到所有值为0的点
        Queue<int[]> queue = new LinkedList<>();
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                if (matrix[row][col] == 0) {
                    queue.offer(new int[] {row, col});
                } else {
                    matrix[row][col] = -1;
                }
            }
        }
        // 把所有非0的部分根据距离更新一遍
        // 上下左右
        int[] dx = new int[] {0, 0, -1, 1 };
        int[] dy = new int[] {-1, 1, 0, 0 };
        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            int x = point[0];
            int y = point[1];
            for (int i = 0; i < 4; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];
                if (0 <= newX && newX < rows
                        && 0 <= newY && newY < cols
                        && matrix[newX][newY] == -1) {
                    matrix[newX][newY] = matrix[x][y] + 1;
                    queue.offer(new int[] {newX, newY});
                }
            }
        }
        return matrix;
    }

    // 29. leetcode 55. 跳跃游戏
    // 给定一个非负整数数组，你最初位于数组的第一个位置。
    // 数组中的每个元素代表你在该位置可以跳跃的最大长度。
    // 判断你是否能够到达最后一个位置。
    // 输入: [2,3,1,1,4]
    // 输出: true
    // 输入: [3,2,1,0,4]
    // 输出: false
    public boolean canJump(int[] nums) {
        // check params
        if (nums.length <= 1) {
            return true;
        }

        // 最远能到达的位置
        int maxIndex = nums[0];
        for (int i = 1; i < nums.length; i++) {
            // 最大步数已经到不了当前位置了
            if (i > maxIndex) {
                return false;
            }
            maxIndex = Math.max(maxIndex, i + nums[i]);
        }
        // 最后是否可以到达末尾位置
        return true;
    }

    // 30. leetcode 11. 盛最多水的容器
    // 给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。
    // 在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。
    // 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
    //
    // 说明：你不能倾斜容器，且 n 的值至少为 2。
    //
    // 方法1: 暴力遍历计算
    public int maxArea(int[] height) {
        int len = height.length;
        if (len < 2) return 0;
        int max = 0;
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                int currentVolume = (j - i) * Math.min(height[i], height[j]);
                max = Math.max(max, currentVolume);
            }
        }

        return max;
    }

    // 方法2: 双指针法
    public int maxAreaII(int[] height) {
        int len = height.length;
        if (len < 2) return 0;
        int max = 0; // max volume
        int left = 0, right = len - 1;
        while (left < right) {
            int currentVolume = (right - left) * Math.min(height[left], height[right]);
            max = Math.max(max, currentVolume);
            // 指针移动
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return max;
    }

    // 31. leetcode 347. 前 K 个高频元素
    // 给定一个非空的整数数组，返回其中出现频率前 k 高的元素。
    // 输入: nums = [1,1,1,2,2,3], k = 2
    // 输出: [1,2]
    // 你可以假设给定的 k 总是合理的，且 1 ≤ k ≤ 数组中不相同的元素的个数。
    // 你的算法的时间复杂度必须优于 O(n log n) , n 是数组的大小。
    public List<Integer> topKFrequent(int[] nums, int k) {

        Map<Integer, Integer> map = new HashMap();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                map.put(nums[i], map.get(nums[i]) + 1);
            } else {
                map.put(nums[i], 1);
            }
        }

        Node[] nodes = new Node[map.size()];
        Set<Integer> set = map.keySet();
        Iterator<Integer> it = set.iterator();
        int j = 0;
        while (it.hasNext()) {
            int key = it.next();
            int value = map.get(key);
            nodes[j++] = new Node(key, map.get(value));
        }

        Arrays.sort(nodes, new Comparator<Node>(){
            @Override
            public int compare(Node o1, Node o2) {
                return o2.frequent - o1.frequent;
            }
        });

        List<Integer> list = new LinkedList<>();
        for (int idx = 0; idx < k; idx++) {
            list.add(nodes[idx].value);
        }

        return list;
    }

    public static class Node {
        private int value;
        private int frequent;
        public Node (int value, int frequent) {
            this.value = value;
            this.frequent = frequent;
        }
    }

    // 方法2： 最大堆方法
    public List<Integer> topKFrequentII(int[] nums, int k) {
        // check input params
        if (nums == null || nums.length <= 0) return null;

        Map<Integer, Integer> map = new HashMap();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }

        // 最小顶堆
        PriorityQueue<Integer> heap = new PriorityQueue<>((o1, o2) -> map.get(o1) - map.get(o2));

        for (int key: map.keySet()) {
            heap.offer(key);
            if (heap.size() > k) {
                // 移除顶部小的值
                heap.poll();
            }
        }

        List<Integer> list = new LinkedList<>();
        while (!heap.isEmpty()) {
            list.add(heap.poll());
        }
        Collections.reverse(list);

        return list;
    }

    // 方法3: 桶排序
    public List<Integer> topKFrequentIII(int[] nums, int k) {
        List<Integer> ans = new ArrayList<>();
        if (nums == null) {
            return ans;
        }
        // 记录每个元素的频率
        Map<Integer,Integer> map = new HashMap<>();
        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
        // 按照 map 中元素的频率来创建数组，高频率的元素位于数组最后边
        List<Integer>[] tmp = new List[nums.length + 1];
        for (int key : map.keySet()) {
            // 定义 i 来接收每个元素的频率值
            int i = map.get(key);
            if (tmp[i] == null) {
                tmp[i] = new ArrayList();
            }
            // 将对应频率的元素放入以频率为下标的数组中
            tmp[i].add(key);
        }
        // 逆向找出前 k 高频率的元素
        for (int i = tmp.length - 1; i >= 0 && ans.size() < k; i--) {
            if (tmp[i] == null) {
                continue;
            }
            // 将当前频率下的元素放入结果集 ans 中
            ans.addAll(tmp[i]);
        }
        return ans;
    }

    // 32. leetcode 215. 数组中的第K个最大元素
    // 在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，
    // 而不是第 k 个不同的元素。你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。
    // 输入: [3,2,1,5,6,4] 和 k = 2
    // 输出: 5
    // 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
    // 输出: 4
    public int findKthLargest(int[] nums, int k) {
        //check the input params
        if (nums == null || nums.length <= 0) return 0;

        // 最小顶堆方法
//        PriorityQueue<Integer> heap = new PriorityQueue<>(k);
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int i = 0; i < nums.length; i++) {
//            if (heap.size() < k) {
//                heap.offer(nums[i]);
//            } else {
//                if (heap.peek() < nums[i]) {
//                    heap.poll();
//                    heap.offer(nums[i]);
//                }
//            }

            heap.offer(nums[i]);
            if (heap.size() > k) {
                heap.poll();
            }
        }

        // 返回堆顶元素（第K最大元素）
        return heap.poll();
    }
  
    // 33. leetcode 200.岛屿数量
    // 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
    //岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
    //此外，你可以假设该网格的四条边均被水包围。
    //示例 1:
    //
    //输入:
    //11110
    //11010
    //11000
    //00000
    //输出: 1
    // 方法1：DFS 深度优先搜索的次数
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;
        int numIslands = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1') {
                    numIslands++;
                    islandDFS(grid, i, j);
                }
            }
        }

        return numIslands;
    }

    public void islandDFS(char[][] grid, int row, int col) {
        int rows = grid.length;
        int cols = grid[0].length;

        if (row < 0 || row >= rows || col < 0 || col >= cols || grid[row][col] == '0') {
            return;
        }

        grid[row][col] = '0';
        islandDFS(grid, row - 1, col);
        islandDFS(grid, row + 1, col);
        islandDFS(grid, row, col - 1);
        islandDFS(grid, row, col + 1);
    }


    // 方法2： BFS广度优先搜索的次数
    public int numIslandsII(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;
        int numIslands = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1') {
                    numIslands++;
                    grid[i][j] = '0';
                    Queue<Integer> neighbors = new LinkedList<>();
                    neighbors.add(i * cols + j);
                    while (!neighbors.isEmpty()){
                        int id = neighbors.poll();
                        int row = id / cols;
                        int col = id % cols;
                        if (row - 1 >=0 && grid[row - 1][col] == '1') {
                            neighbors.add((row - 1) * cols + col);
                            grid[row - 1][col] = '0';
                        }
                        if (row + 1 < cols && grid[row+1][col] == '1') {
                            neighbors.add((row+1) * cols + col);
                            grid[row+1][col] = '0';
                        }
                        if (col - 1 >= 0 && grid[row][col-1] == '1') {
                            neighbors.add(row * cols + col-1);
                            grid[row][col-1] = '0';
                        }
                        if (col + 1 < cols && grid[row][col+1] == '1') {
                            neighbors.add(row * cols + col+1);
                            grid[row][col+1] = '0';
                        }
                    }
                }
            }
        }

        return numIslands;
    }

    // 34. leetcode 1248. 统计「优美子数组」
    // 给你一个整数数组 nums 和一个整数 k。
    // 如果某个 连续 子数组中恰好有 k 个奇数数字，我们就认为这个子数组是「优美子数组」。
    // 请返回这个数组中「优美子数组」的数目。
    // 输入：nums = [1,1,2,1,1], k = 3
    // 输出：2
    // 解释：包含 3 个奇数的子数组是 [1,1,2,1] 和 [1,2,1,1] 。

    public int numberOfSubarrays(int[] nums, int k) {
        int n = nums.length;
        int[] odd = new int[n + 2];
        int ans = 0, cnt = 0;
        for (int i = 0; i < n; ++i) {
            if (nums[i] % 2 == 1) {
                odd[++cnt] = i;
            }
        }
        //init
        odd[0] = -1;
        odd[++cnt] = n;
        for (int i = 1; i + k <= cnt; ++i) {
            ans += (odd[i] - odd[i - 1]) * (odd[i + k] - odd[i + k - 1]);
        }
        return ans;
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
