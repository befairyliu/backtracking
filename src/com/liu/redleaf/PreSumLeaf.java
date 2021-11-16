package com.liu.redleaf;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class PreSumLeaf {

    // 前缀和 + hash
    public int subarraySum(int[] nums, int k) {
        int len = nums.length;
        int count = 0;
        int[] preSums = new int[len + 1];
        preSums[0] = 0;
        for (int i = 0; i < len; i++) {
            preSums[i + 1] = preSums[i] + nums[i];
        }

        for (int left = 0; left < len; left++) {
            for (int right = left + 1; right < len + 1; right++) {
                int sum = preSums[right] - preSums[left];
                if (sum == k) {
                    count++;
                }
            }
        }

        return count;
    }


    public int subarraySum2(int[] nums, int k) {
        // 前缀数组
        int[] befores = new int[nums.length + 1];
        befores[0] = 0;
        for (int i = 1; i < befores.length; i++) {
            befores[i] = nums[i - 1] + befores[i - 1];
        }
        // 遍历统计次数
        int count = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        for (int i = 1; i < befores.length; i++) {
            int temp = befores[i] - k;
            if (map.containsKey(temp)) {
                count += map.get(temp);
            }
            map.put(befores[i], map.getOrDefault(befores[i], 0) + 1);
        }
        return count;
    }

    // leetcode 523 连续的子数组和
    public boolean checkSubarraySum(int[] nums, int k) {
        // check input params
        if (nums == null || nums.length < 2) {
            return false;
        }

        // key: 前缀和 mod k的值， value: nums数组的index
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);
        int preSumModK = 0;
        for (int i = 1; i <= nums.length; i++) {
            preSumModK += nums[i - 1];
            if (k != 0) {
                preSumModK = preSumModK % k;
            }

            if (!map.containsKey(preSumModK)) {
                // 第一次加入key 和 i
                map.put(preSumModK, i);
            } else {
                // 之前已经有该key, value的值始终是第一次出现key的index位置
                int index = map.get(preSumModK);
                if (i - index >= 2) {
                    return true;
                }
            }
        }
        return false;
    }

    // 560. 和为 K 的子数组
    public int subarraySum3(int[] nums, int k) {
        int count = 0;
        int preSum = 0;
        // key: 前缀和， value: 前缀和为key的次数
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        for (int num : nums) {
            preSum += num;
            if (map.containsKey(preSum - k)) {
                count = count + map.get(preSum - k);
            }
            map.put(preSum, map.getOrDefault(preSum, 0) + 1);
        }
        return count;
    }

    // 525. 连续数组
    public int findMaxLength(int[] nums) {
        int res = 0;
        int preSum = 0;
        // 1: 1, 0: -1
        Map<Integer, Integer> map = new HashMap<>();
        // key: preSum, value: index（key为index的前缀和）
        map.put(0, 0);
        for (int i = 1; i <= nums.length; i++) {
            if (nums[i - 1] == 0) {
                // 为方便计算: 0 as -1
                preSum += -1;
            }
            preSum += nums[i - 1];

            if (!map.containsKey(preSum)) {
                // 第一次存储
                map.put(preSum, i);
            } else {
                // 之前已经存储过，现在拿出来取范围最大值
                int index = map.get(preSum);
                res = Math.max(res, i - index);
            }
        }

        return res;
    }

    // 1124. 表现良好的最长时间段
    // 输入：hours = [9,9,6,0,6,6,9]
    // 输出：3
    // 解释：最长的表现良好时间段是 [9,9,6]。
    public int longestWPI(int[] hours) {
        int len = hours.length;
        // 大于8小时记1，小于等于8小时记-1
        int[] scores = new int[len];
        for (int i = 0; i < len; i++) {
            if (hours[i] > 8) {
                scores[i] = 1;
            } else {
                scores[i] = -1;
            }
        }

        // 前缀和
        int[] preSum = new int[len + 1];
        preSum[0] = 0;
        for (int j = 1; j <= len; j++) {
            preSum[j] = preSum[j - 1] + scores[j - 1];
        }

        // // 找一个最长的区间 能使 score的区间元素和 > 0
        // 寻找最长的区间 使得preSum[j] - preSum[i] > 0" ，
        // 不等式移项 变为 "寻找满足preSum[j] > preSum[i] 的最长区间" (其中j > i)，
        // 这句话正是"单调递增"的描述形式，相当于一个"最长上坡"问题；
        int res = 0;
        // 单调递减栈
        // 存储index
        LinkedList<Integer> stack = new LinkedList<>();
        stack.push(0);
        for (int i = 1; i <= len; i++) {
            if (stack.isEmpty() ||  preSum[i] < preSum[stack.peek()]) {
                System.out.println("stack push i: " + i);
                stack.push(i); // //栈中索引指向的元素严格单调递减
            }
        }

        for (int i = len; i >= 0; i--) {
            while (!stack.isEmpty() && preSum[i] > preSum[stack.peek()]) {
                // 说明栈顶索引到i位置的和是大于0的，是表现良好的时间段
                // 与栈顶索引指向元素比较，如果相减结果大于 0，则一直出栈，直到不大于 0 为止，然后更新当前最大宽度
                int index = stack.pop();
                res = Math.max(res, i - index);
                System.out.println("The res: " + res + ", i:" + i + ", index: " + index);
            }
        }

        return res;
    }


    // 528. 按权重随机选择
    public int pickIndex(int[] w) {
        initW(w);

        return 0;
    }

    private void initW(int[] w) {

    }

    // 304. 二维区域和检索 - 矩阵不可变
