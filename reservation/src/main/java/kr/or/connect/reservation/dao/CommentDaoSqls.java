package kr.or.connect.reservation.dao;

public class CommentDaoSqls {
	public static final String SELECT_RESERVATION_USER_COMMENTS_BY_PRODUCT_ID =
			"SELECT Comment.id," + 
			"       Comment.product_id," + 
			"       Comment.reservation_info_id," + 
			"       Comment.score," + 
			"       user.email AS reservation_email," + 
			"       Comment.comment," + 
			"       Comment.create_date," + 
			"       Comment.modify_date " + 
			"FROM   reservation_user_comment Comment" + 
			"       JOIN user " + 
			"         ON Comment.user_id = user.id " + 
			"WHERE  Comment.product_id = :productId " + 
			"ORDER  BY id DESC " + 
			"LIMIT  :start, 5";
	
	public static final String SELECT_RESERVATION_USER_COMMENT_IMAGES =
			"SELECT reservationUserCommentImage.id," + 
			"       reservation_info_id," + 
			"       reservation_user_comment_id," + 
			"       file_id," + 
			"       fileInfo.file_name," + 
			"       save_file_name," + 
			"       content_type," + 
			"       delete_flag," + 
			"       create_date," + 
			"       modify_date " + 
			"FROM   reservation_user_comment_image reservationUserCommentImage" + 
			"       JOIN file_info fileInfo " + 
			"         ON reservationUserCommentImage.file_id = fileInfo.id " + 
			"WHERE  reservationUserCommentImage.reservation_user_comment_id = :reservationUserCommentId";
	
	public static final String SELECT_COUNT_OF_COMMENTS_BY_PRODUCT_ID = 
			"SELECT COUNT(*) " + 
			"FROM reservation_user_comment " +
			"WHERE product_id = :productId";
	
	public static final String INSERT_RESERVATION_USER_COMMENT_IMAGE = 
			"INSERT INTO reservation_user_comment_image " + 
			"            (reservation_info_id, " + 
			"             reservation_user_comment_id, " + 
			"             file_id) " + 
			"VALUES     (:reservationInfoId, " + 
			"            :reservationUserCommentId, " + 
			"            :fileId)";
	
	
	
}
