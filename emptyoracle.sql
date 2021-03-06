-- fishoracle database

-- 
-- Table structure for table `platform`
-- 

CREATE TABLE `platform` (
  `platform_id` int UNSIGNED NOT NULL auto_increment,
  `platform_name` varchar(64) NOT NULL,
  `platform_type` varchar(64) NOT NULL,
  PRIMARY KEY  (`platform_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

-- 
-- Table structure for table `study`
-- 

CREATE TABLE `study` (
  `study_id` int UNSIGNED NOT NULL auto_increment,
  `study_date_inserted` date NOT NULL,
  `study_name` varchar(255) NOT NULL,
  `study_description` varchar(255) NOT NULL,
  `study_assembly` varchar(64) NOT NULL,
  `study_user_id` int NOT NULL,
  PRIMARY KEY  (`study_id`),
  UNIQUE KEY `study_name` (`study_name`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `organ`
-- 

CREATE TABLE `organ` (
  `organ_id` int UNSIGNED NOT NULL auto_increment,
  `organ_label` varchar(64) NOT NULL,
  `organ_type` varchar(30) NOT NULL,
  `organ_activity` varchar(30) NOT NULL,
  PRIMARY KEY  (`organ_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `property`
-- 

CREATE TABLE `property` (
  `property_id` int UNSIGNED NOT NULL auto_increment,
  `property_label` varchar(30) NOT NULL,
  `property_type` varchar(30) NOT NULL,
  `property_activity` varchar(30) NOT NULL,
  PRIMARY KEY  (`property_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

-- --------------------------------------------------------

-- 
-- Table structure for table `segment`
-- 

CREATE TABLE `segment` (
  `segment_id` int UNSIGNED NOT NULL auto_increment,
  `chromosome` varchar(16) NOT NULL,
  `start` int NOT NULL,
  `end` int NOT NULL,
  `mean` double NOT NULL DEFAULT 0.0,
  `markers` int NOT NULL DEFAULT 0,
  `status` int NOT NULL DEFAULT -1,
  `status_score` double NOT NULL DEFAULT 0.0,
  `type` varchar(24) NOT NULL DEFAULT "",
  `platform_id` int,
  `study_id` int,
  PRIMARY KEY(`segment_id`),
  INDEX (`chromosome`,`start`,`end`),
  INDEX (`mean`),
  INDEX (`status`),
  INDEX (`platform_id`),
  INDEX (`study_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

-- 
-- Table structure for table `tissue_sample_property`
-- 

CREATE TABLE `tissue_sample_property` (
  `tissue_sample_property_id` int UNSIGNED NOT NULL auto_increment,
  `tissue_sample_id` int NOT NULL,
  `property_id` int NOT NULL,
  PRIMARY KEY  (`tissue_sample_property_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


-- --------------------------------------------------------

-- 
-- Table structure for table `tissue_sample`
-- 

CREATE TABLE `tissue_sample` (
  `tissue_sample_id` int UNSIGNED  NOT NULL auto_increment,
  `tissue_sample_organ_id` int NOT NULL,
  `study_id` int NOT NULL,
  PRIMARY KEY  (`tissue_sample_id`),
  INDEX (`study_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `user`
-- 

CREATE TABLE `user` (
  `user_id` int UNSIGNED NOT NULL auto_increment,
  `first_name` varchar(128),
  `last_name` varchar(128),
  `username` varchar(128) NOT NULL,
  `email` varchar(128) NOT NULL,
  `password` varchar(128) NOT NULL,
  `isactive` int NOT NULL,
  `isadmin` int NOT NULL,
  PRIMARY KEY  (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `group`
-- 

CREATE TABLE `fo_group` (
  `fo_group_id` int UNSIGNED NOT NULL auto_increment,
  `name` varchar(128),
  `isactive` int NOT NULL,
  PRIMARY KEY  (`fo_group_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `user_in_group`
-- 

CREATE TABLE `user_in_group` (
  `user_in_group_id` int UNSIGNED NOT NULL auto_increment,
  `user_id` int NOT NULL,
  `group_id` int NOT NULL,
  PRIMARY KEY  (`user_in_group_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `project`
-- 

CREATE TABLE `project` (
  `project_id` int UNSIGNED NOT NULL auto_increment,
  `name` varchar(128),
  `description` varchar(1024),
  PRIMARY KEY  (`project_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `group_project_access`
-- 

CREATE TABLE `group_project_access` (
  `group_project_access_id` int UNSIGNED NOT NULL auto_increment,
  `project_id` int NOT NULL,
  `group_id` int NOT NULL,
  `access_type` varchar(2),
  PRIMARY KEY  (`group_project_access_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `study_in_project`
-- 

CREATE TABLE `study_in_project` (
  `study_in_project_id` int UNSIGNED NOT NULL auto_increment,
  `project_id` int NOT NULL,
  `study_id` int NOT NULL,
  PRIMARY KEY  (`study_in_project_id`),
  INDEX (`project_id`),
  INDEX (`study_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `mutation`
-- 

CREATE TABLE `mutation` (
  `mutation_id` int UNSIGNED NOT NULL auto_increment,
  `chromosome` varchar(16) NOT NULL,
  `start` int NOT NULL,
  `end` int NOT NULL,
  `db_snp_id` varchar(64),
  `mut_ref` varchar(1),
  `mut_alt` varchar(1),
  `quality` double,
  `somatic` varchar(64),
  `confidence` varchar(128),
  `snp_tool` varchar(128),
  `platform_id` int NOT NULL,
  `study_id` int,
  PRIMARY KEY(`mutation_id`),
  INDEX (`chromosome`,`start`,`end`),
  INDEX (`quality`),
  INDEX (`somatic`),
  INDEX (`confidence`),
  INDEX (`snp_tool`),
  INDEX (`platform_id`),
  INDEX (`study_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

-- 
-- Table structure for table `translocation`
-- 

CREATE TABLE `translocation` (
  `translocation_id` int UNSIGNED NOT NULL auto_increment,
  `chromosome` varchar(16) NOT NULL,
  `start` int NOT NULL,
  `end` int NOT NULL,
  `translocation_ref_id` int NOT NULL,
  `platform_id` int NOT NULL,
  `study_id` int,
  PRIMARY KEY(`translocation_id`),
  INDEX (`chromosome`,`start`,`end`),
  INDEX (`translocation_ref_id`),
  INDEX (`platform_id`),
  INDEX (`study_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

-- 
-- Table structure for table `feature`
-- 

CREATE TABLE `feature` (
  `feature_id` int UNSIGNED NOT NULL auto_increment,
  `chromosome` varchar(16) NOT NULL,
  `start` int NOT NULL,
  `end` int NOT NULL,
  `feature_type` varchar(128) NOT NULL,
  `name` varchar(128),
  `platform_id` int NOT NULL,
  `study_id` int,
  PRIMARY KEY(`feature_id`),
  INDEX (`chromosome`,`start`,`end`),
  INDEX (`feature_type`),
  INDEX (`platform_id`),
  INDEX (`study_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

-- 
-- Table structure for table `ensembl_dbs`
-- 

CREATE TABLE `ensembl_dbs` (
  `ensembl_dbs_id` int UNSIGNED NOT NULL auto_increment,
  `db_name` varchar(128) NOT NULL,
  `label` varchar(128) NOT NULL,
  `version` int NOT NULL,
  PRIMARY KEY  (`ensembl_dbs_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `config_attribute`
-- 

CREATE TABLE `config_attribute` (
  `config_attribute_id` int UNSIGNED NOT NULL auto_increment,
  `key` varchar(32) NOT NULL,
  `value` text NOT NULL,
  PRIMARY KEY  (`config_attribute_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `config`
-- 

CREATE TABLE `config` (
  `config_id` int UNSIGNED NOT NULL auto_increment,
  `user_id` INT NOT NULL,
  `name` varchar(128) NOT NULL,
  PRIMARY KEY  (`config_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `config`
-- 

CREATE TABLE `track_config` (
  `track_config_id` int UNSIGNED NOT NULL auto_increment,
  `config_id` INT NOT NULL,
  `title` varchar(32) NOT NULL,
  `track_number` INT NOT NULL,
  PRIMARY KEY  (`track_config_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `attrib_in_config`
-- 

CREATE TABLE `attrib_in_config` (
  `attrib_in_config_id` int UNSIGNED NOT NULL auto_increment,
  `config_id` INT NOT NULL,
  `config_attribute_id` INT NOT NULL,
  PRIMARY KEY  (`attrib_in_config_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `attrib_in_track_config`
-- 

CREATE TABLE `attrib_in_track_config` (
  `attrib_in_track_config_id` int UNSIGNED NOT NULL auto_increment,
  `track_config_id` INT NOT NULL,
  `config_attribute_id` INT NOT NULL,
  PRIMARY KEY  (`attrib_in_track_config_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;
