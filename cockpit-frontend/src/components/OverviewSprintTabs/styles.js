import { makeStyles } from '@material-ui/core/styles';
import { darkGrey } from '../../common/scss/colorVarialble.scss';

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
  title: {
    marginTop: '8px',
    fontSize: '18px',
    color: darkGrey,
    fontWeight: 'bold',
    fontFamily: "'Helvetica Neue', 'Helvetica', 'Arial', sans-serif",
  },
  technicalDebt: {
    fontSize: '16px',
    marginTop: '8px',
  },
}));
