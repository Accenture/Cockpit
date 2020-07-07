import React, { useEffect } from 'react';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import Avatar from '@material-ui/core/Avatar';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import useStyles from './styles';
import { mvpSelector } from '../../redux/selector';

export default function TeamCard() {
  const classes = useStyles();
  const mvpId = useParams().id;
  const mvpInfo = useSelector((state) => mvpSelector(state, mvpId));
  const [teamMembers, setTeamMembers] = React.useState([]);
  useEffect(() => {
    if (mvpInfo.team) {
      setTeamMembers(mvpInfo.team.teamMembers);
    }
  }, [mvpInfo]);
  return (
    <Card className={classes.teamCard}>
      <CardContent>
        <Grid container alignItems="center">
          {teamMembers.length === 0 && (
            <Grid item xs={6}>
              <Typography variant="h6">Team</Typography>
            </Grid>
          )}
          <Grid item xs={6}>
            <Typography
              variant="subtitle2"
              className={mvpInfo.team ? classes.teamName : ''}
            >
              {mvpInfo.team ? mvpInfo.team.name : 'Not defined'}
            </Typography>
          </Grid>
        </Grid>
        {teamMembers.length > 0 && (
          <List>
            {teamMembers.map((member) => (
              <div key={member.id}>
                <ListItem alignItems="flex-start" className={classes.listItem}>
                  <ListItemAvatar>
                    <Avatar
                      alt="No Image"
                      src={member.urlTeamMemberAvatar}
                      className={classes.uppercaseText}
                    >
                      {member.firstName.charAt(0)}
                      {member.lastName.charAt(0)}
                    </Avatar>
                  </ListItemAvatar>
                  <ListItemText
                    className={classes.capitalizedText}
                    primary={`${member.firstName} ${member.lastName}`}
                    secondary={
                      <>
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
              </div>
            ))}
          </List>
        )}
      </CardContent>
    </Card>
  );
}
