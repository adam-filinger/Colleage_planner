import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TimeTable {

    public Day[] timeTable;

    public List<Rule> rules;

    public TimeTable() {
        this.timeTable = new Day[5];
        for (int i = 0; i < 5; i++) {
            timeTable[i] = new Day(i + 1);
        }
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public Day[] getTimeTable() {
        return timeTable;
    }

    public List<Rule> getRules() {
        return rules;
    }
}
