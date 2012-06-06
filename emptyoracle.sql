-- fishoracle database

-- 
-- Table structure for table `area_access`
-- 

CREATE TABLE `area_access` (
  `area_access_area_name` varchar(64) NOT NULL,
  `area_access_user_id` int NOT NULL default '0',
  `area_access_table_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`area_access_area_name`,`area_access_user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

-- 
-- Table structure for table `chip`
-- 

CREATE TABLE `chip` (
  `chip_id` int UNSIGNED NOT NULL auto_increment,
  `chip_name` varchar(64) NOT NULL,
  `chip_type` varchar(64) NOT NULL,
  PRIMARY KEY  (`chip_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

-- 
-- Table structure for table `microarraystudy`
-- 

CREATE TABLE `microarraystudy` (
  `microarraystudy_id` int UNSIGNED NOT NULL auto_increment,
  `microarraystudy_date_inserted` date NOT NULL,
  `microarraystudy_name` varchar(255) NOT NULL,
  `microarraystudy_description` varchar(255) NOT NULL,
  `microarraystudy_user_id` int NOT NULL,
  `microarraystudy_sample_on_chip_id` int NOT NULL,
  PRIMARY KEY  (`microarraystudy_id`),
  UNIQUE KEY `microarraystudy_name` (`microarraystudy_name`)
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
-- Table structure for table `cnc_segment`
-- 

CREATE TABLE `cn_segment` (
  `cn_segment_id` int UNSIGNED NOT NULL auto_increment,
  `cn_segment_chromosome` varchar(16) NOT NULL,
  `cn_segment_start` int NOT NULL,
  `cn_segment_end` int NOT NULL,
  `cn_segment_mean` double,
  `cn_segment_markers` int,
  `cn_segment_microarraystudy_id` int,
  PRIMARY KEY(`cn_segment_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

-- 
-- Table structure for table `sample_on_chip`
-- 

CREATE TABLE `sample_on_chip` (
  sample_on_chip_id int UNSIGNED NOT NULL auto_increment,
  `sample_on_chip_chip_id` int NOT NULL,
  `sample_on_chip_tissue_sample_id` int NOT NULL,
  `sample_on_chip_microarraystudy_id` int NOT NULL DEFAULT 0,
  PRIMARY KEY  (`sample_on_chip_id`)
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
  PRIMARY KEY  (`tissue_sample_id`)
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
-- Table structure for table `microarraystudy_in_project`
-- 

CREATE TABLE `microarraystudy_in_project` (
  `microarraystudy_in_project_id` int UNSIGNED NOT NULL auto_increment,
  `project_id` int NOT NULL,
  `microarraystudy_id` int NOT NULL,
  PRIMARY KEY  (`microarraystudy_in_project_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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
