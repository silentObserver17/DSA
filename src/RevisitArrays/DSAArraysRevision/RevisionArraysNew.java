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
}

public class RevisionArraysNew {
    public  static void main(String[] args) {
        RevisionArrays rev = new RevisionArrays();

        rev.moveZerosEnd(new int[]{0,1,0,3,12});
    }
}
