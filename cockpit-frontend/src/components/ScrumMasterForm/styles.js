import { makeStyles } from '@material-ui/core/styles';

export default makeStyles((theme) => ({
  modal: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  paper: {
    position: 'relative',
    backgroundColor: theme.palette.background.paper,
    boxShadow: theme.shadows[5],
  },
  partition: {
    height: 150,
    backgroundColor: '#e0e0e0',
    width: '100%',
    verticalAlign: 'middle',
    textAlign: 'center',
  },
  grid: {
    padding: theme.spacing(2, 4, 1),
    width: 'calc(100%)',
    margin: 0,
    marginTop: 32,
  },
  formLabel: {
    fontWeight: 'bold',
  },
  center: {
    textAlign: 'center',
  },
  noImageLabel: {
    width: '350px',
    margin: 'auto',
    display: 'inline-block',
    marginTop: 50,
  },
  line: {
    borderBottom: 'solid 1px #e0e0e0',
    width: '100%',
  },
  addPictureButton: {
    zIndex: '1000',
    position: 'absolute',
    left: 0,
    right: 0,
    marginLeft: 'auto',
    marginRight: 'auto',
    width: 132,
    marginTop: 130,
    top: 0,
    borderRadius: 20,
  },
  textField: {
    marginTop: 8,
    'margin-bottom': 8,
  },
  closeIcon: {
    zIndex: '1000',
    position: 'absolute',
    left: 180,
    right: 0,
    marginLeft: 'auto',
    marginRight: 'auto',
    marginTop: 120,
    top: 0,
    color: 'grey',
    width: 40,
  },
  bubbleBox: {
    width: 200,
    padding: 10,
    fontWeight: 900,
    color: '#00bfb6',
    position: 'absolute',
    zIndex: 10000,
    backgroundColor: '#fff',
    borderRadius: 5,
    left: 0,
    right: 0,
    margin: 'auto',
    textAlign: 'start',
    marginTop: 10,
    top: 0,
  },
  test: {
    color: 'blue',
  },
  img: {
    height: '100%',
    width: '100%',
    objectFit: 'cover',
  },
}));
