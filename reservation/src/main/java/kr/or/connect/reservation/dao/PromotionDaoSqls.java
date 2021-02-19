package kr.or.connect.reservation.dao;

public class PromotionDaoSqls {
	public static final String SELECT_PROMOTIONS = 
			"SELECT promotion.id," +
			"		promotion.product_id," +
			"       product.category_id," + 
			"       category.NAME AS category_name," + 
			"       product.description," + 
			"       product_image.file_id " + 
			"FROM   promotion " + 
			"       JOIN product" + 
			"         ON promotion.product_id = product.id" + 
			"       JOIN product_image" + 
			"         ON product.id = product_image.product_id" + 
			"       JOIN category" + 
			"         ON category.id = product.category_id " + 
			"WHERE  product_image.type = 'th'";
}
