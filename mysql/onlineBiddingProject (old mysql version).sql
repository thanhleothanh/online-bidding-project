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

create TABLE `admin` (
  `profile_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `admin`
--

insert into `admin` (`profile_id`) VALUES
(11);

-- --------------------------------------------------------

--
-- Table structure for table `auction`
--

create TABLE `auction` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `category_id` int DEFAULT NULL,
  `description` varchar(1000) CHARACTER SET utf8mb4  DEFAULT NULL,
  `status` int NOT NULL,
  `time_start` datetime NOT NULL,
  `time_end` datetime NOT NULL,
  `price_start` double DEFAULT '0',
  `price_step` double DEFAULT '0',
  `highest_price` double DEFAULT '0',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `auction`
--

insert into `auction` (`id`, `user_id`, `category_id`, `description`, `status`, `time_start`, `time_end`, `price_start`, `price_step`, `highest_price`, `created_at`, `updated_at`) VALUES
(92, 6, 1, 'nue keyboard auction', 3, '2022-08-01 14:40:00', '2022-08-24 14:36:00', 100000, 50000, 300000, '2022-08-01 14:37:22', '2022-08-12 12:31:41'),
(93, 8, 1, 'TGR Police auction', 3, '2022-08-01 14:43:00', '2022-08-23 14:40:00', 100000, 500000, 0, '2022-08-01 14:41:25', '2022-08-01 14:41:51'),
(94, 9, 4, 'Kikuichimonji auction', 3, '2022-08-01 14:48:00', '2022-08-20 14:45:00', 1000000, 500000, 0, '2022-08-01 14:46:28', '2022-08-01 14:47:06'),
(95, 6, 8, 'Noxary vulcan pro auction', 3, '2022-08-01 14:56:00', '2022-08-28 14:53:00', 1000000, 500000, 1500000, '2022-08-01 14:54:04', '2022-08-01 15:07:49'),
(96, 13, 9, 'Keycult No. 2 auction', 3, '2022-08-01 15:01:00', '2022-08-26 14:58:00', 1000000, 1000000, 2000000, '2022-08-01 14:59:59', '2022-08-10 21:50:40'),
(97, 6, 4, 'gmk keycaps auction', 4, '2022-08-12 11:59:00', '2022-08-12 12:01:00', 100000, 50000, 100000, '2022-08-12 11:58:04', '2022-08-12 11:59:07');

-- --------------------------------------------------------

--
-- Table structure for table `auction_user`
--

create TABLE `auction_user` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `auction_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `auction_user`
--

insert into `auction_user` (`id`, `user_id`, `auction_id`) VALUES
(29, 6, 96),
(33, 8, 92),
(32, 8, 97),
(31, 10, 96),
(30, 13, 95);

-- --------------------------------------------------------

--
-- Table structure for table `bid`
--

create TABLE `bid` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `auction_id` int NOT NULL,
  `price` double NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `bid`
--

insert into `bid` (`id`, `user_id`, `auction_id`, `price`, `created_at`, `updated_at`) VALUES
(130, 6, 96, 1000000, '2022-08-01 15:01:26', '2022-08-01 15:01:26'),
(131, 13, 95, 1000000, '2022-08-01 15:02:11', '2022-08-01 15:02:11'),
(132, 13, 95, 1500000, '2022-08-01 15:07:49', '2022-08-01 15:07:49'),
(133, 10, 96, 2000000, '2022-08-10 21:50:40', '2022-08-10 21:50:40'),
(134, 8, 97, 100000, '2022-08-12 11:59:07', '2022-08-12 11:59:07'),
(135, 8, 92, 200000, '2022-08-12 12:08:27', '2022-08-12 12:08:27'),
(136, 8, 92, 300000, '2022-08-12 12:31:41', '2022-08-12 12:31:41');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

create TABLE `category` (
  `id` int NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4  NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `category`
--

insert into `category` (`id`, `name`) VALUES
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

create TABLE `item` (
  `id` int NOT NULL,
  `name` varchar(200) CHARACTER SET utf8mb4  NOT NULL,
  `description` varchar(1000) CHARACTER SET utf8mb4  DEFAULT NULL,
  `amount` int NOT NULL,
  `auction_id` int NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `item`
--

insert into `item` (`id`, `name`, `description`, `amount`, `auction_id`, `created_at`, `updated_at`) VALUES
(82, 'nue keyboard by kbye of Studio Kestra, custom commission for harsha.', 'Mint green cerakoted aluminum case with brass accent.  Lubed (205g0) TX clip-in stabilizers.  Lubed (205g0) H1 switches, with 68g springs and Deskey films, mounted on PCB.', 1, 92, '2022-08-01 14:37:22', '2022-08-01 14:37:22'),
(83, 'Polycarbonate Alice (Police)', 'Design by TGR  Polycarbonate case with copper weight.  Lubed (205g0) Durock v2 screw-in stabilizers.  Lubed (205g0) Cherry MX Black switches - with 65g springs and TX films - mounted on a copper full plate.', 1, 93, '2022-08-01 14:41:25', '2022-08-01 14:41:25'),
(84, 'Kikuichimonji keyboard', 'Design by bisoromi, run by AEBoards  Silver-anodized aluminum case.  Lubed (205g0) Durock v2 screw-in stabilizers.  Lubed Boba LT switches, with 62g springs - mounted on an aluminum full plate.', 1, 94, '2022-08-01 14:46:28', '2022-08-01 14:46:28'),
(85, 'Vulcan Pro Design by Noxary.', '\"Hyper Red\"-anodized aluminum case.  Clipped and lubed (205g0) Cherry clip-in stabilizers.  Lubed (205g0) Gateron Ink Red switches, with 62g springs and TX films - mounted on an aluminum full plate.  Vulcan PCB by ai03 (QMK/VIA).', 1, 95, '2022-08-01 14:54:04', '2022-08-01 14:54:04'),
(86, 'Keycult No. 2', 'Silver-anodized aluminum case with sandblasted stainless steel bottom. 5.5 degree angle.  Lubed (205g0) Durock v2 screw-in stabilizers.  Lubed (205g0) Cherry MX Black switches, with 63.5g springs - mounted on an aluminum full plate.', 1, 96, '2022-08-01 14:59:59', '2022-08-01 14:59:59'),
(87, 'gmk colors', 'gmk colors, unreleased, prolly a scam idk', 1, 97, '2022-08-12 11:58:04', '2022-08-12 11:58:04');

-- --------------------------------------------------------

--
-- Table structure for table `item_image`
--

create TABLE `item_image` (
  `id` int NOT NULL,
  `image_url` varchar(500) CHARACTER SET utf8mb4  NOT NULL,
  `public_id` varchar(1000) DEFAULT NULL,
  `item_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `item_image`
--

insert into `item_image` (`id`, `image_url`, `public_id`, `item_id`) VALUES
(41, 'https://res.cloudinary.com/metanoia/image/upload/v1659339437/onlinebiddingproject/s3jp7oqwmrtpijj7lifw.jpg', 'onlinebiddingproject/s3jp7oqwmrtpijj7lifw', 82),
(42, 'https://res.cloudinary.com/metanoia/image/upload/v1659339433/onlinebiddingproject/jlypg0tlb4gglm6xbuhx.jpg', 'onlinebiddingproject/jlypg0tlb4gglm6xbuhx', 82),
(43, 'https://res.cloudinary.com/metanoia/image/upload/v1659339428/onlinebiddingproject/og2lxi9pjuq6rwjejast.jpg', 'onlinebiddingproject/og2lxi9pjuq6rwjejast', 82),
(44, 'https://res.cloudinary.com/metanoia/image/upload/v1659339424/onlinebiddingproject/swrf8zzqfzygt6gltzt6.jpg', 'onlinebiddingproject/swrf8zzqfzygt6gltzt6', 82),
(45, 'https://res.cloudinary.com/metanoia/image/upload/v1659339681/onlinebiddingproject/xqxlfofmvthycr4w1wa0.jpg', 'onlinebiddingproject/xqxlfofmvthycr4w1wa0', 83),
(46, 'https://res.cloudinary.com/metanoia/image/upload/v1659339677/onlinebiddingproject/bxxvkawa8ttelcaxgnrq.jpg', 'onlinebiddingproject/bxxvkawa8ttelcaxgnrq', 83),
(47, 'https://res.cloudinary.com/metanoia/image/upload/v1659339674/onlinebiddingproject/cxoilyhq07sz6lcfijge.jpg', 'onlinebiddingproject/cxoilyhq07sz6lcfijge', 83),
(48, 'https://res.cloudinary.com/metanoia/image/upload/v1659339670/onlinebiddingproject/koo8008m99txsibgykvn.jpg', 'onlinebiddingproject/koo8008m99txsibgykvn', 83),
(49, 'https://res.cloudinary.com/metanoia/image/upload/v1659339982/onlinebiddingproject/ddwcfyuk8g2vkada298g.jpg', 'onlinebiddingproject/ddwcfyuk8g2vkada298g', 84),
(50, 'https://res.cloudinary.com/metanoia/image/upload/v1659339976/onlinebiddingproject/nht8tdvmqdgtlleulamm.jpg', 'onlinebiddingproject/nht8tdvmqdgtlleulamm', 84),
(51, 'https://res.cloudinary.com/metanoia/image/upload/v1659339973/onlinebiddingproject/m6qx4x9fhwalbstybjda.jpg', 'onlinebiddingproject/m6qx4x9fhwalbstybjda', 84),
(52, 'https://res.cloudinary.com/metanoia/image/upload/v1659340438/onlinebiddingproject/s1bha9z3wfkv7tpfb9yr.jpg', 'onlinebiddingproject/s1bha9z3wfkv7tpfb9yr', 85),
(53, 'https://res.cloudinary.com/metanoia/image/upload/v1659340434/onlinebiddingproject/otyeipgl7hvxaxagh4q3.jpg', 'onlinebiddingproject/otyeipgl7hvxaxagh4q3', 85),
(54, 'https://res.cloudinary.com/metanoia/image/upload/v1659340430/onlinebiddingproject/yhekvpygilp5g0zgsdvi.jpg', 'onlinebiddingproject/yhekvpygilp5g0zgsdvi', 85),
(55, 'https://res.cloudinary.com/metanoia/image/upload/v1659340427/onlinebiddingproject/vwcyphcsc4oivevrbz6m.jpg', 'onlinebiddingproject/vwcyphcsc4oivevrbz6m', 85),
(56, 'https://res.cloudinary.com/metanoia/image/upload/v1659340764/onlinebiddingproject/qgxkg9sc1eb7fmfsbfi7.jpg', 'onlinebiddingproject/qgxkg9sc1eb7fmfsbfi7', 86),
(57, 'https://res.cloudinary.com/metanoia/image/upload/v1659340760/onlinebiddingproject/g9dy8lebx81csmzldp2n.jpg', 'onlinebiddingproject/g9dy8lebx81csmzldp2n', 86),
(58, 'https://res.cloudinary.com/metanoia/image/upload/v1659340756/onlinebiddingproject/tsqs1klwtjzre5delyqu.jpg', 'onlinebiddingproject/tsqs1klwtjzre5delyqu', 86),
(59, 'https://res.cloudinary.com/metanoia/image/upload/v1659340753/onlinebiddingproject/zkv9vjopgvmoxz3bm8km.jpg', 'onlinebiddingproject/zkv9vjopgvmoxz3bm8km', 86),
(60, 'https://res.cloudinary.com/metanoia/image/upload/v1660280251/onlinebiddingproject/s8souhhaoohgcvwv57bh.jpg', 'onlinebiddingproject/s8souhhaoohgcvwv57bh', 87),
(61, 'https://res.cloudinary.com/metanoia/image/upload/v1660280244/onlinebiddingproject/ify9nicknmpyhlegasoe.jpg', 'onlinebiddingproject/ify9nicknmpyhlegasoe', 87),
(62, 'https://res.cloudinary.com/metanoia/image/upload/v1660280237/onlinebiddingproject/fx2imhpmcq8wfjoehw21.jpg', 'onlinebiddingproject/fx2imhpmcq8wfjoehw21', 87);

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

create TABLE `notification` (
  `id` int NOT NULL,
  `profile_id` int DEFAULT NULL,
  `notification_type` varchar(50) NOT NULL,
  `entity_type` varchar(25) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `notification`
--

insert into `notification` (`id`, `profile_id`, `notification_type`, `entity_type`, `created_at`, `updated_at`) VALUES
(43, 6, 'SUBMIT_AUCTION', 'AUCTION', '2022-08-01 14:38:02', '2022-08-01 14:38:02'),
(44, 11, 'REVIEW_AUCTION', 'AUCTION', '2022-08-01 14:38:14', '2022-08-01 14:38:14'),
(45, NULL, 'START_AUCTION', 'AUCTION', '2022-08-01 14:40:00', '2022-08-01 14:40:00'),
(46, 8, 'NEW_BID_AUCTION', 'AUCTION', '2022-08-01 14:40:00', '2022-08-12 12:31:41'),
(47, 8, 'SUBMIT_AUCTION', 'AUCTION', '2022-08-01 14:41:30', '2022-08-01 14:41:30'),
(48, 11, 'REVIEW_AUCTION', 'AUCTION', '2022-08-01 14:41:51', '2022-08-01 14:41:51'),
(49, NULL, 'START_AUCTION', 'AUCTION', '2022-08-01 14:43:00', '2022-08-01 14:43:00'),
(50, NULL, 'NEW_BID_AUCTION', 'AUCTION', '2022-08-01 14:43:00', '2022-08-01 14:43:00'),
(51, 9, 'SUBMIT_AUCTION', 'AUCTION', '2022-08-01 14:46:32', '2022-08-01 14:46:32'),
(52, 11, 'REVIEW_AUCTION', 'AUCTION', '2022-08-01 14:47:06', '2022-08-01 14:47:06'),
(53, NULL, 'START_AUCTION', 'AUCTION', '2022-08-01 14:48:00', '2022-08-01 14:48:00'),
(54, NULL, 'NEW_BID_AUCTION', 'AUCTION', '2022-08-01 14:48:00', '2022-08-01 14:48:00'),
(55, 6, 'SUBMIT_AUCTION', 'AUCTION', '2022-08-01 14:54:10', '2022-08-01 14:54:10'),
(56, 11, 'REVIEW_AUCTION', 'AUCTION', '2022-08-01 14:54:27', '2022-08-01 14:54:27'),
(57, NULL, 'START_AUCTION', 'AUCTION', '2022-08-01 14:56:00', '2022-08-01 14:56:00'),
(58, 13, 'NEW_BID_AUCTION', 'AUCTION', '2022-08-01 14:56:00', '2022-08-01 15:07:49'),
(59, 13, 'SUBMIT_AUCTION', 'AUCTION', '2022-08-01 15:00:13', '2022-08-01 15:00:13'),
(60, 11, 'REVIEW_AUCTION', 'AUCTION', '2022-08-01 15:00:27', '2022-08-01 15:00:27'),
(61, NULL, 'START_AUCTION', 'AUCTION', '2022-08-01 15:01:00', '2022-08-01 15:01:00'),
(62, 10, 'NEW_BID_AUCTION', 'AUCTION', '2022-08-01 15:01:00', '2022-08-10 21:50:40'),
(63, 6, 'SUBMIT_AUCTION', 'AUCTION', '2022-08-12 11:58:08', '2022-08-12 11:58:08'),
(64, 11, 'REVIEW_AUCTION', 'AUCTION', '2022-08-12 11:58:32', '2022-08-12 11:58:32'),
(65, NULL, 'START_AUCTION', 'AUCTION', '2022-08-12 11:59:00', '2022-08-12 11:59:00'),
(66, 8, 'NEW_BID_AUCTION', 'AUCTION', '2022-08-12 11:59:00', '2022-08-12 11:59:07'),
(67, NULL, 'END_AUCTION', 'AUCTION', '2022-08-12 12:01:00', '2022-08-12 12:01:00'),
(69, 6, 'CREATE_REPORT', 'REPORT', '2022-08-12 12:12:15', '2022-08-12 12:12:15');

-- --------------------------------------------------------

--
-- Table structure for table `notification_auction`
--

create TABLE `notification_auction` (
  `id` int NOT NULL,
  `auction_id` int NOT NULL,
  `notification_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `notification_auction`
--

insert into `notification_auction` (`id`, `auction_id`, `notification_id`) VALUES
(37, 92, 43),
(38, 92, 44),
(39, 92, 45),
(40, 92, 46),
(41, 93, 47),
(42, 93, 48),
(43, 93, 49),
(44, 93, 50),
(45, 94, 51),
(46, 94, 52),
(47, 94, 53),
(48, 94, 54),
(49, 95, 55),
(50, 95, 56),
(51, 95, 57),
(52, 95, 58),
(53, 96, 59),
(54, 96, 60),
(55, 96, 61),
(56, 96, 62),
(57, 97, 63),
(58, 97, 64),
(59, 97, 65),
(60, 97, 66),
(61, 97, 67);

-- --------------------------------------------------------

--
-- Table structure for table `notification_notified`
--

create TABLE `notification_notified` (
  `id` int NOT NULL,
  `notification_id` int NOT NULL,
  `profile_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `notification_notified`
--

insert into `notification_notified` (`id`, `notification_id`, `profile_id`) VALUES
(48, 43, 11),
(49, 44, 6),
(50, 45, 6),
(51, 46, 6),
(52, 47, 11),
(53, 48, 8),
(54, 49, 8),
(55, 50, 8),
(56, 51, 11),
(57, 52, 9),
(58, 53, 9),
(59, 54, 9),
(60, 55, 11),
(61, 56, 6),
(62, 57, 6),
(63, 58, 6),
(64, 59, 11),
(65, 60, 13),
(66, 61, 13),
(67, 62, 13),
(68, 62, 6),
(69, 58, 13),
(70, 62, 10),
(71, 63, 11),
(72, 64, 6),
(73, 65, 6),
(74, 66, 6),
(75, 66, 8),
(76, 67, 6),
(77, 67, 8),
(79, 46, 8),
(80, 69, 11);

-- --------------------------------------------------------

--
-- Table structure for table `notification_report`
--

create TABLE `notification_report` (
  `id` int NOT NULL,
  `report_id` int NOT NULL,
  `notification_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `notification_report`
--

insert into `notification_report` (`id`, `report_id`, `notification_id`) VALUES
(5, 24, 69);

-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

create TABLE `profile` (
  `id` int NOT NULL,
  `username` varchar(30) CHARACTER SET utf8mb4  NOT NULL,
  `password` varchar(200) CHARACTER SET utf8mb4  NOT NULL,
  `bio` varchar(10000) DEFAULT NULL,
  `legitimate_score` int NOT NULL DEFAULT '0',
  `name` varchar(100) CHARACTER SET utf8mb4  NOT NULL,
  `status` int NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `image_url` varchar(500) CHARACTER SET utf8mb4  DEFAULT NULL,
  `role_id` int NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `profile`
--

insert into `profile` (`id`, `username`, `password`, `bio`, `legitimate_score`, `name`, `status`, `email`, `image_url`, `role_id`, `created_at`, `updated_at`) VALUES
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

create TABLE `QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `BLOB_DATA` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

-- --------------------------------------------------------

--
-- Table structure for table `QRTZ_CALENDARS`
--

create TABLE `QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(190) NOT NULL,
  `CALENDAR` blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

-- --------------------------------------------------------

--
-- Table structure for table `QRTZ_CRON_TRIGGERS`
--

create TABLE `QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

-- --------------------------------------------------------

--
-- Table structure for table `QRTZ_FIRED_TRIGGERS`
--

create TABLE `QRTZ_FIRED_TRIGGERS` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

-- --------------------------------------------------------

--
-- Table structure for table `QRTZ_JOB_DETAILS`
--

create TABLE `QRTZ_JOB_DETAILS` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `QRTZ_JOB_DETAILS`
--

insert into `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`, `DESCRIPTION`, `JOB_CLASS_NAME`, `IS_DURABLE`, `IS_NONCONCURRENT`, `IS_UPDATE_DATA`, `REQUESTS_RECOVERY`, `JOB_DATA`) VALUES
('quartzScheduler', 'END92', 'END AUCTIONS', 'SCHEDULING ENDING AUCTION #92', 'com.ghtk.onlinebiddingproject.jobs.EndAuctionJob', '0', '0', '0', '0', 0xaced0005737200156f72672e71756172747a2e4a6f62446174614d61709fb083e8bfa9b0cb020000787200266f72672e71756172747a2e7574696c732e537472696e674b65794469727479466c61674d61708208e8c3fbc55d280200015a0013616c6c6f77735472616e7369656e74446174617872001d6f72672e71756172747a2e7574696c732e4469727479466c61674d617013e62ead28760ace0200025a000564697274794c00036d617074000f4c6a6176612f7574696c2f4d61703b787001737200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f4000000000000c7708000000100000000174000961756374696f6e4964737200116a6176612e6c616e672e496e746567657212e2a0a4f781873802000149000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b02000078700000005c7800),
('quartzScheduler', 'END93', 'END AUCTIONS', 'SCHEDULING ENDING AUCTION #93', 'com.ghtk.onlinebiddingproject.jobs.EndAuctionJob', '0', '0', '0', '0', 0xaced0005737200156f72672e71756172747a2e4a6f62446174614d61709fb083e8bfa9b0cb020000787200266f72672e71756172747a2e7574696c732e537472696e674b65794469727479466c61674d61708208e8c3fbc55d280200015a0013616c6c6f77735472616e7369656e74446174617872001d6f72672e71756172747a2e7574696c732e4469727479466c61674d617013e62ead28760ace0200025a000564697274794c00036d617074000f4c6a6176612f7574696c2f4d61703b787001737200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f4000000000000c7708000000100000000174000961756374696f6e4964737200116a6176612e6c616e672e496e746567657212e2a0a4f781873802000149000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b02000078700000005d7800),
('quartzScheduler', 'END94', 'END AUCTIONS', 'SCHEDULING ENDING AUCTION #94', 'com.ghtk.onlinebiddingproject.jobs.EndAuctionJob', '0', '0', '0', '0', 0xaced0005737200156f72672e71756172747a2e4a6f62446174614d61709fb083e8bfa9b0cb020000787200266f72672e71756172747a2e7574696c732e537472696e674b65794469727479466c61674d61708208e8c3fbc55d280200015a0013616c6c6f77735472616e7369656e74446174617872001d6f72672e71756172747a2e7574696c732e4469727479466c61674d617013e62ead28760ace0200025a000564697274794c00036d617074000f4c6a6176612f7574696c2f4d61703b787001737200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f4000000000000c7708000000100000000174000961756374696f6e4964737200116a6176612e6c616e672e496e746567657212e2a0a4f781873802000149000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b02000078700000005e7800),
('quartzScheduler', 'END95', 'END AUCTIONS', 'SCHEDULING ENDING AUCTION #95', 'com.ghtk.onlinebiddingproject.jobs.EndAuctionJob', '0', '0', '0', '0', 0xaced0005737200156f72672e71756172747a2e4a6f62446174614d61709fb083e8bfa9b0cb020000787200266f72672e71756172747a2e7574696c732e537472696e674b65794469727479466c61674d61708208e8c3fbc55d280200015a0013616c6c6f77735472616e7369656e74446174617872001d6f72672e71756172747a2e7574696c732e4469727479466c61674d617013e62ead28760ace0200025a000564697274794c00036d617074000f4c6a6176612f7574696c2f4d61703b787001737200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f4000000000000c7708000000100000000174000961756374696f6e4964737200116a6176612e6c616e672e496e746567657212e2a0a4f781873802000149000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b02000078700000005f7800),
('quartzScheduler', 'END96', 'END AUCTIONS', 'SCHEDULING ENDING AUCTION #96', 'com.ghtk.onlinebiddingproject.jobs.EndAuctionJob', '0', '0', '0', '0', 0xaced0005737200156f72672e71756172747a2e4a6f62446174614d61709fb083e8bfa9b0cb020000787200266f72672e71756172747a2e7574696c732e537472696e674b65794469727479466c61674d61708208e8c3fbc55d280200015a0013616c6c6f77735472616e7369656e74446174617872001d6f72672e71756172747a2e7574696c732e4469727479466c61674d617013e62ead28760ace0200025a000564697274794c00036d617074000f4c6a6176612f7574696c2f4d61703b787001737200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f4000000000000c7708000000100000000174000961756374696f6e4964737200116a6176612e6c616e672e496e746567657212e2a0a4f781873802000149000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b0200007870000000607800);

-- --------------------------------------------------------

--
-- Table structure for table `QRTZ_LOCKS`
--

create TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `QRTZ_LOCKS`
--

insert into `QRTZ_LOCKS` (`SCHED_NAME`, `LOCK_NAME`) VALUES
('quartzScheduler', 'TRIGGER_ACCESS');

-- --------------------------------------------------------

--
-- Table structure for table `QRTZ_PAUSED_TRIGGER_GRPS`
--

create TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

-- --------------------------------------------------------

--
-- Table structure for table `QRTZ_SCHEDULER_STATE`
--

create TABLE `QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(190) NOT NULL,
  `LAST_CHECKIN_TIME` bigint NOT NULL,
  `CHECKIN_INTERVAL` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

-- --------------------------------------------------------

--
-- Table structure for table `QRTZ_SIMPLE_TRIGGERS`
--

create TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `REPEAT_COUNT` bigint NOT NULL,
  `REPEAT_INTERVAL` bigint NOT NULL,
  `TIMES_TRIGGERED` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `QRTZ_SIMPLE_TRIGGERS`
--

insert into `QRTZ_SIMPLE_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `REPEAT_COUNT`, `REPEAT_INTERVAL`, `TIMES_TRIGGERED`) VALUES
('quartzScheduler', 'END92', 'DEFAULT', 0, 0, 0),
('quartzScheduler', 'END93', 'DEFAULT', 0, 0, 0),
('quartzScheduler', 'END94', 'DEFAULT', 0, 0, 0),
('quartzScheduler', 'END95', 'DEFAULT', 0, 0, 0),
('quartzScheduler', 'END96', 'DEFAULT', 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `QRTZ_SIMPROP_TRIGGERS`
--

create TABLE `QRTZ_SIMPROP_TRIGGERS` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

-- --------------------------------------------------------

--
-- Table structure for table `QRTZ_TRIGGERS`
--

create TABLE `QRTZ_TRIGGERS` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `QRTZ_TRIGGERS`
--

insert into `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `JOB_NAME`, `JOB_GROUP`, `DESCRIPTION`, `NEXT_FIRE_TIME`, `PREV_FIRE_TIME`, `PRIORITY`, `TRIGGER_STATE`, `TRIGGER_TYPE`, `START_TIME`, `END_TIME`, `CALENDAR_NAME`, `MISFIRE_INSTR`, `JOB_DATA`) VALUES
('quartzScheduler', 'END92', 'DEFAULT', 'END92', 'END AUCTIONS', NULL, 1661326560000, -1, 5, 'WAITING', 'SIMPLE', 1661326560000, 0, NULL, 1, ''),
('quartzScheduler', 'END93', 'DEFAULT', 'END93', 'END AUCTIONS', NULL, 1661240400000, -1, 5, 'WAITING', 'SIMPLE', 1661240400000, 0, NULL, 1, ''),
('quartzScheduler', 'END94', 'DEFAULT', 'END94', 'END AUCTIONS', NULL, 1660981500000, -1, 5, 'WAITING', 'SIMPLE', 1660981500000, 0, NULL, 1, ''),
('quartzScheduler', 'END95', 'DEFAULT', 'END95', 'END AUCTIONS', NULL, 1661673180000, -1, 5, 'WAITING', 'SIMPLE', 1661673180000, 0, NULL, 1, ''),
('quartzScheduler', 'END96', 'DEFAULT', 'END96', 'END AUCTIONS', NULL, 1661500680000, -1, 5, 'WAITING', 'SIMPLE', 1661500680000, 0, NULL, 1, '');

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

create TABLE `report` (
  `id` int NOT NULL,
  `user_reporter_id` int NOT NULL,
  `user_reported_id` int NOT NULL,
  `auction_id` int DEFAULT NULL,
  `description` varchar(1000) CHARACTER SET utf8mb4  DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `report`
--

insert into `report` (`id`, `user_reporter_id`, `user_reported_id`, `auction_id`, `description`, `created_at`, `updated_at`) VALUES
(24, 6, 13, NULL, 'bài có vấn đề, admin check hộ em he!', '2022-08-12 12:12:15', '2022-08-12 12:12:15');

-- --------------------------------------------------------

--
-- Table structure for table `report_image`
--

create TABLE `report_image` (
  `id` int NOT NULL,
  `report_id` int NOT NULL,
  `image_url` varchar(500) CHARACTER SET utf8mb4  NOT NULL,
  `public_id` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

-- --------------------------------------------------------

--
-- Table structure for table `report_result`
--

create TABLE `report_result` (
  `id` int NOT NULL,
  `admin_id` int NOT NULL,
  `report_id` int NOT NULL,
  `result` int NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

-- --------------------------------------------------------

--
-- Table structure for table `review_result`
--

create TABLE `review_result` (
  `id` int NOT NULL,
  `admin_id` int NOT NULL,
  `auction_id` int NOT NULL,
  `result` int NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `review_result`
--

insert into `review_result` (`id`, `admin_id`, `auction_id`, `result`, `created_at`, `updated_at`) VALUES
(34, 11, 92, 1, '2022-08-01 14:38:14', '2022-08-01 14:38:14'),
(35, 11, 93, 1, '2022-08-01 14:41:51', '2022-08-01 14:41:51'),
(36, 11, 94, 1, '2022-08-01 14:47:06', '2022-08-01 14:47:06'),
(37, 11, 95, 1, '2022-08-01 14:54:27', '2022-08-01 14:54:27'),
(38, 11, 96, 1, '2022-08-01 15:00:27', '2022-08-01 15:00:27'),
(39, 11, 97, 1, '2022-08-12 11:58:32', '2022-08-12 11:58:32');

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

create TABLE `role` (
  `id` int NOT NULL,
  `name` varchar(30) CHARACTER SET utf8mb4  NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `role`
--

insert into `role` (`id`, `name`) VALUES
(1, 'ADMIN'),
(2, 'USER');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

create TABLE `user` (
  `profile_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `user`
--

insert into `user` (`profile_id`) VALUES
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

create TABLE `verification_token` (
  `id` int NOT NULL,
  `expiration_time` datetime NOT NULL,
  `token` varchar(1000) NOT NULL,
  `profile_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

-- --------------------------------------------------------

--
-- Table structure for table `winner`
--

create TABLE `winner` (
  `id` int NOT NULL,
  `bid_id` int NOT NULL,
  `auction_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `winner`
--

insert into `winner` (`id`, `bid_id`, `auction_id`) VALUES
(680, 134, 97);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
alter table `admin`
  ADD PRIMARY KEY (`profile_id`),
  ADD UNIQUE KEY `user_id` (`profile_id`);

--
-- Indexes for table `auction`
--
alter table `auction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `category_id` (`category_id`);

--
-- Indexes for table `auction_user`
--
alter table `auction_user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nguoidung_id` (`user_id`,`auction_id`),
  ADD KEY `baidaugia_id` (`auction_id`);

--
-- Indexes for table `bid`
--
alter table `bid`
  ADD PRIMARY KEY (`id`),
  ADD KEY `baidaugia_id` (`auction_id`),
  ADD KEY `nguoidung_id` (`user_id`);

--
-- Indexes for table `category`
--
alter table `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `item`
--
alter table `item`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `auction_id` (`auction_id`);

--
-- Indexes for table `item_image`
--
alter table `item_image`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sanpham_id` (`item_id`);

--
-- Indexes for table `notification`
--
alter table `notification`
  ADD PRIMARY KEY (`id`),
  ADD KEY `profile_id` (`profile_id`);

--
-- Indexes for table `notification_auction`
--
alter table `notification_auction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `auction_id` (`auction_id`),
  ADD KEY `notification_id` (`notification_id`);

--
-- Indexes for table `notification_notified`
--
alter table `notification_notified`
  ADD PRIMARY KEY (`id`),
  ADD KEY `notification_id` (`notification_id`),
  ADD KEY `profile_id` (`profile_id`);

--
-- Indexes for table `notification_report`
--
alter table `notification_report`
  ADD PRIMARY KEY (`id`),
  ADD KEY `report_id` (`report_id`),
  ADD KEY `notification_id` (`notification_id`);

--
-- Indexes for table `profile`
--
alter table `profile`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `tai_khoan` (`username`),
  ADD KEY `vaitro_id` (`role_id`);

--
-- Indexes for table `QRTZ_BLOB_TRIGGERS`
--
alter table `QRTZ_BLOB_TRIGGERS`
  ADD PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  ADD KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`);

--
-- Indexes for table `QRTZ_CALENDARS`
--
alter table `QRTZ_CALENDARS`
  ADD PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`);

--
-- Indexes for table `QRTZ_CRON_TRIGGERS`
--
alter table `QRTZ_CRON_TRIGGERS`
  ADD PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`);

--
-- Indexes for table `QRTZ_FIRED_TRIGGERS`
--
alter table `QRTZ_FIRED_TRIGGERS`
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
alter table `QRTZ_JOB_DETAILS`
  ADD PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  ADD KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  ADD KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`);

--
-- Indexes for table `QRTZ_LOCKS`
--
alter table `QRTZ_LOCKS`
  ADD PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`);

--
-- Indexes for table `QRTZ_PAUSED_TRIGGER_GRPS`
--
alter table `QRTZ_PAUSED_TRIGGER_GRPS`
  ADD PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`);

--
-- Indexes for table `QRTZ_SCHEDULER_STATE`
--
alter table `QRTZ_SCHEDULER_STATE`
  ADD PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`);

--
-- Indexes for table `QRTZ_SIMPLE_TRIGGERS`
--
alter table `QRTZ_SIMPLE_TRIGGERS`
  ADD PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`);

--
-- Indexes for table `QRTZ_SIMPROP_TRIGGERS`
--
alter table `QRTZ_SIMPROP_TRIGGERS`
  ADD PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`);

--
-- Indexes for table `QRTZ_TRIGGERS`
--
alter table `QRTZ_TRIGGERS`
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
alter table `report`
  ADD PRIMARY KEY (`id`),
  ADD KEY `baidaugia_id` (`auction_id`),
  ADD KEY `nguoidung_id_reported` (`user_reported_id`),
  ADD KEY `nguoidung_id_reporter` (`user_reporter_id`);

--
-- Indexes for table `report_image`
--
alter table `report_image`
  ADD PRIMARY KEY (`id`),
  ADD KEY `phieubaocao_id` (`report_id`);

--
-- Indexes for table `report_result`
--
alter table `report_result`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `admin_id` (`admin_id`,`report_id`),
  ADD KEY `phieubaocao_id` (`report_id`);

--
-- Indexes for table `review_result`
--
alter table `review_result`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `admin_id` (`admin_id`,`auction_id`),
  ADD KEY `baidaugia_id` (`auction_id`);

--
-- Indexes for table `role`
--
alter table `role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
alter table `user`
  ADD PRIMARY KEY (`profile_id`),
  ADD UNIQUE KEY `user_id` (`profile_id`);

--
-- Indexes for table `verification_token`
--
alter table `verification_token`
  ADD PRIMARY KEY (`id`),
  ADD KEY `verification_token_ibfk_1` (`profile_id`);

--
-- Indexes for table `winner`
--
alter table `winner`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `auction_id` (`auction_id`),
  ADD KEY `lenhdaugia_id` (`bid_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `auction`
--
alter table `auction`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=99;

--
-- AUTO_INCREMENT for table `auction_user`
--
alter table `auction_user`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `bid`
--
alter table `bid`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=137;

--
-- AUTO_INCREMENT for table `category`
--
alter table `category`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `item`
--
alter table `item`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=89;

--
-- AUTO_INCREMENT for table `item_image`
--
alter table `item_image`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;

--
-- AUTO_INCREMENT for table `notification`
--
alter table `notification`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;

--
-- AUTO_INCREMENT for table `notification_auction`
--
alter table `notification_auction`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;

--
-- AUTO_INCREMENT for table `notification_notified`
--
alter table `notification_notified`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=81;

--
-- AUTO_INCREMENT for table `notification_report`
--
alter table `notification_report`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `profile`
--
alter table `profile`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `report`
--
alter table `report`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `report_image`
--
alter table `report_image`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `report_result`
--
alter table `report_result`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `review_result`
--
alter table `review_result`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `role`
--
alter table `role`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `verification_token`
--
alter table `verification_token`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `winner`
--
alter table `winner`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=681;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `admin`
--
alter table `admin`
  ADD CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`) ON delete CASCADE ON update RESTRICT;

--
-- Constraints for table `auction`
--
alter table `auction`
  ADD CONSTRAINT `auction_ibfk_5` FOREIGN KEY (`user_id`) REFERENCES `user` (`profile_id`) ON delete CASCADE ON update RESTRICT,
  ADD CONSTRAINT `auction_ibfk_6` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON delete SET NULL ON update RESTRICT;

--
-- Constraints for table `auction_user`
--
alter table `auction_user`
  ADD CONSTRAINT `auction_user_ibfk_1` FOREIGN KEY (`auction_id`) REFERENCES `auction` (`id`) ON delete CASCADE ON update RESTRICT,
  ADD CONSTRAINT `auction_user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`profile_id`) ON delete CASCADE ON update RESTRICT;

--
-- Constraints for table `bid`
--
alter table `bid`
  ADD CONSTRAINT `bid_ibfk_1` FOREIGN KEY (`auction_id`) REFERENCES `auction` (`id`) ON delete CASCADE ON update RESTRICT,
  ADD CONSTRAINT `bid_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`profile_id`) ON delete CASCADE ON update RESTRICT;

--
-- Constraints for table `item`
--
alter table `item`
  ADD CONSTRAINT `item_ibfk_1` FOREIGN KEY (`auction_id`) REFERENCES `auction` (`id`) ON delete CASCADE ON update CASCADE;

--
-- Constraints for table `item_image`
--
alter table `item_image`
  ADD CONSTRAINT `item_image_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON delete CASCADE ON update RESTRICT;

--
-- Constraints for table `notification`
--
alter table `notification`
  ADD CONSTRAINT `notification_ibfk_1` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`) ON delete CASCADE ON update RESTRICT;

--
-- Constraints for table `notification_auction`
--
alter table `notification_auction`
  ADD CONSTRAINT `notification_auction_ibfk_1` FOREIGN KEY (`auction_id`) REFERENCES `auction` (`id`) ON delete CASCADE ON update RESTRICT,
  ADD CONSTRAINT `notification_auction_ibfk_2` FOREIGN KEY (`notification_id`) REFERENCES `notification` (`id`) ON delete CASCADE ON update RESTRICT;

--
-- Constraints for table `notification_notified`
--
alter table `notification_notified`
  ADD CONSTRAINT `notification_notified_ibfk_1` FOREIGN KEY (`notification_id`) REFERENCES `notification` (`id`) ON delete CASCADE ON update RESTRICT,
  ADD CONSTRAINT `notification_notified_ibfk_2` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`) ON delete CASCADE ON update RESTRICT;

--
-- Constraints for table `notification_report`
--
alter table `notification_report`
  ADD CONSTRAINT `notification_report_ibfk_1` FOREIGN KEY (`report_id`) REFERENCES `report` (`id`) ON delete CASCADE ON update RESTRICT,
  ADD CONSTRAINT `notification_report_ibfk_2` FOREIGN KEY (`notification_id`) REFERENCES `notification` (`id`) ON delete CASCADE ON update RESTRICT;

--
-- Constraints for table `profile`
--
alter table `profile`
  ADD CONSTRAINT `profile_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON delete RESTRICT ON update RESTRICT;

--
-- Constraints for table `QRTZ_BLOB_TRIGGERS`
--
alter table `QRTZ_BLOB_TRIGGERS`
  ADD CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`);

--
-- Constraints for table `QRTZ_CRON_TRIGGERS`
--
alter table `QRTZ_CRON_TRIGGERS`
  ADD CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`);

--
-- Constraints for table `QRTZ_SIMPLE_TRIGGERS`
--
alter table `QRTZ_SIMPLE_TRIGGERS`
  ADD CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`);

--
-- Constraints for table `QRTZ_SIMPROP_TRIGGERS`
--
alter table `QRTZ_SIMPROP_TRIGGERS`
  ADD CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`);

--
-- Constraints for table `QRTZ_TRIGGERS`
--
alter table `QRTZ_TRIGGERS`
  ADD CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`);

--
-- Constraints for table `report`
--
alter table `report`
  ADD CONSTRAINT `report_ibfk_1` FOREIGN KEY (`auction_id`) REFERENCES `auction` (`id`) ON delete CASCADE ON update RESTRICT,
  ADD CONSTRAINT `report_ibfk_3` FOREIGN KEY (`user_reported_id`) REFERENCES `user` (`profile_id`) ON delete CASCADE ON update RESTRICT,
  ADD CONSTRAINT `report_ibfk_4` FOREIGN KEY (`user_reporter_id`) REFERENCES `user` (`profile_id`) ON delete CASCADE ON update RESTRICT;

--
-- Constraints for table `report_image`
--
alter table `report_image`
  ADD CONSTRAINT `report_image_ibfk_1` FOREIGN KEY (`report_id`) REFERENCES `report` (`id`) ON delete CASCADE ON update RESTRICT;

--
-- Constraints for table `report_result`
--
alter table `report_result`
  ADD CONSTRAINT `report_result_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`profile_id`) ON delete CASCADE ON update RESTRICT,
  ADD CONSTRAINT `report_result_ibfk_2` FOREIGN KEY (`report_id`) REFERENCES `report` (`id`) ON delete CASCADE ON update RESTRICT;

--
-- Constraints for table `review_result`
--
alter table `review_result`
  ADD CONSTRAINT `review_result_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`profile_id`) ON delete CASCADE ON update RESTRICT,
  ADD CONSTRAINT `review_result_ibfk_2` FOREIGN KEY (`auction_id`) REFERENCES `auction` (`id`) ON delete CASCADE ON update RESTRICT;

--
-- Constraints for table `user`
--
alter table `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`) ON delete CASCADE ON update RESTRICT;

--
-- Constraints for table `verification_token`
--
alter table `verification_token`
  ADD CONSTRAINT `verification_token_ibfk_1` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`) ON delete CASCADE ON update RESTRICT;

--
-- Constraints for table `winner`
--
alter table `winner`
  ADD CONSTRAINT `winner_ibfk_1` FOREIGN KEY (`bid_id`) REFERENCES `bid` (`id`) ON delete CASCADE ON update RESTRICT,
  ADD CONSTRAINT `winner_ibfk_2` FOREIGN KEY (`auction_id`) REFERENCES `auction` (`id`) ON delete CASCADE ON update RESTRICT;
commit;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
