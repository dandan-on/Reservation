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
import kr.or.connect.reservation.dto.PromotionsResult;
import kr.or.connect.reservation.service.PromotionService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes= {MvcConfig.class, ApplicationConfig.class})	
public class PromotionApiControllerTest {
	@InjectMocks //목객체를 사용
	public PromotionApiController promotionApiController;
	
	@Mock //목객체 생성 (가짜 개체)
	PromotionService promotionService;
	
	private MockMvc mockMvc;
	
	@Before //테스트 메소드 실행 전에 실행됨
	public void createController() {
		MockitoAnnotations.initMocks(this); //@Mock이 붙은 필드를 목객체로 초기화
		mockMvc = MockMvcBuilders.standaloneSetup(promotionApiController).build(); //MockMvc 타입의 변수 초기화. 테스트를 위한 객체 생성
	}
	
	@Test
	public void promotions() throws Exception {
	
		PromotionsResult promotion = new PromotionsResult();
		promotion.setId((long) 1);
		promotion.setCategoryId((long) 3);
		
		List<PromotionsResult> promotionList = Arrays.asList(promotion);
		when(promotionService.getPromotionList()).thenReturn(promotionList);

		RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/api/promotions").contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(reqBuilder).andExpect(status().isOk()).andDo(print()); 
		
		verify(promotionService).getPromotionList();
	}
}
