package DynamicProgramming;

import java.util.Arrays;

class DPStocksQuestions{
    public int maxProfit(int[] prices) {
        int[][] dp = new int[prices.length][2];
        for(int i = 0; i < prices.length; i++){
            Arrays.fill(dp[i],-1);
        }

        return maxProfitHelper(0, 1, prices, dp);
    }

    private int maxProfitHelper(int index, int buy, int[] prices, int[][] dp) {
        if(index == prices.length){
            return 0;
        }

        if(dp[index][buy] != - 1) return dp[index][buy];

        if(buy == 1) {
            int buyToday = -prices[index] + maxProfitHelper(index+1, 0, prices, dp);
            int skip = maxProfitHelper(index+1, buy, prices, dp);

            return dp[index][buy] = Math.max(buyToday, skip);
        }else {
            int sellToday = prices[index];
            int skip = maxProfitHelper(index+1, buy, prices, dp);

            return dp[index][buy] = Math.max(sellToday, skip);
        }
    }

    public int maxProfitTabulation(int[] prices) {
        int n = prices.length;

        int[][] dp = new int[n+1][2];

        for(int i = n - 1; i >= 0; i--){
            for(int buy = 0; buy < 2; buy++) {
                if(buy == 1) {
                    int buyToday = -prices[i] + dp[i + 1][0];
                    int skip = dp[i + 1][buy];

                    dp[i][buy] = Math.max(buyToday, skip);
                }else{
                    int sellToday = prices[i];
                    int skip = dp[i + 1][buy];

                    dp[i][buy] = Math.max(sellToday, skip);
                }
            }
        }

        return dp[0][1];
    }

    public int maxProfitSpaceOptimization(int[] prices) {
        int n = prices.length;

        int[] next = new int[2];

        for(int i = n - 1; i >= 0; i--){
            int[] curr = new int[2];
            for(int buy = 0; buy < 2; buy++) {
                if(buy == 1) {
                    int buyToday = -prices[i] + next[0];
                    int skip = next[buy];

                    curr[buy] = Math.max(buyToday, skip);
                }else{
                    int sellToday = prices[i];
                    int skip = next[buy];

                    curr[buy] = Math.max(sellToday, skip);
                }
            }
            next = curr;
        }

        return next[1];
    }

    public int maxProfit2(int[] prices) {
        int[][] dp = new int[prices.length][2];
        for(int i = 0; i < prices.length; i++){
            Arrays.fill(dp[i],-1);
        }

        return maxProfitHelper2(0, 1, prices, dp);
    }

    private int maxProfitHelper2(int index, int buy, int[] prices, int[][] dp) {
        if(index == prices.length){
            return 0;
        }

        if(dp[index][buy] != -1) return dp[index][buy];

        if(buy == 1) {
            int buyToday = -prices[index] + maxProfitHelper2(index+1, 0, prices, dp);
            int skip =  maxProfitHelper2(index+1, buy, prices, dp);

            return dp[index][buy] = Math.max(buyToday, skip);
        }else{
            int sellToday = prices[index] + maxProfitHelper2(index+1, 1, prices, dp);
            int skip = maxProfitHelper2(index + 1, buy, prices, dp);

            return dp[index][buy] = Math.max(sellToday, skip);
        }
    }

    public int maxProfit2Tabulation(int[] prices) {
        int n = prices.length;

        int[][] dp = new int[n+1][2];

        for(int index = n - 1; index >= 0; index--) {
            for(int buy = 0; buy <= 1; buy++) {
                if(buy == 1) {
                    int buyToday = -prices[index] + dp[index + 1][0];
                    int skip = dp[index + 1][buy];

                    dp[index][buy] = Math.max(buyToday, skip);
                }else{
                    int sellToday = prices[index] + dp[index + 1][1];
                    int skip = dp[index + 1][buy];

                    dp[index][buy] = Math.max(sellToday, skip);
                }
            }
        }

        return dp[0][1];
    }

    public int maxProfit2SpaceOptimization(int[] prices) {
        int n = prices.length;

        int[] next = new int[2];

        for(int index = n - 1; index >= 0; index--){
            int[] curr = new int[2];
            for(int buy = 0; buy <= 1; buy++) {
                if(buy == 1) {
                    int buyToday = -prices[index] + next[0];
                    int skip = next[buy];

                    curr[buy] = Math.max(buyToday, skip);
                }else{
                    int sellToday = prices[index] + next[1];
                    int skip = next[buy];

                    curr[buy] = Math.max(sellToday, skip);
                }
            }

            next = curr;
        }

        return next[1];
    }

