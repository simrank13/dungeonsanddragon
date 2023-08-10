/**
 * This class represents the nodes of a doubly linked list that you will use to implement the priority queue
 * @author skullar5
 *
 * @param <T>
 */
public class DLinkedNode<T> {
	/**
	 * Reference to data item stored in the node of the doubly linked list
	 */
	private T dataItem;
	/**
	 * Priority of the data item stored in the node of the doubly linked list
	 */
	private double priority;
	/**
	 * Reference to next node in doubly linked list
	 */
	private DLinkedNode<T>next;
	/**
	 * Reference to previous node in doubly linked list
	 */
	private DLinkedNode<T>prev;

	/**
	 * This method creates a node storing the given data item and priority
	 * @param data
	 * @param prio
	 */
	public DLinkedNode(T data, double prio) {
		// instance variables prev and next initialized to null in empty doubly linked list which indicates that there is no next or prev node in doubly linked list
		//instance variables dataItem and priority initialized with the value of parameters, data and prio
		next = null;
		prev = null;
		dataItem = data; 
		priority = prio; 
	}

	/**
	 * This constructor creates empty node, with null dataItem and zero priority
	 */
	public DLinkedNode() {
		// initalize priority as 0 and dataItem as null creating an empty node in doubly linked list
		priority = 0;
		dataItem = null;
	}

	/**
	 * getter method that returns the priority of the data item stored in node of doubly linked list
	 * @return priority
	 */
	public double getPriority() {
		// return priority of data item stored in node in doubly linked list
		return priority; 
	}

	/**
	 * getter method that returns data item stored in node of doubly linked list
	 * @return dataItem
	 */
	public T getDataItem() {
		//return data item stored in node doubly linked list
		return dataItem;
	}

	/**
	 * getter method that returns the next node in the doubly linked list
	 * @return next
	 */
	public DLinkedNode<T> getNext() {
		// return next node in doubly linked list
		return next;
	}

	/**
	 *  getter method that returns the previous node in the doubly linked list
	 * @return prev
	 */
	public DLinkedNode<T> getPrev() {
		// return previous node in doubly linked list
		return prev;
	}

	/**
	 * setter method that updates the data item stored in node in doubly linked list
	 * @param data
	 */
	public void setDataItem(T data) {
		//update data item to have value of parameter, data
		dataItem = data;
	}

	/**
	 * setter method that sets the next node in doubly linked list
	 * @param node
	 */
	public void setNext(DLinkedNode<T> node) {
		// update next node to have value of parameter, node
		next = node;
	}

	/**
	 * setter method that sets the previous node in doubly linked list
	 * @param node
	 */
	public void setPrev(DLinkedNode<T> node) {
		//update previous node to have value of parameter, node
		prev = node;
	}

	/**
	 * setter method that updates the priority of the data item stored in node in doubly linked list
	 * @param prio
	 */
	public void setPriority(double prio) {
		// update priority of data item to have value of parameter, prio
		priority = prio;
	}
}