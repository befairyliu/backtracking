package com.liu.redleaf;

import java.util.ArrayList;
import java.util.Stack;

public class BinarySearchLeaf {



    // 「搜索区间」是 [left, right]
    // while(left <= right) 的终止条件是 left == right + 1
    // while(left < right) 的终止条件是 left == right
    // 对于寻找左右边界的二分搜索，常见的手法是使用左闭右开的「搜索区间」
    int binarySearch(int[] nums, int target) {
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

    // 寻找左侧边界的二分查找：查找 [1,2,2,4] 中的2最左边边索引
    // 左右都闭
    int left_bound0(int[] nums, int target) {
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

        // 检查出界情况
        if (left >= nums.length || nums[left] != target) {
            return -1;
        }

        return left;
    }

    // 寻找右侧边界的二分查找: 查找 [1,2,2,4] 中的2最右边索引
    // 左右都闭
    int right_bound0(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] == target) {
                // 这里改成收缩左侧边界即可
                left = mid + 1;
            }
        }

        // 这里改为检查 right 越界的情况，见下图
        if (right < 0 || nums[right] != target) {
            return -1;
        }
        return right;
    }

    // =========================================================
    // 寻找左侧边界的二分查找：查找 [1,2,2,4] 中的2最左边边索引
    // 左闭右开
    int left_bound1(int[] nums, int target) {
        if (nums.length == 0) return -1;
        int left = 0;
        int right = nums.length; // 注意

        while (left < right) { // 注意
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                right = mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid; // 注意
            }
        }
        //return left;

        // target 比所有数都大
        if (left == nums.length) return -1;
        // 类似之前算法的处理方式
        return nums[left] == target ? left : -1;
    }




    // 寻找右侧边界的二分查找: 查找 [1,2,2,4] 中的2最右边索引
    // 左闭右开
    int right_bound1(int[] nums, int target) {
        if (nums.length == 0) return -1;
        int left = 0, right = nums.length;

        while (left < right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                left = mid + 1; // 注意
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid;
            }
        }

        //return left - 1; // 注意
        if (left == 0) return -1;
        return nums[left-1] == target ? (left-1) : -1;
    }
}
