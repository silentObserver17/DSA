package Recursion;

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

}
