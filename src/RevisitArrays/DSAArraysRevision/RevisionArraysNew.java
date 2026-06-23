package RevisitArrays.DSAArraysRevision;

import java.util.Arrays;

class RevisionArrays{
    public void moveZerosEnd(int[] nums) {
        if(nums == null || nums.length == 0)
            return;

        int i = 0;

        for(int j = 0; j < nums.length; j++) {
            if(nums[j] != 0) {
                if(i != j) {
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
                i++;
            }
        }

        System.out.println(Arrays.toString(nums));
    }

    // nums = [1,1,2,2,3,4,4]
    public int RemoveDuplicatesFromArray(int[] nums) {
        if(nums == null || nums.length == 0)
            return 0;

        int i = 0;
        for(int j = 1; j < nums.length; j++) {
            if(nums[i] != nums[j]) {
                i++;
                nums[i] = nums[j];
            }
        }

        return i + 1;
    }

    public int BestTimeToBuySellStocks(int[] prices) {
        if(prices == null || prices.length == 0)
            return 0;

        int maxProfit = 0;
        int buyDay = prices[0];

        for(int i = 1; i < prices.length; i++) {
            int sellDay = prices[i];

            int cost = sellDay - buyDay;

            maxProfit = Math.max(maxProfit, cost);
            buyDay = Math.min(buyDay, sellDay);
        }

        return maxProfit;
    }
}

public class RevisionArraysNew {
    public  static void main(String[] args) {
        RevisionArrays rev = new RevisionArrays();

        rev.moveZerosEnd(new int[]{0,1,0,3,12});
        System.out.println(rev.RemoveDuplicatesFromArray(new int[]{1,1,2,2,3,4,4}));
    }
}
