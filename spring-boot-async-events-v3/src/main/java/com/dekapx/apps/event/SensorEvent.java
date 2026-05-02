package com.dekapx.apps.event;

import org.springframework.context.ApplicationEvent;

public class SensorEvent<T> extends ApplicationEvent {
    private T model;

    public SensorEvent(Object source, T model) {
        super(source);
        this.model = model;
    }

    public T getModel() {
        return model;
    }
}