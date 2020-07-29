import React, { useEffect, useState } from 'react';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import LinearProgress from '@material-ui/core/LinearProgress';
import useStyles from './styles';

export default function Obeya(props) {
  const classes = useStyles();
  const { mvp } = props;
  const [sprint, setSprint] = useState({
    teamMood: 0,
    teamMotivation: 0,
    teamCofidence: 0,
  });

  useEffect(() => {
    if (mvp.jira.sprints && mvp.jira.currentSprint) {
      const currentSprint = mvp.jira.sprints[mvp.jira.currentSprint - 1];
      if (
        currentSprint &&
        currentSprint.teamMood &&
        currentSprint.teamMotivation &&
        currentSprint.teamConfidence
      ) {
        setSprint(currentSprint);
      } else if (mvp.jira.sprints.length > 1) {
        const latestSprint = mvp.jira.sprints[mvp.jira.currentSprint - 2];
        if (latestSprint) setSprint(latestSprint);
      }
    }
  }, [mvp]);
  return (
    <div>
      <Grid container spacing={3}>
        <Grid item xs={4}>
          <Typography gutterBottom className={classes.greyTitles}>
            Mood
          </Typography>
          <div>
            <LinearProgress
              className={
                sprint.teamMood === 1
                  ? classes.redProgress
                  : sprint.teamMood === 2
                  ? classes.orangeProgress
                  : sprint.teamMood === 3
                  ? classes.greenProgress
                  : sprint.teamMood === 4
                  ? classes.darkGreenProgress
                  : classes.progress
              }
              variant="determinate"
              value={sprint.teamMood * 25 || 0}
            />
            <span className={classes.progressBarTxt} style={{ left: '5%' }}>
              {sprint.teamMood || 0}
            </span>
          </div>
        </Grid>
        <Grid item xs={4}>
          <Typography gutterBottom className={classes.greyTitles}>
            Confidence
          </Typography>
          <div>
            <LinearProgress
              className={
                sprint.teamConfidence === 1
                  ? classes.redProgress
                  : sprint.teamConfidence === 2
                  ? classes.orangeProgress
                  : sprint.teamConfidence === 3
                  ? classes.greenProgress
                  : sprint.teamConfidence === 4
                  ? classes.darkGreenProgress
                  : classes.progress
              }
              variant="determinate"
              value={sprint.teamConfidence * 25 || 0}
            />
            <span className={classes.progressBarTxt} style={{ left: '5%' }}>
              {sprint.teamConfidence || 0}
            </span>
          </div>
        </Grid>
        <Grid item xs={4}>
          <Typography gutterBottom className={classes.greyTitles}>
            Motivation
          </Typography>
          <div>
            <LinearProgress
              className={
                sprint.teamMotivation === 1
                  ? classes.redProgress
                  : sprint.teamMotivation === 2
                  ? classes.orangeProgress
                  : sprint.teamMotivation === 3
                  ? classes.greenProgress
                  : sprint.teamMotivation === 4
                  ? classes.darkGreenProgress
                  : classes.progress
              }
              variant="determinate"
              value={sprint.teamMotivation * 25 || 0}
            />
            <span className={classes.progressBarTxt} style={{ left: '5%' }}>
              {sprint.teamMotivation || 0}
            </span>
          </div>
        </Grid>
      </Grid>
    </div>
  );
}
