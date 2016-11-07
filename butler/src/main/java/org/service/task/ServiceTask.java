package org.service.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ServiceTask {
	Logger log = LoggerFactory.getLogger(ServiceTask.class);

	@Scheduled(cron = "0 0/1 * * * ?")
	public void Test() {
		log.error("这是日志内容！！！！");
	}
}
