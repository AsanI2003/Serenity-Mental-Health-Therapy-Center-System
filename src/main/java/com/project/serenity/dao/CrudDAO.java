package com.project.serenity.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO <T> extends SuperDAO{
    public boolean save(T Dto) throws SQLException;

    public boolean update(T Dto) throws SQLException ;

    public boolean delete(int Id) throws SQLException ;

    public T get(int Id) throws SQLException ;


    public List<T> getAll() throws SQLException ;
}
