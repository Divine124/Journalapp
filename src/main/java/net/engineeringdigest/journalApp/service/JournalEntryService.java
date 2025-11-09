package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalentryRepository;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Slf4j
public class JournalEntryService {
    @Autowired
    private JournalentryRepository journalentryRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username)

    {
        try {User user = userService.findByUsername(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalentryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveuser(user);

        } catch (Exception e){

            throw new RuntimeException("Could not save journal entry", e);

        }
    }
    public void saveEntry(JournalEntry journalEntry)

    {
        journalentryRepository.save(journalEntry);
    }
    public List<JournalEntry> getall(){
        return journalentryRepository.findAll();

    }
    public Optional<JournalEntry> findbyid(ObjectId id){
        return journalentryRepository.findById(id);
    }
    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        boolean removed = false;
        try {
            User user = userService.findByUsername(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.saveuser(user);
                journalentryRepository.deleteById(id);
            }
        } catch (Exception e) {
            log.error("An error occurred while deleting the entry: ", e);
            throw new RuntimeException("An error occurred while deleting the entry.", e);
        }
        return removed;
    }


    public List<JournalEntry> findByUsername(String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            return user.getJournalEntries();
        }
        return new ArrayList<>();
    }

}
