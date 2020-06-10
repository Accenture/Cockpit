import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import ButtonGroup from '@material-ui/core/ButtonGroup';
import Button from '@material-ui/core/Button';
import { editSMFormState, closeEditMvpSMForm } from '../Header/HeaderSlice';

export default function EditMvpSMForm() {
  const dispatch = useDispatch();
  const open = useSelector(editSMFormState);
  const handleClose = () => {
    dispatch(closeEditMvpSMForm());
  };
  const body = (
    <div>
      <DialogContent>
        <DialogContentText id="alert-dialog-description">
          <ButtonGroup color="primary" orientation="vertical">
            <Button variant="contained">Overview</Button>
            <Button disabled>Sprint 0</Button>
            <Button disabled>Sprint 1</Button>
            <Button disabled>Sprint 2</Button>
            <Button disabled>Sprint 3</Button>
            <Button disabled>Sprint 4</Button>
            <Button disabled>Sprint 5</Button>
            <Button disabled>Sprint 6</Button>
          </ButtonGroup>
        </DialogContentText>
      </DialogContent>
      <DialogActions>
        {/* <Button onClick={handleClose} color="primary">
          Disagree
        </Button>
        <Button onClick={handleClose} color="primary" autoFocus>
          Agree
  </Button> */}
      </DialogActions>
    </div>
  );
  return (
    <div>
      <Dialog
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
