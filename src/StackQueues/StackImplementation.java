package StackQueues;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class StackImplementation {
    class ArrayStack {
        private int[] elements;
        private int top;
        private static final int DEFAULT_CAPACITY = 10;

        public ArrayStack() {
            elements = new int[DEFAULT_CAPACITY];
            top = -1;
        }

        public ArrayStack(int capacity) {
            if(capacity < 0) throw new IllegalArgumentException("Capacity cannot be negative");
            elements = new int[capacity];
            top = -1;
        }

        public void push(int item){
            if(isFull()){
                resize();
            }
            elements[++top] = item;
        }

        public int pop(){
            if(isEmpty()){
                System.out.println("Stack is empty");
                return -1;
            }

            int item = elements[top];
            elements[top--] = 0;
            return item;
        }

        public boolean isEmpty(){
            return top == -1;
        }

        public int size(){
            return top + 1;
        }

        private boolean isFull(){
            return top == elements.length - 1;
        }

        private void resize(){
            int newCapacity =  elements.length * 2;
            int[] newElements = new int[newCapacity];

            System.arraycopy(elements, 0, newElements, 0, size());
            elements = newElements;
        }

        public void clear(){
            while(!isEmpty()){
                pop();
            }
        }
    }

    class QueueStack{
        private Queue<Integer> queue;

        public QueueStack() {
            queue = new LinkedList<Integer>();
        }

        public void push(int x) {
            queue.offer(x);

            for(int i = 0; i < queue.size() - 1; i++){
                queue.offer(queue.poll());
            }
        }

        public int pop() {
            if(isEmpty()) return -1;

            return queue.poll();
        }

        public int top() {
            if(isEmpty()) return -1;
            return queue.peek();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    class QueueStack2{
        private Queue<Integer> q1;
        private Queue<Integer> q2;

        public QueueStack2() {
            q1 = new LinkedList();
            q2 = new LinkedList();
        }

        public void push(int x) {
            q1.offer(x);
        }

        public int pop() {
            if(q1.isEmpty()) return -1;

            while(q1.size() > 1){
                q2.offer(q1.poll());
            }

            int top = q1.poll();

            //swap queues.
            Queue<Integer> temp = q1;
            q1 = q2;
            q2 = temp;

            return top;
        }

        public int top(){
            if(q1.isEmpty()) return -1;

            while(q1.size() > 1){
                q2.offer(q1.poll());
            }

            int top = q1.peek();
            q2.offer(q1.poll());

            //swap queues.
            Queue<Integer> temp = q1;
            q1 = q2;
            q2 = temp;

            return top;
        }

        public boolean isEmpty() {
            return q1.isEmpty();
        }
    }

    public void StackUsingQueues(){
        QueueStack qs =  new QueueStack();
        qs.push(1);
        qs.push(2);
        qs.push(3);
        qs.push(4);

        System.out.println(qs.pop());
        System.out.println(qs.top());
        System.out.println(qs.pop());
        System.out.println(qs.top());
    }

    class LinkedListStack{
        class Node {
            int data;
            Node next;

            public Node(int data){
                this.data = data;
                this.next = null;
            }
        }

        Node head;

        public LinkedListStack(){
            head = null;
        }

        public void push(int x){
            Node newNode =  new Node(x);
            if(head == null){
                head = newNode;
            }
            else{
                newNode.next = head;
                head = newNode;
            }
        }

        public int pop(){
            if (head == null) return -1;
            int data = head.data;
            head = head.next;
            return data;
        }

        public int top(){
            if(head == null) return -1;
            return head.data;
        }

        public boolean isEmpty(){
            return head == null;
        }
    }

}
