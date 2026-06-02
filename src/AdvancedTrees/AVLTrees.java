package AdvancedTrees;

/**
 * AVL Tree — Self-Balancing Binary Search Tree
 *
 * Invariant : |height(left) - height(right)| <= 1  for every node
 * Guarantee : O(log n) insert / delete / search  (height ≤ 1.44·log₂n)
 *
 * Sections
 * ─────────
 * 1.  Node  (inner class)
 * 2.  Core helpers  (height, balanceFactor, updateHeight)
 * 3.  Rotations     (rotateRight, rotateLeft)
 * 4.  Rebalance     (decides which rotation to apply)
 * 5.  Insert
 * 6.  Delete
 * 7.  Search / Min / Max / Contains
 * 8.  Traversals    (inorder, preorder, postorder, levelorder)
 * 9.  Pretty-print  (sideways tree diagram in console)
 * 10. main          (demo)
 */

public class AVLTrees {
    // ──────────────────────────────────────────────────────────────
    // 1. Node
    // ──────────────────────────────────────────────────────────────
    private static class Node {
        int val;
        int height;
        Node left, right;

        public Node(int val) {
            this.val = val;
        }
    }

    private Node root;

    // ──────────────────────────────────────────────────────────────
    // 2. Core helpers
    // ──────────────────────────────────────────────────────────────

    /** Height of a node; null → 0 */
    private int height(Node n) {
        return (n == null) ? 0 : n.height;
    }

    /**
     * Balance Factor = height(left) - height(right)
     *  +2 → left-heavy violation
     *  -2 → right-heavy violation
     */

    private int bf(Node n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    /** Recalculate height from children (call after any structural change). */
    private void updateHeight(Node n) {
        if (n != null) {
            n.height = 1 + Math.max(height(n.left), height(n.right));
        }
    }

    // ──────────────────────────────────────────────────────────────
    // 3. Rotations
    // ──────────────────────────────────────────────────────────────

    /**
     * Right Rotation  (fixes LL case)
     *
     *      y                  x
     *     / \               /   \
     *    x   T3    →→→    T1     y
     *   / \                     / \
     *  T1  T2                 T2  T3
     *
     * @return new root of this subtree (x)
     */

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // perform rotation
        x.right = y;
        y.left = T2;

        //heights: y first(it's now lower) and then x
        updateHeight(y);
        updateHeight(x);

        return x; // x is now the new subtree root.
    }

    /**
     * Left Rotation  (fixes RR case)
     *
     *    x                      y
     *   / \                   /   \
     *  T1   y       →→→      x    T3
     *      / \              / \
     *     T2  T3           T1  T2
     *
     * @return new root of this subtree (y)
     */

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // perform rotation
        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    // ──────────────────────────────────────────────────────────────
    // 4. Rebalance
    // ──────────────────────────────────────────────────────────────
    /**
     * After insert/delete, update height and apply the correct rotation.
     *
     *  BF = +2, child BF >= 0  →  LL  →  single right rotation
     *  BF = +2, child BF  < 0  →  LR  →  left-rotate child, then right-rotate node
     *  BF = -2, child BF <= 0  →  RR  →  single left rotation
     *  BF = -2, child BF  > 0  →  RL  →  right-rotate child, then left-rotate node
     */

    private Node rebalance(Node n) {
        updateHeight(n);
        int balance = bf(n);

        // ── Left-heavy ──────────────────────────────
        if(balance == 2) {
            if(bf(n.left) < 0) {
                n.left = rotateLeft(n.left);    //   first: left-rotate left child
            }
            return rotateRight(n);              // LL (or after LR fix): right-rotate
        }

        // ── Right-heavy ─────────────────────────────
        if (balance == -2) {
            if (bf(n.right) > 0) {              // RL case
                n.right = rotateRight(n.right); //   first: right-rotate right child
            }
            return rotateLeft(n);               // RR (or after RL fix): left-rotate
        }

        return n;  // already balanced
    }

    // ──────────────────────────────────────────────────────────────
    // 5. Insert
    // ──────────────────────────────────────────────────────────────
    public void insert(int val) {
        root = insert(root, val);
    }

    private Node insert(Node n, int val) {
        if(n == null) {
            return new Node(val);
        }

        if      (val < n.val) n.left  = insert(n.left,  val);
        else if (val > n.val) n.right = insert(n.right, val);
        else                  return n;   // duplicate → ignore

        // ── Rebalance on the way back up ─────────────
        return rebalance(n);
    }

    // ──────────────────────────────────────────────────────────────
    // 6. Delete
    // ──────────────────────────────────────────────────────────────

    public void delete(int val) {
        root = delete(root, val);
    }

    private Node delete(Node n, int val) {
        if (n == null) return null;   // value not found

        if (val < n.val) {
            n.left  = delete(n.left,  val);
        } else if (val > n.val) {
            n.right = delete(n.right, val);
        } else {
            // ── Found the node to delete ─────────────

            // Case A: one or zero children
            if (n.left  == null) return n.right;
            if (n.right == null) return n.left;

            // Case B: two children
            //   Replace value with in-order successor (smallest in right subtree)
            //   then delete that successor from the right subtree.
            Node successor = minNode(n.right);
            n.val          = successor.val;
            n.right        = delete(n.right, successor.val);
        }

        return rebalance(n);
    }

