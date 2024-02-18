import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MySQLDatabase {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/customers";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "IST888IST888";

    public static void mysqlCRUD(Scanner scanner) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            boolean running = true;
            while (running) {
                System.out.println("MySQL CRUD Operations:");
                System.out.println("1. Create");
                System.out.println("2. Read");
                System.out.println("3. Update");
                System.out.println("4. Delete");
                System.out.println("5. Back");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1:
                        // Create customer
                        createCustomer(connection, scanner);
                        break;
                    case 2:
                        // Read customers
                        readCustomers(connection);
                        break;
                    case 3:
                        // Update customer
                        updateCustomer(connection, scanner);
                        break;
                    case 4:
                        // Delete customer
                        deleteCustomer(connection, scanner);
                        break;
                    case 5:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please select again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createCustomer(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter customer details:");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Gender: ");
        String gender = scanner.nextLine();

        String sql = "INSERT INTO customers (first_name, last_name, age, email, gender) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, gender);
            preparedStatement.executeUpdate();
            System.out.println("Customer created successfully.");
        }
    }

    private static void readCustomers(Connection connection) throws SQLException {
        String sql = "SELECT * FROM customers";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id"));
                System.out.println("First Name: " + resultSet.getString("first_name"));
                System.out.println("Last Name: " + resultSet.getString("last_name"));
                System.out.println("Age: " + resultSet.getInt("age"));
                System.out.println("Email: " + resultSet.getString("email"));
                System.out.println("Gender: " + resultSet.getString("gender"));
                System.out.println();
            }
        }
    }

    private static void updateCustomer(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter the ID of the customer to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        System.out.println("Enter updated details:");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Gender: ");
        String gender = scanner.nextLine();

        String sql = "UPDATE customers SET first_name = ?, last_name = ?, age = ?, email = ?, gender = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, gender);
            preparedStatement.setInt(6, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer updated successfully.");
            } else {
                System.out.println("Customer with ID " + id + " not found.");
            }
        }
    }

    private static void deleteCustomer(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter the ID of the customer to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        String sql = "DELETE FROM customers WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer deleted successfully.");
            } else {
                System.out.println("Customer with ID " + id + " not found.");
            }
        }
    }
}
