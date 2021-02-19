package kr.or.connect.reservation.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//자바 config 설정 파일
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{
	//DispatcherServlet을 FrontController로 지정
	//WebApplicationInitializer을 구현하고 있는 클래스를 상속받아 필요한 부분만 오버라이딩
	
	//Spring 기본 설정 파일 클래스 지정
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[] {ApplicationConfig.class, SecurityConfig.class}; //배열의 초기화 방식
	}
	
	//Spring MVC 설정 파일 클래스 지정
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[] {MvcConfig.class};
	}
	
	//DispatcherServlet이 동작할 맵핑정보 설정 - 모든 요청을 처리
	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] {"/"};
	}
	
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		return new Filter[] {encodingFilter};
	}
	
}
