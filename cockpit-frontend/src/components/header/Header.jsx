import React from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import IconButton from '@material-ui/core/IconButton';
import MvpMenu from '../MvpMenu/MvpMenu';
// styles
import useStyles from './styles';

export default function Header() {
  const classes = useStyles();
  return (
    <div className={classes.root}>
      <AppBar position="static" className={classes.appBar}>
        <Toolbar color>
          <IconButton
            edge="start"
            className={classes.menuButton}
            color="inherit"
            aria-label="menu"
          >
            Cockpit
          </IconButton>
          <MvpMenu />
        </Toolbar>
      </AppBar>
    </div>
  );
}
