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
import { mvpSelector } from '../../redux/selector';

import useStyles from './styles';

export default function TeamMemberList() {
  let teamMembers = [];
  const classes = useStyles();
  const mvpId = useParams().id;
  const mvpInfo = useSelector((state) => mvpSelector(state, mvpId));
  if (mvpInfo.team) {
    teamMembers = mvpInfo.team.teamMembers;
  }
  return (
    <div>
      {teamMembers.length > 0 && (
        <List className={classes.root}>
          {teamMembers.map((member) => (
            <div key={member.id}>
              <ListItem alignItems="flex-start">
                <ListItemAvatar>
                  <Avatar alt="Remy Sharp" src={member.urlTeamMemberAvatar} />
                </ListItemAvatar>
                <ListItemText
                  className={classes.capitalizedText}
                  primary={`${member.firstName} ${member.lastName}`}
                  secondary={
                    <>
                      {member.email} <br />
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
    </div>
  );
}
