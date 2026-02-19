package StackQueues;

import java.util.*;

public class StackFAQ {
    class MinStack1{
        private Stack<int[]> st;

        public MinStack1(){
            st = new Stack<>();
        }

        public void push(int val){
            if(st.isEmpty()){
                st.push(new int[]{val, val});
                return;
            }
            int mini = Math.min(getMin(), val);
            st.push(new int[]{val, mini});
        }


        public void pop() {
            st.pop();
        }

        public int top() {
            return st.peek()[0];
        }

        public int getMin(){
            return st.peek()[1];
        }
    }

    class MinStack2{
        private Stack<Integer> dataStack;
        private Stack<Integer> minStack;

        public MinStack2(){
            dataStack = new Stack<>();
            minStack = new Stack<>();
        }

        public void push(int val){
            dataStack.push(val);

            if(minStack.isEmpty()){
                minStack.push(val);
            }else{
                int mini =  Math.min(minStack.peek(), val);
                minStack.push(mini);
            }
        }

        public void pop() {
            dataStack.pop();
            minStack.pop();
        }

        public int top() {
            return dataStack.peek();
        }

        public int getMin(){
            return minStack.peek();
        }
    }

    class MinStackOptimal{
        private Stack<Long> stack;
        private long min;

        public MinStackOptimal(){
            stack = new Stack<>();
        }

        public void push(int val){
            if(stack.isEmpty()){
                stack.push((long)val);
                min = val;
            }else if(val >= min){
                stack.push((long)val);
            }else{
                long encoded = 2L * val - min;
                stack.push(encoded);
                min = val;
            }
        }

        public void pop(){
            long top =  stack.pop();
            if(top < min){
                min = 2 * min - top;
            }
        }

        public int top(){
            long top = stack.peek();
            if(top < min){
                return (int)min;
            }
            return (int)top;
        }

        public long getMin(){
            return (int)min;
        }
    }

    public List<Integer> maxSlidingWindowBrute(int[] arr, int k) {
        int n = arr.length;
        List<Integer> ans = new ArrayList<>();

        for(int i = 0; i <= n - k; i++){
            int maxi = arr[i];
            for(int j = i; j < i + k; j++){
                maxi = Math.max(maxi, arr[j]);
            }
            ans.add(maxi);
        }

        return ans;
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();
        int ansIndex = 0;

        for(int i = 0; i < n; i++){
            if(!deque.isEmpty() && deque.peek() <= i - k){
                deque.pollFirst();
            }

            while(!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]){
                deque.pollLast();
            }

            deque.offerLast(i);
            if(i >= k - 1){
                ans[ansIndex++] = nums[deque.peekFirst()];
            }
        }

