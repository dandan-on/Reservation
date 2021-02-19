package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.CategoryDaoSqls.SELECT_CATEGORIES;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.CategoriesResult;

@Repository
public class CategoryDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<CategoriesResult> rowMapperOfCategoryList = BeanPropertyRowMapper.newInstance(CategoriesResult.class); //query의 결과(resultSet)과 자바 객체를 매핑 =while(rs.next())


    public CategoryDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }
    
    public List<CategoriesResult> selectCategoryList() {
	    return jdbc.query(SELECT_CATEGORIES, rowMapperOfCategoryList);
	}  
    
}
