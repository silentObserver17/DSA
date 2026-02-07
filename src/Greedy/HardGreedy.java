package Greedy;

import java.util.Arrays;

public class HardGreedy {
    public boolean isValidBrute(String s) {
        return validParanthesisChecker(0, 0, s);
    }

    private boolean validParanthesisChecker(int open, int index, String s) {
        if(open < 0) return false;
        if(index == s.length()){
            return open == 0;
        }

        char ch = s.charAt(index);

        if(ch == '('){
            return validParanthesisChecker(open + 1, index + 1, s);
        }

        if(ch == ')'){
            return validParanthesisChecker(open - 1, index + 1, s);
        }

        // if ch == '*'
        return validParanthesisChecker(open, index + 1, s) || // * as empty string
                validParanthesisChecker(open + 1, index + 1, s) || // * as open paranthesis
                validParanthesisChecker(open - 1, index + 1, s); // * as close paranthesis
    }

    public boolean isValid(String s) {
        int minOpen = 0;
        int maxOpen = 0;

        for(char ch : s.toCharArray()){
            if(ch == '('){
                minOpen++;
                maxOpen++;
            }
            else if(ch == ')'){
                minOpen--;
                maxOpen--;
            }
            else if(ch == '*'){
                minOpen--;
                maxOpen++;
            }

            if(minOpen < 0) minOpen = 0;
            if(maxOpen < 0) return false;
        }

        return minOpen == 0;
    }

    public boolean isValidTwoPasses(String s) {
        int balance = 0;
        int startCount = 0;

        // ensure we don't have too many closing brackets.
        for(char ch : s.toCharArray()){
            if(ch == '('){
                balance++;
            }
            else if(ch == ')'){
                balance--;
            }else if(ch == '*'){
                startCount++;
            }

            if(balance < 0){
                if(startCount > 0){
                    startCount--;
                    balance++;
                }else{
                    return false;
                }
            }
        }

        balance = 0;
        startCount = 0;

        // ensure we don't have too many opening brackets. // backward
        for(int i = s.length() - 1; i >= 0; i--){
            char ch = s.charAt(i);

            if(ch == ')'){
                balance++;
            }
            else if(ch == '('){
                balance--;
            }
            else if(ch == '*'){
                startCount++;
            }

            if(balance < 0){
                if(startCount > 0){
                    startCount--;
                    balance++;
                }else{
                    return false;
                }
            }
        }

        return true;
    }

    public int candy(int[] ratings) {
        int[] candies = new int[ratings.length];
        Arrays.fill(candies, 1);

        // left pass.
        for(int i = 1; i < ratings.length; i++){
            if(ratings[i] > ratings[i-1]){
                candies[i] = Math.max(candies[i], candies[i-1] + 1);
            }
        }

        for(int i = ratings.length - 2; i >= 0; i--){
            if(ratings[i] > ratings[i+1]){
                candies[i] = Math.max(candies[i], candies[i+1] + 1);
            }
        }

        int res = Arrays.stream(candies).sum();
        return res;
    }

    public int canCompleteCycle(int[] gas, int[] cost){
        int total = 0;
        int tank = 0;
        int start = 0;

        for(int i =0; i < gas.length; i++){
            int diff = gas[i] - cost[i];
            total += diff;
            tank += diff;

            if(tank < 0){
                tank = 0;
                start = i + 1;
            }
        }

        return total >= 0 ? start : -1;
    }

}
