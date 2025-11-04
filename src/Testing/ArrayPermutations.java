package Testing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayPermutations {
    public List<List<Integer>> permutationsBacktrack(int[] nums){
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        Arrays.sort(nums);
        boolean[] used = new boolean[nums.length];

        permutationsBacktrackHelper(nums, used, current, result);
        return result;
    }

    public void permutationsBacktrackHelper(int[] nums, boolean[] used, List<Integer> current, List<List<Integer>> result){
        if(current.size() == nums.length){
            result.add(new ArrayList<>(current));
            return;
        }

        for(int i = 0; i < nums.length; i++){
            if(used[i] || (i > 0 && nums[i] == nums[i - 1] && !used[i - 1])) continue;

            current.add(nums[i]);
            used[i] = true;

            permutationsBacktrackHelper(nums, used, current, result);

            current.removeLast();
            used[i] = false;
        }
    }

    public List<List<Integer>> permutationsSwapping(int[] nums){
        List<List<Integer>> result = new ArrayList<>();
        permutationsSwappingHelper(nums, 0, result);
        return result;
    }

    public void permutationsSwappingHelper(int[] nums, int start, List<List<Integer>> result){
        if(start == nums.length){
            List<Integer> permutations = new ArrayList<>();
            for(int num: nums) permutations.add(num);
            result.add(permutations);
            return;
        }

        for(int i = start; i < nums.length; i++){
            swap(nums, start, i);
            permutationsSwappingHelper(nums, start+1, result);
            swap(nums, start, i); // backtrack.
        }
    }

    public void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
