package StackQueues;

import java.util.Stack;

public class QueueImplementation {
    class ArrayQueue {
        private int[] elements;
        private int front, rear, size;
        private static final int DEFAULT_CAPACITY = 10;

        public ArrayQueue() {
            this(DEFAULT_CAPACITY);
        }

        public ArrayQueue(int capacity) {
            if(capacity < 0) throw new IllegalArgumentException();
            elements = new int[capacity];
            front = 0;
            rear = 0;
            size = 0;
        }

        public void push(int value){
            if(isFull()) resize();

            elements[rear] = value;
            rear = (rear + 1) % elements.length;
            size++;
        }

        public int pop(){
            if(isEmpty()){
                return -1;
            }

            int value = elements[front];
            front = (front + 1) % elements.length;
            size--;
            return value;
        }

        public int peek(){
            if(isEmpty()){
                return -1;
            }

            return elements[front];
        }

        public boolean isEmpty(){
            return size == 0;
        }

        private boolean isFull(){
            return size == elements.length;
        }

        private void resize(){
            int oldCapacity = elements.length;
            int newCapacity = oldCapacity * 2;
            int[] newArr = new int[newCapacity];

            for(int i = 0; i < size; i++){
                newArr[i] = elements[(front + i) % oldCapacity];
            }

            elements = newArr;
            front = 0;
            rear = size;
        }
    }

    public void QueueUsingArray(){
        ArrayQueue queue = new ArrayQueue(3);
        queue.push(1);
        queue.push(2);
        queue.push(3);

        System.out.println(queue.isFull());

        System.out.println(queue.pop());
        System.out.println(queue.pop());

        queue.push(6);
        queue.push(7);
        queue.push(8);

        System.out.println(queue.pop());
        System.out.println(queue.pop());
        System.out.println(queue.pop());
        System.out.println(queue.pop());
    }

    class StackQueue{
        private Stack<Integer> input;
        private Stack<Integer> output;

        public StackQueue() {
            input = new Stack<>();
            output = new Stack<>();
        }

        public void push(int value){
            input.push(value);
        }

        public int pop(){
            if(!output.isEmpty()){
                return output.pop();
            }

            pourInputToOutput();

            return output.pop();
        }

        public int peek(){
            if(!output.isEmpty()){
                return output.peek();
            }

            pourInputToOutput();

            return output.peek();
        }

        public boolean isEmpty(){
            return  input.isEmpty() && output.isEmpty();
        }

        private void pourInputToOutput(){
            while(!input.isEmpty()){
                output.push(input.pop());
            }
        }
    }

    public void QueueUsingStack(){
        StackQueue queue = new StackQueue();
        queue.push(1);
        queue.push(2);
        queue.push(3);

        System.out.println(queue.isEmpty());

        System.out.println(queue.peek());

        System.out.println(queue.pop());
        System.out.println(queue.pop());

        queue.push(6);
        System.out.println(queue.peek());
    }

    class LinkedListQueue{
        class Node {
            int data;
            Node next;

            public Node(int data){
                this.data = data;
                this.next = null;
            }
        }

        Node front;
        Node rear;

        public LinkedListQueue(){
            front = null;
            rear = null;
        }

        public void push(int value){
            Node node = new Node(value);
            if(front == null) front = rear = node;
            else {
                rear.next = node;
                rear = node;
            }
        }

        public int pop(){
            if(front == null) return -1;
            int data = front.data;
            front = front.next;

            if(front == null) rear = null;

            return data;
        }

        public int peek(){
            if(front == null) return -1;
            return front.data;
        }

        public boolean isEmpty(){
            return front == null;
        }
    }
}
