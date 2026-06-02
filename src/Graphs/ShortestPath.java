package Graphs;

import java.lang.reflect.Array;
import java.util.*;

public class ShortestPath {
    record Path(int distance, int edge){}

    public int[] dijkstraShortestPath(int[][] graph, int source, int V) {
        List<List<Path>> adj = new ArrayList<>();

        for(int i = 0; i < V; i++){
            adj.add(new ArrayList<>());
        }

        for(int i = 0; i < graph.length; i++){
            int u = graph[i][0];
            int v = graph[i][1];
            int wt = graph[i][2];

            adj.get(u).add(new Path(wt, v));
            adj.get(v).add(new Path(wt, u));
        }

        PriorityQueue<Path> pq = new PriorityQueue<>((a, b) -> a.distance - b.distance);
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);

        pq.offer(new Path(0, source));
        dist[source] = 0;

        while (!pq.isEmpty()){
            Path p = pq.poll();
            int distance =  p.distance;
            int edge = p.edge;

            if(distance > dist[edge]) continue;

            for(Path it: adj.get(edge)){
                int edgeWt =  it.distance;
                int adjNode = it.edge;

                if(distance + edgeWt < dist[adjNode]){
                    dist[adjNode] = distance + edgeWt;
                    pq.offer(new Path(dist[adjNode], adjNode));
                }
            }
        }

