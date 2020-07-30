package com.cockpit.api.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Mvp;
import com.cockpit.api.model.dto.BurnUpChartDTO;
import com.cockpit.api.model.dto.MvpDTO;

import com.cockpit.api.model.dao.Sprint;

@Service
public class BurnUpChartService {

	private JiraService jiraService;

	private UserStoryService userStoryService;

	private SprintService sprintService;

	private MvpService mvpService;

	private ModelMapper modelMapper = new ModelMapper();


	@Autowired
	public BurnUpChartService(
			JiraService jiraService,
			UserStoryService userStoryService,
			SprintService sprintService,
			MvpService mvpService

	){
		this.jiraService = jiraService;
		this.userStoryService = userStoryService;
		this.sprintService = sprintService;
		this.mvpService = mvpService;
	}

	public List<BurnUpChartDTO> getChartData(Long id) throws ResourceNotFoundException {
		List<BurnUpChartDTO> chartDataList = new ArrayList<>();
		MvpDTO mvpDto = mvpService.findMvpById(id);
		Mvp mvp = modelMapper.map(mvpDto, Mvp.class);
		Jira jira = jiraService.findByMvp(mvp);
		double rate = 0;
		long lastNbUsClosed = 0;
		int iteration = 1;
		double projection = 0;
		int totalUSNumber = 0;
		int maxSprintNumber = 12;
		Integer numberOfUsClosed = 0;
		if (mvp.getSprintNumber() != null) {
			maxSprintNumber = mvp.getSprintNumber();
		}
		for (int sprintNumber = 1; sprintNumber <= maxSprintNumber; sprintNumber++) {
			BurnUpChartDTO chart = new BurnUpChartDTO();

			chart.setSprintId(sprintNumber);
			Sprint thisSprint = sprintService.findByJiraAndSprintNumber(jira, sprintNumber);
			int actualSprintStories = userStoryService.getNumberOfStoriesInOneSprint(thisSprint, jira);
			numberOfUsClosed = numberOfUsClosed + thisSprint.getCompletedUsNumber();
			totalUSNumber = totalUSNumber + actualSprintStories;

			setTotalStories(chart, sprintNumber, jira);
			setUsClosed(chart, sprintNumber, jira, numberOfUsClosed);
			setExpected(chart, totalUSNumber, sprintNumber, jira, numberOfUsClosed, thisSprint);

			// Set projection
			if (chart.getUsClosed() != null) {
				rate = ((double) numberOfUsClosed / (sprintNumber + 1));
				lastNbUsClosed = numberOfUsClosed;
			} else {
				projection = lastNbUsClosed + iteration * rate;
				chart.setProjectionUsClosed(projection);
				iteration++;
			}
			if (sprintNumber == sprintService.findSprintNumberForADate(jira, Calendar.getInstance().getTime())) {
				chart.setProjectionUsClosed((double) lastNbUsClosed);
			}

			chartDataList.add(chart);
		}
		return chartDataList;

	}

	private void setUsClosed(BurnUpChartDTO chart, int sprintNumber, Jira jira, int numberOfUsClosed) {
		int sprintN = sprintService.findSprintNumberForADate(jira, Calendar.getInstance().getTime());
		if (sprintNumber <= sprintN) {
			chart.setUsClosed(numberOfUsClosed);

		}
	}

	private void setTotalStories(BurnUpChartDTO chart, int sprintNumber, Jira jira) {

		Sprint currentSprint = sprintService.findByJiraAndSprintNumber(jira, sprintNumber);
		if (currentSprint != null) {
			chart.setTotalStories(currentSprint.getTotalNbUs());
		}
	}

	private void setExpected(BurnUpChartDTO chart, int totalUSNumber, int sprintNumber,
							 Jira jira, int numberOfUsClosed, Sprint sprint) {
		if (jira.getCurrentSprint() != null && sprintNumber <= jira.getCurrentSprint()) {
			chart.setExpectedUsClosed(numberOfUsClosed + sprint.getNotCompletedUsNumber());
		} else if (sprint != null) {
			chart.setExpectedUsClosed(totalUSNumber);
		} else {
			chart.setExpectedUsClosed(null);
		}
	}
}
