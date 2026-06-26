package DynamicProgramming;

import java.util.Arrays;

class DpOnStringsQuestions{
    public int LCS(String str1, String str2){
        int m = str1.length();
        int n = str2.length();

        int[][] dp = new int[m+1][n+1];
        for(int i = 0; i <= m; i++){
            Arrays.fill(dp[i], -1);
        }

        return LCSHelper(str1, str2, m-1, n-1, dp);
    }

    private int LCSHelper(String str1, String str2, int i, int j, int[][] dp){
        if(i < 0 || j < 0) {
            return 0;
        }

        if(dp[i][j] != -1) return dp[i][j];

        if(str1.charAt(i) == str2.charAt(j)){
            return dp[i][j] =  1 + LCSHelper(str1, str2, i-1, j-1, dp);
        }
        else{
            return dp[i][j] = Math.max(LCSHelper(str1, str2, i, j-1, dp), LCSHelper(str1, str2, i-1, j, dp));
        }
    }

    public int LCSTabulation(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();

        int[][] dp = new int[m+1][n+1];


        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){
                if(str1.charAt(i-1) == str2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }else{
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

        return dp[m][n];
    }

    public int LCSSpaceOptimization(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();

        int[] dp = new int[n+1];


        for(int i = 1; i <= m; i++){
            int[] curr = new int[n + 1];
            for(int j = 1; j <= n; j++){
                if(str1.charAt(i-1) == str2.charAt(j-1)){
                    curr[j] = dp[j-1] + 1;
                }else{
                    curr[j] = Math.max(dp[j], curr[j-1]);
                }
            }

            dp = curr;
        }

        return dp[n];
    }

    public String LCSPrintString(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();

        int[][] dp = new int[m+1][n+1];

        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){
                if(str1.charAt(i-1) == str2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }else{
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        int i = m;
        int j = n;

        while(i >= 1 && j >= 1) {
            if(str1.charAt(i-1) == str2.charAt(j-1)){
                sb.append(str1.charAt(i-1));
                i--;
                j--;
            } else if(dp[i-1][j] > dp[i][j-1]){
                i--;
            }else{
                j--;
            }
        }

        return sb.reverse().toString();
    }

    public int LCSubstring(String s1,  String s2) {
        int m = s1.length();
        int n = s2.length();

        int[][] dp = new int[m + 1][n + 1];
        int max = 0;

        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++) {
                if(s1.charAt(i-1) == s2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }else{
                    dp[i][j] = 0;
                }

                max = Math.max(max, dp[i][j]);
            }
        }

        return max;
    }

    public int LCSubstringSpaceOptimization(String s1,  String s2) {
        int m = s1.length();
        int n = s2.length();

        int[] dp = new int[n + 1];
        int max = 0;

        for(int i = 1; i <= m; i++){
            int[] curr = new int[n + 1];
            for(int j = 1; j <= n; j++) {
                if(s1.charAt(i-1) == s2.charAt(j-1)){
                    curr[j] = dp[j-1] + 1;
                }else{
                    curr[j] = 0;
                }

                max = Math.max(max, curr[j]);
            }

            dp = curr;
        }

        return max;
    }

    public int LongestPalindromicSubsequence(String s) {
        int n = s.length();
        String reversed = new StringBuilder(s).reverse().toString();

        int[][]  dp = new int[n+1][n+1];
        for(int i = 0; i <= n; i++){
            Arrays.fill(dp[i], -1);
        }

        return palindromicSubsequenceHelper(s, reversed, n-1, n-1, dp);
    }

    private int palindromicSubsequenceHelper(String str1, String str2, int i, int j, int[][] dp){
        if(i < 0 || j < 0) return 0;

        if(dp[i][j] != -1) return dp[i][j];

        if(str1.charAt(i) == str2.charAt(j)){
            return dp[i][j] =  1 + palindromicSubsequenceHelper(str1, str2, i-1, j-1, dp);
        }else{
            return dp[i][j] = Math.max(palindromicSubsequenceHelper(str1, str2, i - 1, j, dp), palindromicSubsequenceHelper(str1, str2, i, j-1, dp));
        }
    }

