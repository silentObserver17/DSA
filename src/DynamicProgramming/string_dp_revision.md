# String DP — Revision Notes

---

## Core Mental Model

All previous DP used **one sequence** + one varying parameter (index + target/capacity).

String DP uses **two sequences, two indices** — both varying simultaneously.

```
dp[i][j] = answer considering first i characters of s1 AND first j characters of s2
```

- Table is always **(m+1) × (n+1)** — one dimension per string
- Character access via `s.charAt(i-1)` — because i is a **length**, not an index
- Base cases: first row and column (i=0 or j=0 = empty string)

---

## The LCS Engine

**Recurrence:**
```
if s1[i] == s2[j]:   dp[i][j] = dp[i-1][j-1] + 1
else:                 dp[i][j] = max(dp[i-1][j], dp[i][j-1])
```

**Base case:** First row and column = 0 (free from Java int[] zero-initialization)

**Direction:** Forward (i: 1→m, j: 1→n) — needs top-left values already computed

**Space optimization:** Two arrays (prev + curr). Diagonal value `dp[j-1]` is safe because it's from the previous row, not overwritten yet.

```java
for (int i = 1; i <= m; i++) {
    int[] curr = new int[n + 1];
    for (int j = 1; j <= n; j++) {
        if (s1.charAt(i-1) == s2.charAt(j-1))
            curr[j] = dp[j-1] + 1;
        else
            curr[j] = Math.max(dp[j], curr[j-1]);
    }
    dp = curr;
}
```

---

## LCS Family — All Problems

LCS is the engine. Every problem below either calls LCS directly or tweaks one thing.

### 1. LCS Length
**Formula:** `dp[m][n]`

### 2. Print LCS
**Approach:** Build dp table normally, then traceback from `(m, n)`:
- Match → append `s1.charAt(i-1)`, move `i-1, j-1`
- No match → move toward `max(dp[i-1][j], dp[i][j-1])`
- Stop when `i < 1 || j < 1`
- Use `sb.append()` + `sb.reverse()` at end

### 3. Longest Common Substring
**Key difference:** Contiguous characters only — no carrying forward on mismatch.

```
if s1[i] == s2[j]:   dp[i][j] = dp[i-1][j-1] + 1
else:                 dp[i][j] = 0              // ← key change
```

**Answer:** Track `max` variable inline during table fill (not `dp[m][n]`)

**Initialize max = 0** (not `Integer.MIN_VALUE`)

### 4. Longest Palindromic Subsequence (LPS)
**Trick:** `LPS(s) = LCS(s, reverse(s))`

**Why it works:** Any palindromic subsequence reads the same forwards and backwards, so it appears in both `s` and `reverse(s)`. Non-palindromes don't survive in both directions.

```java
String reversed = new StringBuilder(s).reverse().toString();
// then run standard LCS
```

### 5. Minimum Insertions to make Palindrome
**Formula:** `n - LPS(s)`

**Why:** LPS characters are already in place. Each remaining character needs one insertion to balance it on the other side.

### 6. Minimum Insertions/Deletions to convert s1 → s2
**Formula:** `(m - LCS) + (n - LCS)`

