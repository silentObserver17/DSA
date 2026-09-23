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

    public int searchInRotatedSortedArray(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;

        while(lo <= hi){
            int mid = lo + (hi - lo)/2;

            if(nums[mid] == target) return mid;

            if(nums[lo] <= nums[mid]) {
                if(nums[lo] <= target && target < nums[mid]){
                    hi = mid - 1;
                }else{
                    lo = mid + 1;
                }
            }else{
                if(nums[mid] < target && target <= nums[hi]){
                    lo = mid + 1;
                }else{
                    hi = mid - 1;
                }
            }
        }

        return -1;
    }

    public int searchInRotatedSortedArrayWithDuplicates(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;

        while(lo <= hi){
            int mid = lo + (hi - lo)/2;

            if(nums[mid] == target) return mid;

            if(nums[lo] == nums[mid] && nums[mid] == nums[hi]) {
                lo++;
                hi--;
                continue;
            }

            if(nums[lo] <= nums[mid]) {
                if(nums[lo] <= target && target < nums[mid]){
                    hi = mid - 1;
                }else{
                    lo = mid + 1;
                }
            }else{
                if(nums[mid] < target && target <= nums[hi]){
                    lo = mid + 1;
                }else{
                    hi = mid - 1;
                }
            }
        }

        return -1;
    }

    public int findMin(int[] nums) {
        int low = 0;
        int high = nums.length - 1;

        while(low < high) {
            int mid = low + (high - low)/2;

            if(nums[mid] > nums[high]) {
                low = mid + 1;
            }else{
                high = mid;
            }
        }

        return nums[high];
    }

    public int findMinWithDuplicates(int[] nums) {
        int low = 0;
        int high = nums.length - 1;

        while(low < high) {
            int mid = low + (high - low)/2;

            if(nums[mid] > nums[high]) {
                low = mid + 1;
            }else if(nums[mid] < nums[high]){
                high = mid;
            }else{
                high--;
            }
        }

        return nums[high];
    }

    public double MedianOfTwoSortedArray(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n =  nums2.length;
        if(m > n) return MedianOfTwoSortedArray(nums2, nums1);
        int low = 0;
        int high = m;
        int half = (m + n + 1)/2;

        while(low <= high) {
            int x = low +  (high - low)/2;
            int y = half - x;

            int l1 = Integer.MIN_VALUE;
            int l2 = Integer.MIN_VALUE;
            int r1 = Integer.MAX_VALUE;
            int r2 = Integer.MAX_VALUE;

            if(x < m) r1 = nums1[x];
            if(y < n) r2 = nums2[y];
            if(x - 1 >= 0) l1 = nums1[x - 1];
            if(y - 1 >= 0) l2 = nums2[y - 1];

            if(l1 <= r2 && l2 <= r1) {
                if((m+n)%2 == 1) return Math.max(l1, l2);
                else return ((double) (Math.max(l1, l2) + Math.min(r1, r2))) / 2.0;
            } else if(l1 > r2) {
                high = x - 1;
            }else{
                low = x + 1;
            }
        }
        return 0;
    }
}

class MonotonicStack {
    public int[] DailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        Stack<Integer> stack = new Stack<>();
        int[] result = new int[n];

