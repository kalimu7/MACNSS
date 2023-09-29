DROP TABLE IF EXISTS `patients`;

CREATE TABLE `patients` (
  `email` char(55) DEFAULT NULL,
  `password` char(55) DEFAULT NULL,
  `nom` char(55) DEFAULT NULL,
  `matricule` varchar(250) NOT NULL,
  PRIMARY KEY (`matricule`)
) 

