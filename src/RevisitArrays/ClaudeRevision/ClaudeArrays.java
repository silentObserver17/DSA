package RevisitArrays.ClaudeRevision;

import java.util.Arrays;

class ArraysRevisionByClaude{
    public int MaximumSubarrayDp(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        int maxValue =  nums[0];

        for(int i = 1; i < n; i++){
            dp[i] = Math.max(dp[i-1] + nums[i], nums[i]);

            maxValue = Math.max(maxValue, dp[i]);
        }

        return maxValue;
    }

    public int MaximumSubarraySpaceOptimization(int[] nums) {
        int n = nums.length;
        int currentMax = nums[0];
        int maxValue = nums[0];

        for(int i = 1; i < n; i++){
            currentMax = Math.max(currentMax + nums[i], nums[i]);

            maxValue = Math.max(maxValue, currentMax);
        }
        return maxValue;
    }

    public int MaximumProductSubarrayDp(int[] nums) {
        int n = nums.length;
        int[] maxDp = new int[n];
        int[] minDp = new int[n];
        int maxSoFar = nums[0];
        maxDp[0] = nums[0];
        minDp[0] = nums[0];

        for(int i = 1; i < n; i++){
            int candidate1 = nums[i] * maxDp[i - 1];
            int candidate2 = nums[i] * minDp[i - 1];
            int candidate3 = nums[i];

            maxDp[i] = Math.max(candidate1, Math.max(candidate2, candidate3));
            minDp[i] = Math.min(candidate1, Math.min(candidate2, candidate3));

            maxSoFar = Math.max(maxSoFar, maxDp[i]);
        }

        return maxSoFar;
    }

    public int MaximumProductSpaceOptimization(int[] nums) {
        int n = nums.length;
        int maxDp = nums[0];
        int minDp = nums[0];
        int maxSoFar = nums[0];

        for(int i = 1; i < n; i++){
            int candidate1 = nums[i] * maxDp;
            int candidate2 = nums[i] * minDp;
            int candidate3 = nums[i];

            maxDp = Math.max(candidate1, Math.max(candidate2, candidate3));
            minDp = Math.min(candidate1, Math.min(candidate2, candidate3));

            maxSoFar = Math.max(maxSoFar, maxDp);
        }

        return maxSoFar;
    }

    public int bestTimeToBuySellStocks(int[] prices) {
        int n = prices.length;
        int[] dp = new int[n];
        int minSoFar =  prices[0];
        int maxSoFar = 0;

        for(int i = 1; i < n; i++){
            dp[i] =  prices[i] - minSoFar;

            minSoFar = Math.min(minSoFar, prices[i]);

            maxSoFar =  Math.max(maxSoFar, dp[i]);
        }

        return maxSoFar;
    }

    public int bestTimeToBuySellStocksSpaceOptimized(int[] prices) {
        int n = prices.length;
        int minSoFar =  prices[0];
        int maxSoFar = 0;

        for(int i = 1; i < n; i++){
            int profit =  prices[i] - minSoFar;

            minSoFar = Math.min(minSoFar, prices[i]);

            maxSoFar =  Math.max(maxSoFar, profit);
        }

        return maxSoFar;
    }

    private void reverseArray(int[] nums, int startIndex, int endIndex) {
        int low = startIndex;
        int high = endIndex;

        while(low < high) {
            int temp = nums[low];
            nums[low] = nums[high];
            nums[high] = temp;

            low++;
            high--;
        }
    }

    public void nextPermutation(int[] nums) {
        int n = nums.length;
        int pivot = -1;

        for(int i = n-2; i >= 0; i--) {
            if(nums[i] < nums[i+1]) {
                pivot = i;
                break;
            }
        }

        if(pivot == -1) {
            reverseArray(nums, 0, n-1);
        }else{
            int nextMaxIndex = -1;

            for(int i = n-1; i > pivot; i--) {
                if(nums[i] > nums[pivot]) {
                    nextMaxIndex = i;
                    break;
                }
            }

            int temp = nums[pivot];
            nums[pivot] = nums[nextMaxIndex];
            nums[nextMaxIndex] = temp;

            reverseArray(nums, pivot+1, n-1);
        }

        System.out.println(Arrays.toString(nums));
    }
}

public class ClaudeArrays {
    public static void main(String[] args) {
        ArraysRevisionByClaude arc = new ArraysRevisionByClaude();
        System.out.println(arc.MaximumSubarrayDp(new int[]{-2, -5}));
        System.out.println(arc.MaximumSubarraySpaceOptimization(new int[]{-2, -5}));

        System.out.println(arc.MaximumProductSubarrayDp(new int[]{2, -3, -2, 5}));
        System.out.println(arc.MaximumProductSpaceOptimization(new int[]{2, -3, -2, 5}));

        System.out.println(arc.bestTimeToBuySellStocks(new int[]{2,1}));
        System.out.println(arc.bestTimeToBuySellStocksSpaceOptimized(new int[]{2,1}));

        arc.nextPermutation(new int[]{1, 5, 8, 4, 7, 6, 5, 3});
    }
}
