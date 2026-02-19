package SlindingWindowPointers;

import java.util.Arrays;
import java.util.HashMap;

public class LongestSmallestWindow {
    public int longestNonRepeatingSubstringBrute(String s) {
        int n = s.length();
        int maxLen = 0;

        for(int i = 0; i <n ; i++){
            int[] hash = new int[128];
            for(int j = i; j < n; j++){
                if(hash[s.charAt(j)] == 1) break;

                hash[s.charAt(j)] = 1;

                int len = j - i + 1;

                maxLen = Math.max(maxLen, len);
            }
        }
        return maxLen;
    }

    public int longestRepeatingSubstring(String s) {
        int n = s.length();
        int maxLen = 0;

        int[] lastSeen =  new int[256];
        Arrays.fill(lastSeen, -1);
        int left = 0;

        for(int right = 0; right < n; right++){
            char c = s.charAt(right);

            if(lastSeen[c] >= left){
                left = lastSeen[c] + 1;
            }

            lastSeen[c] = right;
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }

    public int longestOnesBrute(int[] nums, int k) {
        int n = nums.length;

        int maxLen = 0;
        int zeros;

        for(int i = 0; i < n; i++){
            zeros = 0;
            for(int j = i; j < n; j++){
                if(nums[j] == 0) zeros += 1;
                if(zeros <= k){
                    int len = j - i + 1;
                    maxLen = Math.max(maxLen, len);
                }else{
                    break;
                }
            }
        }

        return maxLen;
    }

    public int longestOnes(int[] nums, int k) {
        int n = nums.length;

        int maxLen = 0;
        int zeroes = 0;
        int left = 0;

        for(int right = 0; right < n; right++){
            if(nums[right] == 0) zeroes++;

            while(zeroes > k && left <= right){
                if(nums[left] == 0) zeroes--;
                left++;
            }

            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }

    public int totalFruits(int[] fruits) {
        int n = fruits.length;
        HashMap<Integer, Integer> map = new HashMap<>();

        int maxLen = 0;
        int left = 0;

        for(int right = 0; right < n; right++){
            map.put(fruits[right], map.getOrDefault(fruits[right], 0) + 1);

            if(map.size() > 2){
                while(map.size() > 2){
                    map.put(fruits[left], map.get(fruits[left]) - 1);
                    if(map.get(fruits[left]) == 0){
                        map.remove(fruits[left]);
                    }
                    left++;
                }
            }
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }

    public int kDistinctCharBrute(String s, int k) {
        int maxLen = 0;

        HashMap<Character, Integer> map = new HashMap<>();

        for(int i = 0; i < s.length(); i++){
            map.clear();
            for(int j = i; j < s.length(); j++){
                char c  = s.charAt(j);
                map.put(c, map.getOrDefault(c, 0) + 1);

                if(map.size() <= k){
                    maxLen = Math.max(maxLen, j - i + 1);
                }else break;
            }
        }

        return maxLen;
    }

    public int kDistinctChar(String s, int k) {
        int n = s.length();
        int maxLen = 0;

        HashMap<Character, Integer> map = new HashMap<>();
        int left = 0;

        for(int right = 0; right < n; right++){
            map.put(s.charAt(right), map.getOrDefault(s.charAt(right), 0) + 1);

            if(map.size() > k){
                while(map.size() > k && left <= right){
                    map.put(s.charAt(left), map.get(s.charAt(left)) - 1);
                    if(map.get(s.charAt(left)) == 0){
                        map.remove(s.charAt(left));
                    }
                    left++;
                }
            }

            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }

    public int characterReplacementBrute(String s, int k) {
        int maxLen = 0;
        int maxFreq = 0;

        for (int i = 0; i < s.length(); ++i) {
            int[] hash  = new int[26];
            for (int j = i; j < s.length(); ++j) {
                hash[s.charAt(j) - 'A']++;

                maxFreq =  Math.max(maxFreq, hash[s.charAt(j) - 'A']);

                int changes = (j - i + 1) - maxFreq;

                if(changes <= k) maxLen = Math.max(maxLen, j - i + 1);
                else break;
            }
        }

        return maxLen;
    }

    public int characterReplacement(String s, int k) {
        int maxLen = 0;
        int maxFreq = 0;
        int left = 0;
        int[] hash = new int[26];

        for(int right = 0; right < s.length(); right++){
            hash[s.charAt(right) - 'A']++;
            maxFreq = Math.max(maxFreq, hash[s.charAt(right) - 'A']);

            while((right - left + 1) - maxFreq > k){
                hash[s.charAt(left) - 'A']--;
                left++;
            }

            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }

    private boolean isValidFreq(int[] count, int windowSize, int k){
        int maxFreq = 0;
        for (int c: count) maxFreq = Math.max(maxFreq, c);
        return windowSize - maxFreq <= k;
    }

    public String characterReplacementSubstring(String s, int k) {
        int[] count = new int[26];
        int left = 0;
        int bestLen = 0;
        int bestL = 0;

        for(int right = 0; right < s.length(); right++){
            count[s.charAt(right) - 'A']++;
            while(!isValidFreq(count, right - left + 1, k)){
                count[s.charAt(left) - 'A']--;
                left++;
            }

            int windowlen = right - left + 1;
            if(windowlen > bestLen){
                bestLen = windowlen;
                bestL = left;
            }
        }

        return s.substring(bestL, bestL + bestLen);
    }

    public String minWindow(String s, String t) {
        if(s.length() == 0 || t.length() == 0) return "";
        if(s.length() < t.length()) return "";

        int[] need = new int[128];
        int required = 0; // Number of characters in t whose freq must be satisfied
        for(char c: t.toCharArray()){
            if(need[c] == 0) required++;
            need[c]++;
        }

        int[] have = new int[128];
        int formed = 0;
        int left = 0;
        int minLen = Integer.MAX_VALUE;
        int minStart = 0;

        for(int right = 0; right < s.length(); right++){
            int idx =  s.charAt(right);
            have[idx]++;

            if(need[idx] > 0 && have[idx] == need[idx]) formed++;

            while(formed == required && left <= right){
                if(minLen > right - left + 1){
                    minLen = right - left + 1;
                    minStart = left;
                }

                char leftChar = s.charAt(left);
                have[leftChar]--;
                if(need[leftChar] > 0 && have[leftChar] < need[leftChar]) formed--;
                left++;
            }
        }

        return minLen == Integer.MAX_VALUE ? "" :  s.substring(minStart,  minStart + minLen);
    }
}
