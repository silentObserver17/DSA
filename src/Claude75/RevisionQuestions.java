package Claude75;

import java.lang.reflect.Array;
import java.util.*;

class ArrayQuestions{
    public int[] TwoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for(int i = 0; i < nums.length; i++){
            int prefix =  target - nums[i];
            if(map.containsKey(prefix)){
                return new int[]{map.get(prefix), i};
            }

            map.put(nums[i], i);
        }

        return new int[]{-1,-1};
    }

    private int[] getFrequency(String s){
        int[] freq = new int[26];
        for(int i = 0; i < s.length(); i++){
            freq[s.charAt(i) - 'a']++;
        }
        return freq;
    }

    private boolean isSameGroup(int[] a, int[] b) {
        for(int i = 0; i < 26; i++){
            if(a[i] != b[i]) {
                return false;
            }
        }

        return true;
    }

    public List<List<String>> GroupAnagramsBruteForce(String[] strs) {
        int n =  strs.length;
        List<List<String>> ans = new ArrayList<>();
        boolean[] visited = new boolean[n];

        for(int i = 0; i < n; i++){
            if(visited[i]) continue;

            List<String> group = new ArrayList<>();
            group.add(strs[i]);
            visited[i] = true;

            int[] freq1 = getFrequency(strs[i]);

            for(int j = i + 1; j < n; j++) {
                if(visited[j]) continue;

                int[] freq2 = getFrequency(strs[j]);

                if(isSameGroup(freq1, freq2)){
                    group.add(strs[j]);
                    visited[j] = true;
                }
            }

            ans.add(group);
        }

        return ans;
    }

    public List<List<String>> GroupAnagrams(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<>();

        for(String s : strs){
            char[] chars = s.toCharArray();
            Arrays.sort(chars);

            String newString =  String.valueOf(chars);

            if(!map.containsKey(newString)){
                map.put(newString, new ArrayList<>());
            }

            map.get(newString).add(s);
        }

        return new ArrayList<>(map.values());
    }

    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] prefix = new int[n];
        int[] suffix = new int[n];

        prefix[0] = 1;
        suffix[n - 1] = 1;

        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] * nums[i - 1];
        }

        for (int i = n - 2; i >= 0; i--) {
            suffix[i] = suffix[i + 1] * nums[i + 1];
        }

        int[] output = new int[n];
        for (int i = 0; i < n; i++) {
            output[i] = prefix[i] * suffix[i];
        }

        return output;
    }

    public int[] productExceptSelfOptimized(int[] nums) {
        int n = nums.length;
        int[] output = new int[n];
        output[0] = 1;

        for(int i = 1; i < n; i++) {
            output[i] = output[i - 1] * nums[i - 1];
        }

        int suffixProduct = 1;
        for(int i = n - 1; i >= 0; i--) {
            output[i] *= suffixProduct;

            suffixProduct *= nums[i];
        }

        return output;
    }

    public int longestConsecutive(int[] nums) {
        HashSet<Integer> set = new HashSet<>();

        int count = 0;
        for(int num : nums){
            set.add(num);
        }

        for(int num : set){
            if(!set.contains(num - 1)){
                int length = 0;
                while(set.contains(num + length)) {
                    length++;
                }

                count =  Math.max(count, length);
            }
        }

        return count;
    }

    public int SubarraySumEqualToK(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        int prefix = 0;
        int count = 0;

        for(int i = 0; i < nums.length; i++){
            prefix += nums[i];

            int target = prefix - k;
            if(map.containsKey(target)){
                count += map.get(target);
            }

            map.put(prefix, map.getOrDefault(prefix, 0) + 1);
        }

        return count;
    }

    private void swap(int[] nums, int first, int last) {
        int temp = nums[first];
        nums[first] = nums[last];
        nums[last] = temp;
    }

    public int firstMissingPositive(int[] nums) {
        int n = nums.length;

        for(int  i = 0; i < n; i++){
            while(nums[i] >= 1 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1);
            }
        }

        for(int i = 0; i < n; i++){
            if(nums[i] != i+1){
                return i+1;
            }
        }

        return n + 1;
    }
}

