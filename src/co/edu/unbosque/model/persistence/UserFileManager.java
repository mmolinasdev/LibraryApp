package co.edu.unbosque.model.persistence;

import co.edu.unbosque.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserFileManager {
    
    private final String filePath;
    private static final String DELIMITER = "|";
    
    public UserFileManager(String dataDirectory) {
        this.filePath = dataDirectory + "users.txt";
    }
    
    public List<User> load() {
        List<User> users = new ArrayList<>();
        File file = new File(filePath);
        
        if (!file.exists()) {
            System.out.println("Users file not found. Starting with empty user list.");
            return users;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            
            while ((line = br.readLine()) != null) {
                lineNumber++;
                
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                User user = UserFileMapper.fromFileLine(line);

                if (user != null) {
                    users.add(user);
                } else {
                    System.out.println("Warning: Invalid user data at line " + lineNumber + ": " + line);
                }
            }
            
            System.out.println("Loaded " + users.size() + " users from file.");
            
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
        
        return users;
    }

    public boolean save(List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (User user : users) {
                bw.write(UserFileMapper.toFileLine(user));
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
            return false;
        }
    }
    
    public boolean exists() {
        return new File(filePath).exists();
    }
    
    public boolean createIfNotExists() {
        File file = new File(filePath);
        
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Created file: " + filePath);
                    return true;
                }
            } catch (IOException e) {
                System.out.println("Error creating file " + filePath + ": " + e.getMessage());
            }
        }
        return false;
    }
    
    public static String getDelimiter() {
        return DELIMITER;
    }
}
