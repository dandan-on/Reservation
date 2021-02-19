package kr.or.connect.reservation.service.security;

import java.util.List;

public interface UserDbService {
	public UserEntity getUser(String email);
	public List<UserRoleEntity> getUserRoles(String email);
}
