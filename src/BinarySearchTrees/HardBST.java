package BinarySearchTrees;

public class HardBST {
    class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val){
            this.val = val;
        }
    }

    record MaxSumTree(int minVal, int maxVal, int sum, int maxSum, boolean isBST){}

    public int maxSumBST(TreeNode root) {
        MaxSumTree node = helperMaxTree(root);
        return node.maxSum;
    }

    public MaxSumTree helperMaxTree(TreeNode root) {
        if(root == null) return new MaxSumTree(Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 0, true);

        MaxSumTree left = helperMaxTree(root.left);
        MaxSumTree right = helperMaxTree(root.right);

        if(left.isBST && right.isBST && left.maxVal < root.val && right.minVal > root.val){
            int currSum = left.sum + right.sum + root.val;
            int maxSum = Math.max(currSum, Math.max(left.maxSum, right.maxSum));

            int minVal = Math.min(left.minVal, root.val);
            int maxVal = Math.max(root.val, right.maxVal);

            return new MaxSumTree(minVal, maxVal, currSum, maxSum, true);
        }

        return new MaxSumTree(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, Math.max(left.maxSum, right.maxSum), false);
    }


    record MaxSizeBST(int minVal, int maxVal, int size, int maxSize, boolean isBST){}

    public int maxSizeBST(TreeNode root) {
        return helperMaxSizeBST(root).maxSize;
    }

    public MaxSizeBST helperMaxSizeBST(TreeNode root) {
        if(root == null)  return new MaxSizeBST(Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 0, true);

        MaxSizeBST left = helperMaxSizeBST(root.left);
        MaxSizeBST right = helperMaxSizeBST(root.right);

        if(left.isBST && right.isBST && left.maxVal < root.val && right.minVal > root.val){
            int currSum = left.size + right.size + root.val;
            int maxSum = Math.max(currSum, Math.max(left.maxSize, right.maxSize));

            int minVal = Math.min(left.minVal, root.val);
            int maxVal = Math.max(root.val, right.maxVal);

            return new MaxSizeBST(minVal, maxVal, currSum, maxSum, true);
        }

        return new MaxSizeBST(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, Math.max(left.maxSize, right.maxSize), false);
    }

    public static void main(String[] args) {

    }
}
