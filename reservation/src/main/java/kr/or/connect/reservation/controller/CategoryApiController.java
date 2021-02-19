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
import kr.or.connect.reservation.dto.CategoriesResult;
import kr.or.connect.reservation.service.CategoryService;

@RestController //응답결과를 JSON으로
@RequestMapping(value="/api")
public class CategoryApiController {
	@Autowired
	private CategoryService categoryService;
	
	@ApiOperation(value="카테고리 목록 구하기")
    @ApiResponses({  // Response Message에 대한 Swagger 설명
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Exception")
    })
	@GetMapping("/categories")
	public  HashMap<String, Object> categories() {
		
		List<CategoriesResult> categoryList = categoryService.getCategoryList();
		int sizeOfCategories = categoryList.size();
		
		HashMap<String, Object> resultMap= new HashMap<>();
		resultMap.put("size", sizeOfCategories);
		resultMap.put("items", categoryList);
		return resultMap;
	}
}
