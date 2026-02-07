package Recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HardRecursion {
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if(digits.isEmpty()) {
            return result;
        }

        String[] mapping = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

        letterBacktracking(0, new StringBuilder(), result, digits, mapping);
        return result;
    }

    private void letterBacktracking(int index, StringBuilder current, List<String> result, String digits, String[] mapping) {
        if(index == digits.length()){
            result.add(current.toString());
            return;
        }

        char digit =  digits.charAt(index);
        String letter = mapping[digit - '0'];

        for(char c : letter.toCharArray()){
            current.append(c);
            letterBacktracking(index+1, current, result, digits, mapping);
            current.deleteCharAt(current.length()-1);
        }
    }

    public List<List<String>> palindromePartitioning(String s){
        List<List<String>> result = new ArrayList<>();

        palindromeParitioningHelper(0, new ArrayList<>(), result, s);
        return result;
    }

    private void palindromeParitioningHelper(int start, List<String> current, List<List<String>> result, String s){
        if(start == s.length()){
            result.add(new ArrayList<>(current));
            return;
        }

        for(int end = start; end < s.length(); end++){
            if(isPalindrome(s, start, end)){
                current.add(s.substring(start, end+1));
                palindromeParitioningHelper(end+1, current, result, s);
                current.remove(current.size()-1);
            }
        }
    }

    private boolean isPalindrome(String s, int left, int right){
        while(left < right){
            if(s.charAt(left) != s.charAt(right)){
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    public List<List<String>> nQueensIntuitive(int n){
        List<List<String>> ans = new ArrayList<>();
        // Initialize the board with empty cells
        List<String> board = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            board.add(".".repeat(n));
        }

        queensIntuitiveBacktrack(0, ans, board, n);
        return ans;
    }

    private void queensIntuitiveBacktrack(int row, List<List<String>> result, List<String> board, int n){
        if(row == board.size()){
            result.add(new ArrayList<>(board));
            return;
        }

        for(int col = 0; col < n; col++){
            if(isSafe(row, col, board, n)){
                char[] rowArr = board.get(row).toCharArray();
                rowArr[col] = 'Q';
                board.set(row, new String(rowArr));

                queensIntuitiveBacktrack(row+1, result, board, n);

                rowArr[col] = '.';
                board.set(row, new String(rowArr));
            }
        }
    }

    private boolean isSafe(int row, int col, List<String> board, int n){
        int r = row;
        int c = col;

        // check the upper left diagonal
        while(r >= 0 && c >= 0){
            if(board.get(r).charAt(c) == 'Q'){
                return false;
            }
            r--;
            c--;
        }

        r = row;
        c = col;

        // check for top
        while(r >= 0){
            if(board.get(r).charAt(c) == 'Q') return false;
            r--;
        }

        r = row;
        c = col;

        // check for top right diagonal
        while(r >= 0 && c < n){
            if(board.get(r).charAt(c) == 'Q') return false;
            r--;
            c++;
        }

        return true;
    }

    public List<List<String>> solveNQueens(int n){
        boolean[] colUsed = new boolean[n];
        boolean[] leftDiag = new boolean[2 * n - 1]; //  row - col + (n - 1)
        boolean[] rightDiag = new boolean[2 * n - 1]; // row + col

        List<List<String>> result = new ArrayList<>();
        char[][] board = new char[n][n];

        // initialize board with '.'
        for(int i = 0; i < n; i++){
            Arrays.fill(board[i], '.');
        }

        nQueensBacktrack(0, n, colUsed, leftDiag, rightDiag, board, result);
        return result;
    }

    private void nQueensBacktrack(int row, int n, boolean[] colUsed, boolean[] leftDiag, boolean[] rightDiag, char[][] board, List<List<String>> result){
        if(row == n){
            result.add(constructBoard(board));
            return;
        }

        for(int col = 0; col < n; col++){
            int ld = row - col + (n - 1);
            int rd = row + col;

            if(!colUsed[col] && !leftDiag[ld] && !rightDiag[rd]){
                board[row][col] = 'Q';
                colUsed[col]    = true;
                leftDiag[ld]    = true;
                rightDiag[rd]   = true;

                nQueensBacktrack(row + 1,  n, colUsed, leftDiag, rightDiag, board, result);

                board[row][col] = '.';
                colUsed[col]    = false;
                leftDiag[ld]    = false;
                rightDiag[rd]   = false;
            }
        }
    }

    private List<String> constructBoard(char[][] board){
        List<String> current = new  ArrayList<>();
        for(int i = 0; i < board.length; i++){
            current.add(String.valueOf(board[i]));
        }
        return current;
    }

    public List<String> RatInMaze(int[][] grid){
        List<String> result = new ArrayList<>();
        if (grid[0][0] == 0 || grid[grid.length - 1][grid[0].length - 1] == 0) {
            return result;
        }

        boolean[][] vis = new boolean[grid.length][grid[0].length];
        vis[0][0] = true;

        StringBuilder path = new StringBuilder();

        findPathsUpdated(0, 0, path, result, vis, grid);
        return result;
    }

    private void findPaths(int row, int col, StringBuilder path, List<String> result, boolean[][] vis, int[][] grid){
        if(row == grid.length-1 && col == grid[0].length-1){
            result.add(path.toString());
            return;
        }
        vis[row][col] = true;

        if(isPathSafe(row + 1, col, vis, grid)){
            path.append("D");
            findPaths(row + 1, col, path, result, vis, grid);
            path.deleteCharAt(path.length() - 1);
        }

        if(isPathSafe(row - 1, col, vis, grid)){
            path.append("U");
            findPaths(row - 1, col, path, result, vis, grid);
            path.deleteCharAt(path.length() - 1);
        }

        if(isPathSafe(row, col + 1, vis, grid)){
            path.append("R");
            findPaths(row, col + 1, path, result, vis, grid);
            path.deleteCharAt(path.length() - 1);
        }

        if(isPathSafe(row, col - 1, vis, grid)){
            path.append("L");
            findPaths(row, col - 1, path, result, vis, grid);
            path.deleteCharAt(path.length() - 1);
        }

        vis[row][col] = false;
    }

    private void findPathsUpdated(int row, int col, StringBuilder path, List<String> result, boolean[][] vis, int[][] grid){
        vis[row][col] = true;

        if(row == grid.length-1 && col == grid[0].length-1){
            result.add(path.toString());
            return;
        }

        int[] delRow = new int[]{-1, 0, 1, 0};
        int[] delCol = new int[]{0, 1, 0, -1};
        char[] dir = new char[]{'U', 'R', 'D', 'L'};

        for(int i = 0; i < 4; i++){
            int r1 = row + delRow[i];
            int c1 = col + delCol[i];

            if(isPathSafe(r1, c1, vis, grid)){
                path.append(dir[i]);
                findPathsUpdated(r1, c1, path, result, vis, grid);
                path.deleteCharAt(path.length() - 1);
            }
        }

        vis[row][col] = false;
    }

    private boolean isPathSafe(int row, int col, boolean[][] vis, int[][] grid){
        if(row >= 0 && row < grid.length && col >= 0 && col < grid[0].length && !vis[row][col] && grid[row][col] == 1){
            return true;
        }
        return false;
    }


    public void solveSudoku(char[][] board) {
        backtrackSudoku(0, 0, board);

        System.out.println(Arrays.deepToString(board));
    }

    private boolean backtrackSudoku(int row, int col, char[][] board){
        int n = 9;

        if(row == n){
            return true;
        }

        if(col == n){
            return backtrackSudoku(row + 1, 0, board);
        }

        if(board[row][col] != '.'){
            return backtrackSudoku(row, col + 1, board);
        }

        for(char num = '1'; num <= '9'; num++){
            if(validSudoku(row,col,num,board)){
                board[row][col] = num;

                if(backtrackSudoku(row, col + 1, board)){
                    return true;
                }
                // backtrack
                board[row][col] = '.';
            }
        }

        return false;
    }

    private boolean validSudoku(int row, int col, char num, char[][] board){
        for(int c = 0; c < 9; c++){
            if(board[row][c] == num){
                return false;
            }
        }

        for(int r = 0; r < 9; r++){
            if(board[r][col] == num){
                return false;
            }
        }

        int boxRow = (row/3)*3;
        int boxCol = (col/3)*3;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(board[boxRow+i][boxCol+j] == num){
                    return false;
                }
            }
        }

        return true;
    }

    public void solveSudokuOptimal(char[][] board) {
        boolean[][] rowUsed   = new boolean[9][10];  // rowUsed[r][num] = true if num used in row r
        boolean[][] colUsed   = new boolean[9][10];
        boolean[][] boxUsed   = new boolean[9][10];  // boxUsed[b][num] = true if num used in box b

        // Initialize used arrays based on given board
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] != '.') {
                    int num = board[r][c] - '0';
                    rowUsed[r][num] = true;
                    colUsed[c][num] = true;
                    int boxIdx = (r / 3) * 3 + (c / 3);
                    boxUsed[boxIdx][num] = true;
                }
            }
        }

        backtrackSudokuOptimal(0,0,board,rowUsed,colUsed,boxUsed);

        System.out.println(Arrays.deepToString(board));
    }

    private boolean backtrackSudokuOptimal(int row, int col, char[][] board, boolean[][] rowUsed, boolean[][] colUsed, boolean[][] boxUsed){
        if(row == 9){
            return true;
        }

        int nextRow = row;
        int nextCol = col + 1;
        if (nextCol == 9) {
            nextRow++;
            nextCol = 0;
        }

        if(board[row][col] != '.'){
            return backtrackSudokuOptimal(nextRow, nextCol, board, rowUsed, colUsed, boxUsed);
        }

        for(int num = 1; num <= 9; num++){
            int boxIdx = (row/3)*3 + (col/3);

            if(!rowUsed[row][num] && !colUsed[col][num] && !boxUsed[boxIdx][num]){
                board[row][col] = (char)('0' + num);
                rowUsed[row][num] = true;
                colUsed[col][num] = true;
                boxUsed[boxIdx][num] = true;

                if(backtrackSudokuOptimal(nextRow, nextCol, board, rowUsed, colUsed, boxUsed)){
                    return true;
                }

                board[row][col] = '.';
                rowUsed[row][num] = false;
                colUsed[col][num] = false;
                boxUsed[boxIdx][num] = false;
            }
        }

        return false;
    }
}
