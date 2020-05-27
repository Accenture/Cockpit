package cockpit.mvpinfos.service;

import cockpit.cockpitcore.domaine.db.Mvp;
import cockpit.cockpitcore.domaine.db.Sprint;
import cockpit.cockpitcore.domaine.dto.ChartData;
import cockpit.cockpitcore.repository.MvpRepository;
import cockpit.cockpitcore.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class BurnUpService {

	@Autowired
	private UserStoryService userStoryService;

	@Autowired
	private SprintService sprintService;

	@Autowired
	private SprintRepository sprintRepository;
	@Autowired
	private MvpRepository mvpRepository;

	public List<ChartData> getChartData(String jiraProjectKey) {
		List<ChartData> chartDataList = new ArrayList<>();
		double projection = 0;
		int previousTotalStories = 0;
		int actualSprintStories = 0;
		int totalUSNumber = 0;
		int taux = 0;
		long lastNbUsClosed=0;
		int iteration=1;
		Mvp mvp = mvpRepository.findById(jiraProjectKey).orElse(null);
		for (int sprintNumber = 0; sprintNumber < mvp.getNbSprint(); sprintNumber++) {
			ChartData chartData = new ChartData();
			Long numberOfUsClosed = userStoryService.findSumOfUsClosedForSprint(mvp, sprintNumber);

			// If sprint is before Today, we display it in GREEN in Chart
			if (sprintNumber < sprintService.findSprintNumberForADate(mvp, Calendar.getInstance().getTime())) {
				chartData.setUsClosed(numberOfUsClosed);
				// If date is today, Then we add both data to have a continuous
				// line in the chart
			} else if (sprintNumber == sprintService.findSprintNumberForADate(mvp, Calendar.getInstance().getTime())) {
				chartData.setUsClosed(numberOfUsClosed);
				// chartData.setProjectionUsClosed(numberOfUsClosed);
				// projection = numberOfUsClosed;
			}
			// else {
			// Else we calculate all sprints velocity for projection
			if (chartData.getUsClosed() != null) {
				taux = (int) (numberOfUsClosed / (sprintNumber + 1));
				lastNbUsClosed=numberOfUsClosed;
			}
			if (chartData.getUsClosed() == null) {
				projection =  numberOfUsClosed +iteration*taux;
				chartData.setProjectionUsClosed(projection);
				iteration++;
				
		}
			// }

			// Calcul code
			chartData.setSprintId(sprintNumber);
			chartData.setTotalSprints(mvp.getNbSprint());
			actualSprintStories = getTotalNumberOfStoriesForAnMVPAndASprintNumber(sprintNumber, mvp);
			if (actualSprintStories == 0) {
				actualSprintStories = previousTotalStories;
			} else {
				previousTotalStories = actualSprintStories;
			}
			totalUSNumber = totalUSNumber + actualSprintStories;
			chartData.setExpectedUsClosed(totalUSNumber);
			Sprint currentSprint = sprintRepository.findByMvpAndSprintNumber(mvp, sprintNumber);
			if (currentSprint != null && currentSprint.getTotalNbUs() != null) {
				chartData.setTotalStories(currentSprint.getTotalNbUs());
			}
			chartDataList.add(chartData);
		}
		for (ChartData chart : chartDataList)
			if (chart.getUsClosed() == null) {
				chartDataList.get(chartDataList.indexOf(chart) - 1).setProjectionUsClosed(
						(double) chartDataList.get(chartDataList.indexOf(chart) - 1).getUsClosed());
				
				break;
			}
		return chartDataList;
	}

	private int getTotalNumberOfStoriesForAnMVPAndASprintNumber(int sprintNumber, Mvp mvp) {
		Sprint sprint = sprintService.findSprintByMvpBySprintNumber(mvp, sprintNumber);
		return userStoryService.getMaxNumberOfStoriesForADateOfAnMvp(sprint, mvp);
	}
}
