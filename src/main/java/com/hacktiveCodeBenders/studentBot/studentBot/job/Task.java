package com.hacktiveCodeBenders.studentBot.studentBot.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.hacktiveCodeBenders.studentBot.studentBot.controller.TaskController;

public class Task  extends QuartzJobBean{
	 private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		 JobDataMap jobDataMap = context.getMergedJobDataMap();
		 String task = jobDataMap.getString("Task"); 
		 logger.info("Hey, Remember to do your task "+ task);
	}
}
