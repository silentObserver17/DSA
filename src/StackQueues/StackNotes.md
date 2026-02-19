**Comprehensive Revision Notes**  
**Monotonic Stack & Advanced Stack Problems**
---

### PART 1: Structured Notes for Key Problems

#### 1. Monotonic Stack Pattern (Core Foundation)

**Problem Statement Summary**  
Maintain a stack where elements are in strictly increasing or decreasing order to efficiently answer "nearest greater/smaller" or "contribution" queries.

**Core Intuition**  
The stack only keeps **useful candidates** for future elements.  
Useless candidates (that can never be answers) are popped immediately.

**When to use Increasing vs Decreasing Stack**

| Goal                              | Stack Order (bottom → top) | Pop Condition                  | Typical Problems                     |
|-----------------------------------|----------------------------|--------------------------------|--------------------------------------|
| Next Greater / Max contribution   | Decreasing                 | top ≤ current                  | Next Greater, Daily Temperatures     |
| Next Smaller / Min contribution   | Increasing                 | top ≥ current                  | Next Smaller, Sum Subarray Mins      |
| Smallest number (greedy removal)  | Non-decreasing             | top > current                  | Remove K Digits                      |
| Largest Rectangle / Trapping Water| Increasing                 | top ≥ current                  | Largest Rectangle, Trapping Rain     |

**Key Tricks**
- Always store **indices**, not values (for width/distance calculation)
- Use `>=` or `<=` carefully to handle **ties/plateaus**
- Each element is pushed and popped **at most once** → O(n) time

---

#### 2. Stock Span / Daily Temperatures

**Problem Summary**  
For each day, find how many consecutive previous days (Stock Span) or future days (Daily Temperatures) satisfy the condition.

**Core Intuition**  
Find the **nearest previous greater** (Stock Span) or **nearest next greater** (Daily Temperatures) element.  
The answer is the distance to that boundary.

**Optimal Approach**  
Monotonic **decreasing** stack of indices (right-to-left for next greater, left-to-right for previous greater).

**Time & Space**  
O(n) time, O(n) space

**Common Mistakes**
- Using `<` instead of `<=` (wrong on equal values)
- Forgetting to handle `prev = -1` case

**Edge Cases**
- All equal prices → span = [1,2,3,...,n]
- Strictly decreasing → all spans = 1
- Strictly increasing → all next greater = 0

**When to use**  
Any "nearest greater/smaller" or "consecutive count until condition breaks" problem.

---

#### 3. Largest Rectangle in Histogram

**Problem Summary**  
Given bar heights, find the largest rectangle area that can be formed.

**Core Intuition**  
For each bar i, find the maximum width where it can be the **smallest bar** (bottleneck).

**Optimal Approach**  
Find **nearest smaller left** and **nearest smaller right** using monotonic **increasing** stack.  
Area[i] = height[i] × (right[i] − left[i] − 1)

**Time & Space**  
O(n) time, O(n) space

**Key Tricks**
- Use `>=` in pop condition to handle equal heights correctly
- Process remaining bars in stack at the end (right boundary = n)
- Width formula: `right - left - 1`

**Common Mistakes**
- Using strict `<` on both sides → wrong width on equal heights
- Forgetting to process remaining stack after loop

**Edge Cases**
- All bars equal → max area = n × height
- All bars strictly increasing/decreasing
- Single bar, all zeros

---

#### 4. Trapping Rain Water (All Approaches)

**Problem Summary**  
Compute total water trapped between bars.

**1. Brute Force**  
For each i, find max left & max right → water = min(l,r) - height[i]

**2. Prefix + Suffix Max**  
Precompute leftMax[] and rightMax[] → O(n) time, O(n) space

**3. Two Pointers** (Best space)  
Move pointer with smaller max height → O(n) time, O(1) space

**4. Monotonic Stack**  
Increasing stack → calculate trapped water when popping

**Key Intuition**  
Water at position i is limited by the **smaller of the tallest bar on left and right**.

**Common Mistakes**
- Not clamping water to ≥ 0
- Wrong pop condition in stack version (`<` vs `<=`)

**When to use which**
- Two Pointers → when interviewer asks for O(1) space
- Monotonic Stack → when they want pattern reuse (same as histogram)

---

#### 5. Sliding Window Maximum

**Problem Summary**  
Return max in every window of size k.

**Core Intuition**  
Maintain a **decreasing deque** of indices.  
Front is always the index of maximum in current window.

**Optimal Approach**
- Remove out-of-window from front
- Remove smaller/equal from back
- Front is answer when window size == k

**Time & Space**  
O(n) time, O(k) space

**Key Trick**  
Use `<=` when popping from back to keep deque small.

---

#### 6. LFU Cache (Advanced)

**Problem Summary**  
Evict least frequently used key. On tie, evict least recently used.

**Core Structure**
- `keyMap`: key → Node (value + freq)
- `freqMap`: freq → DoublyLinkedList (LRU order)
- `minFreq` tracker

**Key Operations (O(1))**
- On get/put: move node to freq+1 list
- On eviction: remove LRU from `minFreq` list

**Your Implementation Highlights**
- Correct `updateFreq` with `minFreq` increment logic
- Proper LRU using `addFront` + `removeLast`
- Handles capacity = 0

---

### PART 2: Conceptual Deep Dive

#### Why These Algorithms Work

**Monotonic Stack Invariant**  
The stack always maintains candidates in sorted order (increasing/decreasing).  
Any element that violates this order can never be useful for future queries → safe to pop.

**Geometric Intuition (Histogram / Rain Water)**  
Each bar tries to be the **height** of a rectangle.  
We find the maximum width where it can be the **shortest bar** (bottleneck).

**Why >= instead of > ?**
- `>` only handles strictly taller/shorter
- `>=` handles **plateaus** (equal heights)
- Without `>=`, equal heights cause wrong width calculation (as you correctly pointed out in [2,2,2] example)

**Deriving Optimal from Brute Force**

| Problem                     | Brute Force Idea                    | Optimization Insight                     |
|----------------------------|-------------------------------------|------------------------------------------|
| Next Greater               | Scan right for each i               | Keep decreasing candidates in stack      |
| Largest Rectangle          | All subarrays → min height          | For each bar, find max width as min      |
| Trapping Rain Water        | For each i, max left & right        | Precompute or maintain running max       |
| LFU Cache                  | Scan all keys by freq               | Group by frequency + LRU per group       |

**Shared Patterns Across Problems**

1. **Boundary Finding** — nearest greater/smaller (Stock Span, Daily Temperatures, Histogram)
2. **Contribution Technique** — how much each element contributes (Sum Subarray Mins/Maxs)
3. **Elimination Technique** — remove impossible candidates (Celebrity Problem)
4. **Greedy Removal** — remove elements that hurt optimality (Remove K Digits)
5. **Two-Level Structure** — key → node + frequency → list (LFU Cache)

---
