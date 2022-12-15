-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 15, 2022 at 06:24 AM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 7.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `googlemaprestapi`
--

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(38);

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `contactname` varchar(55) NOT NULL,
  `email` varchar(65) NOT NULL,
  `password` varchar(64) NOT NULL,
  `company` varchar(100) NOT NULL,
  `address` varchar(255) NOT NULL,
  `latitude` varchar(25) DEFAULT NULL,
  `longitude` varchar(25) DEFAULT NULL,
  `open` varchar(10) NOT NULL,
  `close` varchar(10) NOT NULL,
  `chargesperhour` float NOT NULL,
  `logo` varchar(150) DEFAULT NULL,
  `enabled` tinyint(4) NOT NULL,
  `reset_password_token` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `contactname`, `email`, `password`, `company`, `address`, `latitude`, `longitude`, `open`, `close`, `chargesperhour`, `logo`, `enabled`, `reset_password_token`) VALUES
(36, 'Narender Singh', 'narendra.s@mipl.us', '$2a$10$Z.lQ0uXhIcoG6jcWsJkk6.GE8IpIFL543UjVa6FHjBLmcVggacrdm', 'Naren Pet Care', 'Vikas Marg, Veer Savarkar Block, Block D, Laxmi Nagar, Delhi, 110092, India', '28.62963733244088', '77.27907846370445', '10:30', '06:30', 200, '//ssl.gstatic.com/accounts/ui/avatar_2x.png', 1, NULL),
(37, 'Sunny Kumar', 'sunny@gmail.com', '$2a$10$hBfjTmP71iSdLq2oPNw9lOwHcC0GNTb7B0OJHnT/174dsJqnoe7i2', 'Sunny Pet Care', 'Vishwakarma Rd, D Block, Sector 59, Noida, Uttar Pradesh 201307, India', '28.606129867831026', '77.37436808877452', '10:30', '06:30', 150, '//ssl.gstatic.com/accounts/ui/avatar_2x.png', 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `users_roles`
--

CREATE TABLE `users_roles` (
  `id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users_roles`
--

INSERT INTO `users_roles` (`id`, `role_id`) VALUES
(31, 2),
(32, 2),
(33, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `hibernate_sequence`
--
ALTER TABLE `hibernate_sequence`
  ADD PRIMARY KEY (`next_val`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`);

--
-- Indexes for table `users_roles`
--
ALTER TABLE `users_roles`
  ADD KEY `user_fk_idx` (`id`),
  ADD KEY `role_fk_idx` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
