-- phpMyAdmin SQL Dump
-- version 5.1.1deb5ubuntu1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jul 17, 2022 at 10:52 PM
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

INSERT INTO `auction` (`id`, `user_id`, `category_id`, `description`, `status`, `time_start`, `time_end`, `price_start`, `price_step`, `highest_price`, `created_at`, `updated_at`) VALUES
(22, 6, NULL, 'bai dau gia test', 4, '2022-07-09 09:50:50', '2022-07-09 12:39:50', 100000, 1000, 1000000, '2022-07-08 15:26:30', '2022-07-09 12:48:36'),
(23, 6, NULL, 'bai dau gia test 2 DRAFT', 4, '2022-07-09 09:50:50', '2022-07-09 12:39:50', 100000, 1000, 100000001, '2022-07-08 15:39:55', '2022-07-09 12:50:23'),
(24, 6, 1, 'bai dau gia test 3 DRAFT', 4, '2022-07-13 09:50:50', '2022-07-16 09:39:50', 100000, 1000, 0, '2022-07-08 16:02:17', '2022-07-08 18:28:02'),
(26, 8, 1, 'bai dau gia test 3 DRAFT', 4, '2022-07-09 18:22:00', '2022-07-13 18:23:00', 100000, 1000, 101000, '2022-07-09 09:19:39', '2022-07-09 18:22:41'),
(28, 8, 1, 'bai dau gia moi', 4, '2022-07-09 13:54:50', '2022-07-17 00:19:00', 100000, 1000, 1000000, '2022-07-09 13:51:45', '2022-07-09 13:57:27'),
(32, 6, 1, 'bai dau gia moi', 4, '2022-07-12 09:53:00', '2022-07-16 15:58:50', 100000, 1000, 330000, '2022-07-09 14:08:04', '2022-07-15 09:51:24'),
(33, 6, 2, 'bai dau gia category1', 4, '2022-07-10 22:55:00', '2022-07-17 00:18:50', 100000, 1000, 0, '2022-07-10 22:47:43', '2022-07-10 22:50:04'),
(34, 8, 1, 'bai dau gia category1', 3, '2022-07-12 19:50:00', '2022-07-20 19:53:00', 100000, 1000, 1000000, '2022-07-12 18:12:32', '2022-07-17 16:53:40'),
(37, 9, 1, 'bai dau gia category1', 4, '2022-07-15 22:32:00', '2022-07-16 22:31:00', 100000, 100000, 4000000, '2022-07-15 22:29:55', '2022-07-16 19:53:57'),
(39, 8, 2, 'bai dau gia test', 0, '2022-07-17 10:53:00', '2022-07-18 10:53:01', 100000, 100000, 0, '2022-07-17 10:39:06', '2022-07-17 10:39:06'),
(42, 8, 2, 'bai dau gia test', 1, '2022-07-18 10:53:00', '2022-07-19 10:53:01', 100000, 100000, 0, '2022-07-17 16:30:28', '2022-07-17 22:38:21');

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

INSERT INTO `auction_user` (`id`, `user_id`, `auction_id`) VALUES
(1, 6, 26),
(6, 6, 34),
(5, 6, 37),
(2, 8, 32),
(4, 8, 37);

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

