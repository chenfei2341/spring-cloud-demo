package com.znv.peim.northbound;

/**
 * @author Chenfei
 */
public class NodeTest {
    public static void main(String[] args) {
        NodeLinked linked = new NodeLinked();
        Integer i1 = new Integer(1);
        linked.add(i1);
        Integer i2 = new Integer(2);
        linked.add(i2);
        Integer i3 = new Integer(3);
        linked.add(i3);
        Integer i4 = new Integer(4);
        linked.add(i4);
        Integer i5 = new Integer(5);
        linked.add(i5);
        Integer i6 = new Integer(6);
        linked.add(i6);
        System.out.println(linked.toString());
        linked.add(4, new Integer(44));
        System.out.println(linked.toString());
        linked.add(2, new Integer(22));
        System.out.println(linked.toString());
        linked.delete(2);
        linked.delete(4);
        System.out.println(linked.toString());
        linked.delete(i3);
        System.out.println(linked.toString());
        linked.add(2, i3);
        System.out.println(linked.toString());
        linked.revert();
        System.out.println(linked.toString());
    }
}

class NodeLinked<E> {
    private Node head = null;
    private Node last = null;
    private int size = 0;

    class Node {
        Object obj = null;
        Node next = null;

        Node(Object obj) {
            this.obj = obj;
        }
    }

    public void add(E data) {
        Node node = new Node(data);
        if (head == null) {
            head = node;
            last = node;
        } else {
            last.next = node;
            last = node;
        }
        size++;
    }

    public void add(int i, E data) {
        Node node = new Node(data);
        if (i >= size || i < 0) {
            throw new IndexOutOfBoundsException("index too long.");
        }
        if (i == 0) {
            node.next = head;
            head = node;
        } else {
            Node current = head;
            Node prex = null;
            while (i > 0) {
                prex = current;
                current = current.next;
                i--;
            }
            node.next = current;
            prex.next = node;
        }
        size++;
    }

    public void delete(int i) {
        if (i >= size || i < 0) {
            throw new IndexOutOfBoundsException("index too long.");
        }

        if (i == 0) {
            head = head.next;
        } else {
            Node current = head;
            Node prex = null;
            while (i > 0) {
                prex = current;
                current = current.next;
                i--;
            }
            prex.next = current.next;
        }
        size--;
    }

    public void delete(E data) {
        if (data == null) {
            throw new NullPointerException("data is null.");
        }

        Node current = head;
        Node prex = null;
        while (current != null) {
            if (data.equals(current.obj)) {
                prex.next = current.next;
                size--;
                break;
            }
            prex = current;
            current = current.next;
        }
    }

    public void revert() {
        Node current = head;
        Node prev = null;
        Node next = null;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        last = head;
        head = prev;
    }

    @Override
    public String toString() {
        Node current = head;
        StringBuilder sb = new StringBuilder();
        while (current != null) {
            sb.append(current.obj.toString()).append(" -> ");
            current = current.next;
        }
        sb.append("null");
        return sb.toString();
    }
}