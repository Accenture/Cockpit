import { makeStyles } from '@material-ui/core/styles';

export default makeStyles((theme) => ({
  root: {
    width: '100%',
    maxWidth: '26ch',
    backgroundColor: theme.palette.background.paper,
    margin: 32,
  },
  inline: {
    display: 'inline',
    color: 'gray',
  },
  noMembers: {
    margin: 32,
    color: 'gray',
  },
  capitalizedText: {
    textTransform: 'capitalize',
  },
  emailText: {
    textTransform: 'none',
    color: '#3f51b5',
  },
}));
