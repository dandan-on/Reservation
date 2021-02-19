package kr.or.connect.reservation.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.or.connect.reservation.dto.ReservationInfoPrice;
import kr.or.connect.reservation.dto.ReservationInfoRequest;
import kr.or.connect.reservation.dto.ReservationInfosResult;
import kr.or.connect.reservation.service.ReservationInfoService;
import kr.or.connect.reservation.service.UserService;

@RestController
@RequestMapping(value="/api")
public class ReservationInfoApiController {
	@Autowired
	private ReservationInfoService reservationInfoService;
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value="예약 등록하기")
    @ApiResponses({ 
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Exception")
    })
	@PostMapping("/reservationInfos")
	public  HashMap<String, Object> inputReservationInfos(Principal principal,
			@RequestBody(required=true) ReservationInfoRequest request) throws AuthenticationException {

		HashMap<String, Object> resultMap= new HashMap<>();
		if (principal == null) {
			throw new AuthenticationCredentialsNotFoundException("로그인한 상태가 아닙니다.");
		} else {
			
			Map<String,Object> reservationInfo = reservationInfoService
					.insertReservationInfo(request.getProductId(), request.getDisplayInfoId(), 
							request.getUserId(), request.getReservationYearMonthDay());
			
			Long reservationInfoId = Long.valueOf(reservationInfo.get("id").toString());
			int countOfInsert = reservationInfoService.insertReservationInfoPrice(request.getPrices(), reservationInfoId);
			
			List<ReservationInfoPrice> priceList = reservationInfoService.getReservationInfoPriceList(reservationInfoId);
			
			resultMap.putAll(reservationInfo);
			resultMap.put("prices", priceList);
		}
		return resultMap;
	}
	
	@ApiOperation(value="예약 정보 구하기")
    @ApiResponses({ 
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Exception")
    })
	@GetMapping("/reservationInfos")
	public  HashMap<String, Object> getReservationInfos(Principal principal) {
		
		HashMap<String, Object> resultMap= new HashMap<>();
		if (principal == null) {
			throw new AuthenticationCredentialsNotFoundException("로그인한 상태가 아닙니다.");
		} else {
			String email = principal.getName();
			Long userId = userService.getUserId(email);
			
			List<ReservationInfosResult> reservationInfoList = reservationInfoService.getReservationInfoList(userId);
			int size = reservationInfoList.size();
			resultMap.put("size", size);
			resultMap.put("items", reservationInfoList);
		}
		return resultMap;
	}
	
	
	@ApiOperation(value="예약 취소하기")
    @ApiResponses({ 
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Exception")
    })
	@PutMapping("/reservationInfos")
	public  HashMap<String, Object> cancelReservationInfos(Principal principal,
			@RequestParam(name="id", required=true) Long id) {
		
		HashMap<String, Object> resultMap= new HashMap<>();
		if (principal == null) {
			throw new AuthenticationCredentialsNotFoundException("로그인한 상태가 아닙니다.");
		} else {
			String result = reservationInfoService.cancelReservationInfo(id);
			resultMap.put("result",result);
		}
		return resultMap;
	}
}
