package kr.or.connect.reservation.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import kr.or.connect.reservation.config.ApplicationConfig;
import kr.or.connect.reservation.config.MvcConfig;
import kr.or.connect.reservation.dto.CategoriesResult;
import kr.or.connect.reservation.service.CategoryService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes= {MvcConfig.class, ApplicationConfig.class})	
public class CategoryApiControllerTest {
	
	@InjectMocks //목객체를 사용
	public CategoryApiController categoryApiController;
	
	@Mock //목객체 생성 (가짜 개체)
	CategoryService categoryService;
	
	private MockMvc mockMvc;
	
	@Before //테스트 메소드 실행 전에 실행됨
	public void createController() {
		MockitoAnnotations.initMocks(this); //@Mock이 붙은 필드를 목객체로 초기화
		mockMvc = MockMvcBuilders.standaloneSetup(categoryApiController).build(); //MockMvc타입의 변수 초기화. 테스트를 위한 객체 생성
	}
	
	@Test
	public void categories() throws Exception {

		CategoriesResult category = new CategoriesResult();
		category.setId((long) 1);
		category.setName("콘서트");
		category.setCount((long) 10);
		
		List<CategoriesResult> categoryList = Arrays.asList(category);
		when(categoryService.getCategoryList()).thenReturn(categoryList); //메소드 호출과 그때 리턴할 값
		
		RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/api/categories").contentType(MediaType.APPLICATION_JSON); //호출할 방식과 URL, 형식
		mockMvc.perform(reqBuilder).andExpect(status().isOk()).andDo(print()); //해당 URL에 요청. 상태코드값이 200이 나와야 하고, 처리 내용 출력
		
		verify(categoryService).getCategoryList(); //호출되었는지 확인
	}
}
