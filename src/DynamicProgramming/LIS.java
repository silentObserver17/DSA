package DynamicProgramming;

import java.util.*;

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

    public List<Integer> PrintLCS(int[] arr) {
        int n = arr.length;

        int[] dp = new int[n];
        int[] parent = new int[n];

        Arrays.fill(dp, 1);
        Arrays.fill(parent, -1);

        int maxLength = 1;
        int endIndex = 0;

        for(int index = 0; index < n; index++) {
            for(int prev = 0; prev < index; prev++) {
                if(arr[index] > arr[prev] && dp[prev] + 1 >  dp[index]) {
                    dp[index] = dp[prev] + 1;
                    parent[index] = prev;
                }
            }

            if(dp[index] > maxLength) {
                maxLength = dp[index];
                endIndex = index;
            }
        }

        List<Integer> lis =  new ArrayList<>();

        while(endIndex != - 1) {
            lis.add(arr[endIndex]);
            endIndex = parent[endIndex];
        }

        Collections.reverse(lis);

        return lis;
    }

    public List<Integer> LargestDivisibleSubset(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);

        int[] parent = new  int[n];
        int[] dp = new int[n];

        Arrays.fill(dp, 1);
        Arrays.fill(parent, -1);

        int maxLength = 1;
        int endIndex = 0;

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < i; j++) {
                if(nums[i] % nums[j] == 0 && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    parent[i] = j;
                }
            }

            if(dp[i] > maxLength) {
                maxLength = dp[i];
                endIndex = i;
            }
        }

        List<Integer> lis =  new ArrayList<>();

        while(endIndex != - 1) {
            lis.add(nums[endIndex]);
            endIndex = parent[endIndex];
        }

        Collections.reverse(lis);
        return lis;
    }

    private boolean isPredecessor(String a, String b) {
        if(a.length() + 1 != b.length()) return false;

        int i = 0;
        int j = 0;

        while(i < a.length() && j < b.length()) {
            if(a.charAt(i) == b.charAt(j)) {
                i++;
                j++;
            }else{
                j++;
            }
        }

        return i == a.length();
    }

    public int longestStrChain(String[] words) {
        int n =  words.length;

        Arrays.sort(words, Comparator.comparingInt(String::length));

        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int maxLength = 1;

        for(int index = 0; index < n; index++) {
            for(int prev = 0; prev < index; prev++) {
                if(isPredecessor(words[prev], words[index]) && dp[prev] + 1 > dp[index]) {
                    dp[index] = dp[prev] + 1;
                }
            }
            if(dp[index] > maxLength) {
                maxLength = dp[index];
            }
        }

        return maxLength;
    }


    public int longestBitonicSubsequence(int[] nums) {
        int n = nums.length;

        int[] lis = new int[n];
        int[] lds = new int[n];

        Arrays.fill(lis, 1);
        Arrays.fill(lds, 1);

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < i; j++) {
                if(nums[i] > nums[j] && lis[j] + 1 > lis[i]) {
                    lis[i] = lis[j] + 1;
                }
            }
        }

        for(int i = n - 1; i >= 0; i--) {
            for(int j = n - 1; j > i; j--) {
                if(nums[i] > nums[j] && lds[j] + 1 > lds[i]) {
                    lds[i] = lds[j] + 1;
                }
            }
        }

        int maxLen = 0;
        for(int i = 0; i < n; i++) {
            maxLen = Math.max(maxLen, lis[i] + lds[i] - 1);
        }

        return maxLen;
    }

    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;

        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int[] count = new int[n];
        Arrays.fill(count, 1);

        int maxLen = 1;

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < i; j++) {
                if(nums[i] > nums[j] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    count[i] = count[j];
                }else if(nums[i] > nums[j] && dp[j] + 1 == dp[i]) {
                    count[i] += count[j];
                }
            }

            if(dp[i] > maxLen) {
                maxLen = dp[i];
            }
        }

        int maxCount = 0;

        for(int i = 0; i < n; i++) {
            if(dp[i] == maxLen) {
                maxCount += count[i];
            }
        }

        return maxCount;
    }

    public int maxSumIS(int[] nums) {
        int n = nums.length;

        int[] dp = new int[n];
        for(int i = 0; i < n; i++) {
            dp[i] = nums[i];
        }

        int maxSum = Integer.MIN_VALUE;

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < i; j++) {
                if(nums[i] > nums[j] && dp[j] + nums[i] > dp[i]) {
                    dp[i] = dp[j] + nums[i];
                }
            }

            if(dp[i] > maxSum) {
                maxSum = dp[i];
            }
        }

        return maxSum;
    }

    public int maxEnvelopes(int[][] envelopes) {
        int n = envelopes.length;

        Arrays.sort(envelopes, (a, b) -> {
            if(a[0] == b[0]) {
                return Integer.compare(b[1], a[1]);
            }
            return Integer.compare(a[0], b[0]);
        });

        int[] tails = new int[n];
        int size = 0;

        for(int[] num : envelopes) {
            int start = 0;
            int end = size;

            while(start < end) {
                int mid = start + (end - start) / 2;

                if(tails[mid] >= num[1]) {
                    end = mid;
                }else{
                    start = mid + 1;
                }
            }

            tails[start] = num[1];

            if(start == size) {
                size++;
            }
        }

        return size;
    }
}

public class LIS {
    public static void main(String[] args) {
        LISQuestions sol = new LISQuestions();
        System.out.println(sol.LongestIncreasingSubsequence(new int[]{3, 10, 2, 1, 20}));
        System.out.println(sol.LongestIncreasingSubsequenceTabulation(new int[]{3, 10, 2, 1, 20}));
        System.out.println(sol.LongestIncreasingSubsequenceSpaceOptimization(new int[]{3, 10, 2, 1, 20}));
        System.out.println(sol.LISBinarySearch(new int[]{3, 10, 2, 1, 20}));

        System.out.println(sol.PrintLCS(new int[]{2,3,1,5}));

        System.out.println(sol.LargestDivisibleSubset(new int[]{1,2,4,8}));

        System.out.println(sol.longestStrChain(new String[]{"a","b","ba","bca","bda","bdca"}));

        System.out.println(sol.longestBitonicSubsequence(new int[]{5, 1, 4, 2, 3, 6, 8, 7}));

        System.out.println(sol.findNumberOfLIS(new int[]{2,2,2,2,2}));

        System.out.println(sol.maxSumIS(new int[]{1,101,2,3,100}));

        System.out.println(sol.maxEnvelopes(new int[][]{
                {5,4},
                {6,4},
                {6,7},
                {2,3}
        }));
    }
}
