Below is a structured summary of **all major patterns we discussed**, with:

- Typical problem names/examples
- Core decision / choice at each step
- When to use this pattern
- Boilerplate skeleton (Java style, minimal version)
- Key tricks / pruning / state
- Time complexity ballpark

### Recursion & Backtracking Patterns Cheat Sheet

| Pattern Name                  | Core Idea / Decision at each step                          | When to use (typical problems)                              | Boilerplate Skeleton (Java-like)                                                                 | State to track                              | Time Complexity (rough) | Key tricks / common pitfalls                     |
|-------------------------------|-------------------------------------------------------------|-------------------------------------------------------------|-----------------------------------------------------------------------------------------------------|---------------------------------------------|--------------------------|--------------------------------------------------|
| **Linear recursion** (reduce by 1) | Reduce one parameter (n → n-1), combine results on return | Factorial, sum of digits, power(x,n), reverse string       | `if (base) return val;`<br>`return combine(f(n-1), current);`                                      | Single decreasing parameter                 | O(n)                    | Forget base case → stack overflow                |
| **Divide & Conquer style** (exponentiation by squaring) | Split into half, recurse on smaller identical problem      | Power(x,n) efficient, merge sort, quicksort                | `if (base) return;`<br>`half = f(n/2); return combine(half, half, odd case);`                      | Parameter halved each time                  | O(log n)                | Handle odd/even correctly                        |
| **Subsets / Power Set**       | For each element: include or exclude                       | All subsets, subset sum (existence/count), combinations    | `add current;`<br>`for i=start to n-1: add nums[i]; backtrack(i+1); remove;`                       | start index                                 | O(n × 2ⁿ)               | Add copy of current path, not reference          |
| **Combination Sum (unlimited)** | For current candidate: take (stay) or skip (next)          | Combination Sum I, unbounded knapsack                      | `for i=start to end: add; backtrack(i); remove;`                                                   | start index                                 | O(n × 4ᵗ) worst         | Stay at i when taking, break if > target         |
| **Combination Sum (at most once + duplicates)** | Take or skip, but skip duplicates at same level            | Combination Sum II, subsets with duplicates                | `if (i>start && nums[i]==nums[i-1]) continue;`<br>`add; backtrack(i+1); remove;`                   | start index                                 | O(n × 2ⁿ)               | Sort + skip duplicates, i+1 always               |
| **Permutations (unique)**     | Choose any unused element for current position             | Permutations I, all arrangements                           | `for i=0 to n-1: if (!used[i]) add; used[i]=true; backtrack(); used[i]=false; remove;`             | used[] array                                | O(n × n!)               | used array or swap method                        |
| **Permutations (with duplicates)** | Same + skip duplicates at same level                      | Permutations II                                            | `if (i>start && nums[i]==nums[i-1]) continue;`<br>`swap(start,i); backtrack(start+1); swap back;` | start index + sorted array                  | O(n × n!)               | Sort + skip identical at same level              |
| **Letter Combinations**       | For current digit: try each possible letter                | Phone number letters, similar mapping problems             | `String letters = mapping[digit-'0'];`<br>`for char c : letters: append c; backtrack(index+1); delete last;` | index in digits                             | O(4ᵈ) where d = digits.length | StringBuilder for efficiency                     |
| **Palindrome Partitioning**   | From current start: try every possible end where substring is palindrome | All ways to partition string into palindromes              | `for end=start to n-1: if isPalindrome(start,end): add substr; backtrack(end+1); remove;`         | start index                                 | O(n × 2ⁿ)               | On-the-fly palindrome check or precompute DP     |
| **Rat in a Maze / Grid paths**| From current cell: try valid 4 directions, no revisit     | All paths from start to end in grid/maze                   | `for each dir: nx,ny = ...; if safe && !visited: mark; append dir; recurse; unmark; delete char;` | current position + visited[][]              | O(4^(n²)) worst         | Mark visited before recurse, unmark on backtrack |
| **Sudoku Solver**             | At next empty cell: try 1-9, check row/col/box safety     | Fill Sudoku board (constraint satisfaction)               | `if row==9 return true; if filled skip; for 1-9: if safe place; if recurse true return true; remove;` | current position (row,col)                  | O(9^d) d=empty cells    | Naive O(27) check or 3 arrays/bitmasks for O(1)  |

