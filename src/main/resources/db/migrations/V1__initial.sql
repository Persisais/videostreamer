CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       user_role varchar(32) not null default 'USER'
);

INSERT INTO users (name, email, password)
VALUES ('admin', 'admin@mail.ru', '1');


CREATE TABLE cameras (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         url TEXT unique NOT null ,
                         is_used BOOLEAN NOT NULL,
                         is_using_timetable BOOLEAN NOT NULL
);

CREATE TABLE timetables (
                            id SERIAL PRIMARY KEY,
                            camera_id INT NOT NULL,
                            date_start TIMESTAMP NOT NULL,
                            date_end TIMESTAMP NOT NULL,
                            is_used boolean not null,
                            FOREIGN KEY (camera_id) REFERENCES cameras(id) ON DELETE CASCADE
);

CREATE TABLE regular_timetables (  --Нужна для установления регулярного расписания по дням недели
                                    id SERIAL PRIMARY KEY,
                                    camera_id INT NOT NULL,
                                    day_of_week INT NOT NULL,  -- День недели, где 1 = Понедельник, 7 = Воскресенье
                                    time_start time NOT NULL,
                                    time_end time NOT  NULL,
                                    is_used boolean not null,
                                    FOREIGN KEY (camera_id) REFERENCES cameras(id) ON DELETE CASCADE
);

CREATE TABLE videos (
                        id SERIAL PRIMARY KEY,
                        camera_id INT NOT NULL,
                        date_start TIMESTAMP NOT NULL,
                        date_end TIMESTAMP NOT NULL,
                        path TEXT NOT NULL,
                        FOREIGN KEY (camera_id) REFERENCES cameras(id) ON DELETE CASCADE
);


CREATE TABLE streams (
                         id SERIAL PRIMARY KEY,
                         camera_id INT NOT NULL,
                         output_url TEXT NOT NULL,
                         time TIMESTAMP NOT NULL,
                         is_used boolean not null,
                         FOREIGN KEY (camera_id) REFERENCES cameras(id) ON DELETE CASCADE
);

CREATE TABLE actions_logs (
                              id SERIAL PRIMARY KEY,
                              user_id INT NOT NULL,
                              action_type VARCHAR(50) NOT NULL,  --ADD_Camera/Add_stream/get_video
                              camera_id INT NOT NULL,
                              time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              commentary TEXT,  --указанное время для полученного видео/ещё что-то
                              FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                              FOREIGN KEY (camera_id) REFERENCES cameras(id) ON DELETE CASCADE --/SET NULL
);