class TwoPointers {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);

        for(int i = 0; i < nums.length; i++){
            if(i > 0 && nums[i] == nums[i-1]) continue;

            int lo = i+1, hi = nums.length-1;
            while(lo < hi){
                int sum = nums[i] + nums[lo] + nums[hi];
                if(sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[lo], nums[hi]));
                    while(lo < hi && nums[lo] == nums[lo+1]) lo++;
                    while(lo < hi && nums[hi] == nums[hi-1]) hi--;
                    lo++; hi--;
                }else if(sum < 0) {
                    lo++;
                }else{
                    hi--;
                }
            }
        }

        return res;
    }

    public int containerWithMaxWater(int[] height) {
        int n = height.length;
        int lo = 0;
        int hi =  n - 1;
        int maxHeight = 0;

        while(lo < hi){
            int minHeight = Math.min(height[lo], height[hi]);
            int width = hi - lo;

            maxHeight = Math.max(maxHeight, minHeight * width);

            if(height[lo] > height[hi]){
                hi--;
            }else{
                lo++;
            }
        }

        return maxHeight;
    }

    private void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public void sortColors(int[] nums) {
        int n = nums.length;
        int low = 0;
        int mid = 0;
        int high = n - 1;

        while(mid <= high){
            if(nums[mid] == 0) {
                swap(nums, low, mid);
                low++;
                mid++;
            }else if(nums[mid] == 1) {
                mid++;
            }else{
                swap(nums, mid, high);
                high--;
            }
        }
        System.out.println(Arrays.toString(nums));
    }

    public int trappingRainWaterBetter(int[] height) {
        int n = height.length;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];

        leftMax[0] = height[0];
        for(int i = 1; i < n; i++){
            leftMax[i] = Math.max(leftMax[i-1], height[i]);
        }

        rightMax[n-1] = height[n-1];
        for(int i = n-2; i >= 0; i--){
            rightMax[i] = Math.max(rightMax[i+1], height[i]);
        }

        int[] water = new int[n];

        for(int i = 0; i < n; i++) {
            water[i] = Math.min(leftMax[i], rightMax[i]) -  height[i];
        }

        return Arrays.stream(water).sum();
    }

    public int trappingRainWater(int[] height) {
        int n = height.length;

        int lo = 0, hi = n - 1;
        int leftMax = height[0];
        int rightMax = height[n - 1];
        int result = 0;

        while(lo <= hi){
            if(leftMax <= rightMax) {
                if(height[lo] > leftMax) {
                    leftMax = height[lo];
                }else{
                    result +=  leftMax - height[lo];
                }
                lo++;
            }else{
                if(height[hi] > rightMax) {
                    rightMax = height[hi];
                }else{
                    result +=  rightMax - height[hi];
                }
                hi--;
            }
        }

        return result;
    }
}

class SlidingWindow {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        HashMap<Character, Integer> map = new HashMap<>();
        int left = 0;
        int maxLength = 0;

        for(int right = 0; right < n; right++) {
            if(map.containsKey(s.charAt(right))){
                left = Math.max(left,  map.get(s.charAt(right))+1);
            }

            int windowLength = right - left + 1;
            maxLength = Math.max(maxLength, windowLength);
            map.put(s.charAt(right), right);
        }

