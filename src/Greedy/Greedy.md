
### 1. **Interval Scheduling (Maximize Non-Overlapping Intervals)**
- **Intuition**: When you have intervals (e.g., meetings with start/end times), the greedy choice is to always pick the one that **ends earliest**. This leaves the most room for future intervals, maximizing the count without overlap. Greedy works because the earliest end is always part of an optimal solution (you can swap others if needed).
- **When to Use**: Problems asking to maximize number of non-overlapping activities, or minimize removals to make non-overlapping (e.g., "max meetings in one room").
- **How to Solve**:
    - Brute force: Try all subsets, check non-overlap → exponential O(2^n).
    - Optimal Greedy: Sort intervals by **end time** ascending. Pick the first, then greedily pick next if start ≥ previous end. Count picks.
- **Common Problems**:
    - N Meetings in One Room (our problem): Sort by end, greedy pick → max count.
    - Non-overlapping Intervals (our problem): Same sort + greedy → removals = n - max non-overlapping.
- **Proof Sketch & Pitfalls**: Greedy choice property: Earliest end allows more later. Pitfall: Sorting by start time fails (e.g., long interval blocks many short ones). Edge: All overlap → 1; Touching allowed. Tip: Always explain "why end time" with counter-example.
- **Complexity**: O(n log n) time (sort + scan), O(1) space extra.

### 2. **Interval Merging/Insertion**
- **Intuition**: For sorted intervals, merge overlaps by taking min start and max end. Greedy because we process in order and only merge when necessary — no backtracking needed.
- **When to Use**: When you need to combine overlapping ranges or insert a new one into a sorted list (e.g., "merge intervals" or "insert interval").
- **How to Solve**:
    - Brute force: Check every pair for overlap → O(n²).
    - Optimal Greedy: Sort by start. Iterate once: If current overlaps previous (current.start ≤ previous.end), merge (max end). Else add as new.
- **Common Problems**:
    - Insert Interval (our problem): Add non-overlapping before, merge overlapping, add after.
    - Merge Intervals: Same logic, but on unsorted list → sort first.
- **Proof Sketch & Pitfalls**: Optimal substructure: Merged result is always minimal. Pitfall: Forgetting touching intervals (start == end is merge if problem says). Edge: No overlaps → return original. Tip: Use ArrayList for result.
- **Complexity**: O(n log n) time (sort if needed + scan), O(n) space for result.

### 3. **Scheduling with Deadlines/Profits (Job Sequencing)**
- **Intuition**: When jobs have deadlines and profits, prioritize **highest profit first**, but schedule **as late as possible** within deadline to leave early slots free for tight-deadline jobs. Greedy because high-profit jobs are always in optimal solution if feasible.
- **When to Use**: Maximize profit with time constraints (e.g., "select jobs with deadlines").
- **How to Solve**:
    - Brute force: All subsets + check deadlines → exponential.
    - Optimal Greedy: Sort by profit descending. Use slot array or union-find for latest free slot ≤ deadline.
- **Common Problems**:
    - Job Sequencing (our problem): Sort profit desc, schedule late → max profit/count.
    - Shortest Job First (our problem): Sort burst time asc → min waiting time.
- **Proof Sketch & Pitfalls**: Greedy choice: High profit first maximizes value. Pitfall: Scheduling early blocks tight jobs (e.g., long job with loose deadline blocks short high-profit). Edge: All deadlines 1 → pick max profit only. Tip: Prove with "swap argument".
- **Complexity**: O(n log n) sort + O(n * maxD) for slots (D=deadline).

### 4. **Optimal Range/Jump (Max Reach with Min Steps)**
- **Intuition**: When you have ranges (like jumps), expand the **current reachable range** greedily, and only increment steps when you must (at the end of current range). Greedy because the farthest in current range is the best for next.
- **When to Use**: Minimize steps to reach end with variable jump lengths (e.g., "min jumps to end").
- **How to Solve**:
    - Brute force: BFS from each position → O(n²).
    - Optimal Greedy: Track current_end and farthest. Update farthest in loop. When i == current_end → increment jumps, set current_end = farthest.
- **Common Problems**:
    - Jump Game II (our problem): Exactly this → min jumps.
    - Minimum Taps to Water Garden: Similar range expansion.
- **Proof Sketch & Pitfalls**: Greedy choice: Farthest in current jumps is optimal. Pitfall: Jumping max every time (local greedy) fails (e.g., [3,2,1,0,4] — max jump from 0 blocks). Edge: All 0 → impossible (but guaranteed reachable). Tip: "Delay jump decision".
- **Complexity**: O(n) time, O(1) space.

