package com.epam.jwd.core_final.exception.ServiceException;

public class CannotUpdateException extends ServiceException {
    String name;

    public CannotUpdateException (String ex, String name) {
        super(ex);
        this.name = name;
    }

    @Override
    public String getMessage() {
        return super.getMessage() +
                "\nobject name:" + name;
    }
}
