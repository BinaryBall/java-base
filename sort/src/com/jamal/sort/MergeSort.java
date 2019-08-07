package com.jamal.sort;

public class MergeSort {
    public static void main(String[] args) {
        int[] nums = new int[]{40, 2, 11, 5, 15, 6, 90, 10};
        merge_sort(nums, 0, nums.length - 1);
        print(nums);
    }

    /**
     * 递归 将数组分成两半，分别对前后两部分排序
     *
     * @param nums     数组
     * @param leftPtr  左半边开始下标
     * @param rightPtr 右半边结束下标
     */
    public static void merge_sort(int[] nums, int leftPtr, int rightPtr) {
        if (leftPtr >= rightPtr) return;
        // 将数组分成两半
        int mid = leftPtr + (rightPtr - leftPtr) / 2;

        // 左半边排序
        merge_sort(nums, leftPtr, mid);

        // 右半边排序
        merge_sort(nums, mid + 1, rightPtr);

        merge(nums, leftPtr, mid + 1, rightPtr);
    }

    /**
     * 将前后两半排好序的数组进行合并
     *
     * @param nums
     * @param leftPtr    左半边开始下标值
     * @param rightPtr   右半边开始下标值
     * @param rightBound 左半边结束值
     */
    public static void merge(int[] nums, int leftPtr, int rightPtr, int rightBound) {
        // 新开辟临时排序数组
        int[] sortNums = new int[rightBound - leftPtr + 1];
        // 求出中间值
        int mid = rightPtr - 1;
        // 前半部分数组起始下标
        int i = leftPtr;
        // 后半部分起始下标
        int j = rightPtr;

        // 临时排序数组的起始下标
        int k = 0;

        // 左右两边分别逐一比较，将小的存入到临时数组
        while (i <= mid && j <= rightBound) {
            sortNums[k++] = nums[i] <= nums[j] ? nums[i++] : nums[j++];
        }
        // 判断左半边时候有剩下
        while (i <= mid) sortNums[k++] = nums[i++];
        // 判断右半边时候有剩下
        while (j <= rightBound) sortNums[k++] = nums[j++];

        // 将数组拷贝回nums
        for (int m = 0; m < sortNums.length; m++) nums[leftPtr + m] = sortNums[m];
    }

    public static void print(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + " ");
        }
    }
}
