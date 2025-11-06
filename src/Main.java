import RevisitArrays.Fundamentals.ArraysFundamentals;
import RevisitArrays.Hard.ArraysHard;
import RevisitArrays.LogicBuilding.ArraysLogic;
import RevisitArrays.Medium.ArraysMedium;
import Testing.ArrayPermutations;

import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // ================== FUNDAMENTALS ========================
        ArraysFundamentals af = new ArraysFundamentals();

        int[] linear = new int[]{2, 3, 4, 5, 3};
        System.out.println(af.linearSearch(linear, 3));

        int[] largestElement = new int[]{3, 3, 0, 99, -40};
        System.out.println(af.largestElement(largestElement));

        int[] secondLargest = new int[]{8, 8, 7, 6, 5};
        System.out.println(af.secondLargestElement(secondLargest));

        int[] maxConsecutiveOnes = new int[]{1, 0, 1, 1, 1, 0, 1, 1, 1};
        System.out.println(af.findMaxConsecutiveOnes(maxConsecutiveOnes));

        int[] rotateOne = new int[]{1, 2, 3, 4, 5};
        af.rotateArrayByOne(rotateOne);

        int[] leftRotate = new int[]{3, 4, 1, 5, 3, -5};
        af.rotateArray(leftRotate, 8);

//        ============================ ARRAYS LOGIC BUILDING ====================
        ArraysLogic al = new ArraysLogic();
        int[] moveZeros = new int[]{0, 20, 0, -20, 0, 20};
        al.moveZeroes(moveZeros);

        int[] removeDuplicates = new int[]{0, 0, 3, 3, 5, 6};
        System.out.println(al.removeDuplicates(removeDuplicates));

        int[] missing = new int[]{0, 2, 3, 1, 4};
        System.out.println(al.missingNumber(missing));

        int[] unionArr1 = new int[]{1,1,2,4,4,6,6,6,7,11,11,12,15,15,16,17,18,21,23,24,24,24,26,30,31,32,34,36,39,42,44,44,45,46,46,48,50};
        int[] unionArr2 = new int[]{1,4,4,5,7,7,8,9,9,9,12,13,15,16,21,21,25,27,27,28,30,30,32,33,33,34,35,38,38,38,39,39,39,40,41,42,42,42,43,43,44,45,46,46,47,47,47,49,50};

        System.out.println(Arrays.toString(al.unionArray(unionArr1, unionArr2)));
        System.out.println(Arrays.toString(al.intersectionArray(unionArr1, unionArr2)));

//        ============================ ARRAYS MEDIUM ====================
        ArraysMedium am = new ArraysMedium();

        int[] leader = new int[]{1, 2, 5, 3, 1, 2};
        System.out.println(am.leaders(leader));

        int[] rearrangeArray = new int[]{2, 4, 5, -1, -3, -4};
        System.out.println(Arrays.toString(am.rearrangeArray(rearrangeArray)));

        int[][] spiral = new int[][]{
                {1,2,3},
                {4,5,6},
                {7,8,9}
        };

        System.out.println(am.spiralOrder(spiral));

        int[] twoSum = new int[]{1, 6, 2, 10, 3};
        System.out.println(Arrays.toString(am.twoSum(twoSum, 7)));

        int[] kadane = new int[]{2, 3, 5, -2, 7, -4};
        System.out.println(am.KadaneAlgorithm(kadane));

        int[] three = new int[]{0, -1, 1, 0};
        System.out.println(am.threeSumBrute(three));
        System.out.println(am.threeSumBetter(three));
        System.out.println(am.threeSum(three));

        int[] four = new int[]{1,0,-1,0,-2,2};
        System.out.println(am.fourSumBruteForce(four, 0));
        System.out.println(am.fourSumBetter(four, 0));
        System.out.println(am.FourSum(four, 0));

        int[] nextPermutationArr = new int[]{1,2,3};
        // System.out.println(Arrays.toString(am.nextPermutation(nextPermutationArr)));
        am.nextPermutationOptimized(nextPermutationArr);

        int[] majority = new int[]{2, 2, 1, 1, 1, 2, 2};
        System.out.println(am.majorityElement(majority));
        System.out.println(am.majorityElementOptimized(majority));

        int[][] rotate = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
        am.rotateMatrix(rotate);
        am.rotateMatrixInPlace(rotate);
        // ================== ARRAY PERMUTATIONS ========================
//        ArrayPermutations ap = new ArrayPermutations();
//        int[] permutationA = new int[]{1,2,3};
//        System.out.println(ap.permutationsBacktrack(permutationA));
//
//        System.out.println(ap.permutationsSwapping(permutationA));


        // ====================== ARRAY HARD ===============================
        ArraysHard ah = new ArraysHard();
        int[] majority2 = new int[]{1, 2, 1, 1, 3, 2, 2};
        System.out.println(ah.majorityElementTwo(majority2));
        System.out.println(ah.majorityElementTwoOptimization(majority2));

        int[] duplicateMissing = new int[]{1, 2, 3, 6, 7, 5, 7};
        System.out.println(Arrays.toString(ah.findMissingRepeatingNumbers(duplicateMissing)));
        System.out.println(Arrays.toString(ah.findMissingRepeatingNumbersOptimized(duplicateMissing)));
    }

}