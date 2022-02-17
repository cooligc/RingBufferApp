package com.skc.ringbuffer;

import com.lmax.disruptor.EventHandler;

import java.util.Map;

public class SecondEventHandler implements EventHandler<AppEvent>{

    /*public static EventHandler<AppEvent>[] getEventHandler() {
        EventHandler<AppEvent> eventHandler
                = (event, sequence, endOfBatch)
                -> print(event.getValues(), sequence,endOfBatch);
        return new EventHandler[] { eventHandler };
    }*/

    private static void print(Map<String, String> values, long sequenceId, boolean endOfBatch) {
        System.out.println(Thread.currentThread().getName() + "\t 2nd \t Event data "+values+" \t sequenceId = "+sequenceId);
    }

    @Override
    public void onEvent(AppEvent appEvent, long sequence, boolean endOfBatch) throws Exception {
        print(appEvent.getValues(), sequence,endOfBatch);
    }
}
