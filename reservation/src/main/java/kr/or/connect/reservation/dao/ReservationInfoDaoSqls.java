package kr.or.connect.reservation.dao;

public class ReservationInfoDaoSqls {
	public static final String SELECT_RESERVATION_INFO_BY_ID = 
			"SELECT id," +
			"	product_id productId," +
			"	cancel_flag cancelFlag," +
			"	display_info_id displayInfoId," +
			"	user_id userId," +
			"	reservation_date reservationDate," +
			"	create_date createDate," +
			"	modify_date modifyDate " +
			"FROM reservation_info "+
			"WHERE id = :id";
	
	public static final String INSERT_RESERVATION_INFO_PRICE = 
			"INSERT INTO reservation_info_price(reservation_info_id, product_price_id, count) " +
			"VALUES(:reservationInfoId, :productPriceId, :count)";
	
	public static final String SELECT_RESERVATION_INFO_PRICE_BY_RESERVATION_INFO_ID = 
			"SELECT id, reservation_info_id, product_price_id, count " +
			"FROM reservation_info_price "+
			"WHERE reservation_info_id = :reservationInfoId";
	
	public static final String SELECT_RESERVATION_INFO_BY_USER_ID = 
			"SELECT reservationInfo.id," + 
			"       reservationInfo.product_id," + 
			"       reservationInfo.display_info_id," + 
			"       reservationInfo.cancel_flag," + 
			"       product.description," + 
			"       product.content," + 
			"       reservationInfo.user_id," + 
			"       reservationInfo.reservation_date," + 
			"       reservationInfo.create_date," + 
			"       reservationInfo.modify_date " + 
			"FROM   reservation_info reservationInfo" + 
			"       JOIN product" + 
			"         ON reservationInfo.product_id = product.id " + 
			"WHERE  reservationInfo.user_id = :userId";
	
	public static final String SELECT_SUM_PRICE_BY_RESERVATION_INFO_ID = 
			"SELECT Sum(count * price) AS sum_price " + 
			"FROM   reservation_info_price reservationInfoPrice" + 
			"       JOIN product_price productPrice" + 
			"         ON reservationInfoPrice.product_price_id = productPrice.id " + 
			"WHERE  reservationInfoPrice.reservation_info_id = :reservationInfoId";
	
	public static final String UPDATE_CANCEL_FLAGE_OF_RESERVATION_INFO_BY_ID = 
			"UPDATE reservation_info " +
			"SET cancel_flag = 1 " +
			"WHERE id = :id";
}
