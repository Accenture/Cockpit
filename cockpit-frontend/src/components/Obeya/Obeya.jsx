import React, { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams } from 'react-router-dom';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import LinearProgress from '@material-ui/core/LinearProgress';
import MvpService from '../../services/apiService';
import { mvpSelector } from '../../redux/selector';
import useStyles from './styles';
import {
  setMood,
  setMotivation,
  setConfidence,
  confidenceState,
  motivationState,
  moodState,
} from './ObeyaSlice';

export default function Obeya(props) {
  const classes = useStyles();
  const dispatch = useDispatch();
  const { mvp } = props;
  /* const [mood, setMood] = useState(0);
  const [motivation, setMotivation] = useState(0);
  const [confidence, setConfidence] = useState(0); */
  const mood = useSelector(moodState);
  const motivation = useSelector(motivationState);
  const confidence = useSelector(confidenceState);

  useEffect(() => {
    async function getObeya() {
      const sprint = await MvpService.getSprint(
        mvp.jira.id,
        mvp.jira.currentSprint,
      );
      if (sprint.data) {
        dispatch(setMood(sprint.data.teamMood));
        dispatch(setMotivation(sprint.data.teamMotivation));
        dispatch(setConfidence(sprint.data.teamConfidence));
      }
    }
    getObeya();
  }, [dispatch, mvp]);
  return (
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
            {mood}
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
            {motivation}
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
            {confidence}
          </span>
        </div>
      </Grid>
    </Grid>
  );
}