INSERT INTO `bid` (`id`, `user_id`, `auction_id`, `price`, `created_at`, `updated_at`) VALUES
(9, 9, 22, 100000, '2022-07-08 15:41:55', '2022-07-08 15:41:55'),
(10, 9, 22, 101000, '2022-07-08 15:43:38', '2022-07-08 15:43:38'),
(11, 8, 22, 200000, '2022-07-08 17:46:05', '2022-07-08 17:46:05'),
(12, 8, 22, 300000, '2022-07-08 17:56:14', '2022-07-08 17:56:14'),
(13, 8, 22, 500000, '2022-07-08 18:06:07', '2022-07-08 18:06:07'),
(14, 8, 22, 1000000, '2022-07-09 12:48:36', '2022-07-09 12:48:36'),
(15, 8, 23, 10000000, '2022-07-09 12:48:43', '2022-07-09 12:48:43'),
(16, 8, 23, 100000001, '2022-07-09 12:50:23', '2022-07-09 12:50:23'),
(17, 9, 28, 100000, '2022-07-09 13:55:49', '2022-07-09 13:55:49'),
(18, 9, 28, 101000, '2022-07-09 13:56:11', '2022-07-09 13:56:11'),
(19, 6, 28, 1000000, '2022-07-09 13:57:27', '2022-07-09 13:57:27'),
(20, 6, 26, 100000, '2022-07-09 18:22:06', '2022-07-09 18:22:06'),
(21, 6, 26, 101000, '2022-07-09 18:22:41', '2022-07-09 18:22:41'),
(22, 8, 32, 200000, '2022-07-12 12:57:32', '2022-07-12 12:57:32'),
(23, 8, 32, 220000, '2022-07-12 13:42:03', '2022-07-12 13:42:03'),
(24, 8, 32, 230000, '2022-07-12 13:55:06', '2022-07-12 13:55:06'),
(25, 8, 32, 240000, '2022-07-12 13:58:27', '2022-07-12 13:58:27'),
(26, 8, 32, 241111, '2022-07-12 13:58:42', '2022-07-12 13:58:42'),
(27, 8, 32, 242222, '2022-07-12 13:59:22', '2022-07-12 13:59:22'),
(28, 8, 32, 243333, '2022-07-12 14:28:58', '2022-07-12 14:28:58'),
(29, 8, 32, 244444, '2022-07-12 15:08:13', '2022-07-12 15:08:13'),
(30, 8, 32, 245555, '2022-07-12 15:08:34', '2022-07-12 15:08:34'),
(31, 8, 32, 246666, '2022-07-12 15:30:23', '2022-07-12 15:30:23'),
(32, 8, 32, 247777, '2022-07-12 15:30:54', '2022-07-12 15:30:54'),
(33, 8, 32, 248888, '2022-07-12 18:05:58', '2022-07-12 18:05:58'),
(34, 8, 32, 249999, '2022-07-12 18:06:17', '2022-07-12 18:06:17'),
(35, 8, 32, 310000, '2022-07-12 18:06:47', '2022-07-12 18:06:47'),
(36, 8, 32, 320000, '2022-07-12 18:06:57', '2022-07-12 18:06:57'),
(37, 8, 32, 330000, '2022-07-15 09:51:24', '2022-07-15 09:51:24'),
(44, 8, 37, 100000, '2022-07-16 12:26:11', '2022-07-16 12:26:11'),
(45, 8, 37, 200000, '2022-07-16 12:33:20', '2022-07-16 12:33:20'),
(46, 8, 37, 300000, '2022-07-16 12:35:53', '2022-07-16 12:35:53'),
(47, 8, 37, 400000, '2022-07-16 12:50:40', '2022-07-16 12:50:40'),
(48, 8, 37, 500000, '2022-07-16 12:57:45', '2022-07-16 12:57:45'),
(49, 8, 37, 600000, '2022-07-16 13:49:06', '2022-07-16 13:49:06'),
(50, 8, 37, 700000, '2022-07-16 13:52:22', '2022-07-16 13:52:22'),
(51, 8, 37, 800000, '2022-07-16 13:57:34', '2022-07-16 13:57:34'),
(52, 8, 37, 900000, '2022-07-16 16:28:23', '2022-07-16 16:28:23'),
(53, 8, 37, 1000000, '2022-07-16 17:05:14', '2022-07-16 17:05:14'),
(54, 8, 37, 1100000, '2022-07-16 19:10:57', '2022-07-16 19:10:57'),
(55, 8, 37, 1200000, '2022-07-16 19:12:41', '2022-07-16 19:12:41'),
(56, 8, 37, 1300000, '2022-07-16 19:15:54', '2022-07-16 19:15:54'),
(57, 8, 37, 1400000, '2022-07-16 19:17:10', '2022-07-16 19:17:10'),
(58, 8, 37, 1500000, '2022-07-16 19:17:57', '2022-07-16 19:17:57'),
(59, 8, 37, 1600000, '2022-07-16 19:20:21', '2022-07-16 19:20:21'),
(60, 8, 37, 1700000, '2022-07-16 19:21:25', '2022-07-16 19:21:25'),
(61, 8, 37, 1800000, '2022-07-16 19:21:33', '2022-07-16 19:21:33'),
(62, 8, 37, 1900000, '2022-07-16 19:22:03', '2022-07-16 19:22:03'),
(63, 8, 37, 2000000, '2022-07-16 19:22:09', '2022-07-16 19:22:09'),
(64, 6, 37, 2100000, '2022-07-16 19:22:36', '2022-07-16 19:22:36'),
(65, 6, 37, 2200000, '2022-07-16 19:22:48', '2022-07-16 19:22:48'),
(66, 8, 37, 2300000, '2022-07-16 19:23:59', '2022-07-16 19:23:59'),
(67, 8, 37, 2400000, '2022-07-16 19:24:28', '2022-07-16 19:24:28'),
(68, 6, 37, 2500000, '2022-07-16 19:24:46', '2022-07-16 19:24:46'),
(69, 6, 37, 2600000, '2022-07-16 19:25:34', '2022-07-16 19:25:34'),
(70, 8, 37, 3000000, '2022-07-16 19:32:21', '2022-07-16 19:32:21'),
(71, 8, 37, 3100000, '2022-07-16 19:32:33', '2022-07-16 19:32:33'),
(72, 8, 37, 3200000, '2022-07-16 19:35:42', '2022-07-16 19:35:42'),
(73, 8, 37, 4000000, '2022-07-16 19:53:57', '2022-07-16 19:53:57'),
(79, 6, 34, 100000, '2022-07-17 13:23:08', '2022-07-17 13:23:08'),
(80, 6, 34, 110000, '2022-07-17 13:46:21', '2022-07-17 13:46:21'),
(81, 6, 34, 111999, '2022-07-17 13:46:34', '2022-07-17 13:46:34'),
(82, 6, 34, 120000, '2022-07-17 13:54:39', '2022-07-17 13:54:39'),
(83, 6, 34, 121000, '2022-07-17 13:54:49', '2022-07-17 13:54:49'),
(84, 6, 34, 122000, '2022-07-17 15:57:02', '2022-07-17 15:57:02'),
(85, 6, 34, 123000, '2022-07-17 15:57:09', '2022-07-17 15:57:09'),
(86, 6, 34, 124000, '2022-07-17 16:53:19', '2022-07-17 16:53:19'),
(87, 6, 34, 200000, '2022-07-17 16:53:25', '2022-07-17 16:53:25'),
(88, 6, 34, 1000000, '2022-07-17 16:53:40', '2022-07-17 16:53:40');

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

