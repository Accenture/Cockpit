import React, { useState, useEffect } from 'react';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';
import ButtonGroup from '@material-ui/core/ButtonGroup';
import TextField from '@material-ui/core/TextField';
import FormLabel from '@material-ui/core/FormLabel';
import Snackbar from '@material-ui/core/Snackbar';
import MuiAlert from '@material-ui/lab/Alert';
import { useParams } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { getOneMvp } from '../../redux/ormSlice';
import MvpTeam from './mvpTeam';
import MvpService from '../../services/apiService';
import useStyles from './styles';
import { mvpSelector } from '../../redux/selector';
import TeamMemberList from '../TeamMemberList/TeamMemberList';

export default function TeamManagementForm() {
  const classes = useStyles();

  const [value, setValue] = useState(0);
  const [teamName, setTeamName] = useState('');
  const [mvpTeam, setMvpTeam] = useState('');
  const [open, setOpen] = React.useState(false);
  const dispatch = useDispatch();
  const mvpId = useParams().id;
  const mvpInfo = useSelector((state) => mvpSelector(state, mvpId));
  const [error, setError] = React.useState(false);

  useEffect(() => {
    setMvpTeam(mvpInfo.team);
  }, [mvpInfo]);
  const handleValueChange = (event, valeur) => {
    setOpen(false);
    setValue(valeur);
  };
  async function save(event) {
    event.preventDefault();
    const team = {
      name: teamName,
    };
    const result = await MvpService.createNewTeam(team, mvpId);
    if (result.data) {
      setOpen(true);
      setTeamName('');
      dispatch(getOneMvp(mvpId));
      setValue(0);
    } else {
      setError(true);
    }
  }
  function handleNameChange(event) {
    setTeamName(event.target.value);
    setError(false);
  }
  function Alert(props) {
    return <MuiAlert elevation={6} variant="filled" {...props} />;
  }
  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }

    setOpen(false);
  };

  function getValue(val) {
    setValue(val);
    setOpen(true);
  }
  return (
    <Paper className={classes.paper}>
      <form>
        <Grid className={classes.grid} container spacing={1}>
          {!mvpTeam && (
            <ButtonGroup color="primary" style={{ marginTop: 32 }}>
              <Button
                style={{ textTransform: 'capitalize' }}
                onClick={(e) => handleValueChange(e, 2)}
              >
                + Add a new team
              </Button>

              <Button
                style={{ textTransform: 'capitalize' }}
                onClick={(e) => handleValueChange(e, 1)}
              >
                assign an existing team
              </Button>
            </ButtonGroup>
          )}

          {(value === 1 || mvpTeam) && <MvpTeam sendValue={getValue} />}
          {value === 2 && (
            <Grid container className={classes.containerAdd}>
              <Grid item xs={8}>
                <FormLabel className={classes.formLabel}>Name</FormLabel>
                <TextField
                  className={classes.textField}
                  required
                  fullWidth
                  variant="outlined"
                  id="teamName"
                  name="name"
                  placeholder="Team Name"
                  size="small"
                  value={teamName || ''}
                  onChange={handleNameChange}
                  error={error}
                  helperText={error ? 'Duplicate Team Name' : ' '}
                />{' '}
              </Grid>
              <Grid item xs={4}>
                <Button
                  onClick={save}
                  id="submit"
                  value="Submit"
                  type="submit"
                  disabled={teamName === ''}
                  variant="outlined"
                  color="primary"
                  className={classes.buttonSave}
                >
                  save
                </Button>
              </Grid>
            </Grid>
          )}
          <Snackbar open={open} autoHideDuration={2000} onClose={handleClose}>
            <Alert onClose={handleClose} severity="success">
              {value === 0
                ? ' Team successfully unassigned!'
                : ' Team successfully created and assigned to this MVP!'}
            </Alert>
          </Snackbar>
        </Grid>
      </form>
      <TeamMemberList />
    </Paper>
  );
}
