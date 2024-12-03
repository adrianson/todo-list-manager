import javax.swing.*;
import java.awt.*;

public class GUI {
    private JTextField inputField;
    private JTextArea taskArea;
    private JButton addButton;
    private JButton deleteButton;

    public JTextField getInputField() {
        return inputField;
    }

    public JTextArea getTaskArea() {
        return taskArea;
    }

    // gui
    public GUI(Todo todo) {
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));

        inputField = new JTextField();
        addButton = new JButton("Add");

        taskArea = new JTextArea();
        taskArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(taskArea);

        deleteButton = new JButton("Delete");

        topPanel.add(inputField, BorderLayout.CENTER);
        topPanel.add(addButton, BorderLayout.EAST);

        todo.add(topPanel, BorderLayout.NORTH);
        todo.add(scrollPane, BorderLayout.CENTER);
        todo.add(deleteButton, BorderLayout.SOUTH);
    }

    public void addListeners(DBObject db) {
        addButton.addActionListener(e -> db.addTask());
        deleteButton.addActionListener(e -> db.deleteTask());
    }

}
