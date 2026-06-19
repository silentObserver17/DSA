package DynamicProgramming;

import java.util.Arrays;
import java.util.List;

class Question2D {
    public int NinjaTraining(int[][] matrix) {
        int days = matrix.length;

        int[][] dp = new int[days][4];
        for(int i = 0; i < days; i++) {
            Arrays.fill(dp[i], -1);
        }

        return NinjaHelper(matrix, days-1, 3, dp);
    }

    private int NinjaHelper(int[][] matrix, int day, int prevTask, int[][] dp) {
        if(day < 0) {
            return 0;
        }

        if(dp[day][prevTask] != -1) return dp[day][prevTask];

        int maxTask = Integer.MIN_VALUE;

        for(int i = 0; i < 3; i++) {
            if(i != prevTask) {
                int task = matrix[day][i] +  NinjaHelper(matrix, day-1, i, dp);
                maxTask = Math.max(maxTask, task);
            }
        }

        return dp[day][prevTask] = maxTask;
    }

    public int NinjaTrainingTabulation(int[][] matrix) {
        int days = matrix.length;

        int[][] dp = new int[days][4];
        for(int i = 0; i < 3; i++) {
            dp[0][i] = matrix[0][i];
        }
        dp[0][3] = Math.max(dp[0][0], Math.max(dp[0][1], dp[0][2]));

        for(int day = 1; day < days; day++) { // days
            for(int i = 0; i < 3; i++) { // previous selected day
                for(int j = 0; j < 3; j++) { // current day
                    if(i != j) {
                        int task = matrix[day][j] +  dp[day-1][i];
                        dp[day][j] = Math.max(dp[day][j], task);
                    }
                }
            }
            dp[day][3] = Math.max(dp[day][0], Math.max(dp[day][1], dp[day][2]));
        }

        System.out.println(Arrays.deepToString(dp));
        return dp[days - 1][3];
    }

    public int NinjaTrainingSpaceOptimization(int[][] matrix) {
        int days = matrix.length;

        int[] prev = new int[4];

        for(int i = 0; i < 3; i++) {
            prev[i] = matrix[0][i];
        }
        prev[3] = Math.max(matrix[0][0], Math.max(matrix[0][1], matrix[0][2]));

        for(int day = 1; day < days; day++) { // days
            int[] temp = new int[4];
            for(int i = 0; i < 3; i++) { // previous selected day
                for(int j = 0; j < 3; j++) { // current day
                    if(i != j) {
                        int task = matrix[day][j] +  prev[i];
                        temp[j] = Math.max(temp[j], task);
                    }
                }
            }
            temp[3] = Math.max(temp[0], Math.max(temp[1], temp[2]));

            prev = temp;
        }

        return prev[3];
    }

    public int UniquePaths(int m, int n) {
        if(m == 0 || n == 0) return 0;

        int[][] dp = new int[m][n];
        for(int i = 0; i < m; i++) {
            Arrays.fill(dp[i], -1);
        }

        return UniquePathsHelper(m-1, n-1, dp);
    }

    private int UniquePathsHelper(int row, int col, int[][] dp) {
        if(row < 0 || col < 0 ) {
            return 0;
        }

        if(row == 0 && col == 0) {
            return 1;
        }

        if(dp[row][col] != -1) return dp[row][col];

        int left = UniquePathsHelper(row, col - 1, dp);
        int up =  UniquePathsHelper(row - 1, col, dp);

        return dp[row][col] = left + up;
    }

    public int UniquePathsTabulation(int  m, int n) {
        int[][] dp = new int[m][n];

        for(int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }

        for(int i = 1; i < m; i++) {
            dp[i][0] = 1;
        }

        for(int row = 1; row < m; row++) {
            for(int col = 1;  col < n; col++) {
                int left = dp[row][col - 1];
                int up = dp[row - 1][col];

                dp[row][col] = left + up;
            }
        }

        return dp[m - 1][n - 1];
    }

    public int UniquePathsSpaceOptimization(int m, int n) {
        int[] dp = new int[n];

        for(int i = 0; i < n; i++) {
            dp[i] = 1;
        }

        for(int row = 1; row < m; row++) {
            int [] temp = new int[n];
            temp[0] = 1;
            for(int col = 1; col < n; col++) {
                int left = temp[col - 1];
                int up = dp[col];

                temp[col] = left + up;
            }

            dp = temp;
        }

        return dp[n - 1];
    }

    public int UniquePathsSpaceOptimization2(int m, int n) {
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        for(int row = 1; row < m; row++) {
            int temp = 1;
            for(int col = 1; col < n; col++) {
                int left = temp;
                int up = dp[col];

                temp = left + up;

                dp[col] = temp;
            }
        }

        return dp[n - 1];
    }

