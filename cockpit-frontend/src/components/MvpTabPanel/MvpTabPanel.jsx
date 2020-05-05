import React from 'react';
import Box from '@material-ui/core/Box';
import { useSelector } from 'react-redux';
import { selectMvpState } from '../MvpMenu/mvpMenuSlice';
import MvpCardList from '../MvpCardsList/MvpCardList';
// styles
import useStyles from './styles';

function TabPanel(props) {
  const { children, value, index } = props;

  return (
    <div role="tabpanel" id={`mvp-panel-${index}`}>
      {value === index && <Box p={3}>{children}</Box>}
    </div>
  );
}

export default function MvpTabs() {
  const classes = useStyles();
  const selectedMvpState = useSelector(selectMvpState);
  return (
    <div className={classes.root}>
      <TabPanel value={selectedMvpState} index="Candidates">
        MVP candidates list
      </TabPanel>
      <TabPanel value={selectedMvpState} index="In Progress">
        <MvpCardList />
      </TabPanel>
      <TabPanel value={selectedMvpState} index="Transferred">
        MVP transffered
      </TabPanel>
    </div>
  );
}
