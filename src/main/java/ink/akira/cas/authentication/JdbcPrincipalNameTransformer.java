package ink.akira.cas.authentication;

import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.authentication.handler.PrincipalNameTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class JdbcPrincipalNameTransformer implements PrincipalNameTransformer {
    private static final Logger logger = LoggerFactory.getLogger(JdbcPrincipalNameTransformer.class);

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private String sql;

    @Override
    public String transform(String formUserId) {
        int paramCount = StringUtils.countMatches(sql, "?");
        Object[] params = new Object[paramCount];
        for (int i = 0; i < paramCount; i++) {
            params[i] = formUserId;
        }
        try {
            return jdbcTemplate.queryForObject(this.sql, params, String.class);
        } catch (EmptyResultDataAccessException e) {
            return formUserId;
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.error("Found multiple result for user login_name");
            return formUserId;
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    @Required
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    public String getSql() {
        return sql;
    }

    @Required
    public void setSql(String sql) {
        this.sql = sql;
    }
}
