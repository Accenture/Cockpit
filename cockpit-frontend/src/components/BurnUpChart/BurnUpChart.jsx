import React, { useEffect } from 'react';
import { Line } from 'react-chartjs-2';
import { useParams } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import Popover from '@material-ui/core/Popover';
import Typography from '@material-ui/core/Typography';
import { mvpSelector } from '../../redux/selector';
import {
  red,
  white,
  darkBlue,
  lightBlueShadow,
  mediumBlueShadow,
  darkBlueShadow,
} from '../../common/scss/colorVarialble.scss';
import {
  fetchBurnUpData,
  burnUpChartState,
  initState,
} from './BurnUpChartSlice';
import useStyles from './styles';

export default function BurnUpChart() {
  const classes = useStyles();

  const chartData = useSelector(burnUpChartState);
  const { id } = useParams();
  const dispatch = useDispatch();
  const { scopeCommitment } = useSelector((state) => mvpSelector(state)).find(
    (mvp) => mvp.id.toString() === id,
  );

  const scopeCommitmentArray = new Array(chartData.length);
  if (scopeCommitment !== 0) {
    scopeCommitmentArray.fill(scopeCommitment);
  }
  const [anchorEl, setAnchorEl] = React.useState(null);
  const [left, setLeft] = React.useState(0);
  const [text, setText] = React.useState('');
  const handlePopoverOpen = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handlePopoverClose = () => {
    setAnchorEl(null);
  };

  const open = Boolean(anchorEl);
  useEffect(() => {
    dispatch(initState());
    dispatch(fetchBurnUpData(id));
  }, [dispatch, id]);

  const data = (canvas) => {
    // style for filled chart
    const ctx = canvas.getContext('2d');
    const gradient = ctx.createLinearGradient(0, 0, 0, 600);
    gradient.addColorStop(0, lightBlueShadow);
    gradient.addColorStop(0.5, mediumBlueShadow);
    gradient.addColorStop(1, darkBlueShadow);

    return {
      labels: chartData.map((sprint) => sprint.sprintId),
      datasets: [
        {
          label: 'User Stories Closed',
          backgroundColor: gradient,
          borderColor: darkBlue,
          borderWidth: 2,
          pointColor: white,
          pointHighlightFill: white,
          lineTension: 0,
          data: chartData.map((sprint) => sprint.usClosed),
        },
        {
          label: 'Expected',
          fill: false,
          borderColor: darkBlue,
          lineTension: 0.2,
          spanGaps: true,
          pointRadius: 0,
          borderDash: [8, 4],
          borderWidth: 2,
          data: chartData.map((sprint) => sprint.expectedUsClosed),
          hidden: true,
        },
        {
          label: 'Total number of stories',
          fill: false,
          borderColor: darkBlue,
          lineTension: 0.1,
          data: chartData.map((sprint) => sprint.totalStories),
        },
        {
          label: 'Forecast',
          fill: false,
          borderColor: darkBlue,
          borderDash: [4, 2],
          lineTension: 0,
          data: chartData.map((sprint) => sprint.projectionUsClosed),
          hidden: true,
        },
        {
          label: 'Scope Commitment',
          fill: false,
          borderColor: red,
          lineTension: 0.1,
          data: scopeCommitmentArray,
        },
      ],
    };
  };

  const options = {
    title: {
      display: true,
      text: 'Burn Up Chart',
      fontSize: '18',
    },
    legend: {
      position: 'bottom',
      labels: {
        filter(item) {
          const lastData = chartData.find(
            (sprint) => sprint.sprintId === chartData.length,
          );
          if (
            lastData != null &&
            item.text.includes('Forecast') &&
            lastData.usClosed != null
          )
            return false;
          return true;
        },
      },
      onHover(event, itemId) {
        if (itemId.datasetIndex === 0) {
          setLeft(70);
          setText('represents the accumulation of US closed in each sprint');
        }
        if (itemId.datasetIndex === 1) {
          setLeft(220);
          setText(
            'reflects the total of [the US done in the previous sprints + the current sprint scope]',
          );
        }
        if (itemId.datasetIndex === 2) {
          setLeft(350);
          setText(
            'reflects the total of [the US done in the previous sprints + the average]',
          );
        }

        if (itemId.datasetIndex === 3) {
          setLeft(500);
          setText('reflects the sum of Users stories in backlog');
        }
        if (itemId.datasetIndex === 4) {
          setLeft(620);
          setText(
            'reflects the number of US identified by the team during the scoping phase and commits implementing them during the cycle',
          );
        }
        handlePopoverOpen(event);
      },
      onLeave() {
        handlePopoverClose();
      },
    },
    responsive: true,
    datasetStrokeWidth: 3,
    pointDotStrokeWidth: 4,
    scaleLabel: "<%= Number(value).toFixed(0).replace('.', ',') + '°C'%>",
    scales: {
      yAxes: [
        {
          scaleLabel: {
            display: true,
            labelString: 'User Story',
          },
        },
      ],
      xAxes: [
        {
          scaleLabel: {
            display: true,
            labelString: 'Sprint',
          },
        },
      ],
    },
  };

  return (
    <div className="burnup-chart">
      <Line data={data} options={options} redraw id="canvas" />
      <Popover
        style={{ left }}
        open={open}
        anchorEl={anchorEl}
        onClose={handlePopoverClose}
        disableRestoreFocus
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'left',
        }}
        transformOrigin={{
          vertical: 'top',
          horizontal: 'left',
        }}
      >
        <Typography className={classes.popover}>{text}</Typography>
      </Popover>
    </div>
  );
}
