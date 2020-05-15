import { makeStyles } from '@material-ui/core/styles';

export default makeStyles((theme) => ({
  mvpStateMenu: {
    margin: theme.spacing(2),
    color: '#ffffff',
    backgroundColor: 'rgba(255, 255, 255, .15)',
    border: 'none',
    outline: 'none',
    '&:hover': {
      border: 'none',
      backgroundColor: 'rgba(255, 255, 255, .15)',
    },
  },
}));
