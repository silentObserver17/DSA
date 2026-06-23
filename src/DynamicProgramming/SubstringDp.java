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

    public int countPartitionDifference(int[] nums, int diff) {
        int n = nums.length;

        int totalSum = Arrays.stream(nums).sum();
        if(diff > totalSum) return 0;

        int numeratorSum = totalSum + diff;
        if(numeratorSum % 2 != 0) return 0;

        int target = numeratorSum / 2;

        int[][] dp = new int[n][target + 1];
        for(int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }

        return partitionHelper(nums, n - 1, target, dp);
    }

    private int partitionHelper(int[] nums, int index, int target, int[][] dp) {
        if(index < 0) {
            return target == 0 ? 1 : 0;
        }

        if(dp[index][target] != -1)  return dp[index][target];

        int exclude = partitionHelper(nums, index - 1, target, dp);

        int include = 0;
        if(nums[index] <= target) {
            include = partitionHelper(nums, index - 1, target - nums[index], dp);
        }

        return dp[index][target] = include + exclude;
    }

    public int countPartitionDifferenceTabulation(int[] nums, int diff) {
        int n = nums.length;

        int totalSum = Arrays.stream(nums).sum();
        if(diff > totalSum) return 0;

        int numeratorSum = totalSum + diff;
        if(numeratorSum % 2 != 0) return 0;

        int target = numeratorSum / 2;

        int[][] dp = new int[n][target + 1];

        for(int i = 0; i < n; i++) {
            dp[i][0] = 1;
        }

        if(nums[0] <= target) dp[0][nums[0]] +=  1;

        for(int i = 1; i < n; i++) {
            for(int j = 0; j < target + 1; j++) {
                int exclude = dp[i - 1][j];
                int include = 0;
                if(nums[i] <= j) {
                    include = dp[i - 1][j - nums[i]];
                }

                dp[i][j] = include + exclude;
            }
        }

        return dp[n - 1][target];
    }

    public int countPartitionDifferenceSpaceOptimization(int[] nums, int diff) {
        int n = nums.length;

        int totalSum = Arrays.stream(nums).sum();
        if(diff > totalSum) return 0;

        int numeratorSum = totalSum + diff;
        if(numeratorSum % 2 != 0) return 0;

        int target = numeratorSum / 2;

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

    public int unboundedKnapsack(int[] val, int[] wt, int W) {
        int n = val.length;

        int[][] dp = new int[n][W + 1];
        for(int i = 1; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }

        return unboundedHelper(n - 1, W, val, wt, dp);
    }

    private int unboundedHelper(int index, int target, int[] val, int[] wt, int[][] dp) {
        if(index == 0) {
            if(wt[0] <= target) return target/wt[0] * val[0];
            return 0;
        }

        if(dp[index][target] != -1)  return dp[index][target];

        int exclude = unboundedHelper(index - 1, target, val, wt, dp);
        int include = 0;
        if(wt[index] <= target) {
            include = val[index] + unboundedHelper(index, target - wt[index], val, wt, dp);
        }

        return dp[index][target] = Math.max(include, exclude);
    }

    public int unboundedKnapsackTabulation(int[] val, int[] wt, int W) {
        int n = val.length;

        int[][] dp = new int[n][W + 1];

        for(int i = 0; i <= W; i++) {
            if(wt[0] <= i) dp[0][i] = i/wt[0] * val[0];
        }

        for(int i = 1; i < n; i++) {
            for(int j = 0; j <= W; j++) {
                int exclude = dp[i - 1][j];
                int include = 0;
                if(wt[i] <= j) {
                    include = val[i] + dp[i][j - wt[i]];
                }

                dp[i][j] = Math.max(include, exclude);
            }
        }

        return dp[n-1][W];
    }

    public int unboundedKnapsackSpaceOptimization(int[] val, int[] wt, int W) {
        int n = val.length;

        int[] dp = new int[W + 1];

        for(int i = 0; i <= W; i++) {
            if(wt[0] <= i) dp[i] = i/wt[0] * val[0];
        }

        for(int i = 1; i < n; i++) {
            for(int j = 0; j <= W; j++) {
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

    public int CoinChange(int[] coins, int amount) {
        int n = coins.length;

        int[][] dp = new int[n][amount + 1];
        for(int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }

        int result = helper(coins, n - 1,  amount, dp);
        if(result == (int)1e9) return -1;
        return result;
    }

    private int helper(int[] coins, int index, int target, int[][] dp) {
        if(index == 0) {
            if(target % coins[0] == 0) return target/coins[0];
            return (int)1e9;
        }

        if(dp[index][target] != -1)  return dp[index][target];

        int exclude = helper(coins, index - 1, target, dp);
        int include = (int)1e9;
        if(coins[index] <= target) {
            include = 1 +  helper(coins, index, target - coins[index], dp);
        }

        return dp[index][target] = Math.min(include, exclude);
    }

    public int CoinChangeTabulation(int[] coins, int amount) {
        int n = coins.length;

        int[][] dp = new int[n][amount + 1];
        for(int i = 0; i <= amount; i++) {
            if(i % coins[0] == 0) {
                dp[0][i] = i / coins[0];
            }else{
                dp[0][i] = (int)1e9;
            }
        }

        for(int i = 1; i < n; i++) {
            for(int j = 0; j <= amount; j++) {
                int exclude = dp[i - 1][j];
                int include = (int)1e9;
                if(coins[i] <= j) {
                    include = 1 +  dp[i][j - coins[i]];
                }

                dp[i][j] = Math.min(include, exclude);
            }
        }

        return dp[n - 1][amount] ==  (int)1e9 ? -1 : dp[n - 1][amount];
    }

    public int CoinChangeOptimization(int[] coins, int amount) {
        int n = coins.length;

        int[] dp = new int[amount + 1];
        for(int i = 0; i <= amount; i++) {
            if(i % coins[0] == 0) {
                dp[i] = i/coins[0];
            }else{
                dp[i] = (int)1e9;
            }
        }

        for(int i = 1; i < n; i++) {
            for(int j = 0; j <= amount; j++) {
                int exclude = dp[j];
                int include = (int)1e9;
                if(coins[i] <= j) {
                    include = 1 +  dp[j - coins[i]];
                }

                dp[j] = Math.min(include, exclude);
            }
        }

        return dp[amount] ==  (int)1e9 ? -1 : dp[amount];
    }

    public int CoinChange2(int[] coins, int amount) {
        int n = coins.length;

        int[][] dp = new int[n][amount + 1];
        for(int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }

        return coinChange2Helper(coins, n - 1,  amount, dp);
    }

    private int coinChange2Helper(int[] coins, int index, int target, int[][] dp) {
        if(index == 0) {
            if(target % coins[0] == 0) return 1;
            return 0;
        }

        if(dp[index][target] != -1)  return dp[index][target];

        int exclude = coinChange2Helper(coins, index - 1, target, dp);
        int include = 0;
        if(coins[index] <= target) {
            include = coinChange2Helper(coins, index, target - coins[index], dp);
        }

        return dp[index][target] = include + exclude;
    }

    public int coinChange2Tabulation(int[] coins, int amount) {
        int n = coins.length;
        int[][] dp = new int[n][amount + 1];

        for(int i = 0; i <= amount; i++) {
            if(i % coins[0] == 0) {
                dp[0][i] = 1;
            }
        }

        for(int i = 1; i < n; i++) {
            for(int j = 0; j <= amount; j++) {
                int exclude = dp[i - 1][j];
                int include = 0;
                if(coins[i] <= j) {
                    include = dp[i][j - coins[i]];
                }

                dp[i][j] = include + exclude;
            }
        }

        return dp[n-1][amount];
    }

    public int coinChange2SpaceOptimization(int[] coins, int amount) {
        int n = coins.length;
        int[] dp = new int[amount + 1];

        for(int i = 0; i <= amount; i++) {
            if(i % coins[0] == 0) {
                dp[i] = 1;
            }
        }

        for(int i = 1; i < n; i++) {
            for(int j = 0; j <= amount; j++) {
                int exclude = dp[j];
                int include = 0;
                if(coins[i] <= j) {
                    include = dp[j - coins[i]];
                }

                dp[j] = include + exclude;
            }
        }

        return dp[amount];
    }

    public int rodCutting(int[] price, int N) {
        int n = price.length;

        int[][] dp = new int[n + 1][N + 1];
        for(int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], -1);
        }

        return rodCuttingHelper(price, n - 1, N, dp);
    }

    private int rodCuttingHelper(int[] price, int index, int target, int[][] dp) {
        if(index == 0) {
            return target * price[index];
        }

        if(dp[index][target] != -1)  return dp[index][target];

        int  exclude = rodCuttingHelper(price, index - 1, target, dp);
        int include = 0;
        if(index + 1 <= target) {
            include = price[index] + rodCuttingHelper(price, index, target - (index + 1), dp);
        }

        return dp[index][target] = Math.max(include, exclude);
    }

    public int rodCuttingTabulation(int[] price, int N) {
        int n =  price.length;
        int[][] dp = new int[n][N + 1];

        for(int i = 0; i <= N; i++) {
            dp[0][i] = i * price[0];
        }

        for(int i = 1; i < n; i++) {
            for(int j = 0; j <= N; j++) {
                int exclude = dp[i - 1][j];
                int include = 0;
                if(i + 1 <= j) {
                    include = price[i] + dp[i][j - (i+1)];
                }

                dp[i][j] = Math.max(include, exclude);
            }
        }

        return dp[n-1][N];
    }

    public int rodCuttingSpaceOptimization(int[] price, int N) {
        int n =  price.length;
        int[] dp = new int[N + 1];

        for(int i = 0; i <= N; i++) {
            dp[i] = i * price[0];
        }

        for(int i = 1; i < n; i++) {
            for(int j = 0; j <= N; j++) {
                int exclude = dp[j];
                int include = 0;
                if(i + 1 <= j) {
                    include = price[i] + dp[j - (i+1)];
                }

                dp[j] = Math.max(include, exclude);
            }
        }

        return dp[N];
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

        System.out.println(sq.countPartitionDifference(new int[]{1,1,2,3}, 1));
        System.out.println(sq.countPartitionDifferenceTabulation( new int[]{0,0,0,0}, 0));
        System.out.println(sq.countPartitionDifferenceSpaceOptimization(new int[]{1,1,2,3}, 1));

        System.out.println(sq.unboundedKnapsack(new int[]{5, 11, 13}, new int[]{2,4,6}, 8));
        System.out.println(sq.unboundedKnapsack(new int[]{5, 11, 13}, new int[]{2,4,6}, 8));
        System.out.println(sq.unboundedKnapsackSpaceOptimization(new int[]{5, 11, 13}, new int[]{2,4,6}, 8));

        System.out.println(sq.CoinChange(new int[]{1,2,5}, 11));
        System.out.println(sq.CoinChangeTabulation(new int[]{1,2,5}, 11));
        System.out.println(sq.CoinChangeOptimization(new int[]{1,2,5}, 11));

        System.out.println(sq.CoinChange2(new int[]{1,2,5}, 5));
        System.out.println(sq.coinChange2Tabulation( new int[]{1,2,5}, 5));
        System.out.println(sq.coinChange2SpaceOptimization(new int[]{1,2,5}, 5));

        System.out.println(sq.rodCutting(new int[]{1, 5, 8, 9}, 4));
        System.out.println(sq.rodCuttingTabulation(new int[]{1, 5, 8, 9}, 4));
        System.out.println(sq.rodCuttingSpaceOptimization(new int[]{1, 5, 8, 9}, 4));
    }
}
