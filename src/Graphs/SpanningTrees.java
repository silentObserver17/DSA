package Graphs;

import java.util.*;

public class SpanningTrees {
    public int PrimsMST(int V, List<List<int[]>> adj) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        boolean[] vis = new  boolean[V];
        int totalCost = 0;
        int edgeUsed = 0;

        pq.offer(new int[]{0, 0});

        while(!pq.isEmpty() && edgeUsed < V) {
            int[] curr = pq.poll();
            int cost = curr[0];
            int node = curr[1];

            if(vis[node]) continue;

            vis[node] = true;
            edgeUsed++;
            totalCost += cost;
            for(int[] neigh:  adj.get(node)) {
                int adjCost = neigh[0];
                int adjNode = neigh[1];

                if(!vis[adjNode]) {
                    pq.offer(new int[]{adjCost, adjNode});
                }
            }
        }

        return totalCost;
    }

    class DisjointSet{
        int[] parent;
        int[] rank;

        public DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];
            for(int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int find(int u) {
            if(parent[u] != u){
                parent[u] = find(parent[u]); // compress
            }

            return parent[u];
        }

        // Union by Rank
        public boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if(rootX == rootY) return false;

            if(rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            }else if(rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            }else{
                parent[rootY] = rootX;
                rank[rootY]++;
            }

            return true;
        }

        public boolean isConnected(int x, int y) {
            return find(x) == find(y);
        }
    }

    class DisjointSetSize{
        int[] parent;
        int[] size;

        public DisjointSetSize(int n) {
            parent = new int[n];
            size = new int[n];
            for(int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public int find(int x) {
            if(parent[x] != x){
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if(rootX == rootY) return false;

            if(size[rootX] < size[rootY]) {
                parent[rootX] = rootY;
                size[rootY] += size[rootX];
            } else{
                parent[rootY] = rootX;
                size[rootX] += size[rootY];
            }

            return true;
        }
    }

    static class KruskalAlgorithm{
        int [] parent;
        int [] size;

        public KruskalAlgorithm(int V) {
            parent = new int[V];
            size = new int[V];
            for(int i = 0; i < V; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public int find(int x) {
            if(parent[x] != x){
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public boolean Union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if(rootX == rootY) return false;

            if(size[rootX] < size[rootY]) {
                parent[rootX] = rootY;
                size[rootY] += size[rootX];
            }else {
                parent[rootY] = rootX;
                size[rootX] += size[rootY];
            }

            return true;
        }

        public int KruskalAlgo(int V, int[][] edges) {
            Arrays.sort(edges, Comparator.comparingInt(a -> a[2]));

            int totalCost = 0;
            int edgesUsed = 0;

            for(int[]edge : edges) {
                int u = edge[0];
                int v = edge[1];
                int cost = edge[2];

                if(Union(u, v)) {
                    totalCost += cost;
                    edgesUsed++;
                    System.out.println("Added edge: " + u + "-" + v + " weight: " + cost);
                }else{
                    System.out.println("Skipped edge: " + u + "-" + v + " (cycle)");
                }
                if(edgesUsed == V - 1) break;
            }
            return totalCost;
        }
    }

    class NetworkConnected {
        int [] parent;
        int [] size;

        private void init(int n) {
            parent = new int[n];
            size = new int[n];

            for(int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        private int find(int x) {
            if(parent[x] != x) {
                parent[x] = find(parent[x]);
            }

            return parent[x];
        }

        private boolean Union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if(rootX == rootY) return false;

            if(size[rootX] < size[rootY]) {
                parent[rootX] = rootY;
                size[rootY] += size[rootX];
            } else {
                parent[rootY] = rootX;
                size[rootX] += size[rootY];
            }

            return true;
        }

        public int makeConnected(int n, int[][] connections) {
            int len = connections.length;

            if(len < n - 1) return -1;

            init(n);
            int redundantEdges = 0;

            for(int[] edge: connections) {
                if(!Union(edge[0], edge[1])) {
                    redundantEdges++;
                }
            }

            int k = 0;

            for(int i = 0; i < n; i++) {
                if(parent[i] == i) {
                    k++;
                }
            }

            return k - 1;
        }
    }

    class RemoveStones {
        int [] parent;
        int [] size;

        private void init(int n) {
            parent = new int[n];
            size = new int[n];

            for(int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        private int find(int x) {
            if(parent[x] != x) {
                parent[x] = find(parent[x]);
            }

            return parent[x];
        }

        private boolean Union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if(rootX == rootY) return false;

            if(size[rootX] < size[rootY]) {
                parent[rootX] = rootY;
                size[rootY] += size[rootX];
            } else {
                parent[rootY] = rootX;
                size[rootX] += size[rootY];
            }

            return true;
        }

        public int removeStones(int[][] stones) {
            int n = 10001;
            init(2 * n + 1);

            for(int[] stone : stones) {
                int row = stone[0];
                int col = n + stone[1];

                Union(row, col);
            }

            Set<Integer> components = new HashSet<>();

            for(int[] stone: stones) {
                components.add(find(stone[0]));
            }

            return stones.length - components.size();
        }
    }

    class AccountsMerge {
        int [] parent;
        int [] size;

        private void init(int n) {
            parent = new int[n];
            size = new int[n];

            for(int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        private int find(int x) {
            if(parent[x] != x) {
                parent[x] = find(parent[x]);
            }

            return parent[x];
        }

        private boolean Union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if(rootX == rootY) return false;

            if(size[rootX] < size[rootY]) {
                parent[rootX] = rootY;
                size[rootY] += size[rootX];
            } else {
                parent[rootY] = rootX;
                size[rootX] += size[rootY];
            }

            return true;
        }

        public List<List<String>> accountsMerge(List<List<String>> accounts) {
            int n = accounts.size();
            init(n);

            Map<String, Integer> map = new HashMap<>();

            for(int i = 0; i < n; i++) {
                for(int j = 1; j < accounts.get(i).size(); j++) {
                    if(!map.containsKey(accounts.get(i).get(j))){
                        map.put(accounts.get(i).get(j), i);
                    }else {
                        Union(i, map.get(accounts.get(i).get(j)));
                    }
                }
            }

            Map<Integer, List<String>> mergedMails = new HashMap<>();


            for(Map.Entry<String, Integer> entry: map.entrySet()) {
                String mail = entry.getKey();
                int node = entry.getValue();

                int parent = find(node);

                mergedMails.computeIfAbsent(parent, k -> new ArrayList<>()).add(mail);
            }

            List<List<String>> result = new ArrayList<>();
            for(Map.Entry<Integer, List<String>> entry: mergedMails.entrySet()) {
                int account =  entry.getKey();

                String name = accounts.get(account).get(0);
                List<String> mails = new ArrayList<>();
                mails.add(name);
                Collections.sort(entry.getValue());
                mails.addAll(entry.getValue());
                result.add(mails);
            }

            return result;
        }
    }

    class NumberOfIslands2 {
        int [] parent;
        int [] size;

        private void init(int n) {
            parent = new int[n];
            size = new int[n];

            for(int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        private int find(int x) {
            if(parent[x] != x) {
                parent[x] = find(parent[x]);
            }

            return parent[x];
        }

        private boolean Union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if(rootX == rootY) return false;

            if(size[rootX] < size[rootY]) {
                parent[rootX] = rootY;
                size[rootY] += size[rootX];
            } else {
                parent[rootY] = rootX;
                size[rootX] += size[rootY];
            }

            return true;
        }

        public List<Integer> numIslands2(int m, int n, int[][] positions){
            boolean[][] visited = new boolean[m][n];
            List<Integer> result = new ArrayList<>();

            int len = n * m;
            init(len);
            int numberIslands = 0;

            int[] rowIndex = {-1, 0, 1, 0};
            int[] colIndex = {0, 1, 0, -1};

            for(int[] position: positions) {
                int u = position[0];
                int v =  position[1];
                int cellIndex = u * n + v;

                if(visited[u][v]) continue;

                visited[u][v] = true;
                numberIslands++;

                for(int i = 0; i < 4; i++) {
                    int nrow = u + rowIndex[i];
                    int ncol = v +  colIndex[i];
                    int nCellIndex =  nrow * n + ncol;

                    if(nrow < 0 || nrow >= m || ncol < 0 || ncol >= n || !visited[nrow][ncol]) continue;

                    if(Union(cellIndex, nCellIndex)) {
                        numberIslands--;
                    }
                }

                result.add(numberIslands);
            }

            return result;
        }
    }
    static class MarkingLargeIsland{
        int [] parent;
        int [] size;

        private void init(int n) {
            parent = new int[n];
            size = new int[n];

            for(int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        private int find(int x) {
            if(parent[x] != x) {
                parent[x] = find(parent[x]);
            }

            return parent[x];
        }

        private boolean Union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if(rootX == rootY) return false;

            if(size[rootX] < size[rootY]) {
                parent[rootX] = rootY;
                size[rootY] += size[rootX];
            } else {
                parent[rootY] = rootX;
                size[rootX] += size[rootY];
            }

            return true;
        }

        public int largestIsland(int[][] grid) {
            int n = grid.length;
            int m = grid[0].length;

            init(m * n);

            int[] rowIndex = {-1, 0, 1, 0};
            int [] colIndex = {0, 1, 0, -1};

            boolean water = false;

            for(int i = 0; i < n; i++) {
                for(int j  = 0; j < m; j++) {
                    if(grid[i][j] == 0) {
                        water = true;
                        continue;
                    }

                    int index = i * m + j;

                    for(int k = 0; k < 4; k++) {
                        int nrow = i + rowIndex[k];
                        int ncol = j + colIndex[k];

                        if(nrow < 0 || nrow >= n || ncol < 0 || ncol >= m || grid[nrow][ncol] == 0) continue;

                        int nIndex = nrow * m + ncol;

                        Union(index, nIndex);
                    }
                }
            }

            if(!water) {
                return n*m;
            }

            int maxSize = 0;

            for(int u = 0; u < n; u++) {
                for(int v  = 0; v < m; v++) {
                    if(grid[u][v] == 0) {
                        int mergedSize = 1;

                        Set<Integer> vis = new HashSet<>();

                        for(int i = 0; i < 4; i++) {
                            int nrow = u + rowIndex[i];
                            int ncol = v + colIndex[i];

                            if(nrow < 0 || nrow >= n || ncol < 0 || ncol >= m || grid[nrow][ncol] == 0) continue;

                            int nIndex = nrow * m + ncol;

                            int parent = find(nIndex);
                            if(!vis.contains(parent)) {
                                vis.add(parent);
                                mergedSize += size[parent];
                            }
                        }

                        maxSize = Math.max(maxSize, mergedSize);
                    }
                }
            }

            return maxSize;
        }
    }


    public static void main(String[] args) {
        MarkingLargeIsland island = new MarkingLargeIsland();

        System.out.println(island.largestIsland(new int[][]{
                {1, 0},
                {0, 1}
        }));

    }
}
