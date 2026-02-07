### Binary Search Revision Notes – Summary

#### 1. Classic / Standard Binary Search Patterns

| Pattern                          | Key Idea / Template                              | Typical Questions we touched / similar | Time       | Common Mistakes / Tips                          |
|----------------------------------|--------------------------------------------------|----------------------------------------|------------|-------------------------------------------------|
| Standard BS on sorted array      | Find target, insertion point                     | Search Insert Position                 | O(log n)   | off-by-one, mid calculation overflow            |
| Lower bound / first ≥ target     | Keep updating when ≥ → go left                   | First occurrence, lower_bound          | O(log n)   | initialize ans = n, not -1                      |
| Upper bound / first > target     | Keep updating when > → go left                   | Last occurrence + 1                    | O(log n)   | same as above                                   |
| First & Last occurrence          | lower_bound + upper_bound - 1                    | First and Last Position of Element     | O(log n)   | forget to check nums[lb] == target              |

**Most important template (Java/Go style):**

```java
int left = 0, right = n;
while (left < right) {
    int mid = left + (right - left) / 2;
    if (condition(mid)) {   // usually nums[mid] >= target / > target
        right = mid;
    } else {
        left = mid + 1;
    }
}
return left;
```

#### 2. Rotated Sorted Array Family

| Variant                               | Key Insight                                              | Questions we solved/discussed                     | Time       | Pitfall / Tip                                  |
|---------------------------------------|----------------------------------------------------------|---------------------------------------------------|------------|------------------------------------------------|
| Find minimum in rotated (no dup)      | nums[mid] > nums[high] → min in right                    | Find Minimum in Rotated Sorted Array              | O(log n)   | high = mid (not mid-1) in else branch          |
| Find minimum in rotated (with dup)    | when equal → high-- (worst O(n))                         | Find Minimum II                                   | O(n) worst | must shrink only one side when equal           |
| Rotation count                        | index of minimum element                                 | How many times array rotated                      | O(log n)   | same as min without dup                        |
| Search in rotated (no dup)            | decide which half is sorted                              | Search in Rotated Sorted Array                    | O(log n)   | compare mid with left/right end                |
| Search in rotated (with dup)          | similar but shrink when equal                            | Search in Rotated Sorted Array II                 | O(n) worst | same as min with dup                           |

#### 3. Binary Search on Answer (Minimize / Maximize something)

| Pattern / Question                            | What we binary search on     | Feasibility check                             | Time           | Key Insight / Template                         |
|-----------------------------------------------|------------------------------|-----------------------------------------------|----------------|------------------------------------------------|
| Koko Eating Bananas                           | speed k                      | total hours needed ≤ h                        | O(n log M)     | ceil(pile / k), minimize k                     |
| Aggressive Cows / Painter's Partition         | min distance D               | can place k cows with min dist ≥ D            | O(n log D)     | greedy placement from left, maximize D         |
| Book Allocation / Split Array Largest Sum     | max pages per student        | can assign with no student > limit            | O(n log S)     | greedy pack, minimize max                      |
| Minimum Days to Make m Bouquets               | day d                        | can make m bouquets of k adjacent ≤ d         | O(n log D)     | consecutive count & reset, minimize d          |
| Minimize Max Distance to Gas Stations         | max gap D                    | can place ≤ k new stations so all gaps ≤ D    | O(n log D)     | ceil(gap/D)-1 per existing gap, floating-point |

**Core template (minimize):**

```java
low = min_possible, high = max_possible
while (low < high) {
    mid = low + (high - low)/2;
    if (possible(mid)) {
        high = mid;          // try smaller
    } else {
        low = mid + 1;
    }
}
return low;
```

For **maximize** → swap the branch: `low = mid + 1` when possible.

### 4. 2D Matrix Binary Search

