package com.krug.teste.services.exceptions;

import org.springframework.dao.DataAccessException;

import java.io.Serial;

public class DatabaseException extends DataAccessException {
    @Serial
    private static final long serialVersionUID = 1L;

    public DatabaseException(String msg) {
        super(msg);
    }
}