    public int maxProfit3(int[] prices) {
        int[][][] dp = new int[prices.length][2][3];
        for(int i = 0; i < prices.length; i++){
            for(int j = 0; j < 2; j++){
                for(int k = 0; k < 3; k++){
                    dp[i][j][k] = -1;
                }
            }
        }

        return maxProfitHelper3(0, 1, prices, 0, dp);
    }

    private int maxProfitHelper3(int index, int buy, int[] prices, int transactions, int[][][] dp){
        if(index ==  prices.length || transactions == 2){
            return 0;
        }

        if(dp[index][buy][transactions] != -1) return dp[index][buy][transactions];

        if(buy == 1){
            int buyToday = -prices[index] + maxProfitHelper3(index + 1, 0, prices, transactions, dp);
            int skip = maxProfitHelper3(index + 1, buy, prices, transactions, dp);

            return dp[index][buy][transactions] = Math.max(buyToday, skip);
        }else{
            int sellToday = prices[index] + maxProfitHelper3(index + 1, 1, prices, transactions + 1, dp);
            int skip = maxProfitHelper3(index + 1, buy, prices, transactions, dp);

            return dp[index][buy][transactions] = Math.max(sellToday, skip);
        }
    }

    public int maxProfit3Tabulation(int[] prices) {
        int n = prices.length;

        int[][][] dp = new int[n+1][2][3];
        for(int index = n - 1; index >= 0; index--) {
            for(int buy = 0; buy <= 1; buy++) {
                for(int transactions = 0; transactions < 2 ; transactions++){
                    if(buy == 1){
                        int buyToday = -prices[index] + dp[index + 1][0][transactions];
                        int skip = dp[index + 1][buy][transactions];

                        dp[index][buy][transactions] = Math.max(buyToday, skip);
                    }else{
                        int sellToday =  prices[index] + dp[index + 1][1][transactions + 1];
                        int skip = dp[index + 1][buy][transactions];

                        dp[index][buy][transactions] = Math.max(sellToday, skip);
                    }
                }
            }
        }

        return dp[0][1][0];
    }

    public int maxProfit3SpaceOptimization(int[] prices) {
        int n = prices.length;

        int[][] next = new int[2][3];
        for(int index = n - 1; index >= 0; index--) {
            int[][] curr = new int[2][3];
            for(int buy = 0; buy <= 1; buy++) {
                for(int transactions = 0; transactions < 2 ; transactions++){
                    if(buy == 1){
                        int buyToday = -prices[index] + next[0][transactions];
                        int skip = next[buy][transactions];

                        curr[buy][transactions] = Math.max(buyToday, skip);
                    }else{
                        int sellToday =  prices[index] + next[1][transactions + 1];
                        int skip = next[buy][transactions];

                        curr[buy][transactions] = Math.max(sellToday, skip);
                    }
                }
            }
            next = curr;
        }

        return next[1][0];
    }

