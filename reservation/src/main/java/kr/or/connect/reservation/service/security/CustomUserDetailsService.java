package kr.or.connect.reservation.service.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	//DB에 접근하는 코드와 스프링 시큐리티 관련 코드 분리
	@Autowired
	UserDbService userDbService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//DB에 아이디가 존재할 경우 객체 리턴, 없을 경우 에러 발생
		//email이 loginId로 사용됨
		UserEntity customUser = userDbService.getUser(email); //디비에서 가져온 유저 정보 저장
		if (customUser == null)
			throw new UsernameNotFoundException("입력한 아이디에 해당하는 사용자를 찾을 수 없습니다");
		
		//유저 정보를 customUserDetails 객체에 저장
		CustomUserDetails customUserDetails = new CustomUserDetails();
		customUserDetails.setUsername(customUser.getUserLoginId());
		customUserDetails.setPassword(customUser.getPassword());
		
		List<UserRoleEntity> customRoles = userDbService.getUserRoles(email);
		List<GrantedAuthority> authorities = new ArrayList<>(); //권한 정보를 추가
		if(customRoles != null) {
			for (UserRoleEntity customRole : customRoles) {
				authorities.add(new SimpleGrantedAuthority(customRole.getRoleName()));
			}
		}
 		
		customUserDetails.setAuthorities(authorities);
		customUserDetails.setEnabled(true);
		customUserDetails.setAccountNonExpired(true);
		customUserDetails.setAccountNonLocked(true);
		customUserDetails.setCredentialsNonExpired(true);
		
		return customUserDetails;
	}

}
