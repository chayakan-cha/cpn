package service.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import service.app.common.config.FocusKeyConfig;
import service.app.db.model.Address;
import service.app.repository.AddressRepository;
import service.app.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService{
	
	Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);
	
	@Autowired
	private FocusKeyConfig focusKeyConfig;
	
	@Autowired
    private AddressRepository addressRepository;
	
	@Override
	@CachePut(value = "saveAddress", key = "#address")
    public boolean saveData(Address address) {
		boolean res = false;
		try {
			Address resAddress = addressRepository.save(address);
			if(!Objects.isNull(resAddress)) {
	    		res = true;
	    	}
		}catch (Exception ex) {
			logger.error(focusKeyConfig.getDatabaseError(), ex);
		}
		return res;
    }
	@Override
	@Cacheable(value = "address", key = "#name", unless = "#result==null||#result==''")
    public List<Address> getDataByUsername(String name) {
    	List<Address> resListAddress = new ArrayList<>();
    	try {
    		resListAddress = addressRepository.findByUserName(name);
    	}catch (Exception ex) {
    		logger.error(focusKeyConfig.getDatabaseError(), ex);
    	}
        return resListAddress;
    }
}
