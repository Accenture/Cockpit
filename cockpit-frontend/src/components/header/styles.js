import { makeStyles } from '@material-ui/core/styles';

export default makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  appBar: {
    height: 70,
    background: '#4a95cd',
  },
  menuButton: {
    paddingLeft: 30,
    marginRight: theme.spacing(2),
  },
  addButton: {
    position: 'absolute',
    right: 100,
    top: 15,
    color: '#ffffff',
    textTransform: 'capitalize',
    backgroundColor: 'rgba(255, 255, 255, .15)',
    backdropilter: 'blur(5px)',
    border: 'none',
    outline: 'none',
    '&:hover': {
      border: 'none',
      backgroundColor: 'rgba(255, 255, 255, .15)',
      backdropilter: 'blur(5px)',
    },
  },
}));
