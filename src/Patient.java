public class Patient extends PatientBase {

    public Patient(String name, String time) {
        super(name, time);
    }

    @Override
    public int compareTo(PatientBase o) {
        String timeParts[] = this.getTime().split(":");
        int hour1 = Integer.parseInt(timeParts[0]);
        int minutes1 = Integer.parseInt(timeParts[1]);

        timeParts = o.getTime().split(":");
        int hour2 = Integer.parseInt(timeParts[0]);
        int minutes2 = Integer.parseInt(timeParts[1]);

        if (hour1 < hour2) {
            return -1;
        } else if (hour1 > hour2) {
            return 1;
        } else {
            if (minutes1 < minutes2) {
                return -1;
            } else if (minutes1 > minutes2) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /* Add any extra functions below */
}
