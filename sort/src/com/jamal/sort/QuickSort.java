package com.jamal.sort;

/**
 * @author 曾小辉
 * @title: QuickSort
 * @projectName sort
 * @description: 快速排序demo
 * @date 2019/8/616:04
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] nums = {40, 2, 11, 5, 15, 6, 90, 10};
        sort(nums, 0, nums.length - 1);
        print(nums);
    }

    // 递归
    public static void sort(int[] nums, int leftBound, int rightBound) {
        if (leftBound >= rightBound) return;
        // 分区值的下标位置
        int mid = partition(nums, leftBound, rightBound);
        // 左分区排序
        sort(nums, leftBound, mid - 1);
        // 右分区排序
        sort(nums, mid, rightBound);
    }

    // 分区
    public static int partition(int[] nums, int leftBound, int rightBound) {

        // 分区点的值
        int pivot = nums[rightBound];

        // 左边下标
        int left = leftBound;
        //右边起始下标
        int right = rightBound - 1;

        while (left <= right) {
            // 找到第一个大于分区值的
            while (left <= right && nums[left] <= pivot) left++;

            // 找到第一个小于分区值的
            while (left <= right && nums[right] > pivot) right--;

            // 将左右两边的值进行交换
            if (left < right) swap(nums, left, right);
        }
        // 将left的值与分区值交换位置
        swap(nums, left, rightBound);

        return left;
    }

    /**
     * 数据交换
     *
     * @param nums
     * @param i
     * @param k
     */
    public static void swap(int[] nums, int i, int k) {
        int temp = nums[i];
        nums[i] = nums[k];
        nums[k] = temp;

    }

    public static void print(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + " ");
        }
    }
}
