package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.FileDaoSqls.SELECT_FILE_INFO_BY_ID;

import java.util.Collections;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FileDao {
	private NamedParameterJdbcTemplate jdbc;

    public FileDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }
    
    public Map<String, Object> selectFileInfo(Long fileId) {
    	Map<String, Object> params = Collections.singletonMap("id", fileId);
    	return jdbc.queryForMap(SELECT_FILE_INFO_BY_ID, params);	
    }

}
