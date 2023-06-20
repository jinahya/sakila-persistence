package com.github.jinahya.sakila.persistence;

import com.github.jinahya.sakila.persistence.util.ReflectionUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

@ApplicationScoped
public class ___EntityManagerProducer {

    private static final Logger log = getLogger(lookup().lookupClass());

    private static final Map<EntityManager, EntityManager> PROXIES =
            new ConcurrentHashMap<>(new WeakHashMap<>());

    @___Uncloseable
    @Produces
    EntityManager produceEntityManager() {
        final var bean = entityManagerFactory.createEntityManager();
        log.debug("producing entity manager: {}", bean);
        final var proxy = ReflectionUtils.createUnCloseableProxy(EntityManager.class, bean);
        PROXIES.put(proxy, bean);
        return proxy;
    }

    void disposeEntityManager(@Disposes @___Uncloseable final EntityManager proxy) {
        log.debug("disposing entity manager: {}", proxy);
        PROXIES.remove(proxy).close();
    }

    @___Uncloseable
    @Inject
    private EntityManagerFactory entityManagerFactory;
}
