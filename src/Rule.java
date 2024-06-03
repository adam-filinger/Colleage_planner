import java.sql.Time;

public class Rule {

    /*
    Same values as day in SchEvent + 0 - all days
    Identifies to which day the rules applies
     */
    public int day;
    /*
    Identifies to what time the rules applies, when to not have a SchEvent in a day
     */
    public Time time;

    public Rule(int day, Time time) {
        this.day = day;
        this.time = time;
    }

    public int getDay() {
        return day;
    }

    public Time getTime() {
        return time;
    }
}
