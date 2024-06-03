import java.sql.Time;
import java.util.List;

public class SchEvent {

    /**
        Mon - 1
        Tue - 2
        Wed - 3
        Sat - 4
        Fri - 5
         **/
    public int day;
    public Course course;
    /**
    *1 - lecture
    *2 - practice
     **/
    public int type;
    /*
    start and end - hh:mm
     */
    public Time start;
    public Time end;

    public SchEvent(int day, Course course, int type, Time start, Time end) {
        this.day = day;
        this.start = start;
        this.end = end;
        this.course = course;
        this.type = type;
    }

    public int getDay() {
        return day;
    }

    public Time getStart() {
        return start;
    }

    public Time getEnd() {
        return end;
    }

    public Course getCourse() {
        return course;
    }

    public int getType() {
        return type;
    }
}