        for(int i = 0; i < n; i++){
            while(!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]){
                int top = stack.pop();
                result[top] = i - top;
            }
            stack.push(i);
        }

        return result;
    }

    public int BasicCalculator2(String s) {
        int n = s.length();
        int num = 0;
        char pendingOp = '+';
        Stack<Integer> stack = new Stack<>();

        for(int i = 0; i < n; i++){
            char currentChar = s.charAt(i);

            if(Character.isDigit(currentChar)){
                num = num * 10 + currentChar - '0';
            }

            if((!Character.isDigit(currentChar) && !Character.isWhitespace(currentChar)) || i == n-1){
                if(pendingOp == '+'){
                    stack.push(num);
                }else if(pendingOp == '-'){
                    stack.push(-num);
                }else if(pendingOp == '*'){
                    stack.push(stack.pop() * num);
                }else if(pendingOp == '/'){
                    stack.push(stack.pop() / num);
                }

                pendingOp = currentChar;
                num = 0;
            }
        }

        int result = 0;
        while(!stack.isEmpty()){
            result += stack.pop();
        }

        return result;
    }

    // [2,1,5,6,2,3]
    private int[] findPreviousSmallerElement(int[] nums, int n) {
        int[] pse = new int[n];
        Arrays.fill(pse, -1);

        Stack<Integer> stack = new Stack<>();

        for(int i = 0; i < n; i++){
            while(!stack.isEmpty() && nums[stack.peek()] >= nums[i]){
                stack.pop();
            }
            if(!stack.isEmpty()) pse[i] = stack.peek();
            stack.push(i);
        }

        return pse;
    }

    private int[] findNextSmallerElement(int[] nums, int n) {
        int[] nse =  new int[n];
        Arrays.fill(nse, n);

        Stack<Integer> stack = new Stack<>();
        for(int i = n - 1; i >= 0; i--){
            while(!stack.isEmpty() && nums[stack.peek()] >= nums[i]){
                stack.pop();
            }

            if(!stack.isEmpty()) nse[i] = stack.peek();
            stack.push(i);
        }

        return nse;
    }

    public int largestRectangleHistogram2Pass(int[] heights) {
        int n = heights.length;
        int area = 0;
        int currentArea = 0;

        int[] nse = findNextSmallerElement(heights, n);
        int[] pse = findPreviousSmallerElement(heights, n);

        for(int i = 0; i < n; i++){
            currentArea = heights[i] * (nse[i] - pse[i] - 1);
            area = Math.max(area, currentArea);
        }

        return area;
    }

    public int largestRectangleHistogram(int[] heights) {
        int n = heights.length;
        int maxArea = 0;

        Stack<Integer> stack = new Stack<>();

        for(int i = 0; i < n; i++){
            while(!stack.isEmpty() && heights[stack.peek()] >= heights[i]){
                int topIdx = stack.pop();

                int left = stack.isEmpty() ? -1 : stack.peek();
                int right = i;

                int currentArea = heights[topIdx] * (right - left - 1);
                maxArea = Math.max(maxArea, currentArea);
            }

            stack.push(i);
        }

        while(!stack.isEmpty()){
            int topIdx = stack.pop();

            int left = stack.isEmpty() ? -1 : stack.peek();
            int right = n;

            int currentArea =  heights[topIdx] * (right - left - 1);
            maxArea = Math.max(maxArea, currentArea);
        }

        return maxArea;
    }
}

