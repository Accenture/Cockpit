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
    // margin: '32px 0 0 32px',
  },
}));
