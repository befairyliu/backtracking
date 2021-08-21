0723

第一题
N个展厅，每个展厅报名人数记于数组中，所有展厅参展总人数上限为cnt。计算单个展厅允许最大参展人数limit，使得总人数不超过cnt。报名人数之和小于cnt，返回-1。
1 < num.lenth < 10^5
1 <= num[i] < 10^5
1 <= cnt < 10^9
本题对效率有要求。

exp
输入[1,4,2,5,5,1,6]，13
输出2

输入[1,1],1
输出0

```
  public static int manageTourists(int[] nums, int cnt) {
        long ans = 0L;
        long[] pre = new long[nums.length];
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            ans = ans + nums[i];
            pre[i] = ans;
        }
        if (ans <= cnt) {
            return -1;
        }
        int max = Arrays.stream(nums).max().getAsInt();
        int ret = 0;
        for (int i = 0 ;i < max; i++) {
            int index = search(nums, i);

            if (check(nums, pre, index, i, cnt)) {
                ret = Math.max(i, ret);
            } else {
                break;
            }

        }
        return ret;
    }

    public static int search(int[] nums, int tar) {
        int l = 0;
        int r = nums.length;
        while (l < r) {
            int mid = (r - l) / 2 + l;
            if (nums[mid] <= tar) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        if (l >= nums.length) {
            return -1;
        }
        return l;
    }

    public static boolean check(int[] nums, long[] pre, int index, int tar, int cnt) {
        long tmp=0L;
        if(index!=0){
            tmp = pre[index - 1];
        }
        tmp = tmp + (long) (nums.length - index) * tar;
        return tmp <= cnt;
    }
```


第二题
设计一个系统管理多个设备，设备可以产生和传输数据。传输数据后传输设备和该设备数据的来源者贡献+10，(需要一直向上查找到最初的数据产生者)，对方已有该数据时，不传输，不加贡献。可以查询所有设备的当前贡献。
设备数<1000,数据标识id<1000。

偷懒直接用大数组存放设备上数据的来源者编号，如果是自己生产，填-1.

```
public DataMachineSystem() {

}
Machine[] machines = null;

public DataMachineSystem(int num) {
    machines = new Machine[num];
    for (int i = 0; i < num; i++) {
        machines[i] = new Machine();
    }
}



public int transferData(int machineA, int machineB, int dataId) {

    Machine temp = machines[machineA - 1];
    if(!temp.dataIds.containsKey(dataId)) {
        temp.dataIds.put(dataId,null);
    }
    int res ;
    if(machines[machineB-1].dataIds.containsKey(dataId)) {
        res= 0;
    } else{
        Machine machine = machines[machineA-1];
        machines[machineB-1].dataIds.put(dataId,machine);
        res= 1;
    }
    temp.contribution+=10*res;
    while (temp.dataIds.get(dataId)!=null && res>0) {
        temp=temp.dataIds.get(dataId);
        temp.contribution+=10;
    }
    return res;
}

public int transferDataToAll(int machine, int dataId) {
    Machine temp = machines[machine - 1];
    if(!temp.dataIds.containsKey(dataId)) {
        temp.dataIds.put(dataId,null);
    }
    int contribution = 0;
    for (int i = 0; i < machines.length; i++) {
        if(!machines[i].dataIds.containsKey(dataId)) {
            contribution++;
            machines[i].dataIds.put(dataId,temp);
        }
    }
    temp.contribution+=10*contribution;
    while (temp.dataIds.get(dataId)!=null && contribution>0) {
        temp=temp.dataIds.get(dataId);
        temp.contribution+=10;

    }
    return contribution;
}

public int queryContribution(int machine) {
    return machines[machine-1].contribution;
}

class Machine{
    int contribution;
    HashMap<Integer,Machine> dataIds = new HashMap<>();
    public Machine(){}
    public Machine(int contribution,HashMap<Integer,Machine> dataIds){
        this.contribution=contribution;
        this.dataIds=dataIds;
    }
}
```


第三题
2*n个社团，两个社团合演一个节目。社团编号和节目成本放在数组中。如：[1,2,100]。要求：所有社团都参加且只表演一个节目，返回最低总成本，用例保证有解。

exp
输入[[0,1,100],[2,3,200]]
输出：300

社团数<16
节目数<500
节目成本<100
```
// 状态压缩 + 动态规划
int CooperativePerformance(int num, int **program, int programSize)
{
    int maxCoop = num * 2;
    int maxState = 1 << maxCoop;
    int dpState[maxState];
    for (int i = 0; i < maxState; i++) {
        dpState[i] = INT_MAX;
    }

    dpState[0] = 0;
    for (int i = 0; i < programSize; i++) {
        int co1 = program[i][0];
        int co2 = program[i][1];
        for (int j = 0; j < maxState; j++) {
            if ((j & (1 << co1)) == 0 || (j & (1 << co2)) == 0) {
                continue;
            }

            if (dpState[j ^ (1 << co1) ^ (1 << co2)] == INT_MAX) {
                continue;
            }

            dpState[j] = fmin(dpState[j], fmin(dpState[j ^ (1 << co1) ^ (1 << co2)], program[i][2]));
        }
    }

    return dpState[maxState - 1];
}
```


