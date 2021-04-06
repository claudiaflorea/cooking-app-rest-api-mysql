CREATE TABLE IF NOT EXISTS chefs (
	c_id INT(10) AUTO_INCREMENT PRIMARY KEY,
    c_name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS recipes (
	r_id INT(10) AUTO_INCREMENT PRIMARY KEY,
    r_name VARCHAR(50) NOT NULL,
    r_difficulty VARCHAR(10),
    r_cooking_time INT(10),
    r_recipe_type VARCHAR(30) NOT NULL
);


CREATE TABLE IF NOT EXISTS ingredients (
	i_id INT(10) AUTO_INCREMENT PRIMARY KEY,
    i_name VARCHAR(50) NOT NULL,
    i_quantity DOUBLE(10, 2) NOT NULL,
    i_unit VARCHAR(10) NOT NULL,
    i_r_id INT(10), 
    FOREIGN KEY (i_r_id) REFERENCES recipes(r_id)
);


CREATE TABLE IF NOT EXISTS dishes (
	d_id INT(10) AUTO_INCREMENT PRIMARY KEY,
    d_name VARCHAR(50) NOT NULL,
    d_r_id INT(10),
    FOREIGN KEY (d_r_id) REFERENCES recipes(r_id)
);

CREATE TABLE IF NOT EXISTS restaurants (
	rt_id INT(10) AUTO_INCREMENT PRIMARY KEY,
    rt_name VARCHAR(50) NOT NULL,
    rt_stars INT(10)
);

CREATE TABLE IF NOT EXISTS restaurants_to_chefs (
	rc_id INT(10) AUTO_INCREMENT PRIMARY KEY,
   	rc_rt_id INT(10),
    rc_c_id INT(10),
    FOREIGN KEY (rc_rt_id) REFERENCES restaurants(rt_id),
    FOREIGN KEY (rc_c_id) REFERENCES chefs(c_id)
);

CREATE TABLE IF NOT EXISTS restaurants_to_dishes (
	rd_id INT(10) AUTO_INCREMENT PRIMARY KEY,
   	rd_rt_id INT(10),
    rd_d_id INT(10),
    FOREIGN KEY (rd_rt_id) REFERENCES restaurants(rt_id),
    FOREIGN KEY (rd_d_id) REFERENCES dishes(d_id)
);


