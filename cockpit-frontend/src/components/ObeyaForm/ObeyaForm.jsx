import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import FormControl from '@material-ui/core/FormControl';
import useStyles from './styles';
import { mvpSelector } from '../../redux/selector';

export default function ObeyaForm(props) {
  const classes = useStyles();
  const { sprintNumber } = props;

  const teamMood = [
    { value: 4, label: 'Awesome' },
    { value: 3, label: 'Good' },
    { value: 2, label: 'Not too bad' },
    { value: 1, label: 'Very bad' },
  ];
  const teamMotivation = [
    {
      value: 4,
      label: 'We will do whatever it takes to succeed, we will do our best',
    },
    { value: 3, label: 'We are ok to try' },
    { value: 2, label: 'We dont believe in it' },
    { value: 1, label: 'Let us go' },
  ];
  const teamConfidence = [
    {
      value: 4,
      label: 'Piece of cake',
    },
    { value: 3, label: 'We have to stay focus' },
    { value: 2, label: 'Hectic' },
    { value: 1, label: 'We have to talk' },
  ];
  const mvpId = useParams().id;
  const mvp = useSelector((state) => mvpSelector(state, mvpId));

  const [selectedSprint, setSelectedSprint] = useState({});

  useEffect(() => {
    const sp = mvp.jira.sprints.find(
      (sprint) => sprint.sprintNumber === sprintNumber,
    );
    setSelectedSprint(sp);
  }, [sprintNumber]);

  useEffect(() => {
    props.sendSprint(selectedSprint);
  }, [selectedSprint]);

  const handleSelectMood = (event) => {
    setSelectedSprint((prevState) => ({
      ...prevState,
      teamMood: event.target.value,
    }));
  };
  const handleSelectMotivation = (event) => {
    setSelectedSprint((prevState) => ({
      ...prevState,
      teamMotivation: event.target.value,
    }));
  };

  const handleSelectConfidence = (event) => {
    setSelectedSprint((prevState) => ({
      ...prevState,
      teamConfidence: event.target.value,
    }));
  };

  return (
    <Paper className={classes.paper}>
      <Grid container className={classes.grid} spacing={1}>
        <Grid item xs={4}>
          <FormControl
            className={classes.textField}
            required
            size="small"
            fullWidth
            variant="outlined"
          >
            <Select
              displayEmpty
              value={selectedSprint.teamMood || ''}
              onChange={handleSelectMood}
            >
              <MenuItem value="" disabled>
                Mood
              </MenuItem>
              {teamMood.map((item) => (
                <MenuItem value={item.value} key={item.value}>
                  {item.label} - {item.value}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={4}>
          <FormControl
            className={classes.textField}
            required
            size="small"
            fullWidth
            variant="outlined"
          >
            <Select
              displayEmpty
              value={selectedSprint.teamMotivation || ''}
              onChange={handleSelectMotivation}
            >
              <MenuItem value="" disabled>
                Motivation
              </MenuItem>
              {teamMotivation.map((item) => (
                <MenuItem value={item.value} key={item.value}>
                  {item.label} - {item.value}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={4}>
          <FormControl
            className={classes.textField}
            required
            size="small"
            fullWidth
            variant="outlined"
          >
            <Select
              displayEmpty
              value={selectedSprint.teamConfidence || ''}
              onChange={handleSelectConfidence}
            >
              <MenuItem value="" disabled>
                Confidence
              </MenuItem>
              {teamConfidence.map((item) => (
                <MenuItem value={item.value} key={item.value}>
                  {item.label} - {item.value}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>
      </Grid>
    </Paper>
  );
}
