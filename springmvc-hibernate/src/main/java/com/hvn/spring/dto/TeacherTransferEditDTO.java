package com.hvn.spring.dto;



public class TeacherTransferEditDTO {
	
	/**
	 * Left
	 */
	private long leftUniversityId;
	
	private long leftDepartmentId;
	
	private long leftTeacherId;
	
	
	/**
	 * Right
	 */
	private long rightUniversityId;
	
	private long rightDepartmentId;
	
	private long rightTeacherId;
	
	public long getLeftUniversityId() {
		return leftUniversityId;
	}

	public void setLeftUniversityId(long leftUniversityId) {
		this.leftUniversityId = leftUniversityId;
	}

	public long getLeftDepartmentId() {
		return leftDepartmentId;
	}

	public void setLeftDepartmentId(long leftDepartmentId) {
		this.leftDepartmentId = leftDepartmentId;
	}

	public long getLeftTeacherId() {
		return leftTeacherId;
	}

	public void setLeftTeacherId(long leftTeacherId) {
		this.leftTeacherId = leftTeacherId;
	}

	public long getRightUniversityId() {
		return rightUniversityId;
	}

	public void setRightUniversityId(long rightUniversityId) {
		this.rightUniversityId = rightUniversityId;
	}

	public long getRightDepartmentId() {
		return rightDepartmentId;
	}

	public void setRightDepartmentId(long rightDepartmentId) {
		this.rightDepartmentId = rightDepartmentId;
	}

	public long getRightTeacherId() {
		return rightTeacherId;
	}

	public void setRightTeacherId(long rightTeacherId) {
		this.rightTeacherId = rightTeacherId;
	}
}

