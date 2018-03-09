import java.util.*;
import java.util.concurrent.atomic.LongAccumulator;

/**
 * Triages patients in Emergency Ward according to medical priority and wait time.
 * Priorities are positive integers;  the highest priority is 1. 
 * Normally patients are seen in priority order, however, if there are patients 
 * who have waited longer than a specified time (maxWait), they are seen first, 
 * in order of their arrival.  
 * @author yashdhamija
 */
public class PatientTriage {

	public APQ<Patient> priorityHeap; //maintain patients in priority order
	public APQ<Patient> timeHeap;  //maintain patients in order of arrival
	private Time maxWait; //maximum waiting time

	//    private APQ<Patient> ntimeHeap; //a clone of timeHeap
	//    private Stack<Patient> timeStack; // a stack for maintaining patients according to time
	/**
	 * Constructor
	 *
	 * @param time Maximum wait time.  Patients waiting longer than this are seen first.
	 */
	public PatientTriage(Time time) {
		Comparator<Patient> priorityComparator = new PatientPriorityComparator();
		Comparator<Patient> timeComparator = new PatientTimeComparator();
		Locator<Patient> priorityLocator = new PatientPriorityLocator();
		Locator<Patient> timeLocator = new PatientTimeLocator();
		priorityHeap = new APQ<>(priorityComparator, priorityLocator);
		timeHeap = new APQ<>(timeComparator, timeLocator);
		setMaxWait(time);

		//        ntimeHeap = new APQ<>(timeComparator, timeLocator);
		//        timeStack = new Stack<>();

	}

	/**
	 * Adds patient to queues.  
	 * @param patient to add.
	 * @throws NullPointerException if given null patient
	 */
	public void add(Patient patient) throws NullPointerException {
		if (patient == null) {
			throw new NullPointerException();
		}
		priorityHeap.offer(patient); //add to priority queue
		timeHeap.offer(patient); //add to arrival time queue

		//also offering to ntimeHeap 
		//        ntimeHeap.offer(patient);
		//        Patient p = timeHeap.peek();
		//        timeStack.add(p);
		//        try {
		//			ntimeHeap.poll();
		//		} catch (BoundaryViolationException e) {
		//			e.printStackTrace();
		//		}
	}

	/**
	 * 
	 * @param t1
	 * @param t2
	 * @return the time difference between t1 and t2
	 * @throws BoundaryViolationException
	 */
	private Time waitTime(Time t1, Time t2) throws BoundaryViolationException {
		int m1 = (t1.getHour() * 60) + t1.getMinute();
		int m2 = (t2.getHour() * 60) + t2.getMinute();
		int diff = m1 - m2;
		return new Time(diff / 60, diff % 60);    		
	}

	/**
	 * Removes next patient in queue.  
	 * @param currentTime used to determine whether to use priority or arrival time
	 * @return Next patient to attend to
	 * @throws NullPointerException if given null time
	 * @throws EmptyQueueException if queue is empty
	 * @throws BoundaryViolationException under some internal error conditions
	 */ 
	public Patient remove(Time currentTime) throws NullPointerException, EmptyQueueException, BoundaryViolationException {
		if (currentTime == null)
			throw new NullPointerException();
		if (timeHeap.isEmpty() || priorityHeap.isEmpty())
			throw new EmptyQueueException();

		Time temp = timeHeap.peek().getArrivalTime().elapsed(currentTime);
		Comparator<Time> timeComparator = new TimeComparator();
		APQ<Patient> heap = priorityHeap;
		APQ<Patient> other = timeHeap;
		if (timeComparator.compare(temp, getMaxWait()) >= 0) {
			heap = timeHeap; other = priorityHeap;
		}	

		Patient result = heap.peek();		
		heap.poll();
		other.remove(result);

		return result;
	}

	/**
	 * @return maximum wait time
	 */
	public Time getMaxWait() {
		return maxWait;
	}

	/**
	 * Set the maximum wait time
	 *
	 * @param time - the maximum wait time
	 * @throws NullPointerException if given null time
	 */
	public void setMaxWait(Time time) throws NullPointerException {
		if (time == null) {
			throw new NullPointerException();
		}
		maxWait = time;
	}

}