    public int LongestPalindromeTabulation(String s) {
        int n = s.length();
        String reversed = new StringBuilder(s).reverse().toString();

        int[][]  dp = new int[n+1][n+1];

        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= n; j++){
                if(s.charAt(i-1) == reversed.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }else{
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

        return dp[n][n];
    }

    public int LongestPalindromeSpaceOptimization(String s) {
        int n = s.length();
        String reversed = new StringBuilder(s).reverse().toString();

        int[]  dp = new int[n+1];

        for(int i = 1; i <= n; i++) {
            int[] curr =  new int[n+1];
            for(int j =  1; j <= n; j++) {
                if(s.charAt(i-1) == reversed.charAt(j-1)){
                    curr[j] = dp[j-1] + 1;
                }else{
                    curr[j] = Math.max(curr[j-1], dp[j]);
                }
            }
            dp = curr;
        }

        return  dp[n];
    }

    public int LongestInsertionPalindromeTabulation(String s) {
        int n = s.length();
        String reversed = new StringBuilder(s).reverse().toString();

        int[][]  dp = new int[n+1][n+1];

        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= n; j++){
                if(s.charAt(i-1) == reversed.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }else{
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

        return n - dp[n][n];
    }

    public int MinimumOperationsString1to2(String str1,  String str2) {
        int m = str1.length();
        int n = str2.length();

        int[][] dp = new int[m+1][n+1];

        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){
                if(str1.charAt(i-1) == str2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }else{
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

        return (m - dp[m][n]) + (n - dp[m][n]);
    }

    public int ShortestCommonSupersequence(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();

        int[][] dp = new int[m+1][n+1];

        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){
                if(str1.charAt(i-1) == str2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }
                else{
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

        return m + n - dp[m][n];
    }

    public String PrintShortestCommonSupersequence(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();

        int[][] dp = new int[m+1][n+1];

        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){
                if(str1.charAt(i-1) == str2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }
                else{
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

       StringBuilder sb = new StringBuilder();
        int i = m;
        int j = n;

        while(i >= 1 && j >= 1){
            if(str1.charAt(i - 1) == str2.charAt(j - 1)){
                sb.append(str1.charAt(i - 1));
                i--;
                j--;
            }
            else if(dp[i - 1][j] > dp[i][j - 1]){
                sb.append(str1.charAt(i - 1));
                i--;
            }else{
                sb.append(str2.charAt(j - 1));
                j--;
            }
        }

        while(i >= 1){
            sb.append(str1.charAt(i-1));
            i--;
        }

        while(j >= 1){
            sb.append(str2.charAt(j-1));
            j--;
        }

        return sb.reverse().toString();
    }

    public int LongestRepeatingSubsequence(String s) {
        int n = s.length();

        int[][] dp = new int[n+1][n+1];
        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= n; j++){
                if(s.charAt(i-1) == s.charAt(j-1) && i != j){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }else{
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

        return dp[n][n];
    }

    public int EditDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        int[][] dp = new int[m+1][n+1];
        for(int i = 0; i <= m; i++){
            dp[i][0] = i;
        }

        for(int j = 0; j <= n; j++){
            dp[0][j] = j;
        }

        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){
                if(word1.charAt(i-1) == word2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1];
                }else {
                    dp[i][j] = 1 + Math.min(dp[i-1][j], Math.min(dp[i][j-1], dp[i-1][j-1]));
                }
            }
        }

        return dp[m][n];
    }

    public int EditDistanceSpaceOptimization(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        int[] dp = new int[n+1];

        for(int j = 0; j <= n; j++){
            dp[j] = j;
        }

        for(int i = 1; i <= m; i++){
            int[] curr = new int[n+1];
            curr[0] = i;
            for(int j = 1; j <= n; j++){
                if(word1.charAt(i-1) == word2.charAt(j-1)){
                    curr[j] = dp[j-1];
                }else {
                    curr[j] = 1 + Math.min(dp[j], Math.min(curr[j-1], dp[j-1]));
                }
            }

            dp = curr;
        }

        return dp[n];
    }

    public boolean WildCardMatching(String s, String p) {
        int n = s.length();
        int m = p.length();

        Boolean[][] dp = new  Boolean[n+1][m+1];

        return wildcardHelper(s, p, n-1, m - 1, dp);
    }

    private boolean wildcardHelper(String s, String p, int i, int j, Boolean[][] dp) {
        if(i < 0 && j < 0) return true;
        if(j < 0) return false;
        if(i < 0) {
            for(int k = j; k >= 0; k--) {
                if(p.charAt(k) != '*') {
                    return false;
                }
            }
            return true;
        }

        if(dp[i][j] != null) return  dp[i][j];

        if(s.charAt(i) == p.charAt(j) || p.charAt(j) == '?') {
            return dp[i][j] = wildcardHelper(s, p, i - 1, j - 1, dp);
        }
        else {
            if(p.charAt(j) == '*') {
                boolean empty =  wildcardHelper(s, p, i, j-1, dp);
                boolean match = wildcardHelper(s, p, i-1, j, dp);

                return dp[i][j] = empty || match;
            }
            else{
                return dp[i][j] = false;
            }
        }
    }

    public boolean WildCardMatchingTabulation(String s, String p) {
        int n = s.length();
        int m = p.length();

        boolean[][] dp = new  boolean[n+1][m+1];
        dp[0][0] = true;

        for(int i = 1; i  <= n; i++){
            dp[i][0] = false;
        }

        for(int j = 1; j <= m; j++){
            dp[0][j] = dp[0][j - 1] && p.charAt(j-1) == '*';
        }

        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= m; j++){
                if(s.charAt(i-1) == p.charAt(j-1) ||  p.charAt(j-1) == '?') {
                    dp[i][j] = dp[i-1][j-1];
                }else{
                    if(p.charAt(j-1) == '*') {
                        boolean empty = dp[i][j - 1];
                        boolean match = dp[i - 1][j];

                        dp[i][j] = empty || match;
                    }else{
                        dp[i][j] = false;
                    }
                }
            }
        }

        return dp[n][m];
    }

