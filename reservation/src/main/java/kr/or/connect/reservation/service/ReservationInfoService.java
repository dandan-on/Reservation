package kr.or.connect.reservation.service;

import java.util.List;
import java.util.Map;

import kr.or.connect.reservation.dto.ReservationInfoPrice;
import kr.or.connect.reservation.dto.ReservationInfosResult;

public interface ReservationInfoService {
	//예약등록하기
	Map<String,Object> insertReservationInfo(Long productId, Long displayInfoId, Long userId, String reservationDate);
	int insertReservationInfoPrice(List<Map<String,Object>> prices, Long reservationInfoId);
	List<ReservationInfoPrice> getReservationInfoPriceList(Long reservationInfoId);
	//예약 정보 구하기
	List<ReservationInfosResult> getReservationInfoList(Long userId);
	//예약 취소하기
	String cancelReservationInfo(Long id);
}
