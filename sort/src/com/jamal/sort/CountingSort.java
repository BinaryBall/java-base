package com.jamal.sort;

/**
 * 计数排序
 */
public class CountingSort {

    public static void main(String[] args) {
        int[] nums = {5,8,9,7,8,4,5,6,9,3,0,2,1};
        print(countingSort(nums,10,0));
    }

    /**
     * 计数排序
     *
     * @param nums       待排序数组
     * @param rangeCount 数组范围个数
     * @param min        最小的个数
     * @return
     */
    public static int[] countingSort(int[] nums, int rangeCount, int min) {
        int[] result = new int[nums.length];

        // 定义计数桶
        int[] count = new int[rangeCount + min];

        // 将数据添加到桶里
        for (int i = 0; i < nums.length; i++) {
            count[nums[i]]++;
        }

        // 遍历桶 将数据写入到返回数组中
        for (int i = min, j = 0; i < count.length; i++) {
            while (count[i]-- > 0) result[j++] = i;
        }
        return result;
    }

    public static void print(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + " ");
        }
    }
}
