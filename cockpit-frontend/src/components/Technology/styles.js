import { makeStyles } from '@material-ui/core/styles';

export default makeStyles((theme) => ({
  paper: {
    height: 'calc(100% - 50px)',
    width: 600,
    borderRadius: 0,
  },
  grid: {
    padding: theme.spacing(2, 4, 1),
    width: 'calc(95%)',
    margin: 0,
  },
  formLabel: {
    fontWeight: 'bold',
  },
  textField: {
    marginTop: 8,
    'margin-bottom': 8,
  },
  buttonSave: {
    borderRadius: 20,
    margin: 32,
  },
  containerAdd: {
    margin: 32,
  },
  technoList: {
    padding: '32px',
  },
  imgStyle: {
    height: 50,
    padding: theme.spacing(0, 0, 0, 1),
    marginTop: 20,
  },
  flexBox: {
    display: 'flex',
  },
}));
