/**
 * Locator for Patient record in priority queue
 */
public class PatientPriorityLocator implements Locator<Patient> {

    public int get(Patient p) throws NullPointerException {
        if (p == null) {
            throw new NullPointerException();
        }
        return p.getPriorityPos();
    }

    public void set(Patient p, int pos) {
        if (p == null) {
            throw new NullPointerException();
        }
        p.setPriorityPos(pos);
    }
}