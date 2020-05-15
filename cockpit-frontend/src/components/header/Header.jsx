import React from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import IconButton from '@material-ui/core/IconButton';
import { useHistory, useLocation } from 'react-router-dom';

import Button from '@material-ui/core/Button';
import { useDispatch } from 'react-redux';
import MvpMenu from '../MvpMenu/MvpMenu';
import { showScrumMasterForm } from './HeaderSlice';
// styles
import useStyles from './styles';

export default function Header() {
  const classes = useStyles();
  const dispatch = useDispatch();

  const history = useHistory();
  const returnToHomePage = () => {
    const path = '/';
    history.push(path);
  };
  const isHomePage = useLocation().pathname === '/';
  return (
    <div className={classes.root}>
      <AppBar position="static" className={classes.appBar}>
        <Toolbar>
          <IconButton
            edge="start"
            className={classes.menuButton}
            color="inherit"
            aria-label="menu"
            onClick={returnToHomePage}
          >
            Cockpit
          </IconButton>
          {isHomePage && <MvpMenu />}
          <Button
            variant="outlined"
            color="primary"
            className={classes.addButton}
            onClick={() => {
              dispatch(showScrumMasterForm());
            }}
          >
            + New MVP
          </Button>
        </Toolbar>
      </AppBar>
    </div>
  );
}
