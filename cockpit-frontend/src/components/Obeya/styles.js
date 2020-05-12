import { makeStyles } from '@material-ui/core/styles';

export default makeStyles(() => ({
  progress: {
    height: '15px!important',
    borderRadius: '20px',
    '& div': {
      borderRadius: '20px',
    },
  },
  progressBarTxt: {
    position: 'relative',
    top: '-18px',
    fontSize: '0.8em',
    fontWight: 'bold',
    color: 'white',
  },
  greyTitles: {
    color: 'grey',
  },
}));
