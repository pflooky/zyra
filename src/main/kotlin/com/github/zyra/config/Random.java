package com.github.zyra.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Random {

    public static void main(String[] args) {
//        int[] nums = {-4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6 ,7, 8, 9, 10, 11, 12};
//        int[] nums = {-4,-1,0,3,10};
        int[] nums = {-1,-100,3,99};
//        System.out.println(search(nums, 11));
//        System.out.println(firstBadVersion(2126753390));
//        System.out.println(searchInsert(nums, 0));
//        System.out.println(Arrays.toString(sortedSquares(nums)));
//        rotateArr(nums, 2);
        System.out.println(checkInclusion("adc", "dcda"));
    }

    public static int search(int[] nums, int target) {
        if (nums.length <= 0) return -1;
        int middle = nums.length / 2;
        int i = middle;
        int end = nums.length;
        int start = 0;
        int steps = 0;
        while (steps <= nums.length / 2) {
            if (nums[i] == target) return i;
            else if (nums[i] < target) {
                i = (end + middle) / 2;
                start = middle;
            } else {
                i = (middle + start) / 2;
                end = middle;
            }
            middle = i;
            steps++;
        }
        return -1;
    }

    public static int firstBadVersion(int n) {
        if (n == 1) return 1;
        long start = 0;
        long middle = n / 2;
        long end = n;
        long firstGoodVersion = 0;
        long firstBadVersion = 0;
        while (start <= end) {
            boolean prevIsBadVersion = isBadVersion((int) middle);
            if (prevIsBadVersion) firstBadVersion = middle;
            else firstGoodVersion = middle;
            if (firstBadVersion - firstGoodVersion == 1) return (int) firstBadVersion;
            if (!prevIsBadVersion) {
                start = middle;
            } else {
                end = middle;
            }
            middle = (start + end + 1) / 2;
        }
        return (int) firstBadVersion;
    }

    private static boolean isBadVersion(int i) {
        return i > 1702766719;
    }

    public static int searchInsert(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        while (start < end) {
            int mid = (start + end) / 2;
            if (nums[mid] == target) return mid;
            else if (nums[mid] < target) start = mid + 1;
            else end = mid;
        }
        if (start == nums.length - 1 && nums[start] < target) {
            return start + 1;
        } else return start;
    }

    public static int[] sortedSquares(int[] nums) {
        int[] sqrd = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int squared = (int) Math.pow(nums[i], 2);
            sqrd[i] = squared;
        }
        for (int j = 0; j < sqrd.length - 1; j++) {
            int curr = sqrd[j];
            if (curr > sqrd[j + 1]) {
                int tmp = sqrd[j + 1];
                sqrd[j + 1] = curr;
                sqrd[j] = tmp;
                for (int k = j; k > 0; k--) {
                    if (sqrd[k] < sqrd[k - 1]) {
                        int tmp1 = sqrd[k - 1];
                        sqrd[k - 1] = sqrd[k];
                        sqrd[k] = tmp1;
                    }
                }
            }
        }
        return sqrd;
    }

    public static void rotate(int[] nums, int k) {
        int nextInd = 0, nextVal = 0;
        for (int i = 0; i < nums.length; i++) {
            int currIndex = (nextInd + k) % nums.length;
            if (i == 0) {
                nextVal = nums[currIndex];
                nums[currIndex] = nums[nextInd];
            } else {
                int tmp = nums[currIndex];
                nums[currIndex] = nextVal;
                nextVal = tmp;
            }
            nextInd = currIndex;
        }
        System.out.println(Arrays.toString(nums));
    }

    public static void rotateArr(int[] nums, int k) {
        reverse(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));
        reverse(nums, 0, k - 1);
        System.out.println(Arrays.toString(nums));
        reverse(nums, k, nums.length - 1);
        System.out.println(Arrays.toString(nums));
    }

    private static void reverse(int[] nums, int start, int end) {
        for (int i = 0; i < (end - start) / 2 + 1; i++) {
            int tmp = nums[end - i];
            nums[end - i] = nums[start + i];
            nums[start + i] = tmp;
        }
        Map<String, Integer> map = new HashMap<>();
    }

    public static boolean checkInclusion(String s1, String s2) {
        for (int i = 0; i < s2.length() - s1.length() + 1; i++) {
            if (isPermutation(s2.substring(i, i + s1.length()), s1)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isPermutation(String s, String r) {
        char[] sChars = s.toCharArray();
        char[] rChar = r.toCharArray();
        System.out.println(s + ": " + r);
        Map<String, Integer> sCharCount = new HashMap<>();

        for (int i = 0; i < sChars.length; i++) {
            sCharCount.put(
                    String.valueOf(sChars[i]),
                    sCharCount.getOrDefault(String.valueOf(sChars[i]), 0) + 1
            );
        }

        for (int j = 0; j < sChars.length; j++) {
            if (!sCharCount.containsKey(String.valueOf(rChar[j])) ||
            sCharCount.get(String.valueOf(rChar[j])) == 0) {
                return false;
            } else {
                sCharCount.put(
                        String.valueOf(rChar[j]),
                        sCharCount.get(String.valueOf(rChar[j])) - 1
                );
            }
        }
        return true;
    }
}
