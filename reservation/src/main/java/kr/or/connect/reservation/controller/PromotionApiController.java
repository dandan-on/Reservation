package kr.or.connect.reservation.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.or.connect.reservation.dto.PromotionsResult;
import kr.or.connect.reservation.service.PromotionService;

@RestController 
@RequestMapping(value="/api")
public class PromotionApiController {
	@Autowired
	private PromotionService promotionService;
	
	@ApiOperation(value="프로모션 정보 구하기")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Exception")
    })
	@GetMapping("/promotions")
	public  HashMap<String, Object> promotions() {
		
		List<PromotionsResult> promotionList = promotionService.getPromotionList();
		int size = promotionList.size();
		
		HashMap<String,Object> resultMap= new HashMap<>();
		resultMap.put("size", size);
		resultMap.put("items", promotionList);		
		return resultMap;
	}
}
