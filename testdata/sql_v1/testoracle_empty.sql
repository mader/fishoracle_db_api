-- phpMyAdmin SQL Dump
-- version 3.4.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 01. Aug 2012 um 14:25
-- Server Version: 5.1.57
-- PHP-Version: 5.3.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `testoracle`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `area_access`
--

CREATE TABLE IF NOT EXISTS `area_access` (
  `area_access_area_name` varchar(64) NOT NULL,
  `area_access_user_id` int(11) NOT NULL DEFAULT '0',
  `area_access_table_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`area_access_area_name`,`area_access_user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `cn_segment`
--

CREATE TABLE IF NOT EXISTS `cn_segment` (
  `cn_segment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `location_id` int(11) NOT NULL,
  `cn_segment_mean` double DEFAULT NULL,
  `cn_segment_markers` int(11) DEFAULT NULL,
  `study_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`cn_segment_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ensembl_dbs`
--

CREATE TABLE IF NOT EXISTS `ensembl_dbs` (
  `ensembl_dbs_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `db_name` varchar(128) NOT NULL,
  `label` varchar(128) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`ensembl_dbs_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `fo_group`
--

CREATE TABLE IF NOT EXISTS `fo_group` (
  `fo_group_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `isactive` int(11) NOT NULL,
  PRIMARY KEY (`fo_group_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Daten für Tabelle `fo_group`
--

INSERT INTO `fo_group` (`fo_group_id`, `name`, `isactive`) VALUES
(1, 'Staff', 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `group_project_access`
--

CREATE TABLE IF NOT EXISTS `group_project_access` (
  `group_project_access_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `access_type` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`group_project_access_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Daten für Tabelle `group_project_access`
--

INSERT INTO `group_project_access` (`group_project_access_id`, `project_id`, `group_id`, `access_type`) VALUES
(1, 1, 1, 'rw'),
(2, 2, 1, 'rw'),
(3, 3, 1, 'r');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `location`
--

CREATE TABLE IF NOT EXISTS `location` (
  `location_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `location_chromosome` varchar(16) NOT NULL,
  `location_start` int(11) NOT NULL,
  `location_end` int(11) NOT NULL,
  PRIMARY KEY (`location_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `mutation`
--

CREATE TABLE IF NOT EXISTS `mutation` (
  `mutation_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `location_id` int(11) NOT NULL,
  `db_snp_id` varchar(64) DEFAULT NULL,
  `mut_ref` varchar(1) DEFAULT NULL,
  `mut_alt` varchar(1) DEFAULT NULL,
  `quality` double DEFAULT NULL,
  `somatic` varchar(64) DEFAULT NULL,
  `confidence` varchar(128) DEFAULT NULL,
  `snp_tool` varchar(128) DEFAULT NULL,
  `study_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`mutation_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `organ`
--

CREATE TABLE IF NOT EXISTS `organ` (
  `organ_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `organ_label` varchar(64) NOT NULL,
  `organ_type` varchar(30) NOT NULL,
  `organ_activity` varchar(30) NOT NULL,
  PRIMARY KEY (`organ_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=29 ;

--
-- Daten für Tabelle `organ`
--

INSERT INTO `organ` (`organ_id`, `organ_label`, `organ_type`, `organ_activity`) VALUES
(1, 'Prostate', 'Tumor tissue', 'enabled'),
(2, 'Prostate', 'Cell line', 'enabled'),
(3, 'Kidney', 'Tumor tissue', 'enabled'),
(4, 'Kidney', 'Cell line', 'enabled'),
(5, 'Esophagus', 'Tumor tissue', 'enabled'),
(6, 'Esophagus', 'Cell line', 'enabled'),
(7, 'Pancreas', 'Tumor tissue', 'enabled'),
(8, 'Pancreas', 'Cell line', 'enabled'),
(9, 'Lung', 'Tumor tissue', 'enabled'),
(10, 'Lung', 'Cell line', 'enabled'),
(11, 'Colon', 'Tumor tissue', 'enabled'),
(12, 'Colon', 'Cell line', 'enabled'),
(13, 'Breast', 'Tumor tissue', 'enabled'),
(14, 'Breast', 'Cell line', 'enabled'),
(15, 'Oral cavity', 'Tumor tissue', 'enabled'),
(16, 'Oral cavity', 'Cell line', 'enabled'),
(17, 'Ovary', 'Tumor tissue', 'enabled'),
(18, 'Ovary', 'Cell line', 'enabled'),
(19, 'Endometrium', 'Tumor tissue', 'enabled'),
(20, 'Endometrium', 'Cell line', 'enabled'),
(21, 'Bone', 'Tumor tissue', 'enabled'),
(22, 'Bone', 'Cell line', 'enabled'),
(23, 'Lymphatic', 'Tumor tissue', 'enabled'),
(24, 'Lymphatic', 'Cell line', 'enabled'),
(25, 'Bladder', 'Tumor tissue', 'enabled'),
(26, 'Bladder', 'Cell line', 'enabled'),
(27, 'Liver', 'Tumor tissue', 'enabled'),
(28, 'Liver', 'Cell line', 'enabled');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `platform`
--

CREATE TABLE IF NOT EXISTS `platform` (
  `platform_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `platform_name` varchar(64) NOT NULL,
  `platform_type` varchar(64) NOT NULL,
  PRIMARY KEY (`platform_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Daten für Tabelle `platform`
--

INSERT INTO `platform` (`platform_id`, `platform_name`, `platform_type`) VALUES
(1, 'mapping10k_xba142', 'snp'),
(2, 'mapping50k_hind240', 'snp'),
(3, 'mapping50k_xba240', 'snp'),
(4, 'mapping250k_nsp', 'snp'),
(5, 'mapping250k_sty', 'snp'),
(6, 'hg-u133a_2', 'expression'),
(7, 'GenomeWideSNP_6', 'snp');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `project`
--

CREATE TABLE IF NOT EXISTS `project` (
  `project_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`project_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Daten für Tabelle `project`
--

INSERT INTO `project` (`project_id`, `name`, `description`) VALUES
(1, 'PTEN_1', 'Set 1 for PTEN'),
(2, 'PTEN_2', 'Set 2 for PTEN'),
(3, 'RYBP', 'Set for RYBP');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `property`
--

CREATE TABLE IF NOT EXISTS `property` (
  `property_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `property_label` varchar(30) NOT NULL,
  `property_type` varchar(30) NOT NULL,
  `property_activity` varchar(30) NOT NULL,
  PRIMARY KEY (`property_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Daten für Tabelle `property`
--

INSERT INTO `property` (`property_id`, `property_label`, `property_type`, `property_activity`) VALUES
(1, 'G0', 'grade', 'enabled'),
(2, 'G1', 'grade', 'enabled'),
(3, 'G2', 'grade', 'enabled'),
(4, 'G3', 'grade', 'enabled'),
(5, 'pT1', 'stage', 'enabled'),
(6, 'pT2', 'stage', 'enabled'),
(7, 'pT3', 'stage', 'enabled'),
(8, 'pT4', 'stage', 'enabled'),
(9, 'premalignant', 'stage', 'enabled'),
(10, 'Primary N0', 'meta status', 'enabled'),
(11, 'Primary N+', 'meta status', 'enabled'),
(12, 'LN Metastasis', 'meta status', 'enabled'),
(13, 'Distant Metastasis', 'meta status', 'enabled');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `sample_on_platform`
--

CREATE TABLE IF NOT EXISTS `sample_on_platform` (
  `sample_on_platform_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `sample_on_platform_platform_id` int(11) NOT NULL,
  `sample_on_platform_tissue_sample_id` int(11) NOT NULL,
  `sample_on_platform_study_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`sample_on_platform_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `study`
--

CREATE TABLE IF NOT EXISTS `study` (
  `study_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `study_date_inserted` date NOT NULL,
  `study_name` varchar(255) NOT NULL,
  `study_type` varchar(64) NOT NULL,
  `study_description` varchar(255) NOT NULL,
  `study_assembly` varchar(64) NOT NULL,
  `study_user_id` int(11) NOT NULL,
  `study_sample_on_platform_id` int(11) NOT NULL,
  PRIMARY KEY (`study_id`),
  UNIQUE KEY `study_name` (`study_name`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `study_in_project`
--

CREATE TABLE IF NOT EXISTS `study_in_project` (
  `study_in_project_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL,
  `study_id` int(11) NOT NULL,
  PRIMARY KEY (`study_in_project_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tissue_sample`
--

CREATE TABLE IF NOT EXISTS `tissue_sample` (
  `tissue_sample_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tissue_sample_organ_id` int(11) NOT NULL,
  PRIMARY KEY (`tissue_sample_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tissue_sample_property`
--

CREATE TABLE IF NOT EXISTS `tissue_sample_property` (
  `tissue_sample_property_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tissue_sample_id` int(11) NOT NULL,
  `property_id` int(11) NOT NULL,
  PRIMARY KEY (`tissue_sample_property_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(128) DEFAULT NULL,
  `last_name` varchar(128) DEFAULT NULL,
  `username` varchar(128) NOT NULL,
  `email` varchar(128) NOT NULL,
  `password` varchar(128) NOT NULL,
  `isactive` int(11) NOT NULL,
  `isadmin` int(11) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Daten für Tabelle `user`
--

INSERT INTO `user` (`user_id`, `first_name`, `last_name`, `username`, `email`, `password`, `isactive`, `isadmin`) VALUES
(1, 'test', 'test', 'test', 'test@test.test', '40bd001563085fc35165329ea1ff5c5ecbdbbeef', 1, 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user_in_group`
--

CREATE TABLE IF NOT EXISTS `user_in_group` (
  `user_in_group_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`user_in_group_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Daten für Tabelle `user_in_group`
--

INSERT INTO `user_in_group` (`user_in_group_id`, `user_id`, `group_id`) VALUES
(1, 1, 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
