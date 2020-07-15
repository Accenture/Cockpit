import React, { useState, useEffect } from 'react';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import FormControl from '@material-ui/core/FormControl';
import { useParams } from 'react-router-dom';
import { useSelector } from 'react-redux';
import Box from '@material-ui/core/Box';
import moment from 'moment/moment';
import BurnUpChart from '../BurnUpChart/BurnUpChart';
import { mvpSelector } from '../../redux/selector';

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
  const mvpId = useParams().id;
  const mvp = useSelector((state) => mvpSelector(state, mvpId));
  const [sprints, setSprints] = useState([]);
  const [selectedSprint, setSelectedSprint] = useState({});
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const classes = useStyles();
  const { selectedTab } = props;

  useEffect(() => {
    if (mvp.jira.currentSprint > 0) {
      setSelectedSprint(mvp.jira.currentSprint);
      const list = [];
      for (let i = 1; i <= mvp.jira.currentSprint; i += 1) {
        list.push(i);
      }
      setSprints(list);
    }
  }, [mvp]);
  function handleChange(event) {
    setSelectedSprint(event.target.value);
    setStartDate(mvp.jira.mvpStartDate);
    setEndDate(mvp.jira.mvpEndDate);
  }
  return (
    <div className={classes.root}>
      <TabPanel value={selectedTab} index="overview">
        <BurnUpChart />
      </TabPanel>
      <TabPanel value={selectedTab} index="sprint">
        <FormControl size="small" className={classes.formControl}>
          <Select displayEmpty value={selectedSprint} onChange={handleChange}>
            {sprints.map((sprint) => (
              <MenuItem
                key={sprint}
                value={sprint}
                style={{ fontWeight: 'bold' }}
              >
                Sprint {sprint}
              </MenuItem>
            ))}
          </Select>{' '}
        </FormControl>
        <div className={classes.parent}>
          <div className={classes.sprintNumber}> SPRINT {selectedSprint} </div>
          <div className={classes.dateStyle}>
            {' '}
            From{' '}
            {mvp.jira.mvpStartDate
              ? moment(startDate).format('MMMM Do')
              : moment(new Date()).format('MMMM Do')}{' '}
            To{' '}
            {mvp.jira.mvpEndDate
              ? moment(endDate).format('MMMM Do')
              : moment(new Date()).format('MMMM Do')}
          </div>
        </div>
      </TabPanel>
    </div>
  );
}
