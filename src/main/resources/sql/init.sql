set FOREIGN_KEY_CHECKS=0 ;

	DELETE FROM `role`;
  INSERT INTO `role` (`id`,`name`) VALUES
	(1,'ROLE_USER'),
	(2,'ROLE_ADMIN');

DELETE  from USER  where id in (1,2) ;
INSERT INTO `user` (`id`, `email`, `enabled`, `firstname`, `last_name`, `password`, `username`, `role_id`) VALUES
	(1, 'user@gmail.com', 1, 'user', 'user', '$2a$10$uY7VCstggF94iZ9JO7Qq6Os.Q/kN8ryr/KCeVHzvlny.1NP8HF8nK', 'user', 1),
	(2, 'admin@gmail.com', 1, 'admin', 'admin', '$2a$10$ScVb.k1330IDWDpghgOad.2mPA6OEC.AzrdQinM4bo8yaDwRNHGMK', 'admin', 2);


set FOREIGN_KEY_CHECKS=1 ;