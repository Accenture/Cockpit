ALTER TABLE jira
ADD CONSTRAINT uk_jira_project_key unique (jira_project_key);