### 5. **Priority Allocation (Greedy Selection by Ratio)**
- **Intuition**: When resources are limited, select items with the highest "value per unit" (e.g., profit/weight). Greedy works when you can take fractions or the problem has optimal substructure.
- **When to Use**: Fractional allocation (e.g., "max profit with limited capacity").
- **How to Solve**:
    - Brute force: All subsets → exponential.
    - Optimal Greedy: Sort by value/weight desc. Take as much as possible until capacity full.
- **Common Problems**:
    - Fractional Knapsack: Sort profit/weight desc → max value.
    - Assign Cookies (our problem): Sort greed/child and cookies → two pointers for max assignments.
- **Proof Sketch & Pitfalls**: Greedy choice: Highest ratio is always in optimal. Pitfall: Doesn't work for 0/1 knapsack (needs DP). Edge: All ratios equal → any order. Tip: "Prove by contradiction — swapping lower ratio improves nothing".
- **Complexity**: O(n log n) time (sort), O(1) space.

### 6. **Circular Greedy (Discard Invalid Prefixes)**
- **Intuition**: For circular problems, compute cumulative sum. If total sum negative → impossible. Else, greedily skip prefixes that cause negative balance (they can't be part of valid start).
- **When to Use**: Circular routes or balances (e.g., "find starting point for full loop").
- **How to Solve**:
    - Brute force: Simulate from each start → O(n²).
    - Optimal Greedy: One pass to compute total. Reset start when cumulative negative.
- **Common Problems**:
    - Gas Station (our problem): Compute gas - cost cumulative → skip invalid prefixes.
    - Car Pooling: Similar capacity checks.
- **Proof Sketch & Pitfalls**: If total ≥ 0, valid start exists after last negative prefix. Pitfall: Forgetting total check first. Edge: All zeros → start 0. Tip: "The theorem: valid if total ≥ 0".
- **Complexity**: O(n) time, O(1) space.

### 7. **Balance Tracking (Counters/Ranges)**
- **Intuition**: For balancing symbols with wildcards, track **min/max possible balance**. Greedy because we aggressively open/close to test feasibility.
- **When to Use**: Valid strings with wildcards (e.g., parentheses with *).
- **How to Solve**:
    - Brute force: Try all * assignments → exponential.
    - Optimal Greedy: Track low/high open count, reset low ≥0, fail if high <0. End low==0.
- **Common Problems**:
    - Valid Parenthesis String (our problem): Exactly this.
    - Longest Valid Parentheses: Stack + greedy max.
- **Proof Sketch & Pitfalls**: Ranges cover all possibilities. Pitfall: Forgetting reset for low. Edge: All * → valid. Tip: "Pessimistic/optimistic scenarios".
- **Complexity**: O(n) time, O(1) space.

### 8. **Local Max/Min Assignment (Two-Pass Allocation)**
- **Intuition**: For local constraints (e.g., higher rating > neighbor candy), assign minimum based on left/right separately, take max per position.
- **When to Use**: Allocate resources with adjacent comparisons (e.g., "more to higher").
- **How to Solve**:
    - Brute force: Try incremental assignments → O(n * max_value).
    - Optimal Greedy: Left pass for increasing left, right pass for increasing right, max per i.
- **Common Problems**:
    - Candy (our problem): Exactly this → min total candies.
    - Wiggle Sequence: Similar greedy for alternates.
- **Proof Sketch & Pitfalls**: Two passes satisfy both sides minimally. Pitfall: Decreasing values → wrong. Edge: Equal ratings → same candies ok. Tip: "Satisfy strictest neighbor".
- **Complexity**: O(n) time, O(n) space.

---
### Greedy Revision Cheat Sheet — Important Problems & Their Core Intuition

#### 1. Interval / Activity Selection Family
| Problem | Link / Number | Core Intuition / Pattern | Key Trick / Why Greedy Works |
|-------|----------------|---------------------------|-------------------------------|
| N Meetings in One Room | GFG / LeetCode-like | Maximize number of non-overlapping intervals | Sort by **end time** ascending, pick earliest ending → leaves max room for future |
| Minimum Number of Platforms (Trains) | GFG classic | Max overlapping intervals (platforms needed) | Sort arrival & departure separately, two pointers, process earliest event first |
| Non-overlapping Intervals (min removals) | LeetCode 435 | Min intervals to remove to make rest non-overlapping | Same as activity selection: sort by end, count how many we can keep → removals = n - kept |
| Insert Interval | LeetCode 57 | Merge new interval into sorted non-overlapping list | One pass: add before, merge overlapping (min start, max end), add after |

**Revision tip**: Whenever you see "intervals + maximize count / minimize removals / merge", think **sort by end time** or **one-pass merge**.

#### 2. Job / Task Scheduling with Profit / Time
| Problem | Link / Number | Core Intuition / Pattern | Key Trick / Why Greedy Works |
|-------|----------------|---------------------------|-------------------------------|
| Job Sequencing with Deadlines | GFG / common | Maximize profit, each job takes 1 unit time | Sort by **profit descending**, schedule **as late as possible** (latest free slot ≤ deadline) |
| Shortest Job First (min avg waiting time) | GFG / OS classic | Minimize average waiting time, all arrive at 0 | Sort by **burst time ascending** → shortest first |
| Task Scheduler (with cooldown) | LeetCode 621 | Schedule tasks with frequency & cooldown | Greedy: always pick most frequent available task (max-heap) |

**Revision tip**: "Profit + deadline + unit time" → **profit descending + late scheduling**.  
"Minimize waiting / turnaround" → **shortest first**.

#### 3. Jump / Reach Problems (Range Expansion)
| Problem | Link / Number | Core Intuition / Pattern | Key Trick / Why Greedy Works |
|-------|----------------|---------------------------|-------------------------------|
| Jump Game II | LeetCode 45 | Minimum jumps to reach last index | Greedy range: track current_end & farthest, increment jumps only when i reaches current_end |
| Jump Game I | LeetCode 55 | Can we reach the last index? | Track max_reach, if i > max_reach → false |
| Minimum Number of Taps to Water a Garden | LeetCode 1326 | Min taps to water whole garden (similar to jump) | Same as Jump Game II: greedy range expansion |

**Revision tip**: "Min steps to reach end with variable jump lengths" → **delayed greedy** (expand range first, spend jump at boundary).

#### 4. Allocation / Matching Problems
| Problem | Link / Number | Core Intuition / Pattern | Key Trick / Why Greedy Works |
|-------|----------------|---------------------------|-------------------------------|
| Assign Cookies | LeetCode 455 | Maximize content children | Sort both greed & cookies ascending, two pointers (smallest cookie that fits) |
| Candy | LeetCode 135 | Min candies with higher rating > neighbors | Two passes: left-to-right + right-to-left, take max per position |
| Fractional Knapsack | GFG classic | Max value with limited capacity | Sort by value/weight descending, take as much as possible |

**Revision tip**: "Higher must get more than neighbor" → **two-pass max**.  
"Max assignments under constraint" → **sort both + two pointers**.

#### 5. Circular / Balance Problems
| Problem | Link / Number | Core Intuition / Pattern | Key Trick / Why Greedy Works |
|-------|----------------|---------------------------|-------------------------------|
| Gas Station | LeetCode 134 | Find starting point for full circuit | One pass: track current & total tank, reset start when current < 0 |
| Valid Parenthesis String | LeetCode 678 | Valid parentheses with * wildcard | Two counters: minOpen & maxOpen, reset min≥0, fail if max<0, end min==0 |

**Revision tip**: Circular + cumulative → **discard negative prefix**.  
Wildcard balance → **range of possible opens** (pessimistic + optimistic).

#### 6. Other High-Frequency Greedy Classics
| Problem | Link / Number | Core Intuition / Pattern | Key Trick / Why Greedy Works |
|-------|----------------|---------------------------|-------------------------------|
| Lemonade Change | LeetCode 860 | Give correct change with greedy bills | Greedy: always give largest possible change (prefer $10 over $5) |
| Queue Reconstruction by Height | LeetCode 406 | Reconstruct queue from height & people in front | Sort by height desc, then insert at index = k (people in front) |
| Maximum Units on a Truck | LeetCode 1710 | Max units with limited truck size | Sort by units/box descending, take as many as possible |
| Distant Barcodes | LeetCode 1054 | Rearrange so no two same adjacent | Greedy: max-heap by frequency, place highest remaining |

### Quick Revision Checklist (10-second recall)

- Intervals → **sort by end time**
- Profit + deadline → **profit desc + schedule late**
- Min jumps / reach → **current_end & farthest**
- Neighbor constraints → **two passes + max**
- Circular balance → **cumulative, reset on negative**
- Wildcard / flexible → **min/max range tracking**
- Matching / assignment → **sort both + two pointers**
- Fractional / ratio → **sort by value per unit**
