import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import LinearProgress from '@material-ui/core/LinearProgress';
import "./MvpCard.scss";
import logo from '../../common/media/total-logo.jpg';

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  cardRoot: {
    minWidth: 275,
    padding: theme.spacing(2)
  }
}));

export default function MvpCard() {
  const classes = useStyles();

  return (
    <div className={classes.root}>
      <Grid container spacing={3}>
        <Grid item xs={3}>
          <Card className={classes.cardRoot}>
            <CardMedia
              className="cardMedia"
              image={logo}
              title="Paella dish"
            />
            <CardContent>
              <Typography className="title" variant="h5" component="h2" gutterBottom>
                Project Name
              </Typography>
              <Grid container spacing={3}>
                <Grid item xs={6}>
                  <Typography gutterBottom>
                    1
                  </Typography>
                  <Typography className="title"color="textSecondary" gutterBottom>
                    Cycle
                  </Typography>
                </Grid>
                <Grid item xs={6}>
                  <Typography gutterBottom>
                    4
                  </Typography>
                  <Typography className="title"color="textSecondary" gutterBottom>
                    Current Sprint
                  </Typography>
                </Grid>
              </Grid>
              <Grid container spacing={3}>
                <Grid item xs={4}>
                  <Typography gutterBottom>
                    Mood
                  </Typography>
                  <div>
                    <LinearProgress className="progress" variant="determinate" value={75} />
                    <span className="progressBarTxt" style={{left:'60%'}}>3</span>
                  </div>
                </Grid>
                <Grid item xs={4}>
                  <Typography gutterBottom>
                    Confidence
                  </Typography>
                  <div>
                    <LinearProgress className="progress" variant="determinate" value={25} />
                    <span className="progressBarTxt" style={{left:'10%'}}>1</span>
                  </div>
                </Grid>
                <Grid item xs={4}>
                  <Typography gutterBottom>
                    Motivation
                  </Typography>
                  <div>
                    <LinearProgress className="progress" variant="determinate" value={50} />
                    <span className="progressBarTxt" style={{left:'35%'}}>2</span>
                  </div>
                </Grid>
              </Grid>
            </CardContent>
          </Card>
        </Grid>
      </Grid>
    </div>
  );
}
