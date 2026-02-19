package SlindingWindowPointers;

import java.util.HashMap;
import java.util.Map;

public class CountingSubarrays {
    public int numberOfSubstringsBrute(String s) {
        int count = 0;

        for(int i = 0; i < s.length(); i++) {
            int[] hash = new int[3];
            for(int j = i; j < s.length(); j++) {
                hash[s.charAt(j) - 'a'] = 1;

                if(hash[0] + hash[1] + hash[2] == 3) {
                    count++;
                }
            }
        }

        return count;
    }

    public int numberOfSubstring(String s){
        int[] freq = new int[3];
        int left = 0;
        int count= 0;

        for(int right = 0; right < s.length(); right++) {
            freq[s.charAt(right) - 'a']++;

            while(freq[0] > 0 && freq[1] > 0 && freq[2] > 0){
                count += s.length() - right;

                freq[s.charAt(left) - 'a']--;
                left++;
            }
        }

        return count;
    }

    public int numberOfSubstringOptimal(String s){
        int n = s.length();
        long count = 0;

        int lastA = -1, lastB = -1, lastC = - 1;

        for(int right = 0; right < n; right++) {
            char ch = s.charAt(right);

            if(ch == 'a') lastA = right;
            else if(ch == 'b') lastB = right;
            else if(ch == 'c') lastC = right;

            if(lastA >= 0 && lastB >= 0 && lastC >= 0){
                int earliest = Math.min(lastA, Math.min(lastB, lastC));
                count += (earliest + 1);
            }
        }

        return (int)count;
    }

    public int numSubarraysWithSumBrute(int[] nums, int goal) {
        int count = 0;

        for(int i = 0; i < nums.length; i++){
            int sum = 0;
            for(int j = i; j < nums.length; j++){
                sum += nums[j];
                if(sum == goal) count++;
            }
        }

        return count;
    }

    public int numSubarraysWithSum(int[] nums, int goal) {
        Map<Integer, Integer> map = new HashMap<>();
        int currSum = 0;
        int count = 0;
        map.put(0, 1);

        for(int i = 0; i < nums.length; i++){
            currSum += nums[i];

            if(map.containsKey(currSum - goal)){
                count +=  map.get(currSum - goal);
            }

            map.put(currSum, map.getOrDefault(currSum, 0) + 1);
        }

        return count;
    }

    public int numSubArraysWithSumSliding(int[] nums, int goal){
        if(goal == 0) return atmost(nums, goal);

        int result = atmost(nums, goal) - atmost(nums, goal - 1);
        return result;
    }

    private int atmost(int[] nums, int k){
        int sum = 0;
        int left = 0;
        int count = 0;

        for(int right = 0; right < nums.length; right++){
            sum += nums[right];

            while(sum > k){
                sum -= nums[left];
                left++;
            }

            count += (right - left) + 1;
        }

        return count;
    }

    public int numberOfSubarraysBrute(int[] nums, int k) {
        int n = nums.length;

        int count = 0;
        for (int i = 0; i < n; i++) {
            int oddCount = 0;
            for (int j = i; j < n; j++) {
                if (nums[j] % 2 == 1) oddCount++;
                if (oddCount == k) count++;
            }
        }

        return count;
    }

    public int numberOfSubarrays(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        int curr = 0;
        int count = 0;

        for(int i = 0; i < nums.length; i++){
            curr += nums[i] % 2;

            if(map.containsKey(curr - k)){
                count +=  map.get(curr - k);
            }

            map.put(curr, map.getOrDefault(curr, 0) + 1);
        }

        return count;
    }

    private int atmostSubArrays(int[] nums, int k) {
        int sum = 0;
        int left = 0;
        int count = 0;
        for(int right = 0; right < nums.length; right++){
            sum += nums[right] % 2;

            while(sum > k){
                sum -= nums[left] % 2;
                left++;
            }
            count += (right - left) + 1;
        }

        return count;
    }

    public int numberOfSubarraysSliding(int[] nums, int k) {
        if(k == 0) return numSubArraysWithSumSliding(nums, k);

        int result = atmostSubArrays(nums, k) - atmostSubArrays(nums, k - 1);
        return result;
    }

    public int maxAreaBrute(int[] height) {
        int n = height.length;

        int maxArea = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                int area = (j - i) * Math.min(height[i], height[j]);
                maxArea = Math.max(maxArea, area);
            }
        }
        return maxArea;
    }

    public int maxArea(int[] height){
        int left = 0;
        int right = height.length - 1;

        int maxArea = 0;
        while(left < right){
            int currentArea = (right - left) * Math.min(height[right], height[left]);
            maxArea = Math.max(maxArea, currentArea);

            if(height[left] < height[right]) left++;
            else right--;
        }

        return maxArea;
    }



}
