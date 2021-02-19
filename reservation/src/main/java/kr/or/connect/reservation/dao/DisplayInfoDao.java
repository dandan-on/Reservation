package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.DisplayInfoDaoSqls.SELECT_AVG_SCORE_OF_COMMENTS_BY_PRODUCT_ID;
import static kr.or.connect.reservation.dao.DisplayInfoDaoSqls.SELECT_COUNT_OF_ALL_DISPLAY_INFOS;
import static kr.or.connect.reservation.dao.DisplayInfoDaoSqls.SELECT_COUNT_OF_DISPLAY_INFOS_IN_CATEGORY;
import static kr.or.connect.reservation.dao.DisplayInfoDaoSqls.SELECT_DISPLAY_INFOS_IN_ALL_CATEGORIES;
import static kr.or.connect.reservation.dao.DisplayInfoDaoSqls.SELECT_DISPLAY_INFOS_IN_CATEGORY;
import static kr.or.connect.reservation.dao.DisplayInfoDaoSqls.SELECT_DISPLAY_INFO_IMAGES_BY_DISPLAY_INFO_ID;
import static kr.or.connect.reservation.dao.DisplayInfoDaoSqls.SELECT_PRODUCT_IMAGES_BY_PRODUCT_ID;
import static kr.or.connect.reservation.dao.DisplayInfoDaoSqls.SELECT_PRODUCT_PRICES_BY_PRODUCT_ID;
import static kr.or.connect.reservation.dao.DisplayInfoDaoSqls.SELECT_DISPLAY_INFO_BY_DISPLAY_INFO_ID;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.DisplayInfoImagesResult;
import kr.or.connect.reservation.dto.DisplayInfosResult;
import kr.or.connect.reservation.dto.ProductImagesResult;
import kr.or.connect.reservation.dto.ProductPricesResult;

@Repository
public class DisplayInfoDao {

	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<DisplayInfosResult> rowMapperOfDisplayInfoList = BeanPropertyRowMapper.newInstance(DisplayInfosResult.class);
	private RowMapper<ProductImagesResult> rowMapperOfProductImageList = BeanPropertyRowMapper.newInstance(ProductImagesResult.class); 
	private RowMapper<DisplayInfoImagesResult> rowMapperOfDisplayInfoImageList = BeanPropertyRowMapper.newInstance(DisplayInfoImagesResult.class);
	private RowMapper<ProductPricesResult> rowMapperOfProductPriceList = BeanPropertyRowMapper.newInstance(ProductPricesResult.class); 
	
    public DisplayInfoDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }
    
    //전시 상품 목록 구하기
    public List<DisplayInfosResult> selectDisplayInfosInCategory(Long categoryId, int start) {
    	Map<String,Object> params = new HashMap<>();
    	params.put("start", start);
    	params.put("categoryId", categoryId);
    	
	    return jdbc.query(SELECT_DISPLAY_INFOS_IN_CATEGORY, params, rowMapperOfDisplayInfoList);
	}
    
    public List<DisplayInfosResult> selectDisplayInfosInAllCategories(int start) {
    	Map<String,Object> params = new HashMap<>();
    	params.put("start", start);
    	
	    return jdbc.query(SELECT_DISPLAY_INFOS_IN_ALL_CATEGORIES, params, rowMapperOfDisplayInfoList);
	}
    
    public int selectCountOfDisplayInfosInCategory(Long categoryId) {
    	Map<String, Object> params = Collections.singletonMap("categoryId", categoryId);
    	return jdbc.queryForObject(SELECT_COUNT_OF_DISPLAY_INFOS_IN_CATEGORY, params, Integer.class);
    }
    
    public int selectCountOfAllDisplayInfos() {
    	return jdbc.queryForObject(SELECT_COUNT_OF_ALL_DISPLAY_INFOS, Collections.emptyMap(), Integer.class);
    }
	
	//전시 정보 구하기
    public List<DisplayInfosResult> selectDisplayInfo(Long displayInfoId) {
    	Map<String, Object> params = Collections.singletonMap("displayInfoId", displayInfoId);
	    return jdbc.query(SELECT_DISPLAY_INFO_BY_DISPLAY_INFO_ID, params, rowMapperOfDisplayInfoList);	    
	} 
    
    public List<ProductImagesResult> selectProductImages(Long productId) {
    	Map<String, Object> params = Collections.singletonMap("productId", productId);
	    return jdbc.query(SELECT_PRODUCT_IMAGES_BY_PRODUCT_ID, params, rowMapperOfProductImageList);	    
	} 
    
    public List<DisplayInfoImagesResult> selectDisplayInfoImages(Long displayInfoId) {
    	Map<String, Object> params = Collections.singletonMap("displayInfoId", displayInfoId);
	    return jdbc.query(SELECT_DISPLAY_INFO_IMAGES_BY_DISPLAY_INFO_ID, params, rowMapperOfDisplayInfoImageList);	    
	} 
    
    public int selectAvgScoreOfComments(Long productId) {
    	Map<String, Object> params = Collections.singletonMap("productId", productId);
    	return jdbc.queryForObject(SELECT_AVG_SCORE_OF_COMMENTS_BY_PRODUCT_ID, params, Integer.class);
    }
    
    public List<ProductPricesResult> selectProductPrices(Long productId) {
    	Map<String, Object> params = Collections.singletonMap("productId", productId);
	    return jdbc.query(SELECT_PRODUCT_PRICES_BY_PRODUCT_ID, params, rowMapperOfProductPriceList);	    
	} 
    
}
