/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE IF NOT EXISTS `csmdatabase` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `csmdatabase`;

CREATE TABLE IF NOT EXISTS `setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numofpc` int(11) DEFAULT NULL,
  `cost` bigint(25) DEFAULT NULL,
  `costuser` bigint(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `setting` (`id`, `numofpc`, `cost`, `costuser`) VALUES
	(1, 16, 12000, 240000);

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `time` bigint(20) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `user` (`id`, `username`, `password`, `name`, `phone`, `time`, `status`) VALUES
	(16, 'TTT', '123', 'Trần Tấn Thịnh', '0329966939', 2326, 'OFFLINE'),
	(17, 'admin', '1234', 'THỊNH', '0329966939', 62886, 'OFFLINE'),
	(18, 'BACH123', '1234', 'Phan Văn Bách', '0323424531', 3000, 'BLOCKED'),
	(19, 'PHUC123', '1234', 'Hồ Duy Phúc', '0332482454', 6000, 'BLOCKED'),
	(20, 'vy', '1234', 'Phan Nguyễn Tường Vy', '0432434343', 4500, 'OFFLINE'),
	(28, 'VINH', '123', 'Lê Quốc Vinh', '0324434432', 5880, 'OFFLINE'),
	(29, 'TAM', '123', 'Nguyễn Khắc Nhân Tâm', '0343473473', 3001, 'OFFLINE'),
	(40, 'Tin', '1', 'Vũ Hoàng Tín', '0332344334', 4500, 'OFFLINE'),
	(41, 'dang', '1', 'Nguyễn Thanh Đăng', '0344354682', 4500, 'OFFLINE'),
	(42, 'duc', '1', 'Lê Huỳnh Đức', '0482343678', 4500, 'OFFLINE'),
	(43, 'Dat', '1', 'Hồ Bá Đạt', '0353823758', 4500, 'BLOCKED'),
	(44, 'Cuong', '1', 'Đỗ Cao Cường', '0234543289', 4500, 'OFFLINE'),
	(45, 'Nhan', '1', 'Võ Trọng Nhân', '0345382945', 3600, 'OFFLINE'),
	(46, 'Tu', '1', 'Lê Văn Tư', '0327573826', 3900, 'OFFLINE');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