### Quick guide: When to use which pattern?

| Problem type / keyword in question                              | Most likely pattern(s) to think of first                     |
|------------------------------------------------------------------|---------------------------------------------------------------|
| All subsets / power set                                          | Subsets / Power Set                                           |
| All combinations that sum to target (unlimited reuse)           | Combination Sum (stay at i)                                   |
| All combinations that sum to target (at most once)               | Combination Sum II (i+1 + skip duplicates if needed)          |
| All permutations of array                                        | Permutations (used[] or swap)                                 |
| All ways to form string from digit mapping                       | Letter Combinations (for each digit → try each letter)        |
| All ways to partition string into valid parts (palindromes, words) | Palindrome Partitioning / Word Break style (try different end positions) |
| Find all paths from start to end in grid/maze (no revisit)       | Rat in a Maze / Grid paths (4 directions + visited)           |
| Fill board with constraints (Sudoku, N-Queens, crosswords)      | Sudoku Solver / N-Queens (try choices per cell/row)           |

### Most reusable boilerplate snippets

1. **Subsets / Combination style** (start index)

```java
for (int i = start; i < n; i++) {
    if (i > start && nums[i] == nums[i-1]) continue; // if duplicates
    path.add(nums[i]);
    backtrack(i + 1 /* or i */, ...);
    path.removeLast();
}
```

2. **Permutations style** (used array)

```java
for (int i = 0; i < n; i++) {
    if (used[i]) continue;
    used[i] = true;
    path.add(nums[i]);
    backtrack(...);
    path.removeLast();
    used[i] = false;
}
```

3. **Grid / Maze style** (4 directions)

```java
int[] dRow = {-1,0,1,0};
int[] dCol = {0,1,0,-1};
char[] dirs = {'U','R','D','L'};

for (int d = 0; d < 4; d++) {
    int nr = r + dRow[d];
    int nc = c + dCol[d];
    if (valid(nr,nc) && !visited[nr][nc] && grid[nr][nc] == 1) {
        visited[nr][nc] = true;
        path.append(dirs[d]);
        backtrack(nr, nc, ...);
        path.deleteLast();
        visited[nr][nc] = false;
    }
}
```

4. **Sudoku / N-Queens style** (try 1 to n per position)

```java
for (int val = 1; val <= 9; val++) {
    if (isSafe(row, col, val)) {
        place val;
        if (backtrack(next)) return true;
        remove val;
    }
}
return false;
```

### Final advice before we move on

Print/save this summary somewhere.  
Before every backtracking problem, ask yourself:

- Am I building a **subset/combination** (take/skip)?
- Am I building a **permutation** (choose any unused)?
- Am I **covering a sequence** (string/grid partitioning, maze path)?
- Am I filling a **board/grid with constraints** (Sudoku/N-Queens)?
- Do I need to **avoid duplicates**? (sort + skip)
- What is the **state** I must track? (index/start, used[], visited[][], current sum/path, etc.)

You now have a very strong backtracking foundation.

---


These notes include:

- Pattern name & aliases
- Core recursive idea / decision
- When to reach for this pattern (trigger words in problem)
- Classic example problems
- Boilerplate code skeleton (Java style, minimal & clean)
- State you need to track
- Typical time & space complexity
- Most common mistakes / pitfalls
- Optimization tricks / follow-up questions interviewers love

### 1. Linear / Tail Recursion (reduce by 1)

**Core idea**  
Reduce one parameter toward a base case → combine results on the way back up.

**When to use**
- Single integer parameter that decreases by 1 each time
- Classic math/combinatorial problems
- Problems that feel like “do something n times”

