import { makeStyles } from '@material-ui/core/styles';

export default makeStyles((theme) => ({
    root: {
        flexGrow: 1,
    },
    appBar: {
        height: 70,
        background: '#4a95cd',
    },
    menuButton: {
        paddingLeft: 30,
        marginRight: theme.spacing(2),
    },
}));