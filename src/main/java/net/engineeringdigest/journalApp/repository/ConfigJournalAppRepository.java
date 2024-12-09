package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.model.ConfigJournalAppEntity;
import net.engineeringdigest.journalApp.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}
