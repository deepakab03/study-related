package com.deepak.questions.int_q.sql;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlConfig.ErrorMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.deepak.questions.int_q.config.DerbyClientConfig;

/**
 * Given the following data definition, for the given rectangles, 
 * write a query that selects each distinct value of area 
 * and the number of rectangles having that area
 * 
 * DOESN'T WORK IN APACHE DERBY DB
 * 
 * @author Deepak Abraham
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=DerbyClientConfig.class)
@Sql(scripts="/sql/ddl/rectangle_area_q.sql", config=@SqlConfig (errorMode=ErrorMode.IGNORE_FAILED_DROPS))
@Sql("/sql/dml/rectangle_area_data.sql")
public class RectangleAreaSqlIT {

    @Resource private NamedParameterJdbcTemplate derbyDbJdbcTemplate;
    
    @Test public void testQuery() {
        String query = "SELECT (width * height) as area, count(*) " + 
                "FROM rectangles " + 
                "GROUP BY area " +
                "ORDER BY area asc ";
        List<Map<String, Object>> dataList = derbyDbJdbcTemplate.getJdbcOperations().queryForList(query);
        
        assertThat(dataList, hasSize(8));
        assertThat(dataList.get(3).get("area"), is(equalTo(500)));
        assertThat(dataList.get(3).get("count"), is(equalTo(4)));
    }
}
