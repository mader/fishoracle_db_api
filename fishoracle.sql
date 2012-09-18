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
-- Table structure for table `platform`
-- 

CREATE TABLE `platform` (
  `platform_id` int UNSIGNED NOT NULL auto_increment,
  `platform_name` varchar(64) NOT NULL,
  `platform_type` varchar(64) NOT NULL,
  PRIMARY KEY  (`platform_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 
-- Dumping data for table `chip_table`
-- 

INSERT INTO `platform` (`platform_name`, `platform_type`) VALUES 
('mapping10k_xba142', 'snp'),
('mapping50k_hind240', 'snp'),
('mapping50k_xba240', 'snp'),
('mapping250k_nsp', 'snp'),
('mapping250k_sty', 'snp'),
('hg-u133a_2', 'expression'),
('GenomeWideSNP_6', 'snp');

-- --------------------------------------------------------

-- 
-- Table structure for table `study`
-- 

CREATE TABLE `study` (
  `study_id` int UNSIGNED NOT NULL auto_increment,
  `study_date_inserted` date NOT NULL,
  `study_name` varchar(255) NOT NULL,
  `study_type` varchar(64) NOT NULL,
  `study_description` varchar(255) NOT NULL,
  `study_assembly` varchar(64) NOT NULL,
  `study_user_id` int NOT NULL,
  `study_sample_on_platform_id` int NOT NULL,
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

-- 
-- Dumping data for table `organ`
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
-- Table structure for table `property`
-- 

CREATE TABLE `property` (
  `property_id` int UNSIGNED NOT NULL auto_increment,
  `property_label` varchar(30) NOT NULL,
  `property_type` varchar(30) NOT NULL,
  `property_activity` varchar(30) NOT NULL,
  PRIMARY KEY  (`property_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

-- 
-- Dumping data for table `property`
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
-- Table structure for table `cnc_segment`
-- 

CREATE TABLE `cn_segment` (
  `cn_segment_id` int UNSIGNED NOT NULL auto_increment,
  `location_id` int NOT NULL,
  `cn_segment_mean` double,
  `cn_segment_markers` int,
  `study_id` int,
  PRIMARY KEY(`cn_segment_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

-- 
-- Table structure for table `sample_on_chip`
-- 

CREATE TABLE `sample_on_platform` (
  `sample_on_platform_id` int UNSIGNED NOT NULL auto_increment,
  `sample_on_platform_platform_id` int NOT NULL,
  `sample_on_platform_tissue_sample_id` int NOT NULL,
  `sample_on_platform_study_id` int NOT NULL DEFAULT 0,
  PRIMARY KEY  (`sample_on_platform_id`)
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
-- Table structure for table `study_in_project`
-- 

CREATE TABLE `study_in_project` (
  `study_in_project_id` int UNSIGNED NOT NULL auto_increment,
  `project_id` int NOT NULL,
  `study_id` int NOT NULL,
  PRIMARY KEY  (`study_in_project_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `mutation`
-- 

CREATE TABLE `mutation` (
  `mutation_id` int UNSIGNED NOT NULL auto_increment,
  `location_id` int NOT NULL,
  `db_snp_id` varchar(64),
  `mut_ref` varchar(1),
  `mut_alt` varchar(1),
  `quality` double,
  `somatic` varchar(64),
  `confidence` varchar(128),
  `snp_tool` varchar(128),
  `study_id` int,
  PRIMARY KEY(`mutation_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

-- 
-- Table structure for table `translocation`
-- 

CREATE TABLE `translocation` (
  `translocation_id` int UNSIGNED NOT NULL auto_increment,
  `location_id` int NOT NULL,
  `translocation_ref_id` int NOT NULL,
  `study_id` int,
  PRIMARY KEY(`translocation_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

-- 
-- Table structure for table `location`
-- 

CREATE TABLE `location` (
  `location_id` int UNSIGNED NOT NULL auto_increment,
  `location_chromosome` varchar(16) NOT NULL,
  `location_start` int NOT NULL,
  `location_end` int NOT NULL,
  PRIMARY KEY  (`location_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `feature`
-- 

CREATE TABLE `feature` (
  `feature_id` int UNSIGNED NOT NULL auto_increment,
  `location_id` int NOT NULL,
  `feature_type` varchar(128) NOT NULL,
  `study_id` int,
  PRIMARY KEY(`feature_id`)
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
