
import java.sql.*;

import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;

public class EmployeeManagementSystem extends Application {

    public static Scanner scanner = new Scanner(System.in);
    static String username;
    static String password;
    Button button;

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Easy Employee Tracker");
        button = new Button();
        button.setText("Enter");
        Label nameLabel = new Label("User Name");
        Label pwLabel = new Label("Password");
        TextField name = new TextField();
        PasswordField pw = new PasswordField();

        VBox layout = new VBox();
        layout.setSpacing(8);
        layout.setPadding(new Insets(10));

        // add to VBox
        layout.getChildren().add(nameLabel);
        layout.getChildren().add(name);
        layout.getChildren().add(pwLabel);
        layout.getChildren().add(pw);
        layout.getChildren().add(button);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();

        // what will happen when button pressed - username and password for database
        // connect
        button.setOnAction(e -> {
            username = name.getText();
            password = pw.getText();
            primaryStage.close();
        });
    }

    public static void main(String[] args) {
        launch(args);

        // Connect to SQL database
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_database", username,
                password)) {

            while (true) { // successful connect to database

                /*
                 * Stage menu = new Stage(); menu.setTitle("Easy Employee Tracker");
                 * 
                 * VBox layout2 = new VBox(); layout2.setSpacing(8); layout2.setPadding(new
                 * Insets(10)); Label l1 = new Label("Employee Management System"); Label l2 =
                 * new Label("1. Add Employee"); layout2.getChildren().add(l1);
                 * layout2.getChildren().add(l2); Scene scene = new Scene(layout2, 300, 250);
                 * menu.setScene(scene); menu.show();
                 */

                System.out.println("\nEmployee Management System");
                System.out.println("1. Add Employee");
                System.out.println("2. View Employee");
                System.out.println("3. Update Employee Name");
                System.out.println("4. Update Employee Salary");
                System.out.println("5. Delete Employee");
                System.out.println("6. Exit");

                System.out.println("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                case 1:
                    addEmployee(conn, scanner);
                    break;
                case 2:
                    viewEmployee(conn, scanner);
                    break;
                case 3:
                    updateEmployeeName(conn, scanner);
                    break;
                case 4:
                    updateEmployeeSalary(conn, scanner);
                    break;
                case 5:
                    deleteEmployee(conn, scanner);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
                }

            }
        } catch (SQLException e) { // unsuccessful connection to database
            System.out.println("Error: " + e.getMessage());
        }
    }

    // add employee function
    private static void addEmployee(Connection conn, Scanner scanner) throws SQLException { // works!
        System.out.println("Enter employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter employee's first and last name: ");
        String name = scanner.nextLine();
        System.out.println("Enter employee salary: ");
        double salary = scanner.nextDouble();

        String query = "INSERT INTO employees(" + "id," + "name," + "salary) " + "VALUES(" + "'" + id + "'," + "'"
                + name + "'," + "'" + salary + "');";
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(query);
        System.out.println("Employee added successfully.");
    }

    // view all employees on database
    private static void viewEmployee(Connection conn, Scanner scanner) throws SQLException { // works!

        String query = "SELECT * FROM employees ;";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        System.out.println("ID\t\tNAME\t\t\tSALARY\n");

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            double salary = rs.getDouble("salary");
            System.out.println(id + "\t\t" + name + "\t\t" + salary);

        }
    }

    // update employee name
    private static void updateEmployeeName(Connection conn, Scanner scanner) throws SQLException { //works!!

        System.out.println("Enter ID of employee to update");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please enter updated name for employee");
        String name = scanner.nextLine();
        String query = "UPDATE employees " + "SET " + "name = '" + name + "'" + "WHERE " + "id = '" + id + "'";
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(query);

        System.out.println("Employee name updated");
    }

    // update salary on employee record
    private static void updateEmployeeSalary(Connection conn, Scanner scanner) throws SQLException { //works!

        System.out.println("Enter ID of employee to update");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Please enter updated salary for employee");
        double salary = scanner.nextDouble();

        String query = "UPDATE employees " + "SET " + "salary = '" + salary + "'" + "WHERE " + "id = '" + id + "'";

        Statement stmt = conn.createStatement();
        stmt.executeUpdate(query);

        System.out.println("Employee salary updated");
    }

    // delete employee record from database
    private static void deleteEmployee(Connection conn, Scanner scanner) throws SQLException { // works!!
        System.out.println("I want to delete employee with ID: ");
        int id = scanner.nextInt();
        String query = "DELETE FROM employees WHERE id = '" + id + "';";
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(query);
        System.out.println("Employee successfully deleted.");
    }

}
