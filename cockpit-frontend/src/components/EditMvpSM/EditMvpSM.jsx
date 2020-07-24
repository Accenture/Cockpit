import React, { useState, useEffect } from 'react';
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
  scopeCommitmentState,
  sprintNumberState,
  statusState,
  imageUrlState,
  mvpStartDateState,
  mvpEndDateState,
} from '../InformationForm/InformationFormSlice';
import { editSMFormState, closeEditMvpSMForm } from '../Header/HeaderSlice';
import { mvpSelector } from '../../redux/selector';
import MvpService from '../../services/apiService';
import { getOneMvp } from '../../redux/ormSlice';
import { fetchBurnUpData } from '../BurnUpChart/BurnUpChartSlice';
import ObeyaForm from '../ObeyaForm/ObeyaForm';

import useStyles from './styles';

export default function EditMvpSMForm() {
  const [value, setValue] = useState(0);
  const [sprint, setSprint] = useState(null);
  const classes = useStyles();
  const dispatch = useDispatch();
  const open = useSelector(editSMFormState);
  const mvpId = useParams().id;
  const mvpInfo = useSelector((state) => mvpSelector(state, mvpId));
  const [sprints, setSprints] = useState([]);
  const name = useSelector(nameState);
  const pitch = useSelector(pitchState);
  const cycle = useSelector(cycleState);
  const scopeCommitment = useSelector(scopeCommitmentState);
  const sprintNumber = useSelector(sprintNumberState);
  const entity = useSelector(entityState);
  const status = useSelector(statusState);
  const urlMvpAvatar = useSelector(imageUrlState);
  const mvpStartDate = useSelector(mvpStartDateState);
  const mvpEndDate = useSelector(mvpEndDateState);
  let mood = 0;
  let motivation = 0;
  let confidence = 0;
  useEffect(() => {
    const list = [];
    for (let i = 1; i <= mvpInfo.sprintNumber; i += 1) {
      list.push(i);
    }
    setSprints(list);
  }, [mvpInfo]);
  const handleClose = () => {
    dispatch(closeEditMvpSMForm());
  };
  const handleChange = (valeur) => {
    setValue(valeur);
  };
  async function submitMvpInfo(e) {
    e.preventDefault();
    if (sprintNumber <= 12 && sprintNumber >= mvpInfo.jira.currentSprint) {
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
          scopeCommitment,
          sprintNumber,
          mvpDescription: pitch,
          status,
        },
      };
      await MvpService.updateJiraProject(newJira);
      dispatch(closeEditMvpSMForm());
      dispatch(getOneMvp(mvpInfo.id));
      if (sprintNumber !== mvpInfo.sprintNumber) {
        dispatch(fetchBurnUpData(mvpId));
      }
    }
  }
  function getTeamMood(val) {
    mood = val;
  }
  function getTeamMotivation(val) {
    motivation = val;
  }
  function getTeamConfidence(val) {
    confidence = val;
  }
  const handleButtonClick = (number) => {
    setSprint(number);
  };
  async function submitSprintInfo(e) {
    debugger
    e.preventDefault();
    const obeya = {
      teamMood: mood,
      teamMotivation: motivation,
      teamConfidence: confidence,
    };
    await MvpService.addObeya(obeya, mvpInfo.jira.id, sprint);
    dispatch(closeEditMvpSMForm());
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
            <Button
              className={classes.buttonStyle}
              variant={!sprint ? 'contained' : 'outlined'}
              onClick={() => setSprint(null)}
            >
              Overview
            </Button>
            {sprints.map((number) => (
              <Button
                key={number}
                className={classes.buttonStyle}
                disabled={number > mvpInfo.jira.currentSprint}
                onClick={() => handleButtonClick(number)}
                variant={sprint === number ? 'contained' : 'outlined'}
              >
                Sprint {number}
              </Button>
            ))}
          </ButtonGroup>
          {!sprint && (
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
          )}
          {sprint && (
            <div>
              <Tabs
                indicatorColor="primary"
                textColor="primary"
                centered
                value={value}
                onChange={handleChange}
              >
                <Tab label="OBEYA" />
                <Tab label="Co-construction game" />
              </Tabs>
              {value === 0 && (
                <ObeyaForm
                  sprintNumber={sprint}
                  sendTeamMood={getTeamMood}
                  sendTeamMotivation={getTeamMotivation}
                  sendTeamConfidence={getTeamConfidence}
                />
              )}
              {value === 1 && <div>Co-construction game</div>}
            </div>
          )}
        </div>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose} style={{ borderRadius: 20 }}>
          Cancel
        </Button>
        {value === 0 && (
          <Button
            onClick={sprint ? submitSprintInfo : submitMvpInfo}
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
