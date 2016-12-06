package code;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * An implementation of a queue using singly linked nodes.  The
 * queue itself maintains links to both the head and the tail
 * node, so that both enqueuing and dequeueing are O(1).
 */


/**
 * 
 * @author Ryan
 * we should make this que just have orders that is it.
 * so fix that 
 * use robots to pull from that and such 
 * 
 */
public class LinkedQueue implements Queue {
    private class Node {
        public String name;
        public Node next;
        public int number;
        public String step;
        public Node(String name,int number,Node next) {
            this.name =name ;
            this.next = next;
            this.number=number;
            this.step="first";
        }
    }

    private Node head = null;
    private Node tail = null;

    public void enqueue(String name,int number) {
        Node newNode = new Node(name,number, null);
        if (isEmpty()) {head = newNode;} else {tail.next = newNode;}
        tail = newNode;
    }

    public Object dequeue(int number) {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Object item = head.name;
        if (tail == head) {
            tail = null;
        }
        head = head.next;
        return item;
    }

    public Object peek() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        return head.name;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        int count = 0;
        for (Node node = head; node != null; node = node.next) {
            count++;
        }
        return count;
    }
   

	@Override
	public boolean addAll(Collection arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray(Object[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object element() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean offer(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object poll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object remove() {
		// TODO Auto-generated method stub
		return null;
	}
    }

