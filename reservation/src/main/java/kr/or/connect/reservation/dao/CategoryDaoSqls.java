package kr.or.connect.reservation.dao;

public class CategoryDaoSqls {
	public static final String SELECT_CATEGORIES = 
			"SELECT category.id," + 
			"       category.name," + 
			"       Count(*) AS count " + 
			"FROM product " + 
			"       JOIN display_info" + 
			"         ON product.id = display_info.product_id" + 
			"       JOIN category" + 
			"         ON product.category_id = category.id " + 
			"GROUP BY category.id";
	
}