================================================================

0604

第二题
// 曲库系统。
//初始化时给定容量为capacity
//操作：
//
//添加。
//当歌曲已经在曲库中，返回-1；
//当容量没满时，直接添加，返回0；
//当容量满时，选取一曲删除，放入新曲，返回被删除曲号。
//删除规则为：1）. 选取播放次数最少的删除；
//2）. 若播放次数一样少（非零），选取第一次播放早的曲删除
//3）. 若播放次数一样少（零），选取添加进曲库早的删除

//播放
//指定的曲子在曲库，播放一次，返回true；否则返回false

//删除
//指定曲在系统，删除该曲及其播放记录，返回true；否则返回false。
```
public class MusicPlayer {


    MusicPlayer() {

    }

    public int addMusic(int musicId) {

        return -1;
    }



    public boolean playMusic(int musicId) {

        return true;
    }

    public boolean deleteMusic(int musicId) {

        return true;
    }
}
```




====================================================================

0521

第二题
二叉树，标记满足下面条件的节点：若有一条从根节点到叶子节点的路径下，存在一个节点，将该路径上分为总和相等的两部分，那么该节点应被打上标记。比如下图中的3、5、1。返回所有未被标记的节点的总和。

【3】7 + 6 = 13 = 11 +2
【5】7 = 7 = 4 + 3
【1】7 + 5 + 4 = 16 = 16


思路是dfs过程中比较上半段的和和下半段的和，如果有相等的，就累加该节点的值到markSum.递归完成后使用总和sum - markSum就是答案。


```
public class Solution {

public int bisectTreePath(TreeNode root) {
    markTree(root, 0);
    return calTree(root);
}

//递归标记树，对符合条件的节点置 val = 0
private void markTree(TreeNode tree, int sum) {
    if (tree == null) {
        return;
    }
    int val = tree.val;

    //一个节点被标记，则其左或右子树，存在一条路径，根节点到叶子节点的路径节点和等于指定和sum
    if (hasSum(tree.left, sum) || hasSum(tree.right, sum)) {
        tree.val = 0;
    }
    markTree(tree.left, sum + val);
    markTree(tree.right, sum + val);
}

//经典题：二叉树是否存在某条根到叶子节点的路径，其节点和为指定值
private boolean hasSum(TreeNode node, int sum) {
    if (node == null) {
        return false;
    }
    if (node.val == sum && node.left == null && node.right == null) {
        return true;
    }
    return hasSum(node.left, sum - node.val) || hasSum(node.right, sum - node.val);
}

//对标记后的树进行递归求和
private int calTree(TreeNode tree) {
    if (tree == null) {
        return 0;
    }
    if (tree.left == null && tree.right == null) {
        return tree.val;
    }
    return tree.val + calTree(tree.left) + calTree(tree.right);
}
}
```
或者

```
public static int bisectTreePath(TreeNode root) {
    finalS = 0;
    cal(root, 0);
    return finalS;
}

public static void cal(TreeNode root, int sum) {
    if (root.right == null && root.left == null) {
        finalS += root.val;
        return;
    }
    if (!canSum(root, sum) || sum == 0) {
        finalS += root.val;
    }
    if (root.right != null) {
        cal(root.right, sum + root.val);
    }
    if (root.left != null) {
        cal(root.left, sum + root.val);
    }
}

public static boolean canSum(TreeNode root, int sum) {
    if (sum == 0 && root.right == null && root.left == null) {
        return true;
    }
    if ((root.right == null && root.left == null) || sum < 0) {
        return false;
    }
    if (root.right == null) {
        return canSum(root.left, sum - root.left.val);
    } 
    if (root.left == null) {
        return canSum(root.right, sum - root.right.val);
    } 
    return canSum(root.right, sum - root.right.val) || canSum(root.left, sum - root.left.val);
}
```


第三题
专业级第三题：
题目：给定一个M*N的矩阵sensors，在其中找所有边长为cnt的正方形区域内sensors[i][j]的总和最大值，然后将总和最大的正方形区域（可能有多个）内的sensors[i][j]的种类求出来。
其中2 <= cnt <= M,N <= 1000; 0 <= sensors[i][j] <= 10000;

