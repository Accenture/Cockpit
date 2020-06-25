import React, { useState, useEffect } from 'react';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import FormControl from '@material-ui/core/FormControl';
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';
import Snackbar from '@material-ui/core/Snackbar';
import MuiAlert from '@material-ui/lab/Alert';
import { useParams } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { getOneMvp } from '../../redux/ormSlice';
import useStyles from './styles';
import MvpService from '../../services/service';
import { mvpSelector } from '../../redux/selector';

export default function MvpTeam(props) {
  const classes = useStyles();
  const [selectedTeam, setSelectedTeam] = useState('');
  const [open, setOpen] = React.useState(false);
  const mvpId = useParams().id;
  const mvpInfo = useSelector((state) => mvpSelector(state, mvpId));
  let mvpTeam = mvpInfo.team;
  const [teams, setTeams] = useState([]);

  useEffect(() => {
    async function getAllTeam() {
      const result = await MvpService.getTeams();
      setTeams(result.data);
      if (mvpTeam) {
        setSelectedTeam(mvpTeam);
      }
    }
    getAllTeam();
    // eslint-disable-next-line
  }, []);
  const dispatch = useDispatch();
  function handleChange(event) {
    const found = teams.find((element) => element.id === event.target.value);
    setSelectedTeam(found);
  }

  async function assign(event) {
    event.preventDefault();
    await MvpService.assignTeam(mvpId, selectedTeam.id);
    setOpen(true);
    dispatch(getOneMvp(mvpId));
  }
  async function unassign(event) {
    event.preventDefault();
    await MvpService.unassignTeam(mvpId);
    dispatch(getOneMvp(mvpId));
    mvpTeam = null;
    props.sendValue(0);
  }
  function Alert(prop) {
    return <MuiAlert elevation={6} variant="filled" {...prop} />;
  }
  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setOpen(false);
  };

  return (
    <div>
      <Grid container className={classes.containerAssign}>
        <Grid item xs={8}>
          <FormControl required size="small" fullWidth variant="outlined">
            <Select
              displayEmpty
              onChange={handleChange}
              value={selectedTeam.id || ''}
            >
              <MenuItem disabled value="">
                select a team
              </MenuItem>
              {teams.map((team) => (
                <MenuItem key={team.id} value={team.id}>
                  {team.name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={4}>
          <Button
            onClick={
              mvpTeam && mvpTeam.id === selectedTeam.id ? unassign : assign
            }
            id="submit"
            value="Submit"
            type="submit"
            disabled={selectedTeam === ''}
            variant="outlined"
            color="primary"
            className={classes.buttonAssign}
          >
            {mvpTeam && mvpTeam.id === selectedTeam.id ? 'unassign' : 'assign'}
          </Button>
        </Grid>
      </Grid>
      <Snackbar open={open} autoHideDuration={2000} onClose={handleClose}>
        <Alert onClose={handleClose} severity="success">
          Team successfully assigned to this MVP!
        </Alert>
      </Snackbar>
    </div>
  );
}
