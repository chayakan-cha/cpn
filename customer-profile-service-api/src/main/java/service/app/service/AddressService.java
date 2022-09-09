package service.app.service;

import java.util.List;
import service.app.db.model.Address;

public interface AddressService {
	boolean saveData(Address address);
	List<Address> getDataByUsername(String name);
}
