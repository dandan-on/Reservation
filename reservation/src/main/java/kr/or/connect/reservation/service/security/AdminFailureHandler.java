package kr.or.connect.reservation.service.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class AdminFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String error_message = exception.getMessage();
		System.out.println("email..."+email);
		System.out.println("password..."+password);
		System.out.println("error_message..."+error_message);
		response.sendRedirect(request.getContextPath()+"/loginerror");
	}

}