        return ans;
    }

    public int trappingRainWaterBrute(int[] height){
        int n = height.length;
        int totalWater = 0;

        for(int i = 0; i < n; i++){
            int maxLeft = 0;
            for(int j = 0; j <= i; j++){
                maxLeft = Math.max(maxLeft, height[j]);
            }

            int maxRight = 0;
            for(int j = i; j < n; j++){
                maxRight = Math.max(maxRight, height[j]);
            }

            int water = Math.min(maxLeft, maxRight) - height[i];

            totalWater += water;
        }

        return totalWater;
    }

    private int[] findPrefixMax(int[] arr, int n){
        int[] prefix = new int[n];

        prefix[0] = arr[0];
        for(int i = 1; i < n; i++){
            prefix[i] = Math.max(prefix[i - 1], arr[i]);
        }
        return prefix;
    }

    private int[] findSuffixMax(int[] arr, int n){
        int[] suffix = new int[n];
        suffix[n - 1] = arr[n - 1];

        for(int i = n - 2; i >= 0; i--){
            suffix[i] = Math.max(suffix[i + 1], arr[i]);
        }
        return suffix;
    }

    public int trappingRainWaterBetter(int[] height){
        int n = height.length;

        int total = 0;

        int[] prefix = findPrefixMax(height, n);
        int[] suffix = findSuffixMax(height, n);

        for(int i = 0; i < n; i++){
            if(height[i] < prefix[i] &&  height[i] < suffix[i]){
                total += Math.min(suffix[i], prefix[i]) - height[i];
            }
        }

        return total;
    }

    public int trappingRainWater(int[] height){
        int n = height.length;
        int total = 0;

        int left = 0, right = n - 1;
        int leftMax = 0, rightMax = 0;

        while(left < right){
            if(height[left] <= height[right]){
                if(height[left] < leftMax){
                    total += leftMax - height[left];
                }else{
                    leftMax = height[left];
                }
                left++;
            }else{
                if(height[right] < rightMax){
                    total += rightMax - height[right];
                }else{
                    rightMax = height[right];
                }
                right--;
            }
        }

        return total;
    }

    public int trappingRainWaterStack(int[] height){
        int n = height.length;
        int total = 0;

        Stack<Integer> stack = new Stack<>();

        for(int i = 0; i < n; i++){
            while(!stack.isEmpty() && height[stack.peek()] <= height[i]){
                int top = stack.pop();

                if(stack.isEmpty()) break;

                int left = stack.peek();
                int width = i - left - 1;
                int boundedHeight = Math.min(height[i], height[left]) - height[top];

                total += width * boundedHeight;
            }

            stack.push(i);
        }

        return total;
    }

    private int[] findPSE(int[] arr, int n){
        int[] pse = new int[n];
        Arrays.fill(pse, -1);

        Stack<Integer> stack = new Stack<>();
        for(int i = 0; i < n; i++){
            while(!stack.isEmpty() && arr[stack.peek()] >= arr[i]){
                stack.pop();
            }

            if(!stack.isEmpty()) pse[i] = stack.peek();

            stack.push(i);
        }

        return pse;
    }

    private int[] findNSE(int[] arr, int n){
        int[] nse = new int[n];
        Arrays.fill(nse, n);

        Stack<Integer> stack = new Stack<>();
        for(int i = n - 1; i  >= 0; i--){
            while(!stack.isEmpty() && arr[stack.peek()] >= arr[i]){
                stack.pop();
            }
            if(!stack.isEmpty()) nse[i] = stack.peek();
            stack.push(i);
        }
        return nse;
    }

    public int largestRectangleAreaBetter(int[] height){
        int n = height.length;
        int area = 0;
        int currentArea = 0;
        int[] nse = findNSE(height, n);
        int[] pse =  findPSE(height, n);

        for(int i = 0; i < n; i++){
            currentArea = height[i] * (nse[i] - pse[i] - 1);

            area = Math.max(currentArea, area);
        }

        return area;
    }

    public int largestRectangleAreaOptimal(int[] height){
        int n = height.length;
        int maxArea = 0;

        Stack<Integer> stack = new Stack<>();

        for(int i = 0; i < n; i++){
            while(!stack.isEmpty() && height[stack.peek()] >= height[i]){
                int top = stack.pop();

                int width;
                if(stack.isEmpty()){
                    width = i;
                } else {
                    width = i - stack.peek() - 1;
                }

                int currArea = width * height[top];
                maxArea = Math.max(currArea, maxArea);
            }

            stack.push(i);
        }

        while(!stack.isEmpty()){
            int top =  stack.pop();

            int width;
            if(stack.isEmpty()){
                width = n;
            } else {
                width = n - stack.peek() - 1;
            }

            int currArea = width * height[top];
            maxArea = Math.max(currArea, maxArea);
        }

        return maxArea;
    }

    public int maximalRectangleBrute(int[][] matrix){
        int n = matrix.length;
        int m = matrix[0].length;
        int maxArea = 0;

        // Fix top row.
        for (int top = 0; top < n; top++) {
            // Fix bottom row
            for (int bottom = top; bottom < n; bottom++) {
                // Fix left column
                for (int left = 0; left < m; left++) {
                    // Fix right column
                    for (int right = left; right < m; right++) {
                        // Check if all cells in this rectangle are '1'
                        boolean allOnes = true;

                        outer:
                        for (int i = top; i <= bottom; i++) {
                            for (int j = left; j <= right; j++) {
                                if (matrix[i][j] == '0') {
                                    allOnes = false;
                                    break outer;
                                }
                            }
                        }

                        // If valid rectangle, calculate area
                        if (allOnes) {
                            int height = bottom - top + 1;
                            int width  = right - left + 1;
                            int area   = height * width;
                            maxArea = Math.max(maxArea, area);
                        }
                    }
                }
            }
        }

        return maxArea;
    }

    public int maximalRectangle(int[][] matrix){
        int n = matrix.length;
        int m =  matrix[0].length;
        int maxArea = 0;
        int[] heights = new int[m];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(matrix[i][j] == 0){
                    heights[j] = 0;
                }else{
                    heights[j]++;
                }
            }
            maxArea = Math.max(maxArea, largestRectangleAreaOptimal(heights));
        }

        return maxArea;
    }

    public int[] stockSpan(int[] arr, int n) {
        int[] spans = new int[n];
        Arrays.fill(spans, 1);

        for(int i = 0; i < n; i++){
            int currSpan = 0;
            for(int j = i; j >= 0; j--){
                if(arr[i] >= arr[j]) currSpan++;
                else break;
            }
            spans[i] = currSpan;
        }

        return spans;
    }

    public int[] previousGreaterElement(int[] arr, int n){
        int[] pge = new int[n];
        Arrays.fill(pge, -1);
        Stack<Integer> st = new Stack<>();

        for(int i = 0; i < n; i++){
            while(!st.isEmpty() && arr[st.peek()] <= arr[i]){
                st.pop();
            }

            if(!st.isEmpty()) pge[i] = st.peek();

            st.push(i);
        }

        return pge;
    }

    public int[] stockSpanOptimal(int[] arr, int n) {
        int[] spans = new int[n];

        int[] pge = previousGreaterElement(arr, n);

        for(int i = 0; i < n; i++){
            spans[i] = i - pge[i];
        }

        return spans;
    }

    public int celebrityBrute(int[][] M) {
        int n = M.length;

        int[] knowMe = new int[n];
        int[] iKnow = new int[n];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(M[i][j] == 1){
                    knowMe[j]++;
                    iKnow[i]++;
                }
            }
        }

        for(int i = 0; i < n; i++){
            if(knowMe[i] == n - 1 &&  iKnow[i] == 0) return i;
        }

        return -1;
    }

    public int celebrity(int[][] M){
        int n = M.length;
        int top = 0;
        int bottom = n - 1;

        while(top < bottom){
            if(M[top][bottom] == 1){ // if top knows bottom not a celebrity.
                top = top + 1;
            }
            else if(M[bottom][top] == 1){ // if bottom know top it's not a celebrity.
                bottom = bottom - 1;
            }
            else{ // if both do not know each other, both cannot be a celebrity.
                top++;
                bottom--;
            }
        }

        if(top > bottom) return -1;

        for(int i = 0; i < n; i++){
            if(i == top) continue;

            if(M[top][i] == 1 || M[i][top] == 0) return -1;
        }

        return top;
    }

    class LRUCache{
        class Node{
            int key, val;
            Node prev, next;
            public Node(int key, int val){
                this.key = key;
                this.val = val;
                this.prev = null;
                this.next = null;
            }
        }

        private int capacity;
        private Node head, tail;
        private Map<Integer, Node> map;

        public LRUCache(int capacity){
            this.capacity = capacity;
            map = new HashMap<>();
            head = new Node(-1, -1);
            tail = new Node(-1, -1);

            head.next = tail;
            tail.prev = head;
        }

        private void addFront(Node node){
            node.next = head.next;
            node.prev = head;

            head.next.prev = node;
            head.next = node;
        }

        private void removeNode(Node node){
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        public int get(int key) {
            if(map.containsKey(key)){
                Node node = map.get(key);
                removeNode(node);
                addFront(node);
                return node.val;
            }

            return -1;
        }

        public void put(int key, int val) {
            if(map.containsKey(key)){
                Node node = map.get(key);
                removeNode(node);
                node.val = val;
                addFront(node);
                return;
            }

            if(capacity == map.size()){
                Node lru = tail.prev;
                removeNode(lru);
                map.remove(lru.key);
            }

            Node node = new Node(key, val);
            map.put(key, node);
            addFront(node);
        }
    }

    public int[] nextGreaterElement(int[] arr, int n){
        int[] nge = new int[n];
        Arrays.fill(nge, -1);
        Stack<Integer> st = new Stack<>();

        for(int i = n - 1; i >= 0; i--){
            while(!st.isEmpty() && arr[st.peek()] < arr[i]){
                st.pop();
            }

            if(!st.isEmpty()) nge[i] = st.peek();

            st.push(i);
        }

        return nge;
    }

    public int[] dailyTemperaturesBrute(int[] temps) {
        int n = temps.length;
        int[] ans = new int[n];
        int[] nge = nextGreaterElement(temps, n);

        for(int i = 0; i < n; i++){
            if(nge[i] != -1){
                ans[i] = nge[i] - i;
            }else{
                ans[i] = 0;
            }
        }

        return ans;
    }

    class LFUCache{
        class Node{
            int key, val, freq;
            Node prev, next;
            public Node(int key, int val){
                this.key = key;
                this.val = val;
                this.freq = 1;
                this.prev = null;
                this.next = null;
            }
        }

        class DoublyLinkedList{
            Node head, tail;
            int size;

            public DoublyLinkedList(){
                head = new Node(-1, -1);
                tail = new Node(-1, -1);
                head.next = tail;
                tail.prev = head;
                size = 0;
            }

            void addFront(Node node){
                node.next = head.next;
                node.prev = head;

                head.next.prev = node;
                head.next = node;
                size++;
            }

            void removeNode(Node node){
                node.prev.next = node.next;
                node.next.prev = node.prev;
                size--;
            }

            Node removeLast(){
                if(size == 0) return null;
                Node node = tail.prev;
                removeNode(node);
                return node;
            }
        }

        private int capacity;
        private Map<Integer, Node> keyMap;
        private Map<Integer, DoublyLinkedList> freqMap;
        private int minFreq;

        public LFUCache(int capacity){
            this.capacity = capacity;
            this.minFreq = 0;
            keyMap = new HashMap<>();
            freqMap = new HashMap<>();
        }

        public int get(int key){
            if(!keyMap.containsKey(key)) return -1;

            Node node = keyMap.get(key);
            updateFrequency(node);
            return node.val;
        }

        public void put(int key, int val){
            if(capacity == 0) return;

            if(keyMap.containsKey(key)){
                Node node = keyMap.get(key);
                node.val = val;
                updateFrequency(node);
                return;
            }

            if(keyMap.size() == capacity){
                DoublyLinkedList minList = freqMap.get(minFreq);
                Node toRemove = minList.removeLast();
                keyMap.remove(toRemove.key);
            }

            Node node = new Node(key, val);
            keyMap.put(key, node);

            freqMap.putIfAbsent(1,  new DoublyLinkedList());
            freqMap.get(1).addFront(node);
            minFreq = 1;
        }

        private void updateFrequency(Node node){
            int oldFreq = node.freq;
            DoublyLinkedList oldList = freqMap.get(oldFreq);

            oldList.removeNode(node);

            if(oldFreq == minFreq && oldList.size == 0) minFreq++;

            node.freq++;

            freqMap.putIfAbsent(node.freq, new DoublyLinkedList());
            freqMap.get(node.freq).addFront(node);
        }
    }
}
