package DynamicProgramming;

import java.util.Arrays;

class SubStringQuestions {
    public boolean isSubsetSum(int[] subset, int target) {
        int n= subset.length;
        Boolean[][] dp = new Boolean[n][target + 1];

        return isSubsetSumRecursion(subset, target, n - 1, dp);
    }

    private boolean isSubsetSumRecursion(int[] subset, int target, int index, Boolean[][] dp) {
        if(index < 0 || target < 0) return false;
        if(target == 0) return true;

        if(dp[index][target] != null) {
            return dp[index][target];
        }

        boolean pick = isSubsetSumRecursion(subset, target - subset[index], index - 1, dp);
        boolean notpick = isSubsetSumRecursion(subset, target, index - 1, dp);

        return dp[index][target] = pick || notpick;
    }

    public boolean isSubsetSumTabulation(int[] subset, int target) {
        int n= subset.length;
        Boolean[][] dp = new Boolean[n][target + 1];

        for(int i = 0; i < n; i++) {
            dp[i][0] = true;
        }


        if(subset[0] <= target) dp[0][subset[0]] =  true;
        for(int j = 0; j < target + 1; j++) {
            if(dp[0][j] == null) {
                dp[0][j] = false;
            }
        }

        for(int i = 1; i < n; i++) {
            for(int j = 0; j < target + 1; j++) {
                boolean notPick = dp[i-1][j];
                boolean pick = false;
                if(subset[i] <= j) {
                    pick = dp[i-1][j - subset[i]];
                }

                dp[i][j] = notPick || pick;
            }
        }

        return dp[n-1][target];
    }

    public boolean isSubsetSumSpaceOptimization(int[] subset, int target) {
        int n= subset.length;
        Boolean[] dp = new Boolean[target + 1];

        dp[0] = true;

        if(subset[0] <= target) dp[subset[0]] =  true;

        for(int j = 0; j < target + 1; j++) {
            if(dp[j] == null) {
                dp[j] = false;
            }
        }

        for(int i = 1; i < n; i++) {
            for(int j = target; j >= 0; j--) {
                boolean notPick = dp[j];
                boolean pick = false;
                if(subset[i] <= j) {
                    pick = dp[j - subset[i]];
                }

                dp[j] = notPick || pick;
            }
        }

        return dp[target];
    }

    public boolean canPartition(int[] nums) {
        int n = nums.length;

        int totalSum = Arrays.stream(nums).sum();
        if(totalSum % 2 != 0) return false;

        int target = totalSum/2;
        Boolean[][] dp = new Boolean[n][target + 1];

        return canPartitionHelper(nums, n-1, target, dp);
    }

    private boolean canPartitionHelper(int[] nums, int index, int target, Boolean[][] dp) {
        if(index < 0 || target < 0) return false;
        if(target == 0) return true;

        if(dp[index][target] != null) {
            return dp[index][target];
        }

        boolean include = canPartitionHelper(nums, index - 1, target - nums[index], dp);
        boolean exclude = canPartitionHelper(nums, index - 1, target, dp);

        return dp[index][target] = include || exclude;
    }

    public boolean canPartitionTabulation(int[] nums) {
        int n = nums.length;

        int totalSum = Arrays.stream(nums).sum();
        if(totalSum % 2 != 0) return false;

        int target = totalSum/2;
        Boolean[][] dp = new Boolean[n][target + 1];

        for(int i = 0; i < n; i++) {
            dp[i][0] = true;
        }

        if(nums[0] <= target) dp[0][nums[0]] =  true;
        for(int j = 1; j < target + 1; j++) {
            if(dp[0][j] != null) continue;
            dp[0][j] = false;
        }

        for(int i = 1; i < n; i++) {
            for(int j = 1; j < target + 1; j++) {
                boolean exclude = dp[i-1][j];

                boolean include = false;
                if(nums[i] <= j) {
                    include = dp[i-1][j - nums[i]];
                }

                dp[i][j] = include || exclude;
            }
        }

        return dp[n-1][target];
    }

