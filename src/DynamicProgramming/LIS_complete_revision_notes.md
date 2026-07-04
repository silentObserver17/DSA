# DP on LIS Family — Complete Revision Notes

## Core Engine (USE THIS BY DEFAULT)
`dp[i]` = best answer ending at index `i`
- Initialize all `dp[i]` = base value (1 for length, `arr[i]` for sum)
- For each `i`, look back at all `j < i` where condition holds
- Answer = max of entire `dp` array
- **Prefer this over 2D formulation** — works for count, sum, and print

---

## Two Formulations Compared

| | 2D `dp[index][prevIndex]` | 1D `dp[i]` ending at i |
|---|---|---|
| Direction | Right to left (depends on `index+1`) | Left to right (depends on `j < i`) |
| State | Carries `prevIndex` as dimension | Explicit inner loop over all `j` |
| Shift trick | `-1` sentinel → `prevIndex+1` for table | Not needed |
| Printing | Awkward | Natural with `parent[]` |
| Default? | Learning exercise only | ✅ Use this in interviews |

---

## Problems Covered

### 1. Core LIS Count — All 4 Stages

**Recursive/Memo (2D formulation):**
- State: `f(index, prevIndex)` — current index and last picked index
- `prevIndex = -1` sentinel for "nothing picked yet"
- Memo table: `dp[n][n+1]` — shift `prevIndex+1` for table access
- Array access: raw `prevIndex`; table access: `prevIndex + 1`
- Include guard: `prevIndex == -1 || arr[index] > arr[prevIndex]`
- Include: `1 + f(index+1, index)` → table: `dp[index+1][index+1]`
- Exclude: `f(index+1, prevIndex)` → table: `dp[index+1][prevIndex+1]`

**Tabulation:**
- Table size: `(n+1) x (n+1)` — extra row for base case at `index = n`
- Outer: `index` from `n-1` to `0`; Inner: `prev` from `0` to `n` (shifted)
- Unshift inside loop: `prevIndex = prev - 1`
- Base case: `dp[n][anything] = 0` (free from Java init)

**Space Optimization:**
- Two arrays: `curr[]` and `next[]`
- `dp[index+1][...]` → `next[...]`, `dp[index][...]` → `curr[...]`

**O(n log n) Binary Search:**
- `tails[i]` = smallest tail of any IS of length `i+1`
- `tails` is always sorted (proof: length `i+1` IS extends length `i` IS → tail is strictly larger)
- For each element `x`: binary search for first element `>= x`
  - Found → replace (better tail, same length)
  - Not found → append (new longer IS)
- Answer = `size` of tails at end
- ⚠️ `tails` does NOT represent actual LIS — use DP for printing

---

### 2. Print LIS
**Formulation:** `dp[i]` = LIS length ending at `i` + `parent[i]` = predecessor index

```
Initialize: dp[i] = 1, parent[i] = -1
Update: if(arr[i] > arr[j] && dp[j] + 1 > dp[i])
            dp[i] = dp[j] + 1;
            parent[i] = j;
```

**Reconstruction:**
1. Find `endIndex` = index with max `dp[i]`
2. Trace back via `parent` until `-1`
3. Reverse collected elements

---

### 3. Largest Divisible Subset (LC 368)
- Sort array first
- Condition: `nums[i] % nums[j] == 0` (replaces `arr[i] > arr[j]`)
- Everything else identical to Print LIS (parent array + traceback)
- TC: O(n²), SC: O(n)

---

### 4. Longest String Chain (LC 1048)
- Sort by **word length**: `Arrays.sort(words, Comparator.comparingInt(String::length))`
- Condition: `isPredecessor(words[j], words[i])`
- `isPredecessor(a, b)`:
  - Guard: `if(a.length() + 1 != b.length()) return false`
  - Two pointers: scan both strings, allow exactly one skip in `b`
  - Return `i == a.length()` at end
- TC: O(n² * L), SC: O(n)

---

### 5. Longest Bitonic Subsequence
- `lis[i]` = LIS length ending at `i` (left to right pass)
- `lds[i]` = LDS length starting at `i` (right to left pass — same engine, reversed)
- Combine: `lis[i] + lds[i] - 1` at each index (subtract 1 — peak counted twice)
- Answer = max across all indices
- TC: O(n²), SC: O(n)

---

### 6. Number of Longest Increasing Subsequences (LC 673)
**Two arrays:** `dp[i]` = LIS length ending at `i`, `count[i]` = number of such LIS

**Update rules (when `nums[i] > nums[j]`):**
- `dp[j] + 1 > dp[i]` → found longer: `dp[i] = dp[j] + 1`, `count[i] = count[j]` (reset)
- `dp[j] + 1 == dp[i]` → found equal: `count[i] += count[j]` (accumulate)

**Final answer:**
1. Find `maxLen = max(dp[i])`
2. Sum `count[i]` for all `i` where `dp[i] == maxLen`

TC: O(n²), SC: O(n)

---

### 7. Maximum Sum Increasing Subsequence
- `dp[i]` = max sum of IS ending at `i`
- Initialize: `dp[i] = nums[i]`
- Update: `if(nums[i] > nums[j] && dp[j] + nums[i] > dp[i]) dp[i] = dp[j] + nums[i]`
- Answer = max of `dp` array
- TC: O(n²), SC: O(n)

---

### 8. Russian Doll Envelopes (LC 354) — HARD
**Key insight:** Sort by width ascending, same width → height **descending**. Then run LIS on heights only.

**Why descending for same width?**
- Same-width envelopes can't nest (need strictly less)
- Descending heights ensures LIS can only pick one from each width group
- Without this trick, LIS would incorrectly pick multiple same-width envelopes

**Sort comparator:**
```java
Arrays.sort(envelopes, (a, b) -> {
    if(a[0] == b[0]) return Integer.compare(b[1], a[1]); // height desc
    return Integer.compare(a[0], b[0]); // width asc
});
```

**Then:** O(n log n) binary search LIS on heights (O(n²) TLEs on LC)

TC: O(n log n), SC: O(n)

---

## Key Learnings

### Shift Abstraction (2D formulation)
- `-1` sentinel requires `+1` index shift everywhere
- Memo: shift hidden inside recursive call
- Tabulation: shift must be applied explicitly
- Iterate shifted values, unshift inside: `prevIndex = prev - 1`

### Tabulation Direction
Direction follows **dependencies**, not memoization:
- 2D formulation needs `index+1` → right to left
- 1D `dp[i]` needs all `j < i` → left to right

### Engine Reuse Pattern
Entire LIS family = same `dp[i]` engine + surgical change:
| Problem | Condition | `dp[i]` stores | Init |
|---|---|---|---|
| LIS Count | `arr[i] > arr[j]` | length | 1 |
| Max Sum IS | `arr[i] > arr[j]` | sum | `arr[i]` |
| Divisible Subset | `nums[i] % nums[j] == 0` | length | 1 |
| String Chain | `isPredecessor(j, i)` | length | 1 |
| Bitonic | two passes | length | 1 |
| Count LIS | `arr[i] > arr[j]` | length + count[] | 1 |
| Russian Doll | sort trick + height LIS | length | 1 |

### Count Array Update Rules (LC 673)
- New max found → **reset** `count[i] = count[j]`
- Equal length found → **accumulate** `count[i] += count[j]`
- Never `count[j] + 1` — `count[j]` already includes all ways to reach `j`

---
