import { makeStyles } from '@material-ui/core/styles';

export default makeStyles(() => ({
  progress: {
    height: '15px!important',
    borderRadius: '20px',
    backgroundColor: 'lightgrey',
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
  subTitle: {
    fontSize: 14,
    textAlign: 'center',
  },
  redProgress: {
    height: '15px!important',
    borderRadius: '20px',
    backgroundColor: 'lightgrey',
    '& div': {
      borderRadius: '20px',
      backgroundColor: 'red',
    },
  },
  greenProgress: {
    height: '15px!important',
    borderRadius: '20px',
    backgroundColor: 'lightgrey',
    '& div': {
      borderRadius: '20px',
      backgroundColor: '#32CD32',
    },
  },
  orangeProgress: {
    height: '15px!important',
    borderRadius: '20px',
    backgroundColor: 'lightgrey',
    '& div': {
      borderRadius: '20px',
      backgroundColor: 'orange',
    },
  },
  darGreenProgress: {
    height: '15px!important',
    borderRadius: '20px',
    backgroundColor: 'lightgrey',
    '& div': {
      borderRadius: '20px',
      backgroundColor: '#006400',
    },
  },
}));
