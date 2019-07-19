package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;

public class TaskDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = -4713228468785959182L;

	private String action;
	private String params;
	private String reasonTaskCreated;
	private String reasonTaskFailed;
	private String status;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getReasonTaskCreated() {
		return reasonTaskCreated;
	}

	public void setReasonTaskCreated(String reasonTaskCreated) {
		this.reasonTaskCreated = reasonTaskCreated;
	}

	public String getReasonTaskFailed() {
		return reasonTaskFailed;
	}

	public void setReasonTaskFailed(String reasonTaskFailed) {
		this.reasonTaskFailed = reasonTaskFailed;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "TaskDto{" +
				"action='" + action + '\'' +
				", params='" + params + '\'' +
				", reasonTaskCreated='" + reasonTaskCreated + '\'' +
				", reasonTaskFailed='" + reasonTaskFailed + '\'' +
				", status='" + status + '\'' +
				'}';
	}
}