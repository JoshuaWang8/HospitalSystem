import java.util.Iterator;
import java.util.Objects;

public class Hospital3 extends HospitalBase {

    /** Linked list of appointments. */
    LinkedList appointments;

    /** Node for the linked list. */
    class Node {
        Patient patient;
        Node next;

        public Node(Patient patient) {
            this.patient = patient;
        }
    }

    /** Class for linked list storing all patient appointments. */
    class LinkedList {
        Node head;
        Node tail;

        public LinkedList() {
            head = null;
            tail = null;
        }
    }

    public Hospital3() {
        // Initialise empty linked list for storing patients
        this.appointments = new LinkedList();
    }

    @Override
    public boolean addPatient(PatientBase patient) {
        Node node = new Node((Patient) patient);

        // Get the scheduled time into integers (hour and minutes separated)
        String timeParts[] = patient.getTime().split(":");
        int hour = Integer.parseInt(timeParts[0]);

        // Check times are valid (during work hours and not during lunch times)
        if ((hour < 8) || (hour > 17) || (hour == 12)) {
            return false;
        }

        // Add patient to the tail of the linked list
        if (this.appointments.head == null) {
            this.appointments.head = node;
        } else {
            this.appointments.tail.next = node;
        }
        this.appointments.tail = node;
        return true;
    }

    @Override
    public Iterator<PatientBase> iterator() {
        // Radix sort patients to get patients in order
        LinkedList sortedAppointments = sortPatients(this.appointments);

        // Create a new iterator
        Iterator<PatientBase> it = new Iterator<>() {
            Node current = sortedAppointments.head;

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

    /**
     * Sort patients in order of appointment times using radix sort.
     * @param patients Pointer to head of linked list of patients.
     * @return Pointer to head of sorted linked list of patients.
     */
    public LinkedList sortPatients(LinkedList patients) {
        LinkedList sortedPatients = patients;
        for (int i = 1; i > -1; i--) {
            // Sort first by minutes, then by hours using bucket sort
            sortedPatients = bucketSort(sortedPatients, i == 1 ? 60 : 10, i);
        }
        return sortedPatients;
    }

    /**
     * Bucket sort algorithm to be used with radix sort.
     * @param patients Pointer to the head of the patients linked list
     * @param buckets Number of buckets to sort into
     * @param dimension Dimension we are sorting on
     * @return Head pointer to sorted linked list
     */
    public LinkedList bucketSort(LinkedList patients, int buckets, int dimension) {
        // Bucket array for sorting patients
        LinkedList sorted[] = new LinkedList[buckets];

        // Sort the patients based on the specified dimension (minutes or hours)
        Node next = patients.head;
        while (next != null) {
            // Convert patient time into integers for sorting
            String[] timeParts = next.patient.getTime().split(":");
            int key = Integer.parseInt(timeParts[dimension]);

            // 0 index keys so that we don't waste array space for bucket search
            if (dimension == 0) {
                key -= 8;
            }

            Node nextCopy = new Node(next.patient);
            if (sorted[key] == null) {
                sorted[key] = new LinkedList();
                sorted[key].head = nextCopy;
                sorted[key].tail = nextCopy;
            } else {
                LinkedList list = sorted[key];
                list.tail.next = nextCopy;
                list.tail = nextCopy;
            }
            next = next.next;
        }

        // Get bucket array into a sorted linked list
        LinkedList sortedList = new LinkedList();

        for (LinkedList list : sorted) {
            if (list != null) {
                if (sortedList.head == null) {
                    sortedList.head = list.head;
                    sortedList.tail = list.tail;
                } else {
                    sortedList.tail.next = list.head;
                    sortedList.tail = list.head;
                }
            }
        }

        return sortedList;
    }

    public static void main(String[] args) {
        /*
         * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         * REMOVE THE MAIN METHOD BEFORE SUBMITTING TO THE AUTOGRADER
         * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         * The following main method is provided for simple debugging only
         */
        var hospital = new Hospital3();
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
