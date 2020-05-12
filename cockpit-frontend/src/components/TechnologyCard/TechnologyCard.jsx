import React from 'react';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import useStyles from './styles';

export default function TecnologyCard() {
  const classes = useStyles();

  return (
    <Card className={classes.technologyCard}>
      <CardContent>
        <Grid container>
          <Grid item xs={6}>
            <Typography variant="h5">Technology</Typography>
          </Grid>
          <Grid item xs={6}>
            <Typography variant="h7">Techonologies not defined</Typography>
          </Grid>
        </Grid>
      </CardContent>
    </Card>
  );
}
