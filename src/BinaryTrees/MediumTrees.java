package BinaryTrees;

import java.util.*;


public class MediumTrees {
    public static List<List<Integer>> VerticalOrderTraversal(TreeNode root){
        List<List<Integer>> res = new ArrayList<>();
        if(root == null) return res;

        record Pair(TreeNode node, int vertical, int horizontal){}

        TreeMap<Integer, TreeMap<Integer, PriorityQueue<Integer>>> map = new TreeMap<>();

        Deque<Pair> que =  new ArrayDeque<>();
        que.offer(new Pair(root,0,0));

        while(!que.isEmpty()){
            Pair pair = que.poll();
            int vertical = pair.vertical;
            int horizontal = pair.horizontal;
            TreeNode node = pair.node;

            map.putIfAbsent(vertical, new TreeMap<>());
            map.get(vertical).putIfAbsent(horizontal, new PriorityQueue<>());
            map.get(vertical).get(horizontal).offer(node.data);

            if(node.left != null){
                que.offer(new Pair(node.left,vertical-1,horizontal + 1));
            }

            if(node.right != null){
                que.offer(new Pair(node.right,vertical+1,horizontal + 1));
            }
        }

        for(TreeMap<Integer, PriorityQueue<Integer>> map1 : map.values()){
            List<Integer> list = new ArrayList<>();
            for(PriorityQueue<Integer> queue1 : map1.values()){
                while(!queue1.isEmpty()){
                    list.add(queue1.poll());
                }
            }

            res.add(list);
        }

        return res;
    }

    public static void main(String[] args){

    }
}
