import java.util.Iterator;
import java.util.Objects;

public class Hospital1 extends HospitalBase {

    // Available times are from 08:00 to 18:00 with lunch from 12:00 to 13:00,
    // appointments of 20 minutes, so total of 30 time slots to insert into
    private PatientBase appointments[];

    public Hospital1() {
        appointments = new PatientBase[30];
    }

    @Override
    public boolean addPatient(PatientBase patient) {
        // Get the scheduled time into integers (hour and minutes separated)
        String timeParts[] = patient.getTime().split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);

        // Check times are valid (during work hours and not during lunch times)
        if ((hour < 8) || (hour > 18) || (hour == 12)) {
            return false;
        }

        // Find the index we will insert the patient at. Calculated by (hours - 8) + i,
        // where i is 0 if minutes is 00, 1 if minutes is 20, and 2 if minutes is 30
        int index = hour - 8;

        if (minutes == 0) {
            index += 0;
        } else if (minutes == 20) {
            index += 1;
        } else if (minutes == 40) {
            index += 2;
        } else {
            return false;
        }

        if ((index < 29) && (appointments[index] == null)) {
            appointments[index] = patient;
            return true;
        }

        return false;
    }

    @Override
    public Iterator<PatientBase> iterator() {
        Iterator<PatientBase> it = new Iterator<>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < appointments.length && appointments[currentIndex] != null;
            }

            @Override
            public PatientBase next() {
                return appointments[currentIndex++];
            }
        };

        return it;
    }

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
