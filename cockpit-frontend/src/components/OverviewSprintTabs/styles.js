import { makeStyles } from '@material-ui/core/styles';
import { lightGrey } from '../../common/scss/colorVarialble.scss';

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
    backgroundColor: lightGrey,
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
  sprintNumber: { fontWeight: 'bold' },
}));
