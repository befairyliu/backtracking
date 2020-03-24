package com.liu.redleaf;

import java.util.Arrays;

public class SortLeaf {


    /**
     * 1. 冒泡排序 数据两两相比进行冒泡
     *
     * @param array
     * @return
     */
    public static int[] bubbleSort(int[] array) {
        // check params
        if (array == null || array.length == 0 || array.length == 1 ) {
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
     * 2. 选择排序 每次选择最小的数放前面
     * @param array
     * @return
     */
    public static int[] selectionSort(int[] array) {
        // check params
        if (array == null || array.length == 0 || array.length == 1) {
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
     * 3.插入排序--通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。
     * @param array
     * @return
     */
    public static int[] insertionSort(int[] array) {
        // check params
        if (array == null || array.length == 0 || array.length == 1) {
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

    //4.1 希尔排序
    public static int[] shellSort(int[] array){
        //check params
        if(array == null || array.length == 0
                || array.length == 1){
            return array;
        }

        //基本上和插入排序一样的道理
        //不一样的地方在于，每次循环的步长，通过减半的方式来实现
        //说明：基本原理和插入排序类似，不一样的地方在于。通过间隔多个数据来进行插入排序。
        int len = array.length;
        int gap = len / 2;
        int temp;
        while (gap > 0) {
            for(int i = gap; i < len; i++){
                temp = array[i];
                int j = i; //从i位置开始
                while(j - gap >= 0 && temp < array[j - gap]){
                    array[j] = array[j - gap];
                    j = j - gap;
                }
                array[j] = temp;
            }
            // 步长每次减半
            gap = gap / 2;
        }
        return array;
    }
    

    /**
     * 4.2 希尔排序
     *
     * @param array
     * @return
     */
    public static int[] ShellSort(int[] array) {
        // check params
        if (array == null || array.length == 0 || array.length == 1) {
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
     * 5.归并排序
     *
     * @param array
     * @return
     */
    public static int[] mergeSort(int[] array) {
        //check params
        if(array == null || array.length == 0
                || array.length == 1){
            return array;
        }

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
            // 1.左右数据都没有遍历完成的时候
            while(i < left.length && j < right.length){
                if(left[i] < right[j]){
                    result[index++] = left[i++];
                } else {
                    result[index++] = right[j++];
                }
            }
            // 2.左数字还没有遍历完成，右数组遍历完成
            while(i < left.length){
                result[index++] = left[i++];
            }

            // 3.左数组遍历完成，右数组还没有遍历完
            while(j < right.length){
                result[index++] = right[j++];
            }
        }
        return result;
    }
    
    
    // 6. 快速排序
    public static int[] quickSort(int[] array){
        //check params
        if(array == null || array.length == 0
                || array.length == 1 ){
            return array;
        }

        quickSort(array, 0, array.length - 1);
        return array;
    }

    public static void quickSort(int[] array, int low, int high){
        if(low > high){
            return;
        }

        // 基准数
        int pivotKey = array[low];
        int i = low, j = high;
        while(i < j){
            //将比pivot小的数交换到低端
            while(i < j && pivotKey <= array[j]){
                j--;
            }
            //将比枢轴记录大的交换到高端
            while(i < j && array[i] <= pivotKey){
                i++;
            }

            //如果满足条件则交换array[i]和array[j]
            if(i < j){
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        // i >= j时，将基准数放到中间的位置（基准数归位）
        array[low] = array[i];
        array[i] = pivotKey;

        // recursion quick sort
        quickSort(array, low, i - 1);
        quickSort(array, i + 1, high);
    }


    public static int[] quickSortII(int[] array, int low, int high){
        //check params
        if(array == null || array.length == 0
                || array.length == 1){
            return array;
        }

        int pivot = 0; //枢纽元素下标
        if(low < high){
            pivot = partition(array, low, high);
            //对第一部分进行递归排序
            quickSortII(array, low, pivot -1);
            //对第二部分进行递归排序
            quickSortII(array, pivot + 1, high);
        }

        return array;
    }

    public static int partition(int[] array, int low, int high){
        //check condition
        int pivotKey = array[low];// 选取第一个数为枢轴元素
        int pivotIndex = low;
        int temp = 0;
        while(low < high){
            //将比pivot小的数交换到低端
            while(low < high && pivotKey <= array[high]){
                high--;
            }
            //将比枢轴记录大的交换到高端
            while(low < high && array[low] <= pivotKey){
                low++;
            }

            //如果满足条件则交换array[low]和array[high]
            if(low < high){
                temp = array[low];
                array[low] = array[high];
                array[high] = temp;
            }
        }

        // low >= high时，交换枢轴元素和array[low]
        //枢纽所在位置赋值
        temp = array[low];
        array[low] = pivotKey;
        array[pivotIndex] = temp;
        //返回枢纽所在的位置
        pivotIndex = low;

        return pivotIndex;
    }


    //7. 计数排序
    /**
     * 计数排序
     *
     * @param array
     * @return
     */
    public static int[] countingSort(int[] array) {
        //check params
        if(array == null || array.length == 0
                || array.length == 1){
            return array;
        }

        int min = array[0], max = array[0];
        //遍历找到最大最小值
        for (int i = 1; i < array.length; i++) {
            max = Math.max(array[i], max);
            min = Math.min(array[i], min);
        }

        // 向量偏差, 0与最小值之间的差值
        int bias = min - 0;
        int[] bucket = new int[max - min + 1];
        Arrays.fill(bucket, 0);

        // 已key为值，统计待排序数出现的次数
        for (int i = 0; i < array.length; i++) {
            //数组每个值都减去偏差，就可以保证bucket的中最小值的index从0开始
            bucket[array[i] - bias]++;
        }

        int index = 0;
        for(int i = 0; i < bucket.length; i++){
            while(bucket[i] > 0){
                //把之前存储时的偏差加回来
                array[index++] = i + bias;
                bucket[i]--;
            }
        }
        return array;
    }
    
    
    
    

    public static void main(String[] args){

        int[] array = {1,6,5,3,7,9,2};
        System.out.println("The sort array:" + bubbleSort(array));
    }
}
