import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import LinearProgress from '@material-ui/core/LinearProgress';
import { useLocation } from 'react-router-dom';
import MvpService from '../../services/apiService';
import useStyles from './styles';
import {
  setMood,
  setMotivation,
  setConfidence,
  confidenceState,
  motivationState,
  moodState,
} from './ObeyaSlice';
import { selectedTabState } from '../MvpInfoPage/MvpInfoPageSlice';

export default function Obeya(props) {
  const isHomePage = useLocation().pathname === '/';
  const classes = useStyles();
  const dispatch = useDispatch();
  const { mvp } = props;
  const selectedTab = useSelector(selectedTabState);
  const mood = useSelector(moodState);
  const motivation = useSelector(motivationState);
  const confidence = useSelector(confidenceState);
  const [display, setDisplay] = useState(true);
  useEffect(() => {
    async function getObeya() {
      const sprint = await MvpService.getSprint(
        mvp.jira.id,
        mvp.jira.currentSprint,
      );
      if (sprint.data) {
        if (
          (sprint.data.teamMood &&
            sprint.data.teamMotivation &&
            sprint.data.teamConfidence) ||
          selectedTab === 'sprint'
        ) {
          setDisplay(false);
          dispatch(setMood(sprint.data.teamMood));
          dispatch(setMotivation(sprint.data.teamMotivation));
          dispatch(setConfidence(sprint.data.teamConfidence));
        } else if (!selectedTab || selectedTab === 'overview') {
          const previousSprint = await MvpService.getSprint(
            mvp.jira.id,
            mvp.jira.currentSprint - 1,
          );
          if (previousSprint.data) {
            dispatch(setMood(previousSprint.data.teamMood));
            dispatch(setMotivation(previousSprint.data.teamMotivation));
            dispatch(setConfidence(previousSprint.data.teamConfidence));
          }
        }
      }
    }
    getObeya();
  }, [dispatch, mvp, selectedTab]);
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
              value={mood * 25}
            />
            <span className={classes.progressBarTxt} style={{ left: '5%' }}>
              {mood || 0}
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
              value={motivation * 25}
            />
            <span className={classes.progressBarTxt} style={{ left: '5%' }}>
              {motivation || 0}
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
              value={confidence * 25}
            />
            <span className={classes.progressBarTxt} style={{ left: '5%' }}>
              {confidence || 0}
            </span>
          </div>
        </Grid>
      </Grid>
      {isHomePage && display && (
        <Typography
          className={classes.subTitle}
          color="textSecondary"
          gutterBottom
        >
          Obeya of Last Sprint
        </Typography>
      )}
    </div>
  );
}
