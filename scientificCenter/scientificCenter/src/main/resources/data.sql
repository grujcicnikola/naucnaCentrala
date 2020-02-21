INSERT INTO `scientific_area` (`id`, `name`) VALUES ('1', 'nature');
INSERT INTO `scientific_area` (`id`, `name`) VALUES ('2', 'architecture');
INSERT INTO `scientific_area` (`id`, `name`) VALUES ('3', 'physics');
INSERT INTO `scientific_area` (`id`, `name`) VALUES ('4', 'chemistry');
INSERT INTO `scientific_area` (`id`, `name`) VALUES ('5', 'mathematics');

INSERT INTO `method_of_payment` (`id`, `name`) VALUES ('1', 'card');
INSERT INTO `method_of_payment` (`id`, `name`) VALUES ('2', 'bitcoin');
INSERT INTO `method_of_payment` (`id`, `name`) VALUES ('3', 'paypal');

insert into roles(name) values('ROLE_ADMIN');
insert into roles(name) values('ROLE_RECENZENT');
insert into roles(name) values('ROLE_EDITOR');
insert into roles(name) values('ROLE_USER');
insert into roles(name) values('ROLE_AUTHOR');

INSERT INTO `user` (`activated`, `city`, `country`,`lat`,`lon`, `email`, `is_recenzent`, `name`, `password`, `surname`, `username`) VALUES (true, 'Zrenjanin', 'Srbija','45.3816', '20.3686', 'petarperic23252@gmail.com', false, 'petar', '$2y$12$rVk/Jg8Ze917CJnC9/cYLuKXoDmWy8kU/R8CVa7i4jsKIsBJikWUi', 'peric', 'petarperic');
INSERT INTO `user` (`activated`, `city`, `country`,`lat`,`lon`, `email`, `is_recenzent`, `name`, `password`, `surname`, `username`) VALUES (true, 'Novi Sad', 'Srbija','45.2671','19.8335', 'nikola@gmail.com', true, 'nikola', '$2y$12$rVk/Jg8Ze917CJnC9/cYLuKXoDmWy8kU/R8CVa7i4jsKIsBJikWUi', 'grujcic', 'nikolagrujcic');
INSERT INTO `user` (`activated`, `city`, `country`,`lat`,`lon`, `email`, `is_recenzent`, `name`, `password`, `surname`, `username`) VALUES (true, 'Nis', 'Srbija', '43.3209','21.8958','dana@gmail.com', true, 'danica', '$2y$12$rVk/Jg8Ze917CJnC9/cYLuKXoDmWy8kU/R8CVa7i4jsKIsBJikWUi', 'markovic', 'danicamarkovic');
INSERT INTO `user` (`activated`, `city`, `country`,`lat`,`lon`, `email`, `is_recenzent`, `name`, `password`, `surname`, `username`) VALUES (true, 'Novi Sad', 'Srbija','45.2671','19.8335', 'ivana@gmail.com', true, 'ivana', '$2y$12$rVk/Jg8Ze917CJnC9/cYLuKXoDmWy8kU/R8CVa7i4jsKIsBJikWUi', 'blagojevic', 'ivanablagojevic');
INSERT INTO `user` (`activated`, `city`, `country`,`lat`,`lon`, `email`, `is_recenzent`, `name`, `password`, `surname`, `username`) VALUES (true, 'Kragujevac', 'Srbija','44.0128','20.9114', 'igrujcic@gmail.com', false, 'ivana', '$2y$12$rVk/Jg8Ze917CJnC9/cYLuKXoDmWy8kU/R8CVa7i4jsKIsBJikWUi', 'grujcic', 'ivanagrujcic');
INSERT INTO `user` (`activated`, `city`, `country`,`lat`,`lon`, `email`, `is_recenzent`, `name`, `password`, `surname`, `username`) VALUES (true, 'Leskovac', 'Srbija', '42.9964','21.9440','grujcicmira@gmail.com', false, 'mira', '$2y$12$rVk/Jg8Ze917CJnC9/cYLuKXoDmWy8kU/R8CVa7i4jsKIsBJikWUi', 'grujcic', 'miragrujcic');
INSERT INTO `user` (`activated`, `city`, `country`,`lat`,`lon`, `email`, `is_recenzent`, `name`, `password`, `surname`, `username`) VALUES (true, 'Zrenjanin', 'Srbija', '45.3816', '20.3686','ljuba@gmail.com', false, 'ljuba', '$2y$12$rVk/Jg8Ze917CJnC9/cYLuKXoDmWy8kU/R8CVa7i4jsKIsBJikWUi', 'grujcic', 'ljubagrujcic');
INSERT INTO `user` (`activated`, `city`, `country`,`lat`,`lon`, `email`, `is_recenzent`, `name`, `password`, `surname`, `username`) VALUES (true, 'Pristina', 'Crna Gora','42.6629','21.1655', 'nikolagrujcic@gmail.com', false, 'marija', '$2y$12$rVk/Jg8Ze917CJnC9/cYLuKXoDmWy8kU/R8CVa7i4jsKIsBJikWUi', 'stanic', 'marijastanic');
INSERT INTO `user` (`activated`, `city`, `country`,`lat`,`lon`, `email`, `is_recenzent`, `name`, `password`, `surname`, `username`) VALUES (true, 'Nis', 'Srbija','43.3209','21.8958', 'recenzent1@gmail.com', true, 'recenzent1', '$2y$12$rVk/Jg8Ze917CJnC9/cYLuKXoDmWy8kU/R8CVa7i4jsKIsBJikWUi', 'recenzent1', 'recenzent1');
INSERT INTO `user` (`activated`, `city`, `country`,`lat`,`lon`, `email`, `is_recenzent`, `name`, `password`, `surname`, `username`) VALUES (true, 'Novi Sad', 'Srbija','45.2671','19.8335', 'recenzent2@gmail.com', true, 'recenzent2', '$2y$12$rVk/Jg8Ze917CJnC9/cYLuKXoDmWy8kU/R8CVa7i4jsKIsBJikWUi', 'recenzent2', 'recenzent2');
INSERT INTO `user` (`activated`, `city`, `country`,`lat`,`lon`, `email`, `is_recenzent`, `name`, `password`, `surname`, `username`) VALUES (true, 'Novi Sad', 'Srbija','45.2671','19.8335', 'recenzent3@gmail.com', true, 'recenzent3', '$2y$12$rVk/Jg8Ze917CJnC9/cYLuKXoDmWy8kU/R8CVa7i4jsKIsBJikWUi', 'recenzent3', 'recenzent3');
INSERT INTO `user` (`activated`, `city`, `country`,`lat`,`lon`, `email`, `is_recenzent`, `name`, `password`, `surname`, `username`) VALUES (true, 'Kragujevac', 'Srbija', '44.0128','20.9114','author1@gmail.com', false, 'miroslav', '$2y$12$rVk/Jg8Ze917CJnC9/cYLuKXoDmWy8kU/R8CVa7i4jsKIsBJikWUi', 'milosavljevic', 'author1');
INSERT INTO `user` (`activated`, `city`, `country`,`lat`,`lon`, `email`, `is_recenzent`, `name`, `password`, `surname`, `username`) VALUES (true, 'Leskovac', 'Srbija', '42.9964','21.9440', 'author2@gmail.com', false, 'miodrag', '$2y$12$rVk/Jg8Ze917CJnC9/cYLuKXoDmWy8kU/R8CVa7i4jsKIsBJikWUi', 'subic', 'author2');
INSERT INTO `user` (`activated`, `city`, `country`,`lat`,`lon`, `email`, `is_recenzent`, `name`, `password`, `surname`, `username`) VALUES (true, 'Zrenjanin', 'Srbija','45.3816', '20.3686', 'author3@gmail.com', false, 'zeljana', '$2y$12$rVk/Jg8Ze917CJnC9/cYLuKXoDmWy8kU/R8CVa7i4jsKIsBJikWUi', 'zeljanic', 'author3');
INSERT INTO `user` (`activated`, `city`, `country`,`lat`,`lon`, `email`, `is_recenzent`, `name`, `password`, `surname`, `username`) VALUES (true, 'Kragujevac', 'Srbija', '44.0128','20.9114','author4@gmail.com', false, 'sanja', '$2y$12$rVk/Jg8Ze917CJnC9/cYLuKXoDmWy8kU/R8CVa7i4jsKIsBJikWUi', 'sanjic', 'author4');
INSERT INTO `user` (`activated`, `city`, `country`,`lat`,`lon`, `email`, `is_recenzent`, `name`, `password`, `surname`, `username`) VALUES (true, 'Nis', 'Srbija', '43.3209','21.8958','author5@gmail.com', false, 'jova', '$2y$12$rVk/Jg8Ze917CJnC9/cYLuKXoDmWy8kU/R8CVa7i4jsKIsBJikWUi', 'jovic', 'author5');




