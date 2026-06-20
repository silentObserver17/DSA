# Arrays Revision Roadmap

## Level 1 — Fundamentals

### 1. Move Zeroes

**Pattern:** Two pointers
**Goal:** Move all zeroes to the end while maintaining order.

Example:

```
Input:
[0,1,0,3,12]

Output:
[1,3,12,0,0]
```

Requirements:

* In-place
* O(1) space

---

### 2. Remove Duplicates From Sorted Array

**Pattern:** Slow/Fast pointer

Example:

```
Input:
[1,1,2,2,3,4,4]

Output:
length = 4
Array:
[1,2,3,4]
```

Requirements:

* Modify in-place

---

### 3. Best Time To Buy And Sell Stock

**Pattern:** Running minimum

Example:

```
Input:
[7,1,5,3,6,4]

Output:
5
```

Logic:

```
buy = 1
sell = 6
profit = 5
```

---

# Level 2 — Hashing / Frequency

### 4. Two Sum

**Pattern:** HashMap lookup

Example:

```
nums = [2,7,11,15]
target = 9

Output:
[0,1]
```

---

### 5. Majority Element

**Pattern:** Frequency / Boyer Moore

Example:

```
Input:
[2,2,1,1,1,2,2]

Output:
2
```

Condition:

```
element frequency > n/2
```

---

### 6. Contains Duplicate

**Pattern:** HashSet

Example:

```
Input:
[1,2,3,1]

Output:
true
```

---

# Level 3 — Prefix Sum / Subarrays

### 7. Maximum Subarray

**Pattern:** Kadane Algorithm

Example:

```
Input:
[-2,1,-3,4,-1,2,1,-5,4]

Output:
6
```

Subarray:

```
[4,-1,2,1]
```

---

### 8. Subarray Sum Equals K

**Pattern:** Prefix Sum + HashMap

Example:

```
nums = [1,2,3]
k = 3

Output:
2
```

Subarrays:

```
[1,2]
[3]
```

---

# Level 4 — Two Pointer

### 9. Container With Most Water

**Pattern:** Two pointers

Example:

```
height =
[1,8,6,2,5,4,8,3,7]

Output:
49
```

---

### 10. 3Sum

**Pattern:** Sorting + Two pointers

Example:

```
nums =
[-1,0,1,2,-1,-4]

Output:
[
 [-1,-1,2],
 [-1,0,1]
]
```

---

# Level 5 — Sliding Window

### 11. Longest Substring Without Repeating Characters

**Pattern:** Variable sliding window

Example:

```
Input:
"abcabcbb"

Output:
3
```

Longest:

```
abc
```

---

### 12. Maximum Sum Subarray Of Size K

**Pattern:** Fixed sliding window

Example:

```
arr = [2,1,5,1,3,2]
k = 3

Output:
9
```

Subarray:

```
[5,1,3]
```

---

# Level 6 — Binary Search

### 13. Search In Rotated Sorted Array

**Pattern:** Modified binary search

Example:

```
nums =
[4,5,6,7,0,1,2]

target = 0

Output:
4
```

---

### 14. Find Peak Element

**Pattern:** Binary search on answer

Example:

```
nums =
[1,2,3,1]

Output:
2
```

---

# Level 7 — Intervals

### 15. Merge Intervals

**Pattern:** Sorting intervals

Example:

```
Input:

[
[1,3],
[2,6],
[8,10],
[15,18]
]


Output:

[
[1,6],
[8,10],
[15,18]
]
```

---

# Level 8 — Advanced Array Patterns

### 16. Product Of Array Except Self

**Pattern:** Prefix + Suffix

Example:

```
Input:
[1,2,3,4]

Output:
[24,12,8,6]
```

Rules:

* No division
* O(1) extra space

---

### 17. Trapping Rain Water

**Pattern:** Two pointers / Prefix maximum

Example:

```
Input:

[0,1,0,2,1,0,1,3,2,1,2,1]

Output:
6
```

---

### 18. Next Permutation

**Pattern:** Array manipulation

Example:

```
Input:
[1,2,3]

Output:
[1,3,2]
```

---

# Recommended Solve Order

```
1. Move Zeroes
2. Stock Buy Sell
3. Kadane
4. Subarray Sum K
5. Container With Most Water
6. 3Sum
7. Sliding Window
8. Rotated Binary Search
9. Merge Intervals
10. Product Except Self
11. Trapping Rain Water
12. Next Permutation
```

This order rebuilds the important array patterns progressively:

```
Traversal
   ↓
Two Pointer
   ↓
Hashing
   ↓
Prefix Sum
   ↓
Sliding Window
   ↓
Binary Search
   ↓
Advanced Manipulation
```

This should work nicely as your array revision checklist.
