import React from 'react';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import LinearProgress from '@material-ui/core/LinearProgress';
import useStyles from './styles';

export default function Obeya() {
  const classes = useStyles();
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
              value={0}
            />
            <span className={classes.progressBarTxt} style={{ left: '5%' }}>
              {0}
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
              value={0}
            />
            <span className={classes.progressBarTxt} style={{ left: '5%' }}>
              {0}
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
              value={0}
            />
            <span className={classes.progressBarTxt} style={{ left: '5%' }}>
              {0}
            </span>
          </div>
        </Grid>
      </Grid>
    </div>
  );
}
