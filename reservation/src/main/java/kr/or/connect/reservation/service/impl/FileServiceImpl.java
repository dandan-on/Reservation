package kr.or.connect.reservation.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.reservation.dao.FileDao;
import kr.or.connect.reservation.service.FileService;

@Service
public class FileServiceImpl implements FileService{
	@Autowired
	private FileDao fileDao;
	
	@Override
	public Map<String, Object> getFileInfo(Long fileId) {
		return fileDao.selectFileInfo(fileId);
	}

}