| Problem                              | Key Property                              | Method                          | Time       | Core Trick / Insight                              |
|--------------------------------------|-------------------------------------------|---------------------------------|------------|---------------------------------------------------|
| Search a 2D Matrix (LC 74)           | row sorted + next row > prev row end      | 1D virtual binary search        | O(log(mn)) | mid / cols , mid % cols mapping                   |
| Search a 2D Matrix II (LC 240)       | row sorted + column sorted                | Staircase from top-right        | O(m+n)     | eliminate row or column per step                  |
| Kth Smallest in Sorted Matrix        | row sorted (often column too)             | Binary search on value          | O(rows × log cols × log MAX) | count ≤ mid using row-wise upper bound            |
| Median of Row-wise Sorted Matrix     | each row sorted                           | Binary search on value + count  | same       | find ((mn+1)/2)-th smallest                       |

### 5. Other useful BS patterns we touched indirectly

- Single Element in Sorted Array (LC 540) → pairing property on indices (even/odd)
- Find Peak Element (LC 162) → check neighbors, go to rising side
- Capacity To Ship Packages Within D Days → minimize capacity (very similar to book allocation)

### Quick Revision Checklist (before any BS interview)

- [ ] Can write lower_bound & upper_bound template blind
- [ ] Know how to handle duplicates (first/last occurrence, rotated with dup)
- [ ] Can decide low/high initialization for “minimize maximum” vs “maximize minimum”
- [ ] Remember ceil(a/b) = (a + b - 1)/b integer trick
- [ ] Can map 1D index → 2D row/col
- [ ] Know staircase method for 240
- [ ] Can implement count ≤ mid in row-sorted matrix
- [ ] Understand when to use (total+1)/2 vs total/2 for median/kth

---

Here are clear, concise revision notes specifically for the **Quick Revision Checklist** points you listed.  
These are written in a compact, interview-ready format — easy to scan, memorize, and recall during a quick pre-interview review.

### Quick Binary Search Revision Checklist – Detailed Notes

#### 1. Can write lower_bound & upper_bound template blind

**Lower Bound** (first position ≥ target)

```java
int lowerBound(int[] arr, int target) {
    int left = 0;
    int right = arr.length;           // note: right = n (not n-1)

    while (left < right) {
        int mid = left + (right - left) / 2;

        if (arr[mid] >= target) {
            right = mid;              // try to find even smaller index
        } else {
            left = mid + 1;
        }
    }
    return left;                      // returns insertion point or n if not found
}
```

**Upper Bound** (first position > target)

```java
int upperBound(int[] arr, int target) {
    int left = 0;
    int right = arr.length;

    while (left < right) {
        int mid = left + (right - left) / 2;

        if (arr[mid] > target) {
            right = mid;
        } else {
            left = mid + 1;
        }
    }
    return left;
}
```

**First occurrence** = lowerBound (if arr[result] == target)  
**Last occurrence** = upperBound(arr, target) - 1 (if valid)

**Tip**: Always initialize `right = arr.length` (allows returning n when target > all elements)

#### 2. How to handle duplicates (first/last occurrence, rotated with dup)

**First & Last occurrence** (sorted array with duplicates)

- First = lower_bound(arr, target) → if arr[first] == target else -1
- Last  = upper_bound(arr, target) - 1 → if arr[last] == target else -1

**Rotated sorted array with duplicates**

- Find min: when nums[mid] == nums[high] → high-- (worst case O(n))
- Search target: same — when equal → high-- (cannot decide which side is sorted)

**Key insight**: Duplicates destroy the "which half is sorted" guarantee → we can only safely shrink one side.

**Common question**: Why not left++ instead of high--?  
→ Because left is usually the pivot reference — shrinking right is safer.

#### 3. Low/high initialization for “minimize maximum” vs “maximize minimum”

