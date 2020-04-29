import React from 'react';
import Button from '@material-ui/core/Button';
import ClickAwayListener from '@material-ui/core/ClickAwayListener';
import Grow from '@material-ui/core/Grow';
import Paper from '@material-ui/core/Paper';
import Popper from '@material-ui/core/Popper';
import MenuItem from '@material-ui/core/MenuItem';
import MenuList from '@material-ui/core/MenuList';
import { makeStyles } from '@material-ui/core/styles';
import { useSelector, useDispatch } from 'react-redux';
import ExpandMoreOutlinedIcon from '@material-ui/icons/ExpandMoreOutlined';

import {
  selectCandidates,
  selectInProgress,
  selectTransferred,
  selectMvpState,
} from './mvpMenuSlice';

const useStyles = makeStyles((theme) => ({
  margin: {
    margin: theme.spacing(2),
  },
}));

export default function MvpMenu() {
  const selectedMvpState = useSelector(selectMvpState);
  const dispatch = useDispatch();
  const classes = useStyles();
  const [open, setOpen] = React.useState(false);
  const anchorRef = React.useRef(null);
  const handleToggle = () => {
    setOpen((prevOpen) => !prevOpen);
  };

  const handleClose = (event) => {
    if (anchorRef.current && anchorRef.current.contains(event.target)) {
      return;
    }
    setOpen(false);
  };

  // return focus to the button when we transitioned from !open -> open
  const prevOpen = React.useRef(open);

  React.useEffect(() => {
    if (prevOpen.current === true && open === false) {
      anchorRef.current.focus();
    }
    prevOpen.current = open;
  }, [open]);

  return (
    <div>
      <Button
        ref={anchorRef}
        aria-controls={open ? 'menu-list-grow' : undefined}
        aria-haspopup="true"
        variant="contained"
        color="primary"
        onClick={handleToggle}
        className={classes.margin}
      >
        {selectedMvpState}
        <ExpandMoreOutlinedIcon />
      </Button>
      <Popper
        open={open}
        anchorEl={anchorRef.current}
        role={undefined}
        transition
        disablePortal
      >
        {({ TransitionProps, placement }) => (
          <Grow
            {...TransitionProps}
            style={{
              transformOrigin: placement === 'center bottom',
            }}
          >
            <Paper>
              <ClickAwayListener onClickAway={handleClose}>
                <MenuList autoFocusItem={open} id="menu-list-grow">
                  <MenuItem
                    onClick={() => {
                      dispatch(selectCandidates());
                      setOpen(false);
                    }}
                  >
                    Candidates
                  </MenuItem>
                  <MenuItem
                    onClick={() => {
                      dispatch(selectInProgress());
                      setOpen(false);
                    }}
                  >
                    In Progress
                  </MenuItem>
                  <MenuItem
                    onClick={() => {
                      dispatch(selectTransferred());
                      setOpen(false);
                    }}
                  >
                    Transferred
                  </MenuItem>
                </MenuList>
              </ClickAwayListener>
            </Paper>
          </Grow>
        )}
      </Popper>
    </div>
  );
}
