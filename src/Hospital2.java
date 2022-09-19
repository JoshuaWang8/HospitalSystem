import java.util.Iterator;
import java.util.Objects;

public class Hospital2 extends HospitalBase {

    /** Pointer to the head of the linked list of appointments. */
    Node head;

    /** Node for the linked list. */
    class Node {
        Patient patient;
        Node next;

        public Node(Patient patient) {
            this.patient = patient;
        }
    }

    public Hospital2() {
        this.head = null;
    }

    @Override
    public boolean addPatient(PatientBase patient) {
        Node newPatient = new Node((Patient) patient);

        // Add new node to correct position in linked list
        if (head == null) {
            head = newPatient;
        } else {
            Node current = this.head;
            while ((current.next != null) && (current.patient.compareTo(newPatient.patient) < 0)) {
                current = current.next;
            }
            current.next = newPatient;
        }
        return true;
    }

    @Override
    public Iterator<PatientBase> iterator() {
        Node head = this.head;

        Iterator<PatientBase> it = new Iterator<>() {
            Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Patient next() {
                Patient nextPatient = current.patient;
                current = current.next; // Move to the next node to read
                return nextPatient;
            }
        };

        return it;
    }

    /* Add any extra functions below */

    public static void main(String[] args) {
        /*
         * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         * REMOVE THE MAIN METHOD BEFORE SUBMITTING TO THE AUTOGRADER
         * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         * The following main method is provided for simple debugging only
         */
        var hospital = new Hospital2();
        var p1 = new Patient("Max", "11:00");
        var p2 = new Patient("Alex", "13:15");
        var p3 = new Patient("George", "14:00");
        hospital.addPatient(p1);
        hospital.addPatient(p2);
        hospital.addPatient(p3);
        var patients = new Patient[] {p1, p2, p3};
        int i = 0;
        for (var patient : hospital) {
            assert Objects.equals(patient, patients[i++]);
        }
    }
}