    public boolean WildCardMatchingSpaceOptimization(String s, String p) {
        int n = s.length();
        int m = p.length();

        boolean[] dp = new  boolean[m+1];
        dp[0] = true;

        for(int j = 1; j <= m; j++){
            dp[j] = dp[j - 1] && p.charAt(j-1) == '*';
        }

        for(int i = 1; i <= n; i++){
            boolean[] curr = new boolean[m+1];
            for(int j = 1; j <= m; j++){
                if(s.charAt(i-1) == p.charAt(j-1) ||  p.charAt(j-1) == '?') {
                    curr[j] = dp[j-1];
                }else{
                    if(p.charAt(j-1) == '*') {
                        boolean empty = curr[j - 1];
                        boolean match = dp[j];

                        curr[j] = empty || match;
                    }else{
                        curr[j] = false;
                    }
                }
            }
            dp = curr;
        }

        return dp[m];
    }

}

public class DpOnStrings {
    public static void main(String[] args) {
        DpOnStringsQuestions dp = new DpOnStringsQuestions();
        System.out.println(dp.LCS("brute", "groot"));

        System.out.println(dp.LCSTabulation("apple", "waffle"));
        System.out.println(dp.LCSSpaceOptimization("apple", "waffle"));

        System.out.println(dp.LCSPrintString("abac", "cab"));

        System.out.println(dp.LCSubstring("abcde", "abfce"));
        System.out.println(dp.LCSubstringSpaceOptimization("abcde", "abfce"));

        System.out.println(dp.LongestPalindromicSubsequence("leetcode"));
        System.out.println(dp.LongestPalindromeTabulation("leetcode"));
        System.out.println(dp.LongestPalindromeSpaceOptimization("leetcode"));

        // leetcode => edocteel => leetcodocteel
        System.out.println(dp.LongestInsertionPalindromeTabulation("leetcode"));

        System.out.println("Min operations needed here: " + dp.MinimumOperationsString1to2("horse", "ros"));

//        str1 = "brute", str2 = "groot" => 2 (rt) => 5 - 2 = 3(bue) && 5 - 2 = 3(goo) gbruoote
        System.out.println(dp.ShortestCommonSupersequence("brute", "groot"));
        System.out.println(dp.PrintShortestCommonSupersequence("brute", "groot"));

        System.out.println(dp.LongestRepeatingSubsequence("axxzxy"));

        System.out.println(dp.EditDistance("horse", "ros"));
        System.out.println(dp.EditDistanceSpaceOptimization("horse", "ros"));

        System.out.println(dp.WildCardMatching("aa", "a"));
        System.out.println(dp.WildCardMatchingTabulation("aa", "*"));
        System.out.println(dp.WildCardMatchingSpaceOptimization("aa", "a"));
    }
}
