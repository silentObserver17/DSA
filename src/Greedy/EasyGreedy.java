package Greedy;

import java.util.Arrays;

public class EasyGreedy {
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int count = 0;
        int i = 0, j = 0;

        while(i < g.length && j < s.length){
            if(g[i] <= s[j]){
                count++;
                i++;
                j++;
            }else{
                j++;
            }
        }

        return count;
    }

    public boolean lemonadeChange(int[] bills) {
        int five = 0;
        int ten = 0;

        for(int bill: bills){
            if(bill == 5) five++;
            else if(bill == 10){
                if(five == 0) return false;
                else{
                    five--;
                    ten++;
                }
            }
            else if(bill == 20){
                if(ten > 0 && five > 0){
                    ten--;
                    five--;
                }else if(five >= 3){
                    five = five - 3;
                }else{
                    return false;
                }
            }
        }

        return true;
    }

    public boolean canJump(int[] nums) {
        int maxReach = 0;

        for(int i = 0; i < nums.length; i++){
            if(i > maxReach){
                return false;
            }

            maxReach = Math.max(maxReach, i + nums[i]);

            if(maxReach >= nums.length - 1){
                return true;
            }
        }

        return true;
    }

}
