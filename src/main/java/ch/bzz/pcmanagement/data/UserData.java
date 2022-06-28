package ch.bzz.pcmanagement.data;

import ch.bzz.pcmanagement.model.User;
import ch.bzz.pcmanagement.service.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * data reader for reading User
 * @author Nick Camenisch
 */
public class UserData {
        private static final UserData instance = new UserData();

    /**
     * finds user by username/password
     * @param username
     * @param password
     * @return
     */
        public static User findUser(String username, String password){
            User user = new User();
            List<User> userList = readJson();

            for (User entry : userList){
                if(entry.getUsername().equals(username) && entry.getPassword().equals(password)){
                    user = entry;
                }
            }
            return user;
        }

    /**
     * reads the json file as a List
     * @return
     */
    public static List<User> readJson(){
            List<User> userList = new ArrayList<>();
            try {
                byte[] jsonData = Files.readAllBytes(Paths.get(Config.getProperty("userJSON")));
                ObjectMapper objectMapper = new ObjectMapper();
                User[] users = objectMapper.readValue(jsonData, User[].class);
                for (User user : users){
                    userList.add(user);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return userList;
        }
}
