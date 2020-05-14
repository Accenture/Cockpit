import React from 'react';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import { useLocation } from 'react-router-dom';
import logo from '../../common/media/logo-total.webp';
import useStyles from './styles';
import Obeya from '../Obeya/Obeya';

export default function MvpCard(props) {
  const classes = useStyles();
  const { mvpInfo } = props;
  const isHomePage = useLocation().pathname === '/';
  return (
    <Card className={isHomePage ? classes.dashboardCard : classes.mvpInfoCard}>
      <CardMedia
        className={classes.cardMedia}
        image={mvpInfo.mvpAvatarUrl ? mvpInfo.mvpAvatarUrl : logo}
        title={mvpInfo.name ? mvpInfo.name : 'Unnamed MVP'}
      />
      <CardContent>
        <Grid container spacing={1}>
          <Grid item xs={7}>
            <Typography className={classes.title} gutterBottom>
              {mvpInfo.name ? mvpInfo.name : 'Unnamed Mvp'}
            </Typography>
          </Grid>
          {!isHomePage && (
            <Grid item xs={5} className={classes.mvpStatusCard}>
              <Typography className={classes.subTitle}>
                {mvpInfo.status
                  ? mvpInfo.status.toUpperCase()
                  : 'Unknown status'}
              </Typography>
            </Grid>
          )}
        </Grid>
        {!isHomePage && mvpInfo.pitch && (
          <Grid container spacing={1}>
            <Grid item xs={12}>
              <Typography className={classes.pitch}>{mvpInfo.pitch}</Typography>
            </Grid>
          </Grid>
        )}
        <Grid container spacing={3}>
          <Grid item xs={6}>
            <Typography gutterBottom>
              {mvpInfo.iterationNumber ? mvpInfo.iterationNumber : 'Unknown'}
            </Typography>
            <Typography
              className={classes.subTitle}
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
              className={classes.subTitle}
              color="textSecondary"
              gutterBottom
            >
              Current Sprint
            </Typography>
          </Grid>
        </Grid>
        {!isHomePage && (
          <Grid container spacing={3}>
            <Grid item xs={6}>
              <Typography gutterBottom>
                {mvpInfo.entity ? mvpInfo.entity : 'Unknown'}
              </Typography>
              <Typography
                className={classes.subTitle}
                color="textSecondary"
                gutterBottom
              >
                Entity
              </Typography>
            </Grid>
          </Grid>
        )}
        {isHomePage && <Obeya />}
      </CardContent>
    </Card>
  );
}