    // ──────────────────────────────────────────────────────────────
    // 7. Search / Min / Max / Contains
    // ──────────────────────────────────────────────────────────────
    public boolean contains(int val) {
        return contains(root, val);
    }

    private boolean contains(Node n, int val) {
        if (n == null)       return false;
        if (val == n.val)    return true;
        if (val  < n.val)    return contains(n.left,  val);
        return                      contains(n.right, val);
    }

    public int min() {
        if (root == null) throw new RuntimeException("Empty tree");
        return minNode(root).val;
    }

    private Node minNode(Node n) {
        while (n.left != null) n = n.left;
        return n;
    }

    public int max() {
        if (root == null) throw new RuntimeException("Empty tree");
        Node n = root;
        while (n.right != null) n = n.right;
        return n.val;
    }

    public int height() { return height(root); }

    // ──────────────────────────────────────────────────────────────
    // 8. Traversals
    // ──────────────────────────────────────────────────────────────

    /** Inorder  (L → Node → R)  → sorted ascending */
    public void inorder() {
        System.out.print("Inorder   : ");
        inorder(root);
        System.out.println();
    }
    private void inorder(Node n) {
        if (n == null) return;
        inorder(n.left);
        System.out.printf("%d(h%d,bf%+d) ", n.val, n.height, bf(n));
        inorder(n.right);
    }

    /** Preorder  (Node → L → R) */
    public void preorder() {
        System.out.print("Preorder  : ");
        preorder(root);
        System.out.println();
    }
    private void preorder(Node n) {
        if (n == null) return;
        System.out.printf("%d(h%d,bf%+d) ", n.val, n.height, bf(n));
        preorder(n.left);
        preorder(n.right);
    }

    /** Postorder (L → R → Node) */
    public void postorder() {
        System.out.print("Postorder : ");
        postorder(root);
        System.out.println();
    }
    private void postorder(Node n) {
        if (n == null) return;
        postorder(n.left);
        postorder(n.right);
        System.out.printf("%d(h%d,bf%+d) ", n.val, n.height, bf(n));
    }

    /** Level-order (BFS) */
    public void levelOrder() {
        System.out.print("LevelOrder: ");
        if (root == null) { System.out.println("(empty)"); return; }
        java.util.Queue<Node> q = new java.util.LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            Node n = q.poll();
            System.out.printf("%d(h%d,bf%+d) ", n.val, n.height, bf(n));
            if (n.left  != null) q.offer(n.left);
            if (n.right != null) q.offer(n.right);
        }
        System.out.println();
    }

    // ──────────────────────────────────────────────────────────────
    // 9. Pretty-print (rotated 90° — right subtree on top)
    // ──────────────────────────────────────────────────────────────

    public void print() {
        System.out.println("Tree (rotated 90° — right is up):");
        print(root, "", false);
        System.out.println();
    }

    private void print(Node n, String prefix, boolean isLeft) {
        if (n == null) return;
        print(n.right, prefix + (isLeft ? "│   " : "    "), false);
        System.out.printf("%s%s[%d | h=%d bf=%+d]%n",
                prefix, isLeft ? "└── " : "┌── ", n.val, n.height, bf(n));
        print(n.left,  prefix + (isLeft ? "    " : "│   "), true);
    }


    // ──────────────────────────────────────────────────────────────
    // 10. main — Demo
    // ──────────────────────────────────────────────────────────────

    public static void main(String[] args) {
        AVLTrees t = new AVLTrees();

        System.out.println("════════════════════════════════════════");
        System.out.println("  AVL Tree Demo");
        System.out.println("════════════════════════════════════════");

        // ── Insert (triggers LL, RR, LR, RL rotations) ────────────
        int[] values = {30, 20, 10, 25, 40, 50, 5, 15, 35, 45};
        System.out.println("\nInserting: 30 20 10 25 40 50 5 15 35 45");
        for (int v : values) {
            t.insert(v);
            System.out.printf("  insert(%2d) → height=%d%n", v, t.height());
        }

        System.out.println();
        t.print();

        System.out.println("── Traversals ───────────────────────────");
        t.inorder();
        t.preorder();
        t.postorder();
        t.levelOrder();

        System.out.println("\n── Contains ─────────────────────────────");
        System.out.println("  contains(25) → " + t.contains(25));  // true
        System.out.println("  contains(99) → " + t.contains(99));  // false

        System.out.println("\n── Min / Max ────────────────────────────");
        System.out.println("  min = " + t.min() + "  max = " + t.max());

        // ── Delete ────────────────────────────────────────────────
        System.out.println("\n── Delete ───────────────────────────────");

        System.out.println("  delete(10) — leaf node");
        t.delete(10);
        t.print();
        t.inorder();

        System.out.println("  delete(30) — node with two children (uses in-order successor)");
        t.delete(30);
        t.print();
        t.inorder();

        System.out.println("  delete(20) — triggers rebalance");
        t.delete(20);
        t.print();
        t.inorder();

        // ── Sorted insert stress test ──────────────────────────────
        System.out.println("════════════════════════════════════════");
        System.out.println("  Sorted Insert Test (1..10)");
        System.out.println("  (would degenerate a plain BST to O(n))");
        System.out.println("════════════════════════════════════════");
        AVLTrees sorted = new AVLTrees();
        for (int i = 1; i <= 10; i++) sorted.insert(i);
        sorted.print();
        sorted.inorder();
        System.out.printf("  Height = %d  (plain BST would be 10)%n", sorted.height());
    }

}
