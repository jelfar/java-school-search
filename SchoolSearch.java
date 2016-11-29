import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class SchoolSearch {
    private static ArrayList<Teacher> teachers = new ArrayList<Teacher>();
    private static ArrayList<Entry> entries = new ArrayList<Entry>();
    private static long startTime, resultTime;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the school search!");
        read_file("list.txt");
        read_file("teachers.txt");
        String choiceLine = "";

        while(!choiceLine.equals("Q") && !choiceLine.equals("Quit")) {
            System.out.println("S[tudent]: <lastname> [B[us]]");
            System.out.println("T[eacher]: <lastname>");
            System.out.println("C[lassroom]: <number>");
            System.out.println("B[us]: <number>");
            System.out.println("G[rade]: <number>");
            System.out.println("CT: or ClassroomTeacher: <number>");
            System.out.println("GT: or GradeTeacher: <number>");
            System.out.println("Q[uit]");

            choiceLine = sc.nextLine();

            String inputLastName;
            int inputNumber;
            double seconds;
            ArrayList<Entry> resultEntries = new ArrayList<Entry>();
            ArrayList<Teacher> resultTeachers = new ArrayList<Teacher>();
            Scanner lineScanner = new Scanner(choiceLine);
            String choice = lineScanner.next();

            if(choice.equals("S:") || choice.equals("Student:")) {
                seconds = getStudentInfo(lineScanner, resultEntries);
                if(seconds > -1) {
                    System.out.println("done... " + seconds + "s");
                }
            } else if(choice.equals("T:") || choice.equals("Teacher:")) {
                seconds = getTeacherInfo(lineScanner, resultEntries);
                for(Entry e : resultEntries) {
                    System.out.println(e.StLastName + ", " + e.StFirstName);
                }
                System.out.println("done... " + seconds + "s");
            } else if(choice.equals("C:") || choice.equals("Classroom:")) {
                seconds = getClassroomInfo(lineScanner, resultEntries);
                for(Entry e : resultEntries) {
                    System.out.println(e.StLastName + ", " + e.StFirstName);
                }
                System.out.println("done... " + seconds + "s");
            } else if(choice.equals("B:") || choice.equals("Bus:")) {
                seconds = getBusInfo(lineScanner, resultEntries);
                for(Entry e : resultEntries) {
                    System.out.println(e.StLastName + ", " + e.StFirstName);
                }
                System.out.println("done... " + seconds + "s");
            } else if(choice.equals("G:") || choice.equals("Grade:")) {
                seconds = getGradeInfo(lineScanner, resultEntries);
                for(Entry e : resultEntries) {
                    System.out.println(e.StLastName + ", " + e.StFirstName);
                }
                System.out.println("done... " + seconds + "s");
            } else if(choice.equals("CT:") || choice.equals("ClassroomTeacher:")) {
                seconds = getClassroomTeacherInfo(lineScanner, resultTeachers);
                for(Teacher t : resultTeachers) {
                    System.out.println(t.TLastName + ", " + t.TFirstName);
                }
                System.out.println("done... " + seconds + "s");
            } else if(choice.equals("GT:") || choice.equals("GradeTeacher:")) {
                seconds = getGradeTeacherInfo(lineScanner, resultTeachers);
                for(Teacher t : resultTeachers) {
                    System.out.println(t.TLastName + ", " + t.TFirstName);
                }
                System.out.println("done... " + seconds + "s");
            }
        }
    }

    public static void read_file(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(", ");
                if(filename.equals("list.txt")) {
                    Entry newEntry = new Entry(lineScanner.next(), lineScanner.next(),
                            lineScanner.nextInt(), lineScanner.nextInt(), lineScanner.nextInt());
                    entries.add(newEntry);
                } else if(filename.equals("teachers.txt")) {
                    String firstName = lineScanner.next();
                    String lastName = lineScanner.next();
                    int classroom = lineScanner.nextInt();
                    int grade = -1;
                    for(Entry e : entries) {
                        if(classroom == e.classroom) {
                            e.TLastName = lastName;
                            e.TFirstName = firstName;
                            grade = e.grade;
                        }
                    }
                    Teacher newTeacher = new Teacher(lastName, firstName,
                                                     classroom, grade);
                    teachers.add(newTeacher);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
           // e.printStackTrace();
            System.out.println("Bad file name");
            System.exit(0);
        }
    }

    public static double getStudentInfo(Scanner lineScanner,
                                      ArrayList<Entry> resultEntries) {
       long startTime, resultTime;
       double seconds;
       String inputLastName = lineScanner.next(); 
       if(lineScanner.hasNext()) { //BUS enabled
           String choice = lineScanner.next();
           if(choice.equals("B") || choice.equals("Bus")) {
               startTime = System.nanoTime();
               for(Entry e : entries) {
                   if(e.StLastName.equals(inputLastName)) {
                       resultEntries.add(e);
                   }
               }
               resultTime = System.nanoTime() - startTime;
               seconds = (double)resultTime / 1000000000.0;

               for(Entry e : resultEntries) {
                   System.out.println(e.StLastName + ", " + e.StFirstName + ", " + e.bus);
               }
               return seconds;
           } else {
               return -1;
           }
       } else {
           startTime = System.nanoTime();
           for(Entry e : entries) {
               if(e.StLastName.equals(inputLastName)) {
                   resultEntries.add(e);
               }
           }
           resultTime = System.nanoTime() - startTime;
           seconds = (double)resultTime / 1000000000.0;

           for(Entry e : resultEntries) {
               System.out.println(e.StLastName + ", " + e.StFirstName + ", " + e.grade + ", " + e.classroom + ", " + e.TLastName + ", " + e.TFirstName);
           }
           return seconds;
       }
    }

    public static double getTeacherInfo(Scanner lineScanner,
                                      ArrayList<Entry> resultEntries) {
       String inputLastName = lineScanner.next(); 
       startTime = System.nanoTime();
       for(Entry e : entries) {
           if(e.TLastName.equals(inputLastName)) {
               resultEntries.add(e);
           }
       }
       resultTime = System.nanoTime() - startTime;
       return (double)resultTime / 1000000000.0;
        
    }
                                            
    public static double getClassroomInfo(Scanner lineScanner,
                                        ArrayList<Entry> resultEntries) {
       int inputNumber = lineScanner.nextInt(); 
       startTime = System.nanoTime();
       for(Entry e : entries) {
           if(e.classroom == inputNumber) {
               resultEntries.add(e);
           }
       }
       resultTime = System.nanoTime() - startTime;
       return (double)resultTime / 1000000000.0;

    }

    public static double getBusInfo(Scanner lineScanner, 
                                   ArrayList<Entry> resultEntries) {
           int inputNumber = lineScanner.nextInt(); 
           startTime = System.nanoTime();
           for(Entry e : entries) {
               if(e.bus == inputNumber) {
                   resultEntries.add(e);
               }
           }
           resultTime = System.nanoTime() - startTime;
           return (double)resultTime / 1000000000.0;
    }

    public static double getGradeInfo(Scanner lineScanner,
                                        ArrayList<Entry> resultEntries) {
       int inputNumber = lineScanner.nextInt(); 
       startTime = System.nanoTime();
       for(Entry e : entries) {
           if(e.grade == inputNumber) {
               resultEntries.add(e);
           }
       }
       resultTime = System.nanoTime() - startTime;
       return (double)resultTime / 1000000000.0;
    }

    public static double getClassroomTeacherInfo(Scanner lineScanner,
                                                 ArrayList<Teacher> resultTeachers) {
       int inputNumber = lineScanner.nextInt(); 
       startTime = System.nanoTime();
       for(Teacher t : teachers) {
           if(t.classroom == inputNumber) {
               resultTeachers.add(t);
           }
       }
       resultTime = System.nanoTime() - startTime;
       return (double)resultTime / 1000000000.0;
    }

    public static double getGradeTeacherInfo(Scanner lineScanner,
                                                 ArrayList<Teacher> resultTeachers) {
       int inputNumber = lineScanner.nextInt(); 
       startTime = System.nanoTime();
       for(Teacher t : teachers) {
           if(t.grade == inputNumber) {
               resultTeachers.add(t);
           }
       }
       resultTime = System.nanoTime() - startTime;
       return (double)resultTime / 1000000000.0;
    }
}

class Teacher {
    public String TLastName, TFirstName;
    public int classroom, grade;

    public Teacher(String teachLN, String teachFN, int cr, int gr) {
        TLastName = teachLN;
        TFirstName = teachFN;
        classroom = cr;
        grade = gr;
    }
}

class Entry {
    public String StLastName, StFirstName, TLastName, TFirstName;
    public int grade, classroom, bus;

    public Entry(String studLN, String studFN, int gr, int cr, int b) {
        StLastName = studLN;
        StFirstName = studFN;
        grade = gr;
        classroom = cr;
        bus = b;
    }

    public String toString() {
        return "test: " + StLastName + " " + StFirstName + " " + grade + " " + classroom + " " + bus + " " + TLastName + " " + TFirstName;
    }
}
