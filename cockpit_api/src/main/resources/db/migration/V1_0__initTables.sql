------------------------------------------------------------
--        Script Postgre
------------------------------------------------------------

------------------------------------------------------------
-- Table: jira
------------------------------------------------------------
CREATE TABLE public.jira(
    id                 SERIAL NOT NULL ,
    jira_project_key   VARCHAR (4) NOT NULL ,
    current_sprint     INT  NOT NULL ,
    jira_project_id    INT  NOT NULL ,
    mvp_start_date     DATE  NOT NULL ,
    mvp_end_date       DATE  NOT NULL  ,
    CONSTRAINT jira_PK PRIMARY KEY (id)
)WITHOUT OIDS;

------------------------------------------------------------
-- Table: mvp
------------------------------------------------------------
CREATE TABLE public.mvp(
    id                SERIAL NOT NULL ,
    name              VARCHAR (50) NOT NULL ,
    entity            VARCHAR (50) NOT NULL ,
    url_mvp_avatar    VARCHAR (50) NOT NULL ,
    cycle             INT  NOT NULL ,
    mvp_description   VARCHAR (255) NOT NULL ,
    status            VARCHAR (50) NOT NULL ,
    id_jira           INT  NOT NULL  ,
    CONSTRAINT mvp_PK PRIMARY KEY (id)
    ,CONSTRAINT mvp_jira_FK FOREIGN KEY (id_jira) REFERENCES public.jira(id)
    ,CONSTRAINT mvp_jira_AK UNIQUE (id_jira)
)WITHOUT OIDS;

------------------------------------------------------------
-- Table: team_member
------------------------------------------------------------
CREATE TABLE public.team_member(
    id           SERIAL NOT NULL ,
    first_name   VARCHAR (50) NOT NULL ,
    last_name    VARCHAR (50) NOT NULL ,
    email        VARCHAR (50) NOT NULL  ,
    CONSTRAINT team_member_PK PRIMARY KEY (id)
)WITHOUT OIDS;

------------------------------------------------------------
-- Table: team
------------------------------------------------------------
CREATE TABLE public.team(
    id       SERIAL NOT NULL ,
    name     VARCHAR (4) NOT NULL ,
    id_mvp   INT    ,
    CONSTRAINT team_PK PRIMARY KEY (id)
    ,CONSTRAINT team_mvp_FK FOREIGN KEY (id_mvp) REFERENCES public.mvp(id)
)WITHOUT OIDS;

------------------------------------------------------------
-- Table: technology
------------------------------------------------------------
CREATE TABLE public.technology(
    id     SERIAL NOT NULL ,
    name   VARCHAR (50) NOT NULL ,
    url    VARCHAR (5) NOT NULL  ,
    CONSTRAINT technology_PK PRIMARY KEY (id)
)WITHOUT OIDS;

------------------------------------------------------------
-- Table: sprint
------------------------------------------------------------
CREATE TABLE public.sprint(
    id                  SERIAL NOT NULL ,
    sprint_start_date   DATE  NOT NULL ,
    sprint_end_date     DATE  NOT NULL ,
    team_motivation     INT  NOT NULL ,
    team_mood           INT  NOT NULL ,
    team_confidence     INT  NOT NULL ,
    total_nb_us         INTEGER  NOT NULL ,
    sprint_number       INT  NOT NULL ,
    id_jira             INT  NOT NULL  ,
    CONSTRAINT sprint_PK PRIMARY KEY (id)
    ,CONSTRAINT sprint_jira_FK FOREIGN KEY (id_jira) REFERENCES public.jira(id)
)WITHOUT OIDS;

------------------------------------------------------------
-- Table: user_story
------------------------------------------------------------
CREATE TABLE public.user_story(
    id              SERIAL NOT NULL ,
    creation_date   DATE  NOT NULL ,
    end_date        DATE  NOT NULL ,
    story_point     INT  NOT NULL ,
    description     VARCHAR (255) NOT NULL ,
    issue_key       VARCHAR (20) NOT NULL ,
    jira_issue_id   INT  NOT NULL ,
    priority        VARCHAR (20) NOT NULL ,
    start_date      DATE  NOT NULL ,
    status          VARCHAR (20) NOT NULL ,
    summary         VARCHAR (255) NOT NULL ,
    id_sprint       INT   ,
    id_jira         INT  NOT NULL  ,
    CONSTRAINT user_story_PK PRIMARY KEY (id)
    ,CONSTRAINT user_story_sprint_FK FOREIGN KEY (id_sprint) REFERENCES public.sprint(id)
    ,CONSTRAINT user_story_jira0_FK FOREIGN KEY (id_jira) REFERENCES public.jira(id)
)WITHOUT OIDS;

------------------------------------------------------------
-- Table: mvp_technologies
------------------------------------------------------------
CREATE TABLE public.mvp_technologies(
    id              INT  NOT NULL ,
    id_technology   INT  NOT NULL  ,
    CONSTRAINT mvp_technologies_PK PRIMARY KEY (id,id_technology)
    ,CONSTRAINT mvp_technologies_mvp_FK FOREIGN KEY (id) REFERENCES public.mvp(id)
    ,CONSTRAINT mvp_technologies_technology0_FK FOREIGN KEY (id_technology) REFERENCES public.technology(id)
)WITHOUT OIDS;

------------------------------------------------------------
-- Table: team_team_members
------------------------------------------------------------
CREATE TABLE public.team_team_members(
    id               INT  NOT NULL ,
    id_team_member   INT  NOT NULL  ,
    CONSTRAINT team_team_members_PK PRIMARY KEY (id,id_team_member)
    ,CONSTRAINT team_team_members_team_FK FOREIGN KEY (id) REFERENCES public.team(id)
    ,CONSTRAINT team_team_members_team_member0_FK FOREIGN KEY (id_team_member) REFERENCES public.team_member(id)
)WITHOUT OIDS;