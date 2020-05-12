import React from 'react';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import useStyles from './styles';

export default function TeamCard() {
  const classes = useStyles();

  return (
    <Card className={classes.teamCard}>
      <CardContent>
        <Grid container>
          <Grid item xs={6}>
            <Typography variant="h5">Team</Typography>
          </Grid>
          <Grid item xs={6}>
            <Typography variant="h7">Team not defined</Typography>
          </Grid>
        </Grid>
      </CardContent>
    </Card>
  );
}
