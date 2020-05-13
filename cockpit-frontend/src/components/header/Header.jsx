import React from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import IconButton from '@material-ui/core/IconButton';
import { useHistory, useLocation } from 'react-router-dom';

import MvpMenu from '../MvpMenu/MvpMenu';

// styles
import useStyles from './styles';

export default function Header() {
  const classes = useStyles();
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
        </Toolbar>
      </AppBar>
    </div>
  );
}
