package kr.or.connect.reservation.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import kr.or.connect.reservation.config.ApplicationConfig;
import kr.or.connect.reservation.config.MvcConfig;
import kr.or.connect.reservation.service.FileService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes= {MvcConfig.class, ApplicationConfig.class})	
public class FileApiControllerTest {
	@InjectMocks //목객체를 사용
	public FileApiController fileApiController;
	
	@Mock //목객체 생성 
	FileService fileService;
	
	private MockMvc mockMvc;
	
	@Before //테스트 메소드 실행 전에 실행됨
	public void createController() {
		MockitoAnnotations.initMocks(this); //@Mock이 붙은 필드를 목객체로 초기화
		mockMvc = MockMvcBuilders.standaloneSetup(fileApiController).build(); //MockMvc 타입의 변수 초기화. 테스트를 위한 객체 생성
	}
	
	
	@Test
	public void downloadImage() throws Exception {
		MultipartFile file = new MockMultipartFile("testImage.jpg", new FileInputStream(new File("c:/tmp/heart.jpg")));
		
		Long fileId = 2L;
		Map<String, Object> fileInfo = new HashMap<>();
		fileInfo.put("fileName", file.getOriginalFilename());
		fileInfo.put("saveFileName", "heart.jpg");
		fileInfo.put("contentType","image/jpg");

		when(fileService.getFileInfo(fileId)).thenReturn(fileInfo);
		
		RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/api/files/"+fileId).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(reqBuilder).andExpect(status().isOk()).andDo(print()); 
				
		verify(fileService).getFileInfo(fileId);
	}
}
