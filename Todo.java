import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Todo extends JFrame {

    // constructor
    public Todo() {
        setTitle("Todo list app");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // layout
        setLayout(new BorderLayout(10, 10));

        GUI gui = new GUI(this);
        DBObject db = new DBObject(this, gui.getInputField(), gui.getTaskArea());
        gui.addListeners(db);
    }

}
