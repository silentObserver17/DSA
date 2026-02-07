### Linked List Notes – Interview Revision Guide

#### 1. Fundamentals & Operations
- **Node structure** (Singly vs Doubly)
    - Singly: `val`, `next`
    - Doubly: `val`, `prev`, `next`
- **Dummy node / sentinel** — used to simplify head/tail handling (deletion, reversal, merging, etc.)
- **Common O(1) helpers**
    - `addToFront(node)` / `addToBack(node)`
    - `removeNode(node)` — O(1) only in DLL (need `prev`)
- **Traversal pattern**: always save `next` before changing `curr.next`

#### 2. Core Patterns & Problems We Solved

| # | Problem / Pattern                              | Key Idea / Technique                              | Time   | Space     | Important Edge Cases                          | Interview Tips / Pitfalls                     |
|---|------------------------------------------------|---------------------------------------------------|--------|-----------|-----------------------------------------------|-----------------------------------------------|
| 1 | Add Two Numbers (digits reversed)              | Single pass with carry, dummy node                | O(n)   | O(max(n,m)) | Final carry, different lengths, both [0]      | Forget final carry → 999+1 = 000 (wrong)     |
| 2 | Odd Even Linked List                           | Two chains (odd & even), connect at end           | O(n)   | O(1)      | n=1, n=2, even/odd length                     | Losing chain when re-linking                  |
| 3 | Sort 0s, 1s, 2s (Dutch National Flag)         | Three chains (0,1,2), append nodes                | O(n)   | O(1)      | No 0s, no 1s, all same value                  | Missing connection when middle group empty    |
| 4 | Remove Nth from End                            | Fast-slow pointers (gap = n), dummy node          | O(n)   | O(1)      | n = length (delete head), n=1                 | Forgetting dummy → head deletion fails        |
| 5 | Reverse Linked List                            | Three pointers (prev, curr, next) or recursion    | O(n)   | O(1) iter / O(n) rec | n=1, n=2, empty                              | Forgetting to save next → lose rest of list   |
| 6 | Reverse in Groups of K                         | Reverse k nodes, connect groups, recursion or iter | O(n)   | O(1) iter / O(n/k) rec | k=1, k > length, last group < k              | Forgetting to connect tail of group to next   |
| 7 | Rotate Right by K                              | Find tail, make circular, cut at (n-k)            | O(n)   | O(1)      | k=0, k ≥ n (use k%n), n=1                    | Forgetting k % n → wrong for large k          |
| 8 | Merge Two Sorted Lists                         | Dummy + tail pointer, merge in-place              | O(n+m) | O(1)      | One/both empty, different lengths            | Setting curr.next = null → lose chain         |
| 9 | Flatten Multi-level LL                         | Recursion + merge sorted child lists              | O(n log n) | O(n) rec | Deep nesting, no child lists                 | Using child instead of next in final list     |
| 10| Sort Linked List                               | Merge sort (find mid, split, merge)               | O(n log n) | O(log n) rec | n=1, already sorted, reverse sorted          | Wrong mid → infinite recursion or overlap     |
| 11| Clone LL with Random & Next                    | HashMap old→new or interweaving (O(1) space)      | O(n)   | O(n) or O(1) | Random to self, random null, cycles          | Forgetting to set random → wrong structure    |
| 12| Delete all occurrences in DLL                  | Dummy + one pass, don't move prev on delete       | O(n)   | O(1)      | All same, delete head/tail, consecutive dups | Moving prev on delete → skip nodes            |
| 13| Remove Duplicates from Sorted DLL              | One pass, compare consecutive, don't move prev on dup | O(n)   | O(1)      | All same, duplicates at head/tail            | Moving prev on dup → skip multiple dups       |
| 14| LRU Cache                                      | HashMap + DLL (sentinels), move to front on access | O(1)   | O(capacity) | Capacity=1, update existing, evict LRU       | Forgetting map.remove on eviction             |

### Quick Revision – Most Asked Patterns

| Pattern                     | Problems Where It Appears                              | Key Trick / Code Snippet                                      |
|-----------------------------|--------------------------------------------------------|----------------------------------------------------------------|
| Dummy / Sentinel node       | Delete head, merge lists, reverse groups, delete all   | `dummy.next = head; return dummy.next;`                        |
| Fast-Slow Pointers          | Middle node, nth from end, cycle detection             | `fast = head.next`, `while fast && fast.next`                 |
| Three Pointers Reversal     | Reverse list, reverse groups                           | `next = curr.next; curr.next = prev; prev = curr; curr = next` |
| Merge Two Sorted            | Merge lists, flatten multilevel                        | `if (l1.val <= l2.val) temp.next = l1; else temp.next = l2`   |
| Find Middle & Split         | Sort list (merge sort), palindrome check               | `slow = head, fast = head.next` → `mid.next = null`           |
| HashMap + Node Mapping      | Clone with random, intersection node                   | `map.put(oldNode, newNode)`                                   |
| Interweaving Nodes          | Clone with random (O(1) space)                         | Insert copy after original → set random → separate            |

### Common Linked List Interview Tips

- Always consider **head** and **tail** cases — use dummy if it simplifies
- **Save next** before changing `curr.next` (classic bug)
- In DLL — always update **both** directions (`prev.next` and `next.prev`)
- When deleting — **don’t move prev** if next might also be deleted
- **k % n** for rotation problems with large k
- Use **sentinels** for LRU Cache, merge, delete — reduces special cases
- Practice **dry running** pointer changes on paper — interviewers love when you do it live

### Linked List – Some Detailed Notes

#### 1. Core Building Blocks & Techniques

- **Dummy / Sentinel Node**  
  Purpose: Simplifies head/tail operations (no special case for modifying head)  
  Typical use: Merge lists, delete nodes (especially head), reverse groups, LRU Cache  
  Pattern: `dummy.next = head; return dummy.next;`

- **Fast-Slow Pointers (Tortoise & Hare)**  
  Use cases:
    - Find middle (slow = head, fast = head.next)
    - Remove nth from end (fast moves n steps ahead first)
    - Cycle detection / cycle start / cycle length  
      Tip: `fast = head.next` often used to make slow land on left half in even length

- **Three Pointers for Reversal**
  ```java
  ListNode prev = null, curr = head, next = null;
  while (curr != null) {
      next = curr.next;
      curr.next = prev;
      prev = curr;
      curr = next;
  }
  return prev;
  ```
  Tip: Always save `next` **before** changing `curr.next`

- **Merge Two Sorted Lists**
  ```java
  ListNode dummy = new ListNode(0);
  ListNode tail = dummy;
  while (l1 != null && l2 != null) {
      if (l1.val <= l2.val) {
          tail.next = l1; l1 = l1.next;
      } else {
          tail.next = l2; l2 = l2.next;
      }
      tail = tail.next;
  }
  tail.next = l1 != null ? l1 : l2;
  return dummy.next;
  ```

- **Find Middle & Split**
  ```java
  ListNode slow = head, fast = head.next;
  while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
  }
  ListNode right = slow.next;
  slow.next = null;
  ```

### Final Interview Checklist for Linked Lists

- Always consider **head** and **tail** — use dummy if it helps
- **Save next** before changing `curr.next` (classic bug)
- In DLL: **always update both directions**
- When deleting duplicates / occurrences: **do NOT move prev** if next might be deleted
- Use **modulo** for rotation problems with large k
- In merge sort: **mid.next = null** to split correctly
- In LRU Cache: **move to front on get/put**, **evict tail.prev** on overflow
- For random pointer clone: **hashmap is safe**, **interweaving is clever but error-prone**

