import RevisitArrays.Fundamentals.ArraysFundamentals;

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
    }
}