package DynamicProgramming;

import java.util.Arrays;

class Questions {
    public int memoFibonacci(int n) {
        int[] memo = new int[n + 1];
        Arrays.fill(memo, -1);

        return helperMemoFibo(n, memo);
    }

    private int helperMemoFibo(int n, int[] memo) {
        if(n <= 1) return n;

        if (memo[n] != -1) return memo[n];

        return memo[n] = helperMemoFibo(n - 1, memo) + helperMemoFibo(n - 2, memo);
    }

    public int TabulationFibonacci(int n) {
        if(n <= 1) return n;

        int[] memo = new int[n + 1];
        memo[0] = 0;
        memo[1] = 1;

        for(int i = 2; i <= n; i++) {
            memo[i] = memo[i - 1] + memo[i - 2];
        }

        return memo[n];
    }

    public int SpaceOptimizationFibonacci(int n) {
        if(n <= 1) return n;

        int prev2 = 0; // dp[n - 2]
        int prev1 = 1; // dp[n - 1]

        for(int i = 2; i <= n; i++) {
            int cur = prev2 + prev1;
            prev2 = prev1;
            prev1 = cur;
        }

        return prev1;
    }

    public int climbStairs(int n) {
        if(n <= 1) return n;

        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);

        return climbStairsHelper(n, dp);
    }

    private int climbStairsHelper(int n, int[] dp) {
        if(n < 0) return 0;
        if(n == 0) return 1;

        if(dp[n] != -1) return dp[n];

        int oneStep = climbStairsHelper(n - 1, dp);
        int twoStep = climbStairsHelper(n - 2, dp);

        return dp[n] = oneStep + twoStep;
    }

    public int climbStairsTabulation(int n) {
        if(n <= 1) return n;

        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;

        for(int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    public int climbStairsSpaceOptimization(int n) {
        if(n <= 1) return n;

        int prev1 = 1;
        int prev2 = 1;

        for(int i = 2; i <= n; i++) {
            int cur = prev1 + prev2;
            prev2 = prev1;
            prev1 = cur;
        }

        return  prev1;
    }

    public int MinCostClimbing(int[] arr) {
        if(arr == null || arr.length == 0) return 0;

        int[] dp = new  int[arr.length + 1];
        Arrays.fill(dp, -1);

        int startFromZero = MinCostClimbingHelper(arr, 0, dp);
        int startFromOne = MinCostClimbingHelper(arr, 1, dp);

        return Math.min(startFromOne, startFromZero);
    }

    private int MinCostClimbingHelper(int[] arr, int index, int[] dp) {
        if(index >= arr.length) {
            return 0;
        }

        if(dp[index] != -1) return dp[index];

        int oneStep = arr[index] + MinCostClimbingHelper(arr, index + 1, dp);

        int twoStep = Integer.MAX_VALUE;
        if(index + 2 <= arr.length) {
            twoStep = arr[index] + MinCostClimbingHelper(arr, index + 2, dp);
        }

        return dp[index] = Math.min(oneStep, twoStep);
    }

    public int MinCostClimbingTabulation(int[] arr) {
        if(arr == null || arr.length == 0) return 0;
        if(arr.length == 1) return arr[0];

        int[] dp = new  int[arr.length + 1];
        dp[0] = arr[0];
        dp[1] = arr[1];

        for(int i = 2; i < arr.length; i++) {
            int oneStep = arr[i] + dp[i - 1];
            int twoStep = arr[i] + dp[i - 2];

            dp[i] = Math.min(oneStep, twoStep);
        }

        return Math.min(dp[arr.length - 1], dp[arr.length - 2]);
    }

    public int MinCostClimbingSpaceOptimization(int[] arr) {
        if(arr == null || arr.length == 0) return 0;
        if(arr.length == 1) return arr[0];

        int prev1 = arr[1];
        int prev2 = arr[0];

        for(int i = 2; i < arr.length; i++) {
            int oneStep = arr[i] + prev1;
            int twoStep = arr[i] + prev2;

            int curr = Math.min(oneStep, twoStep);
            prev2 = prev1;
            prev1 = curr;
        }

        return Math.min(prev1, prev2);
    }
}

public class OneDDP {
    public  static void main(String[] args) {
        Questions q = new Questions();
        System.out.println(q.memoFibonacci(5));
        System.out.println(q.TabulationFibonacci(5));
        System.out.println(q.SpaceOptimizationFibonacci(5));

        System.out.println(q.climbStairs(5));
        System.out.println(q.climbStairsTabulation(5));
        System.out.println(q.climbStairsSpaceOptimization(5));

        System.out.println(q.MinCostClimbing(new int[]{1, 100, 1, 1, 1, 100, 1, 1, 100, 1}));
        System.out.println(q.MinCostClimbingTabulation(new int[]{10, 15, 20}));
        System.out.println(q.MinCostClimbingSpaceOptimization(new int[]{10, 15, 20}));
    }
}
