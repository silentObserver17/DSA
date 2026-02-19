### Overview of Sliding Window & Two Pointers
- **Core Idea**: Use two pointers (left and right) to define a "window" over the array/string. Move them based on a condition to find max/min length, count valid subarrays, or optimize something (e.g., area, sum).
    - **Sliding Window**: Usually variable-size, same direction (left/right both move rightward, left catches up when condition violates).
    - **Two Pointers**: Can be same direction (for unsorted arrays) or opposite (for sorted or max/min problems).
- **When to use**: Problems with contiguous subarrays/strings, constraints like "at most k", "exactly k", "sum = goal", "max/min area".
    - Time: O(n) in most cases (each element visited constant times).
    - Space: O(1) or O(n) (for freq maps).
- **Brute Force Baseline**: Always start with O(n²) pairs/loops → optimize to O(n).
- **Interview Tip**: Draw the array, pointers, and simulate with small example. Ask clarifying questions: "Sorted? Unique? Negatives? Empty array?"
- **Common Pitfalls**: Off-by-1 errors, forgetting to update max/min, not handling k=0, overestimating in maxFreq trick.
- **Guiding Question for you**: When do you suspect a problem is sliding window? (Answer: Contiguous subarray, condition on sum/freq/distinct, max/min length/count.)

### 1. Fixed-Size Sliding Window
- **Definition**: Window of **exact size k**. Slide it across the array, compute something (e.g., max sum).
- **When to use**: "Subarray of exactly size k with max/min something".
- **Key Steps**:
    - Compute initial window [0..k-1].
    - Slide: subtract left, add right, update result.
- **Complexity**: Time O(n), Space O(1).
- **Questions we solved**:
    - Max sum subarray of size k (e.g., nums = [1,4,2,10,23,3,1,0,20], k=4 → 39).
- **Code Snippet (Java)**:
  ```java
  int maxSum = 0;
  int currentSum = 0;
  for (int i = 0; i < k; i++) currentSum += nums[i];
  maxSum = currentSum;

  for (int i = k; i < n; i++) {
      currentSum += nums[i] - nums[i - k];
      maxSum = Math.max(maxSum, currentSum);
  }
  return maxSum;
  ```
- **Example/Edge Case**: nums = [1,2,3,4], k=2 → max=7 ("3+4").  
  Edge: k=1 → max element; k=n → sum of all; k>n → 0.
- **Pitfall**: Don't forget to check k > n or k=0.
- **Guiding Question**: How is this different from variable window? (Answer: No condition check, just slide fixed.)

### 2. Variable-Size Sliding Window (Condition-Based)
- **Definition**: Expand right, shrink left when condition violates. Use for "longest/shortest subarray satisfying X".
- **When to use**: "At most k distinct", "at most k flips", "all chars from t".
- **Key Steps**:
    - Expand right → add element, update state (freq, sum, distinct).
    - While invalid → shrink left → remove element, update state.
    - When valid → update max/min length.
- **Complexity**: Time O(n), Space O(1) or O(26/128) for freq.
- **Questions we solved**:
    - Longest Substring Without Repeating Characters (no repeats → k=1 distinct).
    - Longest Substring with At Most K Distinct Characters (at most k distinct).
    - Fruit Into Baskets (at most 2 distinct).
    - Max Consecutive Ones III (at most k flips of 0 to 1).
    - Longest Repeating Character Replacement (at most k changes to make all same).
    - Minimum Window Substring (shortest containing all chars of t).
- **Code Snippet (Java — at most k distinct)**:
  ```java
  HashMap<Character, Integer> map = new HashMap<>();
  int left = 0, maxLen = 0;
  for (int right = 0; right < n; right++) {
      map.put(s.charAt(right), map.getOrDefault(s.charAt(right), 0) + 1);
      while (map.size() > k) {
          char ch = s.charAt(left);
          map.put(ch, map.get(ch) - 1);
          if (map.get(ch) == 0) map.remove(ch);
          left++;
      }
      maxLen = Math.max(maxLen, right - left + 1);
  }
  ```
    - For min window: Expand until valid, shrink while valid, update minLen inside shrink.
- **Example/Edge Case**: s = "eceba", k=2 (at most 2 distinct) → 3 ("ece").  
  Edge: k=0 → 0; empty s → 0; s shorter than t → "".
- **Pitfall**: Update max/min after while loop (valid state); remove from map when count=0; handle `formed` for min window.
- **Guiding Question**: How do you know when to shrink? (Answer: When condition violates, e.g., distinct > k, changes > k, sum > goal.)

### 3. Two Pointers (Opposite Direction)
- **Definition**: Start left=0, right=n-1, move towards center based on condition.
- **When to use**: Sorted array, max/min something (e.g., max area, min difference).
- **Key Steps**: Compute value, move the pointer that improves the condition (greedy choice).
- **Complexity**: Time O(n), Space O(1).
- **Questions we solved**:
    - Container With Most Water (max area = width × min(height)).
