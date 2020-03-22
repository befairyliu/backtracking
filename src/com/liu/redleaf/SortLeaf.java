package com.liu.redleaf;

import java.util.Arrays;

public class SortLeaf {


    /**
     * 冒泡排序 数据两两相比进行冒泡
     *
     * @param array
     * @return
     */
    public static int[] bubbleSort(int[] array) {
        // check params
        if (array == null || array.length == 0) {
            return array;
        }

        //把最大的数值往后移
        for (int i = 0; i < array.length; i++) {
            //已经拍过序的就不用再比较排序,所以j < array.length - 1 - i
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        return array;
    }


    /**
     * 选择排序 每次选择最小的数放前面
     * @param array
     * @return
     */
    public static int[] selectionSort(int[] array) {
        // check params
        if (array == null || array.length == 0) {
            return array;
        }

        for (int i = 0; i < array.length; i++) {
            int minIndex = i;//默认第一个数最小，从0开始
            for (int j = i; j < array.length; j++) {
                //找到最小的数
                if (array[j] < array[minIndex]) {
                    minIndex = j; //将最小数的索引保存
                }
            }
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }
        return array;
    }



    /**
     * 插入排序--通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。
     * @param array
     * @return
     */
    public static int[] insertionSort(int[] array) {
        // check params
        if (array == null || array.length == 0) {
            return array;
        }

        int current; // 记录当前位置（未排序部分）
        for (int i = 0; i < array.length - 1; i++) {
            current = array[i + 1];
            int j = i; //j用来在已排序好的序列中循环
            while (j >= 0 && current < array[j]) {
                //大于当前值的数，位置往后移
                array[j+1] = array[j];
                j--;
            }
            //插入待排序的值
            array[j+1] = current;
        }
        return array;
    }


    /**
     * 希尔排序
     *
     * @param array
     * @return
     */
    public static int[] ShellSort(int[] array) {
        // check params
        if (array == null || array.length == 0) {
            return array;
        }

        int len = array.length;
        int gap = len / 2;
        // gap循环步长,不断缩小gap，直到1为止
        while (gap > 0) {
            for (int i = 0; i < len - gap ; i++) {
                //使用当前gap进行组内插入
                for(int k = 0; k < len - gap; k = k + gap){
                    if(array[k] > array[k+gap]){
                        int temp = array[k+gap];
                        array[k+gap] = array[k];
                        array[k] = temp;
                    }
                }
            }
            gap /= 2;
        }
        return array;
    }


    /**
     * 归并排序
     *
     * @param array
     * @return
     */
    public static int[] MergeSort(int[] array) {
        if (array.length < 2) return array;
        int mid = array.length / 2;
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);
        return merge(MergeSort(left), MergeSort(right));
    }
    /**
     * 归并排序——将两段排序好的数组结合成一个排序数组
     *
     * @param left
     * @param right
     * @return
     */
    public static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        for (int index = 0, i = 0, j = 0; index < result.length; index++) {
            if (i >= left.length)
                result[index] = right[j++];
            else if (j >= right.length)
                result[index] = left[i++];
            else if (left[i] > right[j])
                result[index] = right[j++];
            else
                result[index] = left[i++];
        }
        return result;
    }


    public static void main(String[] args){

        int[] array = {1,6,5,3,7,9,2};
        System.out.println("The sort array:" + bubbleSort(array));
    }
}
