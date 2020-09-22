import { makeStyles } from '@material-ui/core/styles';

export default makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    backgroundColor: theme.palette.background.paper,
  },
  formControl: {
    position: 'absolute',
    top: 0,
    marginTop: '96px',
    marginLeft: '190px',
    padding: 4,
  },
  parent: {
    textAlign: 'center',
    marginTop: 16,
  },
  dateStyle: {
    color: 'rgba(0, 0, 0, 0.54)',
    marginTop: 8,
  },
  boldText: { fontWeight: 'bold' },
  impCard: {
    marginTop: '20px',
    backgroundColor: '#F5F5F9',
    textAlign: 'cetnter',
  },
  preLine: {
    whiteSpace: 'pre-line',
  },
}));
