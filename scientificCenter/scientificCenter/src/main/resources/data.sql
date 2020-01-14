INSERT INTO `scientific_area` (`id`, `name`) VALUES ('1', 'nature');
INSERT INTO `scientific_area` (`id`, `name`) VALUES ('2', 'architecture');
INSERT INTO `scientific_area` (`id`, `name`) VALUES ('3', 'physics');
INSERT INTO `scientific_area` (`id`, `name`) VALUES ('4', 'chemistry');
INSERT INTO `scientific_area` (`id`, `name`) VALUES ('5', 'mathematics');

INSERT INTO `method_of_payment` (`id`, `name`) VALUES ('1', 'card');
INSERT INTO `method_of_payment` (`id`, `name`) VALUES ('2', 'bitcoin');
INSERT INTO `method_of_payment` (`id`, `name`) VALUES ('3', 'paypal');

insert into roles(name) values('ROLE_ADMIN');
insert into roles(name) values('ROLE_EDITOR');
insert into roles(name) values('ROLE_RECENZENT');


INSERT INTO `user` (`activated`, `city`, `country`, `email`, `is_recenzent`, `name`, `password`, `surname`, `username`) VALUES (true, 'Zrenjanin', 'Srbija', 'petarperic23252@gmail.com', false, 'petar', '$2b$10$Fteo8iSDjs2OWFQE/4Ruw.88AXPrVDfb2v4v0SGeAD8e6uwsCkLsG', 'peric', 'petarperic');
INSERT INTO `user` (`activated`, `city`, `country`, `email`, `is_recenzent`, `name`, `password`, `surname`, `username`) VALUES (true, 'Novi Sad', 'Srbija', 'nikola@gmail.com', true, 'nikola', '$2b$10$Fteo8iSDjs2OWFQE/4Ruw.88AXPrVDfb2v4v0SGeAD8e6uwsCkLsG', 'grujcic', 'nikolagrujcic');
INSERT INTO `user` (`activated`, `city`, `country`, `email`, `is_recenzent`, `name`, `password`, `surname`, `username`) VALUES (true, 'Novi Sad', 'Srbija', 'dana@gmail.com', true, 'danica', '$2b$10$Fteo8iSDjs2OWFQE/4Ruw.88AXPrVDfb2v4v0SGeAD8e6uwsCkLsG', 'markovic', 'danicamarkovic');
INSERT INTO `user` (`activated`, `city`, `country`, `email`, `is_recenzent`, `name`, `password`, `surname`, `username`) VALUES (true, 'Novi Sad', 'Srbija', 'ivana@gmail.com', true, 'ivana', '$2b$10$Fteo8iSDjs2OWFQE/4Ruw.88AXPrVDfb2v4v0SGeAD8e6uwsCkLsG', 'blagojevic', 'ivanablagojevic');
INSERT INTO `user` (`activated`, `city`, `country`, `email`, `is_recenzent`, `name`, `password`, `surname`, `username`) VALUES (true, 'Zrenjanin', 'Srbija', 'igrujcic@gmail.com', false, 'ivana', '$2b$10$Fteo8iSDjs2OWFQE/4Ruw.88AXPrVDfb2v4v0SGeAD8e6uwsCkLsG', 'grujcic', 'ivanagrujcic');
INSERT INTO `user` (`activated`, `city`, `country`, `email`, `is_recenzent`, `name`, `password`, `surname`, `username`) VALUES (true, 'Zrenjanin', 'Srbija', 'mira@gmail.com', false, 'mira', '$2b$10$Fteo8iSDjs2OWFQE/4Ruw.88AXPrVDfb2v4v0SGeAD8e6uwsCkLsG', 'grujcic', 'miragrujcic');
INSERT INTO `user` (`activated`, `city`, `country`, `email`, `is_recenzent`, `name`, `password`, `surname`, `username`) VALUES (true, 'Zrenjanin', 'Srbija', 'ljuba@gmail.com', false, 'ljuba', '$2b$10$Fteo8iSDjs2OWFQE/4Ruw.88AXPrVDfb2v4v0SGeAD8e6uwsCkLsG', 'grujcic', 'ljubagrujcic');

insert into user_roles(user_id,role_id) values (1,1);
insert into user_roles(user_id,role_id) values (2,2);
insert into user_roles(user_id,role_id) values (3,2);
insert into user_roles(user_id,role_id) values (4,2);
insert into user_roles(user_id,role_id) values (5,3);
insert into user_roles(user_id,role_id) values (6,3);
insert into user_roles(user_id,role_id) values (7,3);

INSERT INTO `admin` (`id`) VALUES ('1');

INSERT INTO `recenzent` (`id`) VALUES ('2');
INSERT INTO `recenzent` (`id`) VALUES ('3');
INSERT INTO `recenzent` (`id`) VALUES ('4');

INSERT INTO `editor` (`id`) VALUES ('5');
INSERT INTO `editor` (`id`) VALUES ('6');
INSERT INTO `editor` (`id`) VALUES ('7');

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
