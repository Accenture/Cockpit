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
import MvpService from '../../services/apiService';
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
  const [error, setError] = React.useState(false);

  const dispatch = useDispatch();

  function openDialog() {
    setIsOpened(true);
  }
  function closeDialog() {
    setIsOpened(false);
  }
  function isImageUrlValid(urlImage) {
    return (
      urlImage !== null &&
      (urlImage === '' ||
        urlImage.startsWith('http://') ||
        urlImage.startsWith('https://'))
    );
  }
  function confirmImageUrl() {
    if (url !== '') {
      dispatch(setImageUrl(url));
      if (isImageUrlValid(url)) {
        dispatch(setFormIsValid());
        closeDialog();
      }
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
        sprintNumber: 8,
      },
    };
    const jira = await MvpService.createNewJiraProject(newJira);
    if (jira.data) {
      dispatch(close());
      dispatch(fetchAllMvps());
      dispatch(setAlltoNull());
    } else {
      setError(true);
    }
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
                placeholder="https://..."
                autoComplete="imageUrl"
                size="small"
                error={!isImageUrlValid(url)}
                helperText={
                  isImageUrlValid(url) ? '' : 'Url starts with http(s)://'
                }
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
              inputProps={{ maxLength: 30 }}
            />
          </Grid>
          <Grid item xs={12}>
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
                <MenuItem value="TDF">TDF</MenuItem>
              </Select>
            </FormControl>
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
                setError(false);
              }}
              required
              fullWidth
              variant="outlined"
              id="jiraProjectKey"
              name="jiraProjectKey"
              placeholder="Jira Key"
              autoComplete="jiraPeojectKey"
              size="small"
              error={error}
              helperText={error ? 'Jira Project Key is invalid!' : ' '}
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
        setError(false);
      }}
      aria-labelledby="simple-modal-title"
      aria-describedby="simple-modal-description"
    >
      {body}
    </Dialog>
  );
}
