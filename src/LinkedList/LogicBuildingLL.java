package LinkedList;

public class LogicBuildingLL {
    class ListNode{
        public int data;
        public ListNode next;
        ListNode() { data = 0; next = null; }
        ListNode(int x) { data = x; next = null; }
        ListNode(int x, ListNode next) { data = x; this.next = next; }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy  = new ListNode(0);
        ListNode current = dummy;
        int carry = 0;

        while(l1 != null || l2 != null || carry != 0){
            int x = l1 == null ? 0 : l1.data;
            int y = l2 == null ? 0 : l2.data;

            int sum = x + y + carry;
            carry = sum / 10;
            int value = sum % 10;

            current.next = new ListNode(value);
            current = current.next;

            if(l1 != null) l1 = l1.next;
            if(l2 != null) l2 = l2.next;
        }

        return dummy.next;
    }


    public ListNode oddEvenList(ListNode head) {
        if(head == null || head.next == null) return head;

        ListNode oddTail = head;
        ListNode evenTail = head.next;
        ListNode evenHead = head.next;

        while(evenTail != null && evenTail.next != null){
            oddTail.next = evenTail.next;
            oddTail = oddTail.next;

            evenTail.next = oddTail.next;
            evenTail = evenTail.next;
        }

        oddTail.next = evenHead;

        return head;
    }

    public ListNode sortList(ListNode head) {
        if(head == null || head.next == null) return head;

        ListNode zeroHead = null, zeroTail = null;
        ListNode oneHead  = null, oneTail  = null;
        ListNode twoHead  = null, twoTail  = null;

        ListNode current = head;

        while(current != null){
            ListNode next =  current.next;
            current.next = null;

            if(current.data == 0){
                if(zeroHead == null){
                    zeroHead = current;
                    zeroTail = current;
                }else{
                    zeroTail.next = current;
                    zeroTail = current;
                }
            }
            else if(current.data == 1){
                if(oneHead == null){
                    oneHead = current;
                    oneTail = current;
                }else{
                    oneTail.next = current;
                    oneTail = current;
                }
            }
            else if(current.data == 2){
                if(twoHead == null){
                    twoHead = current;
                    twoTail = current;
                }
                else{
                    twoTail.next = current;
                    twoTail = current;
                }
            }

            current = next;
        }

        if(zeroTail != null){
            zeroTail.next = oneHead != null ? oneHead : twoHead;
        }

        if(oneTail != null){
            oneTail.next = twoHead;
        }

        if(zeroHead != null) return zeroHead;
        if(oneHead != null) return oneHead;

        return twoHead;
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode fast = dummy;
        ListNode slow = dummy;

        // since its linked list and we are using one based index.
        for(int i = 1; i <= n; i++){
            fast = fast.next;
        }

        while(fast.next != null){
            fast = fast.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;

        return dummy.next;
    }

    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode prev = null;
        ListNode curr = head;
        ListNode next = null;  // temporary to save forward link

        while (curr != null) {
            next = curr.next;

            curr.next = prev;
            prev = curr;
            curr = next;
        }

        return prev;
    }

    public ListNode reverseListRecursion(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode reversedHead = reverseListRecursion(head.next);

        head.next.next = head;
        head.next = null;
        return reversedHead;
    }


    public ListNode addOneBrute(ListNode head) {
        if(head == null) return head;
        head = reverseList(head);

        ListNode current = head;
        int carry = 1;

        while(current != null && carry > 0){
            int sum = current.data + carry;
            current.data = sum % 10;
            carry = sum / 10;

            if(current.next == null && carry > 0){
                current.next = new ListNode(carry);
                carry = 0;
            }
            current = current.next;
        }

        return reverseList(head);
    }

    public ListNode addOne(ListNode head) {
        int carry = helperAddOne(head);

        if(carry > 0){
            ListNode newHead = new  ListNode(carry);
            newHead.next = head;
            return newHead;
        }

        return head;
    }

    public int helperAddOne(ListNode head){
        if(head == null) return 1;

        int carry = helperAddOne(head.next);

        int sum = head.data + carry;
        head.data = sum % 10;
        carry = sum / 10;

        return carry;
    }

    public ListNode middleOfLinkedList(ListNode head) {
        if(head == null) return head;

        ListNode fast = head;
        ListNode slow = head;

        while(fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }

    public ListNode removeMiddle(ListNode head) {
        if(head == null || head.next == null) return null;

        ListNode fast = head;
        ListNode slow = head;
        ListNode prev = null;

        while(fast != null && fast.next != null){
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        prev.next = slow.next;
        return head;
    }

    public ListNode findStartingPoint(ListNode head) {
        if(head ==  null || head.next == null) return null;

        ListNode fast = head;
        ListNode slow = head;

        while(fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;

            if(fast == slow){
                ListNode ptr1 = head;
                ListNode ptr2 = slow;

                while(ptr1 != ptr2){
                    ptr1 = ptr1.next;
                    ptr2 = ptr2.next;
                }

                return ptr1;
            }
        }

        return null;
    }

    public int findLengthOfLoop(ListNode head) {
        if(head ==  null || head.next == null) return 0;

        ListNode fast = head;
        ListNode slow = head;

        while(fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;

            if(fast == slow){
                int count = 1;
                fast = fast.next;

                while(fast != slow){
                    fast = fast.next;
                    count++;
                }

                return count;
            }
        }

        return 0;
    }



}
