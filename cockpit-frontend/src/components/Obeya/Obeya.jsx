import React, { useEffect, useState } from 'react';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import LinearProgress from '@material-ui/core/LinearProgress';
import useStyles from './styles';

export default function Obeya(props) {
  const classes = useStyles();
  const { mvp } = props;
  const [sprint, setSprint] = useState({});
  let sp = {};
  useEffect(() => {
    sp = mvp.jira.sprints[mvp.jira.sprints.length - 1];
    if (sp.teamMood && sp.teamMotivation && sp.teamConfidence) {
      setSprint(sp);
    } else {
      sp = mvp.jira.sprints[mvp.jira.sprints.length - 2];
      setSprint(sp);
    }
  }, []);
  return (
    <div>
      <Grid container spacing={3}>
        <Grid item xs={4}>
          <Typography gutterBottom className={classes.greyTitles}>
            Mood
          </Typography>
          <div>
            <LinearProgress
              className={classes.progress}
              variant="determinate"
              value={sprint.teamMood * 25}
            />
            <span className={classes.progressBarTxt} style={{ left: '5%' }}>
              {sprint.teamMood}
            </span>
          </div>
        </Grid>
        <Grid item xs={4}>
          <Typography gutterBottom className={classes.greyTitles}>
            Confidence
          </Typography>
          <div>
            <LinearProgress
              className={classes.progress}
              variant="determinate"
              value={sprint.teamConfidence * 25}
            />
            <span className={classes.progressBarTxt} style={{ left: '5%' }}>
              {sprint.teamConfidence}
            </span>
          </div>
        </Grid>
        <Grid item xs={4}>
          <Typography gutterBottom className={classes.greyTitles}>
            Motivation
          </Typography>
          <div>
            <LinearProgress
              className={classes.progress}
              variant="determinate"
              value={sprint.teamMotivation * 25}
            />
            <span className={classes.progressBarTxt} style={{ left: '5%' }}>
              {sprint.teamMotivation}
            </span>
          </div>
        </Grid>
      </Grid>
    </div>
  );
}
