/**
 * Locator for Patient record in arrival time queue
 */
public class PatientTimeLocator implements Locator<Patient> {

    public int get(Patient p) throws NullPointerException {
        if (p == null) {
            throw new NullPointerException();
        }
        return p.getTimePos();
    }

    public void set(Patient p, int pos) throws NullPointerException {
        if (p == null) {
            throw new NullPointerException();
        }
        p.setTimePos(pos);
    }
}
