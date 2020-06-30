import React, { useEffect, useState } from 'react';
import { Line } from 'react-chartjs-2';
import { useParams } from 'react-router-dom';
import { useSelector } from 'react-redux';
import MvpService from '../../services/service';
import { mvpSelector } from '../../redux/selector';
import {
  red,
  white,
  darkBlue,
  lightBlueShadow,
  mediumBlueShadow,
  darkBlueShadow,
} from '../../common/scss/colorVarialble.scss';

export default function BurnUpChart() {
  const [chartData, setChartData] = useState([]);
  const { id } = useParams();

  const { scopeCommitment } = useSelector((state) => mvpSelector(state)).find(
    (mvp) => mvp.id.toString() === id,
  );

  const scopeCommitmentArray = new Array(chartData.length).fill(
    scopeCommitment,
  );

  async function getData(mvpId) {
    const result = await MvpService.getBurnUpChartData(mvpId);
    setChartData(result.data);
  }

  useEffect(() => {
    getData(id);
  }, [id]);

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
          label: 'expected',
          fill: false,
          borderColor: darkBlue,
          lineTension: 0.2,
          spanGaps: true,
          pointRadius: 0,
          borderDash: [8, 4],
          borderWidth: 2,
          data: chartData.map((sprint) => sprint.expectedUsClosed),
        },
        {
          label: 'Total number of stories ',
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
    },
    responsive: true,
    datasetStrokeWidth: 3,
    pointDotStrokeWidth: 4,
    scaleLabel: "<%= Number(value).toFixed(0).replace('.', ',') + '°C'%>",
  };

  return (
    <div className="burnup-chart">
      <Line data={data} options={options} redraw />
    </div>
  );
}
