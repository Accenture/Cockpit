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

	public Integer getTeamMood() {
		return teamMood;
	}

	public Integer getTeamConfidence() {
		return teamConfidence;
	}


}
