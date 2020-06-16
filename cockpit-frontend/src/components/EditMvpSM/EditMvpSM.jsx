import React, { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import Dialog from '@material-ui/core/Dialog';
// import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import ButtonGroup from '@material-ui/core/ButtonGroup';
import Button from '@material-ui/core/Button';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import InformationForm from '../InformationForm/InformationForm';

import { editSMFormState, closeEditMvpSMForm } from '../Header/HeaderSlice';
import useStyles from './styles';

export default function EditMvpSMForm() {
  const [value, setValue] = useState(0);

  const classes = useStyles();
  const dispatch = useDispatch();
  const open = useSelector(editSMFormState);
  const handleClose = () => {
    dispatch(closeEditMvpSMForm());
  };
  const handleChange = (event, valeur) => {
    setValue(valeur);
  };
  const body = (
    <div>
      <DialogContent>
        <div className={classes.root}>
          <ButtonGroup
            color="primary"
            orientation="vertical"
            className={classes.ButtonGroup}
          >
            <Button className={classes.buttonStyle} variant="contained">
              Overview
            </Button>

            <Button className={classes.buttonStyle} disabled>
              Sprint 1
            </Button>
            <Button className={classes.buttonStyle} disabled>
              Sprint 2
            </Button>
            <Button className={classes.buttonStyle} disabled>
              Sprint 3
            </Button>
            <Button className={classes.buttonStyle} disabled>
              Sprint 4
            </Button>
            <Button className={classes.buttonStyle} disabled>
              Sprint 5
            </Button>
            <Button className={classes.buttonStyle} disabled>
              Sprint 6
            </Button>
            <Button className={classes.buttonStyle} disabled>
              Sprint 7
            </Button>
            <Button className={classes.buttonStyle} disabled>
              Sprint 8
            </Button>
          </ButtonGroup>
          <div>
            <Tabs
              indicatorColor="primary"
              textColor="primary"
              centered
              value={value}
              onChange={handleChange}
            >
              <Tab label="Information" />
              <Tab label="Team" />
              <Tab label="Technologies" />
            </Tabs>
            {value === 0 && <InformationForm />}
            {value === 1 && <div>Teams</div>}
            {value === 2 && <div>Technologies</div>}
          </div>
        </div>
      </DialogContent>
      {/*  <DialogActions>
        <Button onClick={handleClose} style={{ borderRadius: 20 }}>
          Cancel
        </Button>
        <Button
          onClick={handleClose}
          color="primary"
          variant="contained"
          autoFocus
          style={{ borderRadius: 20 }}
        >
          Save
        </Button>
   </DialogActions> */}
    </div>
  );
  return (
    <div>
      <Dialog
        maxWidth="xl"
        className={classes.modal}
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        {body}
      </Dialog>
    </div>
  );
}
