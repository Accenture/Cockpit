import React from 'react';
import Button from '@material-ui/core/Button';
import ClickAwayListener from '@material-ui/core/ClickAwayListener';
import Grow from '@material-ui/core/Grow';
import Paper from '@material-ui/core/Paper';
import Popper from '@material-ui/core/Popper';
import MenuItem from '@material-ui/core/MenuItem';
import MenuList from '@material-ui/core/MenuList';
import { useSelector, useDispatch } from 'react-redux';
import ExpandMoreOutlinedIcon from '@material-ui/icons/ExpandMoreOutlined';
import {
  selectEP,
  selectRC,
  selectMS,
  selectGRP,
  selectTDF,
  selectSTELA,
  selectAllEntities,
  selectFilterState,
} from './mvpEntityFilterSlice';
// styles
import useStyles from './styles';

export default function MvpEntiryFilter() {
  const selectedEntity = useSelector(selectFilterState);
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
    <>
      <Button
        ref={anchorRef}
        aria-controls={open ? 'menu-list-grow' : undefined}
        aria-haspopup="true"
        variant="outlined"
        onClick={handleToggle}
        className={classes.mvpFilterMenu}
      >
        {selectedEntity}
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
                      dispatch(selectEP());
                      setOpen(false);
                    }}
                  >
                    EP
                  </MenuItem>
                  <MenuItem
                    onClick={() => {
                      dispatch(selectRC());
                      setOpen(false);
                    }}
                  >
                    RC
                  </MenuItem>
                  <MenuItem
                    onClick={() => {
                      dispatch(selectMS());
                      setOpen(false);
                    }}
                  >
                    MS
                  </MenuItem>
                  <MenuItem
                    onClick={() => {
                      dispatch(selectGRP());
                      setOpen(false);
                    }}
                  >
                    GRP
                  </MenuItem>
                  <MenuItem
                    onClick={() => {
                      dispatch(selectTDF());
                      setOpen(false);
                    }}
                  >
                    TDF
                  </MenuItem>
                  <MenuItem
                    onClick={() => {
                      dispatch(selectSTELA());
                      setOpen(false);
                    }}
                  >
                    STELA
                  </MenuItem>
                  <MenuItem
                    onClick={() => {
                      dispatch(selectAllEntities());
                      setOpen(false);
                    }}
                  >
                    ALL ENTITIES
                  </MenuItem>
                </MenuList>
              </ClickAwayListener>
            </Paper>
          </Grow>
        )}
      </Popper>
    </>
  );
}
