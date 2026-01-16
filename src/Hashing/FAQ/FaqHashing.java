package Hashing.FAQ;

import java.util.*;

public class FaqHashing {
    public int longestConsecutiveBruteForce(int[] nums){
        int n = nums.length;
        if (n == 0) return 0;

        int maxLen = 1;
        int currLen = 1;

        Arrays.sort(nums);

        for(int i = 1; i < n; i++){
            if (nums[i] == nums[i-1]){
                continue;
            }else if (nums[i] == nums[i-1] + 1){
                currLen++;
            }else {
                maxLen = Math.max(maxLen,currLen);
                currLen = 1;
            }
        }

        return maxLen;
    }

    public int longestConsecutiveBetter(int[] nums){
        int n = nums.length;
        if(n == 0) return 0;

        Arrays.sort(nums);

        int lastSmaller = Integer.MIN_VALUE;
        int count = 0;
        int longest = 1;

        for (int num : nums) {
            if (num - 1 == lastSmaller) {
                count++;
                lastSmaller = num;
            } else if (num != lastSmaller) {
                count = 1;
                lastSmaller = num;
            }
            longest = Math.max(longest, count);
        }

        return longest;
    }

    public int longestConsecutiveOptimal(int[] nums){
        int n = nums.length;
        if(n == 0) return 0;

        HashSet<Integer> set = new HashSet<>();
        int longest = 1;

        for(int num : nums){
            set.add(num);
        }

        for(int num: nums){
            if(!set.contains(num-1)){
                int length = 0;
                while(set.contains(num + length)){
                    length++;
                }
                longest = Math.max(longest, length);
            }
        }

        return longest;
    }

    // TC: Outer Loop O(N)
    // Inner Loop O(N)
    // Sorting K Log K => O(N^2 . K Log K)
    public List<List<String>> GroupAnagramsBruteForce(String[] strs){
        if(strs == null || strs.length == 0) return new ArrayList<>();
        List<List<String>> result = new ArrayList<>();
        boolean[] visited = new boolean[strs.length];

        for(int i = 0; i < strs.length; i++){
            if(visited[i]) continue;

            List<String> list = new ArrayList<>();
            list.add(strs[i]);
            visited[i] = true;

            char[] charsI =  strs[i].toCharArray();
            Arrays.sort(charsI);
            String sortedString = new String(charsI);

            for (int j = i + 1; j < strs.length; j++){
                if(visited[j]) continue;

                char[] chars = strs[j].toCharArray();
                Arrays.sort(chars);
                if(sortedString.equals(new String(chars))){
                    list.add(strs[j]);
                    visited[j] = true;
                }
            }
            result.add(list);
        }

        return result;
    }

    public List<List<String>> GroupAnagramsBetter(String[] strs){
        if(strs == null || strs.length == 0) return new ArrayList<>();
        List<List<String>> result = new ArrayList<>();
        HashMap<String, List<String>> map = new HashMap<>();

        for(String s: strs){
            char[] chars = s.toCharArray();
            Arrays.sort(chars);

            String sortedString = new String(chars);

            if (!map.containsKey(sortedString)){
                map.put(sortedString, new ArrayList<>());
            }

            map.get(sortedString).add(s);
        }

        for(Map.Entry<String, List<String>> e: map.entrySet()){
            result.add(e.getValue());
        }

        return result;
    }

    public List<List<String>> GroupAnagrams(String[] strs){
        if(strs == null || strs.length == 0) return new ArrayList<>();
        HashMap<String, List<String>> map = new HashMap<>();

        for (String s: strs){
            int[] count = new int[26];
            for (char c: s.toCharArray()){
                count[c - 'a']++;
            }

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 26; i++){
                builder.append('#').append(count[i]);
            }

            map.computeIfAbsent(builder.toString(), k -> new ArrayList<>()).add(s);
        }

