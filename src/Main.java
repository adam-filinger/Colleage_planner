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
        Course marketing = new Course("3MG216");
        all_courses.add(marketing);
        Course database = new Course("4IT102");
        all_courses.add(database);
        Course economics = new Course("3MI101");
        all_courses.add(economics);
        EventCreator creator = new EventCreator();

        //creating schEvents
        //events for marketing
        marketing.setSchEvents(creator.createEvents("C:\\Users\\eidam\\OneDrive\\Dokumenty\\table_export.csv", marketing));

        //events for database
        database.setSchEvents(creator.createEvents("C:\\Users\\eidam\\OneDrive\\Dokumenty\\table_export (2).csv", database));

        //events for economics
        economics.setSchEvents(creator.createEvents("C:\\Users\\eidam\\OneDrive\\Dokumenty\\table_export (1).csv", economics));


        //creating timetable
        TimeTable timeTable = new TimeTable();

        Day[] daySeq = new Day[5];

        List<Rule> rules = new ArrayList<>();
        timeTable.setRules(rules);
        for(Day d : timeTable.getTimeTable()){
            for (Course c : all_courses){
                for(SchEvent e : c.getSchEvents()){
                    if(d.getDay().equals(e.getDay())){
                        d.numOfEvents++;
                    }
                }
            }
            if(d.numOfEvents > daySeq[0].numOfEvents){
                daySeq[0] = d;
            } else if (d.numOfEvents > daySeq[1].numOfEvents) {
                daySeq[1] = d;
            } else if (d.numOfEvents > daySeq[2].numOfEvents) {
                daySeq[2] = d;
            }else if (d.numOfEvents > daySeq[3].numOfEvents) {
                daySeq[3] = d;
            }else if (d.numOfEvents > daySeq[2].numOfEvents) {
                daySeq[3] = d;
            }else if (d.numOfEvents > daySeq[4].numOfEvents) {
                daySeq[4] = d;
            }

        }

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
                        for(Day d : timeTable.getTimeTable()){
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
            System.out.println(d.dayOfWeek);
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

}