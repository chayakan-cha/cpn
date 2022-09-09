package service.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import service.app.db.model.Address;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long>{
	@Query(value = "SELECT * FROM address WHERE user_name = :name ",
	        nativeQuery = true)
	public List<Address> findByUserName(String name);
}
