import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.sql.Time;

public class Main {

    static List<Course> all_courses = new ArrayList<>();

    public static void main(String[] args) {

        //create courses
        Course java = new Course("4IT101");
        all_courses.add(java);
        Course ucet = new Course("1FU201");
        all_courses.add(ucet);
        Course stateg = new Course("3SG201");
        all_courses.add(stateg);
        Course sof_in = new Course("4IT115");
        all_courses.add(sof_in);
        Course info = new Course("4IZ210");
        all_courses.add(info);
        EventCreator creator = new EventCreator();

        //creating schEvents
        //events for marketing
        java.setSchEvents(creator.createEvents("exports\\4it101.csv", java));

        //events for database
        ucet.setSchEvents(creator.createEvents("exports\\1fu201.csv", ucet));

        //events for economics
        stateg.setSchEvents(creator.createEvents("exports\\3sg201.csv", stateg));

        //events for economics
        sof_in.setSchEvents(creator.createEvents("exports\\4it115.csv", sof_in));

        //events for economics
        info.setSchEvents(creator.createEvents("exports\\4iz210.csv", info));




        //creating timetable
        TimeTable timeTable = new TimeTable();

        //define rules
        List<Rule> rules = new ArrayList<>();
        rules.add(new Rule(0, new Time(7, 30, 0)));
        rules.add(new Rule(1, new Time(18, 0, 0)));


        timeTable.setRules(rules);

        countEvents(timeTable);

        bubbleSort(timeTable.timeTable, 5);



        /*
        Naplnit celý timeTable všemi dostupnými rozvrhovými akcemi
        seřadit
         */

        /*
        When creating timeTable you need to check:
        1. Whether the course has both lecture and practice already added to timeTable
        2. Whether the course already has lecture/practice added to timeTable
        3. Whether the day you want to add a SchEvent to isn't full
        4. Whether the SchEvent you want to add doesn't overlap with SchEvent already in timeTable
         */


        createTimeTable(timeTable, all_courses);

        for(Day d : timeTable.getTimeTable()){
            System.out.println("-----------------------");
            System.out.println("The day is: " + d.dayOfWeek);
            for(SchEvent e : d.getDay()){
                System.out.println(":::::::::::::::::::::");
                System.out.println(e.getCourse().getId());
                System.out.println(e.getType());
                System.out.println(e.getStart() + "-" + e.getEnd());
                System.out.println(":::::::::::::::::::::::");
            }
            System.out.println("-------------------------------");
        }




    }

    static void bubbleSort(Day arr[], int n)
    {
        int i, j;
        Day temp;
        boolean swapped;
        for (i = 0; i < n - 1; i++) {
            swapped = false;
            for (j = 0; j < n - i - 1; j++) {
                if (arr[j].numOfEvents < arr[j + 1].numOfEvents) {

                    // Swap arr[j] and arr[j+1]
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            // If no two elements were
            // swapped by inner loop, then break
            if (swapped == false)
                break;
        }
    }

    public static void createTimeTable(TimeTable timeTable, List<Course> from_courses){
        int courses_added = 0;

        while(courses_added != from_courses.size()){
            for (Course c : from_courses){
                if(c.lectureAdded && c.practiceAdded){
                    System.out.println("Course " + c.getId() + " added!");
                    System.out.println("..................................");
                    courses_added++;
                }else{
                    for(Day d : timeTable.getTimeTable()){
                        for(SchEvent e : c.getSchEvents()){
                            if(e.getType() == 1){
                                if(c.lectureAdded){
                                    continue;
                                }
                            } else if (e.getType() == 2) {
                                if(c.practiceAdded){
                                    continue;
                                }
                            }

                            if(d.dayOfWeek == e.getDay()){
                                if(d.getDay().isEmpty()){
                                    d.getDay().add(e);
                                    if(e.getType() == 1){
                                        c.setLectureAdded();
                                    }else{
                                        c.setPracticeAdded();
                                    }
                                }else{
                                    if(findMatches(d, e) == 0){
                                        d.getDay().add(e);
                                        if(e.getType() == 1){
                                            c.setLectureAdded();
                                        }else{
                                            c.setPracticeAdded();
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }

    }

    public static int findMatches(Day searchDay, SchEvent event){
        int matches = 0;

        for(SchEvent d_e : searchDay.getDay()){
            if(d_e.getStart().equals(event.getStart())){
                matches++;
            }
            if(event.getStart().before(d_e.getEnd())){
                if(event.getStart().after(d_e.getStart())){
                    matches++;
                }
            }
        }

        return matches;
    }

    public static void countEvents(TimeTable timeTable){
        //events to remove
        List<SchEvent> remove_events = new ArrayList<>();

        for(Day d : timeTable.getTimeTable()){
            for (Course c : all_courses){
                for(SchEvent e : c.getSchEvents()){
                    if(d.dayOfWeek == e.getDay()){
                        d.numOfEvents++;
                    }
                    for(Rule r : timeTable.getRules()){
                        if(r.getDay() == 0){
                            if(e.getStart().equals(r.getTime())){
                                remove_events.add(e);
                            }
                        } else if (r.getDay() == e.getDay()) {
                            if(e.getStart().equals(r.getTime())){
                                remove_events.add(e);
                            }
                        }
                    }
                }
            }
            System.out.println("For day: " + d.dayOfWeek + " the number of events is: " + d.numOfEvents);
        }

        for(SchEvent re : remove_events){
            re.getCourse().getSchEvents().remove(re);
        }
    }


}