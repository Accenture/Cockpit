import React from 'react';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import Divider from '@material-ui/core/Divider';
import ListItemText from '@material-ui/core/ListItemText';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import Avatar from '@material-ui/core/Avatar';
import Typography from '@material-ui/core/Typography';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import Button from '@material-ui/core/Button';
import AddIcon from '@material-ui/icons/Add';
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import FormLabel from '@material-ui/core/FormLabel';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import FormControl from '@material-ui/core/FormControl';
import { mvpSelector } from '../../redux/selector';
import useStyles from './styles';

export default function TeamMemberList() {
  const [open, setOpen] = React.useState(false);
  let teamMembers = [];
  const classes = useStyles();
  const mvpId = useParams().id;
  const mvpInfo = useSelector((state) => mvpSelector(state, mvpId));
  if (mvpInfo.team) {
    teamMembers = mvpInfo.team.teamMembers;
  }
  function displayForm() {
    setOpen(true);
  }
  return (
    <div>
      <Grid container spacing={1} className={classes.grid}>
        <Grid item xs={6}>
          {teamMembers.length > 0 && (
            <List className={classes.root}>
              {teamMembers.map((member) => (
                <div key={member.id}>
                  <ListItem alignItems="flex-start">
                    <ListItemAvatar>
                      <Avatar
                        alt="Remy Sharp"
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
        <Grid item xs={6}>
          <Button
            className={classes.addButton}
            variant="outlined"
            color="primary"
            startIcon={<AddIcon />}
            onClick={displayForm}
          >
            Add A Member
          </Button>
          {open && (
            <form style={{ marginTop: 32 }}>
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
                  />
                </Grid>
                <Grid item xs={12}>
                  <FormLabel className={classes.formLabel}>Last Name</FormLabel>
                  <TextField
                    className={classes.textField}
                    required
                    fullWidth
                    variant="outlined"
                    placeholder="Member Last Name"
                    size="small"
                    inputProps={{ maxLength: 50 }}
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
                    <Select displayEmpty value="">
                      <MenuItem value="" disabled>
                        Select a Role
                      </MenuItem>
                      <MenuItem value="EP">PO</MenuItem>
                      <MenuItem value="RC">Scrum Master</MenuItem>
                      <MenuItem value="MS">Developer</MenuItem>
                      <MenuItem value="GRP">Technical Lead</MenuItem>
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
                  />
                </Grid>
                <Grid item xs={12}>
                  <FormLabel className={classes.formLabel}>
                    Avatar URL
                  </FormLabel>
                  <TextField
                    className={classes.textField}
                    required
                    fullWidth
                    variant="outlined"
                    placeholder="http://"
                    size="small"
                  />
                </Grid>
                <Grid item xs={5} />
                <Grid item xs={3}>
                  <Button className={classes.addButton}>Discard</Button>
                </Grid>
                <Grid item xs={1} />
                <Grid item xs={3}>
                  <Button
                    color="primary"
                    variant="outlined"
                    className={classes.addButton}
                  >
                    Add
                  </Button>
                </Grid>
              </Grid>
            </form>
          )}
        </Grid>
      </Grid>
    </div>
  );
}
