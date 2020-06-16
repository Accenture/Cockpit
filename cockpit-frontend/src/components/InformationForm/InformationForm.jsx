import React, { useEffect, useState } from 'react';
import TextField from '@material-ui/core/TextField';
import Grid from '@material-ui/core/Grid';
import FormLabel from '@material-ui/core/FormLabel';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import Paper from '@material-ui/core/Paper';
import FormControl from '@material-ui/core/FormControl';
import { useParams } from 'react-router-dom';
import { useSelector } from 'react-redux';
import MvpService from '../../services/service';
import { mvpSelector } from '../../redux/selector';
import useStyles from './styles';

export default function InformationForm() {
  const classes = useStyles();

  const [name, setName] = useState('');
  const [pitch, setPitch] = useState('');
  const [cycle, setCycle] = useState(0);
  const [status, setStatus] = useState('');
  const [entity, setEntity] = useState('');
  const [urlMvpAvatar, setUrlMvpAvatar] = useState('');
  const [nbSprint, setNbSprint] = useState(8);
  const [mvpStartDate, setMvpStartDate] = useState(0);
  const [mvpEndDate, setMvpEndDate] = useState(0);
  const mvpId = useParams().id;
  const mvpInfo = useSelector((state) => mvpSelector(state, mvpId));
  useEffect(() => {
    setName(mvpInfo.name);
    setPitch(mvpInfo.pitch);
    setCycle(mvpInfo.cycle);
    setStatus(mvpInfo.status);
    setEntity(mvpInfo.entity);
    setUrlMvpAvatar(mvpInfo.urlMvpAvatar);
    // setNbSprint(mvpInfo.nbSprint);
    setMvpStartDate(mvpInfo.jira.mvpStartDate);
    setMvpEndDate(mvpInfo.jira.mvpEndDate);
  }, [mvpInfo]);
  function handleNameChange(event) {
    setName(event.target.value);
  }
  function handlePitchChange(event) {
    setPitch(event.target.value);
  }
  function handleCycleChange(event) {
    setCycle(event.target.value);
  }
  function handleStatusChange(event) {
    setStatus(event.target.value);
  }
  function handleEntityChange(event) {
    setEntity(event.target.value);
  }
  function handleImageChange(event) {
    setUrlMvpAvatar(event.target.value);
  }
  function handleStartDateChange(event) {
    setMvpStartDate(event.target.value);
  }
  function handleEndDateChange(event) {
    setMvpEndDate(event.target.value);
  }
  async function submit(e) {
    e.preventDefault();
    const newJira = {
      jiraProjectKey: mvpInfo.jira.jiraProjectKey,
      currentSprint: mvpInfo.jira.currentSprint,
      jiraProjectId: mvpInfo.jira.jiraProjectId,
      mvpStartDate: mvpStartDate,
      mvpEndDate: mvpEndDate,
      mvp: {
        name: name,
        entity: entity,
        urlMvpAvatar: urlMvpAvatar,
        cycle: cycle,
        mvpDescription: pitch,
        status: status,
        team: {
          name: '',
          teamMembers: [
            {
              firstName: '',
              lastName: '',
              email: '',
            },
          ],
        },
        technologies: [
          {
            name: '',
            url: '',
          },
        ],
      },
    };
    await MvpService.updateJiraProject(newJira);
   // dispatch(close());
  }
  return (
    <Paper className={classes.paper}>
      <form>
        <Grid className={classes.grid} container spacing={1}>
          <Grid item xs={12}>
            <FormLabel className={classes.formLabel}>Name</FormLabel>
            <TextField
              className={classes.textField}
              required
              fullWidth
              value={name}
              variant="outlined"
              id="mvpName"
              name="name"
              placeholder="MVP Name"
              size="small"
              onChange={handleNameChange}
            />
          </Grid>
          <Grid item xs={12}>
            <FormLabel className={classes.formLabel}>Cycle</FormLabel>
            <TextField
              className={classes.textField}
              value={cycle}
              required
              fullWidth
              variant="outlined"
              id="cycle"
              name="cycle"
              placeholder="Cycle"
              size="small"
              type="number"
              inputProps={{ min: '1', step: '1' }}
              onChange={handleCycleChange}
            />
          </Grid>
          <Grid item xs={12}>
            <FormLabel className={classes.formLabel}>Status</FormLabel>
            <FormControl
              required
              size="small"
              fullWidth
              variant="outlined"
              className={classes.textField}
              value={mvpInfo.status}
            >
              <Select
                displayEmpty
                value={status || ''}
                onChange={handleStatusChange}
              >
                <MenuItem value="condidates">Condidates</MenuItem>
                <MenuItem value="inprogress">In progress</MenuItem>
                <MenuItem value="transferred">Transferred</MenuItem>
              </Select>
            </FormControl>
          </Grid>
          <Grid item xs={12}>
            <FormLabel className={classes.formLabel}>Entity</FormLabel>
            <FormControl
              required
              size="small"
              fullWidth
              variant="outlined"
              className={classes.textField}
            >
              <Select
                displayEmpty
                value={entity || ''}
                onChange={handleEntityChange}
              >
                <MenuItem value="EP">EP</MenuItem>
                <MenuItem value="RC">RC</MenuItem>
                <MenuItem value="MS">MS</MenuItem>
                <MenuItem value="GRP">GRP</MenuItem>
              </Select>
            </FormControl>
          </Grid>
          <Grid item xs={12}>
            <div style={{ display: 'flex' }}>
              <div>
                <FormLabel className={classes.formLabel}>
                  MVP photo URL
                </FormLabel>
                <TextField
                  className={classes.textField}
                  value={urlMvpAvatar}
                  required
                  fullWidth
                  variant="outlined"
                  id="mvpPhoto"
                  name="mvpPhoto"
                  placeholder="http://"
                  size="small"
                  onChange={handleImageChange}
                />
              </div>
              <img className={classes.imgStyle} src={urlMvpAvatar} alt="img" />
            </div>
          </Grid>
          <Grid item xs={12}>
            <FormLabel className={classes.formLabel}>Pitch</FormLabel>
            <TextField
              className={classes.textField}
              value={pitch || ''}
              required
              fullWidth
              variant="outlined"
              id="pitch"
              name="pitch"
              placeholder="Pitch"
              size="small"
              multiline
              rows="3"
              onChange={handlePitchChange}
            />
          </Grid>
          <Grid item xs={12}>
            <FormLabel className={classes.formLabel}>Sprint number</FormLabel>
            <TextField
              className={classes.textField}
              value={nbSprint}
              required
              fullWidth
              variant="outlined"
              id="sprintNumber"
              name="sprintNumber"
              placeholder="Sprint number"
              size="small"
              type="number"
              inputProps={{ min: '0', step: '1' }}
            />
          </Grid>
          <Grid item xs={6}>
            <TextField
              id="date"
              label="MVP start date"
              type="date"
              // defaultValue="2020-02-24"
              className={classes.textField}
              InputLabelProps={{
                shrink: true,
              }}
              variant="outlined"
              value={mvpStartDate || ''}
              onChange={handleStartDateChange}
            />
          </Grid>
          <Grid item xs={6}>
            <TextField
              id="date"
              label="MVP end date"
              type="date"
              format="DD/MM/YYYY"
              // defaultValue="2020-05-24"
              className={classes.textField}
              InputLabelProps={{
                shrink: true,
              }}
              variant="outlined"
              value={mvpEndDate || ''}
              onChange={handleEndDateChange}
            />
          </Grid>
        </Grid>
      </form>
    </Paper>
  );
}
