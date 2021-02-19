package kr.or.connect.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.reservation.dao.DisplayInfoDao;
import kr.or.connect.reservation.dto.DisplayInfoImagesResult;
import kr.or.connect.reservation.dto.DisplayInfosResult;
import kr.or.connect.reservation.dto.ProductImagesResult;
import kr.or.connect.reservation.dto.ProductPricesResult;
import kr.or.connect.reservation.service.DisplayInfoService;

@Service
public class DisplayInfoServiceImpl implements DisplayInfoService {

	@Autowired
	private DisplayInfoDao displayInfoDao;
	
	//전시 상품 목록 구하기
	@Override
	public List<DisplayInfosResult> getDisplayInfoListInCategory(Long categorId, int start) {
		List<DisplayInfosResult> displayInfoList = null;
		if (categorId !=0L) {
			displayInfoList = displayInfoDao.selectDisplayInfosInCategory(categorId, start);
		} else {
			displayInfoList = displayInfoDao.selectDisplayInfosInAllCategories(start);
		}
		return displayInfoList;
	}
	
	@Override
	public int getTotalCountOfDisplayInfosInCategory(Long categoryId) {
		int totalCount = 0;
		if(categoryId != 0L) { 
			totalCount = displayInfoDao.selectCountOfDisplayInfosInCategory(categoryId); //특정 카테고리 내의 전시 상품 수 조희
		} else {
			totalCount = displayInfoDao.selectCountOfAllDisplayInfos(); //전체 상품 수 조회
		}
		return totalCount;
	}

	//전시 정보 구하기
	@Override
	public List<DisplayInfosResult> getDisplayInfo(Long displayId) {
		return displayInfoDao.selectDisplayInfo(displayId);
	}

	@Override
	public List<ProductImagesResult> getProductImages(Long productId) {
		return displayInfoDao.selectProductImages(productId);
	}

	@Override
	public List<DisplayInfoImagesResult> getDisplayInfoImages(Long displayInfoId) {
		return displayInfoDao.selectDisplayInfoImages(displayInfoId);
	}

	@Override
	public int getAvgScoreOfComments(Long productId) {
		return displayInfoDao.selectAvgScoreOfComments(productId);
	}

	@Override
	public List<ProductPricesResult> getProductPrices(Long productId) {
		return displayInfoDao.selectProductPrices(productId);
	}
}
