import React, { useState, useEffect } from 'react';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import FormControl from '@material-ui/core/FormControl';
import { useParams } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import Box from '@material-ui/core/Box';
import moment from 'moment/moment';
import BurnUpChart from '../BurnUpChart/BurnUpChart';
import MvpService from '../../services/apiService';
import { mvpSelector } from '../../redux/selector';
import { setMood, setMotivation, setConfidence } from '../Obeya/ObeyaSlice';
import { selectedTabState } from '../MvpInfoPage/MvpInfoPageSlice';

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
export default function OverviewSprintTabs() {
  const mvpId = useParams().id;
  const mvp = useSelector((state) => mvpSelector(state, mvpId));
  const [sprints, setSprints] = useState([]);
  const [selectedSprint, setSelectedSprint] = useState(0);
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const classes = useStyles();
  const dispatch = useDispatch();
  const selectedTab = useSelector(selectedTabState);

  useEffect(() => {
    async function getSprint() {
      const sprint = await MvpService.getSprint(
        mvp.jira.id,
        mvp.jira.currentSprint,
      );
      if (sprint.data) {
        setStartDate(sprint.data.sprintStartDate);
        setEndDate(sprint.data.sprintEndDate);
      } else {
        setStartDate(null);
        setEndDate(null);
      }
    }
    if (mvp.jira.currentSprint > 0) {
      setSelectedSprint(mvp.jira.currentSprint);
      getSprint();
      const list = [];
      for (let i = 1; i <= mvp.jira.currentSprint; i += 1) {
        list.push(i);
      }
      setSprints(list);
    }
  }, [mvp, selectedTab]);
  async function handleChange(event) {
    setSelectedSprint(event.target.value);
    const sprint = await MvpService.getSprint(mvp.jira.id, event.target.value);
    if (sprint.data) {
      setStartDate(sprint.data.sprintStartDate);
      if (event.target.value !== mvp.jira.currentSprint) {
        setEndDate(sprint.data.sprintCompleteDate);
      } else {
        setEndDate(sprint.data.sprintEndDate);
      }
      dispatch(setMood(sprint.data.teamMood));
      dispatch(setMotivation(sprint.data.teamMotivation));
      dispatch(setConfidence(sprint.data.teamConfidence));
    } else {
      setStartDate(null);
      setEndDate(null);
    }
  }
  return (
    <div className={classes.root}>
      <TabPanel value={selectedTab} index="overview">
        <BurnUpChart />
      </TabPanel>
      <TabPanel value={selectedTab} index="sprint">
        <FormControl size="small" className={classes.formControl}>
          <Select
            displayEmpty
            value={selectedSprint}
            onChange={handleChange}
            className={classes.sprintNumber}
          >
            {sprints.map((sprint) => (
              <MenuItem key={sprint} value={sprint}>
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
            {startDate
              ? moment(startDate).format('MMMM Do')
              : moment(new Date()).format('MMMM Do')}{' '}
            To{' '}
            {endDate
              ? moment(endDate).format('MMMM Do')
              : moment(new Date()).format('MMMM Do')}
          </div>
        </div>
      </TabPanel>
    </div>
  );
}
