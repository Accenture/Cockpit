import React, { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import FormLabel from '@material-ui/core/FormLabel';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from '@material-ui/core/FormControl';
import Button from '@material-ui/core/Button';
import CancelIcon from '@material-ui/icons/Cancel';
import IconButton from '@material-ui/core/IconButton';
import Dialog from '@material-ui/core/Dialog';
import { showSMFormState, close } from '../Header/HeaderSlice';
import {
  SMFormState,
  setMvp,
  setJiraId,
  setEntity,
  setCycle,
  setFormIsValid,
  setImageUrl,
  setAlltoNull,
  imageUrlState,
  mvpState,
  jiraIdState,
  entityState,
  cycleState,
} from './ScrumMasterFormSlice';
import '../../App.scss';
import useStyles from './styles';
import MvpService from '../../services/service';
import { fetchAllMvps } from '../../redux/ormSlice';

export default function ScrumMasterForm() {
  const classes = useStyles();

  let url = '';
  let btnClass = [classes.bubbleBox, 'bubble'];
  btnClass = btnClass.join(' ');

  const [isOpened, setIsOpened] = useState(false);
  const imageUrl = useSelector(imageUrlState);
  const mvpName = useSelector(mvpState);
  const jira = useSelector(jiraIdState);
  const selectedEntity = useSelector(entityState);
  const cycle = useSelector(cycleState);
  const isFormValid = useSelector(SMFormState);
  const open = useSelector(showSMFormState);
  const dispatch = useDispatch();

  function openDialog() {
    setIsOpened(true);
  }
  function closeDialog() {
    setIsOpened(false);
  }
  function confirmImageUrl() {
    if (url !== '') {
      dispatch(setImageUrl(url));
      dispatch(setFormIsValid());
      url = '';
      closeDialog();
    }
  }
  function removeImageUrl() {
    dispatch(setImageUrl(null));
  }
  function handleChange(event) {
    url = event.target.value;
  }
  async function submit(e) {
    e.preventDefault();
    const mvp = {
      id: jira,
      name: mvpName,
      pitch: 'Hello',
      status: 'inprogress',
      iterationNumber: cycle,
      entity: selectedEntity,
      currentSprint: 2,
      nbSprint: 0,
      mvpEndDate: '',
      mvpStartDate: '',
      mvpAvatarUrl: imageUrl,
      jiraProjectId: 10009,
      jiraBoardId: 1004,
      location: '',
      nbUsersStories: 0,
      bugsCount: 0,
      allBugsCount: 0,
      timeToFix: '',
      timeToDetect: '',
      cascade: [{}],
      sprint: [{}],
      userStories: [{}],
      bugHistories: [{}],
    };
    await MvpService.createMvp(mvp);
    dispatch(close());
    dispatch(fetchAllMvps());
    dispatch(setAlltoNull());
  }

  const body = (
    <div className={classes.paper}>
      <form onSubmit={submit}>
        <div className={classes.partition}>
          {imageUrl && (
            <img className={classes.img} alt="Invalid url" src={imageUrl} />
          )}
          {isOpened && (
            <div className={btnClass}>
              <FormLabel className={classes.formLabel}>
                Enter Image Url
              </FormLabel>
              <TextField
                onChange={handleChange}
                className={classes.textField}
                variant="outlined"
                type="url"
                id="imageUrl"
                name="imageUrl"
                placeholder="http://"
                autoComplete="imageUrl"
                size="small"
              />
            </div>
          )}
          {!imageUrl && (
            <div className={classes.noImageLabel}>
              <FormLabel className={classes.formLabel}>
                No image URL has been entered. Click on the &#34;Add
                Picture&#34; button to add one.
              </FormLabel>
            </div>
          )}
        </div>
        <div>
          <Button
            className={classes.addPictureButton}
            variant="contained"
            color="primary"
            component="span"
            onClick={isOpened ? confirmImageUrl : openDialog}
          >
            {isOpened ? 'Confirm' : 'Add Picture'}
          </Button>{' '}
        </div>
        {isOpened && (
          <IconButton className={classes.closeIcon} onClick={closeDialog}>
            <CancelIcon fontSize="large" />
          </IconButton>
        )}
        <Grid className={classes.grid} container spacing={1}>
          <Grid item xs={12}>
            <FormLabel className={classes.formLabel}>
              Choose a name for the MVP
            </FormLabel>
            <TextField
              className={classes.textField}
              onChange={(e) => {
                dispatch(setMvp(e.target.value));
                dispatch(setFormIsValid());
              }}
              required
              fullWidth
              variant="outlined"
              id="mvpName"
              name="mvpName"
              label="MVP Name"
              autoComplete="mvpName"
              size="small"
            />
          </Grid>
          <Grid item xs={12}>
            <FormLabel className={classes.formLabel}>
              Enter your Jira Project Key
            </FormLabel>

            <TextField
              className={classes.textField}
              onChange={(e) => {
                dispatch(setJiraId(e.target.value));
                dispatch(setFormIsValid());
              }}
              required
              fullWidth
              variant="outlined"
              id="jiraID"
              name="jiraID"
              label="Jira Key"
              autoComplete="jiraID"
              size="small"
            />
          </Grid>
          <Grid item xs={5}>
            <FormLabel className={classes.formLabel}>
              Choose an entity
            </FormLabel>

            <FormControl
              required
              size="small"
              fullWidth
              variant="outlined"
              className={classes.textField}
            >
              <InputLabel id="entityID">Entity</InputLabel>
              <Select
                labelId="entityID"
                id="entity"
                label="Entity"
                value={selectedEntity || ''}
                onChange={(e) => {
                  dispatch(setEntity(e.target.value));
                  dispatch(setFormIsValid());
                }}
              >
                <MenuItem value="EP">EP</MenuItem>
                <MenuItem value="RC">RC</MenuItem>
                <MenuItem value="MS">MS</MenuItem>
                <MenuItem value="GRP">GRP</MenuItem>
              </Select>
            </FormControl>
          </Grid>
          <Grid item xs={2} />
          <Grid item xs={5}>
            <FormLabel className={classes.formLabel}>Enter a cycle</FormLabel>

            <TextField
              className={classes.textField}
              required
              onChange={(e) => {
                dispatch(setCycle(e.target.value));
                dispatch(setFormIsValid());
              }}
              fullWidth
              variant="outlined"
              id="cycleID"
              name="cycleID"
              label="Cycle"
              type="number"
              autoComplete="cycleID"
              size="small"
            />
          </Grid>
          <Grid item xs={12} className={classes.center}>
            <FormLabel>
              You need to fill all the fields before submitting an MVP
            </FormLabel>
          </Grid>
        </Grid>{' '}
        <div className={classes.line} />
        <Button
          fullWidth
          variant="text"
          id="submit"
          value="Submit"
          type="submit"
          size="large"
          disabled={!isFormValid}
        >
          Add MVP
        </Button>
      </form>
    </div>
  );
  return (
    <Dialog
      className={classes.modal}
      open={open}
      onClose={() => {
        dispatch(close());
        removeImageUrl();
        dispatch(setAlltoNull());
        closeDialog();
      }}
      aria-labelledby="simple-modal-title"
      aria-describedby="simple-modal-description"
    >
      {body}
    </Dialog>
  );
}
