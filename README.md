# DSA
DSA Practice.

Absolutely. That's actually a good idea.

You've just spent time understanding the **why** behind Dijkstra, Bellman-Ford, and Floyd-Warshall. If you immediately jump to MSTs, some of that intuition won't get reinforced.

I'd solve a handful of carefully chosen problems first.

---

# Floyd-Warshall Practice

These are the highest priority.

## 1. 1334. Find the City With the Smallest Number of Neighbors at a Threshold Distance

✅ Just solved.

Pattern:

```text
All-Pairs Shortest Path
+
Count reachable nodes
```

---

## 2. Floyd Warshall (GFG)

Classic implementation problem.

Pattern:

```text
Convert adjacency matrix
Run Floyd-Warshall
Return shortest path matrix
```

Good for mastering:

```java
dist[i][j] =
Math.min(dist[i][j], dist[i][k] + dist[k][j]);
```

---

## 3. 2976. Minimum Cost to Convert String I

This is one of my favorite Floyd-Warshall problems.

Pattern:

```text
Characters = nodes
Transformation cost = edge weight
```

Need:

```text
Minimum conversion cost between every pair of characters
```

Immediately:

```text
26 nodes
All pairs shortest path
```

Floyd-Warshall becomes beautiful here.

Difficulty:

```text
Medium
```

---

## 4. 2959. Number of Possible Sets of Closing Branches

Hard.

Very Floyd-Warshall heavy.

Pattern:

```text
Repeated APSP checks
```

Don't worry if it feels difficult.

---

# Bellman-Ford Practice

---

## 1. Bellman-Ford Algorithm (GFG)

Must do.

Implement Bellman-Ford from scratch.

Focus on:

```text
V-1 relaxations
+
Negative cycle detection
```

---

## 2. Network Delay Time (743)

You already discussed this recently.

Interesting exercise:

Solve using:

```text
Dijkstra
```

Then ask yourself:

```text
Could Bellman-Ford solve it?
```

Answer:

```text
Yes
```

but slower.

Good for understanding when Bellman-Ford is applicable.

---

## 3. Cheapest Flights Within K Stops (787)

One of the most important Bellman-Ford interview questions.

Seriously.

Do not skip.

Many people try Dijkstra first.

Bellman-Ford solution is elegant.

Pattern:

```text
Relax edges K+1 times
```

This problem teaches:

> Bellman-Ford isn't just for negative edges.

---

## 4. Find if Path Exists with Limited Stops

Any variant where:

```text
Maximum edges allowed
```

appears.

Bellman-Ford often sneaks in.

---

# Understanding-Oriented Problems

These are great for intuition.

---

## 1. Detect Negative Cycle in Graph (GFG)

Pattern:

```text
One extra relaxation
```

You should instantly think:

```text
Bellman-Ford
```

---

## 2. Floyd Warshall Negative Cycle Detection

Not always a standalone problem.

But practice checking:

```java
dist[i][i] < 0
```

after APSP.

---

# Suggested Order

I'd do them in this exact sequence:

```text
1. GFG Floyd Warshall
2. LeetCode 2976
3. GFG Bellman-Ford
4. Detect Negative Cycle (GFG)
5. LeetCode 787 (Cheapest Flights Within K Stops)
6. LeetCode 2959 (optional challenge)
```

---

# One Problem I Especially Recommend

If you only do **one more Bellman-Ford problem**, make it:

Cheapest Flights Within K Stops

Because after solving it you'll realize something profound:

> Bellman-Ford is not really about negative edges.
>
> Bellman-Ford is about controlling how many edges a path is allowed to use.

That insight unlocks a whole category of interview questions.

---

After those, I'd move to:

```text
Disjoint Set Union (DSU)
    ↓
Kruskal
    ↓
Prim
```

because MST questions show up much more frequently than advanced shortest-path algorithms like Johnson's.
