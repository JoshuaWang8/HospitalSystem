import org.junit.Before;
import org.junit.Test;
import java.util.Iterator;
import static org.junit.Assert.*;

public class Hospital1Test {

    /** Hospital system for tests. */
    Hospital1 hospital1;
    Iterator<PatientBase> iterator;

    @Before
    public void setUp() {
        hospital1 = new Hospital1();
        iterator = hospital1.iterator();
    }

    /** Test initial system with no entries. */
    @Test
    public void testInitial() {
        assertFalse(iterator.hasNext());
    }

    /** Test inserting patients into the system. */
    @Test
    public void testValidEntries() {
        var patient1 = new Patient("Bob", "08:00");
        assertTrue(hospital1.addPatient(patient1));
        var patient2 = new Patient("Adam", "08:20");
        assertTrue(hospital1.addPatient(patient2));
        var patient3 = new Patient("Max", "08:40");
        assertTrue(hospital1.addPatient(patient3));
        var patient4 = new Patient("George", "11:00");
        assertTrue(hospital1.addPatient(patient4));

        assertTrue(iterator.hasNext());
        assertEquals(patient1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(patient2, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(patient3, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(patient4, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testInvalidEntries() {
        var patient1 = new Patient("Bob", "08:00");
        assertTrue(hospital1.addPatient(patient1));

        // Adding into a timeslot has already been taken
        var patient2 = new Patient("Alex", "08:00");
        assertFalse(hospital1.addPatient(patient2));

        // Inserting into a time that is not within work hours (before 08:00 or on/after 18:00)
        var patient3 = new Patient("George", "07:00");
        assertFalse(hospital1.addPatient(patient3));

        var patient4 = new Patient("Adam", "18:00");
        assertFalse(hospital1.addPatient(patient4));

        var patient5 = new Patient("Charles", "20:00");
        assertFalse(hospital1.addPatient(patient5));

        // Inserting a time during lunch break
        var patient6 = new Patient("Max", "12:20");
        assertFalse(hospital1.addPatient(patient6));

        // Inserting a time with incorrect minutes (not 00, 20 or 40)
        var patient7 = new Patient("Zac", "13:30");
        assertFalse(hospital1.addPatient(patient7));

        // Check iterator is correct
        assertTrue(iterator.hasNext());
        assertEquals(patient1, iterator.next());
        assertFalse(iterator.hasNext());
    }
}
