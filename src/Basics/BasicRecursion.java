package Basics;

import java.util.ArrayList;

public class BasicRecursion {
    public int arraySum(int[] nums){
        return  sumHelper(nums, 0);
    }

    private int sumHelper(int[] nums, int index){
        if(index==nums.length){
            return 0;
        }

        return nums[index] + sumHelper(nums,index+1);
    }

    public ArrayList<Character> reverseString(ArrayList<Character> s) {
        int left = 0;
        int right = s.size() - 1;

        reverseStringHelper(s, left, right);
        return s;
    }

    private void reverseStringHelper(ArrayList<Character> s, int left, int right){
        if(left > right) return;

        char temp = s.get(left);
        s.set(left,s.get(right));
        s.set(right,temp);

        reverseStringHelper(s,left+1,right-1);
    }

    public boolean palindromeCheck(String s) {
        int left = 0;
        int right = s.length()-1;

        return palindromeCheckHelper(s, left,right);
    }

    public boolean palindromeCheckHelper(String s, int left, int right){
        if(left >= right) return true;

        if(s.charAt(left) != s.charAt(right)) return false;

        return palindromeCheckHelper(s, left + 1, right - 1);
    }

    public boolean checkPrime(int num) {
        if(num <= 1) return false;

        return checkPrimeHelper(num, 2);
    }

    public boolean checkPrimeHelper(int num, int x){
        if(x > Math.sqrt(num)) return true;

        if(num % x == 0) return false;

        return checkPrimeHelper(num, x + 1);
    }

    public int[] reverseArray(int[] nums){
        int left = 0;
        int right = nums.length - 1;

        reverseArrayHelper(nums, left, right);
        return nums;
    }

    public void reverseArrayHelper(int[] nums, int left, int right){
        if(left >= right) return;

        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;

        reverseArrayHelper(nums, left + 1, right - 1);
    }

    public boolean isSorted(ArrayList<Integer> nums) {
        int index = 1;

        return isSortedHelper(nums, index);
    }

    private boolean isSortedHelper(ArrayList<Integer> nums, int index) {
        if(index == nums.size()) return true;

        if(nums.get(index) < nums.get(index - 1)) return false;

        return isSortedHelper(nums, index + 1);
    }

    public int addDigits(int num) {
        if(num < 10)  return num;

        int sum = addDigitsHelper(num);

        return addDigits(sum);
    }

    private int addDigitsHelper(int num){
        if(num == 0) return 0;

        return num%10 +  addDigitsHelper(num/10);
    }

    public int fibonacci(int n){
        if(n == 0) return 0;
        if(n == 1) return 1;

        return fibonacci(n-1) + fibonacci(n-2);
    }
}
