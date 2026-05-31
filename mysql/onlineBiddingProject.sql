-- phpMyAdmin SQL Dump
-- version 5.1.1deb5ubuntu1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Aug 12, 2022 at 01:05 PM
-- Server version: 8.0.30-0ubuntu0.22.04.1
-- PHP Version: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `onlineBiddingProject`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `profile_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`profile_id`) VALUES
(11);

-- --------------------------------------------------------

--
-- Table structure for table `auction`
--

CREATE TABLE `auction` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `category_id` int DEFAULT NULL,
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `status` int NOT NULL,
  `time_start` datetime NOT NULL,
  `time_end` datetime NOT NULL,
  `price_start` double DEFAULT '0',
  `price_step` double DEFAULT '0',
  `highest_price` double DEFAULT '0',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `auction`
--

-- --------------------------------------------------------

--
-- Table structure for table `auction_user`
--

CREATE TABLE `auction_user` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `auction_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `auction_user`
--

-- --------------------------------------------------------

--
-- Table structure for table `bid`
--

CREATE TABLE `bid` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `auction_id` int NOT NULL,
  `price` double NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `bid`
--

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `name`) VALUES
(1, 'Đồ điện tử'),
(2, 'Thời trang'),
(3, 'Đồ nội thất'),
(4, 'Giải trí'),
(5, 'Nhà cửa'),
(6, 'Sức khoẻ'),
(7, 'Thú cưng'),
(8, 'Nghệ thuật'),
(9, 'Trang sức'),
(10, 'Thực phẩm'),
(11, 'Khoa học');

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `id` int NOT NULL,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `amount` int NOT NULL,
  `auction_id` int NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `item`
--


-- --------------------------------------------------------

--
-- Table structure for table `item_image`
--

