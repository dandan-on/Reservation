package kr.or.connect.reservation.dao;

public class FileDaoSqls {
	public static final String SELECT_FILE_INFO_BY_ID = 
			"SELECT file_name fileName, save_file_name saveFileName, content_type contentType " + 
			"FROM file_info " + 
			"WHERE id = :id";
}
