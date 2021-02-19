package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.CommentDaoSqls.SELECT_COUNT_OF_COMMENTS_BY_PRODUCT_ID;
import static kr.or.connect.reservation.dao.CommentDaoSqls.SELECT_RESERVATION_USER_COMMENTS_BY_PRODUCT_ID;
import static kr.or.connect.reservation.dao.CommentDaoSqls.SELECT_RESERVATION_USER_COMMENT_IMAGES;
import static kr.or.connect.reservation.dao.ReservationInfoDaoSqls.SELECT_RESERVATION_INFO_BY_ID;
import static kr.or.connect.reservation.dao.CommentDaoSqls.INSERT_RESERVATION_USER_COMMENT_IMAGE;

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

import kr.or.connect.reservation.dto.ReservationUserCommentImagesResult;
import kr.or.connect.reservation.dto.ReservationUserCommentsResult;

@Repository
public class CommentDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<ReservationUserCommentsResult> rowMapperOfCommentsResult = 
			BeanPropertyRowMapper.newInstance(ReservationUserCommentsResult.class);
	private RowMapper<ReservationUserCommentImagesResult> rowMapperOfCommentImagesResult = 
			BeanPropertyRowMapper.newInstance(ReservationUserCommentImagesResult.class);
	private SimpleJdbcInsert insertActionOfReservationUserComment;
	private SimpleJdbcInsert insertActionOfFileInfo;

	
    public CommentDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertActionOfReservationUserComment = new SimpleJdbcInsert(dataSource)
        		.withTableName("reservation_user_comment")
        		.usingGeneratedKeyColumns("id");
        this.insertActionOfFileInfo = new SimpleJdbcInsert(dataSource)
        		.withTableName("file_info")
        		.usingGeneratedKeyColumns("id");
    }
    
    public List<ReservationUserCommentsResult> selectReservationUserComments(Long productId, int start) {
    	Map<String,Object> params = new HashMap<>();
    	params.put("productId", productId);
    	params.put("start", start);
    	return jdbc.query(SELECT_RESERVATION_USER_COMMENTS_BY_PRODUCT_ID, params, rowMapperOfCommentsResult);	
    }
    
    public List<ReservationUserCommentImagesResult> selectReservationUserCommentImages(Long reservationUserCommentId) {
    	Map<String, Object> params = Collections.singletonMap("reservationUserCommentId", reservationUserCommentId);
    	return jdbc.query(SELECT_RESERVATION_USER_COMMENT_IMAGES, params, rowMapperOfCommentImagesResult);	
    }
    
    public int selectCountfComments(Long productId) {
    	Map<String, Object> params = Collections.singletonMap("productId", productId);
    	return jdbc.queryForObject(SELECT_COUNT_OF_COMMENTS_BY_PRODUCT_ID, params, Integer.class);
    }
    
    public Map<String,Object> selectReservationInfo(Long id) {
    	Map<String, Object> params = Collections.singletonMap("id", id);
    	return jdbc.queryForMap(SELECT_RESERVATION_INFO_BY_ID, params);
    }
    
    public Long insertReservationUserComment(Long productId, Long reservationInfoId, Long userId, float score, String comment) {
    	HashMap<String,Object> params = new HashMap<>();
    	params.put("product_id", productId);
    	params.put("reservation_info_id", reservationInfoId);
    	params.put("user_id", userId);
    	params.put("score", score);
    	params.put("comment", comment);
    	params.put("create_date", new java.sql.Date(new Date().getTime()));
    	params.put("modify_date", new java.sql.Date(new Date().getTime()));
	    return insertActionOfReservationUserComment.executeAndReturnKey(params).longValue();
	}
    
    public Long insertFileInfo(String fileName, String saveFileName, String contentType) {
    	HashMap<String,Object> params = new HashMap<>();
    	params.put("file_name", fileName);
    	params.put("save_file_name", saveFileName);
    	params.put("content_type", contentType);
    	params.put("delete_flag", 0);
    	params.put("create_date", new java.sql.Date(new Date().getTime()));
    	params.put("modify_date", new java.sql.Date(new Date().getTime()));
	    return insertActionOfFileInfo.executeAndReturnKey(params).longValue();
	}
    
    public int insertReservationUserCommentImage(Long reservationInfoId, Long reservationUserCommentId, Long fileId) {
    	HashMap<String,Object> params = new HashMap<>();
    	params.put("reservationInfoId", reservationInfoId);
    	params.put("reservationUserCommentId", reservationUserCommentId);
    	params.put("fileId", fileId);
    	return jdbc.update(INSERT_RESERVATION_USER_COMMENT_IMAGE, params);
    }
    
}
