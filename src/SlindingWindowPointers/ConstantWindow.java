package SlindingWindowPointers;

import java.util.Arrays;

public class ConstantWindow {
    // It is basically like take/not take pattern from recursion.
    // but here we have two choices either take left or take right.
    public int maxScoreBruteForce(int[] cardScore, int k) {
        return maxScoreHelper(cardScore, k, 0, cardScore.length - 1);
    }

    public int maxScoreHelper(int[] cardScore, int k, int left, int right) {
        if(k == 0) return 0;

        int leftpick = cardScore[left] + maxScoreHelper(cardScore, k - 1, left + 1, right);
        int rightpick = cardScore[right] + maxScoreHelper(cardScore, k - 1, left, right - 1);

        return Math.max(leftpick, rightpick);
    }

    public int maxScore(int[] cardScore, int k){
        int totalSum = Arrays.stream(cardScore).sum();
        int windowSize = cardScore.length - k;
        int currSum = 0;

        if(windowSize == 0) return 0;

        for(int i = 0; i < windowSize; i++){
            currSum += cardScore[i];
        }

        int minSum = currSum;

        for(int i = windowSize; i < cardScore.length; i++){
            currSum += cardScore[i];
            currSum -= cardScore[i - windowSize];
            minSum = Math.min(minSum, currSum);
        }

        return totalSum - minSum;
    }

    public int maxScoreOptimal1(int[] cardScore, int k){
        int n =  cardScore.length;
        if(k >= n){ // take all cards
            int total = 0;
            for(int num: cardScore) total += num;
            return total;
        }

        // compute left prefix sum
        int[] left = new int[k + 1];
        for(int i = 1; i <= k; i++){
            left[i] = left[i - 1] + cardScore[i - 1];
        }

        // compute right prefix sum
        int[] right = new int[k + 1];
        for(int i = 1; i <= k; i++){
            right[i] = right[i - 1] + cardScore[n - i];
        }

        int maxScore = 0;
        for(int leftCount = 0; leftCount <= k; leftCount++){
            int rightCount = k - leftCount;
            int current = left[leftCount] + right[rightCount];
            maxScore = Math.max(maxScore, current);
        }

        return maxScore;
    }

    public int maxScoreSlidingWindow(int[] cardScore, int k){
        int n = cardScore.length;

        if(k >= n){ // take all cards
            int total = 0;
            for(int num: cardScore) total += num;
            return total;
        }

        // Start by taking all elements from right side.
        int currentSum = 0;
        for(int i = n - k; i < n; i++){
            currentSum += cardScore[i];
        }

        int maxScore = currentSum;
        // now gradually move cards from right to left
        for(int i = 0; i < k; i++){
            currentSum -= cardScore[n - k + i];
            currentSum += cardScore[i];
            maxScore = Math.max(maxScore, currentSum);
        }

        return maxScore;
    }

}
