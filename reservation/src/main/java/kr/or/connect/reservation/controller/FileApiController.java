package kr.or.connect.reservation.controller;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.or.connect.reservation.service.FileService;

@RestController
@RequestMapping(value="/api")
public class FileApiController {
	@Autowired
	private FileService fileService;
	
	@ApiOperation(value="이미지 다운로드")
    @ApiResponses({  
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Exception")
    })
	@GetMapping(value = "/files/{fileId}")
	public void downloadImage(@PathVariable(name="fileId", required=true) Long fileId, HttpServletResponse response) {
		
		Map<String, Object> fileInfo = fileService.getFileInfo(fileId);
		String fileName = (String) fileInfo.get("fileName");
		String saveFileName = "c:/tmp/"+(String) fileInfo.get("saveFileName");
		String contentType = (String) fileInfo.get("contentType");
		
		response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\";");
		response.setHeader("Content-Transfer-Encoding","binary");
		response.setHeader("Content-Type", contentType);
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires","-1;");
		
		try (FileInputStream fis = new FileInputStream(saveFileName);
			 OutputStream out = response.getOutputStream();) 
		{
			int readCount = 0;
			byte[] buffer = new byte[1024];
			
			while((readCount = fis.read(buffer)) != -1) {
				out.write(buffer,0,readCount);
			}
		} catch(Exception ex) {
			throw new RuntimeException("file download error");
		}
	}
}
