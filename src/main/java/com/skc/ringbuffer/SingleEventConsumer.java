package com.skc.ringbuffer;

import com.lmax.disruptor.EventHandler;

import java.util.Map;

public class SingleEventConsumer implements EventHandler<AppEvent>{

    /*public EventHandler<AppEvent>[] getEventHandler() {
        EventHandler<AppEvent> eventHandler
                = (event, sequence, endOfBatch)
                -> print(event.getValues(), sequence,endOfBatch);
        return new EventHandler[] { eventHandler };
    }*/

    private void print(Map<String, String> values, long sequenceId, boolean endOfBatch) {
        System.out.println(Thread.currentThread().getName() + "\tEvent data "+values+" \t sequenceId = "+sequenceId);
    }

    @Override
    public void onEvent(AppEvent event, long sequence, boolean endOfBatch) throws Exception {
        print(event.getValues(), sequence,endOfBatch);
    }
}
