import React, { useEffect } from 'react';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import { useParams } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { mvpSelector } from '../../redux/selector';

import useStyles from './styles';

export default function TecnologyCard() {
  const classes = useStyles();
  const mvpId = useParams().id;
  const mvpInfo = useSelector((state) => mvpSelector(state, mvpId));
  const [technologies, setTechnologies] = React.useState([]);
  useEffect(() => {
    setTechnologies(mvpInfo.technologies);
  }, [mvpInfo]);
  return (
    <Card className={classes.technologyCard}>
      <CardContent>
        <Grid container alignItems="center">
          <Grid item xs={technologies.length === 0 ? 6 : 12}>
            <Typography variant="h6">Technology</Typography>
          </Grid>
          <Grid item xs={technologies.length === 0 ? 6 : 12}>
            {technologies.length === 0 && (
              <Typography variant="subtitle2">Not defined</Typography>
            )}
            {technologies.length > 0 &&
              technologies.map((techno) => (
                <img className={classes.img} alt="No url" src={techno.url} />
              ))}
          </Grid>
        </Grid>
      </CardContent>
    </Card>
  );
}
