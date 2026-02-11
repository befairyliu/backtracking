package com.liu.redleaf;

/**
 * 6. Z字形变换
 * 将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列。
 * 
 * 示例 1：
 * 输入：s = "PAYPALISHIRING", numRows = 3
 * 输出："PAHNAPLSIIGYIR"
 * 解释：
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 * 
 * 示例 2：
 * 输入：s = "PAYPALISHIRING", numRows = 4
 * 输出："PINALSIGYAHRPI"
 * 解释：
 * P     I    N
 * A   L S  I G
 * Y A   H R
 * P     I
 */
public class ZigzagConversion {
    
    public String convert(String s, int numRows) {
        if (numRows == 1 || s.length() <= numRows) {
            return s;
        }
        
        // 创建 numRows 个 StringBuilder 来存储每一行的字符
        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = new StringBuilder();
        }
        
        int currentRow = 0;
        boolean goingDown = false;
        
        // 遍历字符串，按 Z 字形分配到各行
        for (char c : s.toCharArray()) {
            rows[currentRow].append(c);
            
            // 在第一行或最后一行时改变方向
            if (currentRow == 0 || currentRow == numRows - 1) {
                goingDown = !goingDown;
            }
            
            // 根据方向移动到下一行
            currentRow += goingDown ? 1 : -1;
        }
        
        // 合并所有行
        StringBuilder result = new StringBuilder();
        for (StringBuilder row : rows) {
            result.append(row);
        }
        
        return result.toString();
    }
    
    public static void main(String[] args) {
        ZigzagConversion solution = new ZigzagConversion();
        
        // 测试示例1
        String s1 = "PAYPALISHIRING";
        int numRows1 = 3;
        String result1 = solution.convert(s1, numRows1);
        System.out.println("输入：s = \"" + s1 + "\", numRows = " + numRows1);
        System.out.println("输出：\"" + result1 + "\"");
        System.out.println("预期：\"PAHNAPLSIIGYIR\"");
        System.out.println("测试" + (result1.equals("PAHNAPLSIIGYIR") ? "通过" : "失败") + "\n");
        
        // 测试示例2
        String s2 = "PAYPALISHIRING";
        int numRows2 = 4;
        String result2 = solution.convert(s2, numRows2);
        System.out.println("输入：s = \"" + s2 + "\", numRows = " + numRows2);
        System.out.println("输出：\"" + result2 + "\"");
        System.out.println("预期：\"PINALSIGYAHRPI\"");
        System.out.println("测试" + (result2.equals("PINALSIGYAHRPI") ? "通过" : "失败") + "\n");
        
        // 测试边界情况
        String s3 = "A";
        int numRows3 = 1;
        String result3 = solution.convert(s3, numRows3);
        System.out.println("输入：s = \"" + s3 + "\", numRows = " + numRows3);
        System.out.println("输出：\"" + result3 + "\"");
        System.out.println("预期：\"A\"");
        System.out.println("测试" + (result3.equals("A") ? "通过" : "失败"));
    }
}
