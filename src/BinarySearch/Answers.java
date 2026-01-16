package BinarySearch;

import java.util.Arrays;

public class Answers {
    public int nthRoot(int n, int m) {
        int low = 1; int high = m;

        while(low <= high){
            int mid = low + (high - low)/2;
            long value = power(mid, n, m);

            if(value == m){
                return mid;
            } else if(value < mid){
                low = mid + 1;
            }else{
                high = mid - 1;
            }
        }

        return -1;
    }

    public long power(int base, int exp, int limit){
        long result = 1;
        for(int i = 0; i < exp; i++){
            result *= base;
            if(result > limit){
                return result;
            }
        }

        return result;
    }

    private long calculateHours(int[] piles, int k) {
        long total = 0;
        for(int pile : piles){
            total += (pile + k - 1)/k;
        }

        return total;
    }

    public int KokoEatingBananas(int[] nums, int h){
        int low = 1; // minimum bananas koko can eat.
        int high = Arrays.stream(nums).max().getAsInt();
        int best = high;

        while(low <= high){
            int mid = low + (high - low)/2;
            long hours = calculateHours(nums, mid);

            if(hours <= h){
                best = mid;
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }

        return best;
    }

    private boolean canPlaceCows(int[] stalls, int k, int d){
        int lastPlace = stalls[0];
        int count = 1;

        for(int i = 1; i < stalls.length; i++){
            if(stalls[i] - lastPlace >= d){
                lastPlace = stalls[i];
                count++;

                if(count >= k){
                    return true;
                }
            }
        }

        return count >= k;
    }

    public int AggressiveCows(int[] stalls, int k){
        Arrays.sort(stalls);

        int n = stalls.length;
        int start = 1;
        int end = stalls[n-1] - stalls[0];
        int best = 0;

        while(start <= end){
            int mid = start + (end - start)/2;
            if(canPlaceCows(stalls, k, mid)){
                start = mid + 1;
                best = mid;
            }
            else{
                end = mid - 1;
            }
        }

        return best;
    }

    private boolean canGiveBooks(int[] books, int m, int maxPages){
        int allocated = 1;
        int currentSum = 0;

        for(int pages: books){
            if(currentSum + pages > maxPages){
                allocated++;
                currentSum = pages;

                if(allocated > m){
                    return false;
                }
            }else{
                currentSum += pages;
            }
        }

        return allocated <= m;
    }

    public int BookAllocation(int[]books, int m){
        int start = Arrays.stream(books).max().getAsInt();
        int end = Arrays.stream(books).sum();
        int best = end;

        while(start <= end){
            int mid = start + (end - start)/2;

            if(canGiveBooks(books, m, mid)){
                end = mid - 1;
                best = mid;
            }else{
                start = mid + 1;
            }
        }
        return  best;
    }

    boolean canMakeMBouquets(int[] bloomDay, int m, int k, int day){
        int bouquets = 0;
        int consecutive = 0;

        for(int bloom: bloomDay){
            if(bloom <= day){
                consecutive++;
                if(consecutive == k){
                    bouquets++;
                    consecutive = 0;
                }
            }else{
                consecutive = 0;
            }
        }

        return bouquets >= m;
    }

    public int MakeBouquets(int[] bloomDay, int m, int k){
        int start =  Arrays.stream(bloomDay).min().getAsInt();
        int end = Arrays.stream(bloomDay).max().getAsInt();

        if((long) m * k > bloomDay.length){
            return -1;
        }

        int best = -1;
        while(start <= end){
            int mid = start + (end - start)/2;
            if(canMakeMBouquets(bloomDay, m, k, mid)){
                best = mid;
                end = mid - 1;
            }else{
                start = mid + 1;
            }
        }

        return best;
    }

    public double gasStationsOptimal(int[] stations, int k){
        int n = stations.length;

        double low = 0, high = 0;

        for(int i = 1; i < n; i++){
            high = Math.max(high, stations[i] - stations[i-1]);
        }

//        double diff = 1e-6;
//        double ans = -1;
//
//        while(high - low > diff){
//            double mid = low + (high - low)/2;
//            if(canPlace(stations, k, mid)){
//                ans = mid;
//                high = mid;
//            }else{
//                low = mid;
//            }
//        }

        for(int iter = 0; iter < 100; iter++){
            double mid = low + (high - low)/2;
            if(canPlace(stations, k, mid)){
                high = mid;
            }else{
                low = mid;
            }
        }

        return high;
    }

    private boolean canPlace(int[] stations, int k, double d){
        int newStations = 0;
        int n = stations.length;

        for(int i = 1; i < n; i++){
            double gap = stations[i] - stations[i-1];

            int needed = (int)Math.ceil(gap / d) - 1;
            newStations += needed;

            if(newStations > k){
                return false;
            }
        }

        return newStations <= k;
    }

}
