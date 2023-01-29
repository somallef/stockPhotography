alter table images add active tinyint;
update images set active = 1;