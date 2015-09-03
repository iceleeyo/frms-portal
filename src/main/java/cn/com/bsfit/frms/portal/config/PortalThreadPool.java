package cn.com.bsfit.frms.portal.config;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Portal线程池配置
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@Configuration
@EnableConfigurationProperties
public class PortalThreadPool {

	@Value("${corePoolSize:1}")
	private int corePoolSize;// 线程池维护线程的最少数量
	@Value("${maxPoolSize:50}")
	private int maxPoolSize;// 线程池维护线程的最大数量
	@Value("${queueCapacity:1000}")
	private int queueCapacity;// 线程池所使用的缓冲队列
	@Value("${keepAliveSeconds:300}")
	private int keepAliveSeconds;// 线程池维护线程所允许的空闲时间
	
	@Bean
	public RejectedExecutionHandler rejectedExecutionHandler() {
		CallerRunsPolicy callerRunsPolicy = new CallerRunsPolicy();
		return callerRunsPolicy;
	}
	
	@Autowired
	private RejectedExecutionHandler rejectedExecutionHandler;// 线程池对拒绝任务(无线程可用)的处理策略
	
	@Bean
	public TaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setKeepAliveSeconds(keepAliveSeconds);
		executor.setRejectedExecutionHandler(rejectedExecutionHandler);
		return executor;
	}

    @Autowired
	private TaskExecutor taskExecutor;

	public void printMessages() {
		for (int i = 0; i < 25; i++) {
			taskExecutor.execute(new MessagePrinterTask("Message" + i));
		}
	}

	private class MessagePrinterTask implements Runnable {
		private String message;

		public MessagePrinterTask(String message) {
			this.message = message;
		}
		
		public void run() {
			System.out.println(message);
		}
	}
}
