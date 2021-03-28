package collections.linkedList;

public class SinglyLinkedList {

    public static Node head;
    
    public static class Node {
        public int data;
        public Node next;
        
        public Node(int data) {
            this.data = data;
            next = null;
        }
    }

    public void display() {
        Node n = head;
        while (n!=null) {
            System.out.println("Node: " +n.data);
            n = n.next;
        }
    }
}
