package DynamicProgramming;

import java.util.Arrays;

class LISQuestions {
    public int LongestIncreasingSubsequence(int[] arr) {
        int n = arr.length;

        int[][] dp = new int[n][n+1];
        for(int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }

        return lisHelper(0, -1, arr, dp);
    }

    private int lisHelper(int index, int prevIndex, int[] arr, int[][] dp) {
        if(index == arr.length) return 0;

        if(dp[index][prevIndex + 1] != -1) return dp[index][prevIndex + 1];

        int include = 0;
        if(prevIndex == -1 || arr[index] > arr[prevIndex]) {
            include = 1 + lisHelper(index + 1, index, arr, dp);
        }
        int exclude = lisHelper(index + 1, prevIndex, arr, dp);

        return dp[index][prevIndex + 1] = Math.max(include, exclude);
    }

    public int LongestIncreasingSubsequenceTabulation(int[] arr) {
        int n = arr.length;

        int[][] dp = new int[n+1][n+1];

        for(int index = n - 1; index >= 0; index--) {
            for(int prev = 0; prev <= n; prev++) {
                int prevIndex = prev - 1;

                int include = 0;
                if(prevIndex == -1 || arr[index] > arr[prevIndex]) {
                    include = 1 + dp[index + 1][index + 1];
                }

                int exclude = dp[index + 1][prevIndex + 1];

                dp[index][prevIndex + 1] =  Math.max(include, exclude);
            }
        }

        return dp[0][0];
    }

    public int LongestIncreasingSubsequenceSpaceOptimization(int[] arr) {
        int n = arr.length;

        int[] next = new int[n+1];

        for(int index = n - 1; index >= 0; index--) {
            int[] curr = new int[n + 1];
            for(int prev = 0; prev <= n; prev++) {
                int prevIndex = prev - 1;

                int include = 0;
                if(prevIndex == -1 || arr[index] > arr[prevIndex]) {
                    include = 1 + next[index + 1];
                }

                int exclude = next[prevIndex + 1];

                curr[prevIndex + 1] =  Math.max(include, exclude);
            }
            next = curr;
        }

        return next[0];
    }

    public int LISBinarySearch(int[] nums) {
        int n = nums.length;

        int[] tails = new int[n];
        int size = 0;

        for(int x : nums) {
            int left = 0;
            int right = size;

            while(left < right) {
                int mid = left + (right - left) / 2;

                if(tails[mid] >= x) {
                    right = mid;
                }else{
                    left = mid + 1;
                }
            }

            tails[left] = x;

            if(left == size) {
                size++;
            }
        }

        return size;
    }

    public String PrintLCS(int[] arr) {
        int n = arr.length;

        int[][] dp = new int[n+1][n+1];

        for(int index = n - 1; index >= 0; index--) {
            for(int prev = 0; prev <= n; prev++) {
                int prevIndex = prev - 1;

                int include = 0;
                if(prevIndex == -1 || arr[index] > arr[prevIndex]) {
                    include = 1 + dp[index + 1][index + 1];
                }

                int exclude = dp[index + 1][prevIndex + 1];

                dp[index][prevIndex + 1] =  Math.max(include, exclude);
            }
        }

        System.out.println(Arrays.deepToString(dp));

        return "";
    }
}

public class LIS {
    public static void main(String[] args) {
        LISQuestions sol = new LISQuestions();
        System.out.println(sol.LongestIncreasingSubsequence(new int[]{3, 10, 2, 1, 20}));
        System.out.println(sol.LongestIncreasingSubsequenceTabulation(new int[]{3, 10, 2, 1, 20}));
        System.out.println(sol.LongestIncreasingSubsequenceSpaceOptimization(new int[]{3, 10, 2, 1, 20}));
        System.out.println(sol.LISBinarySearch(new int[]{3, 10, 2, 1, 20}));

        System.out.println(sol.PrintLCS(new int[]{3, 10, 2, 1, 20}));
    }
}
