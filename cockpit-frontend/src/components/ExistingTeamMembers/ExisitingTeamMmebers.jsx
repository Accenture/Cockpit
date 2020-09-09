import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams } from 'react-router-dom';
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';
import Avatar from '@material-ui/core/Avatar';
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import { mvpSelector } from '../../redux/selector';
import useStyles from './styles';
import MvpService from '../../services/apiService';
import { getOneMvp } from '../../redux/ormSlice';

export default function ExisitingTeamMmebers() {
  const [members, setMembers] = React.useState([]);
  const [selectedMember, setSelectedMember] = React.useState(null);
  const mvpId = useParams().id;
  const mvpInfo = useSelector((state) => mvpSelector(state, mvpId));
  const classes = useStyles();
  const dispatch = useDispatch();

  useEffect(() => {
    async function getAllMembers() {
      const result = await MvpService.getMembers();
      const list = result.data.filter(
        (member) =>
          !mvpInfo.team.teamMembers.find((element) => element.id === member.id),
      );
      setMembers(list);
    }
    getAllMembers();
  }, [mvpInfo.team.teamMembers]);
  function handleSelect(value) {
    setSelectedMember(value);
  }
  async function assign() {
    await MvpService.assignTeamMember(mvpInfo.team.id, selectedMember.id);
    dispatch(getOneMvp(mvpId));
    setSelectedMember(null);
  }
  return (
    <div>
      <Autocomplete
        id="members"
        size="small"
        value={selectedMember}
        options={members}
        getOptionLabel={(option) => option.email}
        style={{ width: 275 }}
        onChange={(event, value) => handleSelect(value)}
        renderInput={(params) => (
          <TextField {...params} label="Member Email" variant="outlined" />
        )}
        getOptionSelected={(option, value) => option.email === value.email}
        renderOption={(option) => (
          <>
            <Grid container spacing={1}>
              <Grid item xs={6}>
                <Avatar alt="team member" src={option.urlTeamMemberAvatar} />
              </Grid>
              <Grid item xs={6}>
                <Typography
                  component="span"
                  variant="body2"
                  color="textPrimary"
                >
                  {option.firstName} {option.lastName}
                </Typography>
                <br />
                <Typography
                  component="span"
                  variant="body2"
                  color="textSecondary"
                >
                  {option.role}
                </Typography>
              </Grid>
            </Grid>
          </>
        )}
      />
      <Button
        onClick={assign}
        id="submit"
        value="Submit"
        type="submit"
        disabled={selectedMember === null}
        variant="outlined"
        color="primary"
        className={classes.buttonAssign}
      >
        assign
      </Button>
    </div>
  );
}
