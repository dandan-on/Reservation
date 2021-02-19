package kr.or.connect.reservation.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;

import kr.or.connect.reservation.config.ApplicationConfig;
import kr.or.connect.reservation.config.MvcConfig;
import kr.or.connect.reservation.dao.CommentDao;
import kr.or.connect.reservation.dto.ReservationUserCommentImagesResult;
import kr.or.connect.reservation.dto.ReservationUserCommentsResult;
import kr.or.connect.reservation.service.impl.CommentServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes= {MvcConfig.class, ApplicationConfig.class})	
public class CommentServiceTest {
    @InjectMocks
    public CommentServiceImpl commentServiceImpl;
    
	@Mock //목객체 생성 
	CommentDao commentDao;
	
	@Before //테스트 메소드 실행 전에 실행됨
	public void before() {
		MockitoAnnotations.initMocks(this); //@Mock이 붙은 필드를 목객체로 초기화		
	}

	@Test
	public void getReservationUserComments() throws Exception {
		
		ReservationUserCommentsResult reservationUserComments = new ReservationUserCommentsResult();
		reservationUserComments.setId(1L);
		
		List<ReservationUserCommentsResult> reservationUserCommentsList = Arrays.asList(reservationUserComments);
		when(commentDao.selectReservationUserComments(2L, 5)).thenReturn(reservationUserCommentsList);
		
		ReservationUserCommentImagesResult reservationUserCommentImage = new ReservationUserCommentImagesResult();
		reservationUserCommentImage.setId(1L);
		List<ReservationUserCommentImagesResult> reservationUserCommentImages =  Arrays.asList(reservationUserCommentImage);
		when(commentDao.selectReservationUserCommentImages(reservationUserComments.getId())).thenReturn(reservationUserCommentImages);
	
		List<ReservationUserCommentsResult> reservationUserCommentsResult = commentServiceImpl.getReservationUserComments(2L, 5);
		
		verify(commentDao).selectReservationUserComments(2L, 5);
		verify(commentDao).selectReservationUserCommentImages(1L);
		assertThat(reservationUserCommentsResult.get(0).getId(), is(1L));
		assertThat(reservationUserCommentsResult.get(0).getReservationUserCommentImages().get(0).getId(), is(1L));

	}
	
	@Test
	public void getTotalCountOfComments() throws Exception {
		
		when(commentDao.selectCountfComments(2L)).thenReturn(10);
		
		int avgScore = commentServiceImpl.getTotalCountOfComments(2L);
		
		verify(commentDao).selectCountfComments(2L);
		assertThat(avgScore, is(10));

	}
	
	@Test
	public void inputComments() throws Exception {

		Map<String, Object> reservationInfo = new HashMap<>();
		reservationInfo.put("productId", 1L);
		reservationInfo.put("userId", 1L);
		Long reservationInfoId = 1L;
		Long reservationUserCommentId = 2L;
		when(commentDao.insertReservationUserComment((Long) reservationInfo.get("productId"), reservationInfoId, 
				(Long) reservationInfo.get("userId"), (float) 4.5, "soso")).thenReturn(reservationUserCommentId);	
		
		MultipartFile file = new MockMultipartFile("testImage.jpg", new FileInputStream(new File("c:/tmp/heart.jpg")));
		Long fileId =3L;
		when(commentDao.insertFileInfo(file.getOriginalFilename(), "랜덤 파일 이름", file.getContentType())).thenReturn(fileId);
		when(commentDao.insertReservationUserCommentImage(reservationInfoId, reservationUserCommentId, fileId)).thenReturn(1);
		
		Long commentIdResult = commentDao.insertReservationUserComment((Long) reservationInfo.get("productId"), reservationInfoId, 
				(Long) reservationInfo.get("userId"), (float) 4.5, "soso");
		Long fileIdResult = commentDao.insertFileInfo(file.getOriginalFilename(), "랜덤 파일 이름", file.getContentType());
		int insertResult = commentDao.insertReservationUserCommentImage(reservationInfoId, reservationUserCommentId, fileId);
		
		assertThat(commentIdResult, is(2L));
		assertThat(fileIdResult, is(3L));
		assertThat(insertResult, is(1));
	}
}