class LinkedList {
    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) { val = x; }
    }

    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        ListNode next = curr.next;

        while (curr != null) {
            curr.next = prev;
            prev = curr;
            curr = next;
            if(next != null) next = next.next;
        }

        head = prev;
        return head;
    }

    public ListNode ReverseLinkedListRecursion(ListNode head) {
        if(head == null || head.next == null) return head;

        ListNode reversedHead = ReverseLinkedListRecursion(head.next);

        head.next.next = head;
        head.next = null;

        return reversedHead;
    }

    public ListNode DetectCycle2(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;

        while(fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow) {
                ListNode x = head;
                ListNode y = slow;

                while(x != y) {
                    x = x.next;
                    y = y.next;
                }

                return x;
            }
        }

        return null;
    }

    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a,b) -> a.val - b.val);

        for(ListNode node : lists) {
            if(node != null) pq.offer(node);
        }

        ListNode dummy = new ListNode(-1);
        ListNode curr = dummy;

        while(!pq.isEmpty()){
            ListNode smallest = pq.poll();

            curr.next = smallest;
            curr = curr.next;

            if(smallest.next != null) {
                pq.offer(smallest.next);
            }
        }

        return dummy.next;
    }

    public ListNode MergeSortMergeKList(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;

        return mergeKListsRecursion(lists, 0, lists.length - 1);
    }

    private ListNode mergeKListsRecursion(ListNode[] lists, int left, int right) {
        if(left > right) return null;
        if(left == right) return  lists[left];

        int mid = left + (right - left) / 2;

        ListNode l1 = mergeKListsRecursion(lists, left, mid);
        ListNode l2 = mergeKListsRecursion(lists, mid + 1, right);

        return MergeTwoLists(l1, l2);
    }

    private ListNode MergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1);
        ListNode curr = dummy;

        while(l1 != null && l2 != null) {
            if(l1.val <= l2.val) {
                curr.next = l1;
                l1 = l1.next;
            }else{
                curr.next = l2;
                l2 = l2.next;
            }

            curr = curr.next;
        }

        if(l1 != null) curr.next = l1;
        else curr.next = l2;

        return dummy.next;
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) return head;

        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode prevTail = dummy;

        while(true) {
            ListNode groupStart = prevTail.next;
            ListNode nextGroupStart = groupStart;
            for(int i = 0; i < k ; i++) {
                if(nextGroupStart == null) return dummy.next;
                nextGroupStart = nextGroupStart.next;
            }

            ListNode prev = null;
            ListNode curr = groupStart;
            ListNode next = null;

            for(int i = 0; i < k; i++) {
                next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }

            prevTail.next = prev;
            groupStart.next = nextGroupStart;
            prevTail = groupStart;
        }

    }

    class LRUCache {
        class Node {
            int key;
            int val;
            Node next;
            Node prev;
            public Node(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }

        private HashMap<Integer, Node> map;
        Node head;
        Node tail;
        private int capacity;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            this.map = new HashMap<>();
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            if (map.containsKey(key)) {
                Node node = map.get(key);
                removeNode(node);
                addToFront(node);
                return node.val;
            }

            return -1;
        }

        public void put(int key, int value) {
            if(map.containsKey(key)) {
                Node node = map.get(key);
                node.val = value;
                removeNode(node);
                addToFront(node);
                map.put(key, node);
                return;
            }

            if(capacity == map.size()) {
                map.remove(tail.prev.key);
                removeNode(tail.prev);
            }

            Node newNode = new Node(key, value);
            map.put(key, newNode);
            addToFront(newNode);
        }

        private void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void addToFront(Node node) {
            node.next = head.next;
            node.prev = head;
            node.next.prev = node;
            head.next = node;
        }
    }

    class Trees {
        public class TreeNode {
            int val;
            TreeNode left;
            TreeNode right;
            TreeNode() {}
            TreeNode(int val) { this.val = val; }
            TreeNode(int val, TreeNode left, TreeNode right) {
                this.val = val;
                this.left = left;
                this.right = right;
            }
        }

        public List<List<Integer>> levelOrder(TreeNode root) {
            List<List<Integer>> result = new ArrayList<>();
            if(root == null) return result;

            Queue<TreeNode> que = new java.util.LinkedList<>();
            que.offer(root);

            while(!que.isEmpty()) {
                int size = que.size();
                List<Integer> level = new  ArrayList<>();

                for(int i = 0; i < size; i++) {
                    TreeNode node = que.poll();
                    level.add(node.val);

                    if(node.left != null) que.offer(node.left);
                    if(node.right != null) que.offer(node.right);
                }

                result.add(level);
            }

            return result;
        }

        public int kthSmallest(TreeNode root, int k) {
            Stack<TreeNode> stack = new Stack<>();
            TreeNode curr = root;
            int count = 0;

            while(true) {
                if(curr != null) {
                    stack.push(curr);
                    curr = curr.left;
                }else{
                    if(stack.isEmpty()) return -1;
                    TreeNode node = stack.pop();
                    count++;
                    if(count == k) return node.val;
                    curr = node.right;
                }
            }
        }

        public TreeNode LowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            if(root == p || root == q || root == null) {
                return root;
            }

            TreeNode left = LowestCommonAncestor(root.left, p, q);
            TreeNode right = LowestCommonAncestor(root.right, p, q);

            if(left != null && right != null) {
                return root;
            }

            if(left != null) {
                return left;
            }

            return right;
        }

        public TreeNode buildTree(int[] preorder, int[] inorder) {
            Map<Integer, Integer> map = new HashMap<>();

            for(int i = 0; i < inorder.length; i++) {
                map.put(inorder[i], i);
            }

            TreeNode root = buildTreeHelper(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, map);

            return root;
        }

        private TreeNode buildTreeHelper(int[] preorder, int preStart, int preEnd, int[]inorder, int inStart, int inEnd, Map<Integer, Integer> map) {
            if(preStart > preEnd || inStart > inEnd) return null;

            TreeNode root = new  TreeNode(preorder[preStart]);
            int inRoot = map.get(root.val);
            int numsLeft = inRoot - inStart;

            root.left = buildTreeHelper(preorder, preStart + 1, preStart + numsLeft, inorder, inStart, inRoot - 1, map);
            root.right = buildTreeHelper(preorder, preStart + numsLeft + 1, preEnd, inorder, inRoot + 1, inEnd, map);

            return root;
        }

        public int maxPathSum(TreeNode root) {
            int[] maxSum = new int[1];
            maxSum[0] = Integer.MIN_VALUE;

            maxPathHelper(root, maxSum);
            return maxSum[0];
        }

        private int maxPathHelper(TreeNode root, int[] maxSum) {
            if(root == null) return 0;

            int leftGain = Math.max(maxPathHelper(root.left, maxSum), 0);
            int rightGain = Math.max(maxPathHelper(root.right, maxSum), 0);

            maxSum[0] = Math.max(maxSum[0], leftGain + rightGain +  root.val);

            return root.val + Math.max(leftGain, rightGain);
        }

        public String serialize(TreeNode root) {
            if(root == null) return "";

            StringBuilder sb = new StringBuilder();
            Queue<TreeNode> que = new java.util.LinkedList<>();
            que.offer(root);

            while(!que.isEmpty()) {
                TreeNode node = que.poll();

                if(node == null) {
                    sb.append("null,");
                }else{
                    sb.append(node.val).append(",");
                    que.offer(node.left);
                    que.offer(node.right);
                }
            }

            return sb.toString();
        }

        public TreeNode deserialize(String data) {
            if(data.equals("")) return null;

            String[] values = data.split(",");
            TreeNode root = new TreeNode(Integer.parseInt(values[0]));
            Queue<TreeNode> que = new java.util.LinkedList<>();
            que.offer(root);

            int i = 1;
            while(!que.isEmpty() && i < values.length) {
                TreeNode node = que.poll();

                if(!values[i].equals("null")) {
                    node.left = new TreeNode(Integer.parseInt(values[i]));
                    que.offer(node.left);
                }
                i++;

                if(!values[i].equals("null")) {
                    node.right = new TreeNode(Integer.parseInt(values[i]));
                    que.offer(node.right);
                }
                i++;
            }

            return  root;
        }
    }
}

