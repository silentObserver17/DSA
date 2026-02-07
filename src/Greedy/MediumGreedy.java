package Greedy;

import java.util.ArrayList;
import java.util.Arrays;

public class MediumGreedy {
    public long shortestJobV1(int[] bt) {
        Arrays.sort(bt);
        long waitingTime = 0;
        long totalWaitingTime = 0;

        for(int i = 1; i < bt.length; i++){
            waitingTime += bt[i-1];
            totalWaitingTime += waitingTime;
        }

        return totalWaitingTime /bt.length;
    }

    public int[] JobSequencing(int[][] Jobs) {
        Arrays.sort(Jobs, ((a,b) -> b[2] - a[2]));
        int profit = 0;
        int jobsCount = 0;

        int maxDeadline = 0;
        for(int[] job: Jobs){
            maxDeadline = Math.max(maxDeadline, job[1]);
        }

        boolean[] slot = new boolean[maxDeadline + 1];

        for(int[] job: Jobs){
            int deadline = job[1];

            for(int i = deadline; i > 0; i--){
                if(!slot[i]){
                    slot[i] = true;
                    jobsCount++;
                    profit += job[2];
                    break;
                }
            }
        }

        return new int[]{jobsCount, profit};
    }

    public int maxMeetings(int[] start, int[] end) {
        if (start.length == 0) return 0;

        int[][] meetingList = new int[start.length][2];
        int meetings = 1;

        for(int i = 0; i < start.length; i++){
            meetingList[i][0] = start[i];
            meetingList[i][1] = end[i];
        }

        Arrays.sort(meetingList, (a,b) -> a[1] - b[1]);

        int lastMeeting = meetingList[0][1];
        for(int i = 1; i < start.length; i++){
            if(meetingList[i][0] >= lastMeeting){
                meetings++;
                lastMeeting = meetingList[i][1];
            }
        }

        return meetings;
    }

    public int MaximumNonOverlappingIntervals(int[][] intervals) {
        if(intervals.length == 0) return 0;
        Arrays.sort(intervals, (a,b) -> Integer.compare(a[1], b[1]));
        int overlap = 0;

        int lastNonOverlapping = intervals[0][1];
        for(int i = 1; i < intervals.length; i++){
            if(intervals[i][0] < lastNonOverlapping){
                overlap++;
            }else{
                lastNonOverlapping = intervals[i][1];
            }
        }

        return overlap;
    }

    public int[][] insertNewInterval(int[][] Intervals, int[] newInterval) {
        ArrayList<int[]> result = new ArrayList<>();
        int i = 0;
        int n = Intervals.length;

        // Add all elements before the start of new interval.
        while(i < n && Intervals[i][1] < newInterval[0]){
            result.add(Intervals[i]);
            i++;
        }

        int mergedStart = newInterval[0];
        int mergedEnd = newInterval[1];

            while(i < n && Intervals[i][0] <= mergedEnd){
                mergedStart = Math.min(mergedStart, Intervals[i][0]);
                mergedEnd = Math.max(mergedEnd, Intervals[i][1]);
                i++;
            }

            result.add(new int[]{mergedStart, mergedEnd});

            while(i < n){
                result.add(Intervals[i]);
                i++;
            }

            return result.toArray(new int[result.size()][]);
        }

    public int findPlatformBrute(int[] Arrival, int[] Departure) {
        int n = Arrival.length;
        int ans = 1;

        for(int i = 0; i < n; i++){
            int count = 1;
            for(int j = 0; j < n; j++){
                if(i == j) continue;
                if(Arrival[j] <= Arrival[i] && Arrival[i] <= Departure[j]){
                    count++;
                }
            }

            ans = Math.max(ans, count);
        }

        return ans;
    }

    public int findPlatform(int[] Arrival, int[] Departure) {
        Arrays.sort(Arrival);
        Arrays.sort(Departure);
        int n = Arrival.length;

        int platforms = 0;
        int maxPlatforms = 0;
        int arrivalCounter = 0;
        int departureCounter = 0;

        while(arrivalCounter < n && departureCounter < n){
            if(Arrival[arrivalCounter] <= Departure[departureCounter]){
                platforms++;
                arrivalCounter++;
            }else if(Arrival[arrivalCounter] > Departure[departureCounter]){
                platforms--;
                departureCounter++;
            }
            maxPlatforms = Math.max(maxPlatforms, platforms);
        }

        return maxPlatforms;
    }

    public int canJump2(int[] nums) {
        int currentEnd = 0;
        int jumps = 0;
        int farthest = 0;

        for(int i = 0; i < nums.length - 1; i++){
            farthest = Math.max(farthest, nums[i] + i);

            if(i == currentEnd){
                jumps++;
                currentEnd = farthest;
            }
        }

        return jumps;
    }

    public String largestNumber(int[] nums) {
        String[] arr = new  String[nums.length];

        for(int i = 0; i < nums.length; i++){
            arr[i] = String.valueOf(nums[i]);
        }

        Arrays.sort(arr, (a,b) -> (b + a).compareTo(a + b));

        if(arr[0].equals("0")) return "0";

        StringBuilder sb = new StringBuilder();
        for(String s : arr) sb.append(s);

        return sb.toString();
    }


}
