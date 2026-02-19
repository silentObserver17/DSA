package StackQueues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class MonotonicStack {
    private boolean isMatched(char open, char close){
        if((open == '(' && close == ')') ||
                (open == '[' && close == ']') ||
                (open == '{' && close == '}')
        ) return true;

        return false;
    }

    public boolean isValidParenthesis(String str){
        Stack<Character> stack = new Stack<>();

        for(char c : str.toCharArray()){
            if(c == '(' || c == '[' || c == '{' ){
                stack.push(c);
            }
            else if(c == ')' || c == ']' || c == '}' ){
                if(stack.isEmpty()) return false;
                char popped = stack.pop();
                if(!isMatched(popped, c)){
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    public int[] nextLargerElementBrute(int[] arr) {
        int[] result = new int[arr.length];
        Arrays.fill(result, -1);

        for(int i = 0; i < arr.length; i++){
            for(int j = i + 1; j < arr.length; j++){
                if(arr[i] < arr[j]){
                    result[i] = arr[j];
                    break;
                }
            }
        }

        return result;
    }

    public int[] nextLargerElement(int[] arr) {
        int n = arr.length;
        int[] result = new int[n];
        Stack<Integer> stack = new Stack<>();

        for(int i = n - 1; i >= 0; i--){
            int curr = arr[i];
            while(!stack.isEmpty() && stack.peek() <= curr){
                stack.pop();
            }

            if(stack.isEmpty()) result[i] = -1;
            else result[i] = stack.peek();

            stack.push(curr);
        }

        return result;
    }

    public int[] nextSmallerElement(int[] arr){
        int n = arr.length;
        int[] result = new int[n];
        Stack<Integer> stack = new Stack<>();

        for(int i = n - 1; i >= 0; i--){
            int curr = arr[i];
            while(!stack.isEmpty() && stack.peek() >= curr){
                stack.pop();
            }

            if(stack.isEmpty()) result[i] = -1;
            else result[i] = stack.peek();

            stack.push(curr);
        }

        return result;
    }

    public int[] nextGreaterElements(int[] arr) {
        int n = arr.length;
        int[] result = new int[n];

        Stack<Integer> stack = new Stack<>();
        for(int i = 2 * n - 1; i >= 0; i--){
            int curr = arr[i%n];

            while(!stack.isEmpty() && stack.peek() <= curr){
                stack.pop();
            }

            if(i < n){
                if(stack.isEmpty()) result[i] = -1;
                else result[i] = stack.peek();
            }

            stack.push(curr);
        }

        return result;
    }

    public int[] asteroidCollision(int[] asteroids){
        Stack<Integer> st = new Stack<>();

        for(int ast : asteroids){
            if(ast > 0) st.push(ast);
            else{
                int abs = Math.abs(ast);

                while(!st.isEmpty() && st.peek() > 0 && abs > st.peek()){
                    st.pop();
                }

                if(!st.isEmpty() && st.peek() == abs){
                    st.pop();
                }else if(st.isEmpty() || st.peek() < 0){
                    st.push(ast);
                }
            }
        }

        int[] result = new int[st.size()];
        for(int i =0; i < st.size(); i++){
            result[i] = st.get(i);
        }

        return result;
    }

    Stack<Integer> stack;
    private int[] findNextSmaller(int[] arr){
        int n = arr.length;
        stack = new Stack<>();
        int[] result =  new int[n];
        Arrays.fill(result, n);

        for(int i = n - 1; i >= 0; i--){
            while(!stack.isEmpty() && arr[stack.peek()] >= arr[i]){
                stack.pop();
            }

            if(!stack.isEmpty()) result[i] = stack.peek();

            stack.push(i);
        }

        return result;
    }

    private int[] findPreviousSmallerElement(int[] arr){
        int n  = arr.length;
        stack = new Stack<>();
        int[] result =  new int[n];
        Arrays.fill(result, -1);
        for(int i = 0; i < n; i++){
            while(!stack.isEmpty() && arr[stack.peek()] > arr[i]){
                stack.pop();
            }

            if(!stack.isEmpty())  result[i] = stack.peek();

            stack.push(i);
        }

        return result;
    }

    private int[] findNextLarger(int[] arr){
        int n = arr.length;
        stack = new Stack<>();
        int[] result =  new int[n];
        Arrays.fill(result, n);

        for(int i = n - 1; i >= 0; i--){
            while(!stack.isEmpty() && arr[stack.peek()] <= arr[i]){
                stack.pop();
            }

            if(!stack.isEmpty()) result[i] = stack.peek();
            stack.push(i);
        }

        return result;
    }

    private int[] findPreviousLarger(int[] arr){
        int n = arr.length;
        stack = new Stack<>();
        int[] result =  new int[n];
        Arrays.fill(result, -1);
        for(int i = 0;  i < n; i++){
            while(!stack.isEmpty() && arr[stack.peek()] < arr[i]){
                stack.pop();
            }

            if(!stack.isEmpty()) result[i] = stack.peek();

            stack.push(i);
        }
        return result;
    }

    public long sumSubarrayMins(int[] arr){
        int n = arr.length;
        int[] prevSmaller =  findPreviousSmallerElement(arr);
        int[] nextSmaller = findNextSmaller(arr);
        long sum = 0;
        int mod = (int)1e9 + 7;

        for(int i = 0; i < n; i++){
            int left = prevSmaller[i], right = nextSmaller[i];

            long count = (long) (i - left) * (right - i);
            sum = (sum + count * arr[i]) % mod;
        }

        return (int)sum;
    }

    public long sumSubarrayMaxs(int[] arr){
        int n = arr.length;
        int[] prevLarger = findPreviousLarger(arr);
        int[] nextLarger = findNextLarger(arr);
        long sum = 0;
        int mod =  (int)1e9 + 7;

        for(int i = 0; i < n; i++){
            int left = prevLarger[i], right = nextLarger[i];
            long count = (long) (i - left) * (right - i);
            sum = (sum + count * arr[i]) % mod;
        }
        return sum;
    }

    public long subArrayRanges(int[] nums) {
        long maxSum = sumSubarrayMaxs(nums);
        long minSum = sumSubarrayMins(nums);
        int mod = (int)1e9 + 7;

        long result = (maxSum - minSum) % mod;
        return result;
    }

    public String removeKdigits(String nums, int k) {
        if (k >= nums.length()) return "0";
        Stack<Character> stack = new Stack<>();

        for(int i = 0; i < nums.length(); i++){
            while(!stack.isEmpty() && k > 0 && stack.peek() > nums.charAt(i)){
                stack.pop();
                k--;
            }

            stack.push(nums.charAt(i));
        }

        while(k > 0 && !stack.isEmpty()){
            stack.pop();
            k--;
        }

        if(stack.isEmpty()) return "0";
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()){
            sb.append(stack.pop());
        }

        sb.reverse();

        int start = 0;
        while(start < sb.length() && sb.charAt(start) == '0'){
            start++;
        }

        String result = sb.substring(start);
        return result.isEmpty() ? "0" : result;
    }

}
