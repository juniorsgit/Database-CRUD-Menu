/** Project: SoloLab Assignment 3
 * Purpose Details: To create a CRUD menu for MySQL, MongoDB, Redis, and Blockchain databases
 * Course: IST242
 * Author: Junior Diaz
 * Date Developed: 2/16/2024
 * Last Date Changed: 2/18/2024
 * Rev: 1

 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

// Define a Block class
class Block {
    private int index;
    private long timestamp;
    private String previousHash;
    private String hash;
    private String data;
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String gender;

    // Constructor
    public Block(int index, String previousHash, int id, String firstName, String lastName, int age, String email, String gender) {
        this.index = index;
        this.timestamp = new Date().getTime();
        this.previousHash = previousHash;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.gender = gender;
        this.data = id + firstName + lastName + age + email + gender;
        this.hash = calculateHash();
    }

    // Calculate the hash of the block
    public String calculateHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String input = index + timestamp + previousHash + data;
            byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    // Getters and Setters
    public int getIndex() {
        return index;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getHash() {
        return hash;
    }

    public String getData() {
        return data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

// Define a Blockchain class
class Blockchain {
    private List<Block> chain;

    // Constructor
    public Blockchain() {
        chain = new ArrayList<>();
        // Create the genesis block (the first block in the chain)
        chain.add(new Block(0, "0", 0, "", "", 0, "", ""));
    }

    // Add a new block to the blockchain
    public void addBlock(int id, String firstName, String lastName, int age, String email, String gender) {
        Block previousBlock = chain.get(chain.size() - 1);
        Block newBlock = new Block(previousBlock.getIndex() + 1, previousBlock.getHash(), id, firstName, lastName, age, email, gender);
        chain.add(newBlock);
    }

    public void printBlockchain() {
        for (Block block : chain) {
            System.out.println("Block #" + block.getIndex());
            System.out.println("Timestamp: " + block.getTimestamp());
            System.out.println("Previous Hash: " + block.getPreviousHash());
            System.out.println("Hash: " + block.getHash());
            System.out.println("ID: " + block.getId());
            System.out.println("First Name: " + block.getFirstName());
            System.out.println("Last Name: " + block.getLastName());
            System.out.println("Age: " + block.getAge());
            System.out.println("Email: " + block.getEmail());
            System.out.println("Gender: " + block.getGender());
            System.out.println();
        }
    }

    // CRUD Operations

    public void createBlock(int id, String firstName, String lastName, int age, String email, String gender) {
        addBlock(id, firstName, lastName, age, email, gender);
    }

    public void updateBlock(int index, int id, String firstName, String lastName, int age, String email, String gender) {
        if (index >= 0 && index < chain.size()) {
            Block blockToUpdate = chain.get(index);
            blockToUpdate.setId(id);
            blockToUpdate.setFirstName(firstName);
            blockToUpdate.setLastName(lastName);
            blockToUpdate.setAge(age);
            blockToUpdate.setEmail(email);
            blockToUpdate.setGender(gender);
            // Recalculate hash after updating block data
            blockToUpdate.calculateHash();
            System.out.println("Block updated successfully.");
        } else {
            System.out.println("Invalid block index.");
        }
    }

    public void deleteBlock(int index) {
        if (index >= 0 && index < chain.size()) {
            chain.remove(index);
            // Recalculate hash for subsequent blocks
            for (int i = index; i < chain.size(); i++) {
                chain.get(i).calculateHash();
            }
            System.out.println("Block deleted successfully.");
        } else {
            System.out.println("Invalid block index.");
        }
    }
}

public class SimpleBlockchain {
    public static void blockchainCRUD(Scanner scanner) {
        Blockchain blockchain = new Blockchain();
        boolean running = true;
        while (running) {
            System.out.println("Select Operation:");
            System.out.println("1. Create Block");
            System.out.println("2. Read Blockchain");
            System.out.println("3. Update Block");
            System.out.println("4. Delete Block");
            System.out.println("5. Back");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character
                    System.out.print("Enter First Name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Enter Last Name: ");
                    String lastName = scanner.nextLine();
                    System.out.print("Enter Age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter Gender: ");
                    String gender = scanner.nextLine();

                    blockchain.createBlock(id, firstName, lastName, age, email, gender);
                    System.out.println("Block created successfully.");
                    break;
                case 2:
                    blockchain.printBlockchain();
                    break;
                case 3:
                    System.out.print("Enter the index of the block to update: ");
                    int updateIndex = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character
                    System.out.print("Enter ID: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character
                    System.out.print("Enter First Name: ");
                    String updateFirstName = scanner.nextLine();
                    System.out.print("Enter Last Name: ");
                    String updateLastName = scanner.nextLine();
                    System.out.print("Enter Age: ");
                    int updateAge = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character
                    System.out.print("Enter Email: ");
                    String updateEmail = scanner.nextLine();
                    System.out.print("Enter Gender: ");
                    String updateGender = scanner.nextLine();

                    blockchain.updateBlock(updateIndex, updateId, updateFirstName, updateLastName, updateAge, updateEmail, updateGender);
                    break;
                case 4:
                    System.out.print("Enter the index of the block to delete: ");
                    int deleteIndex = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character
                    blockchain.deleteBlock(deleteIndex);
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            blockchainCRUD(scanner);
        }
    }
}
