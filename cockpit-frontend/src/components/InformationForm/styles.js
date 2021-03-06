import { makeStyles } from '@material-ui/core/styles';

export default makeStyles((theme) => ({
  paper: {
    height: 'calc(100% - 50px)',
    width: 600,
    borderRadius: 0,
  },
  grid: {
    padding: theme.spacing(2, 4, 1),
    width: 'calc(80%)',
    margin: 0,
  },
  formLabel: {
    fontWeight: 'bold',
  },
  textField: {
    marginTop: 8,
    'margin-bottom': 8,
  },
  imgStyle: {
    height: 50,
    width: 80,
    padding: theme.spacing(0, 0, 0, 1),
    marginTop: 25,
  },
}));
