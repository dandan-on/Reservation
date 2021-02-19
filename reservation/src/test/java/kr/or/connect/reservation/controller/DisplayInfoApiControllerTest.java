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
import kr.or.connect.reservation.dto.DisplayInfoImagesResult;
import kr.or.connect.reservation.dto.DisplayInfosResult;
import kr.or.connect.reservation.dto.ProductImagesResult;
import kr.or.connect.reservation.dto.ProductPricesResult;
import kr.or.connect.reservation.service.DisplayInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes= {MvcConfig.class, ApplicationConfig.class})	
public class DisplayInfoApiControllerTest {

	@InjectMocks //목객체를 사용
	public DisplayInfoApiController displayInfoApiController;
	
	@Mock //목객체 생성 (가짜 개체)
	DisplayInfoService displayInfoService;
	
	private MockMvc mockMvc;
	
	@Before //테스트 메소드 실행 전에 실행됨
	public void createController() {
		MockitoAnnotations.initMocks(this); //@Mock이 붙은 필드를 목객체로 초기화
		mockMvc = MockMvcBuilders.standaloneSetup(displayInfoApiController).build(); //MockMvc 타입의 변수 초기화. 테스트를 위한 객체 생성
	}
	
	@Test
	public void displayinfosInCategory() throws Exception {
		Long categoryId = 3L;
		int start = 0;
		
		DisplayInfosResult displayInfo = new DisplayInfosResult();
		displayInfo.setId(1L);
		displayInfo.setCategoryId(categoryId);
		
		List<DisplayInfosResult> displayInfoList = Arrays.asList(displayInfo);
		when(displayInfoService.getDisplayInfoListInCategory(categoryId, start)).thenReturn(displayInfoList);
		when(displayInfoService.getTotalCountOfDisplayInfosInCategory(categoryId)).thenReturn(16);

		RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/api/displayinfos?categoryId="+categoryId+"&start="+start).
				contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(reqBuilder).andExpect(status().isOk()).andDo(print()); 
		
		verify(displayInfoService).getDisplayInfoListInCategory(categoryId, start);
		verify(displayInfoService).getTotalCountOfDisplayInfosInCategory(categoryId);
	}
	
	@Test
	public void displayinfosByDisplayId() throws Exception {
		
		Long displayId = 1L;
		Long productId = 2L;
		Long displayInfoId = 3L;

		DisplayInfosResult product = new DisplayInfosResult();
		
		product.setId(productId);
		product.setDisplayInfoId(displayInfoId);
		
		List<DisplayInfosResult> displayInfo  = Arrays.asList(product);	
		List<ProductImagesResult> productImages = Arrays.asList(new ProductImagesResult());		
		List<DisplayInfoImagesResult> displayInfoImages = Arrays.asList(new DisplayInfoImagesResult());	
		List<ProductPricesResult> productPrices = Arrays.asList(new ProductPricesResult());	
		
		when(displayInfoService.getDisplayInfo(displayId)).thenReturn(displayInfo);
		when(displayInfoService.getProductImages(productId)).thenReturn(productImages);
		when(displayInfoService.getDisplayInfoImages(displayInfoId)).thenReturn(displayInfoImages);
		when(displayInfoService.getAvgScoreOfComments(productId)).thenReturn(3);
		when(displayInfoService.getProductPrices(productId)).thenReturn(productPrices);
				
		RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/api/displayinfos/"+displayId).contentType(MediaType.APPLICATION_JSON); 
		mockMvc.perform(reqBuilder).andExpect(status().isOk()).andDo(print());
		
		verify(displayInfoService).getDisplayInfo(displayId);
		verify(displayInfoService).getProductImages(productId);
		verify(displayInfoService).getDisplayInfoImages(displayInfoId);
		verify(displayInfoService).getAvgScoreOfComments(productId);
		verify(displayInfoService).getProductPrices(productId);
	}
}
