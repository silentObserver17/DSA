package Basics;

import java.util.*;

public class BasicStrings {
    public void reverseString(List<Character> s) {
        int start = 0;
        int end = s.size() - 1;

        while(start < end){
            char temp = s.get(start);
            s.set(start,s.get(end));
            s.set(end,temp);

            start++;
            end--;
        }
    }

    public boolean palindromeCheck(String s) {
        int start = 0;
        int end = s.length() - 1;

        while(start < end){
            if(s.charAt(start) != s.charAt(end)){
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    public String largeOddNum(String s) {
        int ind = -1;

        for(int i = s.length() - 1; i  >= 0; i--){
            if((s.charAt(i) - '0') % 2 == 1){
                ind = i;
                break;
            }
        }

        if(ind == -1) return "";

        int i = 0;
        while(i <= ind && s.charAt(i) == '0') i++;

        return s.substring(i, ind + 1);
    }

    public String longestCommonPrefix(String[] str) {
        Arrays.sort(str);
        String firstString = str[0];
        String lastString = str[str.length - 1];

        int i;
        for(i = 0; i < Math.min(firstString.length(), lastString.length()); i++){
            if(firstString.charAt(i) != lastString.charAt(i)){
                break;
            }
        }

        return firstString.substring(0, i);
    }

    public boolean isomorphicString(String s, String t) {
        if(s.length() != t.length()) return false;

        HashMap<Character, Character> smap = new HashMap<>();
        HashMap<Character, Character> tmap = new HashMap<>();

        for(int i = 0; i < s.length(); i++){
            char c1 =  s.charAt(i);
            char c2 = t.charAt(i);

            if(smap.containsKey(c1)){
                if(smap.get(c1) != c2) return false;
            }else{
                smap.put(c1, c2);
            }

            if(tmap.containsKey(c2)){
                if(tmap.get(c2) != c1) return false;
            }else{
                tmap.put(c2, c1);
            }
        }

        return true;
    }

    public boolean isomorphicStringArray(String s, String t) {
        if(s.length() != t.length()) return false;

        int[] m1 = new int[256], m2 = new int[256];

        for(int i = 0; i < s.length(); i++){
            if(m1[s.charAt(i)] != m2[t.charAt(i)]) return false;

            m1[s.charAt(i)] = i + 1;
            m2[t.charAt(i)] = i + 1;
        }

        return true;
    }

    // s = abcde" , goal = "cdeab"
    public boolean rotateString(String s, String goal) {
        if (s.length() != goal.length()) return false;
        StringBuffer sb = new StringBuffer(s);

        if(s.equals(goal)) return true;

        for(int i = 0; i < s.length(); i++){
            sb.append(sb.charAt(0));
            sb.deleteCharAt(0);

            if(sb.toString().equals(goal)) return true;
        }

        return false;
    }

    public boolean rotateStringOptimal(String s, String goal){
        if(s.length() != goal.length()) return false;
        return (s + s).contains(goal);
    }

    public boolean anagramStringsBrute(String s, String t) {
        if(s.length() != t.length()) return false;

        char[] sArray = s.toCharArray();
        char[] tArray = t.toCharArray();
        Arrays.sort(sArray);
        Arrays.sort(tArray);

        return Arrays.equals(sArray, tArray);
    }

    public boolean anagramStrings(String s, String t) {
        if(s.length() != t.length()) return false;

        int[] count = new int[26];

        for(char c: s.toCharArray()) count[c - 'a']++;
        for(char c: t.toCharArray()) count[c - 'a']--;

        for(int i = 0; i < count.length; i++){
            if(count[i] != 0) return false;
        }

        return true;
    }

    public List<Character> frequencySort(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for(char ch: s.toCharArray()){
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        PriorityQueue<Character> pq = new PriorityQueue<>(
                (a,b)->{
                    int freqCompare = map.get(b) - map.get(a);
                    if(freqCompare != 0) return freqCompare;

                    return a - b;
                }
        );

        pq.addAll(map.keySet());

        List<Character> list = new ArrayList<>();
        while(!pq.isEmpty()){
            char c = pq.poll();
            list.add(c);
        }

        return list;
    }

}