- **Code Snippet (Java)**:
  ```java
  int left = 0, right = height.length - 1, maxArea = 0;
  while (left < right) {
      maxArea = Math.max(maxArea, (right - left) * Math.min(height[left], height[right]));
      if (height[left] < height[right]) left++;
      else right--;
  }
  return maxArea;
  ```
- **Example/Edge Case**: [1,8,6,2,5,4,8,3,7] → 49.  
  Edge: [1,1] → 1; all decreasing → first and last; n=2 → 1 loop.
- **Pitfall**: Forgetting to update max inside loop; handling equal heights.
- **Guiding Question**: Why move smaller height? (Answer: Larger height stays, smaller is bottleneck — moving it might increase min height.)

### 4. Counting Valid Subarrays (Exactly / At-Most K Condition)
- **Definition**: Count subarrays with exactly k (odds, sum, distinct). Use prefix sum or at-most trick.
- **When to use**: "Number of subarrays with sum = goal", "exactly k odd numbers", "contain all three chars".
- **Key Steps**:
    - **Prefix sum**: Map of prefix → freq; count += freq[prefix - goal]
    - **At-most trick**: exactly k = atMost(k) - atMost(k-1)
        - atMost(k): sliding window with sum/odds ≤ k, count += (right - left + 1)
- **Complexity**: Time O(n), Space O(n) for map, O(1) for sliding window (binary case).
- **Questions we solved**:
    - Binary Subarrays With Sum (sum = goal).
    - Number of Substrings Containing All Three Characters (all a,b,c).
    - Number of Nice Subarrays (exactly k odds).
- **Code Snippet (Prefix Sum — Java)**:
  ```java
  Map<Integer, Integer> map = new HashMap<>();
  map.put(0, 1);
  int curr = 0, count = 0;
  for (int num : nums) {
      curr += num % 2;
      if (map.containsKey(curr - k)) count += map.get(curr - k);
      map.put(curr, map.getOrDefault(curr, 0) + 1);
  }
  ```
- **Code Snippet (At-Most Sliding Window — Java)**:
  ```java
  private int atMost(int[] nums, int k) {
      int left = 0, sum = 0, count = 0;
      for (int right = 0; right < nums.length; right++) {
          sum += nums[right] % 2;
          while (sum > k) {
              sum -= nums[left] % 2;
              left++;
          }
          count += (right - left + 1);
      }
      return count;
  }
  public int numberOfSubarrays(int[] nums, int k) {
      return atMost(nums, k) - atMost(nums, k - 1);
  }
  ```
- **Example/Edge Case**: nums = [1,1,2,1,1], k=3 (odds=3) → 2.  
  Edge: k=0 → subarrays with no odds; all evens → n(n+1)/2; k > n → 0.
- **Pitfall**: Initialize map with (0,1); handle k=0 separately in at-most; use long for count.
- **Guiding Question**: Why does atMost(k) - atMost(k-1) give exactly k? (Answer: Subtracts the subarrays with fewer than k.)

### Overall Structure for Solving Sliding Window / Two Pointers Problems
1. **Understand**: Rephrase problem — is it max/min length, count, contiguous?
2. **Brute Force**: O(n²) → compute complexity.
3. **Optimize**: Identify condition (sum, freq, distinct). Use window/map if needed.
4. **Edge Cases**: Empty array, n=1, all same, all different, k=0/large.
5. **Implement**: Start with pointers, update state, shrink when invalid.
6. **Analyze**: Time O(n), space O(1 or n). Explain why correct.
7. **Test**: Dry run small example, edge cases.

### Interview Tips & Common Pitfalls
- **Tip**: Ask: "Sorted? Negatives? Duplicates? Return length/substring/count?"
- **Tip**: Draw array with pointers, simulate 2-3 steps out loud.
- **Pitfall**: Off-by-1 (left/right indices, +1 in len).
- **Pitfall**: Not updating max/min in correct place (after while).
- **Pitfall**: Forgetting to remove from map when count=0.
- **Tip**: If binary (0/1), mention at-most trick for O(1) space.
- **Tip**: Practice explaining "why safe" for greedy choices (e.g., move smaller pointer, lazy maxFreq).

### Patterns to Remember
- **Max length with condition**: Expand, shrink when invalid, update maxLen.
- **Min length with condition**: Expand until valid, shrink while valid, update minLen inside shrink.
- **Count exactly k**: Prefix sum map or at-most(k) - at-most(k-1).
- **Opposite pointers**: For max/min in sorted-like (move based on condition).
- **Count containing all types**: Last occurrence + min(last) + 1 per right.