    public boolean canPartitionSpaceOptimization(int[] nums) {
        int n = nums.length;

        int totalSum = Arrays.stream(nums).sum();
        if(totalSum % 2 != 0) return false;

        int target = totalSum/2;
        Boolean[] dp = new Boolean[target + 1];

        dp[0] = true;

        if(nums[0] <= target) dp[nums[0]] =  true;
        for(int j = 1; j < target + 1; j++) {
            if(dp[j] != null) continue;
            dp[j] = false;
        }

        for(int i = 1; i < n; i++) {
            for(int j = target; j >= 0; j--) {
                boolean exclude = dp[j];

                boolean include = false;
                if(nums[i] <= j) {
                    include = dp[j - nums[i]];
                }

                dp[j] = include || exclude;
            }
        }

        return dp[target];
    }

    public int perfectSum(int[] nums, int target) {
        int n = nums.length;

        int[][] dp = new int[n][target + 1];
        for(int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }

        return subsetCountHelper(nums, n - 1, target, dp);
    }

    private int subsetCountHelper(int[] nums, int index, int target, int[][] dp) {
        if(index < 0) {
            return target == 0 ? 1 : 0;
        }

        if(dp[index][target] != -1) return dp[index][target];

        int exclude = subsetCountHelper(nums, index -1, target, dp);
        int include = 0;
        if(nums[index] <= target) {
            include = subsetCountHelper(nums, index - 1, target - nums[index], dp);
        }

        return dp[index][target] = include + exclude;
    }

    public int perfectSumTabulation(int[] nums, int target) {
        int n = nums.length;
        int[][] dp = new int[n][target + 1];

        for(int j = 0; j < n; j++) {
            dp[j][0] = 1;
        }

        if(nums[0] <= target) dp[0][nums[0]] +=  1;

        for(int i = 1; i < n; i++) {
            for(int j = 1; j < target + 1; j++) {
                int exclude = dp[i - 1][j];
                int include = 0;
                if(nums[i] <= j) {
                    include = dp[i - 1][j - nums[i]];
                }

                dp[i][j] = include + exclude;
            }
        }

        return dp[n-1][target];
    }

    public int perfectSumSpaceOptimization(int[] nums, int target) {
        int n = nums.length;
        int[] dp = new int[target + 1];

        dp[0] = 1;


        if(nums[0] <= target) dp[nums[0]] +=  1;

        for(int i = 1; i < n; i++) {
            for(int j = target; j >= 0; j--) {
                int exclude = dp[j];
                int include = 0;
                if(nums[i] <= j) {
                    include = dp[j - nums[i]];
                }

                dp[j] = include + exclude;
            }
        }

        return dp[target];
    }

}


public class SubstringDp {
    public static void main(String[] args) {
        SubStringQuestions sq = new SubStringQuestions();
        System.out.println(sq.isSubsetSum(new int[]{3, 1, 4, 2}, 5));
        System.out.println(sq.isSubsetSumTabulation(new int[]{3, 1, 4, 2}, 5));
        System.out.println(sq.isSubsetSumSpaceOptimization(new int[]{3, 1, 4, 2}, 5));

        System.out.println(sq.canPartition(new int[]{1,5,11,5}));
        System.out.println(sq.canPartitionTabulation(new int[]{1,5,11,5}));
        System.out.println(sq.canPartitionSpaceOptimization(new int[]{1,5,11,5}));

        System.out.println(sq.perfectSum(new int[]{5, 2, 3, 10, 6, 8}, 10));
        System.out.println(sq.perfectSumTabulation( new int[]{5, 2, 3, 10, 6, 8}, 10));
        System.out.println(sq.perfectSumSpaceOptimization(new int[]{5, 2, 3, 10, 6, 8}, 10));
    }
}
