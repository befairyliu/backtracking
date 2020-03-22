package com.liu.redleaf;

public class ToolLeaf {

    //辗转相除法, 获取最大公约数
    public static int gcd(int a, int b){
        if(b == 0){
            return a;
        } else {
            //recursion
            return gcd(b, a % b);
        }
    }
}