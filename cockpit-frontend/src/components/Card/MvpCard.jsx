import React from 'react';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import LinearProgress from '@material-ui/core/LinearProgress';
import logo from '../../common/media/total-logo.jpg';
import useStyles from './styles';

export default function MvpCard(props) {
  const classes = useStyles();
  const { mvpInfo } = props;
  return (
    <Grid container spacing={1}>
      <Grid item xs={2}>
        <Card className={classes.cardRoot}>
          <CardMedia
            className={classes.cardMedia}
            image={mvpInfo.mvpAvatarUrl ? mvpInfo.mvpAvatarUrl : logo}
            title={mvpInfo.name ? mvpInfo.name : "Unnamed MVP"}
          />
          <CardContent>
            <Typography
              className={classes.title}
              variant="h5"
              component="h2"
              gutterBottom
            >
              {mvpInfo.name ? mvpInfo.name : 'Unnamed Mvp'}
            </Typography>
            <Grid container spacing={3}>
              <Grid item xs={6}>
                <Typography gutterBottom>
                  {mvpInfo.iterationNumber
                    ? mvpInfo.iterationNumber
                    : 'Unknown'}
                </Typography>
                <Typography
                  className={classes.title}
                  color="textSecondary"
                  gutterBottom
                >
                  Cycle
                </Typography>
              </Grid>
              <Grid item xs={6}>
                <Typography gutterBottom>
                  {mvpInfo.currentSprint ? mvpInfo.currentSprint : 'Unknown'}
                </Typography>
                <Typography
                  className={classes.title}
                  color="textSecondary"
                  gutterBottom
                >
                  Current Sprint
                </Typography>
              </Grid>
            </Grid>
            <Grid container spacing={3}>
              <Grid item xs={4}>
                <Typography gutterBottom>Mood</Typography>
                <div>
                  <LinearProgress
                    className={classes.progress}
                    variant="determinate"
                    value={75}
                  />
                  <span className={classes.progressBarTxt} style={{ left: '60%' }}>
                    3
                  </span>
                </div>
              </Grid>
              <Grid item xs={4}>
                <Typography gutterBottom>Confidence</Typography>
                <div>
                  <LinearProgress
                    className={classes.progress}
                    variant="determinate"
                    value={25}
                  />
                  <span className={classes.progressBarTxt} style={{ left: '10%' }}>
                    1
                  </span>
                </div>
              </Grid>
              <Grid item xs={4}>
                <Typography gutterBottom>Motivation</Typography>
                <div>
                  <LinearProgress
                    className={classes.progress}
                    variant="determinate"
                    value={50}
                  />
                  <span className={classes.progressBarTxt} style={{ left: '35%' }}>
                    2
                  </span>
                </div>
              </Grid>
            </Grid>
          </CardContent>
        </Card>
      </Grid>
    </Grid>
  );
}