class HeapPriorityQueue {
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> Integer.compare(map.get(a), map.get(b)));

        for(int num : map.keySet()) {
            pq.offer(num);

            if(pq.size() > k) {
                pq.poll();
            }
        }

        int[] res = new int[k];
        for(int i = 0; i < k; i++) {
            res[i] = pq.poll();
        }

        return res;
    }

    public int[] topKFrequentBucketSort(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }

        List<Integer>[] buckets = new  List[nums.length + 1];

        for(int num: map.keySet()) {
            int freq = map.get(num);

            if(buckets[freq] == null) {
                buckets[freq] = new ArrayList<>();
            }

            buckets[freq].add(num);
        }

        int count = 0;
        int[] res = new int[k];
        for(int i = nums.length; i >= 1; i-- ) {
            if(buckets[i] == null) continue;

            for(int num : buckets[i]) {
                res[count++] = num;
                if(count == k) return res;
            }
        }

        return res;
    }

    public int findKthLargest(int[] nums, int k) {
        int target = nums.length - k;

        int left = 0;
        int right = nums.length - 1;

        while(left <= right) {
            int pivotIndex = parition(nums, left, right);

            if(pivotIndex == target) {
                return nums[pivotIndex];
            }else if(pivotIndex < target){
                left = pivotIndex + 1;
            }else{
                right = pivotIndex - 1;
            }
        }

        return -1;
    }

    private int parition(int[] nums, int left, int right) {
        int pivot = nums[right];
        int p = left;

        for(int i = left; i <= right; i++) {
            if(nums[i] <= pivot) {
                swap(nums, i, p);
                p++;
            }
        }

        swap(nums, p, right);
        return p;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}

