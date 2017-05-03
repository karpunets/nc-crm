package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.DiscountDao;
import com.netcracker.crm.domain.model.Discount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.netcracker.crm.dao.impl.sql.DiscountSqlQuery.*;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 26.04.2017
 */

@Repository
public class DiscountDaoImpl implements DiscountDao {
    private static final Logger log = LoggerFactory.getLogger(DiscountDaoImpl.class);

    private SimpleJdbcInsert discountInsert;
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private DiscountExtractor discountExtractor;

    @Override
    public Long create(Discount discount) {
        if (discount.getId() != null) {
            return null;
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_DISCOUNT_TITLE, discount.getTitle())
                .addValue(PARAM_DISCOUNT_PERCENTAGE, discount.getPercentage())
                .addValue(PARAM_DISCOUNT_DESCRIPTION, discount.getDescription())
                .addValue(PARAM_DISCOUNT_ACTIVE, discount.getActive());
        Long id = discountInsert.executeAndReturnKey(params).longValue();
        discount.setId(id);
        log.info("Discount with id: " + id + " is successfully created.");
        return id;
    }

    @Override
    public Long update(Discount discount) {
        Long discountId = discount.getId();
        if (discountId == null) {
            return null;
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_DISCOUNT_ID, discountId)
                .addValue(PARAM_DISCOUNT_TITLE, discount.getTitle())
                .addValue(PARAM_DISCOUNT_PERCENTAGE, discount.getPercentage())
                .addValue(PARAM_DISCOUNT_DESCRIPTION, discount.getDescription())
                .addValue(PARAM_DISCOUNT_ACTIVE, discount.getActive());
        long affectedRows = namedJdbcTemplate.update(SQL_UPDATE_DISCOUNT, params);
        if (affectedRows == 0) {
            log.error("Discount has not been updated");
            return null;
        } else {
            log.info("Discount with id " + discountId + " was successfully updated");
            return discountId;
        }
    }

    @Override
    public Long delete(Long id) {
        if (id != null) {
            long deletedRows = namedJdbcTemplate.getJdbcOperations().update(SQL_DELETE_DISCOUNT, id);
            if (deletedRows == 0) {
                log.error("Discount has not been deleted");
                return -1L;
            } else {
                log.info("Discount with id " + id + " was successfully deleted");
                return deletedRows;
            }
        }
        return null;
    }

    @Override
    public Long delete(Discount discount) {
        if (discount != null) {
            return delete(discount.getId());
        }
        return null;
    }

    @Override
    public Discount findById(Long id) {
        log.debug("Start finding discount by id");
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_DISCOUNT_ID, id);
        List<Discount> discounts = namedJdbcTemplate.query(SQL_FIND_DISC_BY_ID, params, discountExtractor);
        Discount discount = null;
        if (discounts.size() != 0) {
            discount = discounts.get(0);
        }
        log.debug("End finding discount by id");
        return discount;
    }

    @Override
    public List<Discount> findByTitle(String title) {
        log.debug("Start finding discounts by title");
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_DISCOUNT_TITLE, "%" + title + "%");
        List<Discount> discounts = namedJdbcTemplate.query(SQL_FIND_DISC_BY_TITLE, params, discountExtractor);
        log.debug("End finding discounts by title");
        return discounts;
    }

    @Override
    public Long getCount() {
        return namedJdbcTemplate.getJdbcOperations().queryForObject(SQL_GET_DISC_COUNT, Long.class);
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.discountInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(PARAM_DISCOUNT_TABLE)
                .usingGeneratedKeyColumns(PARAM_DISCOUNT_ID);
        this.discountExtractor = new DiscountExtractor();
    }

    private static final class DiscountExtractor implements ResultSetExtractor<List<Discount>> {
        @Override
        public List<Discount> extractData(ResultSet rs) throws SQLException, DataAccessException {
            log.debug("Start extracting data");
            List<Discount> discounts = new ArrayList<>();
            while (rs.next()) {
                Discount discount = new Discount();
                discount.setId(rs.getLong(PARAM_DISCOUNT_ID));
                discount.setDescription(rs.getString(PARAM_DISCOUNT_DESCRIPTION));
                discount.setPercentage(rs.getDouble(PARAM_DISCOUNT_PERCENTAGE));
                discount.setTitle(rs.getString(PARAM_DISCOUNT_TITLE));
                discount.setActive(rs.getBoolean(PARAM_DISCOUNT_ACTIVE));
                discounts.add(discount);
            }
            log.debug("End extracting data");
            return discounts;
        }
    }
}
