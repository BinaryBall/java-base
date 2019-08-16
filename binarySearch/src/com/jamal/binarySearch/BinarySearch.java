package com.jamal.binarySearch;

/**
 * @author xiaoxiang
 * @title: BinarySearch
 * @projectName binarySearch
 * @description: TODO
 * @date 2019/8/1211:02
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] nums = {1, 2, 4, 5, 6, 7, 9, 12, 15, 19, 23, 26, 29, 34, 39};
//        System.out.println(bserach(nums, nums.length, 26));
//        System.out.println(recursiveBserach(nums, 0, nums.length - 1, 26));
//        System.out.println(bsearchFirstValue(nums, nums.length, 26));
        System.out.println(bserachFirstOverVlaue(nums, nums.length, 0));
    }

    /**
     * 循环版二分查找
     *
     * @param nums  数组
     * @param n     数组长度
     * @param value 要查找的值
     * @return
     */
    private static int bserach(int[] nums, int n, int value) {
        int low = 0;
        int high = n - 1;
        while (low <= high) {
            // 找出中间下标
            int mid = low + ((high - low) >> 1);
            if (nums[mid] > value) {
                high = mid - 1;
            } else if (nums[mid] < value) {
                low = mid + 1;
            } else {
                return mid;
            }
        }

        return -1;
    }

    /**
     * 递归算法实现二分查找
     *
     * @param nums  数组
     * @param low   左下标
     * @param high  右下标
     * @param value 要查找的值
     * @return
     */
    private static int recursiveBserach(int[] nums, int low, int high, int value) {

        if (low > high) return -1;

        // 找出中间下标
        int mid = low + ((high - low) >> 1);

        if (nums[mid] == value) {
            return mid;

        } else if (nums[mid] > value) {
            return recursiveBserach(nums, low, mid - 1, value);
        } else {
            return recursiveBserach(nums, mid + 1, high, value);
        }
    }


    /**
     * 查找第一个等于给定值的元素
     *
     * @param nums
     * @param length
     * @param value
     * @return
     */
    private static int bsearchFirstValue(int[] nums, int length, int value) {
        int low = 0;
        int high = length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (nums[mid] > value) {
                high = mid - 1;
            } else if (nums[mid] < value) {
                low = mid + 1;
            } else {
                // 判断当前是第一个元素或者前一个元素不等于要查找的值，则返回下标，如果前一个元素也等于要查找的值，则继续往前查找。
                if ((mid == 0) || (nums[mid - 1] != value)) return mid;
                else high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 查找第一个大于给定值的元素
     *
     * @param nums   数组
     * @param length 数组的长度
     * @param value  给定的值
     * @return
     */
    private static int bserachFirstOverVlaue(int[] nums, int length, int value) {
        int low = 0;
        int high = length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (nums[mid] > value) {
                // 判断当前是第一个元素或者前一个元素小于等于给定值，则返回下标，如果前一个元素大于给定的值，则继续往前查找。
                if ((mid == 0) || nums[mid - 1] <= value) return mid;
                else high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

}
