package com.liu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
    // 3.4 适用场景：
    // 在STL的sort算法和stdlib的qsort算法中，都将插入排序作为快速排序的补充，用于少量元素的排序。
    // 另外，在JDK 7 java.util.Arrays所用的sort方法的实现中，当待排数组长度小于47时，会使用插入排序。
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
    // 希尔排序是第一个突破O(n2)的排序算法，它是简单插入排序的改进版.
    // 4.1 算法思想：
    // 先将整个待排序的记录序列分割成为若干子序列分别进行直接插入排序，具体算法描述：
    // 选择一个增量序列t1，t2，…，tk，其中ti>tj，tk=1；
    // 按增量序列个数k，对序列进行 k 趟排序；
    // 每趟排序，根据对应的增量ti，将待排序列分割成若干长度为m 的子序列，分别对各子表进行直接插入排序。仅增量因子为1 时，整个序列作为一个表来处理，表长度即为整个序列的长度。
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
    // 5.1 算法思路：分而治之，借助额外空间，合并两个有序数组，得到更长的有序数组。
    //     申请空间，使其大小为两个已经排序序列之和，该空间用来存放合并后的序列
    //     设定两个指针，最初位置分别为两个已经排序序列的起始位置
    //     比较两个指针所指向的元素，选择相对小的元素放入到合并空间，并移动指针到下一位置
    //     重复步骤3直到某一指针到达序列尾
    //     将另一序列剩下的所有元素直接复制到合并序列尾
    // 5.2 复杂度
    // 时间复杂度：O(NlogN)，这里N是数组的长度；
    // 空间复杂度：O(N)，辅助数组与输入数组规模相当。
    // 5.3 稳定性：在遇到相等的数据的时候必然是按顺序“抄写”到辅助数组上的，所以，归并排序同样是稳定算法。
    // 5.4 适用场景：归并排序在数据量比较大的时候也有较为出色的表现（效率上），
    // 但是，其空间复杂度O(n)使得在数据量特别大的时候（例如，1千万数据）几乎不可接受。
    // 算法优化时，可以在数组待排数组的个数小于一定值时（例如<=7个时）采用插入排序法。
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
    // 6.1 算法思想：
    // 6.1.1 从数列中挑出一个元素，称为"基准"（pivot），
    // 6.1.2 重新排序数列，所有比基准值小的元素摆放在基准前面，所有比基准值大的元素摆在基准后面（相同的数可以到任何一边）。在这个分区结束之后，该基准就处于数列的中间位置。这个称为分区（partition）操作。
    // 6.1.3 递归地（recursively）把小于基准值元素的子数列和大于基准值元素的子数列排序。
    // 6.2 复杂度：
    //    时间复杂度：O(NlogN)，这里 N是数组的长度；
    //    空间复杂度：O(logN)，这里占用的空间主要来自递归函数的栈空间
    // 6.3 稳定性：快速排序并不是稳定的，因为无法保证相等的数据按顺序被扫描到和按顺序存放。
    // 6.4 适用场景：
    //    快速排序在大多数情况下都是适用的，尤其在数据量大的时候性能优越性更加明显。
    //    但是在必要的时候，需要考虑下优化以提高其在最坏情况下的性能。
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
            //将比基准数pivotKey小的数交换到低端
            while(i < j && pivotKey <= array[j]){
                j--;
            }
            //将比基准数pivotKey大的交换到高端
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
            //将比基准数pivotKey小的数交换到低端
            while(low < high && pivotKey <= array[high]){
                high--;
            }
            //将比基准数pivotKey大的交换到高端
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
    // 计数排序不是基于比较的排序算法，其核心在于将输入的数据值转化为键存储在额外开辟的数组空间中。
    // 作为一种线性时间复杂度的排序，计数排序要求输入的数据必须是有确定范围的整数。
    // 7.1 算法思想：
    // 找出待排序的数组中最大和最小的元素；
    // 统计数组中每个值为i的元素出现的次数，存入数组C的第i项；
    // 对所有的计数累加（从C中的第一个元素开始，每一项和前一项相加）；
    // 反向填充目标数组：将每个元素i放在新数组的第C(i)项，每放一个元素就将C(i)减去1。
    // 7.2 复杂度：
    //   时间复杂度：O(N + k)，这里 N是数组的长度；
    //   空间复杂度：O(k)
    // 7.3 稳定性：稳定的排序算法。
    // 7.4 适用场景：排序目标要能够映射到整数域，其最大值最小值应当容易辨别。
    // 另外，计数排序需要占用大量空间，它比较适用于数据比较集中的情况。
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


    // 8. 堆排序
    // 8.1 算法思想：堆排序(Heapsort)是指利用堆积树（堆）这种数据结构所设计的一种排序算法，它是选择排序的一种。可以利用数组的特点快速定位指定索引的元素。堆排序就是把最大堆堆顶的最大数取出，将剩余的堆继续调整为最大堆，再次将堆顶的最大数取出，这个过程持续到剩余数只有一个时结束。
    //     创建一个堆 H[0……n-1]；
    //     把堆首（最大值）和堆尾互换；
    //     把堆的尺寸缩小 1，并调用 shift_down(0)，目的是把新的数组顶端数据调整到相应位置；
    //     重复步骤 2，直到堆的尺寸为 1。
    // 8.2 复杂度：
    //   时间复杂度：O(NlogN)，这里 N是数组的长度；
    //   空间复杂度：O(1)
    // 8.3 稳定性：堆排序存在大量的筛选和移动过程，属于不稳定的排序算法。
    // 8.4 适用场景：堆排序在建立堆和调整堆的过程中会产生比较大的开销，在元素少的时候并不适用。
    // 但是，在元素比较多的情况下，还是不错的一个选择。尤其是在解决诸如“前n大的数”一类问题时，几乎是首选算法。
    public static int[] heapSort(int []array){
        //check params
        if(array == null || array.length == 0
            || array.length == 1){
            return array;
        }

        //1.构建大顶堆
        for(int i = array.length/2 - 1; i >= 0; i--){
            //从第一个非叶子结点从下至上，从右至左调整结构
            adjustHeap(array, i, array.length);
        }
        //2.调整堆结构+交换堆顶元素与末尾元素
        for(int j = array.length - 1; j>0; j--){
            // 将堆顶元素与末尾元素进行交换
            int temp = array[0];
            array[0] = array[j];
            array[j] = temp;

            // 重新对堆进行调整
            adjustHeap(array,0,j);
        }

        return array;
    }

    /**
     * 调整大顶堆（仅是调整过程，建立在大顶堆已构建的基础上）
     * @param array
     * @param i
     * @param length
     */
    public static void adjustHeap(int []array, int i, int length){
        int temp = array[i];//先取出当前元素i
        for(int k = i*2 + 1; k < length; k = k*2 + 1) {//从i结点的左子结点开始，也就是2i+1处开始
            if(k + 1 < length && array[k] < array[k+1]) {//如果左子结点小于右子结点，k指向右子结点
                k++;
            }

            if(array[k] > temp) { // 如果子节点大于父节点，将子节点值赋给父节点（不用进行交换）
                array[i] = array[k];
                i = k;
            } else {
                break;
            }
        }
        array[i] = temp;//将temp值放到最终的位置
    }


    // 9. 桶排序
    // 桶排序又叫箱排序，是计数排序的升级版，它的工作原理是将数组分到有限数量的桶子里，然后对每个桶子再分别排序
    // （有可能再使用别的排序算法或是以递归方式继续使用桶排序进行排序），最后将各个桶中的数据有序的合并起来。
    // 9.1 算法思想：
    // 找出待排序数组中的最大值max、最小值min
    // 我们使用 动态数组ArrayList 作为桶，桶里放的元素也用 ArrayList 存储。桶的数量为(max-min)/arr.length+1
    // 遍历数组 arr，计算每个元素 arr[i] 放的桶
    // 每个桶各自排序
    // 遍历桶数组，把排序好的元素放进输出数组
    // 9.2 复杂度：
    //   时间复杂度：O(NlogN)，这里 N是数组的长度；
    //   空间复杂度：O(1)
    // 9.3 稳定性：算法是不稳定的
    // 9.4 适用场景：桶排序可用于最大最小值相差较大的数据情况，但桶排序要求数据的分布必须均匀，否则可能导致数据都集中到一个桶中。
    // 比如[104,150,123,132,20000], 这种数据会导致前4个数都集中到同一个桶中。导致桶排序失效。
    public static void bucketSort(int[] arr){
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < arr.length; i++){
            max = Math.max(max, arr[i]);
            min = Math.min(min, arr[i]);
        }
        //桶数
        int bucketNum = (max - min) / arr.length + 1;
        ArrayList<ArrayList<Integer>> bucketArr = new ArrayList<>(bucketNum);
        for(int i = 0; i < bucketNum; i++){
            bucketArr.add(new ArrayList<Integer>());
        }
        //将每个元素放入桶
        for(int i = 0; i < arr.length; i++){
            int num = (arr[i] - min) / (arr.length);
            bucketArr.get(num).add(arr[i]);
        }
        //对每个桶进行排序
        for(int i = 0; i < bucketArr.size(); i++){
            Collections.sort(bucketArr.get(i));
        }
        System.out.println(bucketArr.toString());
    }


    // 10. 基数排序
    // 基数排序(Radix Sort)是桶排序的扩展，它的基本思想是：将整数按位数切割成不同的数字，然后按每个位数分别比较。
    // 排序过程：将所有待比较数值（正整数）统一为同样的数位长度，数位较短的数前面补零。然后，从最低位开始，依次进行一次排序。这样从最低位排序一直到最高位排序完成以后, 数列就变成一个有序序列。
    // 10.1 算法思想：
    // 取得数组中的最大数，并取得位数；
    // arr为原始数组，从最低位开始取每个位组成radix数组；
    // 对radix进行计数排序（利用计数排序适用于小范围数的特点）
    // 10.2 复杂度：
    //   时间复杂度：O(N*k)，这里 N是数组的长度；
    //   空间复杂度：O(N+k)
    // 10.3 稳定性：稳定
    // 10.4 适用场景：基数排序要求较高，元素必须是整数，整数时长度10W以上，最大值100W以下效率较好，但是基数排序比其他排序好在可以适用字符串，或者其他需要根据多个条件进行排序的场景，
    // 例如日期，先排序日，再排序月，最后排序年 ，其它排序算法可是做不了的。
    public abstract class Sorter {
        public abstract void sort(int[] array);
    }

    public class RadixSorter extends Sorter {

        private int radix;

        public RadixSorter() {
            radix = 10;
        }

        @Override
        public void sort(int[] array) {
            // 数组的第一维表示可能的余数0-radix，第二维表示array中的等于该余数的元素
            // 如：十进制123的个位为3，则bucket[3][] = {123}
            int[][] bucket = new int[radix][array.length];
            int distance = getDistance(array); // 表示最大的数有多少位
            int temp = 1;
            int round = 1; // 控制键值排序依据在哪一位
            while (round <= distance) {
                // 用来计数：数组counter[i]用来表示该位是i的数的个数
                int[] counter = new int[radix];
                // 将array中元素分布填充到bucket中，并进行计数
                for (int i = 0; i < array.length; i++) {
                    int which = (array[i] / temp) % radix;
                    bucket[which][counter[which]] = array[i];
                    counter[which]++;
                }
                int index = 0;
                // 根据bucket中收集到的array中的元素，根据统计计数，在array中重新排列
                for (int i = 0; i < radix; i++) {
                    if (counter[i] != 0)
                        for (int j = 0; j < counter[i]; j++) {
                            array[index] = bucket[i][j];
                            index++;
                        }
                    counter[i] = 0;
                }
                temp *= radix;
                round++;
            }
        }

        private int getDistance(int[] array) {
            int max = computeMax(array);
            int digits = 0;
            int temp = max / radix;
            while(temp != 0) {
                digits++;
                temp = temp / radix;
            }
            return digits + 1;
        }

        private int computeMax(int[] array) {
            int max = array[0];
            for(int i=1; i<array.length; i++) {
                if(array[i]>max) {
                    max = array[i];
                }
            }
            return max;
        }
    }



    public static void main(String[] args){
        int[] array = {2,7,4,1,8,1,6};
        //System.out.println("The sorted array: "+ quickSort(array));
        //System.out.println("The sorted array: "+ quickSortII(array, 0, array.length -1));
        int[] array2 = {-15,5,7,6,5};
        System.out.println("The sorted array: "+ countingSort(array2));
    }

}

