import React, { useState, useEffect } from 'react';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import Paper from '@material-ui/core/Paper';
import FormControl from '@material-ui/core/FormControl';
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';
import ButtonGroup from '@material-ui/core/ButtonGroup';
import TextField from '@material-ui/core/TextField';
import FormLabel from '@material-ui/core/FormLabel';
import Snackbar from '@material-ui/core/Snackbar';
import MuiAlert from '@material-ui/lab/Alert';
import { useParams } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import MvpService from '../../services/service';
import { setselectedTeam, selectedTeamState } from './TeamManagementFormSlice';
import useStyles from './styles';

export default function TeamManagementForm() {
  const classes = useStyles();

  const [teams, setTeams] = useState([]);
  const [value, setValue] = useState(0);
  const [teamName, setTeamName] = useState('');
  const [open, setOpen] = React.useState(false);
  const dispatch = useDispatch();
  const selectedTeam = useSelector(selectedTeamState);
  const mvpId = useParams().id;
  function handleChange(event) {
    const found = teams.find((element) => element.id === event.target.value);
    dispatch(setselectedTeam(found));
  }
  useEffect(() => {
    async function getAllTeam() {
      const result = await MvpService.getTeams();
      setTeams(result.data);
    }
    getAllTeam();
  }, []);
  const handleValueChange = (event, valeur) => {
    setValue(valeur);
  };
  async function save(event) {
    event.preventDefault();
    const team = {
      name: teamName,
    };
    await MvpService.createNewTeam(team, mvpId);
    setOpen(true);
    setTeamName('');
  }
  function handleNameChange(event) {
    setTeamName(event.target.value);
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
  return (
    <Paper className={classes.paper}>
      <form>
        <Grid className={classes.grid} container spacing={1}>
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

          {value === 1 && (
            <FormControl
              required
              size="small"
              fullWidth
              variant="outlined"
              style={{ marginTop: 32 }}
            >
              <Select
                displayEmpty
                onChange={handleChange}
                value={selectedTeam.id || ''}
              >
                <MenuItem disabled value="">
                  select a team
                </MenuItem>
                {teams.map((team) =>
                  team.name !== '' ? (
                    <MenuItem key={team.id} value={team.id}>
                      {team.name}
                    </MenuItem>
                  ) : (
                    ''
                  ),
                )}
              </Select>
            </FormControl>
          )}
          {value === 2 && (
            <Grid container style={{ marginTop: 32 }}>
              <Grid item xs={8}>
                <FormLabel className={classes.formLabel}>Name</FormLabel>
                <TextField
                  className={classes.textField}
                  required
                  fullWidth
                  variant="outlined"
                  id="mvpName"
                  name="name"
                  placeholder="MVP Name"
                  size="small"
                  value={teamName || ''}
                  onChange={handleNameChange}
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
              <Snackbar
                open={open}
                autoHideDuration={6000}
                onClose={handleClose}
              >
                <Alert onClose={handleClose} severity="success">
                  Team successfully created and assigned to this MVP !
                </Alert>
              </Snackbar>
            </Grid>
          )}
        </Grid>
      </form>
    </Paper>
  );
}
