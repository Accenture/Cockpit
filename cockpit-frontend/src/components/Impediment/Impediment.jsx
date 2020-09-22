import React, { useEffect, useState, useRef } from 'react';
import List from '@material-ui/core/List';
import { useDispatch } from 'react-redux';
import ListItem from '@material-ui/core/ListItem';
import Divider from '@material-ui/core/Divider';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import FormLabel from '@material-ui/core/FormLabel';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import AddIcon from '@material-ui/icons/Add';
import MvpService from '../../services/apiService';
import { getOneMvp } from '../../redux/ormSlice';

import useStyles from './styles';

export default function Impediment(props) {
  const [impediments, setImpediments] = useState([]);
  const { sprintNumber } = props;
  const [open, setOpen] = React.useState(false);
  const [openDialog, setOpenDialog] = React.useState(false);
  const { mvp } = props;
  const [name, setName] = React.useState('');
  const [explanation, setExplanation] = React.useState('');
  const dispatch = useDispatch();
  const [update, setUpdate] = React.useState(false);
  const [number, setNumber] = React.useState(0);
  const [selectedImpediment, setSelectedImpediment] = React.useState(0);
  const classes = useStyles();
  const myRef = useRef();

  function initState() {
    setName('');
    setExplanation('');
    setOpen(false);
  }
  useEffect(() => {
    setOpen(false);
    initState();
    setUpdate(false);
  }, [sprintNumber]);

  useEffect(() => {
    const sp = mvp.jira.sprints.find(
      (sprint) => sprint.sprintNumber === sprintNumber,
    );
    setImpediments(sp.impediments);
    setNumber(sp.impediments.length);
  }, [mvp, sprintNumber]);

  function scrollToEndOfForm() {
    setTimeout(() => {
      myRef.current.scrollIntoView({ behavior: 'smooth', block: 'end' });
    }, 100);
  }

  function displayForm() {
    initState();
    setUpdate(false);
    setOpen(true);
    scrollToEndOfForm();
  }
  async function submit(e) {
    e.preventDefault();
    const impediment = {
      name,
      description: explanation,
    };
    if (!update) {
      await MvpService.addImpediment(impediment, mvp.jira.id, sprintNumber);
      dispatch(getOneMvp(mvp.id));
      setNumber(number + 1);
      setOpen(false);
    } else {
      await MvpService.updateImpediment(impediment, selectedImpediment);
      setOpen(false);
      dispatch(getOneMvp(mvp.id));
      setUpdate(false);
    }
    initState();
  }
  function fieldsValidator() {
    if (name.length === 0 || explanation.length === 0) return true;
    return false;
  }
  function closeForm() {
    initState();
    setOpen(false);
  }
  const handleCloseDialog = () => {
    setOpenDialog(false);
  };
  async function deleteImpediment() {
    await MvpService.deleteImpediment(selectedImpediment);
    dispatch(getOneMvp(mvp.id));
    handleCloseDialog();
    setOpen(false);
  }

  function openDeleteDialog(impediment) {
    setOpenDialog(true);
    setSelectedImpediment(impediment.id);
  }
  function handleEditClick(impediment) {
    setName(impediment.name);
    setExplanation(impediment.description);
    setOpen(true);
    setUpdate(true);
    setSelectedImpediment(impediment.id);
    scrollToEndOfForm();
  }

  return (
    <div ref={myRef} className={classes.root}>
      {impediments.length > 0 && (
        <div>
          <div className={classes.title}>Main impediments</div>
          <List>
            {impediments.map((impediment) => (
              <Grid container alignItems="center" key={impediment.id}>
                <Grid item xs={7}>
                  <ListItem className={classes.listItem}>
                    {impediment.name}
                  </ListItem>
                </Grid>{' '}
                <Grid item xs={2} className={classes.alignment}>
                  <Button
                    variant="outlined"
                    color="primary"
                    className={classes.deleteButton}
                    startIcon={<EditIcon />}
                    onClick={() => handleEditClick(impediment)}
                  >
                    Edit
                  </Button>{' '}
                </Grid>{' '}
                <Grid item xs={3} className={classes.alignment}>
                  <Button
                    variant="outlined"
                    color="secondary"
                    className={classes.deleteButton}
                    startIcon={<DeleteIcon />}
                    onClick={() => openDeleteDialog(impediment)}
                  >
                    Delete
                  </Button>
                </Grid>
                <Grid item xs={12}>
                  {' '}
                  <Divider component="li" />
                </Grid>
              </Grid>
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
        disabled={number >= 5}
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
                placeholder="Enter Impediment Name"
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
                multiline
                rows={5}
                required
                fullWidth
                variant="outlined"
                placeholder="Enter Impediment Explanation"
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
                {update ? 'Update' : 'Add'}
              </Button>
            </Grid>
          </Grid>
        </form>
      )}
      <Dialog open={openDialog} onClose={handleCloseDialog}>
        <DialogContent>
          <DialogContentText>
            Are you sure you want to delete the selected impediment?
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={deleteImpediment} color="primary">
            Yes
          </Button>
          <Button onClick={handleCloseDialog} color="primary">
            No
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
