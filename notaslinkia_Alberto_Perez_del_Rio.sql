-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-03-2023 a las 15:55:15
-- Versión del servidor: 10.4.27-MariaDB
-- Versión de PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `notaslinkia`
--

DROP DATABASE IF EXISTS notaslinkia;

CREATE DATABASE notaslinkia;

USE notaslinkia;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alumnos`
--

CREATE TABLE `alumnos` (
  `id_alumno` int(11) NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `nom_user` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `historial`
--

CREATE TABLE `historial` (
  `id` int(11) NOT NULL,
  `tipo` varchar(25) NOT NULL,
  `user` int(11) NOT NULL,
  `detalle` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `modulos`
--

CREATE TABLE `modulos` (
  `id` int(11) NOT NULL,
  `nombre` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `notas`
--

CREATE TABLE `notas` (
  `id_notas` int(11) NOT NULL,
  `id_alumno` int(11) NOT NULL,
  `id_modulo` int(11) NOT NULL,
  `notas` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;



-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profesores`
--

CREATE TABLE `profesores` (
  `id` int(11) NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `nom_user` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alumnos`
--
ALTER TABLE `alumnos`
  ADD PRIMARY KEY (`id_alumno`) USING BTREE;

--
-- Indices de la tabla `historial`
--
ALTER TABLE `historial`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `modulos`
--
ALTER TABLE `modulos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `notas`
--
ALTER TABLE `notas`
  ADD PRIMARY KEY (`id_notas`) USING BTREE,
  ADD KEY `modulo_fk` (`id_modulo`),
  ADD KEY `alumno_fk` (`id_alumno`);

--
-- Indices de la tabla `profesores`
--
ALTER TABLE `profesores`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `alumnos`
--
ALTER TABLE `alumnos`
  MODIFY `id_alumno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT de la tabla `historial`
--
ALTER TABLE `historial`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT de la tabla `modulos`
--
ALTER TABLE `modulos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT de la tabla `notas`
--
ALTER TABLE `notas`
  MODIFY `id_notas` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT de la tabla `profesores`
--
ALTER TABLE `profesores`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `notas`
--
ALTER TABLE `notas`
  ADD CONSTRAINT `alumno_fk` FOREIGN KEY (`id_alumno`) REFERENCES `alumnos` (`id_alumno`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `modulo_fk` FOREIGN KEY (`id_modulo`) REFERENCES `modulos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;



-- Volcado de datos para la tabla `profesores`
--
-- Importante que exista usuario
-- (1, 'Administrador', 'admin', 'admin'),

INSERT INTO `profesores` (`id`, `nombre`, `nom_user`, `password`) VALUES
(1, 'Administrador', 'admin', 'admin'),
(2, 'Pedro Sánchez', 'pedro', 'clave1'),
(3, 'Laura Gómez', 'laura', 'clave2'),
(4, 'Miguel Álvarez', 'miguel', 'clave3'),
(5, 'Sofía Ruiz', 'sofia', 'clave4');

--
-- Volcado de datos para la tabla `notas`
--

INSERT INTO `notas` (`id_notas`, `id_alumno`, `id_modulo`, `notas`) VALUES
(1, 1, 1, 8.5),
(2, 1, 2, 7.2),
(3, 1, 3, 9.1),
(4, 2, 1, 7.8),
(5, 2, 2, 6.5),
(6, 2, 3, 8.2),
(7, 3, 1, 7.1),
(8, 3, 2, 8.9),
(9, 3, 3, 6.7),
(10, 4, 1, 6.9),
(11, 4, 2, 7.6),
(12, 4, 3, 8.3);

--
-- Volcado de datos para la tabla `modulos`
--

INSERT INTO `modulos` (`id`, `nombre`) VALUES
(1, 'M01'),
(2, 'M02'),
(3, 'M03'),
(4, 'M04'),
(5, 'M05'),
(6, 'M06'),
(7, 'M07'),
(8, 'M08'),
(9, 'M09'),
(10, 'M10'),
(11, 'M11'),
(12, 'M12'),
(13, 'M13'),
(14, 'M14');


--
-- Volcado de datos para la tabla `historial`
--

INSERT INTO `historial` (`id`, `tipo`, `user`, `detalle`) VALUES
(1, 'Inicio de sesión', 5, '2023-03-12 20:32:38.158'),
(2, 'Inicio de sesión', 5, '2023-03-12 20:39:53.159'),
(3, 'Inicio de sesión', 10, '2023-03-12 20:39:53.159');


--
-- Volcado de datos para la tabla `alumnos`
--

INSERT INTO `alumnos` (`id_alumno`, `nombre`, `nom_user`, `password`) VALUES
(1, 'Maria', 'maria2', 'clave1234'),
(2, 'Luis García', 'luis', 'clave3'),
(3, 'Ana Martínez', 'ana', 'clave4'),
(4, 'Pedro Picapiedra', 'pica', '12345'),
(5, 'Sandra Gomez', 'sandra', '12345');


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
