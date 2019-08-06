package com.jamal.sort;

public class MergeSort {
    public static void main(String[] args) {
        int[] nums = new int[]{40, 2, 11, 5, 15, 6, 90, 10};
        merge_sort(nums, 0, nums.length - 1);
        print(nums);
    }

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

    public static void merge(int[] nums, int leftPtr, int rightPtr, int rightBound) {
        // 排序数组
        int[] sortNums = new int[rightBound-leftPtr+1];
        // 求出中间值
        int mid = rightPtr - 1;
        // 前半部分数组起始下标
        int i = leftPtr;
        // 后半部分起始下标
        int j = rightPtr;
        // 排序数组的起始下标
        int k = 0;

        while (i <= mid && j <= rightBound) {
            sortNums[k++] = nums[i] <= nums[j]?nums[i++]:nums[j++];
        }
        while (i <= mid) sortNums[k++] = nums[i++];
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