        return maxLength;
    }

    public int characterReplacement(String s, int k) {
        HashMap<Character, Integer> map = new HashMap<>();
        int n = s.length();
        int maxFreq = 0;
        int maxLength = 0;
        int left = 0;

        for(int right = 0; right < n; right++){
            map.put(s.charAt(right), map.getOrDefault(s.charAt(right), 0) + 1);
            maxFreq = Math.max(maxFreq, map.get(s.charAt(right)));

            if(((right - left) + 1 - maxFreq) > k){
                map.put(s.charAt(left), map.get(s.charAt(left)) - 1);
                left++;
            }

            maxLength = Math.max(maxLength, (right - left + 1));

        }

        return maxLength;
    }

    public String minWindow(String s, String t) {
        if (t.isEmpty() || s.length() < t.length()) return "";

        int[] need = new int[128];
        int matched = 0;
        int required = 0;

        for(char c : t.toCharArray()){
            if(need[c] == 0) required++;
            need[c]++;
        }
        
        int left = 0;
        int minLength = Integer.MAX_VALUE;
        int startIndex = -1;
        int[] have =  new int[128];
        
        for(int right = 0; right < s.length(); right++){
            have[s.charAt(right)]++;
            
            if(have[s.charAt(right)] == need[s.charAt(right)]){
                matched++;
            }

            while(required == matched && left <= right){
                int currentLength = right - left + 1;
                if(currentLength < minLength){
                    minLength = currentLength;
                    startIndex = left;
                }

                have[s.charAt(left)]--;
                if(have[s.charAt(left)] == need[s.charAt(left)] - 1) matched--;
                left++;
            }
        }

        return startIndex != -1 ? s.substring(startIndex, startIndex + minLength) : "";
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();
        int ansIndex = 0;

        for(int right = 0; right < n; right++){
            if(!deque.isEmpty() && deque.peekFirst() <= right - k) {
                deque.pollFirst();
            }

            while(!deque.isEmpty() && nums[deque.peekLast()] <= nums[right]){
                deque.pollLast();
            }

            deque.offerLast(right);

            if(!deque.isEmpty() && right >= k - 1){
                result[ansIndex] = nums[deque.peekFirst()];
                ansIndex++;
            }
        }

        return result;
    }
}

class BinarySearch{
    private int firstOccurrence(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        int ans = -1;

        while(low <= high){
            int mid =  low + (high - low)/2;
            if(nums[mid] == target){
                ans = mid;
                high = mid - 1;
            }else if(nums[mid] < target){
                low = mid + 1;
            }else{
                high = mid - 1;
            }
        }

        return ans;
    }

    private int lastOccurrence(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        int ans = -1;

        while(low <= high){
            int mid =  low + (high - low)/2;
            if(nums[mid] == target){
                ans = mid;
                low = mid + 1;
            }else if(nums[mid] < target){
                low = mid + 1;
            }else {
                high = mid - 1;
            }
        }

        return ans;
    }

    public int[] searchRange(int[]nums, int target){
        int first = firstOccurrence(nums, target);
        int last = lastOccurrence(nums, target);

        return new int[]{first, last};
    }
}


public class RevisionQuestions {
    public static void main(String[] args) {
        System.out.println("======================= ARRAY QUESTIONS ====================================");
        ArrayQuestions aq = new ArrayQuestions();

        System.out.println(Arrays.toString(aq.TwoSum(new int[]{2, 7, 11, 15}, 9)));

        System.out.println(aq.GroupAnagramsBruteForce(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"}));
        System.out.println(aq.GroupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"}));

        System.out.println(Arrays.toString(aq.productExceptSelf(new int[]{1,2,3,4})));
        System.out.println(Arrays.toString(aq.productExceptSelfOptimized(new int[]{1,2,3,4})));

        System.out.println(aq.longestConsecutive(new int[]{100,4,200,1,3,2}));

        System.out.println(aq.SubarraySumEqualToK(new int[]{1,1,1}, 2));

        System.out.println(aq.firstMissingPositive(new int[]{3, 4, -1, 1}));

        System.out.println("==================== TWO POINTERS ===============================================");
        TwoPointers tp = new  TwoPointers();
        System.out.println(tp.threeSum(new int[]{-2, -2, 0, 0, 2, 2}));

        System.out.println(tp.containerWithMaxWater(new int[]{1,8,6,2,5,4,8,3,7}));

        tp.sortColors(new int[]{1,2,0});

        System.out.println(tp.trappingRainWaterBetter(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}));
        System.out.println(tp.trappingRainWater(new int[]{5, 1, 6, 1, 5}));

        System.out.println("=================== SLIDING WINDOW =================================================");
        SlidingWindow sw = new SlidingWindow();
        System.out.println(sw.lengthOfLongestSubstring(""));

        System.out.println(sw.characterReplacement("", 0));

        System.out.println(sw.minWindow("a", "a"));

        System.out.println(Arrays.toString(sw.maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3)));

        System.out.println("=================== BINARY SEARCH =================================================");
        BinarySearch bs = new BinarySearch();
        System.out.println(Arrays.toString(bs.searchRange(new int[]{5,7,7,8,8,10}, 8)));
    }
}
