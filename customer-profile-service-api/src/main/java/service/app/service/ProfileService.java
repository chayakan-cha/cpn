package service.app.service;
import service.app.common.model.RequestProfile;
import service.app.db.model.Profile;

public interface ProfileService {
	Profile getDataByUsername(String name);
	boolean saveData(RequestProfile profile);
}
