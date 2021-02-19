package kr.or.connect.reservation.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.or.connect.reservation.dto.ReservationUserCommentsResult;

public interface CommentService {
	public List<ReservationUserCommentsResult> getReservationUserComments(Long productId, int start);
	public int getTotalCountOfComments(Long productId);
	public HashMap<String, Object> inputComments(Long reservationInfoId, float score, String comment, MultipartFile file, String saveFileName);

}
