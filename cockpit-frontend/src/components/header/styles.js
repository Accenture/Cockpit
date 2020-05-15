import { makeStyles } from '@material-ui/core/styles';

export default makeStyles((theme) => ({
  appBar: {
    height: 70,
    background: '#4a95cd',
  },
  logoButton: {
    paddingLeft: 30,
    marginRight: theme.spacing(2),
  },
  growArea: {
    flexGrow: 1,
  },
  addButton: {
    display: 'flex',
    margin: theme.spacing(2),
    color: '#ffffff',
    textTransform: 'capitalize',
    backgroundColor: 'rgba(255, 255, 255, .15)',
    border: 'none',
    outline: 'none',
    '&:hover': {
      border: 'none',
      backgroundColor: 'rgba(255, 255, 255, .15)',
      backdropilter: 'blur(5px)',
    },
  },
}));
