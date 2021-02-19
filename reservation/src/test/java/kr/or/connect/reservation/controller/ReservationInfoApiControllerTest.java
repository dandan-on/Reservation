package kr.or.connect.reservation.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import kr.or.connect.reservation.config.ApplicationConfig;
import kr.or.connect.reservation.config.MvcConfig;
import kr.or.connect.reservation.dto.ReservationInfoPrice;
import kr.or.connect.reservation.dto.ReservationInfoRequest;
import kr.or.connect.reservation.dto.ReservationInfosResult;
import kr.or.connect.reservation.service.ReservationInfoService;
import kr.or.connect.reservation.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes= {MvcConfig.class, ApplicationConfig.class})
public class ReservationInfoApiControllerTest {
	@InjectMocks //목객체를 사용
	public ReservationInfoApiController reservationInfoApiController;
	
	@Mock //목객체 생성 (가짜 개체)
	ReservationInfoService reservationInfoService;
	
	@Mock
	UserService userService;
	
	private MockMvc mockMvc;
	
	@Before //테스트 메소드 실행 전에 실행됨
	public void createController() {
		MockitoAnnotations.initMocks(this); //@Mock이 붙은 필드를 목객체로 초기화
		mockMvc = MockMvcBuilders.standaloneSetup(reservationInfoApiController).build(); //MockMvc 타입의 변수 초기화. 테스트를 위한 객체 생성
	}
	
	@Test
	@WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
	public void inputReservationInfos() throws Exception {
		
		Map<String,Object> reservationInfo = new HashMap<>();
		reservationInfo.put("id",1L);
		
		Map<String,Object> price = new HashMap<>();
		price.put("count", 2);
		price.put("productPriceId", 1L);
		List<Map<String,Object>> priceList = Arrays.asList(price);
		
		ReservationInfoRequest request = new ReservationInfoRequest(priceList, 1L, 1L, "2020.11.11", 1L);
		Long productId = request.getProductId();
		Long displayInfoId = request.getDisplayInfoId();
		String reservationDate = request.getReservationYearMonthDay();
		Long userId = request.getUserId();
		
		ReservationInfoPrice reservationInfoPrice = new ReservationInfoPrice();
		reservationInfoPrice.setId(2L);
		List<ReservationInfoPrice> reservationInfoPriceList = Arrays.asList(reservationInfoPrice);
		Principal principal = SecurityContextHolder.getContext().getAuthentication();
		
		when(reservationInfoService.insertReservationInfo(productId, displayInfoId, userId, reservationDate)).thenReturn(reservationInfo);
		when(reservationInfoService.insertReservationInfoPrice(priceList, (long) reservationInfo.get("id"))).thenReturn(1);
		when(reservationInfoService.getReservationInfoPriceList((long) reservationInfo.get("id"))).thenReturn(reservationInfoPriceList);

		RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/api/reservationInfos").
				contentType(MediaType.APPLICATION_JSON).principal(principal);
		mockMvc.perform(reqBuilder).andExpect(status().isOk()).andDo(print()); 
		
		Map<String,Object> reservationInfoResult = reservationInfoService.insertReservationInfo(productId, displayInfoId, userId, reservationDate);
		Long reservationInfoId = (long) reservationInfoResult.get("id");
		int countOfInsert = reservationInfoService.insertReservationInfoPrice(priceList, reservationInfoId);
		List<ReservationInfoPrice> reservationInfoPriceListResult = 
				reservationInfoService.getReservationInfoPriceList(reservationInfoId);
		
		verify(reservationInfoService).insertReservationInfo(productId, displayInfoId, userId, reservationDate);
		verify(reservationInfoService).insertReservationInfoPrice(priceList, reservationInfoId);
		verify(reservationInfoService).getReservationInfoPriceList(reservationInfoId);
		assertThat(reservationInfoResult.get("id"), is(1L));
		assertThat(countOfInsert, is(1));
		assertThat(reservationInfoPriceListResult.get(0).getId(),is(2L));

	}
	
	@Test
	@WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
	public void getReservationInfos() throws Exception {

		ReservationInfosResult reservationInfo = new ReservationInfosResult();
		reservationInfo.setId(1L);
		Long userId = 2L;
		
		List<ReservationInfosResult> reservationInfoList = Arrays.asList(reservationInfo);
		Principal principal = SecurityContextHolder.getContext().getAuthentication();
		
		when(userService.getUserId(principal.getName())).thenReturn(userId);
		when(reservationInfoService.getReservationInfoList(userId)).thenReturn(reservationInfoList);

		RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/api/reservationInfos").
				contentType(MediaType.APPLICATION_JSON).principal(principal);
		mockMvc.perform(reqBuilder).andExpect(status().isOk()).andDo(print()); 
		
		verify(reservationInfoService).getReservationInfoList(userId);
	}
	
	@Test
	@WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
	public void cancelReservationInfos() throws Exception {
		Principal principal = SecurityContextHolder.getContext().getAuthentication();
		Long id = 1L;
		
		when(reservationInfoService.cancelReservationInfo(id)).thenReturn("success");

		RequestBuilder reqBuilder = MockMvcRequestBuilders.put("/api/reservationInfos?id="+id).
				contentType(MediaType.APPLICATION_JSON).principal(principal);
		mockMvc.perform(reqBuilder).andExpect(status().isOk()).andDo(print()); 
		
		verify(reservationInfoService).cancelReservationInfo(id);
	}
}
