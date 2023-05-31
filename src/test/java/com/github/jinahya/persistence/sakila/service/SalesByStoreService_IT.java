package com.github.jinahya.persistence.sakila.service;

import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@AddBeanClasses({SalesByStoreService.class})
class SalesByStoreService_IT
        extends ___PersistenceServiceIT<SalesByStoreService> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    SalesByStoreService_IT() {
        super(SalesByStoreService.class);
    }

    @DisplayName("findAll")
    @Nested
    class FindAllTest {

        @DisplayName("findAll(null)")
        @Test
        void __MaxResultsNull() {
            final var list = applyServiceInstance(s -> s.findAll(null));
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull();
        }

        @DisplayName("findAll(!null)")
        @Test
        void __() {
            final int maxResults = ThreadLocalRandom.current().nextInt(1, 8);
            final var list = applyServiceInstance(s -> s.findAll(maxResults));
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .hasSizeLessThanOrEqualTo(maxResults);
        }
    }
}
