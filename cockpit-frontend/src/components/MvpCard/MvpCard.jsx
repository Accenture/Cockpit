import React, { useState, useEffect } from 'react';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import { useLocation } from 'react-router-dom';
import Link from '@material-ui/core/Link';
import logo from '../../common/media/logo-total.webp';
import useStyles from './styles';
import Obeya from '../Obeya/Obeya';

export default function MvpCard(props) {
  const classes = useStyles();
  const { mvpInfo } = props;
  const [pitch, setPitch] = useState('');
  const [readMore, setReadMore] = useState(false);
  const isHomePage = useLocation().pathname === '/';
  let status;
  const mvpNameGridValue = isHomePage ? 12 : 7;
  if (mvpInfo.status === 'inprogress') status = 'In Progress';
  else if (mvpInfo.status === 'transferred') status = 'Transferred';
  else status = 'Unknown status';
  const seeMore = (event) => {
    event.preventDefault();
    setPitch(mvpInfo.mvpDescription);
    setReadMore(false);
  };
  useEffect(() => {
    if (mvpInfo.mvpDescription && mvpInfo.mvpDescription.length > 220) {
      setPitch(mvpInfo.mvpDescription.substring(0, 220));
      setReadMore(true);
    } else {
      setPitch(mvpInfo.mvpDescription);
    }
  }, [mvpInfo.mvpDescription]);
  const seeLess = () => {
    setReadMore(true);
    setPitch(mvpInfo.mvpDescription.substring(0, 220));
  };
  return (
    <Card className={isHomePage ? classes.dashboardCard : classes.mvpInfoCard}>
      <CardMedia
        className={classes.cardMedia}
        image={mvpInfo.urlMvpAvatar ? mvpInfo.urlMvpAvatar : logo}
        title={mvpInfo.name ? mvpInfo.name : 'Unnamed MVP'}
      />
      <CardContent>
        <Grid container spacing={1}>
          <Grid item xs={mvpNameGridValue}>
            <Typography className={classes.title} gutterBottom>
              {mvpInfo.name ? mvpInfo.name : 'Unnamed Mvp'}
            </Typography>
          </Grid>
          {!isHomePage && (
            <Grid item xs={5} className={classes.mvpStatusCard}>
              <Typography className={classes.subTitle}>{status}</Typography>
            </Grid>
          )}
        </Grid>
        {!isHomePage && mvpInfo.mvpDescription && (
          <Grid container spacing={1}>
            <Grid item xs={12}>
              <Typography className={classes.mvpDescription}>
                <span onClick={seeLess}>{pitch}</span>
                {readMore && <span>... </span>}
                {readMore && (
                  <Link href="#" onClick={seeMore}>
                    See more
                  </Link>
                )}
              </Typography>
            </Grid>
          </Grid>
        )}
        <Grid container spacing={3}>
          <Grid item xs={6}>
            <Typography gutterBottom>
              {mvpInfo.jira.currentSprint
                ? mvpInfo.jira.currentSprint
                : 'Unknown'}
            </Typography>
            <Typography
              className={classes.subTitle}
              color="textSecondary"
              gutterBottom
            >
              Current Sprint
            </Typography>
          </Grid>
          {!isHomePage && (
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
          )}
        </Grid>
        {isHomePage && <Obeya mvp={mvpInfo} />}
      </CardContent>
    </Card>
  );
}
