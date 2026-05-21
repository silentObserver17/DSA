package Basics;

import java.util.Arrays;

public class BasicArrays {
    public  int sum(int arr[], int n) {
        int sum = 0;
        for(int i = 0; i < n; i++){
            sum += arr[i];
        }

        return sum;
    }

    public int countOdd(int[] arr, int n) {
        int count = 0;

        for(int i = 0; i < n; i++){
            if(arr[i] % 2 == 1) count++;
        }

        return count;
    }

    public boolean arraySortedOrNot(int[] arr, int n) {
        for(int i = 1; i < n; i++){
            if(arr[i-1] > arr[i]) return false;
        }

        return true;
    }

    public void reverse(int[] arr, int n){
        int left = 0;
        int right = n - 1;

        while(left <= right){
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;

            left++;
            right--;
        }

        System.out.println(Arrays.toString(arr));
    }
}
