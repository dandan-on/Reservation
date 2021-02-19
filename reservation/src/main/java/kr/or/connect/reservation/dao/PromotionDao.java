package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.PromotionDaoSqls.SELECT_PROMOTIONS;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.PromotionsResult;

@Repository
public class PromotionDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<PromotionsResult> rowMapperOfPromotionList = BeanPropertyRowMapper.newInstance(PromotionsResult.class);
	
    public PromotionDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }
    
    public List<PromotionsResult> selectPromotions() {
	    return jdbc.query(SELECT_PROMOTIONS, rowMapperOfPromotionList);
	}
}
