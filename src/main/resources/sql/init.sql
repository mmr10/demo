set FOREIGN_KEY_CHECKS=0 ;

	DELETE FROM `authority`;
  INSERT INTO `authority` (`id`,`name`) VALUES
	(1,'ROLE_USER'),
	(2,'ROLE_ADMIN');

DELETE  from USER  where id in (1,2) ;
DELETE  from user_authority where user_id in (1,2) ;
INSERT INTO `user` (`id`, `email`, `enabled`, `firstname`, `last_name`, `password`, `username`) VALUES
	(1, 'user@gmail.com', 1, 'user', 'user', '$2a$10$uY7VCstggF94iZ9JO7Qq6Os.Q/kN8ryr/KCeVHzvlny.1NP8HF8nK', 'user'),
	(2, 'admin@gmail.com', 1, 'admin', 'admin', '$2a$10$ScVb.k1330IDWDpghgOad.2mPA6OEC.AzrdQinM4bo8yaDwRNHGMK', 'admin');

	INSERT INTO `user_authority` (`user_id`, `authority_id`) VALUES
	(1, 1),
	(2, 2);

set FOREIGN_KEY_CHECKS=1 ;