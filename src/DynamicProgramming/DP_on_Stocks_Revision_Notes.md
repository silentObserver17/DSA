# DP on Stocks — Revision Notes

## 1. Core Theory

Every stock problem revolves around three actions on each day: **buy**, **sell**, or **rest/skip**. The single piece of state that determines which actions are *legal* on a given day is:

> **Do I currently hold a stock?**

This is captured by a variable `buy`:
- `buy = 1` → free to buy (not holding)
- `buy = 0` → free to sell (holding)

The base state for the whole family is `f(index, buy)` = max profit obtainable from day `index` onward, given current `buy` status. Different problems add or modify this state to encode their specific constraint.

**Convention used throughout:** left-to-right recursion (`f(i+1, ...)`), not right-to-left. Base case is `index >= n` → return `0`.

---

## 2. LC 121 — Best Time to Buy and Sell Stock (1 transaction)

**Constraint encoding:** No new state variable needed. After selling, simply **don't recurse** — return `prices[index]` directly instead of `prices[index] + f(index+1, 1)`. This forces exactly one transaction.

**Recurrence:**
- `buy == 1`: `max(-prices[i] + f(i+1, 0), f(i+1, 1))`
- `buy == 0`: `max(prices[i], f(i+1, 0))` ← no further recursion on sell

**States:** `(index, buy)` → `2n` states → `dp[n][2]`

**Space optimization:** Two `int[2]` arrays (`curr`, `next`), or 4 scalar variables.

---

## 3. LC 122 — Best Time to Buy and Sell Stock II (Unlimited transactions)

**Only change from LC 121:** sell branch recurses again with `buy=1`, allowing future transactions.

```
sellToday = prices[i] + f(i+1, 1)
```

Everything else (state space, dimensions, space optimization) is identical to LC 121.

---

## 4. LC 123 — Best Time to Buy and Sell Stock III (At most 2 transactions)

**New state variable:** `transactions` — counted **on sell** (a transaction = one buy+sell pair completed).

**State:** `(index, buy, transactions)` → 3D: `dp[n+1][2][3]` (transactions ∈ {0,1,2})

**Base cases:** `index == n` **or** `transactions == 2` → return `0`. Both handled for free by Java's default array init to `0` in tabulation.

**Recurrence:**
- `buy == 1`: `max(-prices[i] + f(i+1, 0, t), f(i+1, 1, t))`
- `buy == 0`: `max(prices[i] + f(i+1, 1, t+1), f(i+1, 0, t))` ← **transactions increments only on sell**

**Common bug:** In tabulation, forgetting to write `transactions + 1` on the sell branch (writing `dp[i+1][1][t]` instead of `dp[i+1][1][t+1]`) silently breaks the transaction count and inflates the answer.

**Tabulation loop bound:** `transactions` loops `0` to `< 2` only (not `<= 2`), since `dp[...][...][2]` must stay `0` (base case).

**Return value:** `dp[0][1][0]` — start at day 0, free to buy, 0 transactions used.

**Space optimization:** `curr`/`next` become `int[2][3]` 2D arrays (one extra dimension vs. LC 121/122).

---

## 5. LC 188 — Best Time to Buy and Sell Stock IV (At most k transactions)

**Generalization of LC 123:** Replace the constant `2` with parameter `k` everywhere.

**State:** `(index, buy, transactions)` → `dp[n+1][2][k+1]` (transactions ∈ {0,...,k})

**Only changes from LC 123:**
- Array size: `k+1` instead of `3`
- Base case: `transactions == k` instead of `== 2`
- Loop bound: `transactions < k`

**Recurrence formulas are unchanged** — only boundaries scale with `k`.

**Space optimization:** Same as LC 123 but `int[2][k+1]`.

---

## 6. LC 309 — Best Time to Buy and Sell Stock with Cooldown

**Constraint encoding:** No new state variable — encoded via an **index jump**. After selling, the next buy-eligible day is `index + 2` (skip one cooldown day), not `index + 1`.

```
sellToday = prices[i] + f(i+2, 1)
```

**Base case must change:** `index >= n` (not `== n`), since `index + 2` can overshoot past `n` to `n+1`.

**State:** `(index, buy)` — same dimensions as LC 122, but **tabulation array must be sized `dp[n+2][2]`** to safely hold the `index+2` jump from `index = n-1`.

**Space optimization — the key new wrinkle:** `dp[index]` depends on **both** `dp[index+1]` (full row, both buy states) and `dp[index+2][1]` (only the sell-branch lookup). This requires **two rolling rows**, not one:

```java
int[] next2 = new int[2];  // represents dp[index+2]
int[] next1 = new int[2];  // represents dp[index+1]

for (index = n-1; index >= 0; index--) {
    int[] curr = new int[2];
    // buy==1: uses next1[0]
    // buy==0: sellToday uses next2[1], skip uses next1[0]
    ...
    next2 = next1;
    next1 = curr;
}
return next1[1];
```

This pattern — needing more than one rolling row because of a multi-step lookback — generalizes beyond stocks; revisit this whenever a recurrence references more than one "future" index.

---

## 7. LC 714 — Best Time to Buy and Sell Stock with Transaction Fee

**Only change from LC 122:** subtract `fee` once per transaction, applied in the **sell branch** (where a transaction completes). No cooldown, so the index jump stays `index + 1`.

```
sellToday = prices[i] - fee + f(i+1, 1)
```

**State, dimensions, and space optimization are identical to LC 122** — this is purely an arithmetic tweak, no structural change.

---

## 8. Summary Table

| Problem | Constraint | State | Key mechanism | dp size |
|---|---|---|---|---|
| LC 121 | 1 transaction | `(i, buy)` | Sell returns `prices[i]` directly (no recursion) | `[n][2]` |
| LC 122 | Unlimited | `(i, buy)` | Sell recurses with `buy=1` | `[n][2]` |
| LC 123 | ≤ 2 transactions | `(i, buy, t)` | `t` increments on sell; base case `t==2` | `[n+1][2][3]` |
| LC 188 | ≤ k transactions | `(i, buy, t)` | Same as 123, generalized to `k` | `[n+1][2][k+1]` |
| LC 309 | Cooldown | `(i, buy)` | Sell jumps to `i+2`; base case `i>=n` | `[n+2][2]` |
| LC 714 | Transaction fee | `(i, buy)` | Sell subtracts `fee` | `[n][2]` |

---

## 9. Big-Picture Pattern

This family mirrors the LCS family's "one engine, different formulas" structure:

- **Core engine:** `f(index, buy)` with the buy/sell/skip include-exclude pattern
- **Constraints are surgical, localized edits:**
  - *Limiting transaction count* → add a `transactions` state dimension
  - *Delaying re-entry* → jump the index further on the triggering branch
  - *Cost per transaction* → arithmetic adjustment in the sell branch
- **Space optimization difficulty scales with lookback depth:** standard 1-step lookback → 1 rolling row; 2-step lookback (cooldown) → 2 rolling rows.

**Debugging heuristic confirmed in this session:** when memoization passes but tabulation fails, suspect an index/offset mismatch between the two (e.g., `f(i+1, 1, t+1)` in recursion vs. `dp[i+1][1][t]` in tabulation — the `+1` on `t` was dropped). Trace both side-by-side line by line.

---

## 10. Next Up

Continue to the next DP family in the curriculum (per JM's structured plan).
