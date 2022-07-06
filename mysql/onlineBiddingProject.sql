-- phpMyAdmin SQL Dump
-- version 5.1.1deb5ubuntu1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jul 07, 2022 at 09:31 AM
-- Server version: 8.0.29-0ubuntu0.22.04.2
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
(7),
(11);

-- --------------------------------------------------------

--
-- Table structure for table `auction`
--

CREATE TABLE `auction` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `item_id` int DEFAULT NULL,
  `winner_id` int DEFAULT NULL,
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
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

INSERT INTO `auction` (`id`, `user_id`, `item_id`, `winner_id`, `description`, `status`, `time_start`, `time_end`, `price_start`, `price_step`, `highest_price`, `created_at`, `updated_at`) VALUES
(13, 8, 1, NULL, 'bai dau gia', 0, '2022-07-22 09:50:50', '2022-07-23 09:39:50', 0, 10000, NULL, '2022-07-06 21:21:09', '2022-07-06 21:50:02'),
(14, 8, NULL, NULL, 'bai dau gia', 0, '2022-07-22 09:50:50', '2022-07-23 09:39:50', 0, 10000, 0, '2022-07-06 21:21:18', '2022-07-06 21:21:18'),
(15, 8, NULL, NULL, 'z', 0, '2022-07-22 09:50:50', '2022-07-23 09:39:50', 0, 10000, 0, '2022-07-06 22:39:26', '2022-07-06 22:39:26');

-- --------------------------------------------------------

--
-- Table structure for table `auction_category`
--

CREATE TABLE `auction_category` (
  `id` int NOT NULL,
  `auction_id` int NOT NULL,
  `category_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `auction_user`
--

CREATE TABLE `auction_user` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `auction_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `id` int NOT NULL,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `amount` int NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`id`, `name`, `description`, `amount`, `created_at`, `updated_at`) VALUES
(1, 'item test', 'item test', 1, '2022-07-06 12:32:49', '2022-07-06 12:32:49'),
(2, 'item test', 'item test', 1, '2022-07-06 12:51:59', '2022-07-06 12:51:59'),
(3, 'item test', 'item test', 1, '2022-07-06 13:05:47', '2022-07-06 13:05:47'),
(4, 'item test', 'item test', 1, '2022-07-06 13:51:19', '2022-07-06 13:51:19'),
(5, 'item test', 'item test', 1, '2022-07-06 13:51:43', '2022-07-06 13:51:43'),
(6, 'item test', 'item test', 1, '2022-07-06 13:54:55', '2022-07-06 13:54:55'),
(7, 'item test', 'item test', 1, '2022-07-06 14:07:17', '2022-07-06 14:07:17'),
(8, 'item test', 'item test', 1, '2022-07-06 14:07:57', '2022-07-06 14:07:57'),
(9, 'item test', 'item test', 1, '2022-07-06 14:39:26', '2022-07-06 14:39:26'),
(10, 'item test', 'item test', 1, '2022-07-06 16:06:57', '2022-07-06 16:06:57');

-- --------------------------------------------------------

--
-- Table structure for table `item_image`
--

