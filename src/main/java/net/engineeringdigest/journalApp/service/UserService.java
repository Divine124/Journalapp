package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean saveNewUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.info("Error occurred while saving journal entry for user: " );
            log.debug("Error occurred while saving journal entry for user: " );
            log.warn("Error occurred while saving journal entry for user: " );
            log.trace("Error occurred while saving journal entry for user: " );
            return false;
        }
    }
    public void saveadmin(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }
    public void saveuser(User user)
    {
        userRepository.save(user);
    }
    public List<User> getall(){
        return userRepository.findAll();

    }
    public Optional<User> findbyid(ObjectId id){
        return userRepository.findById(id);
    }
    public void deletebyid(ObjectId myid){
        userRepository.deleteById(myid);
    }
    public User findByUsername(String userame){
        return userRepository.findByUsername(userame);
    }
}
