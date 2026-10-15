-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Oct 15, 2026 at 11:39 AM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ekspedisi_antariksa`
--

-- --------------------------------------------------------

--
-- Table structure for table `ekspedisi`
--

CREATE TABLE `ekspedisi` (
  `id_ekspedisi` int NOT NULL,
  `nama` varchar(100) NOT NULL,
  `tujuan` varchar(100) NOT NULL,
  `durasi` int NOT NULL,
  `status` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `ekspedisi`
--

INSERT INTO `ekspedisi` (`id_ekspedisi`, `nama`, `tujuan`, `durasi`, `status`) VALUES
(1, 'Galang Dana BEM KM UNMUL', 'Mars', 14, 'Berlangsung'),
(2, 'Misi Bulan Nusantara', 'Bulan', 30, 'Direncanakan'),
(3, 'otw rukes', 'Planet rukes', 20, 'Berlangsung');

-- --------------------------------------------------------

--
-- Table structure for table `kru`
--

CREATE TABLE `kru` (
  `id_kru` int NOT NULL,
  `nama` varchar(100) NOT NULL,
  `usia` int NOT NULL,
  `tipe` varchar(20) NOT NULL,
  `spesialisasi` varchar(100) DEFAULT NULL,
  `jam_terbang` int DEFAULT NULL,
  `bidang_keahlian` varchar(100) DEFAULT NULL,
  `sertifikasi` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `kru`
--

INSERT INTO `kru` (`id_kru`, `nama`, `usia`, `tipe`, `spesialisasi`, `jam_terbang`, `bidang_keahlian`, `sertifikasi`) VALUES
(1, 'Dylan Al Furqon', 25, 'Astronot', 'Pengendali Multiverse', 500, NULL, 'Sertifikasi Bahasa'),
(2, 'Tony Stark', 45, 'Teknisi', 'Genius, Billionaire, Playboy', NULL, 'Mesin Roket', 'ISO 9001');

-- --------------------------------------------------------

--
-- Table structure for table `pesawat_antariksa`
--

CREATE TABLE `pesawat_antariksa` (
  `id_pesawat` int NOT NULL,
  `nama` varchar(100) NOT NULL,
  `jenis` varchar(50) NOT NULL,
  `kapasitas` int NOT NULL,
  `status` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `pesawat_antariksa`
--

INSERT INTO `pesawat_antariksa` (`id_pesawat`, `nama`, `jenis`, `kapasitas`, `status`) VALUES
(1, 'Taufan BAEK', 'Roket Orbital', 6, 'Siap'),
(2, 'Nusantara-2', 'Shuttle', 4, 'Perawatan');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ekspedisi`
--
ALTER TABLE `ekspedisi`
  ADD PRIMARY KEY (`id_ekspedisi`);

--
-- Indexes for table `kru`
--
ALTER TABLE `kru`
  ADD PRIMARY KEY (`id_kru`);

--
-- Indexes for table `pesawat_antariksa`
--
ALTER TABLE `pesawat_antariksa`
  ADD PRIMARY KEY (`id_pesawat`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