**Examples**
- Factorial
- Sum of digits
- Power(x, n) naive
- Reverse string / array
- Decimal to binary conversion

**Boilerplate**

```java
int f(int n) {
    if (n == 0 || n == 1) return 1;           // base case(s)
    return n * f(n - 1);                       // recursive case
}
```

**State tracked**  
Just the decreasing parameter (n)

**Complexity**  
Time: O(n)  
Space: O(n) recursion stack

**Common pitfalls**
- Forgetting base case → StackOverflowError
- Not returning the recursive result → lost computation
- Off-by-one in base case (n==0 vs n==1)

**Interview tip**  
Interviewers often ask: “Convert this recursive solution to iterative?” → use a loop + accumulator.

### 2. Divide & Conquer (logarithmic recursion)

**Core idea**  
Split problem into **half** (or constant parts), solve recursively, combine results.

**When to use**
- Problems where you can halve the input size
- Classic: exponentiation, binary search, merge sort, quicksort, closest pair of points

**Examples**
- Power(x, n) efficient (exponentiation by squaring)
- Merge sort
- Binary search
- Majority element (Boyer-Moore is greedy, but divide & conquer also works)

**Boilerplate** (power example)

```java
long power(long x, long n) {
    if (n == 0) return 1;
    if (n == 1) return x;

    long half = power(x, n / 2);
    long res = half * half;

    if (n % 2 == 1) res *= x;

    return res;
}
```

**State tracked**  
The halved parameter (n/2)

**Complexity**  
Time: O(log n)  
Space: O(log n) recursion depth

**Common pitfalls**
- Wrong handling of odd exponents
- Integer overflow (use long)
- n < 0 case (if allowed)

### 3. Subsets / Power Set / Include–Exclude

**Core idea**  
For each element: **include** it or **exclude** it → recurse on the rest.

**When to use**
- Generate all subsets
- Subset sum (existence / count / all combinations)
- Partition problems (equal sum, into k subsets)
- Combinations of fixed size k

**Examples**
- Subsets I & II
- Subset Sum (count / existence)
- Combination Sum I & II
- Partition Equal Subset Sum

**Boilerplate** (most common style)

```java
void backtrack(int start, List<Integer> path, List<List<Integer>> result, int[] nums) {
    result.add(new ArrayList<>(path));           // add at every step

    for (int i = start; i < nums.length; i++) {
        if (i > start && nums[i] == nums[i-1]) continue; // skip duplicates (II)

        path.add(nums[i]);
        backtrack(i + 1, path, result, nums);    // i+1 = at most once
        // or backtrack(i, ...)                  // unlimited reuse
        path.remove(path.size() - 1);
    }
}
```

**State tracked**  
`start` index + current path

**Complexity**  
Time: O(n × 2ⁿ)  
Space: O(n) recursion + O(n × 2ⁿ) output

**Common pitfalls**
- Forgetting to copy path → all lists become same
- Not handling duplicates → duplicate subsets
- Wrong recursion index (i vs i+1)

### 4. Permutations (choose any unused)

**Core idea**  
For current position: try **any unused** element → mark used → recurse → unmark.

**When to use**
- All possible arrangements of elements
- Problems where order matters and all items must be used exactly once

**Examples**
- Permutations I (unique)
- Permutations II (with duplicates)

**Boilerplate** (used array style)

```java
void backtrack(List<Integer> path, boolean[] used, List<List<Integer>> result, int[] nums) {
    if (path.size() == nums.length) {
        result.add(new ArrayList<>(path));
        return;
    }

    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        // For duplicates version:
        // if (i > 0 && nums[i] == nums[i-1] && !used[i-1]) continue;

        used[i] = true;
        path.add(nums[i]);
        backtrack(path, used, result, nums);
        path.remove(path.size() - 1);
        used[i] = false;
    }
}
```

**State tracked**  
`used[]` array + current path

**Complexity**  
Time: O(n × n!)  
Space: O(n) recursion + O(n × n!) output

**Common pitfalls**
- Forgetting to copy path
- Not sorting + skipping duplicates in II → duplicate permutations

