import { makeStyles } from '@material-ui/core/styles';

export default makeStyles((theme) => ({
  root: {
    width: '100%',
    maxWidth: '26ch',
    backgroundColor: theme.palette.background.paper,
  },
  inline: {
    display: 'inline',
    color: 'gray',
  },
  noMembers: {
    color: 'gray',
  },
  capitalizedText: {
    textTransform: 'capitalize',
  },
  emailText: {
    textTransform: 'none',
    color: '#3f51b5',
  },
  addButton: {
    borderRadius: 20,
    textTransform: 'capitalize',
    margin: '32px 0',
  },
  grid: {
    padding: theme.spacing(2, 4, 1),
    width: 'calc(100%)',
  },
  formLabel: {
    fontWeight: 'bold',
  },
  textField: {
    marginTop: 8,
    marginBottom: 8,
  },
}));
