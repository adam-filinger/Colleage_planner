import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventCreator
{

    ArrayList<List> records = new ArrayList();
    int day = 1;
    int typ = 1;
    Time start;
    Time end;

    public List<SchEvent> createEvents(String path, Course course){
        List<SchEvent> schEvents = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(
                path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (List event: records){
            if(event.get(0).equals("Den")){
                continue;
            }
            day = day(event.get(0).hashCode());
//            System.out.println(event.toString());
//            System.out.println(course.getId());
            typ = eventType(event.get(3).hashCode());
            String timeString = (String) event.get(1);
            String[] startAndEndTimes = timeString.split("-");
            schEvents.add(new SchEvent(day, course, typ, createTime(startAndEndTimes[0].split(":")), createTime(startAndEndTimes[1].split(":"))));
        }
        return schEvents;
    }


    /*
    return value of type of event (exp. in class schevent), if invalid hash returns 0
     */
    public int eventType(int eventTypHash){
        if (eventTypHash < 0) {
            return 2;
        } else if (eventTypHash > 0){
            return 1;
        } else{
            return 0;
        }

    }

    /*
    return the int value of a Day(exp. in class Day), if invalid hash returns O
     */
    public int day(int dayHash){

        switch (dayHash){
            case 1331298713:
                return 1;
            case 395181433:
                return 2;
            case 140774534:
                return 3;
            case 1765072423:
                return 4;
            case 2026289997:
                return 5;
        }

        return 0;
    }

    public Time createTime(String[] TimeString){
        return new Time(Integer.valueOf(TimeString[0]), Integer.valueOf(TimeString[1]), 0);
    }
}
