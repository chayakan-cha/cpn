package service.app.controller;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import io.micrometer.core.annotation.Timed;
import service.app.common.config.FocusKeyConfig;
import service.app.common.constant.Message;
import service.app.common.model.AddressByProfile;
import service.app.common.model.ReponseAddress;
import service.app.common.model.ReponseProfile;
import service.app.common.model.RequestAddress;
import service.app.common.model.RequestProfile;
import service.app.db.model.Address;
import service.app.db.model.Profile;
import service.app.service.AddressService;
import service.app.service.ProfileService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	  
	SimpleDateFormat dateYMDHMSs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", new Locale("en", "US"));

	Logger logger = LoggerFactory.getLogger(CustomerController.class);
	   
	@Autowired
    private ProfileService profileService;
	
	@Autowired
    private AddressService addressService;
	
	@Autowired
	private FocusKeyConfig focusKeyConfig;
	
	Gson gson = new Gson();
	
	@Timed(value="get.data.by.username",description="time to retrieve",percentiles={0.5,0.9})
	@RequestMapping(
			  value = "/getDataByUsername", 
			  method = RequestMethod.GET, 
			  produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<ReponseProfile> getDataByUsername(@RequestBody RequestProfile body) {
		logger.info("=request= ->",gson.toJson(body));
		ReponseProfile  res = new ReponseProfile();
		Long startTime = System.currentTimeMillis();
		String startDateTime = dateYMDHMSs.format(startTime);
		try {
			Profile resProfile = profileService.getDataByUsername(body.getName());		
			if(resProfile != null) {
				res.setProfile(resProfile);
				res.setMsg(Message.SUCCESS.SUCCESS000.getMessage());
				res.setStatus(Message.SUCCESS.SUCCESS000.getCode());
				res.setStartDtm(startDateTime);
			}else {
				res.setMsg(Message.ERROR.ERROR102_ERROR_INVALID_USERNAME.getMessage());
				res.setStatus(Message.ERROR.ERROR102_ERROR_INVALID_USERNAME.getCode());
				res.setStartDtm(startDateTime);
			}
		}catch (Exception ex){
			logger.error(focusKeyConfig.getExceptionError(), ex);
		}finally {
			long endTime = System.currentTimeMillis();
			String endDateTime = dateYMDHMSs.format(endTime);
			long responseTime = endTime - startTime;

			res.setEndDtm(endDateTime);
			res.setResTime(responseTime);
		}
		logger.info("=response= ->",gson.toJson(res));
		return ResponseEntity.status(HttpStatus.OK).body(res);	
	}
	@Timed(value="get.address.by.username",description="time to retrieve",percentiles={0.5,0.9})
	@RequestMapping(
			  value = "/getAddressByUsername", 
			  method = RequestMethod.GET, 
			  produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<ReponseAddress> getAddressByUsername(@RequestBody RequestProfile body) {
		logger.info("=request= ->",gson.toJson(body));
		AddressByProfile addressByProfile = new AddressByProfile();
		List<AddressByProfile> listAddress = new ArrayList<>();
		ReponseAddress res = new ReponseAddress();
		Long startTime = System.currentTimeMillis();
		String startDateTime = dateYMDHMSs.format(startTime);
		try {
			List<Address> resAddress = addressService.getDataByUsername(body.getName());	

	    	if(resAddress != null && resAddress.size() > 0) {
	    		resAddress.forEach(data ->{
	    			addressByProfile.setHouseNo(data.getHouseNo());
	    			addressByProfile.setLane(data.getLane());
	    			addressByProfile.setPostalCode(data.getPostalCode());
	    			addressByProfile.setProvince(data.getProvince());
	        		addressByProfile.setDistrict(data.getDistrict());
	        		addressByProfile.setAlley(data.getAlley());
	        		addressByProfile.setRoad(data.getRoad());
	        		addressByProfile.setSubDistrict(data.getSubDistrict());
	        		addressByProfile.setVillageNo(data.getVillageNo());
	        		
	        		listAddress.add(addressByProfile);
	        		res.setAddress(listAddress);
	        	});
	    		res.setUserName(body.getName());
	    		res.setMsg(Message.SUCCESS.SUCCESS000.getMessage());
		    	res.setStatus(Message.SUCCESS.SUCCESS000.getCode());
		    	res.setStartDtm(startDateTime);
	    	}else {
				res.setMsg(Message.ERROR.ERROR102_ERROR_INVALID_USERNAME.getMessage());
				res.setStatus(Message.ERROR.ERROR102_ERROR_INVALID_USERNAME.getCode());
				res.setStartDtm(startDateTime);
	    	}
	    	
		}catch (Exception ex){
			logger.error(focusKeyConfig.getExceptionError(), ex);
		}finally {
			long endTime = System.currentTimeMillis();
			String endDateTime = dateYMDHMSs.format(endTime);
			long responseTime = endTime - startTime;

			res.setEndDtm(endDateTime);
			res.setResTime(responseTime);
		}
		logger.info("=response= ->",gson.toJson(res));
		return ResponseEntity.status(HttpStatus.OK).body(res);	
	}
	
	@RequestMapping(
			  value = "/addProfile", 
			  method = RequestMethod.POST, 
			  produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<ReponseProfile> addProfile(@RequestBody RequestProfile body) {
		logger.info("=request= ->",gson.toJson(body));
		ReponseProfile res = new ReponseProfile();
		Long startTime = System.currentTimeMillis();
		String startDateTime = dateYMDHMSs.format(startTime);
		try {		
			boolean resSave = profileService.saveData(body);
			if(resSave) {
				res.setMsg(Message.SUCCESS.SUCCESS000.getMessage());
				res.setStatus(Message.SUCCESS.SUCCESS000.getCode());
				res.setStartDtm(startDateTime);
			}else {
				res.setMsg(MessageFormat.format(Message.ERROR.ERROR103_ERROR_DB_SAVE_EXCEPTION.getMessage(), "Error save failed."));
				res.setStatus(Message.ERROR.ERROR103_ERROR_DB_SAVE_EXCEPTION.getCode());
				res.setStartDtm(startDateTime);
			}
		}catch (Exception ex){
			logger.error(focusKeyConfig.getExceptionError(), ex);
		}finally {
			long endTime = System.currentTimeMillis();
			String endDateTime = dateYMDHMSs.format(endTime);
          	long responseTime = endTime - startTime;

          	res.setEndDtm(endDateTime);
          	res.setResTime(responseTime);
		}
		logger.info("=response= ->",gson.toJson(body));
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	@RequestMapping(
			  value = "/addAddress", 
			  method = RequestMethod.POST, 
			  produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<ReponseProfile> addAddress(@RequestBody RequestAddress body) {
		logger.info("=request= ->",gson.toJson(body));
		ReponseProfile res = new ReponseProfile();
        Long startTime = System.currentTimeMillis();
        String startDateTime = dateYMDHMSs.format(startTime);
		try {
			
			Profile resProfile = profileService.getDataByUsername(body.getUserName());	
			if(resProfile != null) {
				Address address = new Address();
				address.setAlley(body.getAlley());
				address.setLane(body.getLane());
				address.setRoad(body.getRoad());
				address.setDistrict(body.getDistrict());
				address.setProvince(body.getProvince());
				address.setPostalCode(body.getPostalCode());
				address.setVillageNo(body.getVillageNo());
				address.setHouseNo(body.getHouseNo());
				address.setSubDistrict(body.getSubDistrict());
				address.setUserName(body.getUserName());
				boolean resSave = addressService.saveData(address);
				if(resSave) {
					res.setMsg(Message.SUCCESS.SUCCESS000.getMessage());
					res.setStatus(Message.SUCCESS.SUCCESS000.getCode());
					res.setStartDtm(startDateTime);
				}else {
					res.setMsg(MessageFormat.format(Message.ERROR.ERROR103_ERROR_DB_SAVE_EXCEPTION.getMessage(), "Error save failed."));
					res.setStatus(Message.ERROR.ERROR103_ERROR_DB_SAVE_EXCEPTION.getCode());
					res.setStartDtm(startDateTime);
				}
			}else {
				res.setMsg(Message.ERROR.ERROR102_ERROR_INVALID_USERNAME.getMessage());
				res.setStatus(Message.ERROR.ERROR102_ERROR_INVALID_USERNAME.getCode());
				res.setStartDtm(startDateTime);
			}
		}catch (Exception ex){
			logger.error(focusKeyConfig.getExceptionError(), ex);
		}finally {
			long endTime = System.currentTimeMillis();
            String endDateTime = dateYMDHMSs.format(endTime);
            long responseTime = endTime - startTime;

            res.setEndDtm(endDateTime);
            res.setResTime(responseTime);
		}
		logger.info("=response= ->",gson.toJson(body));
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
}
