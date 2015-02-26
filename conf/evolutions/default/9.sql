# ajout de trois comptes par défaut avec les différents niveaux de droits

# --- !Ups

INSERT INTO `membre` (`membre_id`, `membre_nom`, `membre_confidentialite_confidentialite_id`, `membre_abonne`, `membre_temoin_actif`,`membre_droits_droits_id`,`membre_inscription_acceptee`,`membre_email`,`membre_mdp_hash`) VALUES
('1', 'user1', '1', '1', '1', '1', '1','user1','1000:64ac80b733fb754926e3c2b6bbbf1c62bb4c53d0295b53c9:dd2a542e1c77d8f785c8a77b43bcd949caec51b2f1ab8b37'),
('2', 'user2', '1', '1', '1', '2', '1','user2','1000:64ac80b733fb754926e3c2b6bbbf1c62bb4c53d0295b53c9:dd2a542e1c77d8f785c8a77b43bcd949caec51b2f1ab8b37'),
('3', 'user3', '1', '1', '1', '3', '1','user3','1000:64ac80b733fb754926e3c2b6bbbf1c62bb4c53d0295b53c9:dd2a542e1c77d8f785c8a77b43bcd949caec51b2f1ab8b37');

INSERT INTO `membre_is_expert_on_groupe` (`membre_is_expert_on_groupe_id`, `membre_membre_id`, `groupe_groupe_id`) VALUES
('1','2','8'),
('2','3','3');

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

DELETE FROM membre;

SET FOREIGN_KEY_CHECKS=1;