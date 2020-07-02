ALTER TABLE team_member
ADD COLUMN role VARCHAR(255),
ADD COLUMN url_team_member_avatar VARCHAR;

ALTER TABLE mvp
ADD COLUMN sprint_number INTEGER;