CREATE TABLE `item_image` (
  `id` int NOT NULL,
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `public_id` varchar(1000) DEFAULT NULL,
  `item_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `item_image`
--

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE `notification` (
  `id` int NOT NULL,
  `profile_id` int DEFAULT NULL,
  `notification_type` varchar(50) NOT NULL,
  `entity_type` varchar(25) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `notification`
--

-- --------------------------------------------------------

--
-- Table structure for table `notification_auction`
--

CREATE TABLE `notification_auction` (
  `id` int NOT NULL,
  `auction_id` int NOT NULL,
  `notification_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `notification_auction`
--

-- --------------------------------------------------------

--
-- Table structure for table `notification_notified`
--

CREATE TABLE `notification_notified` (
  `id` int NOT NULL,
  `notification_id` int NOT NULL,
  `profile_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `notification_notified`
--


-- --------------------------------------------------------

--
-- Table structure for table `notification_report`
--

CREATE TABLE `notification_report` (
  `id` int NOT NULL,
  `report_id` int NOT NULL,
  `notification_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `notification_report`
--


-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

CREATE TABLE `profile` (
  `id` int NOT NULL,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `bio` varchar(10000) DEFAULT NULL,
  `legitimate_score` int NOT NULL DEFAULT '0',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` int NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `role_id` int NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `profile`
--

INSERT INTO `profile` (`id`, `username`, `password`, `bio`, `legitimate_score`, `name`, `status`, `email`, `image_url`, `role_id`, `created_at`, `updated_at`) VALUES
(6, 'nguoidung1', '$2a$10$dYlSRalQ1PpbzCS3yvuEt.NRmlnqTvJ5cLuK6IiTT2E2S9NagM..e', 'https://www.facebook.com/leothanhng/', 1, 'thanhthanh', 2, 'thanhthanhthanh@gmail.com', NULL, 2, '2022-07-05 14:08:36', '2022-08-12 12:01:00'),
(8, 'nguoidung3', '$2a$10$2s/6CA8LQnptxgaLslGQrupfBbf8LdKGQXaC9kS0Cxe4pjj8Qu7GK', NULL, 0, 'nguoidung', 2, NULL, NULL, 2, '2022-07-06 12:08:27', '2022-07-06 12:08:27'),
(9, 'nguoidung4', '$2a$10$5cebCC/r0VADhNvcT7MluOgXmzYTAOCyLxmPjr7owOh.reR6JA1Mu', NULL, 0, 'nguoidung', 2, NULL, NULL, 2, '2022-07-06 13:52:04', '2022-07-06 13:52:04'),
(10, 'nguoidung5', '$2a$10$DU4mDl6z.rh/tGUZWHvQQe5uMaQwM5WcdJzY5oaRLsllaVXPlq2hm', NULL, 0, 'nguoidung', 2, NULL, NULL, 2, '2022-07-06 13:54:14', '2022-08-10 21:51:52'),
(11, 'adminadmin', '$2a$10$n7rgvfnOhz8MesOsHpHwJ.Djuz4/ohBXg1pV7RonC683erIQHsu8m', '', 0, 'adminadmin', 2, NULL, NULL, 1, '2022-07-06 22:38:52', '2022-08-11 15:11:30'),
(12, 'nguoidung', '$2a$10$xpqqLaxYLxRB79mMlLOBTu8XthDx6h2fhO.dWGq2MDF0kHCgXV6L2', NULL, 0, 'thanhthanh', 2, NULL, NULL, 2, '2022-07-08 23:19:15', '2022-07-08 23:19:15'),
(13, 'thanhng260588', '$2a$10$pUd9A3Sf16Z8VdS6lWRO0.yMAxuOjONINLz69Ua27SzQSczgO6klm', NULL, 0, 'thanhthanh', 2, 'thanhng260588@gmail.com', NULL, 2, '2022-07-25 09:50:40', '2022-08-11 16:02:02');

-- --------------------------------------------------------

--
-- Table structure for table `QRTZ_BLOB_TRIGGERS`
--

CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `BLOB_DATA` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `QRTZ_CALENDARS`
--

CREATE TABLE `QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(190) NOT NULL,
  `CALENDAR` blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `QRTZ_CRON_TRIGGERS`
--

CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `QRTZ_FIRED_TRIGGERS`
--

CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `INSTANCE_NAME` varchar(190) NOT NULL,
  `FIRED_TIME` bigint NOT NULL,
  `SCHED_TIME` bigint NOT NULL,
  `PRIORITY` int NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(190) DEFAULT NULL,
  `JOB_GROUP` varchar(190) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `QRTZ_JOB_DETAILS`
--

CREATE TABLE `QRTZ_JOB_DETAILS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(190) NOT NULL,
  `JOB_GROUP` varchar(190) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `QRTZ_JOB_DETAILS`
--

-- --------------------------------------------------------

--
-- Table structure for table `QRTZ_LOCKS`
--

CREATE TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `QRTZ_LOCKS`
--

INSERT INTO `QRTZ_LOCKS` (`SCHED_NAME`, `LOCK_NAME`) VALUES
('quartzScheduler', 'TRIGGER_ACCESS');

-- --------------------------------------------------------

--
-- Table structure for table `QRTZ_PAUSED_TRIGGER_GRPS`
--

CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `QRTZ_SCHEDULER_STATE`
--

CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(190) NOT NULL,
  `LAST_CHECKIN_TIME` bigint NOT NULL,
  `CHECKIN_INTERVAL` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `QRTZ_SIMPLE_TRIGGERS`
--

CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `REPEAT_COUNT` bigint NOT NULL,
  `REPEAT_INTERVAL` bigint NOT NULL,
  `TIMES_TRIGGERED` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `QRTZ_SIMPLE_TRIGGERS`
--

-- --------------------------------------------------------

--
-- Table structure for table `QRTZ_SIMPROP_TRIGGERS`
--

CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int DEFAULT NULL,
  `INT_PROP_2` int DEFAULT NULL,
  `LONG_PROP_1` bigint DEFAULT NULL,
  `LONG_PROP_2` bigint DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `QRTZ_TRIGGERS`
--

CREATE TABLE `QRTZ_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `JOB_NAME` varchar(190) NOT NULL,
  `JOB_GROUP` varchar(190) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint DEFAULT NULL,
  `PREV_FIRE_TIME` bigint DEFAULT NULL,
  `PRIORITY` int DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint NOT NULL,
  `END_TIME` bigint DEFAULT NULL,
  `CALENDAR_NAME` varchar(190) DEFAULT NULL,
  `MISFIRE_INSTR` smallint DEFAULT NULL,
  `JOB_DATA` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `QRTZ_TRIGGERS`
--


--
-- Table structure for table `report`
--

CREATE TABLE `report` (
  `id` int NOT NULL,
  `user_reporter_id` int NOT NULL,
  `user_reported_id` int NOT NULL,
  `auction_id` int DEFAULT NULL,
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `report`
--

-- --------------------------------------------------------

--
-- Table structure for table `report_image`
--

CREATE TABLE `report_image` (
  `id` int NOT NULL,
  `report_id` int NOT NULL,
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `public_id` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `report_result`
--

CREATE TABLE `report_result` (
  `id` int NOT NULL,
  `admin_id` int NOT NULL,
  `report_id` int NOT NULL,
  `result` int NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `review_result`
--

CREATE TABLE `review_result` (
  `id` int NOT NULL,
  `admin_id` int NOT NULL,
  `auction_id` int NOT NULL,
  `result` int NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `review_result`
--

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` int NOT NULL,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'ADMIN'),
(2, 'USER');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `profile_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`profile_id`) VALUES
(6),
(8),
(9),
(10),
(12),
(13);

-- --------------------------------------------------------

--
-- Table structure for table `verification_token`
--

CREATE TABLE `verification_token` (
  `id` int NOT NULL,
  `expiration_time` datetime NOT NULL,
  `token` varchar(1000) NOT NULL,
  `profile_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `winner`
--

CREATE TABLE `winner` (
  `id` int NOT NULL,
  `bid_id` int NOT NULL,
  `auction_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `winner`
--

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`profile_id`),
  ADD UNIQUE KEY `user_id` (`profile_id`);

--
-- Indexes for table `auction`
--
ALTER TABLE `auction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `category_id` (`category_id`);

--
-- Indexes for table `auction_user`
--
ALTER TABLE `auction_user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nguoidung_id` (`user_id`,`auction_id`),
  ADD KEY `baidaugia_id` (`auction_id`);

--
-- Indexes for table `bid`
--
ALTER TABLE `bid`
  ADD PRIMARY KEY (`id`),
  ADD KEY `baidaugia_id` (`auction_id`),
  ADD KEY `nguoidung_id` (`user_id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `auction_id` (`auction_id`);

--
-- Indexes for table `item_image`
--
ALTER TABLE `item_image`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sanpham_id` (`item_id`);

--
-- Indexes for table `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`id`),
  ADD KEY `profile_id` (`profile_id`);

--
-- Indexes for table `notification_auction`
--
ALTER TABLE `notification_auction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `auction_id` (`auction_id`),
  ADD KEY `notification_id` (`notification_id`);

--
-- Indexes for table `notification_notified`
--
ALTER TABLE `notification_notified`
  ADD PRIMARY KEY (`id`),
  ADD KEY `notification_id` (`notification_id`),
  ADD KEY `profile_id` (`profile_id`);

--
-- Indexes for table `notification_report`
--
ALTER TABLE `notification_report`
  ADD PRIMARY KEY (`id`),
  ADD KEY `report_id` (`report_id`),
  ADD KEY `notification_id` (`notification_id`);

--
-- Indexes for table `profile`
--
ALTER TABLE `profile`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `tai_khoan` (`username`),
  ADD KEY `vaitro_id` (`role_id`);

--
-- Indexes for table `QRTZ_BLOB_TRIGGERS`
--
ALTER TABLE `QRTZ_BLOB_TRIGGERS`
  ADD PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  ADD KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`);

--
-- Indexes for table `QRTZ_CALENDARS`
--
ALTER TABLE `QRTZ_CALENDARS`
  ADD PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`);

--
-- Indexes for table `QRTZ_CRON_TRIGGERS`
--
ALTER TABLE `QRTZ_CRON_TRIGGERS`
  ADD PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`);

--
-- Indexes for table `QRTZ_FIRED_TRIGGERS`
--
ALTER TABLE `QRTZ_FIRED_TRIGGERS`
  ADD PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  ADD KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  ADD KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  ADD KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  ADD KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  ADD KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  ADD KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`);

--
-- Indexes for table `QRTZ_JOB_DETAILS`
--
ALTER TABLE `QRTZ_JOB_DETAILS`
  ADD PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  ADD KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  ADD KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`);

--
-- Indexes for table `QRTZ_LOCKS`
--
ALTER TABLE `QRTZ_LOCKS`
  ADD PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`);

--
-- Indexes for table `QRTZ_PAUSED_TRIGGER_GRPS`
--
ALTER TABLE `QRTZ_PAUSED_TRIGGER_GRPS`
  ADD PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`);

--
-- Indexes for table `QRTZ_SCHEDULER_STATE`
--
ALTER TABLE `QRTZ_SCHEDULER_STATE`
  ADD PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`);

--
-- Indexes for table `QRTZ_SIMPLE_TRIGGERS`
--
ALTER TABLE `QRTZ_SIMPLE_TRIGGERS`
  ADD PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`);

