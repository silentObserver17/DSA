# Subset / Knapsack DP — Revision Notes

## The Core Mental Model

Every problem in this family is built on one idea: **for each element, you either include it or exclude it.**

```
f(i, ...) = best/count/exists using elements 0..i
          = combine(include element i, exclude element i)
```

The "combine" step changes per problem variant:
- Existence → `include || exclude`
- Count → `include + exclude`
- Optimization → `Math.max(include, exclude)` or `Math.min(include, exclude)`

---

## Recurrence Template

```java
// Existence (Subset Sum)
return include || exclude;

// Count (Perfect Sum, Count Partitions)
return include + exclude;

// Max value (Knapsack, Rod Cutting)
return Math.max(include, exclude);

// Min count (Coin Change)
return Math.min(include, exclude);
```

---

## Base Case Derivation Rule

Always derive base cases by asking: **what are the terminal states of the recursion?**

Two terminal states always exist:
1. `index < 0` or `index == 0` — no elements left (or only the first one)
2. `target == 0` — goal already achieved (or `target < 0` — overshot)

**Order matters for counting problems:**
```java
// CORRECT for counting (handles index < 0 AND target == 0 simultaneously)
if (target == 0) return 1;
if (index < 0 || target < 0) return 0;

// WRONG for counting — misses the case where index < 0 AND target == 0
if (index < 0 || target < 0) return 0;  // fires first, returns 0 incorrectly
if (target == 0) return 1;
```

**For existence problems** the order doesn't matter (both return false/true consistently).

---

## Zero-Element Edge Case ⚠️

When array can contain `0`-valued elements, **never use early `target == 0` return** in existence problems:

```java
// WRONG — misses subsets that include zeros after target is hit
if (target == 0) return true;  // stops exploring; [0, 0, 1] with target=1 returns 1 instead of 4

// CORRECT — always go until index < 0
if (index < 0) return target == 0 ? 1 : 0;
```

Also in tabulation — start inner loop at `j = 0` for counting problems if zeros are possible, otherwise `dp[i][0]` won't be updated past the base case row.

---

## Impossible Sentinel for Minimization ⚠️

When minimizing (Coin Change), use `(int)1e9` instead of `Integer.MAX_VALUE`:

```java
// WRONG — overflow when you do 1 + Integer.MAX_VALUE
include = 1 + Integer.MAX_VALUE;  // becomes negative

// CORRECT
int include = (int) 1e9;
if (coins[i] <= target) {
    include = 1 + helper(coins, index, target - coins[i], dp);
}
```

Convert back to `-1` only at the very end:
```java
int result = helper(...);
return result == (int) 1e9 ? -1 : result;
```

---

## Space Optimization: Loop Direction

This is the most important pattern to remember:

| Variant | Loop Direction | Why |
|---|---|---|
| 0/1 Knapsack | Right → Left | Preserve previous row values; each item used at most once |
| Unbounded Knapsack | Left → Right | Intentionally read updated values; same item can be reused |

```java
// 0/1 — right to left
for (int j = W; j >= 0; j--) {
    dp[j] = Math.max(dp[j], val[i] + dp[j - wt[i]]);
}

// Unbounded — left to right
for (int j = 0; j <= W; j++) {
    dp[j] = Math.max(dp[j], val[i] + dp[j - wt[i]]);
}
```