class GraphNormal {
    public int numIslandsDfs(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        boolean[][] vis = new boolean[n][m];

        int[] rowIndex = {-1, 0, 1, 0};
        int[] colIndex = {0, -1, 0, 1};
        int count = 0;

        for(int row = 0; row < n; row++) {
            for(int col = 0; col < m; col++) {
                if(grid[row][col] == '1' && !vis[row][col]) {
                    count++;
                    dfsIslands(row, col, grid, vis, rowIndex, colIndex);
                }
            }
        }

        return count;
    }

    private void dfsIslands(int row, int col, char[][]grid, boolean[][]vis, int[]rowIndex, int[]colIndex) {
        vis[row][col] = true;

        for(int i = 0; i < 4; i++) {
            int nrow = row + rowIndex[i];
            int ncol = col + colIndex[i];

            if(nrow >= 0 && nrow < grid.length && ncol >= 0 && ncol < grid[0].length && grid[nrow][ncol] == '1' && !vis[nrow][ncol]) {
                dfsIslands(nrow, ncol,  grid, vis, rowIndex, colIndex);
            }
        }
    }

    private int numIslandsBFS(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        boolean[][] vis = new boolean[n][m];

        int count = 0;

        for(int row = 0; row < n; row++) {
            for(int col = 0; col < m; col++) {
                if(grid[row][col] == '1' && !vis[row][col]) {
                    count++;
                    bfsIsland(row, col, grid, vis);
                }
            }
        }

        return count;
    }

