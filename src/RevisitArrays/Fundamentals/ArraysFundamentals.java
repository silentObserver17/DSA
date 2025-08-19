package RevisitArrays.Fundamentals;

import java.util.Arrays;

public class ArraysFundamentals {
    public int linearSearch(int nums[], int target) {
        int n = nums.length;

        for(int i = 0; i < n; i++){
            if(nums[i] == target){
                return i;
            }
        }

        return -1;
    }

    public int largestElement(int[] nums){
        int max = Integer.MIN_VALUE;

        for(int num: nums){
            max = Math.max(max, num);
        }

        return max;
    }

    public int secondLargestElement(int[] nums){
        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;

        if(nums.length < 2) return -1;

        for(int i = 0; i < nums.length; i++){
            if(nums[i] > largest){
                secondLargest = largest;
                largest = nums[i];
            }else if(nums[i] > secondLargest && nums[i] != largest){
                secondLargest = nums[i];
            }
        }

        if(secondLargest == Integer.MIN_VALUE) return -1;

        return secondLargest;
    }

    public int findMaxConsecutiveOnes(int[] nums) {
        int counter = 0;
        int maxCounter = 0;
        int j = 0;

        while(j < nums.length){
            if(nums[j] == 1){
                counter++;
            }else{
                maxCounter = Math.max(counter, maxCounter);
                counter = 0;
            }
            j++;
        }

        return Math.max(maxCounter, counter);
    }

    public void rotateArrayByOne(int[] nums) {
        int storeFirst = nums[0];
        int n = nums.length;

        for(int i = 1; i < n; i++){
            nums[i-1] = nums[i];
        }

        nums[n-1] = storeFirst;

        System.out.println(Arrays.toString(nums));
    }

    public void rotateArray(int[] nums, int k) {
        int n = nums.length;
        k = k % n;

        if(k >= n){
            return;
        }

        int[] leftElements = new int[k];

        System.arraycopy(nums, 0, leftElements, 0, k);

        for(int i = k; i < n; i++){
            nums[i - k] = nums[i];
        }

        System.arraycopy(leftElements, 0, nums, n - k, k);

        System.out.println(Arrays.toString(nums));
    }
}
