import BinarySearch.Answers;
import BinarySearch.LBBinarySearch;
import BinarySearch.TwoDBinarySearch;
import Greedy.HardGreedy;
import Greedy.MediumGreedy;
import Hashing.FAQ.FaqHashing;
import LinkedList.BasicLL;
import Recursion.BasicRecursion;
import Recursion.HardRecursion;
import Recursion.MediumRecursion;
import RevisitArrays.Fundamentals.ArraysFundamentals;
import RevisitArrays.Hard.ArraysHard;
import RevisitArrays.LogicBuilding.ArraysLogic;
import RevisitArrays.Medium.ArraysMedium;
import Sorting.MergeSort;
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

        int[] invenrsions = new int[]{5,4,3,2,1};
        System.out.println(ah.countInversionsBrute(invenrsions));
        System.out.println(ah.countInversionsOptimal(invenrsions));

        int[] maxProduct = new int[]{2, 3, -2, 4, -1};
        System.out.println(ah.maxProduct(maxProduct));

        int[] reversePairs = new int[]{6, 4, 1, 2, 7};
        System.out.println(ah.ReversePairs(reversePairs));

        int[] nums1 = {-5, -2, 4, 5, 0, 0, 0};
        int[] nums2 = {-3, 1, 8};

        ah.MergeWithoutExtraSpace1(nums1, nums2, 4, 3);

        // ================== SORTING ========================
//        MergeSort ms = new MergeSort();
//        int[] mergeSortArr = new int[]{5,4,3,2,1};
//        ms.MergeSort(mergeSortArr, 0, mergeSortArr.length - 1);

        // ===================== HASHING ========================
        System.out.println("==================== HASHING =====================");
        FaqHashing fh = new  FaqHashing();
        int[] longestConsec = new int[]{0,3,7,2,5,8,4,6,0,1};
//        System.out.println(fh.longestConsecutiveBruteForce(longestConsec));
//        System.out.println(fh.longestConsecutiveBetter(longestConsec));
        System.out.println(fh.longestConsecutiveOptimal(longestConsec));

        String[] groupAnagrams = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println(fh.GroupAnagramsBruteForce(groupAnagrams));
        System.out.println(fh.GroupAnagramsBetter(groupAnagrams));
        System.out.println(fh.GroupAnagrams(groupAnagrams));

        System.out.println(fh.lengthOfLongestSubstringBruteForce("abba"));
        System.out.println(fh.lengthOfLongestSubstringOptimal("abba"));

        int[] subarrSum = new int[]{1,1,1};
        System.out.println(fh.subarraySumBruteForce(subarrSum, 2));
        System.out.println(fh.subarraySumOptimal(subarrSum, 2));

        System.out.println(fh.MaximumWindowSubstring("ADOBECODEBANC", "ABC"));
        System.out.println(fh.MaximumWindowSubstringOptimal("ADOBECODEBANC", "ABC"));
        System.out.println(fh.MaximumWindowSubstringOptimal2("aaab", "aab"));


        // ================== BINARY SEARCH =========================
        System.out.println("================ BINARY SEARCH ===================");
        LBBinarySearch lbb = new LBBinarySearch();
        int[] firstlast = new int[]{5,7,7,8,8,10};
        System.out.println(Arrays.toString(lbb.firstLastOccurrence(firstlast, 8)));

        int[] minSortedRotated = new int[]{4,5,6,7,0,1,2};
        System.out.println(lbb.minimumInRotatedArray(minSortedRotated));

        int[] minSortedDuplicates = new int[]{2, 0, 1, 2, 2, 2, 2};
        System.out.println(lbb.minimumInRotatedArrayDuplicates(minSortedDuplicates));

        System.out.println(lbb.searchInRotatedArray(minSortedRotated, 1));
        System.out.println(lbb.searchInRotatedArrayDuplicates(new int[]{3,1}, 1));

        int[] timesArrayRotated = new int[]{4,5,6,7,0,1,2,3};
        System.out.println(lbb.NoOfTimesArrayRotated(timesArrayRotated));

        System.out.println("---------------------- BS ON ANSWERS ----------------");
        Answers ans = new Answers();
        System.out.println(ans.nthRoot(3,27));

        int[] koko = new int[]{3,6,7,11};
        System.out.println(ans.KokoEatingBananas(koko, 8));

        int[]aggressiveCows = new int[]{1,2,4,8,9};
        System.out.println(ans.AggressiveCows(aggressiveCows, 3));

        int[] bookAllocation = new int[]{10, 20, 30, 40};
        System.out.println(ans.BookAllocation(bookAllocation, 2));

        int[] bloomDay = new int[]{1, 10, 3, 10, 2};
        System.out.println(ans.MakeBouquets(bloomDay, 3, 2));

        int[] gasStations = new int[]{1, 13, 17, 23};
        System.out.println(ans.gasStationsOptimal(gasStations, 5));

        System.out.println("================= 2D BINARY SEARCH ================");
        TwoDBinarySearch tdb = new TwoDBinarySearch();

        int[][] searchBinaryMatrix = new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12}};
        System.out.println(tdb.searchMatrix(searchBinaryMatrix, 12));

        int[][] searchMatrix2 = new int[][]{{1,  4,  7, 11, 15}, {2,  5,  8, 12, 19}, {3,  6,  9, 16, 22},{10,13,14,17,24},{18,21,23,26,30}};
        System.out.println(tdb.searchMatrix2(searchMatrix2, 12));

        int[][] median2d = new int[][]{{1, 3, 5}, {2,6,9}, {3,6,9}};
        System.out.println(tdb.median2DBruteForce(median2d));
        System.out.println(tdb.median2dForBothEvenOdd(median2d));

        System.out.println("================= RECURSION ================");
        BasicRecursion br = new  BasicRecursion();
        System.out.println(br.PowerN(5,4));
        System.out.println(br.PowerNOptimal(3,5));

        System.out.println(br.SumOfDigitsBruteForce(1234));
        System.out.println(br.SumOfDigitsRecursion(-123));

        System.out.println(br.ReverseString("hello"));
        System.out.println(br.ReverseStringSecond("hello"));

        System.out.println(br.generateParenthesis(3));

        System.out.println(br.powerSet(new int[]{1,2,3}));

        System.out.println(br.subsequenceSumK(new int[]{1,2,3,4,5}, 8));

        System.out.println(br.subsequenceSumKCount(new int[]{1,2,3,4,5}, 8));

        MediumRecursion mr = new  MediumRecursion();
        System.out.println(mr.CombinationSum(new int[]{2,3,5}, 8));

        System.out.println(mr.CombinationSum2(new int[]{2, 1, 2, 7, 6, 1, 5}, 8));

        System.out.println(mr.Subset1BruteForce(new int[]{2,3}));
        System.out.println(mr.Subset1(new int[]{2,3}));
        System.out.println(mr.Subset2(new int[]{1, 2, 2}));

        System.out.println(mr.combinationSum3(3, 8));

        System.out.println(mr.permutation1(new int[]{1,2,3}));

        System.out.println(mr.permutation2(new int[]{1,1,2}));

        HardRecursion hr = new  HardRecursion();
        System.out.println(hr.letterCombinations("7"));

        System.out.println(hr.palindromePartitioning("aab"));

        System.out.println(hr.nQueensIntuitive(4));
        System.out.println(hr.solveNQueens(4));

        int[][] ratMaze = new int[][]{{1, 0, 0, 0}, {1, 1, 0, 1}, {1, 1, 0, 0}, {0, 1, 1, 1}};
        System.out.println(hr.RatInMaze(ratMaze));

        char[][] sudokuBoard = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };

        hr.solveSudokuOptimal(sudokuBoard);
