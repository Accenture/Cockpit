package com.cockpit.api.model.dto;

public class ObeyaDTO {

	private Integer teamMotivation;

	private Integer teamMood;

	private Integer teamConfidence;

	public ObeyaDTO(Integer teamMotivation, Integer teamMood, Integer teamConfidence) {
		this.teamMotivation = teamMotivation;
		this.teamMood = teamMood;
		this.teamConfidence = teamConfidence;
	}

	public ObeyaDTO() {

	}

	public Integer getTeamMotivation() {
		return teamMotivation;
	}

	public void setTeamMotivation(Integer teamMotivation) {
		this.teamMotivation = teamMotivation;
	}

	public Integer getTeamMood() {
		return teamMood;
	}

	public void setTeamMood(Integer teamMood) {
		this.teamMood = teamMood;
	}

	public Integer getTeamConfidence() {
		return teamConfidence;
	}

	public void setTeamConfidence(Integer teamConfidence) {
		this.teamConfidence = teamConfidence;
	}
}
