package com.liu.redleaf;

public class BinarySearchUtil {

    /*
     * @description: 经典写法，查找数组中不含有重复数字target的位置下标
     * @param nums 待查找数组
     * @param target 待查找的目标值
     * @return 目标值 target 在数组中的位置下标
     * */
    public static int binarySearch(int[] nums, int target) {
        if (nums.length == 0) return -1;
        int left = 0;
        int right = nums.length - 1; // 注意

        while(left <= right) {
            int mid = left + (right - left) / 2;
            if(nums[mid] == target)
                return mid;
            else if (nums[mid] < target)
                left = mid + 1; // 注意
            else if (nums[mid] > target)
                right = mid - 1; // 注意
        }

        return -1;
    }

    /*
    * @description: 查找数组中含有重复数字target左边第一个位置的下标
    * @param nums 待查找数组
    * @param target 待查找的目标值
    * @return 目标值 target 在数组中左边第一个位置的下标
    * */
    public static int binarySearchFirst(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        // 搜索区间为 [left, right]
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                // 搜索区间变为 [mid+1, right]
                left = mid + 1;
            } else if (nums[mid] > target) {
                // 搜索区间变为 [left, mid-1]
                right = mid - 1;
            } else if (nums[mid] == target) {
                // 收缩右侧边界
                right = mid - 1;
            }
        }

        // 最后检查 left 出界情况
        return left == nums.length || nums[left] != target ? -1 : left;
    }

    /*
     * @description: 查找数组中含有重复数字target右边最后一个位置的下标
     * @param nums 待查找数组
     * @param target 待查找的目标值
     * @return 目标值 target 在数组中右边最后一个位置的下标
     * */
    public static int binarySearchLast(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        // 搜索区间为 [left, right]
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] == target) {
                // 收缩左侧边界即可
                left = mid + 1;
            }
        }

        // 最后检查 right 越界的情况
        return right < 0 || nums[right] != target ? -1 : right;
    }

    /*
     * @description: 经典写法，查找数组中左边第一个大于等于 target 元素的下标
     * @param nums 待查找数组
     * @param target 待查找的目标值
     * @return 目标值 target 在数组中左边第一个大于等于 target 元素的下标
     * */
    public static int binarySearchClassFirstLarger(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (mid == 0 || nums[mid - 1] < target) {
                return mid;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    /*
     * @description: 查找数组中左边第一个大于等于 target 元素的下标
     * @param nums 待查找数组
     * @param target 待查找的目标值
     * @return 目标值 target 在数组中左边第一个大于等于 target 元素的下标
     * */
    public static int binarySearchFirstLarger(int[] nums, int target) {
        int left = 0, right = nums.length;
        while (left < right) {
            int mid = (left + right) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return right == nums.length ? -1 : (nums[right] == target ? right : -1);
    }

    /*
     * @description: 经典写法的查找数组中右边最后一个小于等于 target 元素的下标
     * @param nums 待查找数组
     * @param target 待查找的目标值
     * @return 目标值 target 在数组中右边最后一个小于等于 target 元素的下标
     * */
    public static int binarySearchClassLastSmaller(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] > target) {
                right = mid - 1;
            } else if (mid == nums.length - 1 || nums[mid + 1] > target) {
                return mid;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    /*
     * @description: 查找数组中右边最后一个小于等于 target 元素的下标
     * @param nums 待查找数组
     * @param target 待查找的目标值
     * @return 目标值 target 在数组中右边最后一个小于等于 target 元素的下标
     * */
    public static int binarySearchLastSmaller(int[] nums, int target) {
        int left = 0, right = nums.length;
        while (left < right) {
            int mid = (left + right) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return right == nums.length ? -1 : (nums[right] == target ? right : -1);
    }
}
