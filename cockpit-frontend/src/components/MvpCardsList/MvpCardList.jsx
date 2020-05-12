import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Grid from '@material-ui/core/Grid';
import { Link } from 'react-router-dom';
import { fetchAllMvps } from '../../redux/ormSlice';
import { mvpSelector } from '../../redux/selector';
import MvpCard from '../Card/MvpCard';
// styles
import useStyles from './styles';

export default function MvpCardList(props) {
  const classes = useStyles();
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(fetchAllMvps());
  }, [dispatch]);

  // Filter Mvps with selected mvp status
  const mvpList = useSelector((state) => mvpSelector(state)).filter(
    (mvp) => mvp.status === props.mvpState,
  );

  return (
    <Grid container>
      <Grid item xs={12}>
        <Grid container spacing={5} className={classes.gridList}>
          {mvpList.map((mvp) => (
            <Grid item key={mvp.id}>
              <Link
                to={`/mvp-info/${mvp.id}`}
                className={classes.cardRouterLink}
              >
                <MvpCard mvpInfo={mvp} />
              </Link>
            </Grid>
          ))}
        </Grid>
      </Grid>
    </Grid>
  );
}
