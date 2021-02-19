package kr.or.connect.reservation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages ={"kr.or.connect.reservation.service","kr.or.connect.reservation.dao"})
@Import({DBConfig.class})
public class ApplicationConfig {
	//Bean들을 설정하는 기본적인 스프링 설정 파일 작성
	//basePackages에 지정된 패키지 이하의 Bean들만 찾음
	
	
}