--
-- Indexes for table `QRTZ_SIMPROP_TRIGGERS`
--
ALTER TABLE `QRTZ_SIMPROP_TRIGGERS`
  ADD PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`);

--
-- Indexes for table `QRTZ_TRIGGERS`
--
ALTER TABLE `QRTZ_TRIGGERS`
  ADD PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  ADD KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  ADD KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  ADD KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  ADD KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  ADD KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  ADD KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  ADD KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  ADD KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  ADD KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  ADD KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  ADD KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  ADD KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`);

--
-- Indexes for table `report`
--
ALTER TABLE `report`
  ADD PRIMARY KEY (`id`),
  ADD KEY `baidaugia_id` (`auction_id`),
  ADD KEY `nguoidung_id_reported` (`user_reported_id`),
  ADD KEY `nguoidung_id_reporter` (`user_reporter_id`);

--
-- Indexes for table `report_image`
--
ALTER TABLE `report_image`
  ADD PRIMARY KEY (`id`),
  ADD KEY `phieubaocao_id` (`report_id`);

--
-- Indexes for table `report_result`
--
ALTER TABLE `report_result`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `admin_id` (`admin_id`,`report_id`),
  ADD KEY `phieubaocao_id` (`report_id`);

--
-- Indexes for table `review_result`
--
ALTER TABLE `review_result`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `admin_id` (`admin_id`,`auction_id`),
  ADD KEY `baidaugia_id` (`auction_id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`profile_id`),
  ADD UNIQUE KEY `user_id` (`profile_id`);

--
-- Indexes for table `verification_token`
--
ALTER TABLE `verification_token`
  ADD PRIMARY KEY (`id`),
  ADD KEY `verification_token_ibfk_1` (`profile_id`);

--
-- Indexes for table `winner`
--
ALTER TABLE `winner`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `auction_id` (`auction_id`),
  ADD KEY `lenhdaugia_id` (`bid_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `auction`
--
ALTER TABLE `auction`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=99;

--
-- AUTO_INCREMENT for table `auction_user`
--
ALTER TABLE `auction_user`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `bid`
--
ALTER TABLE `bid`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=137;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `item`
--
ALTER TABLE `item`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=89;

--
-- AUTO_INCREMENT for table `item_image`
--
ALTER TABLE `item_image`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;

--
-- AUTO_INCREMENT for table `notification`
--
ALTER TABLE `notification`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;

--
-- AUTO_INCREMENT for table `notification_auction`
--
ALTER TABLE `notification_auction`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;

--
-- AUTO_INCREMENT for table `notification_notified`
--
ALTER TABLE `notification_notified`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=81;

--
-- AUTO_INCREMENT for table `notification_report`
--
ALTER TABLE `notification_report`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `profile`
--
ALTER TABLE `profile`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `report`
--
ALTER TABLE `report`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `report_image`
--
ALTER TABLE `report_image`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `report_result`
--
ALTER TABLE `report_result`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `review_result`
--
ALTER TABLE `review_result`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `verification_token`
--
ALTER TABLE `verification_token`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `winner`
--
ALTER TABLE `winner`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=681;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;

--
-- Constraints for table `auction`
--
ALTER TABLE `auction`
  ADD CONSTRAINT `auction_ibfk_5` FOREIGN KEY (`user_id`) REFERENCES `user` (`profile_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  ADD CONSTRAINT `auction_ibfk_6` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT;

--
-- Constraints for table `auction_user`
--
ALTER TABLE `auction_user`
  ADD CONSTRAINT `auction_user_ibfk_1` FOREIGN KEY (`auction_id`) REFERENCES `auction` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  ADD CONSTRAINT `auction_user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`profile_id`) ON DELETE CASCADE ON UPDATE RESTRICT;

--
-- Constraints for table `bid`
--
ALTER TABLE `bid`
  ADD CONSTRAINT `bid_ibfk_1` FOREIGN KEY (`auction_id`) REFERENCES `auction` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  ADD CONSTRAINT `bid_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`profile_id`) ON DELETE CASCADE ON UPDATE RESTRICT;

--
-- Constraints for table `item`
--
ALTER TABLE `item`
  ADD CONSTRAINT `item_ibfk_1` FOREIGN KEY (`auction_id`) REFERENCES `auction` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `item_image`
--
ALTER TABLE `item_image`
  ADD CONSTRAINT `item_image_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;

--
-- Constraints for table `notification`
--
ALTER TABLE `notification`
  ADD CONSTRAINT `notification_ibfk_1` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;

--
-- Constraints for table `notification_auction`
--
ALTER TABLE `notification_auction`
  ADD CONSTRAINT `notification_auction_ibfk_1` FOREIGN KEY (`auction_id`) REFERENCES `auction` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  ADD CONSTRAINT `notification_auction_ibfk_2` FOREIGN KEY (`notification_id`) REFERENCES `notification` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;

--
-- Constraints for table `notification_notified`
--
ALTER TABLE `notification_notified`
  ADD CONSTRAINT `notification_notified_ibfk_1` FOREIGN KEY (`notification_id`) REFERENCES `notification` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  ADD CONSTRAINT `notification_notified_ibfk_2` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;

--
-- Constraints for table `notification_report`
--
ALTER TABLE `notification_report`
  ADD CONSTRAINT `notification_report_ibfk_1` FOREIGN KEY (`report_id`) REFERENCES `report` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  ADD CONSTRAINT `notification_report_ibfk_2` FOREIGN KEY (`notification_id`) REFERENCES `notification` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;

--
-- Constraints for table `profile`
--
ALTER TABLE `profile`
  ADD CONSTRAINT `profile_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `QRTZ_BLOB_TRIGGERS`
--
ALTER TABLE `QRTZ_BLOB_TRIGGERS`
  ADD CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`);