//    public NumMatrix(int[][] matrix) {
//
//    }

    int[][] presums; //二维数组前缀和
    public void initMatrix (int[][] matrix) {
        int row = matrix.length;
        if (row == 0) {
            return;
        }

        int col = matrix[0].length;
        presums = new int[row + 1][col + 1];
        // 第一行和第一列默认为0参与计算，就不需要单独初始化第一行和第一列了
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                presums[i + 1][j + 1] = matrix[i][j] + presums[i][j + 1] + presums[i + 1][j] - presums[i][j];
            }
        }

//        for (int i = 0; i <= row; i++) {
//            for (int j = 0; j <= col; j++) {
//                System.out.print(" " + presums[i][j]);
//            }
//            System.out.println();
//        }

        System.out.println("The presum is completed:");
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return presums[row2 + 1][col2 + 1] - presums[row2 + 1][col1] - presums[row1][col2 + 1] + presums[row1][col1];
    }


    // 303. 区域和检索 - 数组不可变
//    public NumArray(int[] nums) {
//
//    }

    int[] preNumSum;
    public void initNum (int[] nums) {
        if (nums.length <= 0) {
            return;
        }

        int len = nums.length;
        preNumSum = new int[len + 1];
        for (int i = 0; i < len; i++) {
            preNumSum[i + 1] = preNumSum[i] + nums[i];
        }
    }

    public int sumRange(int left, int right) {
        return preNumSum[right + 1] - preNumSum[left];
    }


    // vip 370 区间加法（差分数组思想）
    // 差分数组:
    // 1. 原始差分数组: d[i] = arr[i] - arr[i-1]
    // 2. 区间修改时，修改差分数组，d[start] =+ inc, d[end + 1] -= inc
    // 3. 最后计算 arr[i] = arr[i-1] + d[i]  （最后类似于前缀和的算法）
    private int[] getModifiedArray (int length, int[][] updates) {
        int[] res = new int[length];
        for (int[] update : updates) {
            int start = update[0];
            int end = update[1];
            int inc = update[2];

            res[start] += inc;
            if (end + 1 < length) {
                res[end + 1] -= inc;
            }
        }

        for (int i = 1; i < length; i++) {
            res[i] += res[i - 1];
        }

        return res;
    }


    // 1109
    // 这里有 n 个航班，它们分别从 1 到 n 进行编号。
    //
    //有一份航班预订表 bookings，表中第 i 条预订记录 bookings[i] = [firsti, lasti, seatsi] 意味着在从 firsti 到 lasti （包含 firsti 和 lasti ）的 每个航班 上预订了 seatsi 个座位。
    //
    // 请你返回一个长度为 n 的数组 answer，里面的元素是每个航班预定的座位总数。
    //
    // 输入：bookings = [[1,2,10],[2,3,20],[2,5,25]], n = 5
    // 输出：[10,55,45,25,25]
    // 航班编号        1   2   3   4   5
    // 预订记录 1 ：   10  10
    // 预订记录 2 ：       20  20
    // 预订记录 3 ：       25  25  25  25
    // 总座位数：      10  55  45  25  25
    // 因此，answer = [10,55,45,25,25]
    //
    // 差分数组算法思想:
    // 1. 求原始差分数组: d[i] = arr[i] - arr[i-1]
    // 2. 区间修改时，修改差分数组，d[start] =+ inc, d[end + 1] -= inc
    // 3. 最后计算 arr[i] = arr[i-1] + d[i]  （最后类似于前缀和的算法）
    public int[] corpFlightBookings(int[][] bookings, int n) {
        // 差分算法公式: arr[i] = arr[i-1] + d[i],
        int[] arr = new int[n];
        // 1. 差分数组, 由于 arr 数组初始值都为 0 ，故优化时可以直接让 arr 数组当差分数组 d 操作
        // 由于 n 个航班数组 arr 的默认值都为 0，故差分数组 d 默认值都为 0
        int[] d = new int[n];
        // 2. 根据题意修改 差分数组 d
        for (int[] booking: bookings) {
            int first = booking[0] - 1;
            int last = booking[1] - 1;
            int seat = booking[2];

            d[first] += seat;
            if (last + 1 < n) {
                d[last + 1] -= seat;
            }
        }

        // 3. 最后计算结果 arr[i] = arr[i-1] + d[i]
        // arr[0] = d[0] + arr[0 - 1] (我们定义arr[0]的前一个数为0)
        arr[0] = d[0];
        for (int i = 1; i < n; i++) {
            arr[i] = d[i] + arr[i - 1];
        }

        return arr;
    }

    // [[2,1,5],[3,5,7]]
    //3
    // false -> true
    public boolean carPooling (int[][] trips, int capacity) {
        int[] path = new int[1001];
        for (int[] trip : trips) {
            int persons = trip[0];
            int start = trip[1];
            int end = trip[2];

            path[start] += persons;
            if (end < path.length) {
                path[end] -= persons;
            }
        }

        // check path[0]
        if (path[0] > capacity) {
            return false;
        }

        for (int i = 1; i < path.length; i++) {
            path[i] += path[i - 1];
            if (path[i] > capacity) {
                return false;
            }
        }

        return true;
    }

    // VIP 548
    // 0 < i, i + 1 < j, j + 1 < k < n - 1
    // (0, i - 1)，(i + 1, j - 1)，(j + 1, k - 1)，(k + 1, n - 1) 的和应该相等
    // 算法思想：
    // preSum(0, i-1) + preSum(i+1, j-1) = preSum(j+1, k-1) + preSum(k+1, n-1)
    // => preSum(0, j-1) - nums[i] = preSum(j+1, n-1) - num[k]
    // => preSum(0, j-1) - preSum(j+1, n-1) = nums[i] - nums[k]
    // => |preSum(0, j-1) - preSum(j+1, n-1)| = |nums[i] - nums[k]| <= max - min
    public boolean splitArray(int[] nums) {
        if (nums.length < 7) {
            return false;
        }

        // 1. 前缀和数组(算上自己): preSum[i] = nums[i] + preSum[i-1]
        int[] preSum = new int[nums.length];
        preSum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            preSum[i] = nums[i] + preSum[i-1];
        }

        // 获取数组的最大值和最小值，方便剪枝
        int max = Arrays.stream(nums).max().getAsInt();
        int min = Arrays.stream(nums).min().getAsInt();

        // 2. 检查是否存在满足三元子数组条件的 i , j, k值
        for (int j = 3; j < nums.length - 3; j++) {
            // 2.1 剪枝节省时间
            if (Math.abs(preSum[nums.length - 1] - preSum[j] - preSum[j - 1]) > (max - min)) {
                continue;
            }

            // 2.2 验证是否有满足条件的三元数组
            Set<Integer> set = new HashSet<>();
            // 2.2.1 查找 preSum(0, i-1) == preSum(i+1, j-1) 的 i 和 j
            for (int i = 1; i < j - 1; i++) {
                if (preSum[i - 1] == preSum[j -1] - preSum[i]) {
                    set.add(preSum[i - 1]);
                }
            }

            // 2.2.2 检验查找是否存在 preSum(j+1, k-1) == preSum(k+1, n-1) 且 == preSum[i - 1] 的 K 值
            for (int k = j + 2; k < nums.length - 1; k++) {
                int value = preSum[k - 1] - preSum[j];
                if ((preSum[nums.length - 1] - preSum[k] == value)
                    && (set.contains(value))) {
                    return true;
                }
            }
        }

        return false;
    }



    public static void main(String[] args) {
        PreSumLeaf instance = new PreSumLeaf();
        //int[] nums = {3, 4, 7, 2, -3, 1, 4, 2};
        //int[] nums = {1,2,3};
        //int k = 7;
        //System.out.println("The greater value：" + instance.subarraySum(nums, 7));

        //int[] nums = {23,2,4,6,7};
        //int k = 6;
        //System.out.println("The res: " + instance.checkSubarraySum(nums, k));

        // int[] nums = {0,1,0,1,0};
        // System.out.println("The res: " + instance.findMaxLength(nums));

        //int[] nums = {9,9,6,0,6,6,9};
        //System.out.println("The res: " + instance.longestWPI(nums));
        int[][] matrix = {{3,0,1,4,2},{5,6,3,2,1},{1,2,0,1,5},{4,1,0,1,7},{1,0,3,0,5}};
        // [[[[-1]]],[0,0,0,0]]
        //int[][] matrix = {-1};
        // [2,1,4,3],[1,1,2,2],[1,2,2,4]
        //instance.initMatrix(matrix);
        //System.out.println("The res: " + instance.sumRegion());

        // bookings = [[1,2,10],[2,3,20],[2,5,25]], n = 5
        int[][] bookings = {{1,2,10},{2,3,20},{2,5,25}};
        int n = 5;
        //System.out.println("The res: " + Arrays.toString(instance.corpFlightBookings(bookings, n)));

        int[][] trips = {{2,1,5},{3,5,7}};
        int capacity = 3;
        System.out.println("The res: " + instance.carPooling(trips, capacity));

    }
}