        return dist;
    }

    int[] rowIndex = {-1, -1, 0, 1, 1, 1, 0, -1};
    int[] colIndex = {0, 1, 1, 1, 0, -1, -1, -1};

    public boolean pathReachable(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        if (grid[0][0] == 1) return false;

        boolean[][] visited = new boolean[n][m];

        if (pathReachable(0, 0, n, m, grid, visited)) {
            return true;
        }

        return false;
    }

    private boolean pathReachable(int row, int col, int n, int m, int[][]grid, boolean[][] visited){
        if(row == n - 1 && col == m - 1){
            return true;
        }

        visited[row][col] = true;

        for(int i = 0; i < 8; i++) {
            int nrow = row + rowIndex[i];
            int ncol = col + colIndex[i];

            if(nrow >= 0 && nrow < n && ncol >= 0 && ncol < m && grid[nrow][ncol] == 0 && !visited[nrow][ncol]){
                if (pathReachable(nrow, ncol, n, m, grid, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    record MatrixPath(int row, int col, int dist){}

    public int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;

        if(grid[0][0] == 1 || grid[n-1][n-1] == 1) return -1;

        Queue<MatrixPath> que = new LinkedList<>();
        boolean[][]vis = new boolean[n][n];

        que.offer(new MatrixPath(0, 0, 1));
        vis[0][0] = true;

        while(!que.isEmpty()){
            MatrixPath p = que.poll();
            int row = p.row;
            int col = p.col;
            int dist = p.dist;

            if(row == n - 1 && col == n- 1) {
                return dist;
            }

            for(int i = 0; i < 8; i++){
                int nrow = row + rowIndex[i];
                int ncol = col + colIndex[i];

                if(nrow >= 0 && nrow < n && ncol >= 0 && ncol < n && grid[nrow][ncol] == 0 && !vis[nrow][ncol]){
                       vis[nrow][ncol] = true;
                       que.offer(new MatrixPath(nrow, ncol, dist + 1));
                }
            }
        }

        return -1;
    }

    public int MaximumPathEffort(int[][] heights) {
        int n = heights.length;
        int m = heights[0].length;

        int[][] dist = new int[n][m];
        for(int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }

        PriorityQueue<MatrixPath> que = new PriorityQueue<>((x,y) -> x.dist - y.dist);

        dist[0][0] = 0;
        que.offer(new MatrixPath(0, 0, 0));

        int[] rowIndex = {-1, 0, 1, 0};
        int[] colIndex = {0, 1, 0, -1};

        while(!que.isEmpty()){
            MatrixPath p = que.poll();
            int row = p.row;
            int col = p.col;
            int distance = p.dist;

            if(row == n - 1 && col == m - 1){
                return distance;
            }

            for(int i = 0; i < 4; i++){
                int nrow = row + rowIndex[i];
                int ncol = col + colIndex[i];

                if(nrow >= 0 && nrow < n && ncol >= 0 && ncol < m) {
                    int minDistance = Math.max(distance, Math.abs(heights[row][col] - heights[nrow][ncol]));

                    if(minDistance < dist[nrow][ncol]){
                        dist[nrow][ncol] = minDistance;
                        que.offer(new MatrixPath(nrow, ncol, minDistance));
                    }
                }
            }
        }
        return 0;
    }

    public List<Integer> PrintShortestPath(int[][] graph, int source, int V, int destination) {
        int n = graph.length;
        int m = graph[0].length;

        List<List<Path>> adj = new ArrayList<>();

        for(int i = 0; i < V; i++){
            adj.add(new ArrayList<>());
        }

        for(int i = 0; i < graph.length; i++){
            int u = graph[i][0];
            int v = graph[i][1];
            int wt = graph[i][2];

            adj.get(u).add(new Path(wt, v));
            adj.get(v).add(new Path(wt, u));
        }

        PriorityQueue<Path> pq = new PriorityQueue<>((x,y) -> x.distance - y.distance);
        int[] dist = new  int[V];
        int[] parent = new int[V];


        Arrays.fill(dist, Integer.MAX_VALUE);
        for(int i =0; i < V; i++) {
            parent[i] = i;
        }

        pq.offer(new Path(0, source));
        dist[source] = 0;

        while(!pq.isEmpty()){
            Path p = pq.poll();
            int distance = p.distance;
            int node = p.edge;

            if(distance > dist[node]) continue;

            for(Path it: adj.get(node)){
                int edgeWt = it.distance;
                int adjNode = it.edge;

                if(edgeWt + distance < dist[adjNode]){
                    dist[adjNode] = distance + edgeWt;
                    parent[adjNode] = node;
                    pq.offer(new Path(dist[adjNode], adjNode));
                }
            }
        }

        List<Integer> path = new  ArrayList<>();
        int node = destination;

        if(dist[destination] == Integer.MAX_VALUE){
            return List.of(-1);
        }

        while(parent[node] != node){
            path.add(node);
            node = parent[node];
        }

        path.add(node);
        Collections.reverse(path);

        return path;
    }

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        Queue<int[]> queue = new LinkedList<>();
        // {stops, edge, cost}
        List<List<int[]>> adj = new ArrayList<>();
        for(int i = 0; i < n; i++){
            adj.add(new ArrayList<>());
        }

        for(int i = 0; i < flights.length; i++){
            int u =  flights[i][0];
            int v = flights[i][1];
            int wt = flights[i][2];

            adj.get(u).add(new int[]{v, wt});
        }

        int[] dist = new  int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);

        queue.offer(new int[]{0, src, 0});
        dist[src] = 0;

        while(!queue.isEmpty()){
            int[] curr =  queue.poll();
            int stop = curr[0];
            int node =  curr[1];
            int cost =  curr[2];

            for(int[] it : adj.get(node)){
                int adjNode = it[0];
                int adjCost = it[1];

                if(cost + adjCost < dist[adjNode] && stop <= k){
                    dist[adjNode] = cost + adjCost;
                    queue.offer(new int[]{stop + 1, adjNode, dist[adjNode]});
                }
            }
        }

        if(dist[dst] == Integer.MAX_VALUE){
            return -1;
        }

        return dist[dst];
    }

    public int networkDelayTime(int[][] times, int n, int k) {
        List<List<int[]>> adj =  new ArrayList<>();
        for(int i = 0; i <= n; i++){
            adj.add(new ArrayList<>());
        }

        for(int i = 0; i < times.length; i++){
            int u = times[i][0];
            int v = times[i][1];
            int wt = times[i][2];

            adj.get(u).add(new int[]{v, wt});
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((x,y) -> x[1] - y[1]);
        pq.offer(new int[]{k, 0});

        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;

        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            int node = curr[0];
            int cost = curr[1];

            if(cost > dist[node]) continue;

            for(int[] it : adj.get(node)){
                int adjNode = it[0];
                int adjCost = it[1];

                if(cost + adjCost < dist[adjNode]){
                    dist[adjNode] = cost + adjCost;
                    pq.offer(new int[]{adjNode, dist[adjNode]});
                }
            }
        }

        int maxTime = 0;
        for(int i = 1; i < dist.length; i++){
            if(dist[i] == Integer.MAX_VALUE){
                return -1;
            }
            maxTime = Math.max(maxTime, dist[i]);
        }

        return maxTime;
    }

    public int waysToArriveDestination(int n, int[][] roads) {
        List<List<long[]>> adj = new ArrayList<>();

        for(int i = 0; i < n; i++){
            adj.add(new ArrayList<>());
        }

        for(int i = 0; i < roads.length; i++){
            int u = roads[i][0];
            int v = roads[i][1];
            int wt = roads[i][2];

            adj.get(u).add(new long[]{v, wt});
            adj.get(v).add(new long[]{u, wt});
        }

        PriorityQueue<long[]> pq = new PriorityQueue<>((x,y) -> Long.compare(x[1], y[1]));

        long[] dist = new long[n];
        long[] ways = new long[n];

        Arrays.fill(dist, Long.MAX_VALUE);

        pq.offer(new long[]{0, 0});
        dist[0] = 0;
        ways[0] = 1;

        int mod = (int)(1e9 + 7);

        while(!pq.isEmpty()){
            long[] curr = pq.poll();
            int node = (int)curr[0];
            long cost = curr[1];

            if(cost >  dist[node]) continue;

            for(long[] edge: adj.get(node)) {
                int adjNode = (int)edge[0];
                long adjCost = edge[1];

                if(cost + adjCost < dist[adjNode]){
                    dist[adjNode] = cost + adjCost;
                    // All shortest paths to adjNode
                    // currently come through node
                    ways[adjNode] = ways[node];

                    pq.offer(new long[]{adjNode, dist[adjNode]});
                }else if(dist[adjNode] == cost + adjCost){
                    ways[adjNode] = (ways[node] + ways[adjNode]) % mod;
                }
            }
        }

        return (int)ways[n - 1];
    }

    public int minSteps(int[] arr, int start, int end) {
        Queue<int[]> que = new LinkedList<>();
        que.offer(new int[]{start, 0});

        int[]dist = new int[100000];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        int mod = 100000;

        while(!que.isEmpty()){
            int[] curr = que.poll();
            int node = curr[0];
            int steps = curr[1];

            if(node == end) return steps;

            for(int factor : arr) {
                int num = (node * factor) % mod;

                if(steps + 1 < dist[num]){
                    dist[num] = steps + 1;
                    que.offer(new int[]{num, steps + 1});
                }
            }
        }

        return -1;
    }

    public int[] bellmanFord(int V, int[][]edges, int src) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[src] = 0;

        for(int i = 0; i < V - 1; i++){
            for(int[] edge : edges){
                int u = edge[0];
                int v = edge[1];
                int wt = edge[2];

                if(dist[u] != Integer.MAX_VALUE && dist[u] + wt < dist[v]){
                    dist[v] = dist[u] + wt;
                }
            }
        }

        // extra pass for negative cycle detection.
        for(int[] edge: edges){
            int u = edge[0];
            int v = edge[1];
            int wt = edge[2];

            if(dist[u] != Integer.MAX_VALUE && dist[u] + wt < dist[v]){
                System.out.println("Negative Cycle Found...");
            }
        }

        return dist;
    }

    public void FloydWarshall(int V, int[][] dist) {
        for(int k = 0; k < V; k++){
            for(int i = 0; i < V; i++){
                for(int j = 0; j < V; j++){
                    if(dist[i][k] == Integer.MAX_VALUE || dist[k][j] == Integer.MAX_VALUE){
                        continue;
                    }

                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
    }

    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] dist = new int[n][n];
        for(int i = 0; i < n; i++){
            Arrays.fill(dist[i], Integer.MAX_VALUE);
            dist[i][i] = 0;
        }

        for(int i = 0; i < edges.length; i++){
            int u = edges[i][0];
            int v = edges[i][1];
            int wt = edges[i][2];

            dist[u][v] = wt;
            dist[v][u] = wt;
        }

        for(int k = 0; k < n; k++) {
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    if(dist[i][k] == Integer.MAX_VALUE || dist[k][j] == Integer.MAX_VALUE){
                        continue;
                    }
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }

        int cityCount = n;
        int cityNumber = -1;

        for(int i = 0; i < n; i++){
            int count = 0;
            for(int j = 0; j < n; j++){
                if(i != j && dist[i][j] <= distanceThreshold){
                    count++;
                }
            }
            if(count <= cityCount){
                cityNumber = i;
                cityCount = count;
            }
        }

        return cityNumber;
    }

    public long minimumCostConvertString(String source, String target, char[] original, char[] changed, int[] cost) {
        long[][] dist = new long[26][26];
        int INF = (int)1e9;

        for(int i = 0; i < 26; i++){
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }

        for(int i = 0; i < original.length; i++){
            int u = original[i] - 'a';
            int v = changed[i] - 'a';
            int wt = cost[i];

            dist[u][v] = Math.min(dist[u][v], wt);
        }

        for(int k = 0; k < 26; k++){
            for(int i = 0; i < 26; i++){
                for(int j = 0; j < 26; j++){
                    if(dist[i][k] == INF || dist[k][j] == INF){
                        continue;
                    }

                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }

        long answer = 0;

        for(int i = 0; i < source.length(); i++){
            char src = source.charAt(i);
            char dst = target.charAt(i);

            if(dist[src - 'a'][dst - 'a'] == INF){
                return -1;
            }
            if(src == dst) {
                continue;
            }

            answer+= dist[src - 'a'][dst - 'a'];
        }

        return answer;
    }

    public int CheapestFlightBellmanFord(int n, int[][]flights, int src, int dst, int k) {
        int INF = (int)1e9;

        int[] dist = new int[n];
        Arrays.fill(dist, INF);

        dist[src] = 0;

        for(int i = 0; i <= k; i++){
            int[] temp = dist.clone();
            for(int[] flight: flights){
                int u = flight[0];
                int v = flight[1];
                int wt = flight[2];

                if(dist[u] == INF) continue;

                temp[v] = Math.min(temp[v], dist[u] + wt);
            }

            dist = temp;
        }

        return dist[dst] == INF ? -1 : dist[dst];
    }

    public int networkDelayTimeBellmanFord(int[][] times, int n, int k) {
        int INF = (int)1e9;

        int[] dist = new int[n + 1];
        Arrays.fill(dist, INF);

        dist[k] = 0;

        for(int i = 1; i <= n - 1; i++){
            int[] temp =  dist.clone();
            for(int[] edge : times) {
                int u = edge[0];
                int v = edge[1];
                int wt = edge[2];

                if(dist[u] == INF) continue;

                temp[v] =  Math.min(temp[v], dist[u] + wt);
            }

            dist = temp;
        }

        int answer = 0;
        for(int i = 1; i <= n; i++){
            if(dist[i] == INF) return -1;

            answer = Math.max(answer, dist[i]);
        }

        return answer;
    }

}
