import React from 'react';
import { Line } from 'react-chartjs-2';

export default function BurnUpChart() {
  const data = (canvas) => {
    const ctx = canvas.getContext('2d');
    const gradient = ctx.createLinearGradient(0, 0, 0, 600);
    gradient.addColorStop(0, 'rgba(18,64,155,0.5)');
    gradient.addColorStop(0.5, 'rgba(18,64,155,0.08)');
    gradient.addColorStop(1, 'rgba(18,64,155,0)');

    return {
      labels: ['1', '2', '3', '4', '5', '6', '7'],
      datasets: [
        {
          label: 'User Stories Closed',
          backgroundColor: gradient, // Put the gradient here as a fill color
          borderColor: 'rgba(18,64,155, 0.6)',
          borderWidth: 2,
          pointColor: '#fff',
          pointStrokeColor: '#ff6c23',
          pointHighlightFill: '#fff',
          pointHighlightStroke: '#ff6c23',
          lineTension: 0,
          data: [1, 1, 7, 12, 18, 25],
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
          data: [1, 3, 10, 16, 20, 25, 28, 30],
        },
        {
          label: 'Total number of stories ',
          fill: false,
          borderColor: 'rgba(18,64,155, 0.6)',
          lineTension: 0.1,
          data: [3, 6, 14, 20, 25, 30, 33, 35],
        },
        {
          label: 'Forecast',
          fill: false,
          borderColor: 'rgba(18,64,155, 0.6)',
          borderDash: [4, 2],
          lineTension: 0,
          data: [1, 3, 5, 12, 14, 20, 24, 28],
        },
      ],
    };
  };

  const options = {
    responsive: true,
    datasetStrokeWidth: 3,
    pointDotStrokeWidth: 4,
    scaleLabel: "<%= Number(value).toFixed(0).replace('.', ',') + '°C'%>",
  };

  return (
    <div className="line-chart">
      <Line data={data} options={options} />
    </div>
  );
}
