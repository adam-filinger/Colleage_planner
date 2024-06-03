import java.util.ArrayList;
import java.util.List;

public class Day {

    public List<SchEvent> day;
    public int dayOfWeek;
    public int numOfEvents; //specifies number of available events for that day

    public Day(int dayOfWeek) {
        this.day = new ArrayList<>();
        this.dayOfWeek = dayOfWeek;
    }

    public List<SchEvent> getDay() {
        return day;
    }

    public void setDay(List<SchEvent> day) {
        this.day = day;
    }

    /*
    Method hasEvent(SchEvent)
        Checks for lecture/practice of a course in a day
        If there's already a lecture/practice of a course in that day
        returns true

    public boolean hasEvent(SchEvent event){
        for(SchEvent e : day){
            if(e.getCourse().getId().equals(event.getCourse().getId())){
                return e.getType() == event.getType();
            }
        }
        return false;
    }

     */

    public boolean isFull(){
        return day.size() == 6;
    }
}
