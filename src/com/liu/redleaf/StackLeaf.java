package com.liu.redleaf;

import java.util.ArrayList;
import java.util.Stack;

// leetcode 面试题 03.03. 堆盘子
// 实现栈的push\pop\popAt功能
public class StackLeaf {
    ArrayList<Stack<Integer>> stacks = new ArrayList<>();
    int cap; //每个栈的容量

    public StackLeaf(int cap){
        this.cap = cap;
    }

    public void push(int value){
        if(this.cap != 0){
            if(stacks.size() == 0  || stacks.get(stacks.size() - 1).size() == cap){
                Stack stack = new Stack();
                stack.push(value);
                stacks.add(stack);
            } else {
                stacks.get(stacks.size() - 1).push(value);
            }
        }
    }

    public int pop() {
        if(stacks.size() == 0){
            return -1;
        }

        int value = stacks.get(stacks.size() - 1).pop();
        //假如有空栈则删除空栈
        if(stacks.get(stacks.size() - 1).size() == 0){
            stacks.remove(stacks.size() - 1);
        }

        return value;
    }

    public int popAt(int index){
        if(stacks.size() == 0 || index >= stacks.size()){
            return -1;
        }

        int value = stacks.get(index).pop();
        if(stacks.get(index).size() == 0){
            stacks.remove(index);
        }

        return value;
    }
}
