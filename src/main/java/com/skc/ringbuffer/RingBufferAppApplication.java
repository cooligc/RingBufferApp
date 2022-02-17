package com.skc.ringbuffer;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

@SpringBootApplication
public class RingBufferAppApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RingBufferAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


		ThreadFactory threadFactory = DaemonThreadFactory.INSTANCE;

		WaitStrategy waitStrategy = new BusySpinWaitStrategy();
		Disruptor<AppEvent> disruptor
				= new Disruptor<>(
				AppEvent.EVENT_FACTORY,
				8,
				threadFactory,
				ProducerType.SINGLE,
				waitStrategy);
		disruptor.handleEventsWith(new SingleEventConsumer().getEventHandler());

		RingBuffer<AppEvent> ringBuffer = disruptor.start();


		for (int eventCount = 0; eventCount < 32; eventCount++) {

			Map<String,String> data = new HashMap<>();
			data.put("hello","hi"+eventCount);
			data.put("counter", eventCount+"");

			long sequenceId = ringBuffer.next();
			AppEvent valueEvent = ringBuffer.get(sequenceId);
			valueEvent.setValues(data);
			ringBuffer.publish(sequenceId);
			System.out.println(Thread.currentThread().getName() + "\tEvent Published "+valueEvent.getValues());
		}


	}
}
