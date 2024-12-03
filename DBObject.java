import javax.swing.*;
import java.sql.*;

public class DBObject {
    // db connection
    private static final String DB = "jdbc:mysql://localhost:3306/todo_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    Todo todo;
    JTextArea taskArea;
    JTextField inputField;

    public DBObject(Todo todo, JTextField inputField, JTextArea taskArea) {
        this.todo = todo;
        this.inputField = inputField;
        this.taskArea = taskArea;
        createTable();
        getTasks();
    }

    // create table
    public void createTable() {

        String sql = "CREATE TABLE IF NOT EXISTS tasks " +
                "(id INT PRIMARY KEY AUTO_INCREMENT, " +
                "task_name VARCHAR(100) NOT NULL)";

        try (Connection conn = DriverManager.getConnection(DB, USER, PASSWORD);
             Statement stmt = conn.createStatement()
        ) {
            stmt.execute(sql);
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(todo, "Database error: " + e.getMessage());
        }
    }

    // get all tasks
    private void getTasks() {

        // clear list
        taskArea.setText("");

        // select query
        String sql = "SELECT task_name FROM tasks";

        try (Connection conn = DriverManager.getConnection(DB, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while(rs.next()) {
                taskArea.append(rs.getString("task_name") + "\n");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(todo, "Error retrieving tasks: " + e.getMessage());
        }
    }

    // add task
    public void addTask() {
        String task = inputField.getText().trim();

        if (task.isEmpty()) {
            return;
        }

        String sql = "INSERT INTO tasks (task_name) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(DB, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, task);
            pstmt.executeUpdate();

            getTasks();
            inputField.setText("");

        } catch(SQLException e) {
            JOptionPane.showMessageDialog(todo, "Error creating task: " + e.getMessage());
        }
    }

    // delete
    public void deleteTask() {
        String sql = "DELETE FROM tasks ORDER BY id DESC LIMIT 1";

        try (Connection conn = DriverManager.getConnection(DB, USER, PASSWORD);
             Statement stmt = conn.createStatement()
        ) {
            stmt.execute(sql);

            getTasks();
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(todo, "Error deleting task: " + e.getMessage());
        }
    }

}
