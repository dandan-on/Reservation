package kr.or.connect.reservation.controller;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.or.connect.reservation.dto.ReservationUserCommentsResult;
import kr.or.connect.reservation.service.CommentService;

@RestController
@RequestMapping(value="/api")
public class CommentApiController {
	
	@Autowired
	private CommentService commentService;
	
	@ApiOperation(value="댓글 목록 구하기")
    @ApiResponses({  
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Exception")
    })
	@GetMapping(value = "/displayinfos/comments")
	public  HashMap<String, Object> showComments(@RequestParam(name="productId", required=false) Long productId, 
			@RequestParam(name="start", required=false, defaultValue="0") int start) {

		List<ReservationUserCommentsResult> reservationUserComments =
				commentService.getReservationUserComments(productId, start);
		int commentCount = reservationUserComments.size();
		int totalCount = commentService.getTotalCountOfComments(productId);
		
		HashMap<String,Object> resultMap= new HashMap<>();
		resultMap.put("totalCount", totalCount);
		resultMap.put("commentCount", commentCount);		
		resultMap.put("reservationUserComments", reservationUserComments);
		return resultMap;
	}
	
	@ApiOperation(value="댓글 등록하기")
    @ApiResponses({  
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Exception")
    })
	@PostMapping(value = "/comments")
	public  HashMap<String, Object> inputComments(Principal principal,
			@RequestParam(name="reservationInfoId", required=true) Long reservationInfoId, 
			@RequestParam(name="score", required=true) float score,
			@RequestParam(name="comment", required=true) String comment,
			@RequestParam(name="multipartFile",required=false) MultipartFile file) {
		
		HashMap<String, Object> resultMap= new HashMap<>();
		if (principal == null) {
			throw new AuthenticationCredentialsNotFoundException("로그인한 상태가 아닙니다.");
		} else {
			if(file != null) {
				UUID uid = UUID.randomUUID(); //겹쳐지지 않는 파일명을 위한 유니크한 값 생성	 
				String saveFileName = uid.toString() + "_" + file.getOriginalFilename(); //원본파일 이름과 UUID 결합
				try (FileOutputStream fos = new FileOutputStream("c:/tmp/"+saveFileName);
					InputStream is =file.getInputStream();) {
					
					int readCount = 0;
					byte[] buffer = new byte[1024];
					
					while((readCount = is.read(buffer)) != -1) { //관련 설명 https://programmers.co.kr/learn/questions/581
						fos.write(buffer,0,readCount);
					}
					resultMap = commentService.inputComments(reservationInfoId, score, comment, file, saveFileName);
				} catch (Exception e){
					throw new RuntimeException("file Save Error");
				}
			} else {
				String saveFileName = null;
				resultMap = commentService.inputComments(reservationInfoId, score, comment, file, saveFileName);
			}
		}
		return resultMap;
	}
	
}
