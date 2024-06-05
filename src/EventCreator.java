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
//            System.out.println(event.get(0).hashCode());
//            System.out.println(event.toString());
//            System.out.println(course.getId());
//            System.out.println(event.get(3).hashCode());
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
        if (eventTypHash == -473185540) {
            return 2;
        } else if (eventTypHash == -1478438803){
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
            case 1268528167:
                return 1;
            case 204884182:
                return 2;
            case -1801241174:
                return 3;
            case 766285814:
                return 4;
            case 80699369:
                return 5;
        }

        return 0;
    }

    public Time createTime(String[] TimeString){
        return new Time(Integer.valueOf(TimeString[0]), Integer.valueOf(TimeString[1]), 0);
    }
}