//        hr.solveSudoku(sudokuBoard);

        System.out.println("================ LINKED LIST ========================");
        BasicLL bll = new  BasicLL();
        bll.ArrayToLinkedList(new int[]{1,2,3,4,5});

        System.out.println("================ GREEDY ALGORITHMS ========================");
        MediumGreedy mg = new MediumGreedy();
        System.out.println(mg.shortestJobV1(new int[]{1, 2, 3, 4}));

        int[][] jobsGreedy = new int[][]{{1, 4, 20}, {2, 1, 10}, {3, 1, 40}, {4, 1, 30}};
        System.out.println(Arrays.toString(mg.JobSequencing(jobsGreedy)));

        int[] meetingStart = new int[]{10,12,20};
        int[] meetingEnd = new int[]{20,25,30};
        System.out.println(mg.maxMeetings(meetingStart, meetingEnd));

        int[][] nonOverlap = new int[][]{{1,3},{1,4},{3,5},{3,4},{4,5}};
        System.out.println(mg.MaximumNonOverlappingIntervals(nonOverlap));

        int[][] Interval = new int[][]{{1,3},{6,9}};
        int[] newInterval = new int[]{2,5};
        System.out.println(Arrays.deepToString(mg.insertNewInterval(Interval, newInterval)));

        int[] arrival = new int[]{900, 940, 950, 1100, 1500, 1800};
        int[] departure = new int[]{910, 1200, 1120, 1130, 1900, 2000};
        System.out.println(mg.findPlatformBrute(arrival, departure));
        System.out.println(mg.findPlatform(arrival, departure));

        HardGreedy hg = new  HardGreedy();
        System.out.println(hg.isValidBrute("(*("));
        System.out.println(hg.isValid("((*))"));
        System.out.println(hg.isValidTwoPasses("((*))"));

        System.out.println(mg.canJump2(new int[]{2,3,0,1,4}));

        int[] gas = new int[]{1,2,3,4,5};
        int[] cost = new int[]{3,4,5,1,2};
        System.out.println(hg.canCompleteCycle(gas, cost));

        System.out.println(mg.largestNumber(new int[]{10,2}));
    }

}