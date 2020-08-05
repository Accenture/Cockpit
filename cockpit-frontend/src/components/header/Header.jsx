import React from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import IconButton from '@material-ui/core/IconButton';
import { useHistory, useLocation } from 'react-router-dom';
import Button from '@material-ui/core/Button';
import { useDispatch, useSelector } from 'react-redux';
import MvpMenu from '../MvpMenu/MvpMenu';
import { showScrumMasterForm, showEditMvpSMForm } from './HeaderSlice';
// styles
import useStyles from './styles';
import 'font-awesome/css/font-awesome.min.css';
import { isScrumMasterState } from '../HomePage/HomePageSlice';

export default function Header() {
  const classes = useStyles();
  const dispatch = useDispatch();
  const isScrumMaster = useSelector(isScrumMasterState);
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
            className={classes.logoButton}
            color="inherit"
            aria-label="menu"
            onClick={returnToHomePage}
          >
            Cockpit
          </IconButton>
          {isHomePage && <MvpMenu />}
          <div className={classes.growArea} />
          {isHomePage && isScrumMaster && (
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
          )}
          {!isHomePage && isScrumMaster && (
            <Button
              variant="outlined"
              color="primary"
              className={classes.addButton}
              onClick={() => {
                dispatch(showEditMvpSMForm());
              }}
            >
              <i
                className="fa fa-pencil"
                style={{ paddingRight: 10 }}
                aria-hidden="true"
              />{' '}
              Edit MVP
            </Button>
          )}
        </Toolbar>
      </AppBar>
    </div>
  );
}