INSERT INTO `item` (`id`, `name`, `description`, `amount`, `auction_id`, `created_at`, `updated_at`) VALUES
(13, 'ban phim', 'leopold', 1, 22, '2022-07-08 15:31:30', '2022-07-08 15:31:30'),
(15, 'ban phim leopold', 'leopold', 1, 24, '2022-07-08 17:35:02', '2022-07-08 17:35:02'),
(18, 'ban phim filco', 'filco', 1, 23, '2022-07-08 17:43:40', '2022-07-08 17:43:40'),
(20, 'ban phim filco', 'filco filco leopold', 1, 28, '2022-07-09 13:52:07', '2022-07-09 13:52:07'),
(21, 'ban phim filco leopold', 'filco filco leopold', 1, 33, '2022-07-10 22:48:16', '2022-07-10 22:48:16'),
(22, 'ban phim filco leopold', 'filco filco leopold', 1, 32, '2022-07-12 09:49:32', '2022-07-12 09:49:32'),
(23, 'ban phim filco leopold', 'filco filco leopold', 1, 34, '2022-07-12 18:21:26', '2022-07-12 18:21:26'),
(27, 'ban phim filco leopold', 'filco filco leopold', 1, 37, '2022-07-15 22:32:20', '2022-07-15 22:32:20'),
(30, 'ban phim filco leopold', 'filco filco leopold', 1, 39, '2022-07-17 10:41:10', '2022-07-17 10:41:10'),
(32, 'ban phim filco leopold', 'filco filco leopold', 0, 42, '2022-07-17 16:30:28', '2022-07-17 16:30:28');

-- --------------------------------------------------------

--
-- Table structure for table `item_image`
--

