-- MySQL dump 10.16  Distrib 10.1.48-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: hospital
-- ------------------------------------------------------
-- Server version	10.1.48-MariaDB-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `arznei`
--

DROP TABLE IF EXISTS `arznei`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `arznei` (
  `id` varchar(36) NOT NULL,
  `bezeichnung` varchar(45) DEFAULT NULL,
  `preis` int(11) DEFAULT NULL,
  `rezept_text` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `arznei`
--

LOCK TABLES `arznei` WRITE;
/*!40000 ALTER TABLE `arznei` DISABLE KEYS */;
INSERT INTO `arznei` VALUES ('0fe2e156-2991-11f0-8c30-d0008dc32d97','Paracetamol',399,NULL),('446037eb-2986-11f0-8c30-d0008dc32d97','Dorithricin',699,NULL),('5c3c53e3-2986-11f0-8c30-d0008dc32d97','Ibuprofen',499,NULL),('5ce18fc1-2991-11f0-8c30-d0008dc32d97','Ceftriaxon',1999,'Rezept erforderlich'),('8aaee3c9-25c4-11f0-8c49-d00192da8f3d','salbe',599,NULL),('c8555a0f-298d-11f0-8c30-d0008dc32d97','Penicillin',150,'Rezept erforderlich');
/*!40000 ALTER TABLE `arznei` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `arzt`
--

DROP TABLE IF EXISTS `arzt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `arzt` (
  `id` varchar(36) NOT NULL,
  `name` varchar(180) NOT NULL,
  `telefon` varchar(42) DEFAULT NULL,
  `email` varchar(256) NOT NULL,
  `fachgebiet` varchar(180) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `arzt`
--

LOCK TABLES `arzt` WRITE;
/*!40000 ALTER TABLE `arzt` DISABLE KEYS */;
INSERT INTO `arzt` VALUES ('0f2bf930-298a-11f0-8c30-d0008dc32d97','Lena Fürst','+49 0837 2349823','lefue@mail.com','Pneumologe'),('0ff5b6b4-2988-11f0-8c30-d0008dc32d97','Tim Murphy','+49 0512 3842948','Timurphy@mail.com','Neurologe'),('31b9d596-298b-11f0-8c30-d0008dc32d97','Walter Weiß','+49 0172 3842948','wal@weiß.com','Onkologe'),('70df50b7-2994-11f0-8c30-d0008dc32d97','Hans Meister','+49 0347 8173584','hans@mei.com','Allgemeinarzt'),('724617e3-25c4-11f0-8c49-d00192da8f3d','Hans Jockl DuMont','++49 0123 456789','hajo@dumont.de','Rheumathologe'),('74e78e30-298a-11f0-8c30-d0008dc32d97','Marie Krümmel','+49 0638 4783748','mar@kruem.com','Anästhesiologe'),('c1625558-298a-11f0-8c30-d0008dc32d97','Frederic House','+49 0174 2738893','fred@house.com','Chirurg'),('fd30ba7a-2986-11f0-8c30-d0008dc32d97','Eric Friede','+49 0475 3448573','friede@eric.com','HNO-Arzt');
/*!40000 ALTER TABLE `arzt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `behandlung`
--

DROP TABLE IF EXISTS `behandlung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `behandlung` (
  `id` varchar(36) NOT NULL,
  `arzt_id` varchar(36) NOT NULL,
  `kunde_id` varchar(36) NOT NULL,
  `pflegekraft_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_behandlung_arzt_idx` (`arzt_id`),
  KEY `fk_behandlung_kunde1_idx` (`kunde_id`),
  KEY `fk_behandlung_krankeschwester1_idx` (`pflegekraft_id`),
  CONSTRAINT `fk_behandlung_arzt` FOREIGN KEY (`arzt_id`) REFERENCES `arzt` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_behandlung_krankeschwester` FOREIGN KEY (`pflegekraft_id`) REFERENCES `pflegekraft` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_behandlung_kunde` FOREIGN KEY (`kunde_id`) REFERENCES `patient` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `behandlung`
--

LOCK TABLES `behandlung` WRITE;
/*!40000 ALTER TABLE `behandlung` DISABLE KEYS */;
INSERT INTO `behandlung` VALUES ('004a699e-2995-11f0-8c30-d0008dc32d97','70df50b7-2994-11f0-8c30-d0008dc32d97','a577429f-2990-11f0-8c30-d0008dc32d97','7d8649e1-2988-11f0-8c30-d0008dc32d97'),('3d59f377-2995-11f0-8c30-d0008dc32d97','fd30ba7a-2986-11f0-8c30-d0008dc32d97','a9346e51-2985-11f0-8c30-d0008dc32d97','64289b0c-2987-11f0-8c30-d0008dc32d97'),('53306e46-2995-11f0-8c30-d0008dc32d97','0f2bf930-298a-11f0-8c30-d0008dc32d97','b37cfb13-298c-11f0-8c30-d0008dc32d97','51dddd6f-2987-11f0-8c30-d0008dc32d97'),('63dc3daf-2995-11f0-8c30-d0008dc32d97','70df50b7-2994-11f0-8c30-d0008dc32d97','bc45ba1f-298f-11f0-8c30-d0008dc32d97','7d8649e1-2988-11f0-8c30-d0008dc32d97'),('81ba8667-2995-11f0-8c30-d0008dc32d97','0f2bf930-298a-11f0-8c30-d0008dc32d97','cd2387e5-2989-11f0-8c30-d0008dc32d97','64289b0c-2987-11f0-8c30-d0008dc32d97'),('9227e632-2994-11f0-8c30-d0008dc32d97','70df50b7-2994-11f0-8c30-d0008dc32d97','4879f2b0-2989-11f0-8c30-d0008dc32d97','64289b0c-2987-11f0-8c30-d0008dc32d97'),('9b9a1cb0-2995-11f0-8c30-d0008dc32d97','fd30ba7a-2986-11f0-8c30-d0008dc32d97','cf186b2c-298e-11f0-8c30-d0008dc32d97','51dddd6f-2987-11f0-8c30-d0008dc32d97'),('aca93fee-2993-11f0-8c30-d0008dc32d97','c1625558-298a-11f0-8c30-d0008dc32d97','1a1d7b14-298c-11f0-8c30-d0008dc32d97','7d8649e1-2988-11f0-8c30-d0008dc32d97'),('ae38f735-2995-11f0-8c30-d0008dc32d97','70df50b7-2994-11f0-8c30-d0008dc32d97','e9385581-298f-11f0-8c30-d0008dc32d97','7d8649e1-2988-11f0-8c30-d0008dc32d97'),('cada3237-2994-11f0-8c30-d0008dc32d97','724617e3-25c4-11f0-8c49-d00192da8f3d','a518321c-25c4-11f0-8c49-d00192da8f3d','51dddd6f-2987-11f0-8c30-d0008dc32d97');
/*!40000 ALTER TABLE `behandlung` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lieferant`
--

DROP TABLE IF EXISTS `lieferant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lieferant` (
  `id` varchar(36) NOT NULL COMMENT '	',
  `bezeichnung` varchar(120) DEFAULT NULL,
  `lieferzeit` int(11) DEFAULT NULL,
  `adresse` varchar(250) DEFAULT NULL,
  `telefon` varchar(42) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lieferant`
--

LOCK TABLES `lieferant` WRITE;
/*!40000 ALTER TABLE `lieferant` DISABLE KEYS */;
INSERT INTO `lieferant` VALUES ('5b11723f-2996-11f0-8c30-d0008dc32d97','Paracetamol',5,'maxstraße 12','+49 098 7654321'),('6caab6f6-2996-11f0-8c30-d0008dc32d97','Dorithricin',5,'maxstraße 12','+49 098 7654321'),('81150b2f-2996-11f0-8c30-d0008dc32d97','Ibuprofen',5,'maxstraße 12','+49 098 7654321'),('93ad343f-25c4-11f0-8c49-d00192da8f3d','salbe',5,'maxstraße 12','+49 098 7654321'),('b2171b46-2996-11f0-8c30-d0008dc32d97','Ceftriaxon',12,'Großberg 14','+49 012 4578369'),('c9146f8c-2996-11f0-8c30-d0008dc32d97','Penicillin',12,'Großberg 14','+49 012 4578369');
/*!40000 ALTER TABLE `lieferant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lieferung`
--

DROP TABLE IF EXISTS `lieferung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lieferung` (
  `id` varchar(36) NOT NULL,
  `arznei_id` varchar(36) NOT NULL,
  `lieferant_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_lieferung_arznei1_idx` (`arznei_id`),
  KEY `fk_lieferung_lieferant1_idx` (`lieferant_id`),
  CONSTRAINT `fk_lieferung_arznei` FOREIGN KEY (`arznei_id`) REFERENCES `arznei` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_lieferung_lieferant` FOREIGN KEY (`lieferant_id`) REFERENCES `lieferant` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lieferung`
--

LOCK TABLES `lieferung` WRITE;
/*!40000 ALTER TABLE `lieferung` DISABLE KEYS */;
INSERT INTO `lieferung` VALUES ('a2348a6b-2997-11f0-8c30-d0008dc32d97','0fe2e156-2991-11f0-8c30-d0008dc32d97','5b11723f-2996-11f0-8c30-d0008dc32d97'),('b049afc9-2997-11f0-8c30-d0008dc32d97','446037eb-2986-11f0-8c30-d0008dc32d97','6caab6f6-2996-11f0-8c30-d0008dc32d97'),('bc308855-2997-11f0-8c30-d0008dc32d97','5c3c53e3-2986-11f0-8c30-d0008dc32d97','81150b2f-2996-11f0-8c30-d0008dc32d97'),('d511f3a4-2997-11f0-8c30-d0008dc32d97','8aaee3c9-25c4-11f0-8c49-d00192da8f3d','93ad343f-25c4-11f0-8c49-d00192da8f3d'),('e90fa578-2997-11f0-8c30-d0008dc32d97','5ce18fc1-2991-11f0-8c30-d0008dc32d97','b2171b46-2996-11f0-8c30-d0008dc32d97'),('f652a736-2997-11f0-8c30-d0008dc32d97','c8555a0f-298d-11f0-8c30-d0008dc32d97','c9146f8c-2996-11f0-8c30-d0008dc32d97');
/*!40000 ALTER TABLE `lieferung` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient` (
  `id` varchar(36) NOT NULL,
  `name` varchar(180) NOT NULL,
  `adresse` varchar(250) NOT NULL,
  `telefon` varchar(42) DEFAULT NULL,
  `email` varchar(256) NOT NULL,
  `icd_key` varchar(120) NOT NULL,
  `bemerkung` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES ('1a1d7b14-298c-11f0-8c30-d0008dc32d97','Rüdiger von Müdiger','Marktplatz 22','+49 764 2837498','rudi@mail.com','K35.8','Appendizitis'),('4879f2b0-2989-11f0-8c30-d0008dc32d97','Lisa Müller','Antoniostraße 2','+49 189 3828394','liz24@mail.com','S61','Fingerschnitt'),('a518321c-25c4-11f0-8c49-d00192da8f3d','Jens Schustermann von Hausen','Lilienstraße 49','+49 198 47829328','Greg@Hausen.com','13.9-','Arthritis'),('a577429f-2990-11f0-8c30-d0008dc32d97','Anton Müller','Großhausen 1','+49 174 3820479','anton@muell.com','G43','Migräne'),('a9346e51-2985-11f0-8c30-d0008dc32d97','Viktor Schwarz','Bertholdstraße 12','+49 156 4694685','vik_schwarz@mail.com','J02.9','Pharyngitis'),('b37cfb13-298c-11f0-8c30-d0008dc32d97','Andre Sportmann','Friedenstraße 32','+49 242 2142134','andi@mail.com','J18','Pneumonie'),('bc45ba1f-298f-11f0-8c30-d0008dc32d97','Nicole Domino','Gabelweg 23','+49 167 3859484','nido@mail.com','J00','Rhinopharyngitis'),('cd2387e5-2989-11f0-8c30-d0008dc32d97','Günther Bauch','Marktplatz 24','+49 764 2837498','guenther@mail.com','J18','Pneumonie'),('cf186b2c-298e-11f0-8c30-d0008dc32d97','Sebastian Großmacher','Grasweg 28','+49 484 8273483','sebi@mail.com','H70.0','Mastoiditis'),('e9385581-298f-11f0-8c30-d0008dc32d97','Dominik Torretto','Neumarkt 38','+49 275 4235643','dom@mail.com','J00','Rhinopharyngitis');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pflegekraft`
--

DROP TABLE IF EXISTS `pflegekraft`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pflegekraft` (
  `id` varchar(36) NOT NULL,
  `name` varchar(180) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pflegekraft`
--

LOCK TABLES `pflegekraft` WRITE;
/*!40000 ALTER TABLE `pflegekraft` DISABLE KEYS */;
INSERT INTO `pflegekraft` VALUES ('51dddd6f-2987-11f0-8c30-d0008dc32d97','Andreas Gartner'),('64289b0c-2987-11f0-8c30-d0008dc32d97','Melina Neumann'),('7d8649e1-2988-11f0-8c30-d0008dc32d97','Patricia Großbach');
/*!40000 ALTER TABLE `pflegekraft` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `termin`
--

DROP TABLE IF EXISTS `termin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `termin` (
  `id` varchar(36) DEFAULT NULL,
  `datum` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `termin`
--

LOCK TABLES `termin` WRITE;
/*!40000 ALTER TABLE `termin` DISABLE KEYS */;
INSERT INTO `termin` VALUES ('980213ad-5268-11f0-8fa1-d92b48e8b2c2','2025-12-10'),('3ec4a7a1-f0ad-4ea4-9224-b4280c62dec7','2030-12-11'),('e622e4c1-9821-4d62-9924-2a11cd9bee64','2026-12-30'),('5ecfb32e-d892-4966-9562-b0933c61a8b8','2027-12-13');
/*!40000 ALTER TABLE `termin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `terminplan`
--

DROP TABLE IF EXISTS `terminplan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `terminplan` (
  `id` varchar(36) DEFAULT NULL,
  `arzt_id` varchar(36) DEFAULT NULL,
  `patient_id` varchar(36) DEFAULT NULL,
  `termin_id` varchar(36) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `terminplan`
--

LOCK TABLES `terminplan` WRITE;
/*!40000 ALTER TABLE `terminplan` DISABLE KEYS */;
INSERT INTO `terminplan` VALUES ('87ad87ee-526a-11f0-8fa1-d92b48e8b2c2','70df50b7-2994-11f0-8c30-d0008dc32d97','a577429f-2990-11f0-8c30-d0008dc32d97','980213ad-5268-11f0-8fa1-d92b48e8b2c2'),('e15d5d2f-17da-457b-8a41-60aff2807ed8','70df50b7-2994-11f0-8c30-d0008dc32d97','a9346e51-2985-11f0-8c30-d0008dc32d97','3ec4a7a1-f0ad-4ea4-9224-b4280c62dec7'),('506d85df-8543-451f-bfd2-57ecfe6bd788','0f2bf930-298a-11f0-8c30-d0008dc32d97','1a1d7b14-298c-11f0-8c30-d0008dc32d97','e622e4c1-9821-4d62-9924-2a11cd9bee64'),('596d4870-1dc0-4afa-b9fe-b3c89b5eded8','0f2bf930-298a-11f0-8c30-d0008dc32d97','a518321c-25c4-11f0-8c49-d00192da8f3d','5ecfb32e-d892-4966-9562-b0933c61a8b8');
/*!40000 ALTER TABLE `terminplan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verschreibung`
--

DROP TABLE IF EXISTS `verschreibung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `verschreibung` (
  `id` varchar(36) NOT NULL,
  `arznei_id` varchar(36) NOT NULL,
  `arzt_id` varchar(36) NOT NULL,
  `patient_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_verschreibung_arznei1_idx` (`arznei_id`),
  KEY `fk_verschreibung_arzt1_idx` (`arzt_id`),
  KEY `fk_verschreibung_patient1_idx` (`patient_id`),
  CONSTRAINT `fk_verschreibung_arznei` FOREIGN KEY (`arznei_id`) REFERENCES `arznei` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_verschreibung_arzt1` FOREIGN KEY (`arzt_id`) REFERENCES `arzt` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_verschreibung_patient1` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verschreibung`
--

LOCK TABLES `verschreibung` WRITE;
/*!40000 ALTER TABLE `verschreibung` DISABLE KEYS */;
INSERT INTO `verschreibung` VALUES ('0b710dce-299a-11f0-8c30-d0008dc32d97','5c3c53e3-2986-11f0-8c30-d0008dc32d97','70df50b7-2994-11f0-8c30-d0008dc32d97','e9385581-298f-11f0-8c30-d0008dc32d97'),('290d82bc-2999-11f0-8c30-d0008dc32d97','0fe2e156-2991-11f0-8c30-d0008dc32d97','70df50b7-2994-11f0-8c30-d0008dc32d97','a577429f-2990-11f0-8c30-d0008dc32d97'),('5b2fd824-2999-11f0-8c30-d0008dc32d97','5c3c53e3-2986-11f0-8c30-d0008dc32d97','fd30ba7a-2986-11f0-8c30-d0008dc32d97','a9346e51-2985-11f0-8c30-d0008dc32d97'),('9b8b0265-2999-11f0-8c30-d0008dc32d97','5ce18fc1-2991-11f0-8c30-d0008dc32d97','0f2bf930-298a-11f0-8c30-d0008dc32d97','b37cfb13-298c-11f0-8c30-d0008dc32d97'),('b288ff61-2999-11f0-8c30-d0008dc32d97','5c3c53e3-2986-11f0-8c30-d0008dc32d97','70df50b7-2994-11f0-8c30-d0008dc32d97','bc45ba1f-298f-11f0-8c30-d0008dc32d97'),('d95c1fa5-2999-11f0-8c30-d0008dc32d97','5ce18fc1-2991-11f0-8c30-d0008dc32d97','0f2bf930-298a-11f0-8c30-d0008dc32d97','cd2387e5-2989-11f0-8c30-d0008dc32d97'),('f82ff250-2999-11f0-8c30-d0008dc32d97','c8555a0f-298d-11f0-8c30-d0008dc32d97','fd30ba7a-2986-11f0-8c30-d0008dc32d97','cf186b2c-298e-11f0-8c30-d0008dc32d97'),('fa13d578-2998-11f0-8c30-d0008dc32d97','5c3c53e3-2986-11f0-8c30-d0008dc32d97','724617e3-25c4-11f0-8c49-d00192da8f3d','a518321c-25c4-11f0-8c49-d00192da8f3d');
/*!40000 ALTER TABLE `verschreibung` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-02 11:23:40
