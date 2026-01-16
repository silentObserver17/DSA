package RevisitArrays.Hard;

import Sorting.MergeSort;

import java.util.*;

public class ArraysHard {
    public List<Integer> majorityElementTwo(int[] nums){
        HashMap<Integer, Integer> map = new HashMap<>();
        List<Integer> ans = new ArrayList<>();
        int n = nums.length;

        for(int num: nums){
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        for(Map.Entry<Integer, Integer> entry: map.entrySet()){
            if(entry.getValue() > n/3){
                ans.add(entry.getKey());
            }
        }

        return ans;
    }

    // using Boyer Moore voting algorithm extended as it eliminates the extra space.
    // this algo works because at most two elements can appear more than n/3 times.
    // so we choose two candidates and two count variables. the rest is same.
    public List<Integer> majorityElementTwoOptimization(int[] nums){
        List<Integer> ans = new ArrayList<>();
        int candidate1 = 0, candidate2 = 1;
        int count1 = 0, count2 = 0;

        for(int num: nums){
            if(num == candidate1){
                count1++;
            }else if(num == candidate2){
                count2++;
            }else if(count1 == 0){
                candidate1 = num;
                count1 = 1;
            }else if(count2 == 0){
                candidate2 = num;
                count2 = 1;
            }else{
                count1--;
                count2--;
            }
        }

        count1 = 0;
        count2 = 0;

        for(int num: nums){
            if(num == candidate1){
                count1++;
            }else if(num == candidate2){
                count2++;
            }
        }

        int threshold = nums.length/3;

        if(count1 > threshold){
            ans.add(candidate1);
        }

        if(count2 > threshold){
            ans.add(candidate2);
        }

        return ans;
    }

    public int[] findMissingRepeatingNumbers(int[] nums){
        int[] ans = new int[2];
        int missing = -1; // on 1st index
        int duplicate = -1; // on 0th index

        Set<Integer> seen = new HashSet<>();
        for(int num: nums){
            if(!seen.add(num))
            {
                duplicate = num;
                break;
            }
        }

        int n = nums.length;
        int currentSum = Arrays.stream(nums).sum() -  duplicate;
        int outputSum = n * (n + 1)/2;

        missing = outputSum - currentSum;
        ans[0] = duplicate;
        ans[1] = missing;

        return ans;
    }

    public int[] findMissingRepeatingNumbersOptimized(int[] nums){
        long n = nums.length;

        // S - Sn = x - y (where x = repeating, y = missing)
        long actualSum = 0;
        long actualSumSquares = 0;
        for(int num: nums){
            actualSum += num;
            actualSumSquares += (long) num * num;
        }

        long expectedSum = n * (n + 1)/2;
        long expectedSquareSum = n * (n + 1) * (2 * n + 1)/6;

        // x - y
        long diff = actualSum - expectedSum;
        // x^2 * y ^2 = (x-y)(x+y)
        long squareDiff = actualSumSquares - expectedSquareSum;

        // x + y
        long sum = squareDiff/diff;

        int duplicate = (int)((diff + sum)/2);
        int missing = (int) (sum - duplicate);

        return new int[]{duplicate, missing};
    }

    public int countInversionsBrute(int[] nums){
        int count = 0;

        for(int i = 0; i < nums.length; i++){
            for(int j = i + 1; j < nums.length; j++){
                if(nums[i] > nums[j]){
                    count++;
                }
            }
        }

        return count;
    }

    public int countInversionsOptimal(int[] nums){
        return MergeSort(nums, 0, nums.length - 1);
    }

    public int MergeSort(int[] nums, int start, int end){
        int count = 0;
        if(start >= end){
            return count;
        }

        int mid = (start + end)/2;
        count += MergeSort(nums, start, mid);
        count += MergeSort(nums, mid + 1, end);
        count += Merge(nums, start, mid, end);

        return count;
    }

    public int Merge(int[] nums, int start, int mid, int end){
        int left = start;
        int right = mid + 1;
        int[] temp = new int[end - start + 1];
        int j = 0;
        int count = 0;

        while(left <= mid && right <= end){
            if(nums[left] <= nums[right]){
                temp[j] = nums[left];
                left++;
            }
            // right is smaller
            else{
                temp[j] = nums[right];
                count += (mid - left + 1);
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

        return count;
    }

    public int maxProduct(int[] nums) {
        if(nums.length == 0) return 0;

        int maxSoFar = nums[0];
        int maxEndingHere = nums[0];
        int minEndingHere = nums[0];

        for(int i = 1; i < nums.length; i++){
            int current = nums[i];

            if(current < 0){
                int temp = maxEndingHere;
                maxEndingHere = minEndingHere;
                minEndingHere = temp;
            }

            maxEndingHere = Math.max(current, maxEndingHere * current);
            minEndingHere = Math.min(current, minEndingHere * current);

            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }

        return maxSoFar;
    }

    public int ReversePairs(int[] nums){
        return mergeSortForReversePairs(nums, 0, nums.length - 1);
    }

    public int mergeSortForReversePairs(int[] nums, int low, int high){
        if(low >= high) return 0;
        int count = 0;

        int mid = (low + high)/2;
        count += mergeSortForReversePairs(nums, low, mid);
        count += mergeSortForReversePairs(nums, mid + 1, high);
        count += countReversePairs(nums, low, mid, high);
        mergeReversePairs(nums, low, mid, high);

        return count;
    }

    public void mergeReversePairs(int[] nums, int low, int mid, int high){
        List<Integer> temp = new ArrayList<>();
        int left = low, right = mid + 1;

        while(left <= mid && right <= high){
            if(nums[left] <= nums[right]){
                temp.add(nums[left]);
                left++;
            }else{
                temp.add(nums[right]);
                right++;
            }
        }

        while(left <= mid) temp.add(nums[left++]);
        while(right <= high) temp.add(nums[right++]);

        for(int i = low; i <= high; i++){
            nums[i] = temp.get(i - low);
        }
    }

    public int countReversePairs(int[] nums, int low, int mid, int high){
        int right = mid + 1, count = 0;

        for(int i = low; i <= mid; i++){
            while(right <= high && (long) nums[i] > 2L * nums[right]){
                right++;
            }
            count += (right - (mid + 1));
        }

        return count;
    }

    // Firstly we swap minimum elements from right to the left as we know all the minimum elements
    // will be on the left. and we break condition when we encounter an element on the left that is
    // smaller than the element on right, since the array is sorted we know all the elements before
    // that element on the left array are gonna be less than the ones on the right so we directly break
    // the array to avoid useless traversal. and then sort both arrays and copy all elements on the right
    // to the left.
    // TC: O(Min(m, n)) + O(n log(n)) + O(m log(m)) => loop + sort n + sort m.
    // SC: O(1) as we are not using any extra space.
    public void MergeWithoutExtraSpace1(int[] nums1, int[] nums2, int m, int n){
        int left = m - 1;
        int right = 0;

        while(left >= 0 && right < n){
            if(nums1[left] > nums2[right]){
                int temp = nums1[left];
                nums1[left] = nums2[right];
                nums2[right] = temp;
                left--;
                right++;
            }else break;
        }

        Arrays.sort(nums1, 0, m);
        Arrays.sort(nums2);

//        for(int i = m; i < m + n; i++){
//            nums1[i] = nums2[i - m];
//        }
//
        System.arraycopy(nums2, 0, nums1, m, n);

        System.out.println(Arrays.toString(nums1));
    }

}








