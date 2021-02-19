package kr.or.connect.reservation.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

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
import kr.or.connect.reservation.dao.PromotionDao;
import kr.or.connect.reservation.dto.PromotionsResult;
import kr.or.connect.reservation.service.impl.PromotionServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes= {MvcConfig.class, ApplicationConfig.class})	
public class PromotionServiceTest {

    @InjectMocks
    public PromotionServiceImpl promotionServiceImpl;
    
	@Mock //목객체 생성 
	PromotionDao promotionDao;
	
	@Before //테스트 메소드 실행 전에 실행됨
	public void before() {
		MockitoAnnotations.initMocks(this); //@Mock이 붙은 필드를 목객체로 초기화		
	}
	
	@Test
	public void getPromotionList() throws Exception {
		
		PromotionsResult promotion = new PromotionsResult();
		promotion.setId(1L);
		
		List<PromotionsResult> promotionList = Arrays.asList(promotion);
		when(promotionDao.selectPromotions()).thenReturn(promotionList);
		
		List<PromotionsResult> promotionListResult = promotionServiceImpl.getPromotionList();
		
		verify(promotionDao).selectPromotions();
		assertThat(promotionListResult.get(0).getId(), is(1L));

	}
}
