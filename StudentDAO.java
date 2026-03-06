import java.sql.*;
import java.util.*;
public class StudentDAO {

    // 1️⃣ INSERT STUDENT
    public boolean insertStudent(Student student) {

        String sql = "INSERT INTO students (name, age, grade) VALUES (?, ?, ?)";

        try (Connection con = DBCONNECTION_MAIN.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setInt(2, student.getAge());
            ps.setString(3, student.getGrade());

            int rows = ps.executeUpdate();
           // System.out.println("Student Inserted Successfully! Rows affected: " + rows);
            return rows>0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<Student> getAllStudents() {

        List<Student> students = new ArrayList<>();

        String sql = "SELECT * FROM students";

        try (Connection con = DBCONNECTION_MAIN.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Student s = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("grade")
                );

                students.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    // 2️⃣ VIEW ALL STUDENTS
   /* public void getAllStudents() {

        String sql = "SELECT * FROM students";

        try (Connection con = DBCONNECTION_MAIN.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nStudent Records:");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getInt("age") + " | " +
                                rs.getString("grade")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    // 3️⃣ UPDATE STUDENT
    public void updateStudent(int id, String newGrade) {

        String sql = "UPDATE students SET grade = ? WHERE id = ?";

        try (Connection con = DBCONNECTION_MAIN.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newGrade);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();
            System.out.println("Rows Updated: " + rows);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4️⃣ DELETE STUDENT
    public void deleteStudent(int id) {

        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection con = DBCONNECTION_MAIN.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            System.out.println("Rows Deleted: " + rows);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getStudentById(int id) {

        String sql = "SELECT * FROM students WHERE id = ?";

        try (Connection con = DBCONNECTION_MAIN.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getInt("age") + " | " +
                                rs.getString("grade")
                );
            } else {
                System.out.println("Student not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getStudentByName(String name) {

        String sql = "SELECT * FROM students WHERE name = ?";

        try (Connection con = DBCONNECTION_MAIN.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getInt("age") + " | " +
                                rs.getString("grade")
                );

            }
            if (!found) {
                System.out.println("Student not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void transactionExample(Student student) {
        System.out.println("\n--- TRANSACTION TEST STARTED ---");
        String insertSQL = "INSERT INTO students(name, age, grade) VALUES (?, ?, ?)";
        String updateSQL = "UPDATE students SET grade = ? WHERE id = ?";

        Connection con = null;
        try {
            con = DBCONNECTION_MAIN.getConnection();
            // disable auto commit
            con.setAutoCommit(false);

            System.out.println("Transaction started...");

            PreparedStatement ps1 = con.prepareStatement(insertSQL);
            PreparedStatement ps2 = con.prepareStatement(updateSQL);

            // INSERT
            System.out.println("Executing INSERT query...");
            ps1.setString(1, student.getName());
            ps1.setInt(2, student.getAge());
            ps1.setString(3, student.getGrade());
            ps1.executeUpdate();

            System.out.println("Insert successful.");

            // UPDATE
            System.out.println("Executing UPDATE query...");
            ps2.setString(1, "A+");
            ps2.setInt(2, 9999);  // intentionally wrong id

            int rowsUpdated = ps2.executeUpdate();

            if (rowsUpdated == 0) {
                throw new SQLException("Update failed because ID does not exist.");
            }

            // commit
            con.commit();
            System.out.println("Transaction committed successfully.");

        } catch (Exception e) {

            try {

                if (con != null) {
                    con.rollback();
                    System.out.println("Transaction rolled back.");
                    System.out.println("Reason: " + e.getMessage());
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } finally {

            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

