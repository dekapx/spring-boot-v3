package com.dekapx.apps.event;

import org.springframework.context.ApplicationEvent;

public class OrderEvent<T> extends ApplicationEvent {
    private T model;

    public OrderEvent(Object source, T model) {
        super(source);
        this.model = model;
    }

    public T getModel() {
        return model;
    }
}
