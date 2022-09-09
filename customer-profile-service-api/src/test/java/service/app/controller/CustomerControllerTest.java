package service.app.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import service.app.common.config.FocusKeyConfig;
import service.app.common.model.RequestProfile;
import service.app.db.model.Address;
import service.app.db.model.Profile;
import service.app.service.AddressService;
import service.app.service.ProfileService;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
    private ProfileService profileService;
	
	@MockBean
    private AddressService addressService;
	
	@MockBean
    private FocusKeyConfig focusKeyConfig;
	
	String requestGetDataByUsername = "{\"name\":\"Jack\"}";
	String requestAddProfile = "{\"name\":\"Jack\",\"email\":\"jack@example.com\", \"birthday\":\"1995-07-19 10:30:00\", \"primaryPhone\":\"xxx-xxxxxx\", \"sex\":\"female\"}";
	String requestAddAddress = "{\"userName\":\"Jack\",\"villageNo\":\"-\" ,\"houseNo\":\"00/2\", \"alle\":\"1\", \"lane\":\"23\", \"road\":\"Ladphrao\", \"district\":\"Chatuchak\", \"subDistrict\":\"Chom Phon\", \"province\":\"Bangkok\", \"postalCode\":\"10900\"}";

	@Test  
    public void getProfileByUsernameCaseSuccess()throws Exception{  
		Profile mockProfile = new Profile();
		mockProfile.setName("Test");
		mockProfile.setEmail("test@example.com");
		mockProfile.setBirthday(new Date());
		mockProfile.setPrimaryPhone("xxx-xxxxxxx");
		Mockito.when(profileService.getDataByUsername(Mockito.anyString())).thenReturn(mockProfile);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/customer/getDataByUsername")
				.accept(MediaType.APPLICATION_JSON).content(requestGetDataByUsername)
				.contentType(MediaType.APPLICATION_JSON);		
		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("000"));
    } 
	@Test  
    public void getProfileByUsernameCaseFailed()throws Exception{  
		Mockito.when(profileService.getDataByUsername(Mockito.anyString())).thenReturn(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/customer/getDataByUsername")
				.accept(MediaType.APPLICATION_JSON).content(requestGetDataByUsername)
				.contentType(MediaType.APPLICATION_JSON);	
		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("102"));	
    }  
    @Test
    public void addProfileCaseSuccess()throws Exception{  
		Profile mockProfile = new Profile();
		mockProfile.setName("Test");
		mockProfile.setEmail("test@example.com");
		mockProfile.setBirthday(new Date());
		mockProfile.setPrimaryPhone("xxx-xxxxxxx");
		Mockito.when(profileService.saveData(Mockito.any(RequestProfile.class))).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/customer/addProfile")
				.accept(MediaType.APPLICATION_JSON).content(requestAddProfile)
				.contentType(MediaType.APPLICATION_JSON);		
		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("000"));
    } 
    @Test
    public void addProfileCaseFailed()throws Exception{  
		Profile mockProfile = new Profile();
		mockProfile.setName("Test");
		mockProfile.setEmail("test@example.com");
		mockProfile.setBirthday(new Date());
		mockProfile.setPrimaryPhone("xxx-xxxxxxx");
		Mockito.when(profileService.saveData(Mockito.any(RequestProfile.class))).thenReturn(false);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/customer/addProfile")
				.accept(MediaType.APPLICATION_JSON).content(requestAddProfile)
				.contentType(MediaType.APPLICATION_JSON);		
		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("103"));
    } 
	@Test  
    public void getAddressByUsernameCaseSuccess()throws Exception{  
    	List<Address> mockListAddress = new ArrayList<>();
    	Address mockAdddress = new Address();
    	mockAdddress.setHouseNo("00/2");
    	mockAdddress.setLane("23");
    	mockAdddress.setPostalCode("10900");
    	mockAdddress.setProvince("Bangkok");
    	mockAdddress.setDistrict("Chatuchak");
    	mockAdddress.setAlley("1");
    	mockAdddress.setRoad("Ladphrao");
		mockAdddress.setSubDistrict("Chom Phon");
		mockAdddress.setVillageNo("-");
    	mockListAddress.add(mockAdddress);
		Mockito.when(addressService.getDataByUsername(Mockito.anyString())).thenReturn(mockListAddress);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/customer/getAddressByUsername")
				.accept(MediaType.APPLICATION_JSON).content(requestGetDataByUsername)
				.contentType(MediaType.APPLICATION_JSON);		
		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("000"));
    } 
	@Test  
    public void getAddressByUsernameCaseFailed()throws Exception{  
		Mockito.when(addressService.getDataByUsername(Mockito.anyString())).thenReturn(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/customer/getAddressByUsername")
				.accept(MediaType.APPLICATION_JSON).content(requestGetDataByUsername)
				.contentType(MediaType.APPLICATION_JSON);		
		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("102"));
    } 
    @Test
    public void addAddressCaseSuccess()throws Exception{  
		Profile mockProfile = new Profile();
		mockProfile.setName("Test");
		mockProfile.setEmail("test@example.com");
		mockProfile.setBirthday(new Date());
		mockProfile.setPrimaryPhone("xxx-xxxxxxx");
		Mockito.when(profileService.getDataByUsername(Mockito.anyString())).thenReturn(mockProfile);
		Mockito.when(addressService.saveData(Mockito.any(Address.class))).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/customer/addAddress")
				.accept(MediaType.APPLICATION_JSON).content(requestAddAddress)
				.contentType(MediaType.APPLICATION_JSON);		
		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("000"));
    } 
    @Test
    public void addAddressCaseFailed()throws Exception{  
		Profile mockProfile = new Profile();
		mockProfile.setName("Test");
		mockProfile.setEmail("test@example.com");
		mockProfile.setBirthday(new Date());
		mockProfile.setPrimaryPhone("xxx-xxxxxxx");
		Mockito.when(profileService.getDataByUsername(Mockito.anyString())).thenReturn(mockProfile);
		Mockito.when(addressService.saveData(Mockito.any(Address.class))).thenReturn(false);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/customer/addAddress")
				.accept(MediaType.APPLICATION_JSON).content(requestAddAddress)
				.contentType(MediaType.APPLICATION_JSON);		
		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("103"));
    }
    @Test
    public void addAddressCaseNoFoundUsername()throws Exception{  
		Profile mockProfile = new Profile();
		mockProfile.setName("Test");
		mockProfile.setEmail("test@example.com");
		mockProfile.setBirthday(new Date());
		mockProfile.setPrimaryPhone("xxx-xxxxxxx");
		Mockito.when(profileService.getDataByUsername(Mockito.anyString())).thenReturn(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/customer/addAddress")
				.accept(MediaType.APPLICATION_JSON).content(requestAddAddress)
				.contentType(MediaType.APPLICATION_JSON);		
		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("102"));
    }
}
