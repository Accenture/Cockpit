import React, { useEffect, useState } from 'react';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import Divider from '@material-ui/core/Divider';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import FormLabel from '@material-ui/core/FormLabel';
import AddIcon from '@material-ui/icons/Add';
import useStyles from './styles';

export default function Impediment(props) {
  const [impediments, setImpediments] = useState([]);
  const { sprintNumber } = props;
  const [open, setOpen] = React.useState(false);
  const { mvp } = props;
  const [name, setName] = React.useState('');
  const [explanation, setExplanation] = React.useState('');

  const classes = useStyles();

  useEffect(() => {
    const sp = mvp.jira.sprints.find(
      (sprint) => sprint.sprintNumber === sprintNumber,
    );
    setImpediments(sp.impediments);
  }, [mvp, sprintNumber]);
  function displayForm() {
    setOpen(true);
  }
  function submit(e) {
    e.preventDefault();
  }
  function fieldsValidator() {
    if (name.length === 0 || explanation.length === 0) return true;
    return false;
  }
  function closeForm() {
    setOpen(false);
  }
  return (
    <div className={classes.root}>
      {impediments.length > 0 && (
        <div>
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
      {impediments.length === 0 && (
        <div className={classes.noImpediment}>
          No impediments has been declared for this sprint
        </div>
      )}
      <Button
        className={classes.addButton}
        variant="outlined"
        color="primary"
        startIcon={<AddIcon />}
        onClick={displayForm}
      >
        Add A New Impediment
      </Button>
      {open && (
        <form onSubmit={submit}>
          <Grid container spacing={1}>
            <Grid item xs={12}>
              <FormLabel className={classes.formLabel}>Name</FormLabel>
              <TextField
                className={classes.textField}
                required
                fullWidth
                variant="outlined"
                placeholder="Impediment Name"
                size="small"
                inputProps={{ maxLength: 35 }}
                onChange={(event) => setName(event.target.value)}
                value={name}
              />
            </Grid>
            <Grid item xs={12}>
              <FormLabel className={classes.formLabel}>Explanation</FormLabel>
              <TextField
                className={classes.textField}
                required
                fullWidth
                variant="outlined"
                placeholder="Impediment Explanation"
                size="small"
                inputProps={{ maxLength: 250 }}
                onChange={(event) => setExplanation(event.target.value)}
                value={explanation}
              />
            </Grid>
            <Grid item xs={6} />
            <Grid item xs={3}>
              <Button className={classes.addButton} onClick={closeForm}>
                Cancel
              </Button>
            </Grid>
            <Grid item xs={3}>
              <Button
                disabled={fieldsValidator()}
                type="submit"
                color="primary"
                variant="outlined"
                className={classes.addButton}
              >
                Save
              </Button>
            </Grid>
          </Grid>
        </form>
      )}
    </div>
  );
}