CREATE TABLE `item_image` (
  `id` int NOT NULL,
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `item_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

CREATE TABLE `profile` (
  `id` int NOT NULL,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
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

INSERT INTO `profile` (`id`, `username`, `password`, `name`, `status`, `email`, `image_url`, `role_id`, `created_at`, `updated_at`) VALUES
(6, 'nguoidung1', '$2a$10$N6UP8kJTRQVNH/ivPbJWf.s2z5Pmst/KGfme0jh3E6Z0R/09IUqI.', 'thanh', 2, NULL, NULL, 2, '2022-07-05 14:08:36', '2022-07-05 14:08:36'),
(7, 'admin', '$2a$10$zKbX4hOQQgcsExe6.MCPR.AZGm8.eYjIfYyPdxQ9fdDhMts4GJjGe', 'admin', 2, NULL, NULL, 1, '2022-07-05 14:56:21', '2022-07-05 14:56:21'),
(8, 'nguoidung3', '$2a$10$2s/6CA8LQnptxgaLslGQrupfBbf8LdKGQXaC9kS0Cxe4pjj8Qu7GK', 'nguoidung', 2, NULL, NULL, 2, '2022-07-06 12:08:27', '2022-07-06 12:08:27'),
(9, 'nguoidung4', '$2a$10$5cebCC/r0VADhNvcT7MluOgXmzYTAOCyLxmPjr7owOh.reR6JA1Mu', 'nguoidung', 2, NULL, NULL, 2, '2022-07-06 13:52:04', '2022-07-06 13:52:04'),
(10, 'nguoidung5', '$2a$10$DU4mDl6z.rh/tGUZWHvQQe5uMaQwM5WcdJzY5oaRLsllaVXPlq2hm', 'nguoidung', 2, NULL, NULL, 2, '2022-07-06 13:54:14', '2022-07-06 13:54:14'),
(11, 'adminadmin', '$2a$10$n7rgvfnOhz8MesOsHpHwJ.Djuz4/ohBXg1pV7RonC683erIQHsu8m', 'admin', 2, NULL, NULL, 1, '2022-07-06 22:38:52', '2022-07-06 22:38:52');

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

CREATE TABLE `report` (
  `id` int NOT NULL,
  `user_reporter_id` int NOT NULL,
  `user_reported_id` int NOT NULL,
  `auction_id` int DEFAULT NULL,
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` int NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `report_image`
--

CREATE TABLE `report_image` (
  `id` int NOT NULL,
  `report_id` int NOT NULL,
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `report_result`
--

CREATE TABLE `report_result` (
  `id` int NOT NULL,
  `admin_id` int NOT NULL,
  `auction_id` int NOT NULL,
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
(10);

-- --------------------------------------------------------

--
-- Table structure for table `winner`
--

CREATE TABLE `winner` (
  `id` int NOT NULL,
  `bid_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
  ADD KEY `nguoidung_id` (`user_id`),
  ADD KEY `sanpham_id` (`item_id`),
  ADD KEY `auction_ibfk_3` (`winner_id`);

--
-- Indexes for table `auction_category`
--
ALTER TABLE `auction_category`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `baidaugia_id` (`auction_id`,`category_id`),
  ADD KEY `danhmuctheloai_id` (`category_id`);

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
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `item_image`
--
ALTER TABLE `item_image`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sanpham_id` (`item_id`);

--
-- Indexes for table `profile`
--
ALTER TABLE `profile`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `tai_khoan` (`username`),
  ADD KEY `vaitro_id` (`role_id`);

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
  ADD UNIQUE KEY `admin_id` (`admin_id`,`auction_id`),
  ADD KEY `phieubaocao_id` (`auction_id`);

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
-- Indexes for table `winner`
--
ALTER TABLE `winner`
  ADD PRIMARY KEY (`id`),
  ADD KEY `lenhdaugia_id` (`bid_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `auction`
--
ALTER TABLE `auction`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `auction_category`
--
ALTER TABLE `auction_category`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `auction_user`
--
ALTER TABLE `auction_user`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `bid`
--
ALTER TABLE `bid`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `item`
--
ALTER TABLE `item`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `item_image`
--
ALTER TABLE `item_image`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `profile`
--
ALTER TABLE `profile`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `report`
--
ALTER TABLE `report`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `report_image`
--
ALTER TABLE `report_image`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `report_result`
--
ALTER TABLE `report_result`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `review_result`
--
ALTER TABLE `review_result`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `winner`
--
ALTER TABLE `winner`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

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
  ADD CONSTRAINT `auction_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`profile_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  ADD CONSTRAINT `auction_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `auction_ibfk_3` FOREIGN KEY (`winner_id`) REFERENCES `winner` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT;

--
-- Constraints for table `auction_category`
--
ALTER TABLE `auction_category`
  ADD CONSTRAINT `auction_category_ibfk_1` FOREIGN KEY (`auction_id`) REFERENCES `auction` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  ADD CONSTRAINT `auction_category_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;

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
-- Constraints for table `item_image`
--
ALTER TABLE `item_image`
  ADD CONSTRAINT `item_image_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;

--
-- Constraints for table `profile`
--
ALTER TABLE `profile`
  ADD CONSTRAINT `profile_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

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
  ADD CONSTRAINT `report_result_ibfk_2` FOREIGN KEY (`auction_id`) REFERENCES `report` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;

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
-- Constraints for table `winner`
--
ALTER TABLE `winner`
  ADD CONSTRAINT `winner_ibfk_1` FOREIGN KEY (`bid_id`) REFERENCES `bid` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
