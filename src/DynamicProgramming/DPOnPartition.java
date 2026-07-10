package DynamicProgramming;

import java.util.Arrays;

class DPPartitionQuestions {
    public int MatrixChainMultiplicationRecursion(int[] nums) {
        int n =  nums.length;

        return mcmHelperRecursion(1, n-1, nums);
    }

    private int mcmHelperRecursion(int i, int j , int[] nums) {
        if(i == j) return 0;

        int ans = Integer.MAX_VALUE;
        for(int k = i; k < j; k++) {
            int left = mcmHelperRecursion(i, k, nums);
            int right = mcmHelperRecursion(k + 1, j, nums);
            int cost = nums[i - 1] * nums[k] * nums[j];

            int result = left + right + cost;

            ans = Math.min(ans, result);
        }

        return ans;
    }

    public int MatrixChainMultiplicationMemo(int[] nums) {
        int n = nums.length;

        int[][] dp = new int[n][n];
        for(int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }

        return mcmHelperMemo(1, n - 1,  nums, dp);
    }

    private int mcmHelperMemo(int i, int j, int[] nums, int[][] dp) {
        if(i == j) return 0;

        if(dp[i][j] != -1) return dp[i][j];

        int ans = Integer.MAX_VALUE;
        for(int k = i; k < j; k++) {
            int left = mcmHelperMemo(i, k, nums, dp);
            int right = mcmHelperMemo(k + 1, j, nums, dp);
            int cost = nums[i - 1] * nums[k] * nums[j];

            int result = left + right + cost;

            ans = Math.min(ans, result);
        }

        return dp[i][j] = ans;
    }

