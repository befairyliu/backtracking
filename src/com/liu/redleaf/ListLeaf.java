package com.liu.readleaf;

import java.util.ArrayList;
import java.util.Stack;

public class ListLeaf {

    // efinition for singly-linked list.
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }



    // 1 leetcode 206 链表反转
    // 1.1 Method 1 iteration
    public static ListNode reverseList(ListNode head){
        ListNode target = null;
        ListNode current = head;

        while(current != null){
            ListNode tempNode = current.next;
            current.next = target;
            target = current;
            current = tempNode;
        }
        return target;
    }

    //1.2 Method 2 recursion
    public static ListNode reverseList2(ListNode head){
        if(head == null || head.next == null){
            return head;
        }

        // 返回的node节点已经把next的关系都梳理链接好了
        ListNode node = reverseList2(head.next);
        //head的下一个节点指向head
        head.next.next = head;
        //切断head原先的链路指向
        head.next = null;

        return node;
    }

    //2. leetcode 92 反转从位置 m 到 n 的链表。请使用一趟扫描完成反转
    //
    public static ListNode reverseBetween(ListNode head, int m, int n) {
        if(head == null || head.next == null || m == n){
            return head;
        }

        ListNode pre = null; //存储反转节点的上一个节点
        ListNode current = head; //当前节点
        // 往后移动M个节点
        while (m > 1){
            pre = current;
            current = current.next;
            m--;
            n--;
        }

        // The two pointers that will fix the final connections.
        ListNode headTail = pre; //存储m位置之前的位置, 第一段末尾节点
        ListNode tail = current;
        //反转从reverse开始的n-m个节点
        ListNode third = null;
        while(n > 0){
            ListNode tempNode = current.next;
            current.next = third;
            third = current;
            current = tempNode;
            n--;
        }

        // connection
        // 把最后的尾巴节点直接拼接到反转开始的m节点上
        if(headTail == null){
            //return third;
            head = third;
            tail.next = current;
        } else {
            // 把最后的尾巴节点直接拼接到反转开始的m节点
            headTail.next.next = current;
            // 把反转后的节点直接拼接到m位置之前的节点上
            headTail.next = third;
        }

        return head;
    }

    //3. leetcode 83 删除排序链表中的重复元素
    public static ListNode deleteDuplication(ListNode head){
        // check condition
        if(head == null || head.next == null){
            return head;
        }

        ListNode current = head;
        while(current != null){
            if(current.next != null && current.val == current.next.val){
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }
        return head;
    }

    //4. leetcode 82 删除排序链表中的重复元素,只保留原始链表中没有重复出现的数字
    public static ListNode deleteDuplicates(ListNode head) {
        // check condition
        if(head == null || head.next == null){
            return head;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode pre = dummy;
        ListNode current = dummy.next;

        while(current != null && current.next != null){
            if(current.val == current.next.val){
                //内循环找到最后一个重复元素
                pre.next = findNext(current);
            } else {
                pre = pre.next;
            }
            current = pre.next;
        }
        return dummy.next;
    }

    public static ListNode findNext(ListNode current){

        ListNode next = current.next;
        while (next != null && current.val == next.val){
            next = next.next;
        }

        return next;
    }

    // 5. leetcode 1019. 链表中的下一个更大节点
    public int[] nextLargerNodes(ListNode head){
        if(head == null){
            return null;
        }

        //1. list save the ListNode
        ArrayList<Integer> list = new ArrayList<>();
        ListNode current = head;
        while (current != null){
            list.add(current.val);
            current = current.next;
        }

        //2. stack存储对应list元素及其之后的元素中最大的值
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[list.size()];
        //3. 循环查找，在 list 中从后往前遍历，stack 中凡是比 list.get(i) 小的都 pop 出去
        // 这样stack 剩下的元素都是比 list.get(i) 更大的元素。
        for(int i=list.size()-1; i>=0; i--){
            while(!stack.isEmpty() && stack.peek() <= list.get(i)){
                stack.pop();
            }

            res[i] = stack.isEmpty() ? 0 : stack.peek();
            stack.push(list.get(i));
        }
        return res;
    }


    public static void main(String[] args){
        ListNode head = null;;
        ListNode last = null;
//        for(int i = 1; i < 6; i++){
//            ListNode temp = new ListNode(i);
//            temp.next = null;
//
//            if(head == null){
//                head = temp;
//                last = head;
//            } else {
//                last.next = temp;
//                last = temp;
//            }
//        }


//        head = new ListNode(1);
//        ListNode current = head;
//        current.next = new ListNode(2);
//        current = current.next;
//        current.next = new ListNode(3);
//        current = current.next;
//        current.next = new ListNode(3);
//        current = current.next;
//        current.next = new ListNode(4);
//        current = current.next;
//        current.next = new ListNode(4);
//        current = current.next;
//        current.next = new ListNode(5);
//        ListNode rever = new ListLeaf().reverseList2(head);
//        ListNode rever = reverseList(head);
//        ListNode rever = reverseBetween(head, 1,2);
//        System.out.println("reverse list: "+rever);


//        head = new ListNode(1);
//        ListNode current = head;
//        current.next = new ListNode(1);
//        current = current.next;
//        current.next = new ListNode(3);
//        current = current.next;
//        current.next = new ListNode(3);
//        current = current.next;
//        current.next = new ListNode(4);
//        System.out.println("deleteDuplication : "+deleteDuplicates(head));

        head = new ListNode(2);
        ListNode current = head;
        current.next = new ListNode(7);
        current = current.next;
        current.next = new ListNode(4);
        current = current.next;
        current.next = new ListNode(3);
        current = current.next;
        current.next = new ListNode(5);
        current = current.next;
        current.next = new ListNode(6);
        ListLeaf instance = new ListLeaf();
        System.out.println("nextLargerNodes : "+instance.nextLargerNodes(head));
    }


}
