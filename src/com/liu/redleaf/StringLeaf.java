

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
