package com.jamal.sort;

import java.util.ArrayList;

/**
 * 基数排序
 */
public class RadixSort {

    public static void main(String[] args) {
        int[] nums = {2154,5896,356,8888,1596,3654,201,698,412};
        radixSort(nums);
        print(nums);
    }

    public static void radixSort(int[] nums){

        // 记录数组的大小
        int length = nums.length;

        //最大值
        int numMax = nums[0];
        for(int i = 0;i < length;i++){
            if(nums[i] > numMax){
                numMax = nums[i];
            }
        }
        //当前排序位置
        int location = 1;

        //桶列表 一个桶中会有多个不同的元素
        ArrayList<ArrayList<Integer>> bucketList = new ArrayList<>();

        //初始化一个空桶
        for(int i = 0; i < 10; i++){
            bucketList.add(new ArrayList<Integer>());
        }

        while(true)
        {
            //求出每位数的最小值
            int min = (int)Math.pow(10,(location - 1));
            // 判断最大值是否小于每位数的最小值，小于就结束
            if(numMax < min){
                break;
            }
            //遍历数据，将数据写入桶
            for(int i = 0; i < length; i++)
            {
                //计算余数 放入相应的桶
                int number = ((nums[i] / min) % 10);
                bucketList.get(number).add(nums[i]);
            }
            //将数从桶中取回，重新组成数组
            int k = 0;
            for (int i=0;i<10;i++){
                int size = bucketList.get(i).size();
                for(int j = 0;j < size;j ++){
                    nums[k++] = bucketList.get(i).get(j);
                }
                // 将桶清空，用于下一次排序
                bucketList.get(i).clear();
            }
            // 位数加一
            location++;
        }
    }

    public static void print(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + " ");
        }
    }
}
