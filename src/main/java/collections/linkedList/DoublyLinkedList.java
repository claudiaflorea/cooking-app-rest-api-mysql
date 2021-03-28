package collections.linkedList;

public class DoublyLinkedList {
    
    public static class Node {
        public int data;
        public Node previous;
        public Node next;
        
        public Node(int data) {
            this.data = data;
        }
    }
    
    Node head, tail = null;
    
    public void addNode(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = tail = newNode;
            head.previous = null;
            head.next = null;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
            tail.next = null;
        }
    }
    
    public void display() {
        Node current = head;
        if (head == null) {
            System.out.println("Doubly linked list is empty");
            return;
        }
        
        while (current!=null) {
            System.out.println("Node: " + current.data);
            current = current.next;
        }
    }
}
