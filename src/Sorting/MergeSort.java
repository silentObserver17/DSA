package Sorting;

import java.util.Arrays;

public class MergeSort {
    public void MergeSort(int[] nums, int start, int end){
        if(start >= end){
            return;
        }

        int mid = (start + end)/2;
        MergeSort(nums, start, mid);
        MergeSort(nums, mid + 1, end);
        Merge(nums, start, mid, end);

        System.out.println(Arrays.toString(nums));
    }

    public void Merge(int[] nums, int start, int mid, int end){
        int left = start;
        int right = mid + 1;
        int[] temp = new int[nums.length];
        int j = 0;

        while(left <= mid && right <= end){
            if(nums[left] <= nums[right]){
                temp[j] = nums[left];
                left++;
            }
            // right is smaller
            else{
                temp[j] = nums[right];
                right++;
            }
            j++;
        }

        while(left <= mid){
            temp[j] = nums[left];
            left++;
            j++;
        }

        while(right <= end){
            temp[j] = nums[right];
            right++;
            j++;
        }

        if (end + 1 - start >= 0) System.arraycopy(temp, 0, nums, start, end + 1 - start);
    }
}