**One-line summary:** Right-to-left = 0/1 (don't reuse). Left-to-right = Unbounded (reuse freely).

---

## Boolean Memo Pitfall

`boolean[][]` defaults to `false` — can't distinguish "not computed" from "computed false":

```java
// WRONG
boolean[][] dp = new boolean[n][target + 1];
if (dp[index][target]) return true;  // can't detect unvisited

// OPTION 1 — int with sentinel
int[][] dp = new int[n][target + 1];
Arrays.fill(dp[i], -1);
if (dp[index][target] != -1) return dp[index][target] == 1;

// OPTION 2 — Boxed Boolean (cleanest)
Boolean[][] dp = new Boolean[n][target + 1];  // defaults to null
if (dp[index][target] != null) return dp[index][target];
```

---

## Problem Map

### Subset Sum
```
f(i, target) = can elements 0..i form exactly target?
include = f(i-1, target - arr[i])
exclude = f(i-1, target)
return include || exclude

Base: target == 0 → true | index < 0 → false | target < 0 → false
```

### Partition Equal Subset Sum (LC 416)
```
Reduce to: Subset Sum with target = totalSum / 2
Guard: if (totalSum % 2 != 0) return false
```

### Perfect Sum / Count Subsets with Given Sum
```
f(i, target) = how many subsets of 0..i sum to target?
include = f(i-1, target - arr[i])
exclude = f(i-1, target)
return include + exclude

Base: if (index < 0) return target == 0 ? 1 : 0
NOTE: check target == 0 AFTER index < 0 check (swap order vs existence)
```

### Minimum Subset Sum Difference
```
Build full Subset Sum table with target = totalSum
Scan last row: for each achievable sum s,
    answer = min(abs((totalSum - s) - s))
```

### Count Partitions with Given Difference
```
Reduce to: Count Subsets with target = (totalSum + diff) / 2
Guards:
    if (diff > totalSum) return 0
    if ((totalSum + diff) % 2 != 0) return 0
```

**Derivation:**
```
S1 + S2 = totalSum
S1 - S2 = diff
→ S1 = (totalSum + diff) / 2
```

### 0/1 Knapsack
```
f(i, W) = max value using items 0..i with capacity W
exclude = f(i-1, W)
include = val[i] + f(i-1, W - wt[i])   if wt[i] <= W
return Math.max(include, exclude)

Base: index == 0 → if (wt[0] <= W) return val[0] else 0
Space opt: RIGHT TO LEFT
```

### Unbounded Knapsack
```
Same as 0/1 but include stays at same index:
include = val[i] + f(i, W - wt[i])    ← index i, not i-1

Base: index == 0 → (W / wt[0]) * val[0]   ← can take multiple times
Space opt: LEFT TO RIGHT
```

### Coin Change (LC 322) — minimize coins
```
f(i, amount) = min coins using denominations 0..i to make amount
exclude = f(i-1, amount)
include = 1 + f(i, amount - coins[i])   if coins[i] <= amount
return Math.min(include, exclude)

Base: index == 0 → amount % coins[0] == 0 ? amount/coins[0] : (int)1e9
Impossible sentinel: (int)1e9
Space opt: LEFT TO RIGHT (unbounded)
```

### Coin Change 2 (LC 518) — count ways
```
Same structure as Coin Change but:
return include + exclude   (counting, not minimizing)
Base: index == 0 → amount % coins[0] == 0 ? 1 : 0
No sentinel needed
Space opt: LEFT TO RIGHT (unbounded)
```

### Rod Cutting
```
f(i, N) = max profit cutting rod of length N using piece lengths 1..i+1
Weight of piece at index i = i + 1   (derived from index, no separate wt[])
exclude = f(i-1, N)
include = price[i] + f(i, N - (i+1))   if (i+1) <= N
return Math.max(include, exclude)

Base: index == 0 → N * price[0]   (length-1 piece, always divisible)
Space opt: LEFT TO RIGHT (unbounded)
GOTCHA: j - (i+1), not j - i+1 (operator precedence!)
```

---

## Tabulation Base Case Patterns

### 0/1 family (boolean/count)
```java
// dp[i][0] = true/1 for all i (empty subset sums to 0)
for (int i = 0; i < n; i++) dp[i][0] = true;

// dp[0][j] — only first element available
if (arr[0] <= target) dp[0][arr[0]] += 1;  // += for counting (handles zeros)
// Fill rest of row 0 with false/0
```

### 0/1 Knapsack
```java
// dp[0][j] — only first item available
for (int j = 0; j <= W; j++) {
    if (wt[0] <= j) dp[0][j] = val[0];
}
```

### Unbounded variants
```java
// dp[0][j] — first item, can take multiple times
for (int j = 0; j <= W; j++) {
    if (wt[0] <= j) dp[0][j] = (j / wt[0]) * val[0];
}

// Coin Change — with impossible sentinel
for (int j = 0; j <= amount; j++) {
    dp[0][j] = (j % coins[0] == 0) ? j / coins[0] : (int) 1e9;
}
```

---

## Common Gotchas

1. **Operator precedence:** `j - i+1` ≠ `j - (i+1)`. Always parenthesize.
2. **Loop start for counting:** Start `j = 0` if zeros possible in array; `j = 1` safe only for all-positive inputs.
3. **`dp[n]` vs `dp[amount]`:** Your 1D array has size `amount+1`, max valid index is `amount`. Never `dp[n]`.
4. **Memoization collision with `-1`:** Don't use `-1` as sentinel if valid answer can be `-1`. Use `(int)1e9` for minimization.
5. **`include` without adding value:** In Knapsack, `include = val[i] + f(...)`, not just `f(...)`. Easy to forget under copy-paste.

---

## Complexity

| Problem | Time | Space (2D) | Space (Optimized) |
|---|---|---|---|
| Subset Sum | O(n × target) | O(n × target) | O(target) |
| 0/1 Knapsack | O(n × W) | O(n × W) | O(W) |
| Unbounded Knapsack | O(n × W) | O(n × W) | O(W) |
| Coin Change | O(n × amount) | O(n × amount) | O(amount) |

All memoized solutions have the same TC as tabulation — each `(index, target)` cell computed once, O(1) work per cell.

---

## What To Skip (For Now)

**LC 2035 (Partition Array Into Two Arrays to Minimize Sum Difference)** — looks like subset DP but requires **meet-in-the-middle** because:
- Both partitions must have exactly equal length `n`
- Array contains negative numbers (can't use sum as array index)

Come back to this after: String DP → Interval DP → Bitmask DP → Meet-in-the-Middle.

**Signal for meet-in-the-middle:** constraints like `n ≤ 30–40` with no clean DP recurrence.
