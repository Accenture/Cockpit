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
		int actualSprintStories = 0;
		int totalUSNumber = 0;
		double taux = 0.0;
		long lastNbUsClosed = 0;
		int iteration = 1;
		Mvp mvp = mvpRepository.findById(jiraProjectKey).orElse(null);
		for (int sprintNumber = 0; sprintNumber < mvp.getNbSprint(); sprintNumber++) {
			ChartData chartData = new ChartData();
			Long numberOfUsClosed = userStoryService.findSumOfUsClosedForSprint(mvp, sprintNumber);

			if (sprintNumber < sprintService.findSprintNumberForADate(mvp, Calendar.getInstance().getTime())) {
				chartData.setUsClosed(numberOfUsClosed);

			} else if (sprintNumber == sprintService.findSprintNumberForADate(mvp, Calendar.getInstance().getTime())) {
				chartData.setUsClosed(numberOfUsClosed);
			}

			if (chartData.getUsClosed() != null) {
				taux = (numberOfUsClosed / (sprintNumber + 1));
				lastNbUsClosed = numberOfUsClosed;
			}
			if (chartData.getUsClosed() == null) {
				projection = numberOfUsClosed + iteration * taux;
				chartData.setProjectionUsClosed(projection);
				iteration++;
			}

			// Calcul code
			chartData.setSprintId(sprintNumber);
			chartData.setTotalSprints(mvp.getNbSprint());
			actualSprintStories = getTotalNumberOfStoriesForAnMVPAndASprintNumber(sprintNumber, mvp);
		
			totalUSNumber = totalUSNumber + actualSprintStories;
			if (actualSprintStories != 0) {
				chartData.setExpectedUsClosed(totalUSNumber);

			} else {
				chartData.setExpectedUsClosed(null);
			}

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
