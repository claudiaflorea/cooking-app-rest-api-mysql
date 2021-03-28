package collections.linkedList;

public class CircularLinkedList {
    
    public class Node {
        public int data;
        public Node next;
        
        public Node(int data) {
            this.data = data;
        }
    }
    
    public Node head = null;
    public Node tail = null;
    
    public void addNode(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
            newNode.next = head;
        } else {
            tail.next = newNode;
            tail = newNode;
            tail.next = head;
        }
    }
    
    public void display() {
        Node current = head;
        if (head == null) {
            System.out.println("CircularLinkedList is empty");
        } else {
            do {
                System.out.println("Node: "+ current.data);
                current = current.next;
            } while (current != head);
        }
    }
}
