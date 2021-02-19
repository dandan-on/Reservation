package kr.or.connect.reservation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.reservation.dao.UserDao;
import kr.or.connect.reservation.dto.User;
import kr.or.connect.reservation.dto.UserRole;
import kr.or.connect.reservation.service.UserService;
import kr.or.connect.reservation.service.security.UserEntity;
import kr.or.connect.reservation.service.security.UserRoleEntity;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public UserEntity getUser(String email) {
		//email이 userLoginId로 사용됨
		User user = userDao.selectUserByEmail(email);
		return new UserEntity(user.getEmail(), user.getPassword());
	}

	@Override
	public List<UserRoleEntity> getUserRoles(String email) {
		List<UserRole> userRoles = userDao.selectUserRolesByEmail(email);
		List<UserRoleEntity> userRoleEntityList = new ArrayList<>();
		
		for (UserRole userRole : userRoles) {
			userRoleEntityList.add(new UserRoleEntity(email, userRole.getRoleName()));
		}
		return userRoleEntityList;
	}

	@Override
	public Long getUserId(String email) {
		User user = userDao.selectUserByEmail(email);
		return user.getId();
	}
	

}