insert into user_roles(user_id,role_id) values (1,1);
insert into user_roles(user_id,role_id) values (2,2);
insert into user_roles(user_id,role_id) values (3,2);
insert into user_roles(user_id,role_id) values (3,4); --dana ima dve uloge
insert into user_roles(user_id,role_id) values (4,2);
insert into user_roles(user_id,role_id) values (5,3);
insert into user_roles(user_id,role_id) values (6,3);
insert into user_roles(user_id,role_id) values (7,3);
insert into user_roles(user_id,role_id) values (8,5);
insert into user_roles(user_id,role_id) values (9,2);
insert into user_roles(user_id,role_id) values (10,2);
insert into user_roles(user_id,role_id) values (11,2);
insert into user_roles(user_id,role_id) values (12,5);
insert into user_roles(user_id,role_id) values (13,5);
insert into user_roles(user_id,role_id) values (14,5);
insert into user_roles(user_id,role_id) values (15,5);
insert into user_roles(user_id,role_id) values (16,5);

INSERT INTO `admin` (`id`) VALUES ('1');

INSERT INTO `recenzent` (`id`) VALUES ('2');
INSERT INTO `recenzent` (`id`) VALUES ('3');
INSERT INTO `recenzent` (`id`) VALUES ('4');
INSERT INTO `recenzent` (`id`) VALUES ('9');
INSERT INTO `recenzent` (`id`) VALUES ('10');
INSERT INTO `recenzent` (`id`) VALUES ('11');

