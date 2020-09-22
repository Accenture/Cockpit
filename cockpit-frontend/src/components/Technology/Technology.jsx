import React, { useEffect } from 'react';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import FormLabel from '@material-ui/core/FormLabel';
import Autocomplete from '@material-ui/lab/Autocomplete';
import { useSelector, useDispatch } from 'react-redux';
import { useParams } from 'react-router-dom';
import { mvpSelector } from '../../redux/selector';
import useStyles from './styles';
import MvpService from '../../services/apiService';
import { getOneMvp } from '../../redux/ormSlice';

export default function Technology() {
  const classes = useStyles();
  const dispatch = useDispatch();

  const [open, setOpen] = React.useState(false);
  const [technoName, setTechnoName] = React.useState('');
  const [technoLogo, setTechnoLogo] = React.useState('');
  const [technologies, setTechnologies] = React.useState([]);
  const [allTechnologies, setAllTechnologies] = React.useState([]);
  const mvpId = useParams().id;
  const mvpInfo = useSelector((state) => mvpSelector(state, mvpId));
  const [value, setValue] = React.useState([]);

  useEffect(() => {
    async function getAllTechnologies() {
      const result = await MvpService.getAllTechnologies();
      setAllTechnologies(result.data);
    }
    getAllTechnologies();
  }, []);

  useEffect(() => {
    setTechnologies(mvpInfo.technologies);
  }, [mvpInfo]);
  useEffect(() => {
    setValue(technologies);
  }, [technologies]);
  function handleLogoChange(event) {
    setTechnoLogo(event.target.value);
  }
  function handleNameChange(event) {
    setTechnoName(event.target.value);
  }
  function closeForm() {
    setOpen(false);
    setTechnoLogo('');
    setTechnoName('');
  }
  async function save() {
    const technology = {
      name: technoName,
      url: technoLogo,
    };
    await MvpService.addTechnology(technology, mvpInfo.id);
    closeForm();
    dispatch(getOneMvp(mvpId));
  }
  function isImageUrlValid(urlImage) {
    return (
      urlImage !== null &&
      (urlImage === '' ||
        urlImage.startsWith('http://') ||
        urlImage.startsWith('https://'))
    );
  }
  const onTagsChange = (event, values) => {
    setValue(values);
  };
  return (
    <div>
      <Paper className={classes.paper}>
        <Autocomplete
          className={classes.technoList}
          multiple
          options={allTechnologies}
          getOptionLabel={(option) => option.name}
          value={value}
          onChange={onTagsChange}
          renderInput={(params) => (
            <TextField
              {...params}
              variant="outlined"
              label="Technology"
              placeholder="name"
            />
          )}
        />
        <Button
          onClick={() => setOpen(true)}
          variant="outlined"
          color="primary"
          className={classes.buttonSave}
        >
          + add new Technology
        </Button>
        {open && (
          <form>
            <Grid container className={classes.containerAdd}>
              <Grid item xs={8}>
                <FormLabel className={classes.formLabel}>Name</FormLabel>
                <TextField
                  className={classes.textField}
                  required
                  fullWidth
                  variant="outlined"
                  name="name"
                  placeholder="Technology Name"
                  size="small"
                  value={technoName || ''}
                  onChange={handleNameChange}
                />
              </Grid>
              <Grid item xs={8}>
                <FormLabel className={classes.formLabel}>Logo</FormLabel>
                <TextField
                  className={classes.textField}
                  required
                  fullWidth
                  variant="outlined"
                  name="logo"
                  size="small"
                  value={technoLogo || ''}
                  onChange={handleLogoChange}
                  placeholder="https://..."
                  error={!isImageUrlValid(technoLogo)}
                  helperText={
                    isImageUrlValid(technoLogo)
                      ? ''
                      : 'Url starts with http(s)://'
                  }
                />
              </Grid>
              <Grid item xs={4} />
              <Grid item xs={2} />
              <Grid item xs={3}>
                <Button
                  onClick={closeForm}
                  variant="outlined"
                  color="primary"
                  className={classes.buttonSave}
                >
                  cancel
                </Button>
              </Grid>
              <Grid item xs={3}>
                <Button
                  onClick={save}
                  disabled={
                    technoName === '' ||
                    technoLogo === '' ||
                    !isImageUrlValid(technoLogo)
                  }
                  variant="contained"
                  color="primary"
                  className={classes.buttonSave}
                >
                  add
                </Button>
              </Grid>{' '}
              <Grid item xs={2} />
            </Grid>
          </form>
        )}
      </Paper>
    </div>
  );
}
