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
}

public class DPOnPartition {
    public static void main(String[] args) {
        DPPartitionQuestions dp = new DPPartitionQuestions();
        System.out.println(dp.MatrixChainMultiplicationRecursion(new int[]{2, 1, 3, 4}));
        System.out.println(dp.MatrixChainMultiplicationMemo(new int[]{2, 1, 3, 4}));
    }
}