    private void bfsIsland(int row, int col, char[][]grid, boolean[][]vis) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{row, col});
        vis[row][col] = true;

        int[] rowIndex = {-1, 0, 1, 0};
        int[] colIndex = {0, -1, 0 , 1};

        while(!queue.isEmpty()) {
            int[] current = queue.poll();

            for(int i = 0; i < 4; i++) {
                int nrow = current[0] + rowIndex[i];
                int ncol = current[1] + colIndex[i];

                if(nrow >= 0 && nrow < grid.length && ncol >= 0 && ncol < grid[0].length && !vis[nrow][ncol] && grid[nrow][ncol] == '1') {
                    queue.offer(new int[]{nrow, ncol});
                    vis[nrow][ncol] = true;
                }
            }
        }
    }

    class Node {
        public int val;
        public List<Node> neighbors;
        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    public Node cloneGraph(Node node) {
        if(node == null) return node;

        Map<Node, Node> map = new  HashMap<>();

        return cloneGraphHelper(node, map);
    }

    private Node cloneGraphHelper(Node node, Map<Node, Node> map) {
        if(map.containsKey(node)) {
            return map.get(node);
        }

        Node clone = new Node(node.val);
        map.put(node, clone);

        for(Node neighbor : node.neighbors) {
            Node clonedNeighbours = cloneGraphHelper(neighbor, map);
            clone.neighbors.add(clonedNeighbours);
        }

        return clone;
    }

    public int MultiSourceBFSOranges(int[][] grid) {
        int rows =  grid.length;
        int cols =  grid[0].length;

        Queue<int[]> queue = new ArrayDeque<>();
        int freshCount = 0;
        int minutes = 0;

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(grid[i][j] == 2) {
                    queue.offer(new int[]{i, j});
                }

                if(grid[i][j] == 1) {
                    freshCount++;
                }
            }
        }

        int[] rowIndex = {-1, 0, 1, 0};
        int[] colIndex = {0, -1, 0, 1};

        while(!queue.isEmpty() && freshCount > 0) {
            int size = queue.size();

            for(int i = 0; i < size; i++) {
                int[] cell = queue.poll();
                int row = cell[0];
                int col = cell[1];

                for(int j = 0; j < 4; j++) {
                    int nrow = row + rowIndex[j];
                    int ncol = col + colIndex[j];

                    if(nrow >= 0 && nrow < rows && ncol >= 0 && ncol < cols && grid[nrow][ncol] == 1) {
                        grid[nrow][ncol] = 2;
                        queue.offer(new int[]{nrow, ncol});
                        freshCount--;
                    }
                }
            }

            minutes++;
        }



        if(freshCount != 0) {
            return -1;
        }

        return minutes;
    }

    record Word(String word, int level){}

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Queue<Word> que = new ArrayDeque<>();
        que.offer(new Word(beginWord, 1));

        Set<String> visited = new HashSet<>(wordList);

        while(!que.isEmpty()) {
            Word current = que.poll();
            String word = current.word;
            int level = current.level;

            if(word.equals(endWord)) {
                return level;
            }

            char[] charArr =  word.toCharArray();
            for(int i = 0; i < charArr.length; i++) {
                char originalChar = charArr[i];

                for(char c = 'a'; c <= 'z'; c++) {
                    if(c == originalChar) continue;

                    charArr[i] = c;
                    String newWord = new String(charArr);

                    if(visited.contains(newWord)) {
                        que.offer(new  Word(newWord, level+1));
                        visited.remove(newWord);
                    }
                }
                charArr[i] = originalChar;
            }
        }

        return 0;
    }

    public int NetworkDelay(int[][] times, int n, int k) {
        List<List<int[]>> adj = new ArrayList<>();

        for(int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        for(int[] time: times) {
            int u = time[0];
            int v = time[1];
            int w = time[2];

             adj.get(u).add(new int[]{v, w});
        }

        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[1] - b[1]);
        pq.offer(new  int[]{k, 0});
        dist[k] = 0;

        while(!pq.isEmpty()) {
            int[] curr = pq.poll();
            int node = curr[0];
            int currDist = curr[1];

            if(currDist > dist[node]) {
                continue;
            }

            for(int[] edge: adj.get(node)) {
                int neighbour = edge[0];
                int weight = edge[1];

                int newDist = currDist + weight;

                if(newDist < dist[neighbour]) {
                    dist[neighbour] = newDist;
                    pq.offer(new int[]{neighbour, newDist});
                }
            }
        }

        int maxTime = 0;
        for(int i = 1; i <= n; i++) {
            if(dist[i] == Integer.MAX_VALUE) {
                return -1;
            }
            maxTime = Math.max(maxTime, dist[i]);
        }

        return maxTime;
    }

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();

        for(int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }

        int[] indegree = new int[numCourses];
        for(int[] pair : prerequisites) {
            int u = pair[0];
            int v = pair[1];

            adj.get(v).add(u);
            indegree[u]++;
        }

        Queue<Integer> queue = new ArrayDeque<>();
        for(int i = 0; i < numCourses; i++ ) {
            if(indegree[i] == 0) {
                queue.offer(i);
            }
        }

        int[] order = new int[numCourses];
        int idx = 0;

        while(!queue.isEmpty()) {
            int node = queue.poll();

            order[idx++] = node;

            for(int next : adj.get(node)) {
                indegree[next]--;
                if(indegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }

        if(idx != numCourses) {
            return new int[]{};
        }

        return order;
    }

    public int[] findOrderDFS(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();

        int[] vis = new int[numCourses];

        for(int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }

        for(int[] pair : prerequisites) {
            int u = pair[0];
            int v = pair[1];

            adj.get(v).add(u);
        }

        Stack<Integer> stack = new Stack<>();

        for(int i = 0; i < numCourses; i++) {
            if(vis[i] == 0) {
                if(!dfsTopo(i,stack, adj, vis)) {
                    return new int[0];
                }
            }
        }

        int[] order =  new int[numCourses];
        int idx = 0;
        while(!stack.isEmpty()) {
            order[idx++] = stack.pop();
        }

        return order;
    }

    private boolean dfsTopo(int node, Stack<Integer> stack, List<List<Integer>> adj, int[] vis) {
        vis[node] = 1;

        for(int next : adj.get(node)) {
            if(vis[next] == 1) {
                return false;
            }

            if(vis[next] == 0) {
                if(!dfsTopo(next, stack, adj, vis)){
                    return false;
                }
            }
        }

        vis[node] = 2;
        stack.push(node);
        return true;
    }

    public String AlienDictionary(String[] words) {
        Map<Character, Set<Character>> adj = new HashMap<>();
        Map<Character, Integer> indegree = new HashMap<>();

        for(String  word : words) {
            for(char c : word.toCharArray()) {
                adj.putIfAbsent(c, new HashSet<>());
                indegree.putIfAbsent(c, 0);
            }
        }

        for(int i = 0; i < words.length - 1; i++) {
            String str1 = words[i];
            String str2 = words[i+1];

            if(str1.length() > str2.length() && str1.startsWith(str2)) {
                return "";
            }

            int len = Math.min(str1.length(), str2.length());
            for(int j = 0; j < len; j++) {
                char c1 = str1.charAt(j);
                char c2 =  str2.charAt(j);
                if(c1 != c2) {
                    if(adj.get(c1).add(c2)) {
                        indegree.put(c2, indegree.get(c2) + 1);
                    }
                    break;
                }
            }
        }

        Queue<Character> queue = new ArrayDeque<>();

        for(char c : indegree.keySet()) {
            if(indegree.get(c) == 0) {
                queue.offer(c);
            }
        }

        StringBuilder order = new StringBuilder();

        while(!queue.isEmpty()) {
            char curr = queue.poll();
            order.append(curr);

            for(char next : adj.get(curr)) {
                indegree.put(next, indegree.get(next) - 1);

                if(indegree.get(next) == 0) {
                    queue.offer(next);
                }
            }
        }

        if(order.length() != indegree.size()) {
            return "";
        }

        return order.toString();
    }
}


