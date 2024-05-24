-- --------------------------------------------------------
-- 호스트:                          10.41.0.112
-- 서버 버전:                        10.11.6-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- teenflea 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `teenflea` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `teenflea`;

-- 테이블 teenflea.flea_bbs 구조 내보내기
CREATE TABLE IF NOT EXISTS `flea_bbs` (
  `bbs_idx` int(11) NOT NULL AUTO_INCREMENT,
  `modify_date` datetime(6) DEFAULT NULL,
  `reg_date` datetime(6) DEFAULT NULL,
  `addr1` varchar(50) DEFAULT NULL,
  `addr2` varchar(30) DEFAULT NULL,
  `category1` varchar(10) NOT NULL,
  `category2` varchar(30) DEFAULT NULL,
  `content` longtext NOT NULL,
  `email` varchar(30) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `price` int(11) DEFAULT 0,
  `read_cnt` int(11) DEFAULT NULL,
  `thumbnail_directory` varchar(100) DEFAULT NULL,
  `thumbnail_file_name` varchar(100) DEFAULT NULL,
  `title` varchar(100) NOT NULL,
  `user_id` varchar(20) NOT NULL,
  `zip_code` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`bbs_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 teenflea.flea_bbs_file 구조 내보내기
CREATE TABLE IF NOT EXISTS `flea_bbs_file` (
  `bbs_file_idx` int(11) NOT NULL AUTO_INCREMENT,
  `modify_date` datetime(6) DEFAULT NULL,
  `reg_date` datetime(6) DEFAULT NULL,
  `bbs_idx` int(11) NOT NULL,
  `directory` varchar(100) NOT NULL,
  `file_name` varchar(100) NOT NULL,
  `user_id` varchar(20) NOT NULL,
  PRIMARY KEY (`bbs_file_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 teenflea.flea_bbs_reply 구조 내보내기
CREATE TABLE IF NOT EXISTS `flea_bbs_reply` (
  `reply_idx` int(11) NOT NULL AUTO_INCREMENT,
  `modify_date` datetime(6) DEFAULT NULL,
  `reg_date` datetime(6) DEFAULT NULL,
  `bbs_idx` int(11) NOT NULL,
  `content` varchar(2000) NOT NULL,
  `user_id` varchar(20) NOT NULL,
  PRIMARY KEY (`reply_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 teenflea.flea_member 구조 내보내기
CREATE TABLE IF NOT EXISTS `flea_member` (
  `member_idx` int(11) NOT NULL AUTO_INCREMENT,
  `modify_date` datetime(6) DEFAULT NULL,
  `reg_date` datetime(6) DEFAULT NULL,
  `addr1` varchar(50) NOT NULL,
  `addr2` varchar(30) NOT NULL,
  `birthday` varchar(20) NOT NULL,
  `email` varchar(30) NOT NULL,
  `leave_date` datetime(6) DEFAULT NULL,
  `name` varchar(10) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  `pwd` varchar(100) NOT NULL,
  `role` varchar(10) DEFAULT NULL,
  `user_id` varchar(20) NOT NULL,
  `user_state` varchar(10) NOT NULL,
  `zip_code` varchar(10) NOT NULL,
  PRIMARY KEY (`member_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
