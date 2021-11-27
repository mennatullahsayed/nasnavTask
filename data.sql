INSERT INTO roles(name) SELECT 'USER'
WHERE NOT EXISTS (SELECT * FROM roles WHERE name='USER');

INSERT INTO roles(name) SELECT 'ADMIN'
WHERE NOT EXISTS (SELECT * FROM roles WHERE name='ADMIN'); 

INSERT INTO users (username, password, email)
SELECT 'admin', 'admin123', 'admin123@gmail.con'
WHERE NOT EXISTS (SELECT * FROM users WHERE username='admin');


INSERT INTO user_roles (user_id,role_id)
SELECT 1,1
WHERE NOT EXISTS (SELECT * FROM user_roles WHERE user_id=1));