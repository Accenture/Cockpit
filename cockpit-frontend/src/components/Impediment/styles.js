import { makeStyles } from '@material-ui/core/styles';

export default makeStyles(() => ({
  root: {
    padding: 32,
  },
  title: {
    fontWeight: 'bold',
    paddingLeft: 16,
  },
  listItem: {
    marginTop: 16,
    color: 'gray',
    fontWeight: 'bold',
  },
  noImpediment: {
    color: 'gray',
  },
  addButton: {
    borderRadius: 20,
    textTransform: 'capitalize',
    margin: '32px 0',
  },
  formLabel: {
    fontWeight: 'bold',
  },
  textField: {
    marginTop: 8,
    marginBottom: 8,
  },
  deleteButton: {
    borderRadius: 20,
    textTransform: 'capitalize',
  },
  alignment: {
    textAlign: 'right',
  },
}));
