package kr.or.connect.reservation.dto;

import java.util.List;
import java.util.Map;

public class ReservationInfoRequest {
	private List<Map<String,Object>> prices;
	private Long productId;
	private Long displayInfoId;
	private String reservationYearMonthDay;
	private Long userId;
	
	public ReservationInfoRequest() {
	}

	public ReservationInfoRequest(List<Map<String, Object>> prices, Long productId, Long displayInfoId,
			String reservationYearMonthDay, Long userId) {
		this.prices = prices;
		this.productId = productId;
		this.displayInfoId = displayInfoId;
		this.reservationYearMonthDay = reservationYearMonthDay;
		this.userId = userId;
	}

	public List<Map<String, Object>> getPrices() {
		return prices; //{"count": 1, "productPriceId": 2 }, {"count": 4,"productPriceId": 3}
	}

	public void setPrices(List<Map<String, Object>> prices) {
		this.prices = prices;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getDisplayInfoId() {
		return displayInfoId;
	}

	public void setDisplayInfoId(Long displayInfoId) {
		this.displayInfoId = displayInfoId;
	}

	public String getReservationYearMonthDay() {
		return reservationYearMonthDay;
	}

	public void setReservationYearMonthDay(String reservationYearMonthDay) {
		this.reservationYearMonthDay = reservationYearMonthDay;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}
