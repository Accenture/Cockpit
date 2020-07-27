import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import FormLabel from '@material-ui/core/FormLabel';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import FormControl from '@material-ui/core/FormControl';
import useStyles from './styles';
import MvpService from '../../services/apiService';
import { mvpSelector } from '../../redux/selector';

export default function ObeyaForm(props) {
  const classes = useStyles();
  const { sprintNumber } = props;

  const [selectedMood, setSelectedMood] = useState(0);

  const [selectedMotivation, setSelectedMotivation] = useState(0);

  const [selectedConfidence, setSelectedConfidence] = useState(0);

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
  useEffect(() => {
    async function getObeya() {
      const sprint = await MvpService.getSprint(mvp.jira.id, sprintNumber);
      if (sprint.data) {
        setSelectedMood(sprint.data.teamMood);
        setSelectedMotivation(sprint.data.teamMotivation);
        setSelectedConfidence(sprint.data.teamConfidence);
      } else {
        setSelectedMood(null);
        setSelectedMotivation(null);
        setSelectedConfidence(null);
      }
    }
    getObeya();
  }, [mvp, sprintNumber]);
  const handleSelectMood = (event) => {
    setSelectedMood(event.target.value);
    props.sendTeamMood(event.target.value);
  };
  const handleSelectMotivation = (event) => {
    setSelectedMotivation(event.target.value);
    props.sendTeamMotivation(event.target.value);
  };

  const handleSelectConfidence = (event) => {
    setSelectedConfidence(event.target.value);
    props.sendTeamConfidence(event.target.value);
  };

  return (
    <Paper className={classes.paper}>
      <Grid container className={classes.grid} spacing={1}>
        <Grid item xs={4}>
          <FormLabel className={classes.formLabel}>Mood</FormLabel>
          <FormControl
            className={classes.textField}
            required
            size="small"
            fullWidth
            variant="outlined"
          >
            <Select
              displayEmpty
              value={selectedMood || ''}
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
          <FormLabel className={classes.formLabel}>Motivation</FormLabel>
          <FormControl
            className={classes.textField}
            required
            size="small"
            fullWidth
            variant="outlined"
          >
            <Select
              displayEmpty
              value={selectedMotivation || ''}
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
          <FormLabel className={classes.formLabel}>Confidence</FormLabel>
          <FormControl
            className={classes.textField}
            required
            size="small"
            fullWidth
            variant="outlined"
          >
            <Select
              displayEmpty
              value={selectedConfidence || ''}
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
