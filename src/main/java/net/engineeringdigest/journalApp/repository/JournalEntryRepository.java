package net.engineeringdigest.journalApp.repository;


import net.engineeringdigest.journalApp.model.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;



@Repository
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId>{

}
