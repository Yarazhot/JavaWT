package dao;

import entity.Entity;

import java.util.List;

public interface Dao<T extends Entity> {
    void add(T t) throws DaoException;

    T getById(int id) throws DaoException;

    List<T> getList(Filter filter) throws DaoException;

    void delete(T t) throws DaoException;
}
