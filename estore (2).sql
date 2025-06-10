-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 23, 2024 at 05:59 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `estore`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAnalytics` (IN `p1_code` INT, OUT `p1_type` VARCHAR(50), OUT `p1_price` DOUBLE)   BEGIN
SELECT p_type,p_price into p1_type,p1_price from product_details where p_code = p1_code;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getCustomerId` (IN `name` VARCHAR(50))   BEGIN
SELECT c_id FROM customer_details where c_name=name;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getLastBillNo` (OUT `bill` INT)   BEGIN
SELECT bill_no into bill from orders where order_id=(SELECT COUNT(*) FROM orders)+15;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `StockUpdate` (IN `p1_code` INT)   BEGIN
UPDATE inventory set units = (units-1) where p_code= p1_code;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `customer_details`
--

CREATE TABLE `customer_details` (
  `c_id` int(11) NOT NULL,
  `c_name` varchar(50) NOT NULL,
  `c_mobile` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer_details`
--

INSERT INTO `customer_details` (`c_id`, `c_name`, `c_mobile`) VALUES
(3251, 'Shivangi', '1010101010'),
(3252, 'Bhavesh', '2020202020'),
(3253, 'Rushvi', '3030303030'),
(3254, 'Dharmit', '4040404040'),
(3255, 'Tisha', '5050505050'),
(3256, 'Vishwam', '6060606060'),
(3257, 'Shreyansh', '7070707070'),
(3258, 'Indrajeet', '8080808080'),
(3259, 'Durgesh', '4125637892');

--
-- Triggers `customer_details`
--
DELIMITER $$
CREATE TRIGGER `CheckCustomerId` BEFORE INSERT ON `customer_details` FOR EACH ROW BEGIN
    IF EXISTS (SELECT c_id FROM Customer_details WHERE c_id = NEW.c_id) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Duplicate CustomerId is not allowed.';
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `inventory`
--

CREATE TABLE `inventory` (
  `p_code` int(11) NOT NULL,
  `units` int(11) NOT NULL,
  `date_of_arrival` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inventory`
--

INSERT INTO `inventory` (`p_code`, `units`, `date_of_arrival`) VALUES
(1011, 10, '2024-08-09 06:12:03'),
(1012, 13, '2024-08-22 07:40:41'),
(1013, 11, '2024-08-09 06:12:03'),
(1014, 8, '2024-08-14 05:18:20'),
(1015, 12, '2024-08-09 06:37:03'),
(1016, 14, '2024-08-14 07:39:52'),
(1017, 9, '2024-08-14 06:35:55'),
(1018, 9, '2024-08-22 07:40:41'),
(1019, 12, '2024-08-09 06:12:03'),
(1020, 9, '2024-08-14 07:35:03'),
(1021, 24, '2024-08-22 18:27:21'),
(1022, 15, '2024-08-14 05:21:29'),
(1023, 14, '2024-08-09 06:15:02'),
(1024, 16, '2024-08-14 06:32:14'),
(1025, 15, '2024-08-14 06:31:29'),
(1026, 18, '2024-08-14 07:35:03'),
(1027, 17, '2024-08-09 06:15:02'),
(1028, 13, '2024-08-09 06:15:02'),
(1029, 16, '2024-08-14 07:39:52'),
(1030, 15, '2024-08-14 05:18:20'),
(1031, 12, '2024-08-09 06:15:02');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `customer_id` int(11) NOT NULL,
  `Amount` double NOT NULL,
  `order_id` int(10) NOT NULL,
  `Bill_no` int(10) NOT NULL,
  `Date` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`customer_id`, `Amount`, `order_id`, `Bill_no`, `Date`) VALUES
(3251, 25000, 16, 972, '2024-08-22'),
(3254, 15999, 17, 973, '2024-08-23'),
(3254, 1299, 18, 974, '2024-08-23'),
(3254, 30000, 19, 975, '2024-08-23'),
(3254, 30000, 20, 976, '2024-08-23'),
(3251, 56299, 21, 977, '2024-08-23'),
(3252, 28489, 22, 978, '2024-08-23');

-- --------------------------------------------------------

--
-- Table structure for table `order_details`
--

CREATE TABLE `order_details` (
  `bill_no` int(10) NOT NULL,
  `order_id` int(10) NOT NULL,
  `Product_code` int(10) NOT NULL,
  `Product_name` varchar(50) NOT NULL,
  `Price` int(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order_details`
--

INSERT INTO `order_details` (`bill_no`, `order_id`, `Product_code`, `Product_name`, `Price`) VALUES
(976, 19, 1014, 'Realme 12 pro plus', 30000),
(977, 20, 1031, 'RedMi Note 12 5G', 20000),
(978, 21, 1025, 'Venus 18L Oven Toast Grill', 4490),
(978, 21, 1017, 'Whirlpool 235L, Double Door', 23999);

-- --------------------------------------------------------

--
-- Table structure for table `product_details`
--

CREATE TABLE `product_details` (
  `p_code` int(11) NOT NULL,
  `p_name` varchar(50) NOT NULL,
  `p_price` double NOT NULL,
  `p_type` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product_details`
--

INSERT INTO `product_details` (`p_code`, `p_name`, `p_price`, `p_type`) VALUES
(1011, 'Apple MacBook', 90000, 'Laptop'),
(1012, 'Dell Inspiron 13th Gen Laptop', 50000, 'Laptop'),
(1013, 'OnePlus Nord CE 3', 25000, 'Mobile Phone'),
(1014, 'Realme 12 pro plus', 30000, 'Mobile Phone'),
(1015, 'Tronica Sargam Party Spreaker', 3499, 'Speaker'),
(1016, 'JYX Sound Speaker', 9499, 'Speaker'),
(1017, 'Whirlpool 235L, Double Door', 23999, 'Refrigerator'),
(1018, 'Samsung 635L, Double Door', 79990, 'Refrigerator'),
(1019, 'Daikin 1.5 Ton Inverter Split AC', 36999, 'A.C.'),
(1020, 'Lloyd 2 Ton Inverter Split AC', 33990, 'A.C.'),
(1021, 'Samsung 80cm HD TV', 15999, 'TV'),
(1022, 'LG 108cm 4K Ultra HD TV', 35000, 'TV'),
(1023, 'Samsung 9.0Kg Top Load', 23990, 'Washing Machine'),
(1024, 'Godrej 6kg 5Star', 24590, 'Washing Machine'),
(1025, 'Venus 18L Oven Toast Grill', 4490, 'Oven'),
(1026, 'Pigeon by Stovekraft Oven', 1649, 'Oven'),
(1027, 'Skullcandy Cassette ', 2599, 'Headphone'),
(1028, 'boAt Rockerz', 1599, 'Headphone'),
(1029, 'Mivi DuoPods', 1299, 'Earpodes'),
(1030, 'Apple EarPodes', 2500, 'Earpodes'),
(1031, 'RedMi Note 12 5G', 20000, 'Mobile Phone');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer_details`
--
ALTER TABLE `customer_details`
  ADD PRIMARY KEY (`c_id`);

--
-- Indexes for table `inventory`
--
ALTER TABLE `inventory`
  ADD PRIMARY KEY (`p_code`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`);

--
-- Indexes for table `product_details`
--
ALTER TABLE `product_details`
  ADD PRIMARY KEY (`p_code`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customer_details`
--
ALTER TABLE `customer_details`
  MODIFY `c_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3260;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `inventory`
--
ALTER TABLE `inventory`
  ADD CONSTRAINT `inventory_ibfk_1` FOREIGN KEY (`p_code`) REFERENCES `product_details` (`p_code`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
