package com.github.jinahya.sakila.persistence.service;

import com.github.jinahya.sakila.persistence.SalesByStore;
import com.github.jinahya.sakila.persistence.util.JdbcUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * A service class related to {@link SalesByStore}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public class SalesByStoreService
        extends ___PersistenceService {

    private static final Logger log = getLogger(lookup().lookupClass());

    /**
     * Creates a new instance.
     */
    SalesByStoreService() {
        super();
    }

    /**
     * Finds all entities.
     *
     * @param maxResults a value for limiting the number of results; {@code null} for an unlimited number of results.
     * @return a list of found entities.
     */
    @NotNull
    public List<@Valid @NotNull SalesByStore> findAll(@Positive final Integer maxResults) {
        final var builder = new StringBuilder("SELECT * FROM " + SalesByStore.VIEW_NAME);
        if (maxResults != null) {
            builder.append(" LIMIT ").append(maxResults);
        }
        return applyConnection(c -> {
            try (var statement = c.createStatement()) {
                try (var results = statement.executeQuery(builder.toString())) {
                    final var list = new ArrayList<SalesByStore>();
                    while (results.next()) {
                        list.add(JdbcUtils.bind(SalesByStore.class, results));
                    }
                    return list;
                }
            } catch (SQLException sqle) {
                throw new RuntimeException("failed to find", sqle);
            }
        });
    }
}
