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
    bottom: '215px',
    left: '250px',
  },
}));
