import React, { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import Grid from '@material-ui/core/Grid';
import FormLabel from '@material-ui/core/FormLabel';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import FormControl from '@material-ui/core/FormControl';

export default function ObeyaForm(props) {
    const [list, setList] = useState([{value:1, label:"awesome"},{value:2, label:"good"}]);

  const sprint = props.sprintNumber;
  const teamMotivation=2;
  const teamConfidence=2;
  //const list=[{value:1, label:"awesome"},{value:2, label:"good"}];
  function handleEntityChange(event) {
      
   // setTeamMood(event.target.value);
  }
  const handleSelect = (option) => {
      debugger
   setList(list.map(genre => {
    return {
      ...genre,
      selected: genre.value === option
    };
  }))
  };
  const selected = list.find(genre => genre.selected);
    const selectedVal = selected ? selected.value : " ";
  return (
    <Grid container spacing={1}>
      <Grid item xs={3}>
        <FormLabel>Mood</FormLabel>
        <FormControl required size="small" fullWidth variant="outlined">
          <Select
            displayEmpty
         //   value={teamMood || ''}
           // onChange={handleEntityChange}
           value={selectedVal || ''}

           onChange={option => handleSelect(option)}
           // optionLabelProp={"value"}
          >
              {list.map((number) => ( <MenuItem value={number.value} key={number.value}>{number.label}</MenuItem> ))}
            
          </Select>
        </FormControl>
      </Grid>
    
    </Grid>
  );
}
