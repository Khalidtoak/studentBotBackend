package com.hacktiveCodeBenders.studentBot.studentBot.controller;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import javax.validation.Valid;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hacktiveCodeBenders.studentBot.studentBot.job.Task;
import com.hacktiveCodeBenders.studentBot.studentBot.payloads.TaskSchedulerResponse;
import com.hacktiveCodeBenders.studentBot.studentBot.payloads.TaskShedulerRequest;

@RestController
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private Scheduler scheduler;

    @PostMapping("/scheduleTask")
    public ResponseEntity<TaskSchedulerResponse> scheduleTask(@Valid @RequestBody TaskShedulerRequest scheduleTaskRequest) {
        try {
        	ZonedDateTime dateTime = ZonedDateTime.parse(scheduleTaskRequest.getTimeStamp());
            if(dateTime.isBefore(ZonedDateTime.now())) {
                TaskSchedulerResponse scheduleEmailResponse = new TaskSchedulerResponse(false,
                        "dateTime must be after current time");
                return ResponseEntity.badRequest().body(scheduleEmailResponse);
            }

            JobDetail jobDetail = buildJobDetail(scheduleTaskRequest);
            Trigger trigger = buildJobTrigger(jobDetail, dateTime);
            scheduler.scheduleJob(jobDetail, trigger);

            TaskSchedulerResponse scheduleEmailResponse = new TaskSchedulerResponse(true,
                    jobDetail.getKey().getName(), jobDetail.getKey().getGroup(), "Task Scheduled Successfully!");
            return ResponseEntity.ok(scheduleEmailResponse);
        } catch (SchedulerException ex) {
            logger.error("Error scheduling email", ex);

            TaskSchedulerResponse scheduleEmailResponse = new TaskSchedulerResponse(false,
                    "Error scheduling email. Please try later!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(scheduleEmailResponse);
        }
    }

    private JobDetail buildJobDetail(TaskShedulerRequest scheduleTaskRequest) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("Task", scheduleTaskRequest.getTask());

        return JobBuilder.newJob(Task.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                .withDescription("Send Email Job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "email-triggers")
                .withDescription("Send Email Trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }
}
