package com.cockpit.api.service;

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
	
}
