package service.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import service.app.db.model.Profile;


@Repository
public interface ProfileRepository extends CrudRepository<Profile, String>{

}
