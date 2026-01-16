package BinarySearch;

public class LBBinarySearch {
    public int lowerBound(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;

        int start = 0;
        int end = nums.length - 1;
        int lowerBound = nums.length;

        while(start <= end){
            int mid = start + (end - start)/2;

            if(nums[mid] >= target){
                lowerBound = mid;
                end = mid - 1;
            }else{
                start = mid + 1;
            }
        }

        return lowerBound;
    }

    public int upperBound(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;

        int start = 0;
        int end = nums.length - 1;
        int upperBound = nums.length;

        while(start <= end){
            int mid = start + (end - start)/2;

            if(nums[mid] > target){
                upperBound = mid;
                end = mid - 1;
            }else{
                start = mid + 1;
            }
        }

        return upperBound;
    }


    public int[] firstLastOccurrence(int[] nums, int target) {
        int first = lowerBound(nums, target);
        if(first == nums.length || nums[first] != target) return new int[]{-1, -1};

        int last = upperBound(nums, target); // the last will be valid since first exists.
        return new int[]{first, last};
    }

    public int minimumInRotatedArray(int[] nums){
        int left = 0;
        int right = nums.length - 1;

        while(left < right){
            int mid = left + (right - left)/2;
            if(nums[mid] > nums[right]){
                left = mid + 1;
            }else{
                right = mid;
            }
        }

        return nums[left];
    }

    public int minimumInRotatedArrayDuplicates(int[] nums){
        int left = 0;
        int right = nums.length - 1;

        while(left < right){
            int mid = left + (right - left)/2;
            if(nums[mid] > nums[right]){
                left = mid + 1;
            }else if (nums[mid] <  nums[right]){
                right = mid;
            }else{
                right--;
            }
        }

        return nums[left];
    }

    public int searchInRotatedArray(int[] nums, int target) {
        if(nums == null || nums.length == 0) return -1;
        int left = 0;
        int right = nums.length - 1;

        while(left <= right){
            int mid = left + (right - left)/2;

            if(nums[mid] == target) return mid;

            if(nums[left] <= nums[mid]){ // means left is sorted.
                if(nums[left] <= target && target < nums[mid]){
                    right = mid - 1;
                }else{
                    left = mid + 1;
                }
            }else{ // means right is sorted
                if(nums[mid] < target && target <= nums[right]){
                    left = mid + 1;
                }else{
                    right = mid - 1;
                }
            }
        }

        return -1;
    }

    public int searchInRotatedArrayDuplicates(int[] nums, int target) {
        if(nums == null || nums.length == 0) return -1;
        int left = 0;
        int right = nums.length - 1;

        while(left <= right){
            int mid = left + (right - left)/2;

            if(nums[mid] == target) return mid;

            if(nums[left] == nums[mid] && nums[mid] == nums[right]){
                left++;
                right--;
                continue;
            }

            if(nums[left] <= nums[mid]){ // means left is sorted.
                if(nums[left] <= target && target < nums[mid]){
                    right = mid - 1;
                }else{
                    left = mid + 1;
                }
            }else{ // means right is sorted
                if(nums[mid] < target && target <= nums[right]){
                    left = mid + 1;
                }else{
                    right = mid - 1;
                }
            }
        }

        return -1;
    }

    public int NoOfTimesArrayRotated(int[] nums){
        int left = 0;
        int right = nums.length - 1;

        while(left < right){
            int mid = left + (right - left)/2;
            if(nums[mid] > nums[right]){
                left = mid + 1;
            }else if (nums[mid] <  nums[right]){
                right = mid;
            }else{
                right--;
            }
        }

        return left;
    }

    public int singleNonDuplicate(int[] nums){
        int n = nums.length;

        if (n == 1) return nums[0];

        if (nums[0] != nums[1]) return nums[0];
        if (nums[n - 1] != nums[n - 2]) return nums[n - 1];

        int start = 1;
        int end = n - 2;

        while(start < end){
            int mid = start + (end - start)/2;
            if(mid % 2 == 0){
                if(nums[mid] == nums[mid + 1]){
                    start = mid + 2;
                }else{
                    end = mid - 1;
                }
            }
            else{
                if(nums[mid] == nums[mid - 1]){
                    start = mid + 2;
                }else{
                    end = mid - 1;
                }
            }
        }

        return nums[start];
    }


    public int singleNonDuplicateClean(int[] nums) {
        int n = nums.length;
        int start = 0;
        int end = n - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;

            if (mid % 2 == 1) {
                mid--;
            }

            if(nums[mid] == nums[mid + 1]){
                start = mid + 2;
            }else{
                end = mid;
            }

        }

        return nums[start];
    }

}
