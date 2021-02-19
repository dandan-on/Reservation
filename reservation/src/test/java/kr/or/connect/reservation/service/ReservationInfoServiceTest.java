package kr.or.connect.reservation.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import kr.or.connect.reservation.config.ApplicationConfig;
import kr.or.connect.reservation.config.MvcConfig;
import kr.or.connect.reservation.dao.ReservationInfoDao;
import kr.or.connect.reservation.dto.ReservationInfosResult;
import kr.or.connect.reservation.service.impl.ReservationInfoServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes= {MvcConfig.class, ApplicationConfig.class})	
public class ReservationInfoServiceTest {
    @InjectMocks
    public ReservationInfoServiceImpl reservationInfoServiceImpl;
    
	@Mock //목객체 생성 
	ReservationInfoDao reservationInfoDao;
	
	@Before //테스트 메소드 실행 전에 실행됨
	public void before() {
		MockitoAnnotations.initMocks(this); //@Mock이 붙은 필드를 목객체로 초기화		
	}
	
	@Test
	public void insertReservationInfo() throws Exception {
		
		Map<String,Object> reservationInfo = new HashMap<>();
		reservationInfo.put("id",1L);
		
		when(reservationInfoDao.insertReservationInfo(1L, 1L, 1L, "2020.11.11")).thenReturn(1L);
		when(reservationInfoDao.selectReservationInfo(1L)).thenReturn(reservationInfo);
		
		Map<String,Object> reservationInfoResult = reservationInfoServiceImpl.
				insertReservationInfo(1L, 1L, 1L, "2020.11.11");
		
		verify(reservationInfoDao).insertReservationInfo(1L, 1L, 1L, "2020.11.11");
		verify(reservationInfoDao).selectReservationInfo(1L);
		assertThat(reservationInfoResult.get("id"), is(1L));

	}
	
	@Test
	public void insertReservationInfoPrice() throws Exception {
		
		Map<String,Object> price = new HashMap<>();
		price.put("count", 2);
		price.put("productPriceId", 1L);
		List<Map<String,Object>> priceList = Arrays.asList(price);
		
		when(reservationInfoDao.insertReservationInfoPrice(2, 1L, 1L)).thenReturn(1);
		
		int countOfInsert = reservationInfoServiceImpl.insertReservationInfoPrice(priceList, 1L);
				
		verify(reservationInfoDao).insertReservationInfoPrice(2, 1L, 1L);
		assertThat(countOfInsert, is(1));

	}
	
	@Test
	public void getReservationInfoList() throws Exception {
		
		ReservationInfosResult ReservationInfo = new ReservationInfosResult();
		ReservationInfo.setId(1L);
		
		List<ReservationInfosResult> ReservationInfoList = Arrays.asList(ReservationInfo);
		when(reservationInfoDao.selectReservationInfoList(2L)).thenReturn(ReservationInfoList);
		
		List<ReservationInfosResult> ReservationInfoListResult = reservationInfoServiceImpl.getReservationInfoList(2L);
		
		verify(reservationInfoDao).selectReservationInfoList(2L);
		assertThat(ReservationInfoListResult.get(0).getId(), is(1L));

	}
}
