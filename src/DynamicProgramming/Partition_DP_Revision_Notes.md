# Partition DP — Revision Notes

## Family overview

Partition DP (a.k.a. "range DP" / "interval DP" / MCM pattern) answers a different
question from every prior DP family:

> "Given a sequence, what's the best way to **split it into ranges**, where the
> cost/result depends on **where** you cut / which range boundaries meet?"

State is typically a **range `(i, j)`** (two-sided range DP), though one variant
(Palindrome Partitioning II) uses a **single index** `(i)` representing a suffix
cut-point instead. The core recurrence tries every valid split point `k` inside
`(i, j)`, recurses on both sides, and combines:

```
f(i, j) = best/combined over all valid k of:  cost(i, k, j) + f(left half) + f(right half)
```

This differs from String DP's `(i, j)` — there, `i` and `j` walk two *different*
sequences in lockstep. In two-sided range DP, `(i, j)` is one range within **one**
sequence (or, in Scramble String, a matched pair of ranges across two strings),
and you brute-force every internal split — that's what pushes time complexity to
**O(n cubed)** or higher, instead of O(n squared).

---

## Problems completed

### 1. Matrix Chain Multiplication (MCM)
- State: f(i, j) = min scalar multiplications to multiply matrix chain i..j.
- Base case: f(i, j) = 0 when i == j (single matrix, nothing to multiply).
- Recurrence: f(i,j) = min over k in [i, j-1] of f(i,k) + f(k+1,j) + arr[i-1]*arr[k]*arr[j]
- Key insight: only the outer dimensions (arr[i-1], arr[j]) and the split
  point (arr[k]) matter for merge cost - inner dimensions cancel out.
- Complexity: O(n^2) states x O(n) work = O(n^3) time, O(n^2) space.
- Space optimization: NOT possible - f(k+1, j) depends on an arbitrary row
  k+1 anywhere between i and j, not a fixed offset.

### 2. Minimum Cost to Cut a Stick (LC 1547)
- State: f(i, j) = min cost to make all needed cuts within stick segment
  spanning newCuts[i] to newCuts[j] (indices into a padded/sorted cuts array).
- Sentinel padding: insert 0 and n into cuts[] (sorted) so every "piece"
  has valid boundary values - same trick reused in Burst Balloons.
- Base case: f(i, j) = 0 when j == i + 1 (adjacent indices - no cut points
  left between them), NOT i == j.
