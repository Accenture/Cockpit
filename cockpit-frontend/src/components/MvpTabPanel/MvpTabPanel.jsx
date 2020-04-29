import React from 'react';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import { useSelector } from 'react-redux';
import { selectMvpState } from '../MvpMenu/mvpMenuSlice';
// styles
import useStyles from './styles';

function TabPanel(props) {
  const { children, value, index } = props;

  return (
    <div role="tabpanel" id={`mvp-panel-${index}`}>
      {value === index && (
        <Box p={3}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}

export default function MvpTabs() {
  const classes = useStyles();
  const selectedMvpState = useSelector(selectMvpState);
  return (
    <div className={classes.root}>
      <TabPanel value={selectedMvpState} index="Candidates">
        MVP Candidates cards
      </TabPanel>
      <TabPanel value={selectedMvpState} index="In Progress">
        MVP In progress cards
      </TabPanel>
      <TabPanel value={selectedMvpState} index="Transferred">
        MVP Transffered cards
      </TabPanel>
    </div>
  );
}
