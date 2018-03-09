import java.util.*;

/**
 * Adaptible priority queue using location-aware entries in a min-heap, based on
 * an extendable array.  The order in which equal entries were added is preserved.
 *
 * @author yashdhamija
 * @param <E> The entry type.
 */
public class APQ<E> {

	private final ArrayList<E> apq; //will store the min heap
	private final Comparator<E> comparator; //to compare the entries
	private final Locator<E> locator;  //to locate the entries within the queue

	/**
	 * Constructor
	 * @param comparator used to compare the entries
	 * @param locator used to locate the entries in the queue
	 * @throws NullPointerException if comparator or locator parameters are null
	 */
	public APQ(Comparator<E> comparator, Locator<E> locator) throws NullPointerException {
		if (comparator == null || locator == null) {
			throw new NullPointerException();
		}
		apq = new ArrayList<>();
		apq.add(null); //dummy value at index = 0
		this.comparator = comparator;
		this.locator = locator;
	}    

	/**
	 * 
	 * @param i
	 * @return the position of parent of any element at a given index
	 */
	protected int parent(int i) {
		return (i) / 2;
	}

	/**
	 * 
	 * @param i
	 * @return position of leftChild
	 */
	protected int leftChild(int i) {
		return 2 * i;
	}

	/**
	 * 
	 * @param i
	 * @return position of right child
	 */
	protected int rightChild(int i) {
		return 2 * i + 1;
	}

	/**
	 * 
	 * @param i
	 * @return true if i is a valid index
	 */
	protected boolean validIndex(int i) {
		return i > 0 && i <= this.size();
	}

	/**
	 * 
	 * @param i
	 * @return true if element i has a parent
	 */
	protected boolean hasParent(int i) {
		return this.validIndex(this.parent(i));
	}

	/**
	 * 
	 * @param i
	 * @return true if element at position i has a left child
	 */
	protected boolean hasLeft(int i) {
		return this.validIndex(this.leftChild(i));
	}

	/**
	 * 
	 * @param i
	 * @return true if element at position i has a right child
	 */
	protected boolean hasRight(int i) {
		return this.validIndex(this.rightChild(i));
	}

	/**
	 * Inserts the specified entry into this priority queue.
	 *
	 * @param e the entry to insert
	 * @throws NullPointerException if parameter e is null
	 */
	public void offer(E e) throws NullPointerException {
		if (e == null)
			throw new NullPointerException();
		else {
			// pos is position/index in PQ where e is added.
			int pos = this.size() + 1;
			apq.add(e);
			locator.set(e, pos);	
			// if the entry added is not at root then it calls for upheap,
			//to check and maintain for heap-order property
			if (pos != 1 ) 
				upheap(pos);        		
		}
	}

	/**
	 * Removes the entry at the specified location.
	 *
	 * @param pos the location of the entry to remove
	 * @throws BoundaryViolationException if pos is out of range
	 */
	public void remove(int pos) throws BoundaryViolationException {
		if (pos < 1 || pos > this.size())
			throw new BoundaryViolationException();

		if (pos == this.size()) 
			apq.remove(pos);    

		else {
			E last = apq.get(this.size());
			apq.set(pos, last);
			locator.set(last, pos);
			apq.remove(this.size());
			this.downheap(pos);
		}       		
	}

	public void remove(E e) throws BoundaryViolationException {
		int x = this.locator.get(e);
		this.remove(x);
	}

	/**
	 * Removes the first entry in the priority queue.
	 * @throws BoundaryViolationException 
	 */
	public E poll() throws BoundaryViolationException {
		E index = apq.get(1);
		this.remove(1);
		return index;
	}

	/**
	 * Returns but does not remove the first entry in the priority queue.
	 */
	public E peek() {
		if (isEmpty()) {
			return null;
		}
		return apq.get(1);
	}

	public boolean isEmpty() {
		return (size() == 0); 
	}

	public int size() {
		return apq.size() - 1; //dummy node at location 0
	}


	/**
	 * Shift the entry at pos upward in the heap to restore the minheap property
	 * @param pos the location of the entry to move
	 */
	private void upheap(int pos) {
		//loop until the element at position pos has a parent and it is smaller than the parent.
		while (pos > 1 && comparator.compare((apq.get(pos)), apq.get(this.parent(pos))) == -1) {
			int p = this.parent(pos);
			this.swap(pos, p);
			pos = p;
		}   		
	}

	/**
	 * Shift the entry at pos downward in the heap to restore the minheap property
	 * @param pos the location of the entry to move
	 */
	private void downheap(int pos) {
		while (this.hasLeft(pos)) {
			int small = this.leftChild(pos);
			E smaller = apq.get(small);
			if (this.hasRight(pos) && comparator.compare(smaller, apq.get(this.rightChild(pos))) == 1) {
				smaller = apq.get(this.rightChild(pos));
				small = this.rightChild(pos);
			}
			if (comparator.compare(apq.get(pos), smaller) < 1) break;
			this.swap(pos, small);
			pos = small;
		}    		
	}

	/**
	 * Swaps the entries at the specified locations.
	 *
	 * @param pos1 the location of the first entry 
	 * @param pos2 the location of the second entry 
	 */
	private void swap(int pos1, int pos2) {
		if (this.validIndex(pos1) && this.validIndex(pos2)) {
			E e1 = apq.get(pos1);
			E e2 = apq.get(pos2);
			apq.set(pos1, e2);
			apq.set(pos2, e1);
			locator.set(e1, pos2);
			locator.set(e2, pos1);
		}
	}
}



















