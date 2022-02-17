package com.skc.ringbuffer;

import com.lmax.disruptor.EventFactory;

import java.util.Map;

public class AppEvent {

    private Map<String,String> values;

    public final static EventFactory EVENT_FACTORY = () -> new AppEvent();


    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }

    public static EventFactory getEventFactory() {
        return EVENT_FACTORY;
    }
}
