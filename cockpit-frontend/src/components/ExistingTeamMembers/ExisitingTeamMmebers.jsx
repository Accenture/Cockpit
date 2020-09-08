import React, { useEffect } from 'react';
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import Avatar from '@material-ui/core/Avatar';
import Typography from '@material-ui/core/Typography';
// import { makeStyles } from '@material-ui/core/styles';
import MvpService from '../../services/apiService';

export default function ExisitingTeamMmebers() {
  const [members, setMembers] = React.useState([]);

  useEffect(() => {
    async function getAllMembers() {
      const list = await MvpService.getMembers();
      setMembers(list.data);
    }
    getAllMembers();
  });
  return (
    <div>
      <Autocomplete
        id="members"
        options={members}
        getOptionLabel={(option) => option.email}
        style={{ width: 300 }}
        renderInput={(params) => (
          <TextField {...params} label="Member Email" variant="outlined" />
        )}
        renderOption={(option) => (
          <>
            <span>{option.email}</span>({option.firstName}) {option.lastName}
          </>
        )}
      />
    </div>
  );
}
