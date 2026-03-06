
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        StudentDAO dao = new StudentDAO();

        while (true) {
            System.out.println("\n1. Insert");
            System.out.println("2. View");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("5. Search By id");
            System.out.println("6. Search By Name");
            System.out.println("7. Exit");
            System.out.println("8. Transaction test");

            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    if(name.trim().isEmpty()){
                        System.out.println("Name cannot be empty.");
                        return;
                    }

                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();

                    if(age <= 0){
                        System.out.println("Age must be greater than 0.");
                        return;
                    }
                    sc.nextLine();
                    System.out.print("Enter Grade: ");
                    String grade = sc.nextLine();

                    Student student = new Student(name, age, grade);
                    //dao.insertStudent(student);
                    boolean inserted = dao.insertStudent(student);

                    if(inserted){
                        System.out.println("Student inserted successfully.");
                    }
                    else{
                        System.out.println("Insert failed.");
                    }
                    break;

//                case 2:
//                    dao.getAllStudents();
//                    break;
                case 2:

                    List<Student> students = dao.getAllStudents();

                    if(students.isEmpty()){
                        System.out.println("No students found.");
                    }
                    else{
                        for(Student s : students){
                            System.out.println(
                                    s.getId() + " | " +
                                            s.getName() + " | " +
                                            s.getAge() + " | " +
                                            s.getGrade()
                            );
                        }
                    }

                    break;

                case 3:
                    System.out.print("Enter ID to update: ");
                    int idUpdate = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter New Grade: ");
                    String newGrade = sc.nextLine();

                    dao.updateStudent(idUpdate, newGrade);
                    break;

                case 4:
                    System.out.print("Enter ID to delete: ");
                    int idDelete = sc.nextInt();

                    dao.deleteStudent(idDelete);
                    break;
                case 5:
                    System.out.println("Enter id to search :");
                    int id=sc.nextInt();
                    dao.getStudentById(id);
                    break;
                case 6:
                    System.out.println("Enter name of Student");
                    String Sname=sc.next();
                    dao.getStudentByName(Sname);
                    break;
                case 7:
                    System.out.println("Exited.");
                    return;
                case 8:

                    Student s = new Student("TransactionStudent", 21, "B");
                    dao.transactionExample(s);
                    break;
                default:
                    System.out.println("Invalid choice.");
           }
        }
    }
}