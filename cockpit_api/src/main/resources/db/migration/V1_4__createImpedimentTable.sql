
CREATE TABLE public.impediment(
    id           SERIAL NOT NULL,
    name         VARCHAR (50),
    description  VARCHAR (500),
    id_sprint    INT,
    CONSTRAINT impediment_PK PRIMARY KEY (id),
    CONSTRAINT impediment_sprint_FK FOREIGN KEY (id_sprint) REFERENCES public.sprint(id)

)WITHOUT OIDS;


