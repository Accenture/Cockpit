import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Grid from '@material-ui/core/Grid';
import { Link } from 'react-router-dom';
import DeleteIcon from '@material-ui/icons/Delete';
import IconButton from '@material-ui/core/IconButton';
import { fetchAllMvps, deleteMvp } from '../../redux/ormSlice';
import { mvpSelector } from '../../redux/selector';
import MvpCard from '../MvpCard/MvpCard';

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

  function deleteJira(e, id) {
    e.stopPropagation();
    dispatch(deleteMvp(id));
  }
  return (
    <Grid container>
      <Grid item xs={12}>
        <Grid container spacing={5} className={classes.gridList}>
          {mvpList.map((mvp) => (
            <Grid item key={mvp.id} className={classes.parent}>
              <IconButton
                className={classes.deleteButton}
                aria-label="delete"
                color="secondary"
                onClick={(e) => deleteJira(e, mvp.id)}
              >
                <DeleteIcon />
              </IconButton>
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
