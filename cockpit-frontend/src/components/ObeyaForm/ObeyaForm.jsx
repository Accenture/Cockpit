import React, { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import Grid from '@material-ui/core/Grid';
import FormLabel from '@material-ui/core/FormLabel';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import FormControl from '@material-ui/core/FormControl';

export default function ObeyaForm() {
  const [list, setList] = useState([
    { value: 1, label: 'awesome' },
    { value: 2, label: 'good' },
  ]);

  const handleSelect = (option) => {
    debugger;
    setList(
      list.map((item) => {
        return {
          ...item,
          // label: `${option}`,
          selected: item.value === option,
        };
      }),
    );
  };
  const selected = list.find((item) => item.selected);
  const selectedVal = selected ? selected.value : ' ';
  return (
    <Grid container spacing={1}>
      <Grid item xs={3}>
        <FormLabel>Mood</FormLabel>
        <FormControl required size="small" fullWidth variant="outlined">
          <Select
            value={selectedVal}
            onChange={(e) => handleSelect(e.target.value)}
            // optionLabelProp={"value"}
          >
            {list.map((item) => (
              <MenuItem value={item.value} key={item.value}>
                {item.label}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
      </Grid>
    </Grid>
  );
}
