package kr.or.connect.reservation.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.or.connect.reservation.dao.CommentDao;
import kr.or.connect.reservation.dto.ReservationUserCommentImagesResult;
import kr.or.connect.reservation.dto.ReservationUserCommentsResult;
import kr.or.connect.reservation.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentDao commentDao;
	
	@Override
	public List<ReservationUserCommentsResult> getReservationUserComments(Long productId, int start) {
		List<ReservationUserCommentsResult> reservationUserComments = 
				commentDao.selectReservationUserComments(productId, start);
		System.out.println(reservationUserComments);
		for (ReservationUserCommentsResult comment : reservationUserComments) {
			List<ReservationUserCommentImagesResult> reservationUserCommentImages = 
					commentDao.selectReservationUserCommentImages(comment.getId());
			comment.setReservationUserCommentImages(reservationUserCommentImages);
		}
		
		return reservationUserComments;
	}

	@Override
	public int getTotalCountOfComments(Long productId) {
		int countOfComments = commentDao.selectCountfComments(productId);
		return countOfComments;
	}

	@Override
	@Transactional
	public HashMap<String, Object> inputComments(Long reservationInfoId, float score, String comment, 
			MultipartFile file, String saveFileName) {
		Map<String, Object> reservationInfo = commentDao.selectReservationInfo(reservationInfoId);
		Long productId = Long.valueOf(reservationInfo.get("productId").toString());
		Long userId = Long.valueOf(reservationInfo.get("userId").toString()); 
		Long reservationUserCommentId = commentDao.insertReservationUserComment(productId, reservationInfoId, userId, score, comment);
		
		String result = "success";
		if (file != null) {
			String fileName = file.getOriginalFilename();
			String contentType = file.getContentType();
			Long fileId = commentDao.insertFileInfo(fileName, saveFileName, contentType);

			int countOfInsert = commentDao.insertReservationUserCommentImage(reservationInfoId, reservationUserCommentId, fileId);

			if (countOfInsert == 0) {
				result = "fail";
			}			
		} 
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", result);
		resultMap.put("productId", productId);
		return resultMap;
	}

}
