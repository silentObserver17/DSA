package Basics;

import java.util.HashMap;
import java.util.Map;

public class BasicHashMap {
    public int mostFrequentElementBrute(int[] nums) {
        int maxCount = -1;
        int result = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length; i++) {
            int count = 0;
            for(int j = 0; j < nums.length; j++) {
                if(nums[j] == nums[i]) {
                    count++;
                }
            }

            if(count == maxCount) {
                result = Math.min(result, nums[i]);
            }
            else if(count > maxCount) {
                maxCount = count;
                result = nums[i];
            }
        }

        return result;
    }

    public int mostFrequentElement(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int result = Integer.MIN_VALUE;
        int maxCount = -1;

        for (int i = 0; i < nums.length; i++) {
            int count = map.getOrDefault(nums[i], 0) + 1;
            map.put(nums[i], count);

            if(count > maxCount || (count == maxCount && nums[i] < result)) {
                maxCount = count;
                result = nums[i];
            }
        }

        return result;
    }

    public int secondMostFrequentElement(int[] nums) {
        if(nums == null || nums.length == 0 || nums.length == 1) return -1;
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        int maxFreq = 0, secMaxFreq = 0;
        int maxEle = -1, secEle = -1;

        for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int element =  entry.getKey();
            int freq = entry.getValue();

            if(freq > maxFreq) {
                secMaxFreq = maxFreq;
                secEle = maxEle;
                maxFreq = freq;
                maxEle = element;
            }
            else if(freq == maxFreq){
                maxEle = Math.min(maxEle, element);
            }
            else if(freq > secMaxFreq){
                secMaxFreq = freq;
                secEle = element;
            }
            else if(freq == secMaxFreq){
                maxEle = Math.min(secEle, element);
            }
        }

        return secEle;
    }

    public int secondMostFrequentElement2(int[] nums) {
        if (nums == null || nums.length <= 1) return -1;

        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        int maxFreq = -1, secMaxFreq = -1;
        int secEle = Integer.MAX_VALUE;

        // First pass: find max frequency
        for (int freq : map.values()) {
            maxFreq = Math.max(maxFreq, freq);
        }

        // Second pass: find second distinct frequency
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int ele = entry.getKey();
            int freq = entry.getValue();

            if (freq < maxFreq) {
                if (freq > secMaxFreq ||
                        (freq == secMaxFreq && ele < secEle)) {
                    secMaxFreq = freq;
                    secEle = ele;
                }
            }
        }

        return secMaxFreq == -1 ? -1 : secEle;
    }

    public int leastFrequentElement(int[] nums) {
        if (nums == null || nums.length == 0) return -1;

        HashMap<Integer, Integer> map = new HashMap<>();

        // Step 1: Build frequency map
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        int minCount = Integer.MAX_VALUE;
        int result = Integer.MAX_VALUE;

        // Step 2: Find least frequent element
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int element = entry.getKey();
            int count = entry.getValue();

            if (count < minCount ||
                    (count == minCount && element < result)) {
                minCount = count;
                result = element;
            }
        }

        return result;
    }

    private int MostFrequentElementCount(int[] nums) {
        if (nums == null || nums.length == 0) return -1;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        int mostFreq = Integer.MIN_VALUE;
        int mostElement =  Integer.MIN_VALUE;

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int element = entry.getKey();
            int freq =  entry.getValue();

            if (freq > mostFreq || (freq ==  mostFreq && element < mostElement)) {
                mostFreq = freq;
                mostElement = element;
            }
        }

        return mostFreq != Integer.MIN_VALUE ? mostFreq : 0;
    }

    public int leastFrequencyCount(int[] nums) {
        if (nums == null || nums.length == 0) return -1;

        HashMap<Integer, Integer> map = new HashMap<>();

        // Step 1: Build frequency map
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        int minCount = Integer.MAX_VALUE;
        int result = Integer.MAX_VALUE;

        // Step 2: Find least frequent element
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int element = entry.getKey();
            int count = entry.getValue();

            if (count < minCount ||
                    (count == minCount && element < result)) {
                minCount = count;
                result = element;
            }
        }

        return minCount == Integer.MAX_VALUE ? 0 : minCount;
    }

    public int sumHighestAndLowestFrequency(int[] nums) {
        int mostFrequent = MostFrequentElementCount(nums);
        int leastFrequent = leastFrequencyCount(nums);

        return mostFrequent + leastFrequent;
    }

    public int sumHighestAndLowestFrequency2(int[] nums) {
        if (nums == null || nums.length == 0) return -1;

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        int maxFreq = Integer.MIN_VALUE;
        int minFreq = Integer.MAX_VALUE;

        for (int freq : map.values()) {
            maxFreq = Math.max(maxFreq, freq);
            minFreq = Math.min(minFreq, freq);
        }

        return maxFreq + minFreq;
    }


}
