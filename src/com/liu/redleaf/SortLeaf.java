package com.liu;

import java.util.Arrays;

//十大排序算法Java示例
public class SortLeaf {

    //1. 冒泡排序
    // 1.1 算法思想：冒泡排序时针对相邻元素之间的比较，可以将大的数慢慢“沉底”(数组尾部)
    // 1.2 复杂度：
    // 时间复杂度：O(N^2)，这里N是数组的长度；
    // 空间复杂度：O(1)，使用到常数个临时变量。
    // 1.3 稳定性：在相邻元素相等时，它们并不会交换位置，所以，冒泡排序是稳定排序。
    public static int[] bubbleSort(int[] array){
        //check params
        if(array == null || array.length == 0
                || array.length == 1){
            return array;
        }

        // 算法思想：比较相邻的元素。如果第一个比第二个大，就交换它们两个，慢慢将较大数“沉淀”到最后。
        for(int i = 0; i < array.length; i++) { // 需要排序的轮次
            for(int j = 0; j < array.length - 1 - i; j++) { // 每轮需要比较的次数
                if(array[j] > array[j + 1]) { // 交换位置
                    int temp = array[j];
                    array[j] = array[j + i];
                    array[j + 1] = temp;
                }
            }
        }

        return array;
    }

    //1. 冒泡排序优化版
    // 在数据完全有序的时候展现出最优时间复杂度，为O(n)。其他情况下，几乎总是O( n2 )。因此，算法在数据基本有序的情况下，性能最好。
    // 增加一个swap的标志，当前一轮没有进行交换时，说明数组已经有序，没有必要再进行下一轮的循环了，直接退出。
    public static int[] bubbleSortOptimized(int[] array){
        //check params
        if(array == null || array.length == 0
            || array.length == 1){
            return array;
        }

        // 算法思想：比较相邻的元素。如果第一个比第二个大，就交换它们两个，慢慢将较大数“沉淀”到最后。
        for(int i = 0; i < array.length; i++) { // 需要排序的轮次

            // 标志位, 默认数组是有序的(排序已经完成)，但只要发生一次交换，就必须进行下一轮比较
            // 如果在内层循环中，都没有执行一次交换操作，说明此时数组已经是升序数组
            boolean sorted = true;
            for(int j = 0; j < array.length - 1 - i; j++) { // 每轮需要比较的次数
                if(array[j] > array[j + 1]) { // 交换位置
                    int temp = array[j];
                    array[j] = array[j + i];
                    array[j + 1] = temp;
                    sorted = false;
                }
            }

            // 排序完成，跳出循环
            if (sorted) {
                break;
            }
        }

        return array;
    }


    //2. 选择排序
    // 2.1 算法思想：
    //    在未排序序列中找到最小（大）元素，存放到排序序列的起始位置
    //    从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。
    //    重复第二步，直到所有元素均排序完毕。
    // 2.2 复杂度
    // 时间复杂度：O(N^2)，这里N是数组的长度；
    // 空间复杂度：O(1)，使用到常数个临时变量。
    // 2.3 稳定性：用数组（默认情况）实现的选择排序是不稳定的，用链表实现的选择排序是稳定的。
    public static int[] selectionSort(int[] array){
        //check params
        if(array == null || array.length == 0
                || array.length == 1){
            return array;
        }

        // 将第一个值看成最小值,然后和后续的比较找出最小值和下标,
        // 交换本次遍历的起始值和最小值,然后循环遍历
        for(int i = 0; i < array.length; i++) { // 需要排序的轮次
            int minIndex = i;

            // 循环查找最小值下表
            for(int j = i; j < array.length; j++){
                if(array[j] < array[minIndex]){
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                int temp = array[minIndex];
                array[minIndex] = array[i];
                array[i] = temp;
            }

        }
        return array;
    }

    //3. 插入排序
    // 3.1 算法思想：
    // 把待排序的数组分成已排序和未排序两部分，初始的时候把第一个元素认为是已排好序的。
    // 从第二个元素开始，在已排好序的子数组中寻找到该元素合适的位置并插入该位置。
    // 重复上述过程直到最后一个元素被插入有序子数组中。
    // 3.2 复杂度
    // 时间复杂度：O(N^2)，这里N是数组的长度；
    // 空间复杂度：O(1)，使用到常数个临时变量。
    // 3.3 稳定性：由于只需要找到不大于当前数的位置而并不需要交换，因此，直接插入排序是稳定的排序方法。
    public static int[] insertionSort(int[] array){
        //check params
        if(array == null || array.length == 0
                || array.length == 1){
            return array;
        }

        // 第1个元素可以认为已经被排序；
        // 从第2个元素开始，取出元素，在已经排序的元素序列中从后向前扫描；
        // 如果已排序元素大于新元素，将已排序元素后移一个位置，如此循环找到新元素的插入位置；
        int current;
        for(int i = 1; i < array.length; i++){
            current = array[i];
            int j = i;
            while (j > 0 && current < array[j - 1]){
                // 后移一个位置
                array[j] = array[j - 1];
                j--;
            }

            // 存在比其小的数，插入
            if (j != i) {
                array[j] = current;
            }
        }

        return array;
    }


    //4. 希尔排序
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


    // 5. 归并排序
    /**
     * 归并排序
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
        return merge(mergeSort(left), mergeSort(right));
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
        if(array == null || array.length <= 1 ){
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
        if(array == null || array.length <= 1){
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
        int[] array = {2,7,4,1,8,1,6};
        //System.out.println("The sorted array: "+ quickSort(array));
        //System.out.println("The sorted array: "+ quickSortII(array, 0, array.length -1));
        int[] array2 = {-15,5,7,6,5};
        System.out.println("The sorted array: "+ countingSort(array2));
    }

}

