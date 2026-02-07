package LinkedList;

import java.util.HashMap;

public class HardLL {
    class ListNode{
        int val;
        ListNode next;

        ListNode(){}
        ListNode(int val){
            this.val = val;
            this.next = null;
        }

        ListNode(int val, ListNode next){
            this.val = val;
            this.next = next;
        }
    }

    private ListNode reverseKNodes(ListNode head, int k){
        if(head == null) return head;

        ListNode temp = head;
        for(int i = 0; i < k; i++){
            if(temp == null) return head;
            temp = temp.next;
        }

        ListNode prev = null;
        ListNode curr = head;
        ListNode next = null;

        for(int i = 0; i < k; i++){
            next = curr.next;

            curr.next = prev;
            prev = curr;
            curr = next;
        }

        head.next = reverseKNodes(curr, k);

        return prev;
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        // Recursion approach.
        // return reverseKNodes(head, k);

        // Iterative approach
        if(head == null || k == 1) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode prevTail = dummy;

        while(true){
            ListNode groupStart = prevTail.next;
            ListNode temp = groupStart;
            for (int i = 0; i < k; i++) {
                if (temp == null) {
                    // Not enough nodes → done
                    return dummy.next;
                }
                temp = temp.next;
            }

            ListNode prev = null;
            ListNode curr = groupStart;
            ListNode next = null;

            for (int i = 0; i < k; i++) {
                next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }

            prevTail.next = prev;
            groupStart.next = curr;
            prevTail = groupStart;
        }
    }

    public ListNode rotateRight(ListNode head, int k) {
        if(head == null || head.next == null || k <= 0) return head;

        int length = 1;
        ListNode tail =  head;

        while(tail.next != null){
            length++;
            tail = tail.next;
        }

        k = k %  length;
        if(k == 0) return head;

        tail.next = head; // make it circular

        ListNode newTail = head;
        for(int i = 1; i < length - k; i++){
            newTail = newTail.next;
        }

        ListNode newHead = newTail.next;
        newTail.next = null;

        return newHead;
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if(list1 == null) return list2;
        if(list2 == null) return list1;

        ListNode dummy = new ListNode(0);
        ListNode temp= dummy;

        while(list1 != null && list2 != null){
            if(list1.val <= list2.val){
                temp.next = list1;
                list1 = list1.next;
            }else{
                temp.next = list2;
                list2 = list2.next;
            }
            temp = temp.next;
        }

        temp.next = list1 != null ? list1 : list2;

        return dummy.next;
    }

    private ListNode findMid(ListNode head){
        ListNode slow = head;
        ListNode fast = head.next;

        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    private ListNode mergeList(ListNode list1, ListNode list2){
        if(list1 == null) return list2;
        if(list2 == null) return list1;

        ListNode dummy = new ListNode(0);
        ListNode temp = dummy;

        while(list1 != null && list2 != null){
            if(list1.val <= list2.val){
                temp.next = list1;
                list1 = list1.next;
            }else{
                temp.next = list2;
                list2 = list2.next;
            }
            temp = temp.next;
        }

        temp.next = list1 != null ? list1 : list2;

        return dummy.next;
    }

    public ListNode mergeSortLinkedList(ListNode head) {
        if(head == null || head.next == null) return head;

        ListNode mid = findMid(head);

        ListNode right = mid.next;
        mid.next = null;
        ListNode left = head;

        left = mergeSortLinkedList(left);
        right = mergeSortLinkedList(right);

        return mergeList(left, right);
    }

    class RandomNode {
        int val;
        RandomNode next;
        RandomNode random;

        RandomNode(int x) {
            val = x;
        }
    }

    public RandomNode copyRandomList(RandomNode head) {
        if(head == null) return null;

        HashMap<RandomNode, RandomNode> map = new HashMap<>();
        RandomNode curr = head;

        while(curr != null){
            map.put(curr, new RandomNode(curr.val));
            curr = curr.next;
        }

        curr = head;
        while(curr != null){
            RandomNode copy = map.get(curr);
            copy.next = map.get(curr.next);
            copy.random = map.get(curr.random);
            curr = curr.next;
        }

        return map.get(head);
    }

    public RandomNode copyRandomListOptimal(RandomNode head) {
        if(head == null) return head;

        RandomNode curr = head;
        while(curr != null){
            RandomNode copy = new RandomNode(curr.val);
            copy.next = curr.next;
            curr.next = copy;
            curr = curr.next.next;
        }

        curr = head;
        while(curr != null){
            if(curr.random != null){
                curr.next.random = curr.random.next;
            }
            curr = curr.next.next;
        }

        RandomNode newHead = head.next;
        RandomNode copyCurr = newHead;
        curr = head;

        while(curr != null){
            curr.next = curr.next.next;
            if(copyCurr.next != null){
                copyCurr.next = copyCurr.next.next;
            }
            curr = curr.next;
            copyCurr = copyCurr.next;
        }

        return newHead;
    }

}
