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

    public int HouseRobber(int[] arr) {
        if(arr == null || arr.length == 0) return 0;

        int[] dp = new int[arr.length];
        Arrays.fill(dp, -1);

        return HouseRobberHelper(arr, arr.length - 1, dp);
    }

    private int HouseRobberHelper(int[] arr, int index, int[] dp) {
        if(index <= 0) {
            if(index == 0) return arr[0];
            return 0;
        }

        if(dp[index] != -1) return dp[index];

        int pick = arr[index] + HouseRobberHelper(arr, index - 2, dp);
        int notPick = HouseRobberHelper(arr, index-1, dp);

        return dp[index] = Math.max(pick, notPick);
    }

    public int HouseRobberTabulation(int[] arr) {
        if(arr == null || arr.length == 0) return 0;
        if(arr.length == 1) return arr[0];
        int[] dp = new int[arr.length];
        dp[0] = arr[0];
        dp[1] = Math.max(arr[0], arr[1]);

        for(int i = 2; i < arr.length; i++) {
            int pick = arr[i] + dp[i - 2];
            int notPick = dp[i - 1];

            dp[i] = Math.max(pick, notPick);
        }

        return dp[arr.length - 1];
    }

    public int HouseRobberSpaceOptimization(int[] arr) {
        if(arr == null || arr.length == 0) return 0;
        if(arr.length == 1) return arr[0];
        int prev1 = Math.max(arr[0], arr[1]);
        int prev2 = arr[0];

        for(int i = 2; i < arr.length; i++) {
            int pick = arr[i] + prev2;
            int notPick = prev1;

            int curr = Math.max(pick, notPick);
            prev2 = prev1;
            prev1 = curr;
        }

        return prev1;
    }

    public int HouseRobbery2(int[] nums) {
        if(nums == null || nums.length == 0) return 0;
        if(nums.length == 1) return nums[0];

        int[] firstDp = new int[nums.length];
        Arrays.fill(firstDp, -1);

        int[] secondDp = new int[nums.length];
        Arrays.fill(secondDp, -1);

        int firstIncluded = robberyHelper(nums, nums.length - 2, firstDp, 0);
        int secondIncluded = robberyHelper(nums, nums.length - 1, secondDp, 1);

        return Math.max(firstIncluded, secondIncluded);
    }

    private int robberyHelper(int[] arr, int index, int[] dp, int range) {
        if(index <= range) {
            if(index == range) return arr[index];
            return 0;
        }

        if(dp[index] != -1) return dp[index];

        int pick = arr[index] + robberyHelper(arr, index - 2, dp, range);
        int notPick = robberyHelper(arr, index-1, dp, range);

        return dp[index] = Math.max(pick, notPick);
    }

    public int HouseRobber2Tabulation(int[] nums) {
        if(nums == null || nums.length == 0) return 0;
        if(nums.length == 1) return nums[0];

        int firstPass = HouseRobberLinear(nums, 0, nums.length - 2);
        int secondPass = HouseRobberLinear(nums, 1, nums.length - 1);

        return Math.max(firstPass, secondPass);
    }

    private int HouseRobberLinear(int[] nums, int start, int end) {
        if(start == end) return nums[start];
        int[] dp = new int[nums.length];
        dp[start] = nums[start];
        dp[start + 1] = Math.max(nums[start], nums[start + 1]);

        for(int i = start + 2; i <= end; i++) {
            int pick =  nums[i] + dp[i - 2];
            int notPick =  dp[i - 1];

            dp[i] = Math.max(pick, notPick);
        }

        return dp[end];
    }

    public int HouseRobber2SpaceOptimization(int[] nums) {
        if(nums == null || nums.length == 0) return 0;
        if(nums.length == 1) return nums[0];

        int firstPass = HouseRobberSpaceOptimization(nums, 0, nums.length - 2);
        int secondPass = HouseRobberSpaceOptimization(nums, 1, nums.length - 1);

        return Math.max(firstPass, secondPass);
    }


    private int HouseRobberSpaceOptimization(int[] nums, int start, int end) {
        if(start == end) return nums[start];
        int prev2 = nums[start];
        int prev1 = Math.max(nums[start], nums[start + 1]);

        for(int i = start + 2; i <= end; i++) {
            int pick =  nums[i] + prev2;
            int notPick =  prev1;

            int curr = Math.max(pick, notPick);
            prev2 = prev1;
            prev1 = curr;
        }

        return prev1;
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

        System.out.println(q.HouseRobber(new int[]{2,7,9,3,1}));
        System.out.println(q.HouseRobberTabulation(new int[]{2,7,9,3,1}));
        System.out.println(q.HouseRobberSpaceOptimization(new int[]{2,7,9,3,1}));

        System.out.println(q.HouseRobbery2(new int[]{1,2,3,1}));
        System.out.println(q.HouseRobber2Tabulation(new int[]{2,3,2}));
        System.out.println(q.HouseRobber2SpaceOptimization(new int[]{2,3,2}));
    }
}
