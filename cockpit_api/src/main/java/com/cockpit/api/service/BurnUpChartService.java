package com.cockpit.api.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dao.Mvp;
import com.cockpit.api.model.dto.BurnUpChartDto;
import com.cockpit.api.model.dto.MvpDTO;

import com.cockpit.api.model.dao.Sprint;

@Service
public class BurnUpChartService {
	
	public static final int SPRINTNUMBER = 8;

	private ModelMapper modelMapper = new ModelMapper();

	@Autowired
	private UserStoryService userStoryService;

	@Autowired
	private SprintService sprintService;
	@Autowired
	private MvpService mvpService;

	private int totalUSNumber = 0;

	public List<BurnUpChartDto> getChartData(Long id) throws ResourceNotFoundException {
		List<BurnUpChartDto> chartDataList = new ArrayList<>();
		MvpDTO mvpDto = mvpService.findMvpById(id);
		Mvp mvp = modelMapper.map(mvpDto, Mvp.class);

		for (int sprintNumber = 0; sprintNumber < SPRINTNUMBER; sprintNumber++) {
			BurnUpChartDto chart = new BurnUpChartDto();
			setExpected(chart, sprintNumber, mvp);
			setTotalStories(chart, sprintNumber, mvp);
			setUsClosed(chart, sprintNumber, mvp);
			chartDataList.add(chart);
		}
		return chartDataList;

	}

	private void setUsClosed(BurnUpChartDto chart, int sprintNumber, Mvp mvp) {
		Integer numberOfUsClosed = userStoryService.findSumOfUsClosedForSprint(mvp, sprintNumber);

		if (sprintNumber <= sprintService.findSprintNumberForADate(mvp, Calendar.getInstance().getTime())) {
			chart.setUsClosed(numberOfUsClosed);

		}
	}

	private void setTotalStories(BurnUpChartDto chart, int sprintNumber, Mvp mvp) {
		Sprint currentSprint = sprintService.findByMvpAndSprintNumber(mvp, sprintNumber);
		if (currentSprint != null) {
			chart.setTotalStories(currentSprint.getTotalNbUs());
		}
	}

	private void setExpected(BurnUpChartDto chart, int sprintNumber, Mvp mvp) {
		Sprint sprint = sprintService.findByMvpAndSprintNumber(mvp, sprintNumber);
		int actualSprintStories = userStoryService.getMaxNumberOfStoriesForADateOfAnMvp(sprint, mvp);
		totalUSNumber = totalUSNumber + actualSprintStories;
		if (actualSprintStories != 0) {
			chart.setExpectedUsClosed(totalUSNumber);

		} else {
			chart.setExpectedUsClosed(null);
		}
	}

	private void setProjection(BurnUpChartDto chart, int sprintNumber, Mvp mvp) {

	}
}