INSERT INTO `editor` (`id`,`journal_id`) VALUES ('5','2');
INSERT INTO `editor` (`id`,`journal_id`) VALUES ('6','3');
INSERT INTO `editor` (`id`,`journal_id`) VALUES ('7','1');

INSERT INTO `author` (`id`) VALUES ('12');
INSERT INTO `author` (`id`) VALUES ('13');
INSERT INTO `author` (`id`) VALUES ('14');
INSERT INTO `author` (`id`) VALUES ('15');
INSERT INTO `author` (`id`) VALUES ('16');
INSERT INTO `author` (`id`) VALUES ('8');

INSERT INTO `user_areas` (`user_id`, `areas_id`) VALUES ('5', '1');
INSERT INTO `user_areas` (`user_id`, `areas_id`) VALUES ('5', '2');
INSERT INTO `user_areas` (`user_id`, `areas_id`) VALUES ('6', '1');
INSERT INTO `user_areas` (`user_id`, `areas_id`) VALUES ('6', '2');
INSERT INTO `user_areas` (`user_id`, `areas_id`) VALUES ('7', '1');
INSERT INTO `user_areas` (`user_id`, `areas_id`) VALUES ('7', '2');

INSERT INTO `user_areas` (`user_id`, `areas_id`) VALUES ('2', '1');
INSERT INTO `user_areas` (`user_id`, `areas_id`) VALUES ('2', '2');
INSERT INTO `user_areas` (`user_id`, `areas_id`) VALUES ('3', '1');
INSERT INTO `user_areas` (`user_id`, `areas_id`) VALUES ('3', '2');
INSERT INTO `user_areas` (`user_id`, `areas_id`) VALUES ('4', '1');
INSERT INTO `user_areas` (`user_id`, `areas_id`) VALUES ('4', '2');
INSERT INTO `user_areas` (`user_id`, `areas_id`) VALUES ('9', '1');
INSERT INTO `user_areas` (`user_id`, `areas_id`) VALUES ('9', '4');
INSERT INTO `user_areas` (`user_id`, `areas_id`) VALUES ('10', '2');
INSERT INTO `user_areas` (`user_id`, `areas_id`) VALUES ('10', '4');
INSERT INTO `user_areas` (`user_id`, `areas_id`) VALUES ('11', '2');
INSERT INTO `user_areas` (`user_id`, `areas_id`) VALUES ('11', '4');

