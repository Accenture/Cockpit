import React, { useEffect } from 'react';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import Divider from '@material-ui/core/Divider';
import ListItemText from '@material-ui/core/ListItemText';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import Avatar from '@material-ui/core/Avatar';
import Typography from '@material-ui/core/Typography';
import { useSelector, useDispatch } from 'react-redux';
import { useParams } from 'react-router-dom';
import Button from '@material-ui/core/Button';
import AddIcon from '@material-ui/icons/Add';
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import FormLabel from '@material-ui/core/FormLabel';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import FormControl from '@material-ui/core/FormControl';
import Snackbar from '@material-ui/core/Snackbar';
import MuiAlert from '@material-ui/lab/Alert';
import { mvpSelector } from '../../redux/selector';
import MvpService from '../../services/service';
import { getOneMvp } from '../../redux/ormSlice';
import useStyles from './styles';

export default function TeamMemberList() {
  const [open, setOpen] = React.useState(false);
  const [snackBar, setSnackBar] = React.useState(false);
  const [firstName, setFirstName] = React.useState('');
  const [lastName, setLastName] = React.useState('');
  const [avatarUrl, setAvatarUrl] = React.useState('');
  const [role, setRole] = React.useState('');
  const [email, setEmail] = React.useState('');
  const [teamMembers, setTeamMembers] = React.useState([]);
  const [update, setUpdate] = React.useState(false);
  // const [validUrl, setValidUrl] = React.useState(true);
  const classes = useStyles();
  const dispatch = useDispatch();
  const mvpId = useParams().id;
  const mvpInfo = useSelector((state) => mvpSelector(state, mvpId));
  function closeForm() {
    setOpen(false);
    setUpdate(false);
    setFirstName('');
    setLastName('');
    setAvatarUrl('');
    setRole('');
    setEmail('');
  }
  useEffect(() => {
    if (mvpInfo.team) {
      setTeamMembers(mvpInfo.team.teamMembers);
    }
    closeForm();
  }, [mvpInfo]);

  function displayForm() {
    setOpen(true);
  }

  /* function checkURL() {
    if (avatarUrl !== '') setValidUrl(false);
    else setValidUrl(true);
  } */

  async function submit(e) {
    e.preventDefault();
    const teamMember = {
      firstName,
      lastName,
      email,
      role,
      urlTeamMemberAvatar: avatarUrl,
    };
    await MvpService.createNewTeamMember(teamMember, mvpInfo.team.id);
    dispatch(getOneMvp(mvpId));
    setFirstName('');
    setLastName('');
    setAvatarUrl('');
    setRole('');
    setEmail('');
    setSnackBar(true);
  }
  function Alert(prop) {
    return <MuiAlert elevation={6} variant="filled" {...prop} />;
  }
  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setSnackBar(false);
  };
  function fieldsValidator() {
    if (
      firstName.length === 0 ||
      lastName.length === 0 ||
      email.length === 0 ||
      role.length === 0
    )
      return true;
    return false;
  }
  function handleClick(member) {
    setUpdate(true);
    setOpen(true);
    setFirstName(member.firstName);
    setLastName(member.lastName);
    setRole(member.role);
    setEmail(member.email);
  }
  function deleteTeamMember(member) {
    MvpService.deleteTeamMember(mvpInfo.team.id, member.id);
    dispatch(getOneMvp(mvpId));
  }
  return (
    <div>
      <Grid container spacing={1} className={classes.grid}>
        <Grid item xs={6}>
          {mvpInfo.team && teamMembers.length > 0 && (
            <List className={classes.root}>
              {teamMembers.map((member) => (
                <div key={member.id}>
                  <ListItem
                    alignItems="flex-start"
                    onClick={() => handleClick(member)}
                    className={classes.item}
                  >
                    <ListItemAvatar>
                      <Avatar
                        alt="team member"
                        src={member.urlTeamMemberAvatar}
                      />
                    </ListItemAvatar>
                    <ListItemText
                      className={classes.capitalizedText}
                      primary={`${member.firstName} ${member.lastName}`}
                      secondary={
                        <>
                          {/*  <span className={classes.emailText}>{member.email}</span>
                      <br /> */}
                          <Typography
                            component="span"
                            variant="body2"
                            className={classes.inline}
                            color="textPrimary"
                          >
                            {member.role}
                          </Typography>
                        </>
                      }
                    />
                  </ListItem>
                  <Divider component="li" />
                </div>
              ))}
            </List>
          )}
          {mvpInfo.team && teamMembers.length === 0 && (
            <div className={classes.noMembers}>No Team members yet</div>
          )}
        </Grid>
        {mvpInfo.team && (
          <Grid item xs={6}>
            {!update && (
              <Button
                className={classes.addButton}
                variant="outlined"
                color="primary"
                startIcon={<AddIcon />}
                onClick={displayForm}
              >
                Add A Member
              </Button>
            )}
            {open && (
              <form onSubmit={submit}>
                <Grid container spacing={1}>
                  <Grid item xs={12}>
                    <FormLabel className={classes.formLabel}>
                      First Name
                    </FormLabel>
                    <TextField
                      className={classes.textField}
                      required
                      fullWidth
                      variant="outlined"
                      placeholder="Member First Name"
                      size="small"
                      inputProps={{ maxLength: 50 }}
                      onChange={(event) => setFirstName(event.target.value)}
                      value={firstName}
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <FormLabel className={classes.formLabel}>
                      Last Name
                    </FormLabel>
                    <TextField
                      className={classes.textField}
                      required
                      fullWidth
                      variant="outlined"
                      placeholder="Member Last Name"
                      size="small"
                      inputProps={{ maxLength: 50 }}
                      onChange={(event) => setLastName(event.target.value)}
                      value={lastName}
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <FormLabel className={classes.formLabel}>Role</FormLabel>
                    <FormControl
                      required
                      size="small"
                      fullWidth
                      variant="outlined"
                      className={classes.textField}
                    >
                      <Select
                        displayEmpty
                        onChange={(event) => setRole(event.target.value)}
                        value={role || ''}
                      >
                        <MenuItem value="" disabled>
                          Select a Role
                        </MenuItem>
                        <MenuItem value="PO">PO</MenuItem>
                        <MenuItem value="PPO">PPO</MenuItem>
                        <MenuItem value="Scrum Master">Scrum Master</MenuItem>
                        <MenuItem value="Technical Lead">
                          Technical Lead
                        </MenuItem>
                        <MenuItem value="Application Developer">
                          Application Developer
                        </MenuItem>
                        <MenuItem value="DevOps">DevOps</MenuItem>
                      </Select>
                    </FormControl>
                  </Grid>
                  <Grid item xs={12}>
                    <FormLabel className={classes.formLabel}>Email</FormLabel>
                    <TextField
                      className={classes.textField}
                      required
                      fullWidth
                      variant="outlined"
                      placeholder="Member Email"
                      size="small"
                      type="email"
                      onChange={(event) => setEmail(event.target.value)}
                      value={email}
                    />
                  </Grid>
                  {/*     <Grid item xs={12}>
                  <FormLabel className={classes.formLabel}>
                    Avatar URL
                  </FormLabel>
                  <TextField
                    className={classes.textField}
                    fullWidth
                    variant="outlined"
                    placeholder="http://"
                    size="small"
                    onChange={(event) => {
                      setAvatarUrl(event.target.value);
                    }}
                    value={avatarUrl}
                    error={!validUrl}
                    helperText={!validUrl ? 'Incorrect image URL.' : ' '}
                  />
                  <img
                    style={{ height: 0 }}
                    alt=""
                    src={avatarUrl}
                    onLoad={() => setValidUrl(true)}
                    onError={() => checkURL()}
                  />
                </Grid> */}
                  <Grid item xs={5} />
                  <Grid item xs={3}>
                    <Button className={classes.addButton} onClick={closeForm}>
                      Discard
                    </Button>
                  </Grid>
                  <Grid item xs={1} />
                  {!update && (
                    <Grid item xs={3}>
                      <Button
                        disabled={fieldsValidator()}
                        type="submit"
                        color="primary"
                        variant="outlined"
                        className={classes.addButton}
                      >
                        Add
                      </Button>
                    </Grid>
                  )}
                </Grid>
              </form>
            )}
          </Grid>
        )}
        <Snackbar open={snackBar} autoHideDuration={6000} onClose={handleClose}>
          <Alert onClose={handleClose} severity="success">
            Team Member successfully added to{' '}
            {mvpInfo.team ? mvpInfo.team.name : ''} team!
          </Alert>
        </Snackbar>
      </Grid>
    </div>
  );
}
