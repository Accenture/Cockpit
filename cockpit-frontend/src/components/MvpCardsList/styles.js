import { makeStyles } from '@material-ui/core/styles';

export default makeStyles(() => ({
  gridList: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-evenly',
    direction: 'row',
  },
  cardRouterLink: {
    textDecoration: 'none',
  },
  deleteButton: {
    top: '210px',
    left: '260px',
    position: 'absolute',
  },
  parent: {
    position: 'relative',
  },
}));
