package RevisitArrays.LogicBuilding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArraysLogic {
    public void moveZeroes(int[] nums) {
        int insertPosition = 0;

        for(int num: nums){
            if(num != 0){
                nums[insertPosition++] = num;
            }
        }

        while(insertPosition != nums.length){
            nums[insertPosition++] = 0;
        }

        System.out.println(Arrays.toString(nums));
    }

    public int removeDuplicates(int[] nums) {
        int left = 0;

        for(int right = 1; right < nums.length; right++){
            if(nums[right] != nums[left]){
                left++;
                nums[left] = nums[right];
            }
        }

        return left + 1;
    }

    // best approach is the mathematical formula. sum of n number is n*(n+1)/2
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int expectedSum = n * (n + 1)/2;
        int actualSum = 0;

        for(int num: nums){
            actualSum += num;
        }

        return expectedSum - actualSum;
    }

    /*
    * If both arrays are sorted then this merge one is the best solution with
    * TC = (O(n+m)) AND SC = O(n+m) but this approach can't handle unsorted
    * output
    *
    * If both arrays are not sorted then TreeSet approach is the best as it
    * sorts all the elements inserted into the set. but it takes log(n) extra time
    * so TC = O((n+m)log(n+m)) and SC = O(n+m)
     */
    public int[] unionArray(int[] nums1, int[] nums2) {
        int i = 0;
        int j = 0;
        List<Integer> list = new ArrayList<>();
        Integer last = null;

        while(i < nums1.length && j < nums2.length){
            int val;
            if(nums1[i] == nums2[j]){
                val = nums1[i];
                i++;
                j++;
            }
            else if(nums1[i] < nums2[j]){
                val = nums1[i];
                i++;
            }
            else{
                val = nums2[j];
                j++;
            }

            if(last == null || val != last){
                list.add(val);
                last = val;
            }
        }

        while(i != nums1.length){
            if(last == null || nums1[i] != last){
                list.add(nums1[i]);
                last = nums1[i];
            }
            i++;
        }

        while(j != nums2.length){
            if(last == null || nums2[j] != last){
                list.add(nums2[j]);
                last = nums2[j];
            }
            j++;
        }

        int[] ans = new int[list.size()];
        for(int l = 0; l < ans.length; l++){
            ans[l] = list.get(l);
        }

        return ans;
    }

    public int[] intersectionArray(int[] nums1, int[] nums2) {
        int i = 0;
        int j = 0;
        List<Integer> list = new ArrayList<>();

        while(i < nums1.length && j < nums2.length){
            if(nums1[i] == nums2[j]){
                list.add(nums1[i]);
                i++;
                j++;
            }
            else if(nums1[i] < nums2[j]){
                i++;
            }
            else{
                j++;
            }
        }

        int[] ans = new int[list.size()];
        for(int l = 0; l < ans.length; l++){
            ans[l] = list.get(l);
        }

        return ans;
    }
}
