package kr.or.connect.reservation.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import kr.or.connect.reservation.config.ApplicationConfig;
import kr.or.connect.reservation.config.MvcConfig;
import kr.or.connect.reservation.dto.ReservationUserCommentsResult;
import kr.or.connect.reservation.service.CommentService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes= {MvcConfig.class, ApplicationConfig.class})	
public class CommentApiControllerTest {
	
	@InjectMocks //목객체를 사용
	public CommentApiController commentApiController;
	
	@Mock //목객체 생성 
	CommentService commentService;
	
	private MockMvc mockMvc;
	
	@Before //테스트 메소드 실행 전에 실행됨
	public void createController() {
		MockitoAnnotations.initMocks(this); //@Mock이 붙은 필드를 목객체로 초기화
		mockMvc = MockMvcBuilders.standaloneSetup(commentApiController).build(); //MockMvc 타입의 변수 초기화. 테스트를 위한 객체 생성
	}
	
	@Test
	public void showComments() throws Exception {
		Long productId = 3L;
		int start = 0;
		
		ReservationUserCommentsResult reservationUserComments = new ReservationUserCommentsResult();
		reservationUserComments.setId(1L);
		reservationUserComments.setComment("재밌었어요!");
		
		List<ReservationUserCommentsResult> displayInfoList = Arrays.asList(reservationUserComments);
		when(commentService.getReservationUserComments(productId, start)).thenReturn(displayInfoList);
		when(commentService.getTotalCountOfComments(productId)).thenReturn(16);

		RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/api/displayinfos/comments?productId="+productId+"&start="+start).
				contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(reqBuilder).andExpect(status().isOk()).andDo(print()); 
		
		verify(commentService).getReservationUserComments(productId, start);
		verify(commentService).getTotalCountOfComments(productId);
	}
	
	@Test
	@WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
	public void inputComments() throws Exception {
		MultipartFile file = new MockMultipartFile("testImage.jpg", new FileInputStream(new File("c:/tmp/heart.jpg")));
		Long reservationInfoId = 1L;
		float score = 5;
		String comment = "very good";
		
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", "success");
		
		when(commentService.inputComments(reservationInfoId, score, comment, file, "랜덤 파일 이름")).thenReturn(resultMap);
		
		Principal principal = SecurityContextHolder.getContext().getAuthentication();
		RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/api/displayinfos/comments?reservationInfoId="+reservationInfoId+
				"&score="+score+"&comment"+comment).principal(principal).
				contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(reqBuilder).andExpect(status().isOk()).andDo(print()); 
		
		HashMap<String, Object> result = commentService.inputComments(reservationInfoId, score, comment, file, "랜덤 파일 이름");
		
		verify(commentService).inputComments(reservationInfoId, score, comment, file, "랜덤 파일 이름");
		assertThat(result.get("result"),is("success"));
	}
	

	
}
