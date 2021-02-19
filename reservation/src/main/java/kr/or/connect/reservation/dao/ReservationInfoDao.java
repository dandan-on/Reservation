package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.ReservationInfoDaoSqls.INSERT_RESERVATION_INFO_PRICE;
import static kr.or.connect.reservation.dao.ReservationInfoDaoSqls.SELECT_RESERVATION_INFO_BY_ID;
import static kr.or.connect.reservation.dao.ReservationInfoDaoSqls.SELECT_RESERVATION_INFO_BY_USER_ID;
import static kr.or.connect.reservation.dao.ReservationInfoDaoSqls.SELECT_RESERVATION_INFO_PRICE_BY_RESERVATION_INFO_ID;
import static kr.or.connect.reservation.dao.ReservationInfoDaoSqls.SELECT_SUM_PRICE_BY_RESERVATION_INFO_ID;
import static kr.or.connect.reservation.dao.ReservationInfoDaoSqls.UPDATE_CANCEL_FLAGE_OF_RESERVATION_INFO_BY_ID;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.ReservationInfoPrice;
import kr.or.connect.reservation.dto.ReservationInfosResult;

@Repository
public class ReservationInfoDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertActionOfReservationInfo;
	private RowMapper<ReservationInfoPrice> rowMapperOfReservationInfoPrices = 
			BeanPropertyRowMapper.newInstance(ReservationInfoPrice.class);
	private RowMapper<ReservationInfosResult> rowMapperOfReservationInfoList = 
			BeanPropertyRowMapper.newInstance(ReservationInfosResult.class);
	
    public ReservationInfoDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertActionOfReservationInfo = new SimpleJdbcInsert(dataSource)
        		.withTableName("reservation_info")
        		.usingGeneratedKeyColumns("id");
    }
    
    public Long insertReservationInfo(Long productId, Long displayInfoId, Long userId, String reservationDate) {
    	HashMap<String,Object> params = new HashMap<>();
    	params.put("product_id", productId);
    	params.put("display_info_id", displayInfoId);
    	params.put("user_id", userId);
    	params.put("reservation_date", reservationDate);
    	params.put("cancel_flag", 0);
    	params.put("create_date", new java.sql.Date(new Date().getTime()));
    	params.put("modify_date", new java.sql.Date(new Date().getTime()));
	    return insertActionOfReservationInfo.executeAndReturnKey(params).longValue();
	}
    
    public Map<String,Object> selectReservationInfo(Long id) {
    	Map<String, Object> params = Collections.singletonMap("id", id);
    	return jdbc.queryForMap(SELECT_RESERVATION_INFO_BY_ID, params);
    }
    
    public int insertReservationInfoPrice(int count, Long productPriceId, Long reservationInfoId) {
    	HashMap<String,Object> params = new HashMap<>();
    	params.put("reservationInfoId", reservationInfoId);
    	params.put("productPriceId", productPriceId);
    	params.put("count", count);
    	return jdbc.update(INSERT_RESERVATION_INFO_PRICE, params);
    }
    
    public List<ReservationInfoPrice> selectReservationInfoPrices(Long reservationInfoId) {
    	Map<String, Object> params = Collections.singletonMap("reservationInfoId", reservationInfoId);
    	return jdbc.query(SELECT_RESERVATION_INFO_PRICE_BY_RESERVATION_INFO_ID, params, rowMapperOfReservationInfoPrices);
    }
    
    public List<ReservationInfosResult> selectReservationInfoList(Long userId) {
    	Map<String, Object> params = Collections.singletonMap("userId", userId);
    	return jdbc.query(SELECT_RESERVATION_INFO_BY_USER_ID, params, rowMapperOfReservationInfoList);
    }
    
    public int selectSumPrice(Long reservationInfoId) {
    	Map<String, Object> params = Collections.singletonMap("reservationInfoId", reservationInfoId);
    	return jdbc.queryForObject(SELECT_SUM_PRICE_BY_RESERVATION_INFO_ID, params, Integer.class);
    }
    
    public int updateCancelFlagOfReservationInfo(Long id) {
    	Map<String, Object> params = Collections.singletonMap("id", id); 
    	return jdbc.update(UPDATE_CANCEL_FLAGE_OF_RESERVATION_INFO_BY_ID, params);
    }
}