    public int maxProfit4(int[] prices, int k) {
        int n = prices.length;

        int[][][] dp = new int[n][2][k+1];

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < 2; j++) {
                for(int l = 0; l <= k; l++) {
                    dp[i][j][l] = -1;
                }
            }
        }

        return maxProfit4Helper(0, 1, 0, k, prices, dp);
    }

    private int maxProfit4Helper(int index, int buy, int transactions, int k, int[] prices, int[][][]dp){
        if(index == prices.length || transactions == k){
            return 0;
        }

        if(dp[index][buy][transactions] != -1) return dp[index][buy][transactions];

        if(buy == 1){
            int buyToday = -prices[index] + maxProfit4Helper(index + 1, 0, transactions, k, prices, dp);
            int skip =  maxProfit4Helper(index + 1, buy, transactions, k, prices, dp);

            return dp[index][buy][transactions] = Math.max(buyToday, skip);
        }else {
            int sellToday = prices[index] + maxProfit4Helper(index + 1, 1, transactions + 1, k, prices, dp);
            int skip = maxProfit4Helper(index + 1, buy, transactions, k, prices, dp);

            return dp[index][buy][transactions] = Math.max(sellToday, skip);
        }
    }

    public int maxProfit4Tabulation(int[] prices, int k) {
        int n = prices.length;

        int[][][] dp = new int[n+1][2][k+1];

        for(int index = n - 1; index >= 0; index--) {
            for(int buy = 0; buy <= 1; buy++) {
                for(int transactions = 0; transactions < k ; transactions++){
                    if(buy == 1){
                        int buyToday = -prices[index] + dp[index + 1][0][transactions];
                        int skip = dp[index + 1][buy][transactions];

                        dp[index][buy][transactions] = Math.max(buyToday, skip);
                    }else{
                        int sellToday =  prices[index] + dp[index + 1][1][transactions+1];
                        int skip = dp[index + 1][buy][transactions];

                        dp[index][buy][transactions] = Math.max(sellToday, skip);
                    }
                }
            }
        }

        return dp[0][1][0];
    }

    public int maxProfit4SpaceOptimization(int[] prices, int k) {
        int n = prices.length;

        int[][] next = new int[2][k+1];

        for(int index = n - 1; index >= 0; index--) {
            int[][] curr = new int[2][k+1];
            for(int buy = 0; buy <= 1; buy++) {
                for(int transactions = 0; transactions < k ; transactions++){
                    if(buy == 1){
                        int buyToday = -prices[index] + next[0][transactions];
                        int skip = next[buy][transactions];

                        curr[buy][transactions] = Math.max(buyToday, skip);
                    }else{
                        int sellToday =  prices[index] + next[1][transactions+1];
                        int skip = next[buy][transactions];

                        curr[buy][transactions] = Math.max(sellToday, skip);
                    }
                }
            }
            next = curr;
        }

        return next[1][0];
    }

    public int maxProfitWithCooldown(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        for(int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }

        return maxProfitCooldownHelper(0, 1, prices, dp);
    }

    private int maxProfitCooldownHelper(int index, int buy, int[] prices,  int[][] dp) {
        if(index >= prices.length){
            return 0;
        }

        if(dp[index][buy] != -1) return dp[index][buy];

        if(buy == 1){
            int buyToday = -prices[index] + maxProfitCooldownHelper(index + 1, 0, prices, dp);
            int skip =  maxProfitCooldownHelper(index + 1, buy, prices, dp);

            return dp[index][buy] = Math.max(buyToday, skip);
        }else{
            int sellToday = prices[index] +  maxProfitCooldownHelper(index + 2, 1, prices, dp);
            int skip =  maxProfitCooldownHelper(index + 1, buy, prices, dp);

            return dp[index][buy] = Math.max(sellToday, skip);
        }
    }

    public int maxProfitWithCoolDownTabulation(int[] prices) {
        int n = prices.length;

        int[][] dp = new int[n+2][2];

        for(int index  = n - 1; index >= 0; index--) {
            for(int buy = 0; buy <= 1; buy++) {
                if(buy == 1){
                    int buyToday = -prices[index] + dp[index + 1][0];
                    int skip = dp[index + 1][buy];

                    dp[index][buy] = Math.max(buyToday, skip);
                }else{
                    int  sellToday =  prices[index] + dp[index + 2][1];
                    int skip =  dp[index + 1][buy];

                    dp[index][buy] = Math.max(sellToday, skip);
                }
            }
        }

        return dp[0][1];
    }

    public int maxProfitWithCoolDownSpaceOptimization(int[] prices) {
        int n = prices.length;

        int[] next2 = new int[2];
        int[] next1 = new int[2];

        for(int index  = n - 1; index >= 0; index--) {
            int[] curr = new int[2];
            for(int buy = 0; buy <= 1; buy++) {
                if(buy == 1){
                    int buyToday = -prices[index] + next1[0];
                    int skip = next1[buy];

                    curr[buy] = Math.max(buyToday, skip);
                }else{
                    int  sellToday =  prices[index] + next2[1];
                    int skip =  next1[buy];

                    curr[buy] = Math.max(sellToday, skip);
                }
            }

            next2 = next1;
            next1 = curr;
        }

        return next1[1];
    }


    public int maxProfitWithTransactionFees(int[] prices, int fees) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        for(int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }

        return maxProfitTransactionFeesHelper(0, 1, prices, dp, fees);
    }

    private int maxProfitTransactionFeesHelper(int index, int buy, int[] prices,  int[][] dp, int fees) {
        if(index >= prices.length){
            return 0;
        }

        if(dp[index][buy] != -1) return dp[index][buy];

        if(buy == 1){
            int buyToday = -prices[index] + maxProfitTransactionFeesHelper(index + 1, 0, prices, dp, fees);
            int skip =  maxProfitTransactionFeesHelper(index + 1, buy, prices, dp, fees);

            return dp[index][buy] = Math.max(buyToday, skip);
        }else{
            int sellToday = prices[index] - fees +  maxProfitTransactionFeesHelper(index + 1, 1, prices, dp, fees);
            int skip =  maxProfitTransactionFeesHelper(index + 1, buy, prices, dp, fees);

            return dp[index][buy] = Math.max(sellToday, skip);
        }
    }

    public int maxProfitTransactionFeesTabulation(int[] prices, int fees) {
        int n = prices.length;

        int[][] dp = new int[n+1][2];

        for(int index = n - 1; index >= 0; index--) {
            for(int buy = 0; buy <= 1; buy++) {
                if(buy == 1) {
                    int buyToday = -prices[index] + dp[index + 1][0];
                    int skip = dp[index + 1][buy];

                    dp[index][buy] = Math.max(buyToday, skip);
                }else{
                    int sellToday = prices[index] - fees + dp[index + 1][1];
                    int skip = dp[index + 1][buy];

                    dp[index][buy] = Math.max(sellToday, skip);
                }
            }
        }

        return dp[0][1];
    }

    public int maxProfitTransactionFeesSpaceOptimization(int[] prices, int fees) {
        int n = prices.length;

        int[] next = new int[2];

        for(int index = n - 1; index >= 0; index--) {
            int[] curr = new int[2];
            for(int buy = 0; buy <= 1; buy++) {
                if(buy == 1) {
                    int buyToday = -prices[index] + next[0];
                    int skip = next[buy];

                    curr[buy] = Math.max(buyToday, skip);
                }else{
                    int sellToday = prices[index] - fees + next[1];
                    int skip = next[buy];

                    curr[buy] = Math.max(sellToday, skip);
                }
            }
            next = curr;
        }

        return next[1];
    }

}

