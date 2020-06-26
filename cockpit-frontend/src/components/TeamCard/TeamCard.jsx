import React from 'react';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import useStyles from './styles';
import { mvpSelector } from '../../redux/selector';

export default function TeamCard() {
  const classes = useStyles();
  const mvpId = useParams().id;
  const mvpInfo = useSelector((state) => mvpSelector(state, mvpId));

  return (
    <Card className={classes.teamCard}>
      <CardContent>
        <Grid container alignItems="center">
          <Grid item xs={6}>
            <Typography variant="h6">Team</Typography>
          </Grid>
          <Grid item xs={6}>
            <Typography
              variant="subtitle2"
              className={mvpInfo.team ? classes.teamName : ''}
            >
              {mvpInfo.team ? mvpInfo.team.name : 'Not defined'}
            </Typography>
          </Grid>
        </Grid>
      </CardContent>
    </Card>
  );
}
