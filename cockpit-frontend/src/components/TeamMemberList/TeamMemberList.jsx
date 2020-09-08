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
import AssignmentIndIcon from '@material-ui/icons/AssignmentInd';
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import FormLabel from '@material-ui/core/FormLabel';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import FormControl from '@material-ui/core/FormControl';
import Snackbar from '@material-ui/core/Snackbar';
import MuiAlert from '@material-ui/lab/Alert';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import { mvpSelector } from '../../redux/selector';
import MvpService from '../../services/apiService';
import { getOneMvp } from '../../redux/ormSlice';
import useStyles from './styles';
import ExisitingTeamMmebers from '../ExistingTeamMembers/ExisitingTeamMmebers';

export default function TeamMemberList() {
  const [open, setOpen] = React.useState(false);
  const [assign, setAssign] = React.useState(false);
  const [snackBar, setSnackBar] = React.useState(false);
  const [firstName, setFirstName] = React.useState('');
  const [lastName, setLastName] = React.useState('');
  const [avatarUrl, setAvatarUrl] = React.useState('');
  const [role, setRole] = React.useState('');
  const [email, setEmail] = React.useState('');
  const [teamMembers, setTeamMembers] = React.useState([]);
  const [update, setUpdate] = React.useState(false);
  const [openDialog, setOpenDialog] = React.useState(false);
  const [selectedMember, setSelectedMember] = React.useState({});
  const [unassign, setUnassign] = React.useState(false);

  const classes = useStyles();
  const dispatch = useDispatch();
  const mvpId = useParams().id;
  const mvpInfo = useSelector((state) => mvpSelector(state, mvpId));
  function initState() {
    setFirstName('');
    setLastName('');
    setAvatarUrl('');
    setRole('');
    setEmail('');
  }
  function closeForm() {
    initState();
    setOpen(false);
    setUpdate(false);
    setUnassign(false);
  }
  useEffect(() => {
    if (mvpInfo.team) {
      setTeamMembers(mvpInfo.team.teamMembers);
    }
    closeForm();
    // eslint-disable-next-line
  }, [mvpInfo]);

  function displayForm() {
    setOpen(true);
    setAssign(false);
  }

  const handleClickOpen = () => {
    setOpenDialog(true);
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
  };

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
    initState();
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
    setSelectedMember(member);
  }
  async function deleteTeamMember() {
    await MvpService.deleteTeamMember(mvpInfo.team.id, selectedMember.id);
    dispatch(getOneMvp(mvpId));
    handleCloseDialog();
  }
  async function unassignTeamMember() {
    await MvpService.unassignTeamMember(mvpInfo.team.id, selectedMember.id);
    dispatch(getOneMvp(mvpId));
    handleCloseDialog();
  }
  function handleUnassignClick() {
    setUnassign(true);
    handleClickOpen();
  }
  return (
    <div>
      <Grid container spacing={1} className={classes.grid}>
        <Grid item xs={6}>
          {mvpInfo.team && teamMembers.length > 0 && (
            <div>
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
            </div>
          )}
          {mvpInfo.team && teamMembers.length === 0 && (
            <div className={classes.noMembers}>No Team members yet</div>
          )}
        </Grid>
        {mvpInfo.team && (
          <Grid item xs={6}>
            {!update && (
              <Grid container spacing={1}>
                <Grid item xs={6}>
                  <Button
                    className={classes.addButton}
                    variant={open ? 'contained' : 'outlined'}
                    color="primary"
                    startIcon={<AddIcon />}
                    onClick={displayForm}
                  >
                    Add New Member
                  </Button>
                </Grid>
                <Grid item xs={6}>
                  <Button
                    className={classes.addButton}
                    variant={assign ? 'contained' : 'outlined'}
                    color="primary"
                    startIcon={<AssignmentIndIcon />}
                    onClick={() => {
                      setAssign(true);
                      setOpen(false);
                    }}
                  >
                    Assign A Member
                  </Button>
                </Grid>
              </Grid>
            )}
            {assign && <ExisitingTeamMmebers />}
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
                        <MenuItem value="Data Scientist">
                          Data Scientist
                        </MenuItem>
                        <MenuItem value="UI">UI</MenuItem>
                        <MenuItem value="UX">UX</MenuItem>
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

                  {update && (
                    <Grid item xs={5}>
                      <Button
                        variant="outlined"
                        color="secondary"
                        className={classes.deleteButton}
                        onClick={handleClickOpen}
                      >
                        Delete
                      </Button>
                    </Grid>
                  )}
                  <Grid item xs={3}>
                    <Button
                      className={classes.deleteButton}
                      onClick={closeForm}
                      variant="outlined"
                    >
                      Discard
                    </Button>
                  </Grid>
                  <Grid item xs={4} />

                  {!update && (
                    <Grid item xs={5}>
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
                  {update && (
                    <Grid item xs={12}>
                      <Button
                        variant="outlined"
                        color="primary"
                        className={classes.unassignButton}
                        onClick={handleUnassignClick}
                      >
                        Unassign from Team
                      </Button>
                    </Grid>
                  )}
                </Grid>
              </form>
            )}
          </Grid>
        )}
        <Snackbar open={snackBar} autoHideDuration={3000} onClose={handleClose}>
          <Alert onClose={handleClose} severity="success">
            Team Member successfully added to{' '}
            {mvpInfo.team ? mvpInfo.team.name : ''} team!
          </Alert>
        </Snackbar>
        <Dialog open={openDialog} onClose={handleCloseDialog}>
          <DialogContent>
            <DialogContentText>
              {unassign
                ? 'Are you sure you want to unassign the selected Team Member from this team ?'
                : 'Are you sure you want to delete the selected Team Member definitely ?'}
            </DialogContentText>
          </DialogContent>
          <DialogActions>
            <Button
              onClick={unassign ? unassignTeamMember : deleteTeamMember}
              color="primary"
            >
              Yes
            </Button>
            <Button onClick={handleCloseDialog} color="primary">
              No
            </Button>
          </DialogActions>
        </Dialog>
      </Grid>
    </div>
  );
}
