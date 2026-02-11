package com.liu.redleaf;

/**
 * 7. 整数反转
 * 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
 * 如果反转后整数超过 32 位的有符号整数的范围 [−2^31, 2^31 − 1] ，就返回 0。
 * 
 * 示例 1：
 * 输入：x = 123
 * 输出：321
 * 
 * 示例 2：
 * 输入：x = -123
 * 输出：-321
 * 
 * 示例 3：
 * 输入：x = 120
 * 输出：21
 */
public class ReverseInteger {
    
    public int reverse(int x) {
        int result = 0;
        
        while (x != 0) {
            int digit = x % 10;
            x /= 10;
            
            // 检查是否会溢出
            // Integer.MAX_VALUE = 2147483647
            // Integer.MIN_VALUE = -2147483648
            if (result > Integer.MAX_VALUE / 10 || 
                (result == Integer.MAX_VALUE / 10 && digit > 7)) {
                return 0;
            }
            if (result < Integer.MIN_VALUE / 10 || 
                (result == Integer.MIN_VALUE / 10 && digit < -8)) {
                return 0;
            }
            
            result = result * 10 + digit;
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        ReverseInteger solution = new ReverseInteger();
        
        // 测试示例1
        int x1 = 123;
        int result1 = solution.reverse(x1);
        System.out.println("输入：x = " + x1);
        System.out.println("输出：" + result1);
        System.out.println("预期：321");
        System.out.println("测试" + (result1 == 321 ? "通过" : "失败") + "\n");
        
        // 测试示例2
        int x2 = -123;
        int result2 = solution.reverse(x2);
        System.out.println("输入：x = " + x2);
        System.out.println("输出：" + result2);
        System.out.println("预期：-321");
        System.out.println("测试" + (result2 == -321 ? "通过" : "失败") + "\n");
        
        // 测试示例3
        int x3 = 120;
        int result3 = solution.reverse(x3);
        System.out.println("输入：x = " + x3);
        System.out.println("输出：" + result3);
        System.out.println("预期：21");
        System.out.println("测试" + (result3 == 21 ? "通过" : "失败") + "\n");
        
        // 测试溢出情况
        int x4 = 1534236469;
        int result4 = solution.reverse(x4);
        System.out.println("输入：x = " + x4);
        System.out.println("输出：" + result4);
        System.out.println("预期：0（溢出）");
        System.out.println("测试" + (result4 == 0 ? "通过" : "失败"));
    }
}
