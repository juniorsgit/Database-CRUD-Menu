/** Project: SoloLab Assignment 3
 * Purpose Details: To create a CRUD menu for MySQL, MongoDB, Redis, and Blockchain databases
 * Course: IST242
 * Author: Junior Diaz
 * Date Developed: 2/16/2024
 * Last Date Changed: 2/18/2024
 * Rev: 1

 */

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import java.util.Scanner;

public class RedisDatabase {
    public static void redisCRUD(Scanner scanner) {
        try {
            boolean running = true;
            while (running) {
                System.out.println("Select Operation:");
                System.out.println("1. Create");
                System.out.println("2. Read");
                System.out.println("3. Update");
                System.out.println("4. Delete");
                System.out.println("5. Back");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1:
                        createCustomer(scanner);
                        break;
                    case 2:
                        readAllCustomers();
                        break;
                    case 3:
                        updateCustomer(scanner);
                        break;
                    case 4:
                        deleteCustomer(scanner);
                        break;
                    case 5:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please select again.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void createCustomer(Scanner scanner) {
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            System.out.print("Enter customer ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter customer first name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter customer last name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter customer age: ");
            String age = scanner.nextLine();
            System.out.print("Enter customer email: ");
            String email = scanner.nextLine();
            System.out.print("Enter customer gender: ");
            String gender = scanner.nextLine();

            jedis.hset(id, "firstname", firstName);
            jedis.hset(id, "lastname", lastName);
            jedis.hset(id, "age", age);
            jedis.hset(id, "email", email);
            jedis.hset(id, "gender", gender);

            System.out.println("Customer created successfully.");
        } catch (JedisConnectionException e) {
            System.err.println("Could not connect to Redis: " + e.getMessage());
        }
    }

    private static void readAllCustomers() {
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            System.out.println("Customers:");
            for (String id : jedis.keys("*")) {
                System.out.println("ID: " + id);
                System.out.println("First Name: " + jedis.hget(id, "firstname"));
                System.out.println("Last Name: " + jedis.hget(id, "lastname"));
                System.out.println("Age: " + jedis.hget(id, "age"));
                System.out.println("Email: " + jedis.hget(id, "email"));
                System.out.println("Gender: " + jedis.hget(id, "gender"));
                System.out.println();
            }
        } catch (JedisConnectionException e) {
            System.err.println("Could not connect to Redis: " + e.getMessage());
        }
    }

    private static void updateCustomer(Scanner scanner) {
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            System.out.print("Enter customer ID to update: ");
            String id = scanner.nextLine();
            if (jedis.exists(id)) {
                System.out.print("Enter new first name: ");
                String firstName = scanner.nextLine();
                System.out.print("Enter new last name: ");
                String lastName = scanner.nextLine();
                System.out.print("Enter new age: ");
                String age = scanner.nextLine();
                System.out.print("Enter new email: ");
                String email = scanner.nextLine();
                System.out.print("Enter new gender: ");
                String gender = scanner.nextLine();

                jedis.hset(id, "firstname", firstName);
                jedis.hset(id, "lastname", lastName);
                jedis.hset(id, "age", age);
                jedis.hset(id, "email", email);
                jedis.hset(id, "gender", gender);

                System.out.println("Customer updated successfully.");
            } else {
                System.out.println("Customer not found.");
            }
        } catch (JedisConnectionException e) {
            System.err.println("Could not connect to Redis: " + e.getMessage());
        }
    }

    private static void deleteCustomer(Scanner scanner) {
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            System.out.print("Enter customer ID to delete: ");
            String id = scanner.nextLine();
            if (jedis.exists(id)) {
                jedis.del(id);
                System.out.println("Customer deleted successfully.");
            } else {
                System.out.println("Customer not found.");
            }
        } catch (JedisConnectionException e) {
            System.err.println("Could not connect to Redis: " + e.getMessage());
        }
    }
}
