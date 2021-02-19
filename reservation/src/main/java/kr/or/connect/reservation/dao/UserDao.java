package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.UserDaoSqls.SELECT_USER_BY_EMAIL;
import static kr.or.connect.reservation.dao.UserDaoSqls.SELECT_USER_ROLES_BY_EMAIL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.User;
import kr.or.connect.reservation.dto.UserRole;

@Repository
public class UserDao {
	private NamedParameterJdbcTemplate jdbc;
	//클래스의 속성을 자동으로 칼럼과 매핑해주는 객체
	private RowMapper<User> rowMapperOfUser = BeanPropertyRowMapper.newInstance(User.class);
	private RowMapper<UserRole> rowMapperOfUserRole = BeanPropertyRowMapper.newInstance(UserRole.class);
	
	public UserDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public User selectUserByEmail(String email) {
		Map<String, Object> params = new HashMap<>();
		params.put("email", email);
		return jdbc.queryForObject(SELECT_USER_BY_EMAIL,params,rowMapperOfUser);
	}
	
	public List<UserRole> selectUserRolesByEmail(String email) {
		Map<String, Object> params = new HashMap<>();
		params.put("email", email);
		return jdbc.query(SELECT_USER_ROLES_BY_EMAIL,params,rowMapperOfUserRole);
	}
}
