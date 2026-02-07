package Recursion;

import java.util.ArrayList;
import java.util.List;

public class BasicRecursion {
    public int PowerN(int x, int n){
        if(n == 0) return 1;
        if(n == 1) return x;

        return x * PowerN(x,  n-1);
    }

    public int PowerNOptimal(int x, int n){
        if(n == 0) return 1;
        // if(n == 1) return x;

        int half = PowerNOptimal(x,n/2);

        if(n % 2 == 0){
            return half * half;
        }else{
            return x * half * half;
        }
    }

    public int SumOfDigitsBruteForce(int num){
        int sum = 0;

        while(num != 0){
            sum += num % 10;
            num /= 10;
        }

        return sum;
    }

    public int SumOfDigitsRecursion(int num){
        if(num == 0) return 0;

        int lastDigit = num % 10;
        int smallerProblem = num / 10;

        return lastDigit + SumOfDigitsRecursion(smallerProblem);
    }

    public String ReverseString(String str){
        if(str.isEmpty()) return "";

        return ReverseString(str.substring(1)) +  str.charAt(0);
    }

    public String ReverseStringSecond(String str){
        if(str.isEmpty()) return "";

        return str.charAt(str.length()-1) + ReverseString(str.substring(0,str.length()-1));
    }

    public List<String> generateParenthesis(int n){
        List<String> result = new ArrayList<>();
        generateParenthesisHelper("", 0, 0, n, result);

        return result;
    }

    public void generateParenthesisHelper(String current, int open, int close, int n, List<String> result){
        if(open == n && close == n){
            result.add(current);
            return;
        }

        if(open < n){
            generateParenthesisHelper(current + "(", open + 1, close, n, result);
        }

        if(close < n && close < open){
            generateParenthesisHelper(current + ")", open, close + 1, n, result);
        }
    }

    public List<List<Integer>> powerSet(int[] arr){
        List<List<Integer>> result = new ArrayList<>();
        powerSetHelper(0, new ArrayList<>(), result, arr);

        return result;
    }

    private void powerSetHelper(int startIndex, List<Integer> current, List<List<Integer>> result, int[] nums){
        result.add(new ArrayList<>(current));

        for(int i = startIndex; i < nums.length; i++){
            current.add(nums[i]);
            powerSetHelper(i + 1,  current, result, nums);
            current.removeLast();
        }
    }

    public boolean subsequenceSumK(int[] nums, int k){
        return subsequenceSumKHelper(nums, k, nums.length - 1);
    }

    private boolean subsequenceSumKHelper(int[] nums, int k, int index){
        if(index < 0){
            return k == 0;
        }

        boolean skip = subsequenceSumKHelper(nums, k, index - 1);
        boolean take = false;
        if(k >= nums[index]){
            take = subsequenceSumKHelper(nums, k - nums[index], index - 1);
        }

        return skip || take;
    }

    public int subsequenceSumKCount(int[] nums, int k){
        return subsequenceSumKCountHelper(nums, k, nums.length - 1);
    }

    private int subsequenceSumKCountHelper(int[] nums, int k, int index){
        if(index < 0) {
            return (k == 0) ? 1 : 0;
        }

        int skip = subsequenceSumKCountHelper(nums, k, index - 1);
        int take = 0;
        if(k >= nums[index]){
            take = subsequenceSumKCountHelper(nums, k - nums[index], index - 1);
        }

        return skip + take;
    }
}
