import React, { useEffect, useState } from 'react';
import { Line } from 'react-chartjs-2';
import { useParams } from 'react-router-dom';
import MvpService from '../../services/service';

export default function BurnUpChart() {
  const [chartData, setChartData] = useState([]);
  const { id } = useParams();
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
    gradient.addColorStop(0, 'rgba(18,64,155,0.5)');
    gradient.addColorStop(0.5, 'rgba(18,64,155,0.08)');
    gradient.addColorStop(1, 'rgba(18,64,155,0)');

    return {
      labels: ['0', '1', '2', '3', '4', '5', '6', '7'],
      datasets: [
        {
          label: 'User Stories Closed',
          backgroundColor: gradient,
          borderColor: 'rgba(18,64,155, 0.6)',
          borderWidth: 2,
          pointColor: '#fff',
          pointStrokeColor: '#ff6c23',
          pointHighlightFill: '#fff',
          pointHighlightStroke: '#ff6c23',
          lineTension: 0,
          data: chartData.map((sprint) => sprint.usClosed),
        },
        {
          label: 'expected',
          fill: false,
          borderColor: 'rgba(18,64,155, 0.6)',
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
          borderColor: 'rgba(18,64,155, 0.6)',
          lineTension: 0.1,
          data: chartData.map((sprint) => sprint.totalStories),
        },
        {
          label: 'Forecast',
          fill: false,
          borderColor: 'rgba(18,64,155, 0.6)',
          borderDash: [4, 2],
          lineTension: 0,
          data: chartData.map((sprint) => sprint.projectionUsClosed),
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
          const lastData = chartData.find((sprint) => sprint.sprintId === 7);
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
    <div className="line-chart">
      <Line data={data} options={options} redraw />
    </div>
  );
}