    public int mcmTabulation(int[] nums) {
        int n = nums.length;

        int[][] dp = new int[n][n];

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(i == j) continue;
                dp[i][j] = Integer.MAX_VALUE;
            }
        }

        for(int i = n-1; i >= 1; i--) {
            for(int j = i + 1; j < n; j++) {
                for(int k = i; k < j; k++) {
                    int left = dp[i][k];
                    int right = dp[k + 1][j];
                    int cost = nums[i - 1] * nums[k]  * nums[j];

                    int result = left + right + cost;

                    dp[i][j] = Math.min(result, dp[i][j]);
                }
            }
        }

        return dp[1][n-1];
    }

    public int minCostToCutStick(int n, int[] cuts) {
        Arrays.sort(cuts);
        int[] newCuts = new int[cuts.length + 2];

        System.arraycopy(cuts, 0, newCuts, 1, cuts.length);
        newCuts[cuts.length + 1] = n;

        return minCostToCutStickHelper(0, newCuts.length - 1,  newCuts);
    }

    private int minCostToCutStickHelper(int i, int j, int[] cuts) {
        if(i + 1 == j)  return 0;

        int ans = Integer.MAX_VALUE;
        for(int k = i+1; k < j; k++) {
            int left = minCostToCutStickHelper(i, k, cuts);
            int right = minCostToCutStickHelper(k, j, cuts);
            int cost = cuts[j] - cuts[i];

            int result = left + right + cost;

            ans = Math.min(ans, result);
        }

        return ans;
    }

    public int minCostToCutStickMemo(int n, int[] cuts) {
        Arrays.sort(cuts);
        int[] newCuts = new int[cuts.length + 2];

        System.arraycopy(cuts, 0, newCuts, 1, cuts.length);
        newCuts[cuts.length + 1] = n;

        int[][] dp = new int[newCuts.length][newCuts.length];
        for(int i = 0; i < newCuts.length; i++) {
            Arrays.fill(dp[i], -1);
        }

        return minCostMemoHelper(0,  newCuts.length - 1, newCuts, dp);
    }

    private int minCostMemoHelper(int i, int j, int[] newCuts, int[][] dp) {
        if(i + 1 == j)  return 0;

        if(dp[i][j] != -1) return dp[i][j];
        int ans = Integer.MAX_VALUE;

        for(int k = i+1; k < j; k++) {
            int left = minCostMemoHelper(i, k, newCuts, dp);
            int right = minCostMemoHelper(k, j, newCuts, dp);
            int cost = newCuts[j] - newCuts[i];

            int result = left + right +  cost;
            ans = Math.min(ans, result);
        }

        return dp[i][j] = ans;
    }

    public int minCostTabulation(int n, int[] cuts) {
        Arrays.sort(cuts);

        int[] newCuts = new int[cuts.length + 2];

        System.arraycopy(cuts, 0, newCuts, 1, cuts.length);
        newCuts[cuts.length + 1] = n;

        int[][] dp = new int[newCuts.length][newCuts.length];

        for(int i = 0; i < newCuts.length; i++) {
            for(int j = 0; j < newCuts.length; j++) {
                if(i + 1 == j) continue;
                dp[i][j] = Integer.MAX_VALUE;
            }
        }

        for(int i = newCuts.length - 1; i >= 0; i--) {
            for(int j = i + 1; j < newCuts.length; j++) {
                for(int k = i + 1; k < j; k++) {
                    int left = dp[i][k];
                    int right = dp[k][j];
                    int cost = newCuts[j] - newCuts[i];

                    int result = left + right + cost;
                    dp[i][j] = Math.min(result, dp[i][j]);
                }
            }
        }

        return dp[0][newCuts.length - 1];
    }

    public int ballonBurst(int[] nums) {
        int n = nums.length;

        int[] newNums = new int[n + 2];
        System.arraycopy(nums, 0, newNums, 1, n);
        newNums[0] = 1;
        newNums[n + 1] = 1;

        return ballonBurstHelper(1, n, newNums);
    }

    private int ballonBurstHelper(int i, int j, int[] newNums) {
        if(i > j)  return 0;

        int ans = Integer.MIN_VALUE;
        for(int k = i; k <= j; k++) {
            int left = ballonBurstHelper(i, k-1, newNums);
            int right = ballonBurstHelper(k+1, j, newNums);
            int cost = newNums[i - 1] * newNums[k] * newNums[j + 1];

            int result = left + right + cost;
            ans = Math.max(ans, result);
        }

        return ans;
    }

    public int ballonBurstMemo(int[] nums) {
        int n = nums.length;

        int[] newNums = new int[n + 2];
        System.arraycopy(nums, 0, newNums, 1, n);
        newNums[0] = 1;
        newNums[n + 1] = 1;

        int[][] dp = new int[newNums.length][newNums.length];
        for(int i = 0; i < newNums.length; i++) {
            Arrays.fill(dp[i], -1);
        }

        return ballonBurstMemoHelper(1, n, newNums, dp);
    }

    private int ballonBurstMemoHelper(int i, int j, int[] newNums, int[][] dp) {
        if(i > j)  return 0;

        if(dp[i][j] != -1) return dp[i][j];

        int ans = Integer.MIN_VALUE;
        for(int k = i; k <= j; k++) {
            int left = ballonBurstMemoHelper(i, k-1, newNums, dp);
            int right = ballonBurstMemoHelper(k+1, j, newNums, dp);
            int cost = newNums[i - 1] * newNums[k] * newNums[j + 1];

            int result = left + right + cost;
            ans = Math.max(ans, result);
        }

        return dp[i][j] =  ans;
    }

    public int ballonBurstTabulation(int[] nums) {
        int n = nums.length;

        int[] newNums = new int[n + 2];
        System.arraycopy(nums, 0, newNums, 1, n);
        newNums[0] = 1;
        newNums[n + 1] = 1;

        int[][] dp = new int[newNums.length][newNums.length];

        for(int i = 0; i < newNums.length; i++) {
            for(int j = 0; j < newNums.length; j++) {
                if(i > j) continue;
                dp[i][j] = Integer.MIN_VALUE;
            }
        }

        for(int i = n; i >= 1; i--) {
            for(int j = i; j <= n; j++) {
                for(int k = i; k <= j; k++) {
                    int left = dp[i][k - 1];
                    int right = dp[k + 1][j];
                    int cost =  newNums[i - 1] * newNums[k] * newNums[j + 1];

                    int result = left + right + cost;

                    dp[i][j] = Math.max(result, dp[i][j]);
                }
            }
        }

        return dp[1][n];
    }

    public int isPalindrome(String s) {
        int n = s.length();
        boolean[][] isPal = new boolean[n][n];

        for(int i = n - 1; i >= 0; i--) {
            for(int j = i;  j < n; j++) {
                if(i == j) {
                    isPal[i][j] = true;
                }
                else if(j == i + 1) {
                    isPal[i][j] = s.charAt(i) == s.charAt(j);
                }else{
                    isPal[i][j] = (s.charAt(i) == s.charAt(j)) && isPal[i+1][j-1];
                }
            }
        }

        int ans = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(isPal[i][j]) {
                    ans++;
                }
            }
        }

        return ans;
    }

    public String LongestPalindromeString(String s) {
        int n = s.length();
        boolean[][] isPal = new boolean[n][n];

        for(int i = n - 1; i >= 0; i--) {
            for(int j = i;  j < n; j++) {
                if(i == j) {
                    isPal[i][j] = true;
                }
                else {
                    boolean isEqual = s.charAt(i) == s.charAt(j);
                    if(j == i + 1) {
                        isPal[i][j] = isEqual;
                    }else{
                        isPal[i][j] = isEqual && isPal[i+1][j-1];
                    }
                }
            }
        }

        int start = 0;
        int maxLen = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(isPal[i][j] && (j - i + 1) > maxLen) {
                    start = i;
                    maxLen = j - i + 1;
                }
            }
        }

        return s.substring(start, start + maxLen);
    }

    public int PalindromePartitioning2(String s) {
        int n = s.length();

        boolean[][] isPal = new boolean[n][n];

        for(int i = n - 1; i >= 0; i--) {
            for(int j = i;  j < n; j++) {
                if(i == j) {
                    isPal[i][j] = true;
                }
                else {
                    boolean isEqual = s.charAt(i) == s.charAt(j);
                    if(j == i + 1) {
                        isPal[i][j] = isEqual;
                    }else{
                        isPal[i][j] = isEqual && isPal[i+1][j-1];
                    }
                }
            }
        }

        int[] dp = new int[n];
        Arrays.fill(dp, -1);

        return palindromePartitioningHelper(0, isPal, n, dp);
    }

    private int palindromePartitioningHelper(int i, boolean[][] isPal, int n, int[] dp) {
        if(i >= n) return -1;

        if(dp[i] != -1) return dp[i];

        int ans = Integer.MAX_VALUE;
        for(int j = i; j < n; j++) {
            if(isPal[i][j]) {
                int result = 1 + palindromePartitioningHelper(j + 1, isPal, n, dp);

                ans =  Math.min(result, ans);
            }
        }

        return dp[i] = ans;
    }

    public int palindromePartitioningTabulation(String s) {
        int n =  s.length();
        boolean[][] isPal = new boolean[n][n];

        for(int i = n - 1; i >= 0; i--) {
            for(int j = i;  j < n; j++) {
                if(i == j) {
                    isPal[i][j] = true;
                }
                else {
                    boolean isEqual = s.charAt(i) == s.charAt(j);
                    if(j == i + 1) {
                        isPal[i][j] = isEqual;
                    }else{
                        isPal[i][j] = isEqual && isPal[i+1][j-1];
                    }
                }
            }
        }

        int[] dp = new int[n+1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[n] = -1;

        for(int i = n-1; i >= 0; i--) {
            for(int j = i; j < n; j++) {
                if(isPal[i][j]) {
                    int result = 1 + dp[j+1];

                    dp[i] = Math.min(result, dp[i]);
                }
            }
        }

        return dp[0];
    }

    public int EvaluateBooleanExpression(String s) {
        int n = s.length();

        int[] result = evaluateBooleanHelper(0, n-1, s);
        return result[0];
    }

    private int[] evaluateBooleanHelper(int i, int j, String s) {
        if(i == j) {
            if(s.charAt(i) == 'T') return new int[]{1, 0};
            else return new int[]{0, 1};
        }

        int[] ways = new int[2];
        for(int k = i+1; k <= j; k+=2) {
            int[] left = evaluateBooleanHelper(i, k - 1, s);
            int[] right = evaluateBooleanHelper(k+1, j, s);

            int lt = left[0];
            int rt = right[0];
            int lf =  left[1];
            int rf =  right[1];

            if(s.charAt(k) == '&') {
                ways[0] += lt * rt;
                ways[1] += (lt * rf) + (lf * rt) + (lf * rf);
            }
            else if(s.charAt(k) == '|') {
                ways[0] += (lt * rt) + (lf * rt) + (lt * rf);
                ways[1] += (lf * rf);
            }else if(s.charAt(k) == '^') {
                ways[0] += (lt * rf) + (rt * lf);
                ways[1] += (rt * lt) + (lf * rf);
            }
        }

        return ways;
    }

    public int EvaluateBooleanExpressionMemo(String s) {
        int n = s.length();

        int[][][]dp = new int[n][n][2];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                for(int k = 0; k < 2; k++){
                    dp[i][j][k] = -1;
                }
            }
        }

        int[] result = evaluateBooleanMemoHelper(0, n-1, s, dp);
        return result[0];
    }

    private int[] evaluateBooleanMemoHelper(int i, int j, String s, int[][][] dp) {
        if(i == j) {
            if(s.charAt(i) == 'T') return new int[]{1, 0};
            else return new int[]{0, 1};
        }

        if(dp[i][j][0] != -1) return dp[i][j];

        int[] ways = new int[2];
        for(int k = i+1; k <= j; k+=2) {
            int[] left = evaluateBooleanMemoHelper(i, k - 1, s, dp);
            int[] right = evaluateBooleanMemoHelper(k+1, j, s, dp);

            int lt = left[0];
            int rt = right[0];
            int lf =  left[1];
            int rf =  right[1];

            if(s.charAt(k) == '&') {
                ways[0] += lt * rt;
                ways[1] += (lt * rf) + (lf * rt) + (lf * rf);
            }
            else if(s.charAt(k) == '|') {
                ways[0] += (lt * rt) + (lf * rt) + (lt * rf);
                ways[1] += (lf * rf);
            }else if(s.charAt(k) == '^') {
                ways[0] += (lt * rf) + (rt * lf);
                ways[1] += (rt * lt) + (lf * rf);
            }
        }

        return dp[i][j] = ways;
    }

    public int EvaluateBooleanExpressionTabulation(String s) {
        int n = s.length();

        int[][][]dp = new int[n+1][n+1][2];
        for(int i = 0; i < n; i++) {
            if(s.charAt(i) == 'T') {
                dp[i][i][0] = 1;
                dp[i][i][1] = 0;
            }else{
                dp[i][i][0] = 0;
                dp[i][i][1] = 1;
            }
        }

        for(int i = n - 1; i >= 0; i--) {
            for(int j = i; j < n; j++) {
                for(int k = i + 1; k <= j; k+=2) {
                    int[] left =  dp[i][k - 1];
                    int[] right =  dp[k + 1][j];

                    int lt = left[0];
                    int rt = right[0];
                    int lf =  left[1];
                    int rf =  right[1];

                    if(s.charAt(k) == '&') {
                        dp[i][j][0] += lt * rt;
                        dp[i][j][1] += (lt * rf) + (lf * rt) + (lf * rf);
                    }
                    else if(s.charAt(k) == '|') {
                        dp[i][j][0] += (lt * rt) + (lf * rt) + (lt * rf);
                        dp[i][j][1] += (lf * rf);
                    }else if(s.charAt(k) == '^') {
                        dp[i][j][0] += (lt * rf) + (rt * lf);
                        dp[i][j][1] += (rt * lt) + (lf * rf);
                    }
                }
            }
        }

        return dp[0][n-1][0];
    }

    public boolean ScrambleString(String s1, String s2) {
        int n = s1.length();

        return helperScramble(0, 0, n, s1, s2);
    }

    private boolean helperScramble(int i1, int i2, int len, String s1, String s2) {
        if(len == 1) return s1.charAt(i1) == s2.charAt(i2);

        for(int p = 1; p < len; p++) {
            boolean noSwap = helperScramble(i1, i2, p, s1, s2) && helperScramble(i1 + p, i2 + p, len - p, s1, s2);
            if(noSwap) {
                return true;
            }

            boolean swap = helperScramble(i1, i2 + len - p, p, s1, s2) && helperScramble(i1 + p, i2, len - p, s1, s2);
            if(swap) {
                return true;
            }
        }

        return false;
    }

    public boolean ScrambledStringMemo(String s1, String s2) {
        int n = s1.length();

        Boolean[][][] dp = new Boolean[n][n][n + 1];

        return helperScrambleMemo(0, 0,  n, s1, s2, dp);
    }

    private boolean helperScrambleMemo(int i1, int i2, int len, String s1, String s2, Boolean[][][] dp) {
        if(len == 1) return s1.charAt(i1) == s2.charAt(i2);

        if(dp[i1][i2][len] != null) return dp[i1][i2][len];

        for(int p = 1; p < len; p++) {
            boolean noSwap = helperScrambleMemo(i1, i2, p, s1, s2,dp) && helperScrambleMemo(i1 + p, i2 + p, len - p, s1, s2,dp);
            if(noSwap) {
                return dp[i1][i2][len] = true;
            }

            boolean swap = helperScrambleMemo(i1, i2 + len - p, p, s1, s2,dp) && helperScrambleMemo(i1 + p, i2, len - p, s1, s2,dp);
            if(swap) {
                return dp[i1][i2][len] = true;
            }
        }

        return dp[i1][i2][len] = false;
    }

    public boolean ScrambledStringTabulation(String s1, String s2) {
        int n = s1.length();

        boolean[][][] dp = new boolean[n][n][n + 1];

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(s1.charAt(i) == s2.charAt(j)) {
                    dp[i][j][1] = true;
                }
            }
        }

        for(int len = 2; len <= n; len++) {
            for(int i1 = 0; i1 <= n - len; i1++) {
                for(int i2 = 0; i2 <= n - len; i2++) {
                    for(int p = 1; p < len; p++) {
                        boolean noSwap = dp[i1][i2][p] && dp[i1 + p][i2 + p][len - p];
                        if(noSwap) {
                            dp[i1][i2][len] = true;
                            break;
                        }

                        boolean swap = dp[i1][i2 + len - p][p] && dp[i1 + p][i2][len - p];
                        if(swap) {
                            dp[i1][i2][len] = true;
                            break;
                        }
                    }
                }
            }
        }

        return dp[0][0][n];
    }

    public boolean ScrambledStringTabulationOptimization(String s1, String s2) {
        int n = s1.length();

        boolean[][][] dp = new boolean[n][n][n + 1];

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(s1.charAt(i) == s2.charAt(j)) {
                    dp[i][j][1] = true;
                }
            }
        }

        for(int len = 2; len <= n; len++) {
            for(int i1 = 0; i1 <= n - len; i1++) {
                for(int i2 = 0; i2 <= n - len; i2++) {
                    // ----------------------------
                    // Optimization 1:
                    // Are the substrings already identical?
                    // ----------------------------
                    boolean same = true;

                    for(int k = 0; k < len; k++) {
                        if(s1.charAt(i1 + k) != s2.charAt(i2 + k)) {
                            same = false;
                            break;
                        }
                    }

                    if(same) {
                        dp[i1][i2][len] = true;
                        continue;
                    }
                    // ----------------------------
                    // Optimization 2:
                    // Character frequency pruning
                    // ----------------------------

                    int[] freq = new int[26];

                    for (int k = 0; k < len; k++) {
                        freq[s1.charAt(i1 + k) - 'a']++;
                        freq[s2.charAt(i2 + k) - 'a']--;
                    }

                    boolean valid = true;

                    for (int x : freq) {
                        if (x != 0) {
                            valid = false;
                            break;
                        }
                    }

                    if (!valid)
                        continue;

                    // ----------------------------
                    // Actual DP transition
                    // ----------------------------
                    for(int p = 1; p < len; p++) {
                        boolean noSwap = dp[i1][i2][p] && dp[i1 + p][i2 + p][len - p];
                        if(noSwap) {
                            dp[i1][i2][len] = true;
                            break;
                        }

                        boolean swap = dp[i1][i2 + len - p][p] && dp[i1 + p][i2][len - p];
                        if(swap) {
                            dp[i1][i2][len] = true;
                            break;
                        }
                    }
                }
            }
        }

        return dp[0][0][n];
    }



}