求解：
第一步：构建一个从[0][0]到[i][j]前缀和二维数组，时间复杂度O(M X N),空间复杂度O(M X N)
第二步：遍历[cnt - 1 : M - 1][cnt - 1 : N - 1]区域内的正方形，基于前缀和数组进行计算，主要考虑是正方形的左上角将右下角的前缀和分为了4个区域，做减法可以求解到正方形区域和值，在这个过程中可以得到最大正方形的区域和值；时间复杂度O(M X N)；
第三步：构建空的unordered_set作为ans, 遍历[cnt - 1 : M - 1][cnt - 1 : N - 1]区域，将正方形区域和与最大值相等的区域内sensors[i][j]加入ans，最后返回ans的size；最恶劣情况下时间复杂度为O(M X N X cnt X cnt)，这跟最大值正方形区域的数量以及多个最大值正方形区域的重叠度有关。


该题重点是减少时间复杂度，使用前缀和减少求部分和的计算量。另外，在找到和最大的若干块之后，还要去重。一开始使用了std::set去重，总是超时。后来使用一个线性表记录这些块中出现过的数字，可以通过。后半段去重可能还有更好的方法。


======================================================================

0423
第一题
公司组织每周运动步数的活动，要搞一个榜单，具体规则如下：
1）步数排行第一的上榜
2）多个并列第一并列上榜

首先会进行一个摸底，摸底结果以：baseScores[] 进行表示:
数组下标为员工编号，对应值即为摸底步数
之后每周的变化会用数组ranks来进行表示：
ranks[i,j,k]
i：表示当前是第几周
j：对应的员工编号
k：步数变化，+ 表示增加，- 表示减少；如：+1 表示在上一周的基础上多走了一步，-1 表示在上一周的基础上少走了一步

每周都会对最新的步数进行统计并查看是否会需要更换榜单，
输出：最终的输出结果为榜单变化的次数；

例：baseScores = {6, 7, 6, 5, 9};

ranks = {{1, 4, -1}, {3, 2, 3}, {3, 3, -2}, {1, 1, 1}, {4, 0, 1}};
第几周    baseScores               榜一         变化次数
 摸底     6, 7, 6, 5, 9             4             0
  1       6, 8, 6, 5, 8             1,4           1
  2       6, 8, 6, 5, 8             1,4           1
  3       6, 8, 9, 3, 8             3             2
  4       7, 8, 9, 3, 8             3             2

最终输出变化次数为：2
```
public class Solution {
    public static void main(String[] args) {
        int[] baseScores = {6, 7, 6, 5, 9};
        int[][] ranks = {{1, 4, -1}, {3, 2, 3}, {3, 3, -2}, {1, 1, 1}, {4, 0, 1}};
        System.out.println(changeRanking(baseScores, ranks));

        int[] baseScores2 = {10020, 10010, 10030, 10015, 8000};
        int[][] ranks2 = {{3, 3, 6}, {2, 1, 8}, {4, 3, 7}, {1, 4, 10}, {2, 0, 10}, {1, 2, -5}, {3, 2, -4}, {3, 0, -9}};
        System.out.println(changeRanking(baseScores2, ranks2));
    }

    public static int changeRanking(int[] baseScores, int[][] ranks) {}
}
```
=================================

第二题：
抢票活动，组织会按 int distribute[] 所给定的是时间定时发票（从小到大 有序的），
领票的人到达时间，到达时间按 int arrive[]，（也是从小到大 有序的）
规则：
1）组织到点发票，每次派发的票数为 num 张，
2）只有在排票时间之前到的才能领到票，若改时间点的票没派发完，则本次余票作废
3）若当次票量不足，则按先来后到先发，没得到票的下轮在领

输出：你想要领票，又不想早到排队，就算一下你最晚多久去一定可以领到票
例：distribute = {11, 20} num = 2 arrive = {11, 12, 15, 15, 15}
11 点：只有一人到，发票1张，作废1张 余下的没领到票的人到达时间为：{12, 15, 15, 15}
20 点：剩下的人都是在20点之前到的，按排序，12和一个15领了票，剩下的人就没了
所以如果你最晚应该在15点之前 14点到才能领到票
```
public class Solution {
  public static void main(String[] args) {
      int[] distribute = {11, 20};
      int num = 2;
      int[] arrive = {11, 12, 15, 15, 15};
      System.out.println(latestTime(distribute, num, arrive));

      int[] distribute2 = {13, 20};
      int num2 = 3;
      int[] arrive2 = {11, 11, 11, 12, 16, 16, 17, 18};
      System.out.println(latestTime(distribute2, num2, arrive2));

      int[] distribute3 = {13};
      int num3 = 1;
      int[] arrive3 = {14};
      System.out.println(latestTime(distribute3, num3, arrive3));

      int[] distribute4 = {13, 20};
      int num4 = 100;
      int[] arrive4 = {11, 11, 11, 11, 11, 12, 16, 16, 17, 18};
      System.out.println(latestTime(distribute4, num4, arrive4));
  }
    
  public static int latestTime(int[] distribute, int num, int[] arrive) {

  }
}
```
