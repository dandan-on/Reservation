package kr.or.connect.reservation.dao;

public class UserDaoSqls {
	public static final String SELECT_USER_BY_EMAIL = 
			"SELECT id, name, password, email, phone, create_date, modify_date " + 
			"FROM user "+ 
			"WHERE email = :email";
	
	public static final String SELECT_USER_ROLES_BY_EMAIL = 
			"SELECT user_role.id, user_id, role_name " +
			"FROM user_role " +
				"JOIN user ON user_role.user_id = user.id " +
			"WHERE user.email = :email";
	
}