import React from 'react';
import Grid from '@material-ui/core/Grid';
import MvpCard from '../Card/MvpCard';
// styles
import useStyles from './styles';

export default function MvpCardList() {
  const mvpList = [
    {
      id: '1',
      name: 'test1',
    },
    {
      id: '2',
      name: 'test2',
    },
    {
      id: '3',
      name: 'test1',
    },
    {
      id: '4',
      name: 'test1',
    },
    {
      id: '5',
      name: 'test1',
    },
    {
      id: '6',
      name: 'test1',
    },
    {
      id: '7',
      name: 'test1',
    },
    {
      id: '8',
      name: 'test1',
    },
  ];
  const classes = useStyles();
  return (
    <Grid container>
      <Grid item xs={12}>
        <Grid container spacing={5} className={classes.gridList}>
          {mvpList.map((mvp) => (
            <Grid item key={mvp.id}>
              <MvpCard mvpInfo={mvp} />
            </Grid>
          ))}
        </Grid>
      </Grid>
    </Grid>
  );
}
