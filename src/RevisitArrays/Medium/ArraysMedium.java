package RevisitArrays.Medium;

import java.util.*;

public class ArraysMedium {
    public ArrayList<Integer> leaders(int[] nums){
        ArrayList<Integer> result = new ArrayList<>();
        int maxSoFar = Integer.MIN_VALUE;

        for(int i = nums.length - 1; i >= 0; i--){
            if(nums[i] > maxSoFar){
                result.add(nums[i]);
                maxSoFar = nums[i];
            }
        }

        return result;
    }

    public int[] rearrangeArray(int[] nums) {
        int n = nums.length;

        int[] ans = new int[n];

        int positive = 0;
        int negative = 1;

        for (int num : nums) {
            if (num < 0) {
                ans[negative] = num;
                negative += 2;
            } else {
                ans[positive] = num;
                positive += 2;
            }
        }

        return ans;
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0) return result;

        int top = 0, bottom = matrix.length - 1;
        int left = 0, right = matrix[0].length - 1;

        while(left <= right && top <= bottom){
            for(int col = left; col <= right; col++){
                result.add(matrix[top][col]);
            }
            top++;

            for(int row = top; row <= bottom; row++){
                result.add(matrix[row][right]);
            }
            right--;

            if(top <= bottom){
                for(int col = right; col >= left; col--){
                    result.add(matrix[bottom][col]);
                }
                bottom--;
            }

            if(left <= right){
                for(int row = bottom; row >= top; row--){
                    result.add(matrix[row][left]);
                }
                left++;
            }

        }

        return result;
    }

    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for(int i = 0; i < nums.length; i++){
            int y = target - nums[i];

            if(map.containsKey(y)){
                return new int[]{map.get(y),i};
            }

            map.put(nums[i], i);
        }

        return new int[]{-1, -1};
    }

    public int KadaneAlgorithm(int[] nums){
        int maxSum = Integer.MIN_VALUE;
        int sum = 0;

        for(int i = 0; i < nums.length; i++){
            sum += nums[i];
            maxSum = Math.max(maxSum, sum);

            if(sum < 0){
                sum = 0;
            }
        }

        return maxSum;
    }

    // TC = O(N^3) x log(no of unique triplets) -> log(n) because set insertion tc
    // SC = O(2 x no of unique triplets) for set and list.
    public List<List<Integer>> threeSumBrute(int[] nums) {
        int n = nums.length;
        Set<List<Integer>> set = new HashSet<>();

        for(int i = 0; i < n-2; i++){
            for(int j = i + 1; j < n-1; j++){
                for(int k = j + 1; k < n; k++){
                    if(nums[i] + nums[j] + nums[k] == 0){
                        List<Integer> temp = new ArrayList<>();
                        temp.add(nums[i]);
                        temp.add(nums[j]);
                        temp.add(nums[k]);

                        Collections.sort(temp);
                        set.add(temp);
                    }
                }
            }
        }

        return new ArrayList<>(set);
    }

    // TC: O(N^2 x log(no of unique triplets)) N2 for loops and o(log) for set insertion
    // SC: O(2 x no of unique triplets) for set and list + O(N) for extra set.
    public List<List<Integer>> threeSumBetter(int[] nums){
        Set<List<Integer>> set = new HashSet<>();
        int n = nums.length;

        for(int i = 0; i < n; i++){
            Set<Integer> hashset = new HashSet<>();
            for(int j = i + 1; j < n; j++){
                int third = -(nums[i] + nums[j]);
                if(hashset.contains(third)){
                    List<Integer> temp = new ArrayList<>();
                    temp.add(nums[i]);
                    temp.add(nums[j]);
                    temp.add(third);

                    Collections.sort(temp);
                    set.add(temp);
                }
                hashset.add(nums[j]);
            }
        }

        return new ArrayList<>(set);
    }

    public List<List<Integer>> threeSum(int[] nums){
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);
        int n = nums.length;

        for(int i = 0; i < n; i++){
            if(i > 0 && nums[i] == nums[i - 1]) continue;

            int first = i + 1;
            int last = n - 1;

            while(first < last){
                int sum = nums[i] + nums[first] + nums[last];
                if(sum == 0){
                    ans.add(Arrays.asList(nums[i], nums[first], nums[last]));

                    while(first < last && nums[first] == nums[first + 1]) first++;
                    while(nums[last] == nums[last - 1]) last--;
                    first++;
                    last--;
                }else if(sum > 0){
                    last--;
                }else{
                    first++;
                }
            }
        }

        return ans;
    }

//    Time Complexity: O(n⁴)
//    Space Complexity: O(1)
    public List<List<Integer>> fourSumBruteForce(int[] nums, int target){
        Set<List<Integer>> set = new HashSet<>();
        int n = nums.length;

        for(int i = 0; i < n; i++){
            for(int j = i + 1; j < n; j++){
                for(int k = j + 1; k < n; k++){
                    for(int l = k + 1; l < n; l++){
                        if(nums[i] + nums[j] + nums[k] + nums[l] == target){
                            List<Integer> temp = Arrays.asList(nums[i], nums[j], nums[k], nums[l]);

                            Collections.sort(temp);
                            set.add(temp);
                        }
                    }
                }
            }
        }

        return new ArrayList<>(set);
    }

//    Time Complexity: O(n³)
//    Space Complexity: O(n²)
    public List<List<Integer>> fourSumBetter(int[] nums, int target){
        Set<List<Integer>> set = new HashSet<>();
        int n = nums.length;

        for(int i = 0; i < n; i++){
            for(int j = i + 1; j < n; j++){
                Set<Long> hashset = new HashSet<>();
                for(int k = j + 1; k < n; k++){
                    long sum =  (long) nums[i] + nums[j] + nums[k];
                    long fourth = target - sum;

                    if(hashset.contains(fourth)){
                        List<Integer> temp = Arrays.asList(nums[i], nums[j], nums[k], (int)fourth);
                        Collections.sort(temp);

                        set.add(temp);
                    }

                    hashset.add((long)nums[k]);
                }
            }
        }

        return new ArrayList<>(set);
    }

//    Time Complexity: O(n³)
//    Space Complexity: O(1) (excluding output)
    public List<List<Integer>> FourSum(int[] nums, int target){
        List<List<Integer>> ans = new ArrayList<>();
        if (nums.length < 4) return ans;
        Arrays.sort(nums);
        int n = nums.length;

        for(int i = 0; i < n; i++){
            if(i > 0 && nums[i] == nums[i - 1]) continue;

            for(int j = i + 1; j < n; j++){
                if(j > i + 1 && nums[j] == nums[j - 1]) continue;
                int first = j + 1;
                int last = n - 1;

                while(first < last){
                    long sum = (long) nums[i] + nums[j] + nums[first] + nums[last];
                    if(sum == target){
                        ans.add(Arrays.asList(nums[i], nums[j], nums[first], nums[last]));

                        while(first < last && nums[first] == nums[first + 1]) first++;
                        while(first < last && nums[last] == nums[last - 1]) last--;
                        first++;
                        last--;
                    }else if(sum > target){
                        last--;
                    }else{
                        first++;
                    }
                }
            }
        }

        return ans;
    }
}
