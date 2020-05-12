import { makeStyles } from '@material-ui/core/styles';

export default makeStyles((theme) => ({
    cardRoot: {
        margin: 'auto',
        minWidth: 325,
        padding: theme.spacing(2),
    },
    bullet: {
        display: 'inline - block',
        margin: '0 2 px',
        transform: 'scale(0.8)',
    },
    title: {
        fontSize: 14,
        fontWeight: 'bold',
    },
    pos: {
        marginBottom: 12,
    },
    cardMedia: {
        height: '15vh',
        paddingTop: '56.25%', // 16:9
        backgroundSize: 'contain',
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
}));