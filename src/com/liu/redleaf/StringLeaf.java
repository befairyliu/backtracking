package com.liu.redleaf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringLeaf {
    //1. 1071. 字符串的最大公因子
    public String gcdOfStrings(String str1, String str2){

        String temp1 = str1 + str2;
        String temp2 = str2 + str1;
        if(!(str1 + str2).equals(str2 + str1)){
            return "";
        }

        int len1 = str1.length();
        int len2 = str2.length();
        // 获取最大公约数
        int count = gcd(len1, len2);
        String result = str1.substring(0, count);

        return result;
    }

    private int gcd(int a, int b){
        // 求两个数的最大公约数
        if(a % b == 0){
            return b;
        } else {
            return gcd(b, a % b);
        }
    }

    //2. leetcode compress string
    public String compressString(String S){
        if(S.isEmpty()){
            return "";
        }

        StringBuilder sb = new StringBuilder();
        char[] chars = S.toCharArray();
        int count = 1;
        char ch = chars[0];
        for(int i=1; i < chars.length; i++){
            if(ch == chars[i]){
                count++;
            } else {
                sb.append(ch).append(count);
                ch = chars[i];
                count = 1;
            }
        }

        //add the last char
        sb.append(ch).append(count);

        return sb.length() < chars.length ? sb.toString() : S;
    }

    //3 leetcode 468  验证IP地址 -- 快手
    public String validateIPAddress(String IP){
        if(IP.chars().filter(ch -> ch == '.').count() == 3){
            return validateIPv4(IP);
        } else if(IP.chars().filter(ch -> ch == ':').count() == 7){
            return validateIPv6(IP);
        } else {
            return "Neither";
        }
    }

    public String validateIPv4(String IP){
        String[] nums = IP.split("\\.", -1);
        for(String str : nums){
            // validate integer in range (0, 255):
            //1. the length is between 1 and 3
            if(str.length() == 0 || str.length() > 3){
                return "Neither";
            }
            //2. no extra leading zeros
            if(str.charAt(0) == '0' && str.length() != 1){
                return "Neither";
            }
            //3. only digits are allowed
            for (char ch: str.toCharArray()) {
                if( !Character.isDigit(ch)){
                    return "Neither";
                }
            }

            //4. less than 255
            if(Integer.parseInt(str) > 255){
                return "Neither";
            }
        }
        return "IPv4";
    }

    public String validateIPv6(String IP){
        String[] nums = IP.split(":", -1);
        String hexDigits = "0123456789abcdefABCDEF";
        for(String str : nums){
            // validate hexadecimal in range(0, 2**16)
            // 1. at least one and not more than 4 hexdigits in one chunk
            if (str.length() == 0 || str.length() > 4) {
                return "Neither";
            }
            // 2. only hexdigits are allowed: 0-9, a-f, A-F
            for(Character ch: str.toCharArray()){
                if(hexDigits.indexOf(ch) == -1){
                    return "Neither";
                }
            }
        }

        return "IPv6";
    }

    //4. leetcode 165 比较版本号
    public int compareVersion(String version1, String version2) {
        if(version1 == null || version2 == null){
            return 0;
        }

        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");

        for(int i = 0; i < Math.max(v1.length, v2.length); i++){
            int a1 = i < v1.length ? Integer.valueOf(v1[i]) : 0;
            int b2 = i < v2.length ? Integer.valueOf(v2[i]) : 0;
            if(a1 < b2){
                return -1;
            } else if(a1 > b2){
                return 1;
            }
        }
        return 0;
    }

    //5. leetcode 17. 电话号码的字母组合
    //Method: backtracking
    Map<String, String> phoneMap = new HashMap<String, String>() {{
        put("2", "abc");
        put("3", "def");
        put("4", "ghi");
        put("5", "jkl");
        put("6", "mno");
        put("7", "pqrs");
        put("8", "tuv");
        put("9", "wxyz");
    }};
    List<String> output = new ArrayList<String>();

    public void backtrack(String combination, String nextDigits){
        if(nextDigits.length() == 0){
            output.add(combination);
        } else {
            String digit = nextDigits.substring(0, 1);
            String letters = phoneMap.get(digit);
            for(int i=0; i< letters.length(); i++){
                String letter = phoneMap.get(digit).substring(i, i+1);
                //backtrack
                backtrack(combination+letter, nextDigits.substring(1));
            }
        }
    }

    public List<String> letterCombinations(String digits) {
        //check
        if(digits != null && digits.length() > 0){
            backtrack("", digits);
        }

        return output;
    }

    
    //6. leetcode 最长回文串
    public int longestPalindrome(String s){
        //check
        if(s == null || s.isEmpty()){
            return 0;
        }

        //统计字符出现的次数
        int[] counts = new int[128];
        for(char c : s.toCharArray()){
            counts[c]++;
        }

        int result = 0;
        for(int count: counts){
            result = result + count / 2 * 2;
            //首次字符是奇数次时+1 （回文的中间对称中心）
            if(count % 2 == 1 && result %2 == 0){
                result++;
            }
        }

        return result;
    }

    //7. leetcode 面试题 01.09. 字符串轮转
    public boolean isFlipedString(String s1, String s2) {
        if(s1.length() != s2.length()){
            return false;
        }

        if(s1.equals(s2)){
            return true;
        }

        s1 += s1;
        return s1.contains(s2);
    }
    
    // 8. leetcode 65 面试题20. 表示数值的字符串
    public boolean isNumber(String s) {
        if(s == null || s.length() == 0){
            return false;
        }
        //标记是否遇到相应情况
        boolean numSeen = false;
        boolean dotSeen = false;
        boolean eSeen = false;
        char[] str = s.trim().toCharArray();
        for(int i = 0;i < str.length; i++){
            if(str[i] >= '0' && str[i] <= '9'){
                numSeen = true;
            } else if(str[i] == '.'){
                //.之前不能出现.或者e
                if(dotSeen || eSeen){
                    return false;
                }
                dotSeen = true;
            } else if(str[i] == 'e' || str[i] == 'E'){
                //e之前不能出现e，必须出现数
                if(eSeen || !numSeen){
                    return false;
                }
                eSeen = true;
                numSeen = false;//重置numSeen，排除123e或者123e+的情况,确保e之后也出现数
            } else if(str[i] == '-' || str[i] == '+'){
                //+-出现在0位置或者e/E的后面第一个位置才是合法的
                if(i != 0 && str[i-1] != 'e' && str[i-1] != 'E'){
                    return false;
                }
            } else {//其他不合法字符
                return false;
            }
        }
        return numSeen;
    }

    //9. leetcode 1111. 有效括号的嵌套深度
    // 奇数层的 ( 分配给 A，偶数层的 ( 分配给 B
    public int[] maxDepthAfterSplit(String seq){

        int[] res = new int[seq.length()];
        int index = 0;
        int depth = 0;
        for(char c : seq.toCharArray()){
            if(c == '('){
                // 当前深度增加
                depth += 1;
                res[index++] = depth % 2;
            } else if(c == ')'){
                res[index++] = depth % 2;
                depth -= 1;
            }
        }

        return res;
    }

    //10. leetcode 8. 字符串转换整数 (atoi)
    public int String2Int(String str) {
        str = str.trim();
        if(str.length() == 0){
            return 0;
        }
        char[] chars = str.toCharArray();
        int length = chars.length;
        int idx = 0;
        boolean isNegative = false;
        if(chars[idx] == '-') {
            // 负号
            isNegative = true;
            idx++;
        } else if(chars[idx] == '+') {
            // 正号
            idx++;
        } else if( !Character.isDigit(chars[idx])){
            // 其他符号
            return 0;
        }

        int res = 0;
        while(idx < length && Character.isDigit(chars[idx])){
            int digit = chars[idx] - '0';
            if(res > (Integer.MAX_VALUE - digit) / 10 ){
                // 本来应该是 res * 10 + digit > Integer.MAX_VALUE
                return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }

            res = res * 10 + digit;
            idx++;
        }

        if(isNegative){
            res = -res;
        }

        return res;
    }

    //11. leetcode 151. 翻转字符串里的单词
//    public String reverseWords(String s) {
//        String[] strs = s.trim().split(" "); // 删除首尾空格，分割字符串
//        StringBuilder res = new StringBuilder();
//        for(int i = strs.length - 1; i >= 0; i--) { // 倒序遍历单词列表
//            if(strs[i].equals("")) continue; // 遇到空单词则跳过
//            res.append(strs[i] + " "); // 将单词拼接至 StringBuilder
//        }
//        return res.toString().trim(); // 转化为字符串，删除尾部空格，并返回
//    }

    public String reverseWords(String s) {
        String[] strs = s.trim().split(" ");
        StringBuilder res = new StringBuilder();
        int count = strs.length - 1;
        while(count >= 0){
            if(strs[count].equals("")){
                count--;
                continue;
            }
            res.append(strs[count--] + " ");
        }

        //移除尾部空格，并返回
        return res.toString().trim();
    }

    // 方法二：双端队列
    public String reverseWordsII(String s) {
        int left = 0, right = s.length() - 1;
        // 去掉字符串开头的空白字符
        while (left <= right && s.charAt(left) == ' ') ++left;

        // 去掉字符串末尾的空白字符
        while (left <= right && s.charAt(right) == ' ') --right;

        Deque<String> d = new ArrayDeque();
        StringBuilder word = new StringBuilder();

        while (left <= right) {
            char c = s.charAt(left);
            if ((word.length() != 0) && (c == ' ')) {
                // 将单词 push 到队列的头部
                d.offerFirst(word.toString());
                word.setLength(0);
            } else if (c != ' ') {
                word.append(c);
            }
            ++left;
        }
        d.offerFirst(word.toString());

        return String.join(" ", d);
    }

    // 12. leetcode 402. 移掉K位数字, 使剩下数最小
    // 给定一个以字符串表示的非负整数 num，移除这个数中的 k 位数字，使得剩下的数字最小。
    // 输入: num = "1432219", k = 3
    // 输出: "1219"
    // 解释: 移除掉三个数字 4, 3, 和 2 形成一个新的最小的数字 1219。
    // 输入: num = "10200", k = 1
    // 输出: "200"
    // 解释: 移掉首位的 1 剩下的数字为 200. 注意输出不能有任何前导零。
    public String removeKdigits(String num, int k) {
        String res = "";
        char[] chars = num.toCharArray();
        Stack<Character> stack = new Stack();
        stack.push(chars[0]);
        // 1. 当前一个数字比当前数字大的时，则移除
        int count = 0; // 当前移除数字个数
        for (int i = 1; i < chars.length; i++) {
            while (!stack.isEmpty() && stack.peek() > chars[i] && count < k) {
                stack.pop();
                count++;
            }

            // 处理前导零, 并加入栈中
            if (!stack.isEmpty() || chars[i] != '0') {
                stack.push(chars[i]);
            }
        }

        // 2.假如移除的数据少于k个，则移除最后k-count个不为0的数
        if (count < k) {
            while (count < k && !stack.isEmpty()) {
                stack.pop();
                count++;
            }
        }
        // 3.拼接成字符串
        while (!stack.isEmpty()) {
            res = stack.pop() + res;
        }

        return res.length() > 0 ? res : "0";
    }

    // 13. leetcode 面试题 01.01 判定字符是否唯一
    // 实现一个算法，确定一个字符串 s 的所有字符是否全都不同。
    // 输入: s = "leetcode"
    // 输出: false
    // 0 <= len(s) <= 100
    //如果你不使用额外的数据结构，会很加分。
    public boolean isUnique(String str) {
//        // Method 1:
//        // check input params
//        if (str == null || str.length() <= 1) return true;
//        // iteration the str
//        for (int i = 0; i < str.length(); i++) {
//            for (int j = i + 1; j < str.length(); j++) {
//                if (str.charAt(i) == str.charAt(j)) return false;
//            }
//        }

        // Method 2:
        for (int i = 0; i < str.length(); i++) {
            if (str.indexOf(str.charAt(i)) != i) {
                return false;
            }
        }

        return true;
    }

    // 14. leetcode 316.去除重复字母
    // 给你一个仅包含小写字母的字符串，请你去除字符串中重复的字母，
    // 使得每个字母只出现一次。需保证返回结果的字典序最小（要求不能打乱其他字符的相对位置）。
    // 输入: "cbacdcbc"
    // 输出: "acdb"
    // 方法1： 单调栈
    public String removeDuplicateLetters(String s) {
        // 1.入参检查
        if (s.isEmpty() || s.length() < 2) {
            return s;
        }

        // 2.统计字符出现次数
        int[] count = new int[26];
        char[] chars = s.toCharArray();
        for (char c : chars) {
            count[c - 'a']++;
        }

        // 3.set记录字符添加情况
        HashSet<Character> set = new HashSet<>();
        Stack<Character> stack = new Stack<>();

        // 4.遍历所有字符，并进行字典排序入栈
        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            if (!set.contains(cur)) {
                while (!stack.isEmpty() && cur < stack.peek() && count[stack.peek() - 'a'] > 0) {
                    set.remove(stack.pop());
                }
                stack.push(cur);
                set.add(cur);
            }
            //每遍历一个元素，操作完成，数值减一
            count[cur - 'a']--;
        }

        // 5.字符串拼接
        StringBuilder res = new StringBuilder();
        while (!stack.isEmpty()) {
            res.insert(0, stack.pop());
        }

        return res.toString();
    }

    //=======================================
    public static void main(String[] args){

        StringLeaf instance = new StringLeaf();

        String str1 = "ABCABC";
        String str2 = "ABC";

        //System.out.println("The gcd string: "+ instance.gcdOfStrings(str1, str2));

        String S = "abbccd";
        System.out.println("The compress string: "+ instance.compressString(S));
    }
}
