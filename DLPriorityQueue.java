/**
 * This class will implement all the methods in the PriorityQueeuADT.java interface plus the method getRear and it will store data items of priority queue in doubly linked list
 * @author skullar5
 *
 * @param <T>
 */
public class DLPriorityQueue<T> implements PriorityQueueADT<T> {
	/**
	 * Reference to first node of the doubly linked list
	 */
	private DLinkedNode<T> front;
	/**
	 * Reference to last node of doubly linked list
	 */
	private DLinkedNode<T> rear;
	/**
	 * Variable value is number of data items in priority queue
	 */
	private int count;

	/**
	 * Constructor that creates empty priority queue
	 */
	public DLPriorityQueue() {
		front = null;
		rear = null;
		count = 0;
	}

	/**
	 * This method adds to priority queue given data item with its associated priority
	 */
	@Override
	public void add(T data, double priority) {
		// created new node called newNode and assign it to values of data and priority
		DLinkedNode<T> newNode = new DLinkedNode<>(data, priority);
		// create new node called currNode and assign it as front node
		DLinkedNode<T> currNode = front;

		// if the priority queue is empty, then initialize front node and rear node as the new node
		if (isEmpty()) {
			front = newNode;
			rear = newNode;

			// otherwise if current node does not point to null and the new nodes priority is less than front nodes priority then 
			//set new node to point to front which indicates that new node is inserted at front of doubly linked list
		} else if ((currNode.getNext() != null) && (newNode.getPriority() < front.getPriority())) {
			newNode.setNext(front);
			front.setPrev(newNode);
			front = newNode;

			// otherwise, if new node does not point to null and new nodes priority is greater than the current's next node priority then
		} else {
			// get the next node of the current node
			while ((currNode.getNext() != null) && (newNode.getPriority() > currNode.getNext().getPriority())) {
				currNode = currNode.getNext();
			}
			// if the current node us pointing to null means current node is at rear of list then make rear point to new node meaning now new node is inserted at rear of list
			if (currNode.getNext() == null) {
				rear.setNext(newNode);
				newNode.setPrev(rear);
				rear = newNode;

				// otherwise, update pointers after inserting new node:
				// then update the new node to point to the next node of the current node and 
				// update the current node to point to the new node and
				// update the next node of current node to point to new node and then set the current node to point to new node
			} else {
				newNode.setNext(currNode.getNext());
				newNode.setPrev(currNode);
				currNode.getNext().setPrev(newNode);
				currNode.setNext(newNode);
			}
		}
		// increment the number of nodes in the doubly linked list
		count++;
	}

	/**
	 * method that changes priority of data item to new value
	 */
	@Override
	public void updatePriority(T data, double newPriority) throws InvalidElementException {
		// initialize priority queue to front of doubly linkest list and initialize boolean variable to check if data item is in priority queue
		DLinkedNode<T> prioQueue = front;
		boolean dataItemInPrioQueue = false;

		// while the prioirity queue isnt null, then if the data item of the prioity queue has sam value as parameter data then data item is in priority queue 
		while (prioQueue != null) {
			if (prioQueue.getDataItem().equals(data)) {
				dataItemInPrioQueue = true;
				break;
			}
			// get next node in priority queue
			prioQueue = prioQueue.getNext();
		}
		//if data item is not in priority queue then throw exception that element is not in priority queueu which is also known as InvalidElementException
		if (!dataItemInPrioQueue) {
			throw new InvalidElementException("Element not found in priority queue.");
		}
		// update the priority of the priority queue with given data item as value of parameter, new priority 
		prioQueue.setPriority(newPriority);

		// while the priority queue's previous node is null and the priority queues priority is less than the previous node of priority queues priority 
		// then initialize data as data item of priority queue and new priority as the priority of priority queue
		while (prioQueue.getPrev() != null && prioQueue.getPriority() < prioQueue.getPrev().getPriority()) {
			data = prioQueue.getDataItem();
			newPriority = prioQueue.getPriority();

			// update the data item of priority queue as the priority queues previous nodes data item and
			// update the priority of priority queue as priority queues previous nodes priority and
			// get the previous node of priority queue and set the data item with value of data and 
			// get the previous node of priority queue and set the priority with value of new priority 
			// this is indicates the swapping of the data and priority of the node
			prioQueue.setDataItem(prioQueue.getPrev().getDataItem());
			prioQueue.setPriority(prioQueue.getPrev().getPriority());
			prioQueue.getPrev().setDataItem(data);
			prioQueue.getPrev().setPriority(newPriority);

			// get the previous node of the priority queue
			prioQueue = prioQueue.getPrev();
		}

		// while the priority queue's next node is null and the priority queues priority is greater than the next node of priority queues priority 
		// then initialize data as data item of priority queue and new priority as the priority of priority queue
		while (prioQueue.getNext() != null && prioQueue.getPriority() > prioQueue.getNext().getPriority()) {
			data = prioQueue.getDataItem();
			newPriority = prioQueue.getPriority();

			// update the data item of priority queue as the priority queues next nodes data item and
			// update the priority of priority queue as priority queues next nodes priority and
			// get the next node of priority queue and set the data item with value of data and 
			// get the next node of priority queue and set the priority with value of new priority 
			// this is indicates the swapping of the data and priority of the node
			prioQueue.setDataItem(prioQueue.getNext().getDataItem());
			prioQueue.setPriority(prioQueue.getNext().getPriority());
			prioQueue.getNext().setDataItem(data);
			prioQueue.getNext().setPriority(newPriority);

			// get the next node of the priority queue
			prioQueue = prioQueue.getNext();
		}
	}

	/**
	 * this method removes and returns the data item in priority queue with smallest priority
	 */
	@Override
	public T removeMin() throws EmptyPriorityQueueException {
		// if the priority quue is empty, a EmptyPrioritityQueueException will be thrown which is basically an exception which means that the priority queue is empty
		if (isEmpty()) {
			throw new EmptyPriorityQueueException("priority queue is empty");
		}
		// initialize frontDataItem variable to get data item of front node in queue
		T frontDataItem = front.getDataItem();
		// if the next node of front node is null meaning front node only node in queue then set front and rear node as null meaning both nodes pointing to null
		if (front.getNext() == null) {
			front = null;
			rear = null;

			// otherwise if next node of front node doesnt equal null then get the next node of front and set the previous node of front as null
			// this means that the front points to next node and its previous node points to null removing front node
		} else if(front.getNext() != null){
			front = front.getNext();
			front.setPrev(null);
		}
		// decrement count of nodes in queue and return the data item of front node
		count--;
		return frontDataItem;
	}

	/**
	 * method which returns true if priority queue is empty else returns false if isnt empty
	 */
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		// return true if count is 0 means priority queue is empty but return false if count isnt 0 which means that priority queue isnt empty
		return count == 0;
	}

	/**
	 * method returns number of data items in priority queue
	 */
	@Override
	public int size() {
		// TODO Auto-generated method stub
		// return the count of data items in prio Queue
		return count;
	}
	/**
	 * method which returns string representation of priority queue
	 */
	public String toString() {
		// initialization of empty string
		// intializing current node to point to front of list
		//while current node isnt null add the number of data items to empty string and concatenate string
		// then return concatenated string 
		String concatStr = "";
		DLinkedNode<T> currNode = front;
		while (currNode != null) {
			concatStr += currNode.getDataItem() + "";
			currNode = currNode.getNext();
		}
		return concatStr;
	}

	/**
	 * method returns rear node of priority queue
	 * @return rear node of priority queue
	 */
	public DLinkedNode<T> getRear() {
		// return rear node
		return rear;
	}
}