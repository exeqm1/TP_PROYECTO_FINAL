-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 19, 2025 at 04:21 AM
-- Server version: 8.4.3
-- PHP Version: 8.3.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gp9_cinemacentro_basededatos`
--

-- --------------------------------------------------------

--
-- Table structure for table `comprador`
--

CREATE TABLE `comprador` (
  `Id_Comprador` int NOT NULL,
  `DNI` int NOT NULL,
  `nombre` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `medioPago` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `fechaNac` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `comprador`
--

INSERT INTO `comprador` (`Id_Comprador`, `DNI`, `nombre`, `password`, `medioPago`, `fechaNac`) VALUES
(14, 26246, 'test', '246246', 'Efectivo', '2025-11-04'),
(15, 66866868, 'etuetu', '244', 'Transferencia', '2025-11-17');

-- --------------------------------------------------------

--
-- Table structure for table `lugar`
--

CREATE TABLE `lugar` (
  `Id_lugar` int NOT NULL,
  `Id_proyeccion` int NOT NULL,
  `fila` int NOT NULL,
  `numero` int NOT NULL,
  `disponible` tinyint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `lugar`
--

INSERT INTO `lugar` (`Id_lugar`, `Id_proyeccion`, `fila`, `numero`, `disponible`) VALUES
(1, 17, 5, 6, 1),
(2, 16, 6, 6, 1);

-- --------------------------------------------------------

--
-- Table structure for table `pelicula`
--

CREATE TABLE `pelicula` (
  `id_Pelicula` int NOT NULL,
  `titulo` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `director` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `actores` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `origen` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `genero` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `estreno` date NOT NULL,
  `enCartelera` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pelicula`
--

INSERT INTO `pelicula` (`id_Pelicula`, `titulo`, `director`, `actores`, `origen`, `genero`, `estreno`, `enCartelera`) VALUES
(14, 'test', 'test', 'test', 'test', 'test', '2025-11-02', 1),
(15, 'teuetu', 'etuet', 'etue', 'tue', 'riyri', '2025-11-07', 1);

-- --------------------------------------------------------

--
-- Table structure for table `proyeccion`
--

CREATE TABLE `proyeccion` (
  `Id_proyeccion` int NOT NULL,
  `Id_pelicula` int NOT NULL,
  `Id_sala` int NOT NULL,
  `idioma` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `es3D` int NOT NULL,
  `subtitulada` int NOT NULL,
  `horaInicio` time NOT NULL,
  `horaFin` time NOT NULL,
  `precio` double NOT NULL,
  `activa` tinyint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `proyeccion`
--

INSERT INTO `proyeccion` (`Id_proyeccion`, `Id_pelicula`, `Id_sala`, `idioma`, `es3D`, `subtitulada`, `horaInicio`, `horaFin`, `precio`, `activa`) VALUES
(16, 14, 17, 'ddye', 1, 1, '01:00:00', '02:00:00', 456, 1),
(17, 15, 18, 'yrir', 1, 1, '01:01:00', '02:02:00', 4846, 1);

-- --------------------------------------------------------

--
-- Table structure for table `sala`
--

CREATE TABLE `sala` (
  `Id_sala` int NOT NULL,
  `nroSala` int NOT NULL,
  `apta3D` int NOT NULL,
  `capacidad` int NOT NULL,
  `estado` tinyint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sala`
--

INSERT INTO `sala` (`Id_sala`, `nroSala`, `apta3D`, `capacidad`, `estado`) VALUES
(17, 12, 1, 50, 1),
(18, 4, 1, 57, 1);

-- --------------------------------------------------------

--
-- Table structure for table `ticket`
--

CREATE TABLE `ticket` (
  `Id_ticket` int NOT NULL,
  `Id_comprador` int NOT NULL,
  `Id_lugar` int NOT NULL,
  `fechaCompra` date NOT NULL,
  `fechaFuncion` date NOT NULL,
  `monto` double NOT NULL,
  `activo` tinyint NOT NULL,
  `id_proyeccion` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ticket`
--

INSERT INTO `ticket` (`Id_ticket`, `Id_comprador`, `Id_lugar`, `fechaCompra`, `fechaFuncion`, `monto`, `activo`, `id_proyeccion`) VALUES
(5, 14, 2, '2025-11-21', '2025-11-06', 0, 1, 16),
(6, 15, 2, '2025-11-21', '2025-11-06', 0, 1, 16);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comprador`
--
ALTER TABLE `comprador`
  ADD PRIMARY KEY (`Id_Comprador`);

--
-- Indexes for table `lugar`
--
ALTER TABLE `lugar`
  ADD PRIMARY KEY (`Id_lugar`),
  ADD KEY `Id_proyeccion` (`Id_proyeccion`);

--
-- Indexes for table `pelicula`
--
ALTER TABLE `pelicula`
  ADD PRIMARY KEY (`id_Pelicula`);

--
-- Indexes for table `proyeccion`
--
ALTER TABLE `proyeccion`
  ADD PRIMARY KEY (`Id_proyeccion`),
  ADD KEY `Id_pelicula` (`Id_pelicula`),
  ADD KEY `Id_sala` (`Id_sala`);

--
-- Indexes for table `sala`
--
ALTER TABLE `sala`
  ADD PRIMARY KEY (`Id_sala`);

--
-- Indexes for table `ticket`
--
ALTER TABLE `ticket`
  ADD PRIMARY KEY (`Id_ticket`),
  ADD KEY `Id_comprador` (`Id_comprador`),
  ADD KEY `Id_lugar` (`Id_lugar`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comprador`
--
ALTER TABLE `comprador`
  MODIFY `Id_Comprador` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `lugar`
--
ALTER TABLE `lugar`
  MODIFY `Id_lugar` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `pelicula`
--
ALTER TABLE `pelicula`
  MODIFY `id_Pelicula` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `proyeccion`
--
ALTER TABLE `proyeccion`
  MODIFY `Id_proyeccion` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `sala`
--
ALTER TABLE `sala`
  MODIFY `Id_sala` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `ticket`
--
ALTER TABLE `ticket`
  MODIFY `Id_ticket` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `lugar`
--
ALTER TABLE `lugar`
  ADD CONSTRAINT `lugar_ibfk_1` FOREIGN KEY (`Id_proyeccion`) REFERENCES `proyeccion` (`Id_proyeccion`);

--
-- Constraints for table `proyeccion`
--
ALTER TABLE `proyeccion`
  ADD CONSTRAINT `proyeccion_ibfk_1` FOREIGN KEY (`Id_pelicula`) REFERENCES `pelicula` (`id_Pelicula`),
  ADD CONSTRAINT `proyeccion_ibfk_2` FOREIGN KEY (`Id_sala`) REFERENCES `sala` (`Id_sala`);

--
-- Constraints for table `ticket`
--
ALTER TABLE `ticket`
  ADD CONSTRAINT `ticket_ibfk_1` FOREIGN KEY (`Id_comprador`) REFERENCES `comprador` (`Id_Comprador`),
  ADD CONSTRAINT `ticket_ibfk_2` FOREIGN KEY (`Id_lugar`) REFERENCES `lugar` (`Id_lugar`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