| Goal                          | When feasible (possible) do... | When not feasible do... | Typical low initialization          | Typical high initialization          |
|-------------------------------|---------------------------------|--------------------------|-------------------------------------|---------------------------------------|
| Minimize maximum (Koko, book allocation, split array) | high = mid                     | low = mid + 1           | smallest possible value (1, max(arr)) | largest possible value (sum, max(arr)) |
| Maximize minimum (aggressive cows, gas stations max gap) | low = mid + 1                 | high = mid - 1          | smallest possible value (1, 0)      | largest possible value (max-min, max gap) |

**Remember the direction flip** — it's the most common mistake in interviews.

**Mnemonic**:
- Want smallest possible → shrink from right when possible
- Want largest possible → shrink from left when possible

#### 4. Remember ceil(a/b) = (a + b - 1)/b integer trick

**When to use**:  
Whenever you need to compute how many groups/segments/pieces needed (e.g. bouquets, gas stations, pages per student)

**Formula** (a and b positive integers):

```java
int segments = (a + b - 1) / b;
int new_items_needed = segments - 1;     // most common use
```

Examples:

- a = 10, b = 3 → (10 + 3 - 1)/3 = 12/3 = 4 segments → 3 new stations
- a = 9, b = 3 → (9+3-1)/3 = 11/3 = 3 segments → 2 new
- a = 1, b = 1 → 1 segment → 0 new

**Pitfall**: Forgetting -1 when calculating new items/stations.

#### 5. Can map 1D index → 2D row/col (LC 74)

**Virtual flattened array mapping**:

```java
int rows = matrix.length;
int cols = matrix[0].length;
int total = rows * cols;

int mid = ... // from 0 to total-1

int row = mid / cols;
int col = mid % cols;

value = matrix[row][col];
```

**Why / cols and % cols?**  
Because each row has `cols` elements — the column is the inner (fastest changing) dimension.

**Pitfall**: Dividing by rows instead of cols → completely wrong mapping unless square matrix.

**Edge**: mid = 0 → (0,0), mid = cols → (1,0), mid = total-1 → (rows-1, cols-1)

#### 6. Know staircase method for LC 240

**Top-right start** (most common)

```java
int row = 0;
int col = matrix[0].length - 1;

while (row < matrix.length && col >= 0) {
    if (matrix[row][col] == target) return true;
    if (matrix[row][col] > target) {
        col--;      // discard column (everything below is larger)
    } else {
        row++;      // discard row (everything left is smaller)
    }
}
return false;
```

**Bottom-left alternative**: row = m-1, col = 0 → row-- when > target, col++ when < target

**Why it works**: From corner, one direction is always smaller, one is always larger → can eliminate whole row or column each time.

**Time**: O(rows + cols) — best possible without extra space.

#### 7. Can implement count ≤ mid in row-sorted matrix

**For one row** (upper bound):

```java
private int countLessEqualInRow(int[] row, int val) {
    int left = 0;
    int right = row.length;

    while (left < right) {
        int m = left + (right - left) / 2;
        if (row[m] <= val) {
            left = m + 1;
        } else {
            right = m;
        }
    }
    return left;   // first index > val → count of ≤ val
}
```

**In matrix**:

```java
int count = 0;
for (int[] row : matrix) {
    count += countLessEqualInRow(row, mid);
}
```

**Used for**: k-th smallest, median in row-sorted matrix

#### 8. Understand when to use (total+1)/2 vs total/2 for median/kth

| Case              | Total elements | k position (1-based)       | Use this k                           | Returns what                                 |
|-------------------|----------------|-----------------------------|--------------------------------------|----------------------------------------------|
| Odd               | 2t+1           | t+1                         | (total + 1)/2                        | Exact middle element                         |
| Even              | 2t             | t and t+1                   | total/2 and total/2 + 1              | Lower middle & upper middle → average        |

**Most problems** guarantee odd number of elements → use (total + 1)/2  
**General case** (even allowed) → compute both and average.

**Pitfall**: Using (total + 1)/2 for even case → gets lower middle only → wrong median.

---
