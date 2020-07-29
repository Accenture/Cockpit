import React, { useEffect, useState } from 'react';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import Divider from '@material-ui/core/Divider';
import useStyles from './styles';

export default function Impediment(props) {
  const [impediments, setImpediments] = useState([]);
  const { sprintNumber } = props;
  const { mvp } = props;
  const classes = useStyles();

  useEffect(() => {
    const sp = mvp.jira.sprints.find(
      (sprint) => sprint.sprintNumber === sprintNumber,
    );
    setImpediments(sp.impediments);
  }, [mvp, sprintNumber]);
  return (
    <div>
      {impediments.length > 0 && (
        <div className={classes.root}>
          <div className={classes.title}>Main impediments</div>
          <List>
            {impediments.map((impediment) => (
              <div key={impediment.id}>
                <ListItem className={classes.listItem}>
                  {impediment.name}
                </ListItem>
                <Divider component="li" />
              </div>
            ))}
          </List>
        </div>
      )}
    </div>
  );
}
