package com.liu.redleaf;

public class DBFSLeaf {
    
    //1. leetcode 1162. 地图分析
    // BFS
    public int maxDistance(int[][] grid) {
        //存储每次向四个方向遍历的位移
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};
        Queue<int[]> queue = new ArrayDeque<>();
        int n = grid.length;
        // 先把所有的陆地都入队。
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    queue.offer(new int[] {i, j});
                }
            }
        }
        // 全是海洋或者全是陆地，返回-1。
        if (queue.size() == 0 || queue.size() == n * n) {
            return -1;
        }

        // 从各个陆地开始，一圈一圈的遍历海洋，最后遍历到的海洋就是离陆地最远的海洋。
        int[] point = null;
        while (!queue.isEmpty()) {
            point = queue.poll();
            int x = point[0], y = point[1];
            // 取出队列的元素，将其四周的海洋入队。
            for (int i = 0; i < 4; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];
                if (newX < 0 || newX >= n || newY < 0 || newY >= n || grid[newX][newY] != 0) {
                    //不再执行循环体中continue语句之后的代码，直接进行下一次循环
                    continue;
                }
                grid[newX][newY] = grid[x][y] + 1; // 这里我直接修改了原数组，因此就不需要额外的数组来标志是否访问
                queue.offer(new int[] {newX, newY});
            }
        }
        // 返回最后一次遍历到的海洋的距离。
        return grid[point[0]][point[1]] - 1;
    }
    
    //2. leetcode 面试题13. 机器人的运动范围
    int count = 0;
    // 辅助数组，标记是否统计过
    boolean[][] visited;
    public int movingCount(int m, int n, int k) {
        //init
        visited = new boolean[m][n];
        DFSHelper(0, 0, m, n, k);
        return count;
    }

    // 方法1: DFS
    // 传入i,j两点 判断当前点是否符合规则 符合规则下继续对上下左右四个点递归判断
    public void DFSHelper (int i, int j, int m, int n, int k){
        // 界限判断
        if (0 <= i && i < m && 0 <= j && j < n && !visited[i][j] && (bitSum(i) + bitSum(j) <= k)) {
            // 标记访问过了
            count++;
            visited[i][j] = true;
            // 上下左右递归
            DFSHelper(i, j - 1, m, n, k);
            DFSHelper(i, j + 1, m, n, k);
            DFSHelper(i - 1, j, m, n, k);
            DFSHelper(i + 1, j, m, n, k);
        }
    }

    // 数位之和计算
    public int bitSum(int n){
        int sum = 0;
        while(n > 0){
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }

    // 方法2: BFS
    public int movingCountII(int m, int n, int k) {
        //队列保存坐标
        Queue<int[]> queue = new LinkedList<>();
        //init
        boolean[][] visited = new boolean[m][n];
        int count = 0;
        // 坐标数组
        int[] dx = { -1, 0, 1, 0 };
        int[] dy = { 0, 1, 0, -1 };
        // 坐标{0,0}入队列
        queue.offer(new int[] {0, 0});

        while(!queue.isEmpty()){
            int[] point = queue.poll();
            int cx = point[0];
            int cy = point[1];

            if(visited[cx][cy] || (bitSum(cx) + bitSum(cy)) > k){
                continue;
            }

            count++;
            visited[cx][cy] = true;
            // 边界判断, 上下左右遍历判断加入队列
            for (int i = 0; i < 4; i++) {
                int x = cx + dx[i];
                int y = cy + dy[i];
                if (0 <= x && x < m && 0 <= y && y < n) {
                    queue.offer(new int[] {x, y});
                }
            }
        }

        return count;
    }

    //3. leetcode 22. 括号生成
    // 每一个括号序列可以用 (a)b 来表示，其中 a 与 b 分别是一个合法的括号序列（可以为空）
    // 递归算法
    ArrayList[] cache = new ArrayList[100];
    public List<String> generateParenthesis(int n) {
        return generate(n);
    }

    public List<String> generate(int n) {
        if (cache[n] != null) {
            return cache[n];
        }
        ArrayList<String> ans = new ArrayList();
        if (n == 0) {
            ans.add("");
        } else {
            for (int c = 0; c < n; ++c)
                for (String left: generate(c))
                    for (String right: generate(n - 1 - c))
                        ans.add("(" + left + ")" + right);
        }
        cache[n] = ans;
        return ans;
    }

    //4. leetcode79 & 剑指 Offer 12. 矩阵中的路径
    // 判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。路径可以从矩阵中的任意一格开始，每一步可以在矩阵中向左、右、上、下移动一格。
    // 如果一条路径经过了矩阵的某一格，那么该路径不能再次进入该格子。
    //
    public boolean exist(char[][] board, String word) {
        char[] chs = word.toCharArray();
        // 遍历矩阵
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (existDFS(board, chs, i, j, 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    // 递归套路：
    // 1. 先判断终止条件
    // 2. 访问修改或其他内容操作
    // 3. 根据题意进行递归操作
    // 4. 还原元素内容
    private boolean existDFS(char[][] board, char[] chs, int i, int j, int k){
        // 1. 先判断终止条件
        // 1.1: (1) 行或列索引越界 或 (2) 当前矩阵元素与目标字符不同 或 (3) 当前矩阵元素已访问过
        if (i < 0 || i > board.length
            || j < 0 || j > board[0].length
            || board[i][j] != chs[k]){
            return false;
        }

        //  1.2: (4) 全部匹配，返回 true
        if (k == chs.length - 1){
            return true;
        }

        // 2. 访问过后，修改字符为空，代表元素已经访问过，防止搜索时重复访问
        board[i][j] = ' ';

        // 3. 递归搜索下一个字符
        // 朝当前元素的 上、下、左、右 四个方向开启下层递归
        boolean res = existDFS(board, chs, i - 1, j, k + 1)
            || existDFS(board, chs, i + 1, j, k + 1)
            || existDFS(board, chs, i, j - 1, k + 1)
            || existDFS(board, chs, i, j + 1, k + 1);

        // 4. 复原当前矩阵元素
        // 将 board[i][j] 元素还原至初始值，即 chs[k]
        board[i][j] = chs[k];

        return res;
    }

    
    public static void main(String[] args) {
//        Scanner cin = new Scanner(System.in, StandardCharsets.UTF_8.name());
//
//        int length = cin.nextInt();
//        int timeLimit = cin.nextInt();
//
//        int[][] officeLayout = new int[length][];
//        for (int i = 0; i < length; i++) {
//            officeLayout[i] = new int[length];
//            for (int j = 0; j < length; j++) {
//                officeLayout[i][j] = cin.nextInt();
//            }
//        }
//        cin.close();
        DBFSLeaf instance = new DBFSLeaf();

        System.out.println("The result: "+ instance.movingCountII(3,2,6));
    }

}
