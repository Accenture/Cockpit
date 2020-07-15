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
}));
