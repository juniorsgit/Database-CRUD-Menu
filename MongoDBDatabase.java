/** Project: SoloLab Assignment 3
 * Purpose Details: To create a CRUD menu for MySQL, MongoDB, Redis, and Blockchain databases
 * Course: IST242
 * Author: Junior Diaz
 * Date Developed: 2/16/2024
 * Last Date Changed: 2/18/2024
 * Rev: 1

 */

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import java.util.Scanner;

public class MongoDBDatabase {
    private static final String DATABASE_NAME = "customers";
    private static final String COLLECTION_NAME = "customers";

    public static void mongoDBCRUD(Scanner scanner) {
        try (MongoClient mongoClient = MongoClients.create()) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            boolean running = true;
            while (running) {
                System.out.println("MongoDB CRUD Operations:");
                System.out.println("1. Create");
                System.out.println("2. Read");
                System.out.println("3. Update");
                System.out.println("4. Delete");
                System.out.println("5. Back");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1:
                        createDocument(collection, scanner);
                        break;
                    case 2:
                        readDocuments(collection);
                        break;
                    case 3:
                        updateDocument(collection, scanner);
                        break;
                    case 4:
                        deleteDocument(collection, scanner);
                        break;
                    case 5:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please select again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createDocument(MongoCollection<Document> collection, Scanner scanner) {
        System.out.println("Enter customer details:");
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
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

        Document document = new Document("id", id)
                .append("first_name", firstName)
                .append("last_name", lastName)
                .append("age", age)
                .append("email", email)
                .append("gender", gender);

        collection.insertOne(document);
        System.out.println("Document created successfully.");
    }

    private static void readDocuments(MongoCollection<Document> collection) {
        for (Document document : collection.find()) {
            System.out.println(document.toJson());
        }
    }

    private static void updateDocument(MongoCollection<Document> collection, Scanner scanner) {
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

        collection.updateOne(Filters.eq("id", id), Updates.combine(
                Updates.set("first_name", firstName),
                Updates.set("last_name", lastName),
                Updates.set("age", age),
                Updates.set("email", email),
                Updates.set("gender", gender)
        ));
        System.out.println("Document updated successfully.");
    }

    private static void deleteDocument(MongoCollection<Document> collection, Scanner scanner) {
        System.out.print("Enter the ID of the customer to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        collection.deleteOne(Filters.eq("id", id));
        System.out.println("Document deleted successfully.");
    }
}
