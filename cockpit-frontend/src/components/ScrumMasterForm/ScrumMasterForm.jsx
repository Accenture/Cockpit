import React, { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import FormLabel from '@material-ui/core/FormLabel';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import FormControl from '@material-ui/core/FormControl';
import Button from '@material-ui/core/Button';
import CancelIcon from '@material-ui/icons/Cancel';
import IconButton from '@material-ui/core/IconButton';
import Dialog from '@material-ui/core/Dialog';
import { showSMFormState, close } from '../Header/HeaderSlice';
import {
  SMFormState,
  setMvpName,
  setJiraProjectKey,
  setEntity,
  setCycle,
  setFormIsValid,
  setImageUrl,
  setAlltoNull,
  imageUrlState,
  mvpState,
  jiraProjectKeyState,
  entityState,
  cycleState,
} from './ScrumMasterFormSlice';
import '../../App.scss';
import useStyles from './styles';
import MvpService from '../../services/service';
import { fetchAllMvps } from '../../redux/ormSlice';

let url = '';

export default function ScrumMasterForm() {
  const classes = useStyles();
  let btnClass = [classes.bubbleBox, 'bubble'];
  btnClass = btnClass.join(' ');
  const [isOpened, setIsOpened] = useState(false);
  const imageUrl = useSelector(imageUrlState);
  const mvpName = useSelector(mvpState);
  const jiraPK = useSelector(jiraProjectKeyState);
  const selectedEntity = useSelector(entityState);
  const cycleNumber = useSelector(cycleState);
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
    const newJira = {
      jiraProjectKey: jiraPK,
      mvp: {
        name: mvpName,
        entity: selectedEntity,
        urlMvpAvatar: imageUrl,
        cycle: cycleNumber,
        status: 'inprogress',
      },
    };
    await MvpService.createNewJiraProject(newJira);
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
                dispatch(setMvpName(e.target.value));
                dispatch(setFormIsValid());
              }}
              required
              fullWidth
              variant="outlined"
              id="mvpName"
              name="mvpName"
              placeholder="MVP Name"
              autoComplete="mvpName"
              size="small"
              inputProps={{ maxLength: 50 }}
            />
          </Grid>
          <Grid item xs={12}>
            <FormLabel className={classes.formLabel}>
              Enter your Jira Project Key
            </FormLabel>

            <TextField
              className={classes.textField}
              onChange={(e) => {
                dispatch(setJiraProjectKey(e.target.value));
                dispatch(setFormIsValid());
              }}
              required
              fullWidth
              variant="outlined"
              id="jiraProjectKey"
              name="jiraProjectKey"
              placeholder="Jira Key"
              autoComplete="jiraPeojectKey"
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
              <Select
                displayEmpty
                value={selectedEntity || ''}
                onChange={(e) => {
                  dispatch(setEntity(e.target.value));
                  dispatch(setFormIsValid());
                }}
              >
                <MenuItem value="" disabled>
                  Entity
                </MenuItem>
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
              placeholder="Cycle"
              type="number"
              inputProps={{ min: '1', step: '1' }}
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
