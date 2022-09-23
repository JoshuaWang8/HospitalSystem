import org.junit.Before;
import org.junit.Test;
import java.util.Iterator;
import static org.junit.Assert.*;

public class Hospital2Test {
    /** Hospital system for tests. */
    Hospital2 hospital2;

    @Before
    public void setUp() {
        hospital2 = new Hospital2();
    }

    /** Test initial system with no entries. */
    @Test
    public void testInitial() {
        Iterator<PatientBase> iterator = hospital2.iterator();
        assertFalse(iterator.hasNext());
    }

    /** Test inserting patients into the system. */
    @Test
    public void testValidEntries() {
        var patient1 = new Patient("Adam", "17:59");
        assertTrue(hospital2.addPatient(patient1));
        var patient2 = new Patient("Bob", "08:00");
        assertTrue(hospital2.addPatient(patient2));
        var patient3 = new Patient("Max", "08:01");
        assertTrue(hospital2.addPatient(patient3));
        var patient4 = new Patient("George", "13:00");
        assertTrue(hospital2.addPatient(patient4));
        var patient5 = new Patient("Zac", "08:00");
        assertTrue(hospital2.addPatient(patient5));

        Iterator<PatientBase> iterator = hospital2.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(patient2, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(patient5, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(patient3, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(patient4, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(patient1, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testInvalidEntries() {
        var patient1 = new Patient("Bob", "08:00");
        assertTrue(hospital2.addPatient(patient1));

        // Adding into a timeslot has already been taken
        var patient2 = new Patient("Alex", "08:00");
        assertTrue(hospital2.addPatient(patient2));

        // Inserting into a time that is not within work hours (before 08:00 or on/after 18:00)
        var patient3 = new Patient("George", "07:00");
        assertFalse(hospital2.addPatient(patient3));

        var patient4 = new Patient("Adam", "18:00");
        assertFalse(hospital2.addPatient(patient4));

        var patient5 = new Patient("Charles", "20:00");
        assertFalse(hospital2.addPatient(patient5));

        // Inserting a time during lunch break
        var patient6 = new Patient("Max", "12:00");
        assertFalse(hospital2.addPatient(patient6));

        // Invalid time format
        var patient7 = new Patient("Greg", "09:60");
        assertFalse(hospital2.addPatient(patient7));

        // Adding another valid time
        var patient8 = new Patient("Zac", "13:31");
        assertTrue(hospital2.addPatient(patient8));

        // Check iterator is correct
        Iterator<PatientBase> iterator = hospital2.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(patient1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(patient2, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(patient8, iterator.next());
        assertFalse(iterator.hasNext());
    }
}
