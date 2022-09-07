import java.util.Iterator;
import java.util.Objects;

public class Hospital1 extends HospitalBase {

    public Hospital1() {
        /* Add your code here! */
    }

    @Override
    public boolean addPatient(PatientBase patient) {
        /* Add your code here! */
        return false;
    }

    @Override
    public Iterator<PatientBase> iterator() {
        /* Add your code here! */
        return null;
    }

    /* Add any extra functions below */

    public static void main(String[] args) {
        /*
         * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         * REMOVE THE MAIN METHOD BEFORE SUBMITTING TO THE AUTOGRADER
         * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         * The following main method is provided for simple debugging only
         */
        var hospital = new Hospital1();
        var p1 = new Patient("Max", "11:00");
        var p2 = new Patient("Alex", "13:00");
        var p3 = new Patient("George", "14:00");
        hospital.addPatient(p1);
        hospital.addPatient(p2);
        hospital.addPatient(p3);
        var patients = new Patient[] { p1, p2, p3 };
        int i = 0;
        for (var patient : hospital) {
            if (!Objects.equals(patient, patients[i++])) {
                System.err.println("Wrong patient encountered, check your implementation!");
            }
        }
    }
}
