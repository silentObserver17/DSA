package RevisitArrays.Hard;

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
}