INSERT INTO `journal` (`is_activated`, `is_open_access`, `issn`, `title`, `editor_in_chief_id`, `price`, subscription_num) VALUES (true, false, '1111-2222', 'Astrology', '5','2.5', 1);
INSERT INTO `journal` (`is_activated`, `is_open_access`, `issn`, `title`, `editor_in_chief_id`, `price`, subscription_num) VALUES (true, true, '2222-2222', 'Interest', '6','1.5', 0);
INSERT INTO `journal` (`is_activated`, `is_open_access`, `issn`, `title`, `editor_in_chief_id`, `price`, subscription_num) VALUES (true, true, '3333-2222', 'National geographic', '7','2.0', 0);


INSERT INTO `journal_areas` (`journal_id`, `areas_id`) VALUES ('1', '1');
INSERT INTO `journal_areas` (`journal_id`, `areas_id`) VALUES ('1', '2');
INSERT INTO `journal_areas` (`journal_id`, `areas_id`) VALUES ('2', '1');
INSERT INTO `journal_areas` (`journal_id`, `areas_id`) VALUES ('2', '2');
INSERT INTO `journal_areas` (`journal_id`, `areas_id`) VALUES ('3', '1');
INSERT INTO `journal_areas` (`journal_id`, `areas_id`) VALUES ('3', '2');


INSERT INTO `recenzent_journal` (`journal_id`, `recenzent_id`) VALUES ('1', '2');
INSERT INTO `recenzent_journal` (`journal_id`, `recenzent_id`) VALUES ('1', '3');
INSERT INTO `recenzent_journal` (`journal_id`, `recenzent_id`) VALUES ('1', '4');
INSERT INTO `recenzent_journal` (`journal_id`, `recenzent_id`) VALUES ('1', '9');
INSERT INTO `recenzent_journal` (`journal_id`, `recenzent_id`) VALUES ('1', '10');
INSERT INTO `recenzent_journal` (`journal_id`, `recenzent_id`) VALUES ('2', '2');
INSERT INTO `recenzent_journal` (`journal_id`, `recenzent_id`) VALUES ('2', '3');
INSERT INTO `recenzent_journal` (`journal_id`, `recenzent_id`) VALUES ('2', '4');
INSERT INTO `recenzent_journal` (`journal_id`, `recenzent_id`) VALUES ('2', '9');
INSERT INTO `recenzent_journal` (`journal_id`, `recenzent_id`) VALUES ('2', '10');
INSERT INTO `recenzent_journal` (`journal_id`, `recenzent_id`) VALUES ('3', '2');


INSERT INTO `membership` (`active`,`author_id`,`journal_id`) VALUES (true,8,2);


insert into subscription (active,frequency,price,type,journal_id,user_id) values (true, 1, 1, 0, 1, 3);
insert into subscription (active,frequency,price,type,journal_id,user_id) values (true, 1, 6, 0, 2, 3);
