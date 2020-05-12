import React from 'react';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import { useLocation } from 'react-router-dom';
import logo from '../../common/media/total-logo.jpg';
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
        <Typography className={classes.title} gutterBottom>
          {mvpInfo.name ? mvpInfo.name : 'Unnamed Mvp'}
        </Typography>
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
        {isHomePage && <Obeya />}
      </CardContent>
    </Card>
  );
}
