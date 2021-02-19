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
import kr.or.connect.reservation.dao.DisplayInfoDao;
import kr.or.connect.reservation.dto.DisplayInfoImagesResult;
import kr.or.connect.reservation.dto.DisplayInfosResult;
import kr.or.connect.reservation.dto.ProductImagesResult;
import kr.or.connect.reservation.dto.ProductPricesResult;
import kr.or.connect.reservation.service.impl.DisplayInfoServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes= {MvcConfig.class, ApplicationConfig.class})	
public class DisplayInfoServiceTest {

    @InjectMocks
    public DisplayInfoServiceImpl displayInfoServiceImpl;
    
	@Mock //목객체 생성 
	DisplayInfoDao displayInfoDao;
	
	@Before //테스트 메소드 실행 전에 실행됨
	public void before() {
		MockitoAnnotations.initMocks(this); //@Mock이 붙은 필드를 목객체로 초기화		
	}
	
	@Test
	public void selectDisplayInfosInCategory() throws Exception {
		Long categoryId = 3L; 
		int start = 0;
		DisplayInfosResult displayInfo= new DisplayInfosResult();
		
		if (categoryId != 0L) {
			displayInfo.setId(1L);
			List<DisplayInfosResult> displayInfoList = Arrays.asList(displayInfo);
			when(displayInfoDao.selectDisplayInfosInCategory(categoryId, start)).thenReturn(displayInfoList);
			
			List<DisplayInfosResult> displayInfoListResult = displayInfoServiceImpl.getDisplayInfoListInCategory(categoryId, start);
			verify(displayInfoDao).selectDisplayInfosInCategory(categoryId, start);
			assertThat(displayInfoListResult.get(0).getId(), is(1L));
			
		} else {
			displayInfo.setId(11L);
			List<DisplayInfosResult> displayInfoList = Arrays.asList(displayInfo);
			when(displayInfoDao.selectDisplayInfosInAllCategories(start)).thenReturn(displayInfoList);
			
			List<DisplayInfosResult> displayInfoListResult = displayInfoServiceImpl.getDisplayInfoListInCategory(categoryId, start);
			verify(displayInfoDao).selectDisplayInfosInAllCategories(start);
			assertThat(displayInfoListResult.get(0).getId(), is(11L));
		}		

	}
	
	@Test
	public void getTotalCountOfDisplayInfosInCategory() throws Exception {
		

		when(displayInfoDao.selectCountOfDisplayInfosInCategory(3L)).thenReturn(10);
		when(displayInfoDao.selectCountOfAllDisplayInfos()).thenReturn(20);
		
		//categoryId != 0 라면, 
//		int totalCount = mainPageServiceImpl.getTotalCountOfDisplayInfosInCategory(3L);
//		verify(mainPageDao).selectCountOfDisplayInfosInCategory(3L);
//		assertThat(totalCount, is(10));
		
		int totalCount = displayInfoServiceImpl.getTotalCountOfDisplayInfosInCategory(0L);
		verify(displayInfoDao).selectCountOfAllDisplayInfos();
		assertThat(totalCount, is(20));
		
	}
	
	@Test
	public void getDisplayInfo() throws Exception {
		
		DisplayInfosResult displayInfo = new DisplayInfosResult();
		displayInfo.setId(1L);
		
		List<DisplayInfosResult> displayInfoList = Arrays.asList(displayInfo);
		when(displayInfoDao.selectDisplayInfo(2L)).thenReturn(displayInfoList);
		
		List<DisplayInfosResult> displayInfoResult = displayInfoServiceImpl.getDisplayInfo(2L);
		
		verify(displayInfoDao).selectDisplayInfo(2L);
		assertThat(displayInfoResult.get(0).getId(), is(1L));

	}
	
	@Test
	public void getProductImages() throws Exception {
		
		ProductImagesResult productImages = new ProductImagesResult();
		productImages.setProductId(1L);
		
		List<ProductImagesResult> ProductImagesList = Arrays.asList(productImages);
		when(displayInfoDao.selectProductImages(2L)).thenReturn(ProductImagesList);
		
		List<ProductImagesResult> productImagesResult = displayInfoServiceImpl.getProductImages(2L);
		
		verify(displayInfoDao).selectProductImages(2L);
		assertThat(productImagesResult.get(0).getProductId(), is(1L));

	}
	
	@Test
	public void getDisplayInfoImages() throws Exception {
		
		DisplayInfoImagesResult displayInfoImages = new DisplayInfoImagesResult();
		displayInfoImages.setId(1L);
		
		List<DisplayInfoImagesResult> displayInfoImagesList = Arrays.asList(displayInfoImages);
		when(displayInfoDao.selectDisplayInfoImages(2L)).thenReturn(displayInfoImagesList);
		
		List<DisplayInfoImagesResult> displayInfoResult = displayInfoServiceImpl.getDisplayInfoImages(2L);
		
		verify(displayInfoDao).selectDisplayInfoImages(2L);
		assertThat(displayInfoResult.get(0).getId(), is(1L));

	}
	
	@Test
	public void getAvgScoreOfComments() throws Exception {
		
		when(displayInfoDao.selectAvgScoreOfComments(2L)).thenReturn(4);
		
		int avgScore = displayInfoServiceImpl.getAvgScoreOfComments(2L);
		
		verify(displayInfoDao).selectAvgScoreOfComments(2L);
		assertThat(avgScore, is(4));

	}
	
	@Test
	public void getProductPrices() throws Exception {
		
		ProductPricesResult productPrices = new ProductPricesResult();
		productPrices.setId(1L);
		
		List<ProductPricesResult> productPricesList = Arrays.asList(productPrices);
		when(displayInfoDao.selectProductPrices(2L)).thenReturn(productPricesList);
		
		List<ProductPricesResult> productPricesResult = displayInfoServiceImpl.getProductPrices(2L);
		
		verify(displayInfoDao).selectProductPrices(2L);
		assertThat(productPricesResult.get(0).getId(), is(1L));

	}
	
}
