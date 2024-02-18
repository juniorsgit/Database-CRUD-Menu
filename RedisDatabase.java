import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Scanner;
import java.util.Set;

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
            System.out.print("Enter customer key: ");
            String key = scanner.nextLine();
            System.out.print("Enter customer value: ");
            String value = scanner.nextLine();
            jedis.set(key, value);
            System.out.println("Customer created successfully.");
        } catch (JedisConnectionException e) {
            System.err.println("Could not connect to Redis: " + e.getMessage());
        }
    }

    private static void readAllCustomers() {
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            Set<String> keys = jedis.keys("*");
            if (keys.isEmpty()) {
                System.out.println("No customers found.");
            } else {
                System.out.println("Customers:");
                for (String key : keys) {
                    System.out.println(key + ": " + jedis.get(key));
                }
            }
        } catch (JedisConnectionException e) {
            System.err.println("Could not connect to Redis: " + e.getMessage());
        }
    }

    private static void updateCustomer(Scanner scanner) {
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            System.out.print("Enter customer key to update: ");
            String key = scanner.nextLine();
            if (jedis.exists(key)) {
                System.out.print("Enter new value: ");
                String value = scanner.nextLine();
                jedis.set(key, value);
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
            System.out.print("Enter customer key to delete: ");
            String key = scanner.nextLine();
            if (jedis.exists(key)) {
                jedis.del(key);
                System.out.println("Customer deleted successfully.");
            } else {
                System.out.println("Customer not found.");
            }
        } catch (JedisConnectionException e) {
            System.err.println("Could not connect to Redis: " + e.getMessage());
        }
    }
}
