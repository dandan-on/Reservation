package kr.or.connect.reservation.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.or.connect.reservation.dto.DisplayInfoImagesResult;
import kr.or.connect.reservation.dto.DisplayInfosResult;
import kr.or.connect.reservation.dto.ProductImagesResult;
import kr.or.connect.reservation.dto.ProductPricesResult;
import kr.or.connect.reservation.service.DisplayInfoService;

@RestController
@RequestMapping(value="/api")
public class DisplayInfoApiController {

	@Autowired
	private DisplayInfoService displayInfoService;

	@ApiOperation(value="전시 상품 목록 구하기")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Exception")
    })
	@GetMapping("/displayinfos")
	public  HashMap<String, Object> displayinfosInCategory(@RequestParam(name="categoryId", required=false, defaultValue="0") Long categoryId, 
			@RequestParam(name="start", required=false, defaultValue="0") int start) {
		
		List<DisplayInfosResult> displayInfoList = displayInfoService.getDisplayInfoListInCategory(categoryId, start);
		int totalCount = displayInfoService.getTotalCountOfDisplayInfosInCategory(categoryId);
		int productCount = displayInfoList.size();
			
		HashMap<String,Object> resultMap= new HashMap<>();
		resultMap.put("totalCount", totalCount);
		resultMap.put("productCount", productCount);		
		resultMap.put("products", displayInfoList);
		return resultMap;
	}	
	
	@ApiOperation(value="전시 정보 구하기")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Exception")
    })
	@GetMapping("/displayinfos/{displayId}")
	public  HashMap<String, Object> displayinfosByDisplayId(@PathVariable("displayId") Long displayId) {
		
		HashMap<String,Object> resultMap= new HashMap<>();
		
		try {
			List<DisplayInfosResult> product = displayInfoService.getDisplayInfo(displayId);
			
			Long productId = product.get(0).getId();
			Long displayInfoId = product.get(0).getDisplayInfoId();
			
			List<ProductImagesResult> productImages = displayInfoService.getProductImages(productId);
			List<DisplayInfoImagesResult> displayInfoImages = displayInfoService.getDisplayInfoImages(displayInfoId);
			int avgScore = displayInfoService.getAvgScoreOfComments(productId);
			List<ProductPricesResult> productPrices =  displayInfoService.getProductPrices(productId);
			
			resultMap.put("product", product);
			resultMap.put("productImages", productImages);		
			resultMap.put("displayInfoImages", displayInfoImages);
			resultMap.put("avgScore", avgScore);
			resultMap.put("productPrices", productPrices);
			
		} catch(IndexOutOfBoundsException e) {
			System.out.println("IndexOutOfBoundsException 발생 "+e.getMessage());
		}
		
		return resultMap;
	}
	
}
