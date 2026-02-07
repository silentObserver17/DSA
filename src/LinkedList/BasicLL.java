package LinkedList;

import java.util.ArrayList;
import java.util.List;

class Node{
    int data;
    Node next;
    public Node(int data){
        this.data=data;
        next=null;
    }
}

public class BasicLL {
    public void ArrayToLinkedList(int[] arr){
        int size = arr.length;
        if(size==0) return;

        Node head = new Node(arr[0]);
        Node cur = head;
        for(int i=1;i<size;i++){
            cur.next = new Node(arr[i]);
            cur = cur.next;
        }

        printLinkedList(head);
        SearchElementInLinkedList(head, 55);
    }

    public void printLinkedList(Node head){
        Node cur = head;
        while(cur!=null){
            System.out.print(cur.data+" -> ");
            cur = cur.next;
        }

        System.out.println("null");
    }

    public void SearchElementInLinkedList(Node head,int data){
        Node current = head;

        while(current != null){
            if(current.data==data){
                System.out.println("Element found in Linked List "+current.data);
                return;
            }
            current = current.next;
        }

        System.out.println("Element not exist in Linked List ");
    }

    class ListNode {
        int data;
        ListNode next;

        ListNode() {
            this.data = 0;
            this.next = null;
        }

        ListNode(int x) {
            this.data = x;
            this.next = null;
        }

        ListNode(int x, ListNode next) {
            this.data = x;
            this.next = next;
        }
    }

    public List<Integer> LLTraversal(ListNode head) {
        List<Integer> result = new ArrayList<>();

        ListNode current = head;

        while(current != null){
            result.add(current.data);
            current = current.next;
        }

        return result;
    }

    public ListNode deleteHead(ListNode head) {
        if(head==null || head.next == null) return null;
        ListNode current = head;
        current = current.next;

        return current;
    }

    public ListNode deleteTail(ListNode head) {
        if(head==null || head.next == null) return null;
        ListNode current = head;

        while(current.next.next != null){
            current = current.next;
        }
        current.next = null;
        return head;
    }

    public ListNode deleteKthNode(ListNode head, int k) {
        if(head==null || k <= 0) return null;
        if(k == 1){
            return head.next;
        }

        ListNode current = head;
        ListNode prev = null;
        int count = 0;
        while(current != null){
            count++;
            if(count == k){
                prev.next = current.next;
                return head;
            }
            prev = current;
            current = current.next;
        }

        return head;
    }

    public ListNode deleteKthNode2(ListNode head, int k) {
        if(head==null || k <= 0) return null;
        if(k == 1){
            return head.next;
        }
        ListNode current = head;

        for(int i = 1; i < k - 1 && current != null; i++) current = current.next;

        if(current != null && current.next != null){
            current.next = current.next.next;
        }

        return head;
    }

    public ListNode deleteNodeWithValueX(ListNode head, int X) {
        if(head==null) return null;
        if(head.data == X){
            return head.next;
        }

        ListNode current = head.next;
        ListNode prev = head;

        while(current != null){
            if(current.data == X){
                prev.next = current.next;
                return head;
            }
            prev = current;
            current = current.next;
        }
        return head;
    }

    public ListNode insertAtHead(ListNode head, int X) {
        ListNode newNode = new ListNode(X, head);
        return newNode;
    }


    public ListNode insertAtTail(ListNode head, int X) {
        if(head==null){
            return new ListNode(X);
        }

        ListNode current = head;
        while(current.next != null){
            current = current.next;
        }

        ListNode newNode = new ListNode(X);
        current.next = newNode;
        return head;
    }

    public ListNode insertAtKthPosition(ListNode head, int X, int K) {
        if(K<=0) return new ListNode(X);
        if(K==1){
            ListNode newNode = new ListNode(X);
            newNode.next = head;
            return newNode;
        }

        ListNode current = head;

        for(int i = 1; i < K - 1 && current != null; i++){
            current = current.next;
        }

        if(current != null){
            ListNode newNode = new ListNode(X);
            newNode.next = current.next;
            current.next = newNode;
        }

        return head;
    }

    public ListNode insertBeforeX(ListNode head, int X, int val) {
        if(head==null) return new ListNode(val);

        if(head.data == X){
            ListNode newNode = new ListNode(val);
            newNode.next = head;
            return newNode;
        }

        ListNode current = head;
        while(current.next != null){
            if(current.next.data == X){
                ListNode newNode = new ListNode(val);
                newNode.next = current.next;
                current.next = newNode;
                break;
            }
            current = current.next;
        }

        return head;
    }


}
