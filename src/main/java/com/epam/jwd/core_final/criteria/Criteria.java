package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.BaseEntity;

/**
 * Should be a builder for {@link BaseEntity} fields
 */
public abstract class Criteria<T extends BaseEntity> {

    protected Long id;
    protected String objectName;
    protected Criteria() {
    }
    protected static abstract class Builder
            <A extends Criteria, B extends Builder<A, B>> {
        protected A object;
        protected B thisObject;
        protected abstract A createObject();
        protected abstract B thisObject();
        public Builder() {
            object = createObject();
            thisObject = thisObject();
        }
        public B setObjectId(Long id) {
            object.id = id;
            return thisObject;
        }
        public B setObjectName(String name) {
            object.objectName = name;
            return thisObject;
        }

        public A build() {
            return object;
        }
    }

    public Long getId() {
        return id;
    }

    public String getObjectName() {
        return objectName;
    }
}
