package kr.or.connect.reservation.service;

import java.util.List;

import kr.or.connect.reservation.dto.DisplayInfoImagesResult;
import kr.or.connect.reservation.dto.DisplayInfosResult;
import kr.or.connect.reservation.dto.ProductImagesResult;
import kr.or.connect.reservation.dto.ProductPricesResult;

public interface DisplayInfoService {
	//전시 상품 목록 구하기
	public List<DisplayInfosResult> getDisplayInfoListInCategory(Long categorId, int start);
	public int getTotalCountOfDisplayInfosInCategory(Long categoryId);
	
	//전시 정보 구하기
	public List<DisplayInfosResult> getDisplayInfo(Long displayId);
	public List<ProductImagesResult> getProductImages(Long productId);
	public List<DisplayInfoImagesResult> getDisplayInfoImages(Long displayInfoId);
	public int getAvgScoreOfComments(Long productId);
	public List<ProductPricesResult> getProductPrices(Long productId);
}
