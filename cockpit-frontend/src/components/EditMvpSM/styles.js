import { makeStyles } from '@material-ui/core/styles';

export default makeStyles(() => ({
  root: {
    display: 'flex',
  },
  modal: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  buttonStyle: {
    height: 51,
    borderRadius: 0,
  },
  ButtonGroup: {
    marginTop: -2.5,
  },
  paper: {
    height: 'calc(100% - 50px)',
    width: 600,
    borderRadius: 0,
  },
}));
