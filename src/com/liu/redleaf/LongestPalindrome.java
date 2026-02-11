package com.liu.redleaf;

/**
 * 最长回文子串
 * 给定一个字符串 s，找到 s 中最长的回文子串
 */
public class LongestPalindrome {
    
    /**
     * 中心扩展法
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        
        int start = 0;
        int end = 0;
        
        for (int i = 0; i < s.length(); i++) {
            // 以单个字符为中心扩展（奇数长度回文）
            int len1 = expandAroundCenter(s, i, i);
            // 以两个字符之间为中心扩展（偶数长度回文）
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        
        return s.substring(start, end + 1);
    }
    
    /**
     * 从中心向两边扩展，返回回文串长度
     */
    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }
    
    public static void main(String[] args) {
        LongestPalindrome solution = new LongestPalindrome();
        
        // 测试示例1
        String s1 = "babad";
        System.out.println("输入：" + s1);
        System.out.println("输出：" + solution.longestPalindrome(s1));
        
        // 测试示例2
        String s2 = "cbbd";
        System.out.println("\n输入：" + s2);
        System.out.println("输出：" + solution.longestPalindrome(s2));
        
        // 额外测试
        String s3 = "a";
        System.out.println("\n输入：" + s3);
        System.out.println("输出：" + solution.longestPalindrome(s3));
        
        String s4 = "ac";
        System.out.println("\n输入：" + s4);
        System.out.println("输出：" + solution.longestPalindrome(s4));
    }
}
