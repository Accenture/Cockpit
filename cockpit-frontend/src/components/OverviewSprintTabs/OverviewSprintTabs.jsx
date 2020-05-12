import React from 'react';
import Box from '@material-ui/core/Box';
// styles
import useStyles from './styles';

function TabPanel(props) {
  const { children, value, index } = props;

  return (
    <div role="tabpanel" id={`mvp-${index}`}>
      {value === index && <Box p={2}>{children}</Box>}
    </div>
  );
}

export default function OverviewSprintTabs(props) {
  const classes = useStyles();
  const { selectedTab } = props;
  return (
    <div className={classes.root}>
      <TabPanel value={selectedTab} index="overview">
        Mvp overview charts
      </TabPanel>
      <TabPanel value={selectedTab} index="sprint">
        Mvp sprint infos
      </TabPanel>
    </div>
  );
}