**Why:** LCS characters are the safe zone (don't touch). Delete non-LCS chars from s1, insert non-LCS chars from s2.

### 7. Shortest Common Supersequence (SCS) — Length
**Formula:** `m + n - LCS`

**Why:** LCS characters are shared — include them once, not twice.

### 8. Print Shortest Common Supersequence
**Traceback from `(m, n)`:**
- Match → append character, move `i-1, j-1`
- No match, `dp[i-1][j] > dp[i][j-1]` → append `s1.charAt(i-1)`, move `i--`
- No match, else → append `s2.charAt(j-1)`, move `j--`
- After loop: append remaining from whichever string hasn't finished

```java
while (i >= 1) { sb.append(s1.charAt(i-1)); i--; }
while (j >= 1) { sb.append(s2.charAt(j-1)); j--; }
return sb.reverse().toString();
```

### 9. Longest Repeating Subsequence
**Trick:** `LCS(s, s)` but with extra condition: `i != j`

```
if s.charAt(i-1) == s.charAt(j-1) && i != j:   dp[i][j] = dp[i-1][j-1] + 1
else:                                             dp[i][j] = max(dp[i-1][j], dp[i][j-1])
```

**Why `i != j`:** Prevents a character from matching with itself at the same position.

---

## LCS Family — Cheat Sheet

| Problem | Input | Formula / Trick |
|---|---|---|
| LCS Length | s1, s2 | `dp[m][n]` |
| Print LCS | s1, s2 | Traceback: match→append, no match→follow max |
| Longest Common Substring | s1, s2 | No-match → 0, track max inline |
| Longest Palindromic Subsequence | s | `LCS(s, reverse(s))` |
| Min Insertions for Palindrome | s | `n - LPS` |
| Min Insertions/Deletions | s1, s2 | `(m - LCS) + (n - LCS)` |
| Shortest Common Supersequence | s1, s2 | `m + n - LCS` |
| Print SCS | s1, s2 | Traceback: match→append once, no match→append from bigger side |
| Longest Repeating Subsequence | s | `LCS(s, s)` with `i != j` condition |

---

## Edit Distance (LC 72)

**Operations:** Insert, Delete, Replace (each costs 1)

**Recurrence:**
```
if s1[i] == s2[j]:   dp[i][j] = dp[i-1][j-1]           // free
else:                 dp[i][j] = 1 + min(
                          dp[i-1][j],    // delete from s1
                          dp[i][j-1],    // insert into s1
                          dp[i-1][j-1]   // replace
                      )
```

**Base cases:**
- `dp[i][0] = i` — converting i chars to empty = i deletions
- `dp[0][j] = j` — converting empty to j chars = j insertions

**Space optimization:** Two arrays. `curr[0] = i` for each row i.

**Key difference from Min Insertions/Deletions:** Replace merges a delete + insert into one operation.

---

## Wildcard Matching (LC 44)

**Pattern characters:** `?` matches any single char, `*` matches any sequence (including empty)

**Recurrence:**
```
if s[i] == p[j] || p[j] == '?':   dp[i][j] = dp[i-1][j-1]
else if p[j] == '*':               dp[i][j] = dp[i][j-1]      // * matches empty
                                            || dp[i-1][j]      // * matches one char
else:                              dp[i][j] = false
```

**Base cases:**
- `dp[0][0] = true` — both empty = match
- `dp[i][0] = false` — pattern empty, string not = no match
- `dp[0][j] = dp[0][j-1] && p.charAt(j-1) == '*'` — string empty, only all-stars pattern matches

**Space optimization gotcha:** `curr[0]` must be `false` (not `true`) for every row i ≥ 1. Let it default from array initialization — don't explicitly set it.

---

## Key Patterns & Pitfalls

### Length-based indexing
- `dp[i][j]` = answer for first `i` chars of s1, first `j` chars of s2
- Character access: `s.charAt(i-1)` — always subtract 1
- Table size: `(m+1) × (n+1)`

### Diagonal value in space optimization
- In LCS/Edit Distance/Wildcard: diagonal = `dp[j-1]` from previous row
- Safe with two-array approach since prev row isn't modified while building curr

### Subsequence vs Substring
- Subsequence: order matters, not contiguous → carry forward on mismatch (`max`)
- Substring: contiguous → reset to 0 on mismatch, track max inline

### Boolean[][] vs boolean[][]
- Use `Boolean[][]` for memoization (need null check for unvisited)
- Use `boolean[][]` for tabulation (every cell explicitly set)

### Base case chain for Wildcard dp[0][j]
```java
dp[0][j] = dp[0][j-1] && p.charAt(j-1) == '*';
```
Once a non-star breaks the chain, everything after stays false automatically.

---

## Implementation Skeleton (LCS — all 4 stages)

```java
// Recursive
private int lcs(String s1, String s2, int i, int j) {
    if (i < 0 || j < 0) return 0;
    if (s1.charAt(i) == s2.charAt(j)) return 1 + lcs(s1, s2, i-1, j-1);
    return Math.max(lcs(s1, s2, i-1, j), lcs(s1, s2, i, j-1));
}

// Memoization — add Boolean[][] dp, null check, dp[i][j] = result

// Tabulation
int[][] dp = new int[m+1][n+1];
for (int i = 1; i <= m; i++)
    for (int j = 1; j <= n; j++)
        if (s1.charAt(i-1) == s2.charAt(j-1)) dp[i][j] = dp[i-1][j-1] + 1;
        else dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
return dp[m][n];

// Space Optimization
int[] dp = new int[n+1];
for (int i = 1; i <= m; i++) {
    int[] curr = new int[n+1];
    for (int j = 1; j <= n; j++)
        if (s1.charAt(i-1) == s2.charAt(j-1)) curr[j] = dp[j-1] + 1;
        else curr[j] = Math.max(dp[j], curr[j-1]);
    dp = curr;
}
return dp[n];
```
