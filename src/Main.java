import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.sql.Time;

public class Main {

    public static void main(String[] args) {

//        Time myTime = new Time(9, 15, 0);
//        Time secondTime = new Time(12, 30, 0);
//        System.out.println(myTime.getTime());
//        System.out.println(secondTime.getTime());
//        Time thirdtime = new Time(9, 15, 0);
//        long duration = secondTime.getTime() - myTime.getTime();
//        System.out.println("-------------------");
//        System.out.println(duration);
//        if(thirdtime.before(myTime)){
//            System.out.println("Can add");
//        } else if (thirdtime.equals(myTime)) {
//            System.out.println("Cant add");
//        } else if (thirdtime.before(secondTime)){
//            System.out.println("Cannot add");
//        } else if (thirdtime.after(secondTime)){
//            System.out.println("Can add");
//        } else if (thirdtime.equals(secondTime)){
//            System.out.println("Cant add");
//        }

        List<Course> all_courses = new ArrayList<>();

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
        java.setSchEvents(creator.createEvents("D:\\Download\\4it101.csv", java));

        //events for database
        ucet.setSchEvents(creator.createEvents("D:\\Download\\1fu201.csv", ucet));

        //events for economics
        stateg.setSchEvents(creator.createEvents("D:\\Download\\3sg201.csv", stateg));

        //events for economics
        sof_in.setSchEvents(creator.createEvents("D:\\Download\\4it115.csv", sof_in));

        //events for economics
        info.setSchEvents(creator.createEvents("D:\\Download\\4iz210.csv", info));




        //creating timetable
        TimeTable timeTable = new TimeTable();

        List<Rule> rules = new ArrayList<>();
        timeTable.setRules(rules);
        for(Day d : timeTable.getTimeTable()){
            for (Course c : all_courses){
                for(SchEvent e : c.getSchEvents()){
                    if(d.dayOfWeek == e.getDay()){
                        d.numOfEvents++;
                    }
                }
            }
            System.out.println("For day: " + d.dayOfWeek + " the number of events is: " + d.numOfEvents);
        }

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
        int courses_added = 0;


        while(courses_added != all_courses.size()){
            for (Course c : all_courses){
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
//                        if (e.getStart().equals(timeTable.getRules().get(0).getTime())) {
//                            continue;
//                        }

                            if(d.dayOfWeek == e.getDay()){
                                if(d.getDay().isEmpty()){
                                    d.getDay().add(e);
                                    if(e.getType() == 1){
                                        c.setLectureAdded();
                                    }else{
                                        c.setPracticeAdded();
                                    }
                                }else{
                                    int matches = 0;
                                    for(SchEvent d_e : d.getDay()){
                                        if(d_e.getStart().equals(e.getStart())){
                                            matches++;
                                        }
                                        if(e.getStart().equals(d_e.getEnd())){
                                            matches++;
                                        }
                                        if(e.getStart().before(d_e.getEnd())){
                                            if(e.getStart().after(d_e.getStart())){
                                                matches++;
                                            }
                                        }
                                    }
                                    if(matches == 0){
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


}