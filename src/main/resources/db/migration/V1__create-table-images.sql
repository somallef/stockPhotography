create table images(

    id bigint not null auto_increment,
    resolution varchar(100),
    license varchar(100),
    file_path varchar(255),
    title varchar(100),
    description varchar(100),
    image_file_name varchar(100),

    primary key(id)

);