package com.cockpit.api.service;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dao.Mvp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dto.JiraDTO;
import com.cockpit.api.repository.JiraRepository;

import java.util.Optional;


@RunWith(SpringRunner.class)
public class JiraServiceTest {
	private ModelMapper modelMapper = new ModelMapper();

	private JiraService jiraService;
	
	@MockBean 
	JiraRepository jiraRepository;
	
	@Before
	public void setUp() {
		this.jiraService = new JiraService(jiraRepository);
	}
	
	@Test
	public void whenCreateJiraThenReturnCreatedJira() {

		Jira mockJira = new Jira();
	
		
		JiraDTO jiraDto = modelMapper.map(mockJira, JiraDTO.class);

		// given
		Mockito.when(jiraRepository.save(Mockito.any(Jira.class))).thenReturn(mockJira);

		// when
		JiraDTO createdJiraDto = jiraService.createNewJiraProject(jiraDto);

		// then
		Assert.assertNotNull(createdJiraDto);

	}

	@Test
	public void whenGetJiraByIdThenReturnJira() throws ResourceNotFoundException {
		Jira mockJira = new Jira();
		mockJira.setId(1l);
		mockJira.setJiraProjectKey("TEST");

		// given
		Mockito.when(jiraRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockJira));

		// when
		JiraDTO jira = jiraService.findJiraById(1l);

		// then
		Assert.assertEquals("TEST", jira.getJiraProjectKey());
	}

	@Test
	public void whenGetJiraByMvpThenReturnJira() throws ResourceNotFoundException {
		Jira mockJira = new Jira();
		Mvp mockMvp = new Mvp();
		mockMvp.setName("TEST_MVP");
		mockJira.setId(1l);
		mockJira.setJiraProjectKey("TEST");
		mockJira.setMvp(mockMvp);

		// given
		Mockito.when(jiraRepository.findByMvp(Mockito.any())).thenReturn(mockJira);

		// when
		Jira foundJira = jiraService.findByMvp(mockMvp);

		// then
		Assert.assertEquals("TEST_MVP", foundJira.getMvp().getName());
	}

	@Test
	public void whenUpdateJiraThenReturnJiraIsUpdated() throws ResourceNotFoundException {
		Jira mockJira = new Jira();
		mockJira.setId(1l);
		mockJira.setJiraProjectKey("HELLO");
		JiraDTO mockJiraDto = modelMapper.map(mockJira, JiraDTO.class);

		// given
		Mockito.when(jiraRepository.findByJiraProjectKey(Mockito.any())).thenReturn(mockJira);
		Mockito.when(jiraRepository.save(Mockito.any())).thenReturn(mockJira);

		// when
		JiraDTO jira = jiraService.updateJira(mockJiraDto);

		// then
		Assert.assertEquals("HELLO", jira.getJiraProjectKey());
	}

	@Test
	public void whenDeleteJiraThenReturnDeletedJira() throws ResourceNotFoundException {
		Jira mockJira = new Jira();
		mockJira.setId(1l);
		mockJira.setJiraProjectKey("DELETE_TEST");
		JiraDTO mockJiraDto = modelMapper.map(mockJira, JiraDTO.class);

		// given
		Mockito.when(jiraRepository.findById(Mockito.any())).thenReturn(Optional.of(mockJira));

		// when
		JiraDTO jira = jiraService.deleteJira(1l);

		// then
		Assert.assertEquals("DELETE_TEST", jira.getJiraProjectKey());
	}


}