- Recurrence: f(i,j) = min over k in (i, j) of f(i,k) + f(k,j) + (newCuts[j]-newCuts[i])
  - note split cost does not depend on k (a cut on a piece always costs the
  piece's current length, regardless of where you cut).
- Complexity: same O(n^3) time / O(n^2) space; no space optimization possible.
- Bug pattern hit: initializing dp[i][j] = MAX_VALUE for a running min, but
  forgetting to correctly protect the actual base case condition (i+1==j, not
  i==j) - silently left the true base case unset/overwritten.

### 3. Burst Balloons (LC 312)
- Reframing trick: instead of "which balloon to pop first" (neighbors depend
  on arbitrary prior order), think "which balloon to pop LAST" in a range -
  its neighbors at that moment are guaranteed to be the fixed boundary values
  outside the range, regardless of internal order. This is a broadly reusable
  trick for range DP problems where order-of-operations seems to matter.
- Sentinel padding: pad nums[] with 1 at both ends (matches problem's
  "no neighbor = 1" rule) -> newNums, real balloons at indices 1..n.
- Base case: f(i, j) = 0 when i > j (empty range).
- Recurrence: f(i,j) = max over k in [i,j] of newNums[i-1]*newNums[k]*newNums[j+1] + f(i,k-1) + f(k+1,j)
  - k itself is excluded from both sub-ranges (it's already "consumed").
- Complexity: O(n^3) time, O(n^2) space; no space optimization.
- Bug pattern hit: loop bound k < j instead of k <= j silently broke the
  single-balloon case f(i,i), since the loop never executed and returned the
  MIN_VALUE sentinel instead of a real value.

### 4. Palindrome Partitioning II (LC 132)
- Structurally different from #1-3: state is a SINGLE INDEX f(i), not a
  range - because you're always cutting a suffix starting at a fixed point, not
  recursively splitting an arbitrary two-sided range.
- Two-layer DP:
  1. Precompute isPal[i][j] (standalone palindromic-substring DP, built bottom-up:
     isPal[i][j] = (s[i]==s[j]) && isPal[i+1][j-1], base cases i==j -> true and
     j==i+1 -> s[i]==s[j]).
  2. Main DP: f(i) = min cuts to partition s[i..n-1] into palindrome pieces.
- Base case: f(n) = -1 (empty suffix). This is a sentinel derived from
  "cuts = pieces - 1", not a literal "cost" - it exists so 1 + f(j+1) self-
  corrects at the boundary (whole suffix already one palindrome -> 1 + (-1) = 0
  cuts, correctly).
- Recurrence: f(i) = min over j in [i, n-1] where isPal[i][j] is true, of 1 + f(j+1)
  - unlike #1-3, most j values are skipped (only valid palindrome splits tried).
- Tabulation: dp[] is 1D, size n+1, filled right-to-left (since f(i)
  depends on f(j+1), always a larger index). dp[n] = -1, rest MAX_VALUE.
- Complexity: O(n^2) for the main DP, plus O(n^2) for the palindrome precompute.

### 5. Boolean Evaluation (Evaluate Expression to True - count ways)
- New wrinkle: state returns a PAIR (waysTrue, waysFalse), not a single
  min/max value - first problem in the family requiring paired/accumulated counts
  rather than a single optimized number.
- Representation: int[2] return from recursion/memo helper; dp[i][j][2]
  (3D array) for tabulation, storing {waysTrue, waysFalse} per range.
- Base case: i == j (single operand character) -> {1,0} if 'T', {0,1} if 'F'.
- Recurrence: for each operator position k (stepping by 2, string alternates
  operand/operator), split into f(i,k-1) and f(k+1,j) (operator excluded, same
  "exclude k" pattern as Burst Balloons), combine based on s[k]:
  - AND: True = L_T*R_T. False = L_T*R_F + L_F*R_T + L_F*R_F.
  - OR: True = L_T*R_T + L_T*R_F + L_F*R_T. False = L_F*R_F.
  - XOR: True = L_T*R_F + L_F*R_T. False = L_T*R_T + L_F*R_F.
- Complexity: O(n^3) time, O(n^2) space. No space optimization.
- Bug patterns hit:
  - Accumulation vs. overwrite: used = instead of += when combining
    contributions across different split points k - each k is an independently
    valid parenthesization, so contributions must SUM, not replace. General
    rule: counting DPs (summing across loop iterations) need += and 0 as the
    identity/init value; optimization DPs (min/max) need direct assignment/compare
    and -1/MAX_VALUE as the sentinel - these are different situations.
  - Memoization sentinel choice: since real counts can legitimately be 0,
    0 is unsafe as a "not computed" sentinel - -1 is required instead.
  - Copy-paste bug: memoized helper's internal recursive calls accidentally
    called the plain (non-memoized) helper instead of itself - silently defeating
    memoization while still producing correct answers. Correct output does not
    guarantee correct complexity.
  - Diagonal base-case initialization must be a separate pass: main triple-
    nested tabulation loop's j starts at i+1 (never touching i==j), so the
    base case needs its own preliminary loop over i alone, checking the actual
    character (not a blanket constant), and must NOT touch off-diagonal cells.

### 6. Scramble String (LC 87)
- New structural leap: compares TWO STRINGS simultaneously. State is
  (i1, i2, len) - starting index in s1, starting index in s2, and a shared
  LENGTH (not two independent end indices) - since (i1,i2) alone doesn't
  uniquely determine the subproblem (same starting positions can correspond to
  different sub-range sizes at different points in the recursion tree).
- "Swap" clarified: swapping means swapping the CONCATENATION ORDER of the
  two split pieces (A+B vs B+A), not reversing characters within a piece.
- Base case: len == 1 -> s1.charAt(i1) == s2.charAt(i2).
- Recurrence: for each split length p (1 to len-1), check EITHER:
  - No-swap: f(i1, i2, p) AND f(i1+p, i2+p, len-p)
  - Swap: f(i1, i2+len-p, p) AND f(i1+p, i2, len-p) (note: s2's last p chars
    start at i2+len-p - derived from matching piece LENGTHS, not positions)
  - If EITHER succeeds for ANY p, the whole f(i1,i2,len) is true (existence
    check, not an optimization - allows early-exit unlike min/max problems).
- Memoization: needs a 3D table dp[i1][i2][len], and since return type is
  boolean (only 2 states), used BOXED Boolean[][][] with null as the
  "not computed" sentinel - primitive boolean has no room for a third state.
- Tabulation loop order: len must be the OUTERMOST loop (2 to n,
  increasing), since every dependency requires a strictly smaller len fully
  computed first - a stronger constraint than the row/column reasoning in 2D
  problems. i1 and i2 order doesn't matter within a fixed len layer, since
  neither depends on the other within the same layer.
- Complexity: O(n^3) states x O(n) work per state = O(n^4) time, O(n^3) space
  - worse than #1-3 and #5 due to the extra state dimension. No space optimization
  (same "arbitrary offset p" reasoning as always).
- Bug patterns hit:
  - Initial base-case loop only set dp[i][i][1] (diagonal only) instead of all
    (i1,i2) pairs - needed nested loop over both i and j independently.
  - Critical tabulation bug: used return true inside the main loop (copied
    from the recursive early-exit pattern) - this exits the ENTIRE tabulation
    function, abandoning every other unfilled (i1,i2,len) state. Fixed by
    replacing with plain assignment (dp[i1][i2][len] = true;) so the loop
    continues to fill all states; break (exiting only the innermost p loop)
    is fine as a minor optimization once a match is found for that state.
- Practical pruning (doesn't change worst-case Big-O, but cuts real runtime):
  - Identical-substring shortcut: if s1[i1..i1+len-1] == s2[i2..i2+len-1]
    exactly, it's trivially a valid "scramble," skip the p loop.
  - Character-frequency pruning: if the two substrings don't have identical
    character multisets, no scramble is possible - skip the p loop. Necessary
    (not sufficient) condition, cheap to check with a single shared count array.
  - Both checks sit OUTSIDE the p loop (once per (len,i1,i2) state, not
    per (state,p) pair) - this keeps worst-case complexity at O(n^4), NOT O(n^5);
    putting a check inside the p loop would multiply cost by the loop's O(n)
    iterations instead of adding it once per state.

---

## Key learnings & principles (cumulative, Partition DP family)

- Range-DP loop direction rule (general form): for each axis independently,
  check which direction that axis moves in the dependencies. If a dependency
  needs a LARGER value on an axis -> iterate right-to-left / large-to-small.
  If it needs a SMALLER value -> iterate left-to-right / small-to-large. This
  is why i went right-to-left and j went left-to-right in every two-sided
  range-DP problem this family - not a coincidence, a direct consequence of
  shrinking toward the diagonal from both sides at once.
- For 3D+ state (Scramble String): when one dimension (len) is a SIZE
  rather than a positional index, and every dependency requires a strictly
  smaller size regardless of the other dimensions, that size dimension becomes
  the outermost loop, iterating small-to-large - a stronger constraint than
  ordinary row/column reasoning.
- Base cases must be re-derived per problem, not pattern-matched: i==j
  (MCM), j==i+1 (Cut Stick), i>j (Balloons), i>=n with sentinel -1
  (Palindrome Part II), len==1 (Boolean Eval, Scramble String). Always ask
  "what's the smallest/emptiest version of this range, and what should it
  structurally return?" rather than reusing the last problem's shape.
- Running min/max requires correct sentinel init AND correct base-case
  protection: initializing to MAX_VALUE/MIN_VALUE isn't enough - the
  actual base-case cells must be left at their correct identity value and not
  swept up by a careless blanket initialization condition.
- Counting DPs vs. optimization DPs need different accumulation habits:
  optimization DPs (min/max) use direct compare/assign with -1/MAX_VALUE
  sentinels; counting DPs (summing valid ways) use += across loop iterations
  with 0 as the safe identity/init value - conflating these two causes
  silent overwrite bugs (Boolean Evaluation) or unsafe sentinel collisions.
- Memoization sentinel must never collide with a legitimately reachable
  value: -1 works when real answers are always >= 0 (counts, costs).
  When the return type is boolean and can't hold a third "unknown" state,
  switch to boxed Boolean with null as the sentinel (Scramble String).
- Loop-bound edge cases are best caught by tracing the smallest non-trivial
  range by hand (single element, adjacent pair, empty range) - fastest way
  to catch off-by-one errors like k < j vs k <= j.
- Recursion's "return true" is NOT safe to copy into tabulation as-is:
  in recursion, return true exits one call, cleanly scoped to one state. In
  tabulation, the same statement exits the ENTIRE function, abandoning every
  other unfilled state in the table. Always replace early-exit return with
  a plain assignment when translating recursive early-exits into iterative
  tabulation loops.
- Space optimization is generally NOT possible for range DP where a
  dependency spans an arbitrary row/column/offset between i and j (as
  opposed to a fixed offset like i-1). True for every two-sided range DP
  problem this family (MCM, Cut Stick, Balloons, Boolean Eval, Scramble
  String). O(n^2)-O(n^3) space is the expected/accepted final answer for
  this family in interviews - no penalty for not finding further optimization.
- Correct output does not guarantee correct complexity: a copy-paste bug
  where a "memoized" helper's internal recursive calls actually invoked the
  non-memoized version still produced correct answers, just with exponential
  time instead of polynomial - a reminder to verify complexity assumptions,
  not just correctness, especially after refactoring.
- Pruning optimizations vs. Big-O optimizations are different things:
  identical-substring and character-frequency checks in Scramble String don't
  change worst-case complexity (still O(n^4)) if placed correctly (once per
  state, not per state x split), but meaningfully reduce real-world runtime by
  skipping impossible branches early.
- "Last operation in the range" reframing (Burst Balloons): a broadly
  reusable trick for range DP problems where order-of-operations seems to
  matter - instead of thinking about what happens FIRST, think about what's
  true at the moment something happens LAST. Often removes order-dependence
  entirely, reducing to a static range recurrence.
- Pieces-vs-cuts / steps-vs-transitions off-by-one: when a recurrence is
  built by reasoning about "pieces" (+1 per piece) but the DP counts
  "cuts," the relationship cuts = pieces - 1 must be baked into the base
  case (hence f(n) = -1, not 0). General lesson: check what boundary
  value makes the "+1 per step" arithmetic self-consistent - don't always
  assume the boundary is 0.

---

## Problems remaining (from Opus's list)

Note on sub-pattern distinction: problems below split into two genuinely
different flavors - "linear/single-index partition DP" (state = single index,
same family as Palindrome Partitioning II, simpler O(n^2) loop direction) vs.
"two-sided range DP" (state = range or matched ranges, same family as MCM/
Balloons/Scramble String, harder O(n^3)-O(n^4), trickier loop-direction reasoning).

Linear/single-index partition DP (recommended to do first - lighter, faster):
- Word Break
- Perfect Squares
- Partition Array for Maximum Sum
- Split Array Largest Sum
- Minimum Difficulty of Job Schedule

Two-sided range DP (harder - recommended last, Remove Boxes hardest of all):
- Palindrome Partitioning III
- Strange Printer
- Remove Boxes

---

## Continuation prompt (paste at the start of next session)

I'm continuing a structured DP curriculum in Java for DSA/interview prep.
Tutoring style: Socratic only - guiding questions, no direct solutions
unless I'm stuck for a genuinely long time.

Completed DP families (including Partition DP, now finished for the "core six"):
- DP Theory + Fibonacci (all 3 approaches)
- 1D DP: Climbing Stairs, Min Cost Climbing Stairs, House Robber I & II
- 2D DP: Ninja's Training, Unique Paths, Min Path Sum, Triangle
- Subset/Subsequence DP: Subset Sum, Partition Equal Subset Sum, Perfect Sum,
  Min Subset Sum Difference, Count Partitions with Given Difference
- Knapsack DP: 0/1 Knapsack, Unbounded Knapsack, Coin Change (LC 322),
  Coin Change 2 (LC 518), Rod Cutting
- String DP: LCS (all 4 stages + print), Longest Common Substring,
  Longest Palindromic Subsequence, Min Insertions for Palindrome,
  Min Insertions/Deletions, Shortest Common Supersequence (length + print),
  Longest Repeating Subsequence, Edit Distance (LC 72), Wildcard Matching (LC 44)
- DP on Stocks: LC 121, 122, 123, 188, 309, 714 (all 4 stages each)
- DP on LIS: Core LIS (all 4 stages + O(n log n)), Print LIS, Largest
  Divisible Subset, Longest String Chain, Longest Bitonic Subsequence,
  Number of LIS, Maximum Sum IS, Russian Doll Envelopes (LC 354)
- Partition DP: Matrix Chain Multiplication, Minimum Cost to Cut a Stick,
  Burst Balloons (LC 312), Palindrome Partitioning II (LC 132), Boolean
  Evaluation, Scramble String (LC 87) - all 4 stages each (space optimization
  correctly ruled out for the two-sided range-DP problems; Scramble String
  additionally includes a practical pruning optimization on top of tabulation).

Key learnings accumulated: [see attached Partition DP revision notes - includes
the general range-DP loop-direction rule, 3D-state loop ordering, counting-vs-
optimization DP accumulation habits, memoization sentinel selection (including
boxed Boolean/null for boolean-return DPs), the "last operation" reframing
trick, and the "recursion's return true is unsafe in tabulation" pitfall]

Remaining Partition DP problems, in recommended order:
1. Word Break (linear/single-index sub-pattern)
2. Perfect Squares (linear/single-index sub-pattern)
3. Partition Array for Maximum Sum (linear/single-index sub-pattern)
4. Split Array Largest Sum (linear/single-index sub-pattern)
5. Minimum Difficulty of Job Schedule (linear/single-index sub-pattern)
6. Palindrome Partitioning III (two-sided range DP)
7. Strange Printer (two-sided range DP)
8. Remove Boxes (two-sided range DP, hardest - save for last)

Please continue with the next problem in this order. Start with brief theory
if the sub-pattern is new (e.g. first linear/single-index problem after the
range-DP problems), then the problem. Strict four-stage progression: recursive
-> memoization -> tabulation -> space optimization (or a clear explanation of
why space optimization doesn't apply, as established in this family).
