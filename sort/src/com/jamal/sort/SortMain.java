package com.jamal.sort;

/**
 * @author xiaoxiang
 * @title: SortMain
 * @projectName sort
 * @description: TODO
 * @date 2019/8/515:07
 */
public class SortMain {
    public static void main(String[] args) {
        int[] nums = new int[]{40, 2, 11, 5, 15, 6, 90, 10, 1, 3, 60, 17, 8};
//        SortUtils.insertionSort(nums,nums.length);
//        for (int i = 0 ; i < nums.length;i++){
//            System.out.println(nums[i]);
//        }
        SortUtils.print(nums);
        System.out.println("排序前.............");
        QuickSort.sort(nums, 0, nums.length - 1);
        SortUtils.print(nums);

    }
}
