use theatre_crud;

INSERT INTO actor (id, first_name, last_name)
VALUES (100001, 'Maja', 'Ostaszewska'),
       (100002, "Magdalena", "Cielecka"),
       (100003, "Magdalena", "Popławska"),
       (100004, "Maciej", "Sthur");

INSERT INTO stage (id, name, seats_amount)
VALUES (100001, 'Mała Sala', 20),
       (100002, 'Wielka Sala', 30);

INSERT INTO spectacle (id, name, stage_id)
VALUES (100001, 'Kabaret Warszawski', 100001),
       (100002, 'Jaś i Małgosia', 100001),
       (100003, 'Francuzi', 100002);

INSERT INTO join_actor_spectacle (spectacle_id, actor_id)
VALUES (100001, 100001),
       (100001, 100002),
       (100001, 100003),
       (100002, 100002),
       (100002, 100003),
       (100003, 100001),
       (100003, 100002);

INSERT INTO spectacle_date (id, date, spectacle_id, stage_id, stage_copy_id)
VALUES (100001, date_sub(curtime(), INTERVAL -2 DAY), 100001, 100001, 100001),
       (100002, date_sub(curtime(), INTERVAL -1 DAY), 100002, 100002, null),
       (100003, date_sub(curtime(), INTERVAL -1 DAY), 100001, 100001, null);

INSERT INTO stage_copy (id, spectacle_price_pln, spectacle_date_id, stage_id)
VALUES (100001, 100, 100001, 100001);

INSERT INTO seats (id, number, status, stage_copy_id)
VALUES (100001, 1, 'FREE', 100001),
       (100002, 2, 'PAID', 100001),
       (100003, 3, 'RESERVED', 100001),
       (100004, 4, 'FREE', 100001),
       (100005, 5, 'FREE', 100001),
       (100006, 6, 'FREE', 100001),
       (100007, 7, 'FREE', 100001),
       (100008, 8, 'FREE', 100001),
       (100009, 9, 'FREE', 100001),
       (100010, 10, 'FREE', 100001);

INSERT INTO user (id, first_name, last_name, mail, password)
VALUES (100001, 'ADMIN', 'ADMIN', 'ADMIN', 'ADMIN'),
       (100002, 'TEST', 'TEST', 'test@test.com', 'test');

INSERT INTO reservation (id, reservation_date, seats_number, seats_id, stage_copy_id, user_id)
VALUES (100001, date_sub(curtime(), INTERVAL +2 DAY), 3, 100003, 100001, 100002);