class DisjointSets {
    class DSURank {
        int[] parent;
        int[] rank;

        DSURank(int n) {
            parent = new int[n];
            rank = new int[n];

            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }

            return parent[x];
        }

        public boolean union(int a, int b) {
            int rootA = find(a);
            int rootB = find(b);

            if (rootA == rootB) {
                return false;
            }

            if (rank[rootA] > rank[rootB]) {
                parent[rootB] = rootA;
            } else if (rank[rootA] < rank[rootB]) {
                parent[rootA] = rootB;
            } else {
                parent[rootB] = rootA;
                rank[rootA]++;
            }

            return true;
        }

        public boolean connected(int a, int b) {
            return find(a) == find(b);
        }
    }

    class DSUSize {
        int[] parent;
        int[] size;

        DSUSize(int n) {
            parent = new int[n];
            size = new int[n];

            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }

            return parent[x];
        }

        public boolean union(int a, int b) {
            int rootA = find(a);
            int rootB = find(b);

            if (rootA == rootB) {
                return false;
            }

            if (size[rootA] < size[rootB]) {
                int temp = rootA;
                rootA = rootB;
                rootB = temp;
            }

            parent[rootB] = rootA;
            size[rootA] += size[rootB];

            return true;
        }
    }

    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        DSURank dsurank = new DSURank(n);

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            if (!dsurank.union(u, v)) {
                return edge;
            }
        }

        return new int[0];
    }

    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        List<int[]> edges = new  ArrayList<>();

        for(int i = 0; i < n; i++) {
            for(int j = i + 1; j < n; j++) {
                int dist = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                edges.add(new int[]{dist, i, j});
            }
        }

        edges.sort((a, b) -> Integer.compare(a[0], b[0]));

        DSUSize dsu = new DSUSize(n);
        int totalCost = 0;
        int edgeUsed = 0;

        for(int[] edge : edges) {
            int wt = edge[0];
            int u = edge[1];
            int v = edge[2];

            if(dsu.union(u, v)) {
                totalCost += wt;
                edgeUsed++;

                if(edgeUsed == n - 1) break;
            }
        }

        return totalCost;
    }

    public int minCostConnectPointsPrim(int[][] points) {
        int n = points.length;
        int minCost = 0;

        boolean[] vis = new boolean[n];
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> Integer.compare(a[0], b[0]));

        pq.offer(new int[]{0, 0});

        while(!pq.isEmpty()) {
            int[] edge = pq.poll();
            int cost = edge[0];
            int u = edge[1];

            if(vis[u]) continue;

            vis[u] = true;
            minCost += cost;

            for(int i = 0; i < n; i++) {
                if(!vis[i]) {
                    int dist = Math.abs(points[u][0] - points[i][0]) + Math.abs(points[u][1] - points[i][1]);

                    pq.offer(new int[]{dist,i});
                }
            }
        }

        return minCost;
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

        System.out.println(bs.searchInRotatedSortedArray(new int[]{4,5,6,7,0,1,2}, 0));

        System.out.println(bs.searchInRotatedSortedArrayWithDuplicates(new int[]{3,1,2,3}, 3));

        System.out.println(bs.findMin(new int[]{1,2}));
        System.out.println(bs.findMinWithDuplicates( new int[]{3, 3, 1, 3}));

        System.out.println(bs.MedianOfTwoSortedArray(new int[]{}, new int[]{1,2,3}));

        System.out.println("=================== STACK =================================================");
        MonotonicStack ms = new  MonotonicStack();
        System.out.println(Arrays.toString(ms.DailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73})));

        System.out.println(ms.BasicCalculator2(" 3/2 "));

        System.out.println(ms.largestRectangleHistogram2Pass(new int[]{2,1,5,6,2,3}));

        System.out.println(ms.largestRectangleHistogram(new int[]{2,1,5,6,2,3}));

        System.out.println("=================== HEAP/PRIORITY QUEUE =================================================");
        HeapPriorityQueue pq = new HeapPriorityQueue();
        System.out.println(Arrays.toString(pq.topKFrequent(new int[]{1,1,1,2,2,3}, 2)));
        System.out.println(Arrays.toString(pq.topKFrequentBucketSort(new int[]{1}, 1)));
        System.out.println(pq.findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2));

        System.out.println("=================== GRAPHS BFS/DFS =================================================");
        GraphNormal gn = new  GraphNormal();
        System.out.println(gn.MultiSourceBFSOranges(new int[][]{
                {2,1,1},
                {1,1,0},
                {0,1,1}
        }));

        System.out.println(gn.ladderLength("hit", "cog", List.of("hot","dot","dog","lot","log","cog")));
        System.out.println(Arrays.toString(gn.findOrder(2, new int[][]{{1,0}})));
        System.out.println(Arrays.toString(gn.findOrderDFS(2, new int[][]{{1,0}})));

    }
}