        return new ArrayList<>(map.values());
    }

    public int lengthOfLongestSubstringBruteForce(String s){
        int result = 0;

        for(int i = 0; i < s.length(); i++){
            Set<Character> set = new HashSet<>();

            for(int j = i; j < s.length(); j++){
                if(set.contains(s.charAt(j))){
                   break;
                }

                set.add(s.charAt(j));
                result = Math.max(result, j - i + 1);
            }
        }

        return result;
    }

    public int lengthOfLongestSubstringOptimal(String s){
        int left = 0;
        int maxLen = 0;
        HashMap<Character, Integer> map = new HashMap<>();

        for(int right = 0; right < s.length(); right++){
            if(map.containsKey(s.charAt(right))){
                if(map.get(s.charAt(right)) + 1 >= left){
                    left = map.get(s.charAt(right)) + 1;
                }
            }

            map.put(s.charAt(right), right);
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }

    public int subarraySumBruteForce(int[] nums, int k){
        int subArrayCount = 0;

        for(int i = 0; i < nums.length; i++){
            int sum = nums[i];
            if (sum == k){
                subArrayCount++;
            }
            for (int j = i + 1; j < nums.length; j++){
                sum += nums[j];
                if(sum == k){
                    subArrayCount++;
                }
            }
        }

        return subArrayCount;
    }

    public int subarraySumOptimal(int[] nums, int k){
        HashMap<Integer, Integer> map = new HashMap<>();
        int subArrayCount = 0;
        int sum = 0;

        map.put(0,1);

        for(int num: nums) {
            sum += num;
            subArrayCount +=  map.getOrDefault(sum - k, 0);
            map.put(sum,  map.getOrDefault(sum, 0) + 1);
        }

        return subArrayCount;
    }

    public String MaximumWindowSubstring(String s, String t){
        String result = "";

        for(int i = 0; i < s.length(); i++){
            for(int j = i; j < s.length(); j++){
                String sub =  s.substring(i, j+1);
                if(ContainsAll(sub, t)){
                    if(result.isEmpty() || sub.length() < result.length()){
                        result = sub;
                    }
                }
            }
        }
        return result;
    }

    public boolean ContainsAll(String sub, String t){
        int[] freq = new int[128];

        for (char c: t.toCharArray()){
            freq[c]++;
        }

        for(char c: sub.toCharArray()){
            freq[c]--;
        }

        for (int count : freq) {
            if (count > 0) {
                return false;
            }
        }
        return true;
    }

    public String MaximumWindowSubstringOptimal(String s, String t){
        if(t.isEmpty()) return "";
        if(s.length() < t.length()) return "";

        int left = 0;
        int start = -1;
        int minLen = Integer.MAX_VALUE;
        int[] hash =  new int[128];
        int valid = 0;

        for(char c: t.toCharArray()){
            hash[c]++;
        }

        for(int right = 0; right < s.length(); right++){
            if(hash[s.charAt(right)] > 0) valid++;
            hash[s.charAt(right)]--;

            while(valid == t.length()){
                if(right - left + 1 < minLen){
                    minLen = right - left + 1;
                    start = left;
                }

                hash[s.charAt(left)]++;
                if(hash[s.charAt(left)] > 0) valid--;
                left++;
            }
        }

        return start == -1 ? "" : s.substring(start, start + minLen);
    }

    public String MaximumWindowSubstringOptimal2(String s, String t){
        if (t.isEmpty() || s.length() < t.length()) return "";

        int[] need = new int[128];
        int required = 0;
        for(char c: t.toCharArray()){
            if(need[c] == 0) required++; // only count unique.
            need[c]++;
        }

        int[] have = new int[128];
        int valid = 0;
        int left = 0;
        int start = -1;
        int minLen = Integer.MAX_VALUE;

        for(int right = 0; right < s.length(); right++){
            char ch = s.charAt(right);
            have[ch]++;

            if(have[ch] == need[ch]) valid++;

            while(valid == required && left <= right){
                int currentLen = right - left + 1;
                if(currentLen < minLen){
                    minLen = currentLen;
                    start = left;
                }

                char d =  s.charAt(left);
                have[d]--;
                if(have[d] == need[d] - 1) valid--; // dropped below required
                left++;
            }
        }
        return start == -1 ? "" : s.substring(start, start + minLen);
    }
}
