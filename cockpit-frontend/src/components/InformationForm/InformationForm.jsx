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
import { mvpSelector } from '../../redux/selector';
import useStyles from './styles';

export default function InformationForm() {
  const classes = useStyles();
  const [mvp, setMvp] = useState({
    id: null,
    name: 'mvpName',
    pitch: 'Hello',
    status: 'inprogress',
    iterationNumber: 1,
    entity: 'es',
    currentSprint: 2,
    nbSprint: 0,
    mvpEndDate: '',
    mvpStartDate: '',
    mvpAvatarUrl: 'imageUrl',
    jiraProjectId: 10009,
    jiraBoardId: 1004,
    location: '',
    nbUsersStories: 0,
    bugsCount: 0,
    allBugsCount: 0,
    timeToFix: '',
    timeToDetect: '',
    cascade: [{}],
    sprint: [{}],
    userStories: [{}],
    bugHistories: [{}],
  });
  const mvpId = useParams().id;
  const mvpInfo = useSelector((state) => mvpSelector(state, mvpId));
  useEffect(() => {
    setMvp(mvpInfo);
  }, [mvpInfo]);

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
              value={mvpInfo.name ? mvpInfo.name : ''}
              variant="outlined"
              id="mvpName"
              name="mvpName"
              placeholder="MVP Name"
              size="small"
            />
          </Grid>
          <Grid item xs={12}>
            <FormLabel className={classes.formLabel}>Cycle</FormLabel>
            <TextField
              className={classes.textField}
              value={mvp.iterationNumber}
              required
              fullWidth
              variant="outlined"
              id="cycle"
              name="cycle"
              placeholder="Cycle"
              size="small"
              type="number"
              inputProps={{ min: '1', step: '1' }}
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
              value={mvp.status}
            >
              <Select displayEmpty value={mvp.status || ''}>
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
              <Select displayEmpty value={mvp.entity || ''}>
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
                  value={mvp.mvpAvatarUrl}
                  required
                  fullWidth
                  variant="outlined"
                  id="mvpPhoto"
                  name="mvpPhoto"
                  placeholder="http://"
                  size="small"
                />
              </div>
              <img
                className={classes.imgStyle}
                src={mvp.mvpAvatarUrl}
                alt="img"
              />
            </div>
          </Grid>
          <Grid item xs={12}>
            <FormLabel className={classes.formLabel}>Pitch</FormLabel>
            <TextField
              className={classes.textField}
              value={mvp.pitch || ''}
              required
              fullWidth
              variant="outlined"
              id="pitch"
              name="pitch"
              placeholder="Pitch"
              size="small"
              multiline
              rows="3"
            />
          </Grid>
          <Grid item xs={12}>
            <FormLabel className={classes.formLabel}>Sprint number</FormLabel>
            <TextField
              className={classes.textField}
              value={mvp.nbSprint}
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
              value={mvp.mvpStartDate || ''}
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
              value={mvp.mvpEndDate || ''}
            />
          </Grid>
        </Grid>
      </form>
    </Paper>
  );
}
