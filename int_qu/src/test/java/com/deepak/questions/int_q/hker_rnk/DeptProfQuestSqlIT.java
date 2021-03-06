package com.deepak.questions.int_q.hker_rnk;

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
 * Find which department has highest average salary and print the name and salary of that department
 * 
 * @author Deepak Abraham
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=DerbyClientConfig.class)
@Sql(scripts= {"/sql/ddl/prof_dept_q.sql"}, config=@SqlConfig (errorMode=ErrorMode.IGNORE_FAILED_DROPS))
@Sql("/sql/dml/prof_dept_q_data.sql")
public class DeptProfQuestSqlIT {

    @Resource private NamedParameterJdbcTemplate derbyDbJdbcTemplate;
    
    public DeptProfQuestSqlIT() {
    }
    
    @Test public void testQuery() {
        String query = "select d.name, avg(p.salary) as avg_sal from prof p, dept d "
                + "where p.dept_id = d.id "
                + "group by d.name "
                + "order by avg_sal desc "
                + "FETCH FIRST 1 ROWS ONLY";
        List<Map<String, Object>> dataList = derbyDbJdbcTemplate.getJdbcOperations().queryForList(query);
        
        assertThat(dataList, hasSize(1));
        assertThat(dataList.get(0).get("name"), is(equalTo("INDUSTRIAL")));
        assertThat(dataList.get(0).get("avg_sal"), is(equalTo(4000)));
    }
}
