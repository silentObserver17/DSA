package Recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MediumRecursion {
    public List<List<Integer>> CombinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        combinationSumHelper(candidates, target, new ArrayList<>(), result, 0);

        return result;
    }

    private void combinationSumHelper(int[] candidates, int target, List<Integer> list, List<List<Integer>> result, int index){
        if(target == 0){
            result.add(new ArrayList<>(list));
            return;
        }

        if(target < 0){
            return;
        }

        for(int i = index; i < candidates.length; i++){
            if(candidates[i] > target){
                break;
            }
            list.add(candidates[i]);
            combinationSumHelper(candidates, target - candidates[i], list, result, i);
            list.remove(list.size()-1);
        }
    }

    public List<List<Integer>> CombinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        combinationSum2Helper(candidates, target, new ArrayList<>(), result, 0);

        return result;
    }

    private void combinationSum2Helper(int[] candidates, int target, List<Integer> list, List<List<Integer>> result, int index){
        if(target == 0){
            result.add(new ArrayList<>(list));
            return;
        }

        if(target < 0){
            return;
        }

        for(int i = index; i < candidates.length; i++){
            if(i > index && candidates[i] == candidates[i-1]){
                continue;
            }
            if(candidates[i] > target){
                break;
            }

            list.add(candidates[i]);
            combinationSum2Helper(candidates, target - candidates[i], list, result, i+1);
            list.remove(list.size()-1);
        }
    }

    public List<Integer> Subset1BruteForce(int[] arr){
        List<List<Integer>> result = new ArrayList<>();
        Subset1BruteForceHelper(0, new ArrayList<>(), result, arr);

        List<Integer> ans = new ArrayList<>();
        for(List<Integer> list : result){
            int sum = 0;
            for (Integer integer : list){
                sum += integer;
            }
            ans.add(sum);
        }

        return ans;
    }

    private void Subset1BruteForceHelper(int startIndex, List<Integer> current, List<List<Integer>> result, int[] nums){
        result.add(new ArrayList<>(current));

        for(int i = startIndex; i < nums.length; i++){
            current.add(nums[i]);
            Subset1BruteForceHelper(i + 1,  current, result, nums);
            current.removeLast();
        }
    }

    public List<Integer> Subset1(int[] nums){
        List<Integer> result = new ArrayList<>();

        subset1Helper(0, 0, result, nums);

        return result;
    }

    private void subset1Helper(int startIndex, int currentSum, List<Integer> result, int[] nums){
        result.add(currentSum);

        for(int i = startIndex; i < nums.length; i++){
            currentSum += nums[i];
            subset1Helper(i + 1, currentSum, result, nums);
            currentSum -= nums[i];
        }
    }

    public List<List<Integer>> Subset2(int[] nums){
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        subset2Helper(0, new ArrayList<>(), result, nums);

        return result;
    }

    private void subset2Helper(int startIndex, List<Integer> current, List<List<Integer>> result, int[] nums){
        result.add(new ArrayList<>(current));

        for(int i = startIndex; i < nums.length; i++){
            if(i > startIndex && nums[i] == nums[i-1]){
                continue;
            }
            current.add(nums[i]);
            subset2Helper(i + 1, current, result, nums);
            current.remove(current.size()-1);
        }
    }

    public List<List<Integer>> combinationSum3(int k, int target){
        List<List<Integer>> result = new ArrayList<>();

        combinationSum3Helper(1, target, k, new  ArrayList<>(), result);
        return result;
    }

    private void combinationSum3Helper(int startIndex, int target, int k, List<Integer> current, List<List<Integer>> result){
        if(k == 0){
            if(target == 0){
                result.add(new ArrayList<>(current));
            }
            return;
        }

        if(target < 0 || startIndex > 9){
            return;
        }

        for(int i = startIndex; i <= 9; i++){
            current.add(i);
            combinationSum3Helper(i + 1, target - i, k - 1, current, result);
            current.remove(current.size()-1);
        }
    }

    public List<List<Integer>> permutation1(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        boolean[] used = new boolean[nums.length];

        permutation1Helper(new  ArrayList<>(), nums, used, result);

        return result;
    }

    private void permutation1Helper(List<Integer> current, int[] nums, boolean[] used, List<List<Integer>> result){
        if(current.size() == nums.length){
            result.add(new ArrayList<>(current));
            return;
        }

        for(int i = 0 ; i < nums.length; i++){
            if(!used[i]){
                current.add(nums[i]);
                used[i] = true;

                permutation1Helper(current, nums, used, result);

                current.remove(current.size()-1);
                used[i] = false;
            }
        }
    }

    public List<List<Integer>> permutation2(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        boolean[] used = new  boolean[nums.length];
        permutation2Helper(new ArrayList<>(), nums, result, used);

        return result;
    }

    private void permutation2Helper(List<Integer> current, int[] nums, List<List<Integer>> result, boolean[] used){
        if(current.size() == nums.length){
            result.add(new ArrayList<>(current));
            return;
        }

        for(int i = 0; i < nums.length; i++){
            if(!used[i]){
                if(i > 0 && nums[i] == nums[i-1] && !used[i - 1]) continue;

                current.add(nums[i]);
                used[i] = true;
                permutation2Helper(current, nums, result, used);
                current.remove(current.size()-1);
                used[i] = false;
            }
        }
    }

}


