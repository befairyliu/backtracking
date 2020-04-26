package com.liu.redleaf;

import static com.liu.redleaf.ToolLeaf.gcd;

//本类主要解决数学类的题目
public class MathLeaf {

    //1. leetcode 365. 水壶问题 -- 最大公约数问题
    public static boolean canMeasureWater(int x, int y, int z){
        //check condition
        //x + y 怎么装都装不满z
        if(x + y < z){
            return false;
        }

        // z的容量跟x或y一样
        if(z == x || z == y || z == x + y){
            return true;
        }

        // 其他情况就是最大公约数问题了
        return z % gcd(x, y) == 0;
    }
    
    //2. 约瑟夫环——公式法（递推公式）
    // f(N,M)=(f(N−1,M)+M)%N
    public int lastRemaining(int n, int m) {
        int pos = 0;
        for (int i = 2; i <= n; i++) {
            pos = (pos + m) % i;
        }
        return pos + 1;
    }

    // 链表法
    public int lastRemainingII(int n, int m) {
        ArrayList<Integer> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            list.add(i);
        }
        int idx = 0;
        while (n > 1) {
            idx = (idx + m - 1) % n;
            list.remove(idx);
            n--;
        }
        return list.get(0);
    }

    public static void main(String[] args){

    }
}
