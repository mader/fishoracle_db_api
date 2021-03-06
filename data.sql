 
INSERT INTO `platform` (`platform_name`, `platform_type`) VALUES 
('mapping10k_xba142', 'snp'),
('mapping50k_hind240', 'snp'),
('mapping50k_xba240', 'snp'),
('mapping250k_nsp', 'snp'),
('mapping250k_sty', 'snp'),
('hg-u133a_2', 'expression'),
('GenomeWideSNP_6', 'snp');

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

