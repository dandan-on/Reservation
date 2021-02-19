package kr.or.connect.reservation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.or.connect.reservation.service.security.AdminFailureHandler;
import kr.or.connect.reservation.service.security.AdminSuccessHandler;
import kr.or.connect.reservation.service.security.CustomUserDetailsService;

//스프링 시큐리티를 이용해 인증,인가 등을 처리하기 위한 설정 파일
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
    //인증,인가 처리하지 않도록 무시
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
        		"/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/swagger/**");
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	//아이디,암호를 입력 받아 로그인을 처리하는 AuthenticationFilter에서 DB에서 정보를 읽어들이기 위해 UserDetailsService 인터페이스를 구현하고 있는 빈을 사용
    	auth.userDetailsService(customUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        	http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/loginerror", "/api/categories",
                		"/api/displayinfos/**","/api/promotions"            
                		).permitAll() //누구나 접근할 수 있는 경로
                .antMatchers("/loginsuccess", "/api/reservationInfos","/api/comments").hasRole("USER")
                .anyRequest().authenticated() //그 외 요청은 모두 인증 후 접근 가능
        		.and()
        			.formLogin()
        			.loginPage("/login")
        			.usernameParameter("email")
        			.passwordParameter("password")
        			.loginProcessingUrl("/authenticate")
        			.defaultSuccessUrl("/loginsuccess", true)
        			.failureForwardUrl("/loginerror")
        			.failureHandler(new AdminFailureHandler())
        			.successHandler(new AdminSuccessHandler())
        			.permitAll()
        		.and()
        			.logout()
        			.logoutUrl("/logout")
        			.logoutSuccessUrl("/login");
    }

    // 패스워드 인코더를 빈으로 등록합니다. 암호를 인코딩하거나, 
    // 인코딩된 암호와 사용자가 입력한 암호가 같은 지 확인할 때 사용합니다.
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
