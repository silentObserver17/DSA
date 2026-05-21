package Basics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BasicMaths {
    public int countDigit(int n) {
        int count = 0;

        n = Math.abs(n); // handle negative numbers.
        if(n == 0) return 1;

        while(n > 0){
            count++;
            n = n / 10;
        }

        return count;
    }

    public int countDigitMaths(int n){
        if(n == 0) return 1;
        return (int) Math.log10(Math.abs(n)) + 1;
    }

    public int countOddDigit(int n) {
        int count = 0;

        while(n > 0){
            int digit = n % 10;
            if(digit % 2 == 1) count++;
            n = n/10;
        }

        return count;
    }

    public int reverseNumber(int n) {
        if(n == 0) return 0;
        int reversed = 0;

        while(n > 0){
            int digit = n % 10;
            reversed = reversed * 10 + digit;
            n = n/10;
        }

        return reversed;
    }

    public boolean isPalindrome(int n) {
        if (n % 10 == 0 && n != 0) return false;

        int reversedNumber =  reverseNumber(n);
        return n == reversedNumber;
    }

    public int largestDigit(int n) {
        int largestDigit = Integer.MIN_VALUE;

        while(n > 0){
            int digit = n % 10;
            largestDigit = Math.max(digit, largestDigit);
            n = n/10;
        }

        return largestDigit;
    }

    public int factorial(int n){
        if(n == 0) return 1;
        int factorial = 1;

        for(int i = n; i >=1 ; i--){
            factorial = factorial * i;
        }

        return factorial;
    }

    public boolean isArmstrong(int n) {
        if(n == 0) return true;

        int original = n;
        int count = countDigitMaths(n);
        int sum = 0;

        while(n > 0){
            int digit = n % 10;
            sum += (int) Math.pow(digit, count);
            n = n/10;
        }

        return sum == original;
    }

    public boolean isPerfect(int n) {
        int sum = 0;

        for(int i = 1; i < n; i++){
            if(n % i == 0){
                sum += i;
            }
        }

        return sum == n;
    }

    public boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;

        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    public int primeUptoNBrute(int n) {
        int count = 0;

        for (int i = 2; i <= n; i++) {
            if (isPrime(i)) {
                count++;
            }
        }

        return count;
    }

    public int GCDBrute(int n1, int n2) {
        int min = Math.min(n1, n2);
        int gcd = 1;

        for(int i = 1; i <= min; i++){
            if(n1 % i == 0 && n2 % i == 0){
                gcd = i;
            }
        }

        return gcd;
    }

    public int GCD(int n1, int n2) {
        while(n1 > 0 && n2 > 0){
            if(n1 > n2){
                n1 = n1 % n2;
            }else{
                n2 = n2 % n1;
            }
        }

        if(n1 == 0) return n2;
        return n1;
    }

    public int GCDRecursion(int n1, int n2){
        if(n2 == 0) return n1;
        return GCDRecursion(n2, n1 % n2);
    }

    public int lcmBrute(int n1, int  n2) {
        int max = Math.max(n1,n2);

        while(true){
            if(max % n1 == 0 && max % n2 == 0){
                return max;
            }
            max++;
        }
    }

    public int LCM(int n1, int n2){
        int gcd = GCDRecursion(n1,n2);

        return (n1/gcd) * n2;
    }

    public int[] divisors(int n) {
        int[] temp = new int[n];
        int count = 0;

        for(int i = 1; i <= n; i++){
            if(n % i == 0) temp[count++] = i;
        }

        int[] ans =  Arrays.copyOf(temp, count);

        return ans;
    }

    public int[] divisors2(int n) {
        List<Integer> list = new ArrayList<>();

        for(int i = 1; i * i <= n; i++){
            if(n % i == 0){
                list.add(i);

                if(i != n/i) list.add(n/i);
            }
        }

        Collections.sort(list);
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

}
