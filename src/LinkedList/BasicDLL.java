package LinkedList;

import java.util.List;

public class BasicDLL {
    class ListNode {
        public int data;
        public ListNode prev;
        public ListNode next;

        public ListNode(){};

        public ListNode(int data){
            this.data = data;
        };

        public ListNode(int data, ListNode prev, ListNode next){
            this.data = data;
            this.prev = prev;
            this.next = next;
        };
    };

    public ListNode arrayToDoublyLinkedList(List<Integer> arr) {
        int len = arr.size();
        if(len == 0) return null;

        ListNode head = new ListNode(arr.get(0));
        ListNode current = head;

        for(int i = 1; i < len; i++){
            ListNode temp = new ListNode(arr.get(i));
            current.next = temp;
            temp.prev = current;
            current = temp;
        }

        return head;
    }

    public ListNode deleteHead(ListNode head) {
        if(head == null || head.next == null) return null;

        ListNode current = head;
        head = head.next;
        current.next = null;
        head.prev = null;

        return head;
    }

    public ListNode deleteTail(ListNode head) {
        if(head == null || head.next == null) return null;

        ListNode current = head;
        while(current.next != null){
            current = current.next;
        }

        ListNode prev = current.prev;
        prev.next = null;
        current.prev = null;

        return head;
    }

    public ListNode deleteKthElement(ListNode head, int k) {
        if(k <= 0 || head == null)  return head;
        if(k == 1) return deleteHead(head);

        ListNode current = head;
        for(int i = 1; i < k && current != null; i++){
            current = current.next;
        }

        if(current == null) return head;

        if(current.next == null){
            ListNode prev = current.prev;
            prev.next = null;
            current.prev = null;
        }
        else{
            ListNode prev = current.prev;
            ListNode next = current.next;

            prev.next = next;
            next.prev = prev;

            current.prev = null;
            current.next = null;
        }

        return head;
    }

    public ListNode insertBeforeHead(ListNode head, int data) {
        if(head == null){
            return new ListNode(data);
        }

        ListNode newNode = new ListNode(data);
        newNode.next = head;
        head.prev = newNode;

        return newNode;
    }

    public ListNode insertBeforeTail(ListNode head, int X) {
        if(head == null){return new ListNode(X);}

        ListNode current = head;
        while(current.next != null){
            current = current.next;
        }

        if(current.prev == null && current.next == null){
            ListNode newNode = new ListNode(X);
            newNode.next = current;
            current.prev = newNode;

            return newNode;
        }

        ListNode prev =  current.prev;
        ListNode newNode = new ListNode(X);
        prev.next = newNode;
        current.prev = newNode;

        newNode.prev = prev;
        newNode.next = current;

        return head;
    }

    public ListNode insertBeforeKthPosition(ListNode head, int X, int K) {
        if(K <= 0 || head == null) return head;

        if(K == 1){
            ListNode newNode = new ListNode(X);
            newNode.next = head;
            head.prev = newNode;

            return newNode;
        }

        ListNode current = head;
        for(int i = 1; i < K; i++){
            current = current.next;
        }

        ListNode prev =  current.prev;
        ListNode newNode = new ListNode(X);
        prev.next = newNode;
        current.prev = newNode;

        newNode.prev = prev;
        newNode.next = current;

        return head;
    }


}
