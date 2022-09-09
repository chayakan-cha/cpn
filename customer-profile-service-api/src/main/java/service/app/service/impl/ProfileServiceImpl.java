package service.app.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import service.app.common.config.FocusKeyConfig;
import service.app.common.model.RequestProfile;
import service.app.db.model.Profile;
import service.app.repository.ProfileRepository;
import service.app.service.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService{
	
	Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class);
	
	@Autowired
	private FocusKeyConfig focusKeyConfig;
	
	@Autowired
    private ProfileRepository profileRepository;

	@Override
    @Cacheable(value = "name", key = "#name", unless = "#result==null||#result==''")
    public Profile getDataByUsername(String name) {
    	Profile profile = new Profile();
    	Optional<Profile> resProfile = profileRepository.findById(name);
    	try {
    		if(resProfile.isPresent()) {
        		profile.setName(resProfile.get().getName());
        		profile.setEmail(resProfile.get().getEmail());
        		profile.setBirthday(resProfile.get().getBirthday());
        		profile.setPrimaryPhone(resProfile.get().getPrimaryPhone());
        	}else {
        		profile = null;
        	}
    	}catch (Exception ex) {
    		logger.error(focusKeyConfig.getDatabaseError(), ex);
    	}
        return profile;
    }
	
	@Override
    @CachePut(value = "saveProfile", key = "#requestProfile")
    public boolean saveData(RequestProfile requestProfile) {
    	boolean res = false;
    	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    	Date birthday;
    	try {
	    	Profile profile = new Profile();
	    	profile.setEmail(requestProfile.getEmail());
	    	profile.setName(requestProfile.getName());
	    	profile.setPrimaryPhone(requestProfile.getPrimaryPhone());
	    	profile.setSex(requestProfile.getSex());
	    	birthday = formatter.parse(requestProfile.getBirthday());
	    	profile.setBirthday(birthday);
	    	Profile resProfile = profileRepository.save(profile);
	    	if(!Objects.isNull(resProfile)) {
	    		res = true;
	    	}
		} catch (Exception ex) {
			logger.error(focusKeyConfig.getDatabaseError(), ex);
		}
    	return res;
    }
    
   
}
