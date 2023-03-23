package com.yunseojin.MyLittleHomepage.v2.domain.contract.query.repository;

import com.yunseojin.MyLittleHomepage.v2.domain.contract.query.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReadOnlyRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

    @Override
    default <S extends T> S save(S entity) {
        throw new UnsupportedOperationException(
                "save operation is not supported in read-only mode.");
    }

    @Override
    default void delete(T entity) {
        throw new UnsupportedOperationException(
                "delete operation is not supported in read-only mode.");
    }

    @Override
    default void deleteAll(Iterable<? extends T> entities) {
        throw new UnsupportedOperationException(
                "delete operation is not supported in read-only mode.");
    }
}