### 5. Grid / Maze / Path finding (4 directions + visited)

**Core idea**  
From current cell → try 4 directions → if valid & not visited → mark → recurse → unmark

**When to use**
- Find all paths from start to end in grid/maze
- Rat in a Maze, Word Search, Number of Islands (DFS variant), Pacific Atlantic Water Flow

**Boilerplate**

```java
void dfs(int r, int c, StringBuilder path, List<String> result, boolean[][] visited, int[][] grid) {
    if (r == n-1 && c == n-1) {
        result.add(path.toString());
        return;
    }

    int[] dr = {-1, 0, 1, 0};
    int[] dc = {0, 1, 0, -1};
    char[] dirs = {'U', 'R', 'D', 'L'};

    for (int d = 0; d < 4; d++) {
        int nr = r + dr[d];
        int nc = c + dc[d];

        if (nr >= 0 && nr < n && nc >= 0 && nc < n &&
            grid[nr][nc] == 1 && !visited[nr][nc]) {

            visited[nr][nc] = true;
            path.append(dirs[d]);

            dfs(nr, nc, path, result, visited, grid);

            path.deleteCharAt(path.length() - 1);
            visited[nr][nc] = false;
        }
    }
}
```

**State tracked**  
Current position (r,c) + visited[][] + path

**Complexity**  
Time: O(4^(n×m)) worst case  
Space: O(n×m) recursion depth worst case

**Common pitfalls**
- Forgetting to mark visited before recurse
- Not unmarking → blocks other paths
- Wrong order of directions → wrong lexicographical order

### 6. Sudoku / N-Queens / Constraint Satisfaction

**Core idea**  
For next position/cell → try all possible values (1–9 or columns) → check constraints → place → recurse → backtrack

**When to use**
- Fill grid/board with constraints (Sudoku, N-Queens, Crosswords, Graph Coloring)

**Boilerplate** (Sudoku style)

```java
boolean backtrack(int row, int col, char[][] board) {
    if (row == 9) return true;

    int nr = row, nc = col + 1;
    if (nc == 9) { nr++; nc = 0; }

    if (board[row][col] != '.') {
        return backtrack(nr, nc, board);
    }

    for (char ch = '1'; ch <= '9'; ch++) {
        if (isSafe(row, col, ch, board)) {
            board[row][col] = ch;
            if (backtrack(nr, nc, board)) return true;
            board[row][col] = '.';
        }
    }
    return false;
}
```

**State tracked**  
Current position (row,col) + board itself

**Complexity**  
O(9^d) where d = empty cells

**Common pitfalls**
- Forgetting to reset cell on backtrack
- Not checking all three constraints (row, col, box)

### Summary Table: When to Reach for Which Pattern

| Trigger words / problem type                            | Go-to pattern(s)                                      |
|---------------------------------------------------------|-------------------------------------------------------|
| All subsets, subset sum, partition into subsets         | Subsets / Include–Exclude                             |
| Sum to target with repetition allowed                   | Combination Sum (stay at i)                           |
| Sum to target with at most once + duplicates            | Combination Sum II (i+1 + skip duplicates)            |
| All rearrangements / order matters                      | Permutations (used[] or swap)                         |
| Digit → letter mapping, fixed choices per position      | Letter Combinations                                   |
| Partition string into palindromes / words               | Palindrome Partitioning / Word Break style            |
| All paths in grid/maze from start to end                | Rat in a Maze / 4-direction backtracking              |
| Fill board/grid with constraints (no two attack, fill valid) | Sudoku / N-Queens style (try values per cell)         |

You now have a very strong, reusable mental framework for almost all recursion/backtracking problems asked in interviews.

Whenever you see a new problem, ask yourself:

1. Am I **generating** all possibilities? → subsets/permutations
2. Am I **covering** a sequence/grid without skipping? → partitioning/maze
3. Am I **filling** positions with constraints? → Sudoku/N-Queens
4. Do I need to **prune** early? → almost always yes
