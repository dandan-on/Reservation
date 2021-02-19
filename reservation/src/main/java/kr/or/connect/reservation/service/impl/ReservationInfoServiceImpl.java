package kr.or.connect.reservation.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.reservation.dao.ReservationInfoDao;
import kr.or.connect.reservation.dto.ReservationInfoPrice;
import kr.or.connect.reservation.dto.ReservationInfosResult;
import kr.or.connect.reservation.service.ReservationInfoService;

@Service
public class ReservationInfoServiceImpl implements ReservationInfoService {
	@Autowired
	private ReservationInfoDao reservationInfoDao;
	
	@Override
	@Transactional(readOnly=false)
	public Map<String,Object> insertReservationInfo(Long productId, Long displayInfoId, Long userId, String reservationDate) {
		Long id = reservationInfoDao.insertReservationInfo(productId, displayInfoId, userId, reservationDate);
		return reservationInfoDao.selectReservationInfo(id);
	}

	@Override
	@Transactional(readOnly=false)
	public int insertReservationInfoPrice(List<Map<String,Object>> prices, Long reservationInfoId) {
		//insert 하는 로직과 reservation_id로 모든 prices 정보를 가져오는 로직을 분리해야함
		int countOfinsert = 0;
		for (Map<String,Object> price : prices) {
			int count = (int) price.get("count");
			Long productPriceId = Long.valueOf(price.get("productPriceId").toString());
			countOfinsert += reservationInfoDao.insertReservationInfoPrice(count, productPriceId, reservationInfoId);	
		}
		return countOfinsert;
	}

	@Override
	public List<ReservationInfoPrice> getReservationInfoPriceList(Long reservationInfoId) {
		return reservationInfoDao.selectReservationInfoPrices(reservationInfoId);
	}

	@Override
	public List<ReservationInfosResult> getReservationInfoList(Long userId) {
		List<ReservationInfosResult> reservationInfoList = reservationInfoDao.selectReservationInfoList(userId);
		for (ReservationInfosResult reservationInfo : reservationInfoList) {
			int sumPrice = reservationInfoDao.selectSumPrice(reservationInfo.getId());
			reservationInfo.setSumPrice(sumPrice);
		}
		return reservationInfoList;
	}

	@Override
	public String cancelReservationInfo(Long id) {
		int resultOfUpdate = reservationInfoDao.updateCancelFlagOfReservationInfo(id);
		String result = "";
		if (resultOfUpdate != 0) {
			result = "success";
		} else {
			result = "fail";
		}
		return result;
	}

}
