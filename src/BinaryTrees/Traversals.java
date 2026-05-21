package BinaryTrees;

import com.sun.source.tree.Tree;

import java.util.*;

class TreeNode{
    int data;
    TreeNode left;
    TreeNode right;

    public TreeNode(int data){
        this.data = data;
    }
}

class InorderTraversal{
    public void inorderRecursive(TreeNode root){
        if(root == null) return;
        inorderRecursive(root.left);
        System.out.print(root.data + " ");
        inorderRecursive(root.right);
    }

    public ArrayList<Integer> inorderIterative(TreeNode root){
        Stack<TreeNode> st = new Stack<>();

        ArrayList<Integer> list = new ArrayList<>();

        TreeNode current = root;
        while(true) {
            if(current != null) {
                st.push(current);
                current = current.left;
            }else{
                if(st.isEmpty()) return list;
                current = st.pop();
                list.add(current.data);
                current = current.right;
            }
        }
    }
}

class PreorderTraversal{
    public void preorderRecursive(TreeNode root){
        if(root == null) return;

        System.out.print(root.data + "  ");
        preorderRecursive(root.left);
        preorderRecursive(root.right);
    }

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    // Preorder traversal CANNOT be correctly implemented using a queue in this way
    public ArrayList<Integer> PreorderIterative(TreeNode root){
        Queue<TreeNode> que = new LinkedList<>();

        ArrayList<Integer> list = new ArrayList<>();

        TreeNode current = root;
        while(true) {
            if(current != null) {
                que.offer(current);
                current = current.left;
            }else{
                if(que.isEmpty()) return list;
                current = que.poll();
                list.add(current.data);
                current = current.right;
            }
        }
    }

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    public ArrayList<Integer> PreorderIterative2(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        stack.push(current);

        while(!stack.isEmpty()) {
            TreeNode curr =  stack.pop();
            list.add(curr.data);

            if(curr.right != null) {
                stack.push(curr.right);
            }

            if(curr.left != null) {
                stack.push(curr.left);
            }
        }

        return list;
    }
}

class PostorderTraversal{
    public void postorderRecursive(TreeNode root){
        if(root == null) return;

        postorderRecursive(root.left);
        postorderRecursive(root.right);
        System.out.print(root.data + " ");
    }

    public ArrayList<Integer> postorderIterative(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while(!stack.isEmpty()){
            TreeNode curr = stack.pop();
            list.add(curr.data);

            if(curr.left != null) {
                stack.push(curr.left);
            }

            if(curr.right != null) {
                stack.push(curr.right);
            }
        }

        Collections.reverse(list);
        return list;
    }

    public ArrayList<Integer> postorderIterative2Stack(TreeNode root){
        ArrayList<Integer> list = new ArrayList<>();

        Stack<TreeNode> st1 = new Stack<>();
        Stack<TreeNode> st2 = new Stack<>();
        if(root == null) return list;

        st1.push(root);

        while(!st1.isEmpty()){
            TreeNode curr = st1.pop();

            if(curr.left != null){
                st1.push(curr.left);
            }

            if(curr.right != null) {
                st1.push(curr.right);
            }

            st2.push(curr);
        }

        while(!st2.isEmpty()){
            list.add(st2.pop().data);
        }

        return list;
    }

    public ArrayList<Integer> preorderTraversalOneStack(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();

        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;

        while(current != null || !stack.isEmpty()){
            if(current != null){
                stack.push(current);
                current = current.left;
            }else{
                TreeNode temp = stack.peek().right;
                if(temp == null){
                    temp = stack.pop();
                    list.add(temp.data);

                    while(!stack.isEmpty() && stack.peek().right == temp){
                        temp = stack.pop();
                        list.add(temp.data);
                    }
                }else{
                    current = temp;
                }
            }
        }

        return list;
    }
}

class LevelOrderTraversal{
    public List<List<Integer>> LevelOrderTraversal(TreeNode root){
        List<List<Integer>> result = new  ArrayList<>();
        if(root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int size = queue.size();

            List<Integer> level = new ArrayList<>();
            for(int i = 0; i < size; i++){
                TreeNode node = queue.poll();
                level.add(node.data);

                if(node.left != null) queue.offer(node.left);
                if(node.right != null) queue.offer(node.right);
            }

            result.add(level);
        }

        return result;
    }
}


public class Traversals {
    public static TreeNode buildTree(Integer[] arr){ // Using Integer instead of int to handle null vaules.
        if(arr == null || arr.length == 0 || arr[0] == null) return null;

        TreeNode root = new TreeNode(arr[0]);
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        int i = 1;
        while(i < arr.length && !q.isEmpty()){
            TreeNode current = q.poll();

            // Left Child
            if(i < arr.length){
                if(arr[i] != null){
                    current.left = new TreeNode(arr[i]);
                    q.offer(current.left); // only enqueue real nodes.
                }
                i++;
            }

            // Right child
            if(i < arr.length){
                if(arr[i] != null){
                    current.right = new TreeNode(arr[i]);
                    q.offer(current.right);
                }
                i++;
            }
        }

        return root;
    }

    public static void main(String[] args){
        Integer[] arr = {1, 4, null, 4, 2};
        TreeNode root = buildTree(arr);

        InorderTraversal inorder =  new InorderTraversal();
        PreorderTraversal preorder = new  PreorderTraversal();
        PostorderTraversal postorder = new   PostorderTraversal();
        LevelOrderTraversal levelorder = new  LevelOrderTraversal();

        System.out.print("Inorder traversal: ");
        inorder.inorderRecursive(root);
        System.out.println();
        System.out.println(inorder.inorderIterative(root));

        System.out.print("Preorder traversal: ");
        preorder.preorderRecursive(root);
        System.out.println();
        System.out.println(preorder.PreorderIterative(root));
        System.out.println(preorder.PreorderIterative2(root));

        System.out.println("Postorder Traversal: ");
        postorder.postorderRecursive(root);
        System.out.println();
        System.out.println(postorder.postorderIterative(root));
        System.out.println(postorder.postorderIterative2Stack(root));
        System.out.println(postorder.preorderTraversalOneStack(root));

        System.out.println("Level order Traversal: ");
        System.out.println(levelorder.LevelOrderTraversal(root));
    }
}