    public int MinPathSumTabulation(int[][]grid) {
        int m = grid.length;
        int n =  grid[0].length;

        int[][] dp = new int[m][n];

        for(int j = 0; j < n; j++) {
            if(j == 0) {
                dp[0][j] = grid[0][j];
                continue;
            }
            dp[0][j] = grid[0][j] + dp[0][j-1];
        }

        for(int i = 1; i < m; i++) {
            dp[i][0] = grid[i][0] + dp[i-1][0];
        }

        for(int row = 1; row < m; row++) {
            for(int col = 1;  col < n; col++) {
                int left = grid[row][col] + dp[row][col - 1];
                int up = grid[row][col] + dp[row - 1][col];

                dp[row][col] = Math.min(left, up);
            }
        }

        return dp[m - 1][n - 1];
    }

    public int MinPathSumSpaceOptimization(int[][] grid) {
        int m = grid.length;
        int n =  grid[0].length;

        int[] dp = new int[n];

        for(int j = 0; j < n; j++) {
            if(j == 0) {
                dp[j] = grid[0][j];
                continue;
            }
            dp[j] = grid[0][j] + dp[j-1];
        }

        for(int row = 1; row < m; row++) {
            dp[0] = dp[0] + grid[row][0];
            for(int col = 1;  col < n; col++) {
                int left = grid[row][col] + dp[col-1];
                int up = grid[row][col] + dp[col];

                dp[col] = Math.min(left, up);
            }
        }

        return dp[n - 1];
    }

    public int triangleSum(List<List<Integer>> triangle) {
        int n = triangle.size();

        int[][] dp = new int[n][n];
        for(int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }

        return triangleSumHelper(triangle, 0, 0, dp);
    }

    private int triangleSumHelper(List<List<Integer>> triangle, int row, int col, int[][] dp) {
        if(row == triangle.size() - 1) return triangle.getLast().get(col);

        if(dp[row][col] != -1) return dp[row][col];

        int down =  triangle.get(row).get(col) + triangleSumHelper(triangle, row + 1, col, dp);
        int downRight = triangle.get(row).get(col) + triangleSumHelper(triangle, row + 1, col + 1, dp);

        return dp[row][col] = Math.min(down, downRight);
    }

    public int triangleSumTabulation(List<List<Integer>> triangle) {
        int n = triangle.size();

        int[][] dp = new int[n][n];

        for(int i = 0; i < n; i++) {
            dp[n-1][i] =  triangle.get(n-1).get(i);
        }

        for(int i = n-2; i >= 0; i--) {
            for(int j = 0; j <= i; j++) {
                int down = triangle.get(i).get(j) + dp[i+1][j];
                int downRight = triangle.get(i).get(j) + dp[i+1][j+1];

                dp[i][j] = Math.min(down, downRight);
            }
        }

        return dp[0][0];
    }

    public int triangleSumSpaceOptimization(List<List<Integer>> triangle) {
        int n = triangle.size();

        int[] dp = new int[n];

        for(int i = 0; i < n; i++) {
            dp[i] =  triangle.get(n-1).get(i);
        }

        for(int i = n-2; i >= 0; i--) {
            for(int j = 0; j <= i; j++) {
                int down = triangle.get(i).get(j) + dp[j];
                int downRight = triangle.get(i).get(j) + dp[j+1];

                dp[j] = Math.min(down, downRight);
            }
        }

        return dp[0];
    }
}

public class TwoDDP {
    public static void main(String[] args) {
        Question2D q = new Question2D();
        System.out.println(q.NinjaTraining(new int[][]{
                {70, 40, 10},
                {180, 20, 5},
                {200, 60, 30},
        }));

        System.out.println(q.NinjaTrainingTabulation(new int[][]{
                {70, 40, 10},
                {180, 20, 5},
                {200, 60, 30},
        }));

        System.out.println(q.NinjaTrainingSpaceOptimization(new int[][]{
                {70, 40, 10},
                {180, 20, 5},
                {200, 60, 30},
        }));

        System.out.println(q.UniquePaths(3, 7));
        System.out.println(q.UniquePathsTabulation(3, 7));
        System.out.println(q.UniquePathsSpaceOptimization(3, 7));
        System.out.println(q.UniquePathsSpaceOptimization2(3, 7));

        System.out.println(q.MinPathSumTabulation(new int[][] {
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}
        }));

        System.out.println(q.MinPathSumSpaceOptimization(new int[][] {
                {1,2},
                {1,1}
        }));

        List<List<Integer>> triangle = Arrays.asList(
                Arrays.asList(2),
                Arrays.asList(3, 4),
                Arrays.asList(6, 5, 7),
                Arrays.asList(4, 1, 8, 3)
        );
        System.out.println(q.triangleSum(triangle));

        System.out.println(q.triangleSumTabulation(triangle));
        System.out.println(q.triangleSumSpaceOptimization(triangle));
    }
}
