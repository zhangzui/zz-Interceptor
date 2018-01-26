package com.zz.schedule.listence.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 事件处理器 
 *
 */
public class EventProcessor {

	/**
	 * 日志输出
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(EventProcessor.class);

	public final static EventProcessor INSTANCE = new EventProcessor();
    /**
     * 事件监听队列
     */
    private final BlockingQueue<Event> events = new LinkedBlockingQueue<Event>();
    /**
     * 线程池
     */
    private ExecutorService eventPool = Executors.newFixedThreadPool(5);

    private Map<Class<? extends Event>,EventListener> cacheEventListener = new HashMap<Class<? extends Event>, EventListener>();
    
    private EventProcessor() { }

    public static EventProcessor getInstance() {
        return INSTANCE;
    }

	/**
	 * 增加事件
	 * @param e
	 */
	public void addEvent(Event e) {
        events.add(e);
    }

	/**
	 * 消费者
	 */
	class Consumer implements Runnable {

		private Event event;
		private EventListener eventListener;

		public Consumer(Event event) {
			this.event = event;
			try {
				if(cacheEventListener.get(event.getClass()) == null){
					EventListener listener = event.bindEventListener();
					cacheEventListener.put(event.getClass(),listener);
					this.eventListener = listener;
				}else {
					this.eventListener = event.bindEventListener();
				}
			} catch (Exception e) {
				LOGGER.error("EventProcessor Consumer init error", e);
			}
		}

		@Override
		public void run() {
			this.eventListener.handle(event);
		}
	}

    /**
     * 执行事件
     */
    public void startEvent() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                	Event evt = null;
                    try {
                        evt = events.take();
                    } catch (InterruptedException e) {
                        LOGGER.error("event take error", e);
                    }
                    if (null == evt) {
                    	return;
					}
                    try {
                        eventPool.execute(new Consumer(evt));
                    } catch (Exception e) {
                        LOGGER.error("event execute error", e);
                    }
                }
            }
        }.start();
    }
}
