package Heaps;

import java.util.*;

class Problems {
    Random rand = new Random();

    public int KthLargestElement(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for(int i = 0; i < k; i++) {
            pq.offer(nums[i]);
        }

        for(int i = k; i < nums.length; i++) {
            if(pq.peek() < nums[i]) {
                pq.poll();
                pq.offer(nums[i]);
            }
        }

        return pq.peek();
    }

    public int KthLargestQuickSelect(int[] nums, int k) {
        int target = nums.length - k;
        int left = 0;
        int right = nums.length - 1;

        while(left <= right) {
            int partitionIndex = RandomPartition(nums, left, right);

            if(partitionIndex == k) {
                return nums[partitionIndex];
            }
            else if(partitionIndex < k) {
                left = partitionIndex + 1;
            }else{
                right = partitionIndex - 1;
            }
        }

        return -1;
    }

    public int RandomPartition(int[] nums, int left, int right) {
        int randomIndex =  left + rand.nextInt(right - left + 1);
        swap(nums, randomIndex, right);

        return partition(nums, left, right);
    }

    public int partition(int[] nums, int left, int right) {
        int pivot = nums[right];

        int i = left;
        for(int j = left; j < right; j++) {
            if(nums[j] <= pivot) {
                swap(nums, i, j);
                i++;
            }
        }

        swap(nums, i, right);

        return i;
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public int KthSmallest(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

        for(int num:  nums) {
            pq.offer(num);

            if(pq.size() > k) {
                pq.poll();
            }
        }

        return pq.peek();
    }

    private class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    public ListNode mergeKLinkedList(ListNode[] lists) {
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) ->a.val - b.val);

        for(ListNode node : lists) {
            if(node != null) {
                minHeap.offer(node);
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;

        while(!minHeap.isEmpty()) {
            ListNode smallest = minHeap.poll();
            cur.next = smallest;
            cur = cur.next;

            if(smallest.next != null) {
                minHeap.offer(smallest.next);
            }
        }

        return dummy.next;
    }

    public int TaskScheduler(char[] tasks, int n) {
        int[] freq = new int[26];
        for(int i = 0; i < tasks.length; i++) {
            freq[tasks[i] - 'A']++;
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

        for(int f: freq) {
            if(f > 0){
                pq.offer(f);
            }
        }
        int time = 0;

        while(!pq.isEmpty()) {
            int slots = n + 1;
            List<Integer> temp = new ArrayList<>();

            while(slots > 0 && !pq.isEmpty()){
                int count = pq.poll();
                if(count - 1 > 0){
                    temp.add(count - 1);
                }

                time++;
                slots--;
            }

            pq.addAll(temp);

            if(!pq.isEmpty()){
                time += slots;
            }
        }

        return time;
    }

    public int TaskSchedulerMath(char[] tasks, int n) {
        int[] freq = new int[26];
        for(int i = 0; i < tasks.length; i++) {
            freq[tasks[i] - 'A']++;
        }

        int maxFreq = 0;
        for(int f: freq) {
            maxFreq = Math.max(maxFreq, f);
        }

        int maxCount = 0;
        for(int f: freq){
            if(f == maxFreq) {
                maxCount++;
            }
        }

        int formulaResult = (maxFreq - 1) * (n + 1) + maxCount;

        return Math.max(formulaResult, tasks.length);
    }

    public boolean isNStraightHand(int[] hand, int groupSize){
        if(hand.length % groupSize != 0) return false;

        Map<Integer, Integer> freq = new HashMap<>();
        for(int card: hand){
            freq.put(card, freq.getOrDefault(card, 0) + 1);
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>(freq.keySet());

        while(!pq.isEmpty()){
            int start =  pq.peek(); // smallest unplaced card

            for(int i = start; i < start + groupSize; i++){
                if(!freq.containsKey(i)) return false;

                freq.put(i, freq.get(i) - 1);

                if(freq.get(i) == 0){
                    // only remove from pq if it is the current min.
                    // heap ordering ensures we remove front naturally
                    if(i != pq.peek()) return false;
                    pq.poll();
                }
            }
        }

        return true;
    }
}

record Tweet(int tweetId, int timestamp){}

class Twitter {
    private int timestamp;
    private Map<Integer, List<Tweet>> tweets; // userId -> their tweets.
    private Map<Integer, Set<Integer>> following; // users -> set of followIds.

    public Twitter(){
        timestamp = 0;
        tweets = new HashMap<>();
        following = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        tweets.putIfAbsent(userId, new ArrayList<>());
        tweets.get(userId).add(new Tweet(tweetId, timestamp++));
    }

    public List<Integer> getTweets(int userId){
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a,b) -> b[0] - a[0]);

        Set<Integer> feedUsers = new HashSet<>();
        feedUsers.add(userId);
        feedUsers.addAll(following.getOrDefault(userId, new HashSet<>()));

        // Send with user's most recent tweet
        for(int uid: feedUsers){
            List<Tweet> userTweets = tweets.getOrDefault(uid, new ArrayList<>());

            if(!userTweets.isEmpty()){
                int idx = userTweets.size() - 1;
                Tweet t = userTweets.get(idx);

                maxHeap.offer(new int[]{t.timestamp(), t.tweetId(), uid, idx});
            }
        }

        List<Integer> feed = new ArrayList<>();

        while(!maxHeap.isEmpty() && feed.size() < 10){
            int[] curr = maxHeap.poll();
            feed.add(curr[1]);

            int nextIdx = curr[3] - 1;
            if(nextIdx >= 0){
                int uid = curr[2];
                Tweet tweet = tweets.get(uid).get(nextIdx);
                maxHeap.offer(new int[]{tweet.timestamp(), tweet.tweetId(), uid, nextIdx});
            }
        }

        return feed;
    }

    public void follow(int followerId, int followeeId) {
        following.putIfAbsent(followerId, new HashSet<>());
        following.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        following.getOrDefault(followerId, new HashSet<>()).remove(followeeId);
    }
}

class MedianFinder {
    PriorityQueue<Integer> maxHeap;
    PriorityQueue<Integer> minHeap;

    public MedianFinder() {
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        minHeap = new PriorityQueue<>();
    }

    public void addNum(int num) {
        // step 1: always add number to max heap
        maxHeap.add(num);

        // step2: fix ordering - left max should not exceed right min
        if(!minHeap.isEmpty() && maxHeap.peek() > minHeap.peek()) {
            minHeap.offer(maxHeap.poll());
        }

        // step3: fix sizes - left can have at most 1 extra.
        if(maxHeap.size() > minHeap.size() + 1) {
            minHeap.offer(maxHeap.poll());
        }else if(minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    public double findMedian() {
        if(maxHeap.size() == minHeap.size()) {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }

        return maxHeap.peek();
    }
}


public class MediumHeaps {
    public static void main(String[] args){
        Problems pb = new  Problems();
        System.out.println(pb.KthLargestElement(new int[]{3,2,1,5,6,4}, 2));

        System.out.println(pb.KthSmallest(new int[]{3,2,1,5,6,4}, 2));

        System.out.println(pb.TaskScheduler(new char[]{'A','A','A','B','B','B'}, 2));
        System.out.println(pb.TaskSchedulerMath(new char[]{'A','A','A','B','B','B'}, 2));

        System.out.println(pb.isNStraightHand(new int[]{1,2,3,6,2,3,4,7,8}, 3));
    }
}
