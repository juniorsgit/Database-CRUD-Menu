/** Project: SoloLab Assignment 3
 * Purpose Details: To create a CRUD menu for MySQL, MongoDB, Redis, and Blockchain databases
 * Course: IST242
 * Author: Junior Diaz
 * Date Developed: 2/16/2024
 * Last Date Changed: 2/18/2024
 * Rev: 1

 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                System.out.println("Select Database:");
                System.out.println("1. MySQL");
                System.out.println("2. MongoDB");
                System.out.println("3. Redis");
                System.out.println("4. Blockchain");
                System.out.println("5. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1:
                        MySQLDatabase.mysqlCRUD(scanner);
                        break;
                    case 2:
                        MongoDBDatabase.mongoDBCRUD(scanner);
                        break;
                    case 3:
                        RedisDatabase.redisCRUD(scanner); // Corrected method name
                        break;
                    case 4:
                        SimpleBlockchain.blockchainCRUD(scanner);
                        break;
                    case 5:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please select again.");
                }
            }
        }
    }
}