CREATE TABLE `item_image` (
  `id` int NOT NULL,
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `item_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `item_image`
--

INSERT INTO `item_image` (`id`, `image_url`, `item_id`) VALUES
(1, 'imageurl', 13),
(2, 'imageurll', 13),
(3, 'imageurll', 13),
(4, 'imageurll', 15),
(5, '1231', 15),
(6, '1231', 21),
(7, 'https://scontent.fhan5-5.fna.fbcdn.net/v/t39.30808-6/293873371_10209508117096478_8798337006566660327_n.jpg?_nc_cat=107&ccb=1-7&_nc_sid=5cd70e&_nc_ohc=lTcN4a505D8AX-_ZsqJ&_nc_ht=scontent.fhan5-5.fna&oh=00_AT-K1pzuaGB9L9aDyuMPZNPqAfawnVy6YppwCleGcG4hZg&oe=62D69A88', 22),
(8, 'https://scontent.fhan5-11.fna.fbcdn.net/v/t39.30808-6/293800619_10209508121016576_4918951137685775306_n.jpg?_nc_cat=111&ccb=1-7&_nc_sid=5cd70e&_nc_ohc=faWOE84UvYgAX8B_kSj&_nc_ht=scontent.fhan5-11.fna&oh=00_AT_hCql-oqfoF2NmOReDm-fURLqnwdTYV9DkHyD0Jj3Idg&oe=62D6219E', 22),
(9, 'https://scontent.fhan15-1.fna.fbcdn.net/v/t39.30808-6/293313076_1480051099131980_4706168332655760610_n.jpg?stp=dst-jpg_p720x720&_nc_cat=106&ccb=1-7&_nc_sid=5cd70e&_nc_ohc=e9BjlXX_trUAX9TFRr-&_nc_ht=scontent.fhan15-1.fna&oh=00_AT-itj6mSntl2cHRo4x1mgbgMR5YOHzS31y6PaLxQNG4oQ&oe=62D95B2B', 30),
(10, 'https://scontent.fhan15-1.fna.fbcdn.net/v/t39.30808-6/293313076_1480051099131980_4706168332655760610_n.jpg?stp=dst-jpg_p720x720&_nc_cat=106&ccb=1-7&_nc_sid=5cd70e&_nc_ohc=e9BjlXX_trUAX9TFRr-&_nc_ht=scontent.fhan15-1.fna&oh=00_AT-itj6mSntl2cHRo4x1mgbgMR5YOHzS31y6PaLxQNG4oQ&oe=62D95B2B', 30),
(13, 'https://scontent.fhan15-1.fna.fbcdn.net/v/t39.30808-6/293313076_1480051099131980_4706168332655760610_n.jpg?stp=dst-jpg_p720x720&_nc_cat=106&ccb=1-7&_nc_sid=5cd70e&_nc_ohc=e9BjlXX_trUAX9TFRr-&_nc_ht=scontent.fhan15-1.fna&oh=00_AT-itj6mSntl2cHRo4x1mgbgMR5YOHzS31y6PaLxQNG4oQ&oe=62D95B2B', 32),
(14, 'https://scontent.fhan15-1.fna.fbcdn.net/v/t39.30808-6/293313076_1480051099131980_4706168332655760610_n.jpg?stp=dst-jpg_p720x720&_nc_cat=106&ccb=1-7&_nc_sid=5cd70e&_nc_ohc=e9BjlXX_trUAX9TFRr-&_nc_ht=scontent.fhan15-1.fna&oh=00_AT-itj6mSntl2cHRo4x1mgbgMR5YOHzS31y6PaLxQNG4oQ&oe=62D95B2B', 32);

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
(6, 'nguoidung1', '$2a$10$KdgBbfHLTabjblJqhTwkne0AayhMR7V2jgJLYtsmnR.V5j6PASLUW', 'thanhthanhthanh', 2, 'thanhthanhthanh@gmail.com', NULL, 2, '2022-07-05 14:08:36', '2022-07-09 14:21:16'),
(8, 'nguoidung3', '$2a$10$2s/6CA8LQnptxgaLslGQrupfBbf8LdKGQXaC9kS0Cxe4pjj8Qu7GK', 'nguoidung', 2, NULL, NULL, 2, '2022-07-06 12:08:27', '2022-07-06 12:08:27'),
(9, 'nguoidung4', '$2a$10$5cebCC/r0VADhNvcT7MluOgXmzYTAOCyLxmPjr7owOh.reR6JA1Mu', 'nguoidung', 2, NULL, NULL, 2, '2022-07-06 13:52:04', '2022-07-06 13:52:04'),
(10, 'nguoidung5', '$2a$10$DU4mDl6z.rh/tGUZWHvQQe5uMaQwM5WcdJzY5oaRLsllaVXPlq2hm', 'nguoidung', 2, NULL, NULL, 2, '2022-07-06 13:54:14', '2022-07-06 13:54:14'),
(11, 'adminadmin', '$2a$10$n7rgvfnOhz8MesOsHpHwJ.Djuz4/ohBXg1pV7RonC683erIQHsu8m', 'admin', 2, NULL, NULL, 1, '2022-07-06 22:38:52', '2022-07-06 22:38:52'),
(12, 'nguoidung', '$2a$10$xpqqLaxYLxRB79mMlLOBTu8XthDx6h2fhO.dWGq2MDF0kHCgXV6L2', 'thanhthanh', 2, NULL, NULL, 2, '2022-07-08 23:19:15', '2022-07-08 23:19:15');

-- --------------------------------------------------------

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

INSERT INTO `report` (`id`, `user_reporter_id`, `user_reported_id`, `auction_id`, `description`, `created_at`, `updated_at`) VALUES
(1, 6, 8, NULL, 'thang nay sui bid', '2022-07-09 23:52:09', '2022-07-09 23:52:09');

-- --------------------------------------------------------

--
-- Table structure for table `report_image`
--

CREATE TABLE `report_image` (
  `id` int NOT NULL,
  `report_id` int NOT NULL,
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `report_image`
--

INSERT INTO `report_image` (`id`, `report_id`, `image_url`) VALUES
(1, 1, 'adu.jpg'),
(2, 1, 'adu2.jpg'),
(3, 1, '1231');

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

--
-- Dumping data for table `report_result`
--

INSERT INTO `report_result` (`id`, `admin_id`, `report_id`, `result`, `created_at`, `updated_at`) VALUES
(1, 11, 1, 1, '2022-07-10 00:12:05', '2022-07-10 00:12:05');

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

INSERT INTO `review_result` (`id`, `admin_id`, `auction_id`, `result`, `created_at`, `updated_at`) VALUES
(1, 11, 33, 1, '2022-07-10 22:50:04', '2022-07-10 22:50:04'),
(2, 11, 32, 1, '2022-07-12 09:52:33', '2022-07-12 09:52:33');

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
(12);

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

INSERT INTO `winner` (`id`, `bid_id`, `auction_id`) VALUES
(9, 14, 22),
(10, 16, 23),
(670, 37, 32),
(671, 73, 37),
(672, 19, 28);

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
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT for table `auction_user`
--
ALTER TABLE `auction_user`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `bid`
--
ALTER TABLE `bid`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=89;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `item`
--
ALTER TABLE `item`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `item_image`
--
ALTER TABLE `item_image`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `profile`
--
ALTER TABLE `profile`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `report`
--
ALTER TABLE `report`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `report_image`
--
ALTER TABLE `report_image`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `report_result`
--
ALTER TABLE `report_result`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `review_result`
--
ALTER TABLE `review_result`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `winner`
--
ALTER TABLE `winner`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=673;

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
  ADD CONSTRAINT `auction_ibfk_4` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT;

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
-- Constraints for table `winner`
--
ALTER TABLE `winner`
  ADD CONSTRAINT `winner_ibfk_1` FOREIGN KEY (`bid_id`) REFERENCES `bid` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  ADD CONSTRAINT `winner_ibfk_2` FOREIGN KEY (`auction_id`) REFERENCES `auction` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;

DELIMITER $$
--
-- Events
--
CREATE DEFINER=`root`@`localhost` EVENT `cancelAuction` ON SCHEDULE EVERY 1 MINUTE STARTS '2022-07-09 11:37:01' ENDS '2022-09-09 11:37:01' ON COMPLETION NOT PRESERVE ENABLE DO UPDATE auction
				SET auction.status = -1
				WHERE now()> auction.time_start and auction.status=1$$

CREATE DEFINER=`root`@`localhost` EVENT `openAuction` ON SCHEDULE EVERY 1 MINUTE STARTS '2022-07-09 11:40:01' ENDS '2022-09-09 11:40:01' ON COMPLETION NOT PRESERVE ENABLE DO UPDATE auction
				SET auction.status = 3
				WHERE now()> auction.time_start and auction.status=2$$

CREATE DEFINER=`root`@`localhost` EVENT `endAuctionAndInsertWinner` ON SCHEDULE EVERY 1 MINUTE STARTS '2022-07-09 13:43:01' ENDS '2022-09-09 13:43:01' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN
            INSERT INTO `winner`(`bid_id`, `auction_id`) select A.bid_id, A.auction_id from (
					select b.id as bid_id, a.id as auction_id  from bid b join auction a on b.auction_id = a.id 
					where now() > a.time_end and a.status=3 and (auction_id, price) IN (
						select auction_id, max(price) from bid
						group by bid.auction_id 
					)) A;
			UPDATE auction 
				SET auction.status = 4
				WHERE NOW() > auction.time_end AND auction.status = 3;
END$$

DELIMITER ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
