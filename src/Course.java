import java.util.List;

public class Course {


    public String id;
    public List<SchEvent> schEvents;
    public boolean lectureAdded = false;
    public boolean practiceAdded = false;
    public boolean added = false;

    public Course(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setSchEvents(List<SchEvent> schEvents) {
        this.schEvents = schEvents;
    }

    public List<SchEvent> getSchEvents() {
        return schEvents;
    }

    public void setLectureAdded() {
        this.lectureAdded = true;
        courseAddedToTable();
    }

    /**
     * Sets practiceAdded to true
     */
    public void setPracticeAdded() {
        this.practiceAdded = true;
        courseAddedToTable();
    }

    public void courseAddedToTable(){
        if(lectureAdded && practiceAdded){
            this.added = true;
        }
    }
}
