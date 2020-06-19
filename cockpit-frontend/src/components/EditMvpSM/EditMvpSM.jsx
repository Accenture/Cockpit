import React, { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import Dialog from '@material-ui/core/Dialog';
import { useParams } from 'react-router-dom';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import ButtonGroup from '@material-ui/core/ButtonGroup';
import Button from '@material-ui/core/Button';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import InformationForm from '../InformationForm/InformationForm';
import TeamManagementForm from '../TeamManagementForm/TeamManagementForm';
import {
  nameState,
  pitchState,
  entityState,
  cycleState,
  statusState,
  imageUrlState,
  mvpStartDateState,
  mvpEndDateState,
} from '../InformationForm/InformationFormSlice';
import { editSMFormState, closeEditMvpSMForm } from '../Header/HeaderSlice';
import { mvpSelector } from '../../redux/selector';
import MvpService from '../../services/service';
import { fetchAllMvps } from '../../redux/ormSlice';

import useStyles from './styles';

export default function EditMvpSMForm() {
  const [value, setValue] = useState(0);

  const classes = useStyles();
  const dispatch = useDispatch();
  const open = useSelector(editSMFormState);
  const mvpId = useParams().id;
  const mvpInfo = useSelector((state) => mvpSelector(state, mvpId));

  const name = useSelector(nameState);
  const pitch = useSelector(pitchState);
  const cycle = useSelector(cycleState);
  const entity = useSelector(entityState);
  const status = useSelector(statusState);
  const urlMvpAvatar = useSelector(imageUrlState);
  const mvpStartDate = useSelector(mvpStartDateState);
  const mvpEndDate = useSelector(mvpEndDateState);
  const handleClose = () => {
    dispatch(closeEditMvpSMForm());
  };
  const handleChange = (event, valeur) => {
    setValue(valeur);
  };
  async function submit(e) {
    e.preventDefault();
    const newJira = {
      id: mvpInfo.jira.id,
      jiraProjectKey: mvpInfo.jira.jiraProjectKey,
      currentSprint: mvpInfo.jira.currentSprint,
      jiraProjectId: mvpInfo.jira.jiraProjectId,
      mvpStartDate,
      mvpEndDate,
      mvp: {
        id: mvpInfo.id,
        name,
        entity,
        urlMvpAvatar,
        cycle,
        mvpDescription: pitch,
        status,
        team: {
          name: '',
          teamMembers: [
            {
              firstName: '',
              lastName: '',
              email: '',
            },
          ],
        },
        technologies: [
          {
            name: '',
            url: '',
          },
        ],
      },
    };
    await MvpService.updateJiraProject(newJira);
    dispatch(closeEditMvpSMForm());
    dispatch(fetchAllMvps());
  }
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
            {value === 1 && <TeamManagementForm />}
            {value === 2 && <div>Technologies</div>}
          </div>
        </div>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose} style={{ borderRadius: 20 }}>
          Cancel
        </Button>
        {value === 0 && (
          <Button
            onClick={submit}
            color="primary"
            variant="contained"
            style={{ borderRadius: 20 }}
          >
            Save
          </Button>
        )}
      </DialogActions>
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
