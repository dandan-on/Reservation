package kr.or.connect.reservation.dao;

public class DisplayInfoDaoSqls {
	//전시 상품 목록 구하기
	public static final String SELECT_DISPLAY_INFOS_IN_CATEGORY = 
			"SELECT product.id" + 
			"     , product.category_id" + 
			"     , displayInfo.id AS display_info_id" + 
			"     , category.name" + 
			"     , product.description" + 
			"     , product.content" + 
			"     , product.event" + 
			"     , displayInfo.opening_hours" + 
			"     , displayInfo.place_name" + 
			"     , displayInfo.place_lot" + 
			"     , displayInfo.place_street" + 
			"     , displayInfo.tel" + 
			"     , displayInfo.homepage" + 
			"     , displayInfo.email" + 
			"     , displayInfo.create_date" + 
			"     , displayInfo.modify_date" + 
			"     , proudctImage.file_id" + 
			"  FROM display_info displayInfo" + 
			"  JOIN product ON displayInfo.product_id = product.id" + 
			"  JOIN product_image proudctImage ON proudctImage.product_id = product.id" + 
			"  JOIN category ON category.id = product.category_id" + 
			" WHERE category.id = :categoryId" + 
			"   AND proudctImage.type = 'th'" + 
			" LIMIT :start, 4";

	public static final String SELECT_DISPLAY_INFOS_IN_ALL_CATEGORIES = 
			"SELECT product.id" + 
			"     , product.category_id" + 
			"     , displayInfo.id AS display_info_id" + 
			"     , category.name" + 
			"     , product.description" + 
			"     , product.content" + 
			"     , product.event" + 
			"     , displayInfo.opening_hours" + 
			"     , displayInfo.place_name" + 
			"     , displayInfo.place_lot" + 
			"     , displayInfo.place_street" + 
			"     , displayInfo.tel" + 
			"     , displayInfo.homepage" + 
			"     , displayInfo.email" + 
			"     , displayInfo.create_date" + 
			"     , displayInfo.modify_date" + 
			"     , proudctImage.file_id" + 
			"  FROM display_info displayInfo" + 
			"  JOIN product ON displayInfo.product_id = product.id" + 
			"  JOIN product_image proudctImage ON proudctImage.product_id = product.id" + 
			"  JOIN category ON category.id = product.category_id" + 
			" WHERE proudctImage.type = 'th'" + 
			" LIMIT :start, 4";
	
	public static final String SELECT_COUNT_OF_DISPLAY_INFOS_IN_CATEGORY = 
			"SELECT COUNT(*) AS count" + 
			"  FROM product" + 
			"  JOIN display_info" +
			"   ON product.id = display_info.product_id" +
			" WHERE product.category_id = :categoryId";
	
	public static final String SELECT_COUNT_OF_ALL_DISPLAY_INFOS = "SELECT COUNT(*) FROM display_info";
	
	//전시 정보 구하기
	public static final String SELECT_DISPLAY_INFO_BY_DISPLAY_INFO_ID = 
			"SELECT product.id," + 
			"       product.category_id," + 
			"       displayInfo.id AS display_info_id," + 
			"       category.name," + 
			"       product.description," + 
			"       product.content," + 
			"       product.event," + 
			"       displayInfo.opening_hours," + 
			"       displayInfo.place_name," + 
			"       displayInfo.place_lot," + 
			"       displayInfo.place_street," + 
			"       displayInfo.tel," + 
			"       displayInfo.homepage," + 
			"       displayInfo.email," + 
			"       displayInfo.create_date," + 
			"       displayInfo.modify_date," + 
			"       productImage.file_id " + 
			"FROM   product" + 
			"       JOIN display_info displayInfo" + 
			"         ON product.id = displayInfo.product_id" + 
			"       JOIN category" + 
			"         ON product.category_id = category.id" + 
			"       JOIN product_image productImage" + 
			"         ON productImage.product_id = product.id " + 
			"WHERE  displayInfo.id = :displayInfoId" + 
			"       AND productImage.type = 'ma' ";
	
	public static final String SELECT_PRODUCT_IMAGES_BY_PRODUCT_ID = 
			"SELECT productImage.product_id," + 
			"       productImage.id AS product_image_id," + 
			"       productImage.type," + 
			"       fileInfo.id AS file_info_id," + 
			"       fileInfo.file_name," + 
			"       fileInfo.save_file_name," + 
			"       fileInfo.content_type," + 
			"       fileInfo.delete_flag," + 
			"       fileInfo.create_date," + 
			"       fileInfo.modify_date " + 
			"FROM   product_image productImage" + 
			"       JOIN file_info fileInfo" + 
			"         ON productImage.file_id = fileInfo.id " + 
			"WHERE  productImage.product_id = :productId" + 
			"       AND productImage.type = 'ma'";
	
	public static final String SELECT_DISPLAY_INFO_IMAGES_BY_DISPLAY_INFO_ID = 
			"SELECT displayInfoImage.id," + 
			"       displayInfoImage.display_info_id," + 
			"       displayInfoImage.file_id," + 
			"       fileInfo.file_name," + 
			"       fileInfo.save_file_name," + 
			"       fileInfo.content_type," + 
			"       fileInfo.delete_flag," + 
			"       fileInfo.create_date," + 
			"       fileInfo.modify_date " + 
			"FROM   display_info_image displayInfoImage " + 
			"       JOIN file_info fileInfo" + 
			"         ON displayInfoImage.file_id = fileInfo.id " + 
			"WHERE  displayInfoImage.display_info_id = :displayInfoId";
	
	public static final String SELECT_AVG_SCORE_OF_COMMENTS_BY_PRODUCT_ID = 
			"SELECT Ifnull(Avg(score), 0) " + 
			"FROM   reservation_user_comment " + 
			"WHERE  product_id = :productId";
	
	public static final String SELECT_PRODUCT_PRICES_BY_PRODUCT_ID = 
			"SELECT id, product_id, price_type_name, price, discount_rate, create_date, modify_date " + 
			"FROM product_price " + 
			"WHERE product_id = :productId " + 
			"ORDER BY id DESC";
}
