------------------------------------------------------------
--        Script Postgre
------------------------------------------------------------



------------------------------------------------------------
-- Table: Jira
------------------------------------------------------------
CREATE TABLE public.Jira(
	id                 SERIAL NOT NULL ,
	jira_project_key   VARCHAR (4) NOT NULL ,
	current_sprint     INT  NOT NULL ,
	jira_project_id    INT  NOT NULL ,
	mvp_start_date     DATE  NOT NULL ,
	mvp_end_date       DATE  NOT NULL  ,
	CONSTRAINT Jira_PK PRIMARY KEY (id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Mvp
------------------------------------------------------------
CREATE TABLE public.Mvp(
	id                SERIAL NOT NULL ,
	name              VARCHAR (50) NOT NULL ,
	entity            VARCHAR (50) NOT NULL ,
	url_mvp_avatar    VARCHAR (50) NOT NULL ,
	cycle             INT  NOT NULL ,
	mvp_description   VARCHAR (255) NOT NULL ,
	status            VARCHAR (50) NOT NULL ,
	id_Jira           INT  NOT NULL  ,
	CONSTRAINT Mvp_PK PRIMARY KEY (id)

	,CONSTRAINT Mvp_Jira_FK FOREIGN KEY (id_Jira) REFERENCES public.Jira(id)
	,CONSTRAINT Mvp_Jira_AK UNIQUE (id_Jira)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: TeamMember
------------------------------------------------------------
CREATE TABLE public.TeamMember(
	id           SERIAL NOT NULL ,
	first_name   VARCHAR (50) NOT NULL ,
	last_name    VARCHAR (50) NOT NULL ,
	email        VARCHAR (50) NOT NULL  ,
	CONSTRAINT TeamMember_PK PRIMARY KEY (id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Team
------------------------------------------------------------
CREATE TABLE public.Team(
	id       SERIAL NOT NULL ,
	name     VARCHAR (4) NOT NULL ,
	id_Mvp   INT    ,
	CONSTRAINT Team_PK PRIMARY KEY (id)

	,CONSTRAINT Team_Mvp_FK FOREIGN KEY (id_Mvp) REFERENCES public.Mvp(id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Technology
------------------------------------------------------------
CREATE TABLE public.Technology(
	id     SERIAL NOT NULL ,
	name   VARCHAR (50) NOT NULL ,
	url    VARCHAR (5) NOT NULL  ,
	CONSTRAINT Technology_PK PRIMARY KEY (id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Mvp_Technologies
------------------------------------------------------------
CREATE TABLE public.Mvp_Technologies(
	id              INT  NOT NULL ,
	id_Technology   INT  NOT NULL ,
	mvp_id          INT  NOT NULL ,
	technology_id   INT  NOT NULL  ,
	CONSTRAINT utiliser_PK PRIMARY KEY (id,id_Technology)

	,CONSTRAINT utiliser_Mvp_FK FOREIGN KEY (id) REFERENCES public.Mvp(id)
	,CONSTRAINT utiliser_Technology0_FK FOREIGN KEY (id_Technology) REFERENCES public.Technology(id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Team_TeamMembers
------------------------------------------------------------
CREATE TABLE public.Team_TeamMembers(
	id              INT  NOT NULL ,
	id_TeamMember   INT  NOT NULL  ,
	CONSTRAINT appartenir_PK PRIMARY KEY (id,id_TeamMember)

	,CONSTRAINT appartenir_Team_FK FOREIGN KEY (id) REFERENCES public.Team(id)
	,CONSTRAINT appartenir_TeamMember0_FK FOREIGN KEY (id_TeamMember) REFERENCES public.TeamMember(id)
)WITHOUT OIDS;
