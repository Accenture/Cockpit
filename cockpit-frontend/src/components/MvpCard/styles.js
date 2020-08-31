import { makeStyles } from '@material-ui/core/styles';

export default makeStyles((theme) => ({
  dashboardCard: {
    marginTop: '-48px',
    padding: theme.spacing(2),
  },
  mvpInfoCard: {
    margin: 'auto',
    backgroundColor: '#F5F5F9',
  },
  mvpStatusCard: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: '10px',
    height: '80%',
    backgroundColor: '#bdbdbd',
    fontSize: 16,
  },
  bullet: {
    display: 'inline - block',
    margin: '0 2 px',
    transform: 'scale(0.8)',
  },
  title: {
    fontSize: 20,
  },
  subTitle: {
    fontSize: 14,
  },
  pitch: {
    fontSize: 14,
  },
  pos: {
    marginBottom: 12,
  },
  cardMedia: {
    height: 0,
    paddingTop: '56.25%', // 16:9
  },
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
