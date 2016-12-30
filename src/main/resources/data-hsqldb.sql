
-- seed users table
-- TODO: encrypt passwords, use for authentication, etc
insert into user (user_id, username, password, role, user_status, created_dt, updated_dt) values (default,'admin', 'admin', 'ADMIN', 'A', current_timestamp, current_timestamp);
insert into user (user_id, username, password, role, user_status, created_dt, updated_dt) values (default,'bill', 'Password1', 'USER', 'A', current_timestamp, current_timestamp);
insert into user (user_id, username, password, role, user_status, created_dt, updated_dt) values (default,'alice', 'Password1', 'USER', 'A', current_timestamp, current_timestamp);
insert into user (user_id, username, password, role, user_status, created_dt, updated_dt) values (default,'bob', 'Password1', 'USER', 'A', current_timestamp, current_timestamp);
insert into user (user_id, username, password, role, user_status, created_dt, updated_dt) values (default,'eve', 'Password1', 'USER', 'A', current_timestamp, current_timestamp);


-- seed event table
insert into event (event_id, event_type, location, description, event_dt, created_dt, updated_dt) values (default,'SPORTING', 'Madison Sq Garden', 'Celtics vs Knicks', '2016-12-31 00:00:00', current_timestamp, current_timestamp);
insert into event (event_id, event_type, location, description, event_dt, created_dt, updated_dt) values (default,'SPORTING', 'Boston Garden', 'Candians vs Bruins', '2017-01-31 00:00:00', current_timestamp, current_timestamp);
insert into event (event_id, event_type, location, description, event_dt, created_dt, updated_dt) values (default,'THEATER', 'Richard Rodgers', 'Hamilton', '2017-02-28 00:00:00', current_timestamp, current_timestamp);
insert into event (event_id, event_type, location, description, event_dt, created_dt, updated_dt) values (default,'CONCERT', 'Staples Center', 'Daft Punk', '2017-03-15 00:00:00', current_timestamp, current_timestamp);

-- seed orders table

-- bill - ordered 4 bruins tickets on 12/24
insert into customer_order  (
    order_id, 
    user_id, 
    event_id, 
    order_dt, 
    num_tickets, 
    order_status,
    created_dt,
    updated_dt
) values (
    default, 
    (select user_id from user where username = 'bill'),
    (select event_id from event where description = 'Candians vs Bruins'),
    '2016-12-24 12:00:00',
    4,
    'ACTIVE',
    current_timestamp,
    current_timestamp
);

-- bob - ordered 8 bruins tickets on 12/25
insert into customer_order  (
    order_id, 
    user_id, 
    event_id, 
    order_dt, 
    num_tickets, 
    order_status,
    created_dt,
    updated_dt
) values (
    default, 
    (select user_id from user where username = 'bob'),
    (select event_id from event where description = 'Candians vs Bruins'),
    '2016-12-25 12:00:00',
    8,
    'ACTIVE',
    current_timestamp,
    current_timestamp
);

-- alice - ordered 2 hamilton tickets on 12/25
insert into customer_order  (
    order_id, 
    user_id, 
    event_id, 
    order_dt, 
    num_tickets, 
    order_status,
    created_dt,
    updated_dt
) values (
    default, 
    (select user_id from user where username = 'alice'),
    (select event_id from event where description = 'Hamilton'),
    '2016-12-25 13:00:00',
    2,
    'ACTIVE',
    current_timestamp,
    current_timestamp
);

-- bob - ordered 2 daft punk tickets on 12/26
insert into customer_order  (
    order_id, 
    user_id, 
    event_id, 
    order_dt, 
    num_tickets, 
    order_status,
    created_dt,
    updated_dt
) values (
    default, 
    (select user_id from user where username = 'bob'),
    (select event_id from event where description = 'Daft Punk'),
    '2016-12-26 14:00:00',
    2,
    'ACTIVE',
    current_timestamp,
    current_timestamp
);