public class DpOnStocks {
    public static void main(String[] args) {
        DPStocksQuestions dps = new  DPStocksQuestions();
        System.out.println(dps.maxProfit(new int[]{7,1,5,3,6,4}));
        System.out.println(dps.maxProfitTabulation( new int[]{7,1,5,3,6,4}));
        System.out.println(dps.maxProfitSpaceOptimization(new int[]{7,1,5,3,6,4}));

        System.out.println(dps.maxProfit2(new int[]{7,1,5,3,6,4}));
        System.out.println(dps.maxProfit2Tabulation(new int[]{7,1,5,3,6,4}));
        System.out.println(dps.maxProfit2SpaceOptimization(new int[]{7,1,5,3,6,4}));

        System.out.println(dps.maxProfit3(new int[]{3,3,5,0,0,3,1,4}));
        System.out.println(dps.maxProfit3Tabulation(new int[]{3,3,5,0,0,3,1,4}));
        System.out.println(dps.maxProfit3SpaceOptimization(new int[]{3,3,5,0,0,3,1,4}));

        System.out.println(dps.maxProfit4(new int[]{3,2,6,5,0,3}, 2));
        System.out.println(dps.maxProfit4Tabulation( new int[]{3,2,6,5,0,3}, 2));
        System.out.println(dps.maxProfit4SpaceOptimization(new int[]{3,2,6,5,0,3}, 2));

        System.out.println(dps.maxProfitWithCooldown(new int[]{1,2,3,0,2}));
        System.out.println(dps.maxProfitWithCoolDownTabulation(new int[]{1,2,3,0,2}));
        System.out.println(dps.maxProfitWithCoolDownSpaceOptimization(new int[]{1,2,3,0,2}));

        System.out.println(dps.maxProfitWithTransactionFees(new int[]{1,3,2,8,4,9}, 2));
        System.out.println(dps.maxProfitTransactionFeesTabulation(new int[]{1,3,2,8,4,9}, 2));
        System.out.println(dps.maxProfitTransactionFeesSpaceOptimization(new int[]{1,3,2,8,4,9}, 2));
    }
}
