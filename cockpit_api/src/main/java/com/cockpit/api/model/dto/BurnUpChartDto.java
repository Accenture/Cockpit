package com.cockpit.api.model.dto;

public class BurnUpChartDto {
	
	private int sprintId;

	private Integer usClosed;

	private Double projectionUsClosed;

	private Integer totalStories;

	private Integer expectedUsClosed;

	public BurnUpChartDto() {
		 // Empty constructor
	}

	public int getSprintId() {
		return sprintId;
	}

	public void setSprintId(int sprintId) {
		this.sprintId = sprintId;
	}

	public Integer getUsClosed() {
		return usClosed;
	}

	public void setUsClosed(Integer usClosed) {
		this.usClosed = usClosed;
	}

	public Double getProjectionUsClosed() {
		return projectionUsClosed;
	}

	public void setProjectionUsClosed(Double projectionUsClosed) {
		this.projectionUsClosed = projectionUsClosed;
	}

	public Integer getTotalStories() {
		return totalStories;
	}

	public void setTotalStories(Integer totalStories) {
		this.totalStories = totalStories;
	}


	public Integer getExpectedUsClosed() {
		return expectedUsClosed;
	}

	public void setExpectedUsClosed(Integer expectedUsClosed) {
		this.expectedUsClosed = expectedUsClosed;
	}

}
