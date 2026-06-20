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

    public int Knapsack(int[] val, int [] wt, int W) {
        int n = val.length;

        int[][] dp = new int[n][W + 1];
        for(int i = 1; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }

        return knapsackHelper(n-1, W, val, wt, dp);
    }

    public int knapsackHelper(int index, int W, int[] val, int[] wt, int[][] dp) {
        if(index == 0) {
            if(wt[0] <= W) return val[0];
            return 0;
        }

        if(dp[index][W] != -1) return dp[index][W];

        int exclude = knapsackHelper(index-1, W, val, wt, dp);
        int include = 0;
        if(wt[index] <= W) {
            include = val[index] + knapsackHelper(index-1, W - wt[index], val, wt, dp);
        }

        return dp[index][W] =  Math.max(include, exclude);
    }

    public int knapsackTabulation(int[]val, int []wt, int W) {
        int n = val.length;
        int[][] dp = new int[n][W + 1];

        for(int i = 0; i <= W; i++) {
            if(wt[0] <= i) dp[0][i] = val[0];
        }

        for(int i = 1; i < n; i++) {
            for(int j = 0; j <= W; j++) {
                int exclude = dp[i - 1][j];
                int include = 0;
                if(wt[i] <= j) {
                    include = val[i] + dp[i - 1][j - wt[i]];
                }

                dp[i][j] = Math.max(include, exclude);
            }
        }

        return dp[n-1][W];
    }

    public int knapsackSpaceOptimization(int[] val, int[]wt, int W) {
        int n = val.length;
        int[] dp = new int[W + 1];

        for(int i = 0; i <= W; i++) {
            if(wt[0] <= i) dp[i] = val[0];
        }

        for(int i = 1; i < n; i++) {
            for(int j = W; j >= 1; j--) {
                int exclude = dp[j];
                int include = 0;
                if(wt[i] <= j) {
                    include = val[i] + dp[j - wt[i]];
                }

                dp[j] = Math.max(include, exclude);
            }
        }

        return dp[W];
    }

    public int minimumSubsetSum(int[] nums) {
        int n = nums.length;

        int totalSum = Arrays.stream(nums).sum();

        Boolean[][] dp = new Boolean[n][totalSum + 1];

        for(int i = 0; i < n; i++) {
            dp[i][0] = true;
        }

        if(nums[0] <= totalSum) dp[0][nums[0]] =  true;
        for(int j = 0; j < totalSum + 1; j++) {
            if(dp[0][j] == null) {
                dp[0][j] = false;
            }
        }

        for(int i = 1; i < n; i++) {
            for(int j = 0; j < totalSum + 1; j++) {
                boolean notPick = dp[i-1][j];
                boolean pick = false;
                if(nums[i] <= j) {
                    pick = dp[i-1][j - nums[i]];
                }

                dp[i][j] = notPick || pick;
            }
        }

        int min = Integer.MAX_VALUE;
       for(int i = 0; i <= totalSum; i++) {
           if(dp[n-1][i] == true) {
               min = Math.min(Math.abs((totalSum - i) - i), min);
           }
       }

       return min;
    }

    public int minimumSubsetSumSpaceOptimization(int[] nums) {
        int n = nums.length;

        int totalSum = Arrays.stream(nums).sum();

        Boolean[] dp = new Boolean[totalSum + 1];

        dp[0] = true;

        if(nums[0] <= totalSum) dp[nums[0]] =  true;
        for(int j = 0; j <= totalSum ; j++) {
            if(dp[j] == null) {
                dp[j] = false;
            }
        }

        for(int i = 1; i < n; i++) {
            for(int j = totalSum; j >= 0; j--) {
                boolean notPick = dp[j];
                boolean pick = false;
                if(nums[i] <= j) {
                    pick = dp[j - nums[i]];
                }

                dp[j] = notPick || pick;
            }
        }

        int min = Integer.MAX_VALUE;
        for(int i = 0; i <= totalSum; i++) {
            if(dp[i] == true) {
                min = Math.min(Math.abs((totalSum - i) - i), min);
            }
        }

        return min;
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

        System.out.println(sq.Knapsack(new int[]{1,2,3}, new int[]{4,5,1}, 4));
        System.out.println(sq.knapsackTabulation( new int[]{1,2,3}, new int[]{4,5,1}, 4));
        System.out.println(sq.knapsackSpaceOptimization(new int[]{1,2,3}, new int[]{4,5,1}, 4));

        System.out.println(sq.minimumSubsetSum(new int[]{1,6,11,5}));
        System.out.println(sq.minimumSubsetSumSpaceOptimization(new int[]{1,6,11,5}));
    }
}
