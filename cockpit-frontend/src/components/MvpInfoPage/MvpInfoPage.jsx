import React from 'react';
import Grid from '@material-ui/core/Grid';
import { useParams } from 'react-router-dom';
import { useSelector } from 'react-redux';
import Header from '../Header/Header';
import { mvpSelector } from '../../redux/selector';
import MvpCard from '../Card/MvpCard';
import TeamCard from '../TeamCard/TeamCard';
import TechnologyCard from '../TechnologyCard/TechnologyCard';
import useStyles from './styles';

function MvpInfoPage() {
  const classes = useStyles();
  const mvpId = useParams().id;
  const mvp = useSelector((state) => mvpSelector(state, mvpId));
  return (
    <div>
      <Header />
      <Grid container className={classes.mvpInfoContainer}>
        <Grid item xs={3} className={classes.leftColumnContainer}>
          <MvpCard mvpInfo={mvp} />
          <TeamCard />
          <TechnologyCard />
        </Grid>
        <Grid item xs={9}>
          Hello Right
        </Grid>
      </Grid>
    </div>
  );
}

export default MvpInfoPage;
