package LinkedList;

import java.util.HashMap;
import java.util.Map;

public class DoubleLL {
    class ListNode {
        int val;
        ListNode next;
        ListNode prev;

        ListNode() {
            val = 0;
            next = null;
            prev = null;
        }

        ListNode(int data1) {
            val = data1;
            next = null;
            prev = null;
        }

        ListNode(int data1, ListNode next1, ListNode prev1) {
            val = data1;
            next = next1;
            prev = prev1;
        }
    }

    public ListNode deleteAllOccurrences(ListNode head, int target) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode prev = dummy;
        ListNode curr = head;

        while (curr != null) {
            if (curr.val == target) {
                prev.next = curr.next;
                if (curr.next != null) {
                    curr.next.prev = prev;
                }

                curr = curr.next;
            } else {
                prev = curr;
                curr = curr.next;
            }
        }

        return dummy.next;
    }

    class LRUCache{
        class Node {
            int key, val;
            Node prev, next;
            Node(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }

        private int capacity;
        private Map<Integer, Node> map; // key -> node
        private Node head; // MRU (Most Recently used)
        private Node tail; // LRU (Least Recently Used)

        public LRUCache(int capacity) {
            this.capacity = capacity;
            map = new HashMap<>();
            head = new Node(0, 0); // sentinel
            tail = new Node(0, 0); // sentinel
            head.next = tail;
            tail.prev = head;
        }

        private void addToFront(Node node){
            node.next = head.next;
            node.prev = head;

            head.next.prev = node;
            head.next = node;
        }

        private void removeNode(Node node){
            if (node == null) return;

            node.prev.next = node.next;
            if(node.next != null){
                node.next.prev = node.prev;
            }

            map.remove(node.key);
        }

        public int get(int key){
            if(map.containsKey(key)){
                Node node = map.get(key);
                removeNode(node);
                addToFront(node);

                return node.val;
            }

            return -1;
        }

        public void put(int key, int value) {
            if(map.containsKey(key)){
                Node node = map.get(key);
                removeNode(node);
                node.val = value;
                addToFront(node);
                return;
            }

            if(capacity == map.size()){
                removeNode(tail.prev);
            }

            Node node = new Node(key, value);
            map.put(key, node);
            addToFront(node);
        }
    }

}
