import { makeStyles } from '@material-ui/core/styles';

export default makeStyles(() => ({
  technologyCard: {
    marginTop: '20px',
    backgroundColor: '#F5F5F9',
    textAlign: 'cetnter',
  },
  img: {
    width: '30%',
    objectFit: 'cover',
  },
  root: {
    display: 'flex',
    flexFlow: 'wrap',
    '& > *': {
      margin: 7,
    },
  },
}));
