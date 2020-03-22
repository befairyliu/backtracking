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

    public static void main(String[] args){

    }
}
