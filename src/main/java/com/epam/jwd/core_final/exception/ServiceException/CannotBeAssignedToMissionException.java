package com.epam.jwd.core_final.exception.ServiceException;

public class CannotBeAssignedToMissionException extends ServiceException {
    Object member;

    public CannotBeAssignedToMissionException (String ex, Object member) {
        super(ex);
        this.member = member;
    }

    @Override
    public String getMessage() {
        return super.getMessage() +
                "\nobject :" + member.toString();
    }
}