public class DPOnPartition {
    public static void main(String[] args) {
        DPPartitionQuestions dp = new DPPartitionQuestions();
        System.out.println(dp.MatrixChainMultiplicationRecursion(new int[]{2, 1, 3, 4}));
        System.out.println(dp.MatrixChainMultiplicationMemo(new int[]{2, 1, 3, 4}));
        System.out.println(dp.mcmTabulation( new int[]{2, 1, 3, 4}));

        System.out.println(dp.minCostToCutStick(7, new int[]{1,3,4,5}));
        System.out.println(dp.minCostToCutStickMemo(7, new int[]{1,3,4,5}));
        System.out.println(dp.minCostTabulation(7, new int[]{1,3,4,5}));

        System.out.println(dp.ballonBurst(new int[]{3,1,5,8}));
        System.out.println(dp.ballonBurstMemo(new int[]{3,1,5,8}));
        System.out.println(dp.ballonBurstTabulation( new int[]{3,1,5,8}));

        System.out.println(dp.isPalindrome("aaa"));
        System.out.println("Longest Palindrome: " + dp.LongestPalindromeString("babad"));

        System.out.println(dp.PalindromePartitioning2("aab"));
        System.out.println(dp.palindromePartitioningTabulation("aab"));

        System.out.println(dp.EvaluateBooleanExpression("F|T^F"));
        System.out.println(dp.EvaluateBooleanExpressionMemo("F|T^F"));
        System.out.println(dp.EvaluateBooleanExpressionTabulation("F|T^F"));

        System.out.println(dp.ScrambleString("abcde", "caebd"));
        System.out.println(dp.ScrambledStringMemo("great", "rgeat"));
        System.out.println(dp.ScrambledStringTabulation("great", "rgeat"));
        System.out.println(dp.ScrambledStringTabulationOptimization("great", "rgeat"));
    }
}