--
-- Constraints for table `QRTZ_CRON_TRIGGERS`
--
ALTER TABLE `QRTZ_CRON_TRIGGERS`
  ADD CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`);

--
-- Constraints for table `QRTZ_SIMPLE_TRIGGERS`
--
ALTER TABLE `QRTZ_SIMPLE_TRIGGERS`
  ADD CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`);

--
-- Constraints for table `QRTZ_SIMPROP_TRIGGERS`
--
ALTER TABLE `QRTZ_SIMPROP_TRIGGERS`
  ADD CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`);

--
-- Constraints for table `QRTZ_TRIGGERS`
--
ALTER TABLE `QRTZ_TRIGGERS`
  ADD CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`);

--
-- Constraints for table `report`
--
ALTER TABLE `report`
  ADD CONSTRAINT `report_ibfk_1` FOREIGN KEY (`auction_id`) REFERENCES `auction` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  ADD CONSTRAINT `report_ibfk_3` FOREIGN KEY (`user_reported_id`) REFERENCES `user` (`profile_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  ADD CONSTRAINT `report_ibfk_4` FOREIGN KEY (`user_reporter_id`) REFERENCES `user` (`profile_id`) ON DELETE CASCADE ON UPDATE RESTRICT;

--
-- Constraints for table `report_image`
--
ALTER TABLE `report_image`
  ADD CONSTRAINT `report_image_ibfk_1` FOREIGN KEY (`report_id`) REFERENCES `report` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;

--
-- Constraints for table `report_result`
--
ALTER TABLE `report_result`
  ADD CONSTRAINT `report_result_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`profile_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  ADD CONSTRAINT `report_result_ibfk_2` FOREIGN KEY (`report_id`) REFERENCES `report` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;

--
-- Constraints for table `review_result`
--
ALTER TABLE `review_result`
  ADD CONSTRAINT `review_result_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`profile_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  ADD CONSTRAINT `review_result_ibfk_2` FOREIGN KEY (`auction_id`) REFERENCES `auction` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;

--
-- Constraints for table `verification_token`
--
ALTER TABLE `verification_token`
  ADD CONSTRAINT `verification_token_ibfk_1` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;

--
-- Constraints for table `winner`
--
ALTER TABLE `winner`
  ADD CONSTRAINT `winner_ibfk_1` FOREIGN KEY (`bid_id`) REFERENCES `bid` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  ADD CONSTRAINT `winner_ibfk_2` FOREIGN KEY (`auction_id`) REFERENCES `auction` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
