package BinarySearch;

import java.util.Arrays;

public class TwoDBinarySearch {
    public boolean searchMatrix(int[][] matrix, int target) {
        int n = matrix.length;
        int m = matrix[0].length;

        int low = 0;
        int high = m * n - 1;

        while (low <= high) {
            int mid = low + (high - low)/2;

            int row =  mid / m;
            int col =  mid % m;

            if(matrix[row][col] == target){
                return true;
            }

            else if(matrix[row][col] < target){
                low = mid + 1;
            }else{
                high = mid - 1;
            }
        }

        return false;
    }

    public boolean searchMatrix2(int[][] matrix, int target) {
        int row = 0;
        int col = matrix[0].length - 1;

        while (row < matrix.length && col >= 0) {
            int element = matrix[row][col];

            if(element == target){
                return true;
            }
            else if(element > target){
                col--;
            }else{
                row++;
            }
        }

        return false;
    }

    public double median2DBruteForce(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        int[] flatten = new int[n * m];

        for(int i = 0; i < n; i++){
            System.arraycopy(matrix[i], 0, flatten, i * m, m);
        }

        Arrays.sort(flatten);
        int len = flatten.length;

        if(flatten.length % 2 == 0){
            return (flatten[len / 2] + flatten[len / 2 - 1]) / 2.0;
        }

        return flatten[flatten.length / 2];
    }

    private int countLessEqualInRow(int[] row, int mid) {
        int left = 0;
        int right = row.length ;

        while(left < right){
            int m = left + (right - left)/2;

            if(row[m] <= mid){
                left = m + 1;
            }else{
                right = m;
            }
        }

        return left;
    }

    public int median2D(int[][] matrix, int medianPos) {
        int rows = matrix.length;
        if (rows == 0) return -1;
        int cols = matrix[0].length;

        int low = Integer.MAX_VALUE;
        int high = Integer.MIN_VALUE;


        for (int[] row : matrix) {
            low = Math.min(low, row[0]);
            high = Math.max(high, row[cols - 1]);
        }

        while(low < high){
            int mid = low + (high - low) / 2;
            int count = 0;

            for (int[] row : matrix) {
                count += countLessEqualInRow(row, mid);
            }

            if(count >= medianPos){
                high = mid;
            }else{
                low = mid + 1;
            }
        }
        return low;
    }

    public double median2dForBothEvenOdd(int[][] matrix){
        int rows = matrix.length;
        int cols = matrix[0].length;
        int total = rows * cols;

        if(total % 2 == 1){
            int medianPos = (total + 1)/2;
            return median2D(matrix, medianPos);
        }

        int median1 = median2D(matrix, total/2);
        int median2 = median2D(matrix, (total + 1)/2);

        return (median1 + median2)/2.0;
    }

}
