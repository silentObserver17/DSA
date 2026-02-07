These notes are written in a way that you can easily revise or use as a quick reference before interviews.

### 1. Group Anagrams (LeetCode 49)

**Pattern**: Hashing for grouping by signature / canonical form

**Problem**: Group strings that are anagrams of each other.

**Brute Force**
- For each string, compare it with all others by sorting both and checking equality
- Time: O(n² × k log k) → TLE for n=10⁴
- Space: O(1) extra

**Optimal Approaches**

1. **Sorting-based key** (most common)
    - Sort each string → use sorted version as key in map
    - Time: **O(n × k log k)**  
      (k ≤ 100 → log k ≈ 7 → very fast in practice)
    - Space: **O(n × k)** (storing all strings + keys)

2. **Frequency count key** (strictly better asymptotically)
    - Count frequency of 26 letters → create key (string or tuple)
    - Use separator (`#`) to avoid collision when counts ≥10
    - Time: **O(n × k)**
    - Space: **O(n × k)**

**Best interview answer**  
Start with sorting version → mention frequency version as optimization (log k factor removed).

**Edge cases**
- Empty strings
- Single character
- All identical strings
- Strings with repeated characters

---

### 2. Longest Substring Without Repeating Characters (LeetCode 3)

**Pattern**: Sliding window + hash map / set (last seen index)

**Problem**: Find length of longest substring with all unique characters.

**Brute Force**
- Check every substring → use set to validate uniqueness
- Time: **O(n²)**
- Space: **O(n)**

**Optimal** — Sliding window with last-seen index

```text
left = 0
map<char, last_index>
maxLen = 0

for right in 0..n-1:
    if s[right] in map and map[s[right]] >= left:
        left = map[s[right]] + 1
    map[s[right]] = right
    maxLen = max(maxLen, right - left + 1)
```

- Time: **O(n)**
- Space: **O(min(n, charset))** → O(1) in practice (ASCII 128/256)

**Important trick**  
Use `left = max(left, lastSeen + 1)` to avoid moving left backward.

**Common mistake**  
Moving left one-by-one with set → can become O(n²) in worst case.

**Edge cases**
- All same characters → 1
- All unique → n
- Empty string → 0
- Single character → 1

---

### 3. Subarray Sum Equals K (LeetCode 560)

**Pattern**: Prefix sum + hash map (frequency of prefix sums)

**Problem**: Count number of contiguous subarrays with sum = k.

**Brute Force**
- For each i → cumulative sum from i to j
- Time: **O(n²)**
- Space: **O(1)**

**Optimal** — Prefix sum + hashmap

```text
map[prefix] = frequency
map[0] = 1   // important!

curr = 0, count = 0

for num in nums:
    curr += num
    count += map.get(curr - k, 0)
    map[curr] += 1
```

- Time: **O(n)**
- Space: **O(n)** worst case

**Why map[0] = 1?**  
To count subarrays that start from index 0.

**Key insight**  
`prefix[j] - prefix[i] == k` → `prefix[i] == prefix[j] - k`

**Edge cases**
- k = 0
- Negative numbers
- All zeros
- Single element = k

---

### 4. Longest Consecutive Sequence (LeetCode 128)

**Pattern**: HashSet + smart traversal (only start from sequence beginnings)

**Problem**: Find longest consecutive sequence in unsorted array.

**Brute Force / Naive Optimal**
- Sort → scan for consecutive runs
- Time: **O(n log n)**
- Space: **O(1)** or O(n)

**Optimal** — HashSet + start-only traversal

```text
set = all numbers
maxLen = 0

for num in nums:
    if num-1 not in set:           // start of sequence
        length = 0
        while num + length in set:
            length += 1
        maxLen = max(maxLen, length)
```

- Time: **O(n)** amortized  
  (each number visited at most ~2 times)
- Space: **O(n)**

**Why O(n) even with duplicates?**  
Duplicates cause multiple starts, but each chain is traversed a bounded number of times.

**Edge cases**
- Duplicates
- All same numbers
- Empty array
- Single element

---

### 5. Minimum Window Substring (LeetCode 76)

**Pattern**: Sliding window + two frequency maps + valid counter

**Problem**: Smallest window in s that contains all characters of t (with frequencies).

**Brute Force**
- Check every substring → validate frequencies
- Time: **O(n³)**
- Space: **O(1)**

**Optimal** — Sliding window with need/have + valid count

```text
need[128], have[128]
required = number of unique chars in t
valid = 0
left = 0

for right in 0..n-1:
    have[s[right]]++
    if have[s[right]] == need[s[right]]: valid++

    while valid == required:
        update min length & start
        have[s[left]]--
        if have[s[left]] == need[s[left]]-1: valid--
        left++
```

- Time: **O(n)**
- Space: **O(1)** (fixed array 128)

**Key points**
- `required` = # distinct characters in t
- `valid` = # characters that have ≥ required count
- Increment `valid` only when exactly reaches need[c]
- Shrink as much as possible when valid == required

**Common mistake**
- Using `valid == t.length()` → fails when t has duplicates

**Edge cases**
- t longer than s
- t empty
- No valid window
- Multiple same-length minimum windows

---

### Summary Table – Patterns & Complexities

| Problem                            | Core Pattern                        | Optimal Time | Optimal Space | Difficulty |
|------------------------------------|-------------------------------------|--------------|---------------|------------|
| Group Anagrams                     | Hashing (group by key)              | O(n·k log k) or O(n·k) | O(n·k)     | Medium     |
| Longest Substring w/o Repeating    | Sliding window + last seen          | O(n)         | O(1)          | Medium     |
| Subarray Sum Equals K              | Prefix sum + hashmap frequency      | O(n)         | O(n)          | Medium     |
| Longest Consecutive Sequence       | HashSet + sequence start trick      | O(n)         | O(n)          | Hard       |
| Minimum Window Substring           | Sliding window + two counters       | O(n)         | O(1)          | Hard       |

### Interview Advice – How to Present These

1. Always start with brute force (shows you think systematically)
2. Explain why it’s slow (usually n² or n log n)
3. Move to hashing / sliding window insight
4. Mention trade-offs (sorting vs frequency count, single vs double map, etc.)
5. Highlight edge cases you considered
6. State time & space clearly with variables defined (n = array length, k = string length)

You've now built a very strong foundation in **hashing + sliding window** patterns — these five problems cover ~80% of the hashing questions asked in FAANG interviews.

Would you like to:
- Continue with **LRU Cache** (HashMap + Doubly Linked List)?
- Start **Binary Trees / BST** problems?
- Do a few more sliding window variants?
- Or maybe do a mock interview session on one of these?

Let me know your preference!  
You're doing really well — keep this momentum going.