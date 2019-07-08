package com.hacktiveCodeBenders.studentBot.studentBot.payloads;
import org.springframework.lang.NonNull;
public class TaskShedulerRequest{
	@NonNull
   private String timeStamp;
	@NonNull
	private String task;

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
   
}
