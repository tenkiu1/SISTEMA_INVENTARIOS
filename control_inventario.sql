-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 26-11-2023 a las 04:53:48
-- Versión del servidor: 10.4.25-MariaDB
-- Versión de PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `control_inventario`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `almacen`
--

CREATE TABLE `almacen` (
  `codigo_almacen` int(11) NOT NULL,
  `almacen` varchar(50) DEFAULT NULL,
  `sector_almacen` varchar(50) DEFAULT NULL,
  `cod_producto` varchar(50) NOT NULL,
  `stock` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `cod_categoria` int(11) NOT NULL,
  `categoria` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ingreso_producto`
--

CREATE TABLE `ingreso_producto` (
  `cod_mov` int(11) NOT NULL,
  `cod_producto` int(11) DEFAULT NULL,
  `producto` varchar(50) DEFAULT NULL,
  `precio` int(5) NOT NULL,
  `stock` int(11) NOT NULL,
  `almacen` varchar(50) DEFAULT NULL,
  `sector_almacen` varchar(50) DEFAULT NULL,
  `fecha_ingreso` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `kardex`
--

CREATE TABLE `kardex` (
  `tipo_movimiento` int(11) NOT NULL,
  `codigo_mov` int(11) NOT NULL,
  `sector_almacen` varchar(50) NOT NULL,
  `almacen` varchar(50) NOT NULL,
  `cod_producto` int(11) NOT NULL,
  `fecha_movimiento` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `packing_list`
--

CREATE TABLE `packing_list` (
  `cod_packing` int(4) NOT NULL,
  `almacen` varchar(50) NOT NULL,
  `proveedor` varchar(50) NOT NULL,
  `archivo` tinyint(1) NOT NULL,
  `fecha` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `cod_producto` int(11) NOT NULL,
  `producto` varchar(50) DEFAULT NULL,
  `precio` decimal(10,2) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `categoria_cod_categoria` int(11) DEFAULT NULL,
  `almacen_codigo_almacen` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reporte_irregularidad`
--

CREATE TABLE `reporte_irregularidad` (
  `cod_reporte` int(11) NOT NULL,
  `nombre_trabajador` varchar(50) NOT NULL,
  `almacen` int(11) DEFAULT NULL,
  `cod_producto` int(11) DEFAULT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `fecha_reporte` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `retirada_venta`
--

CREATE TABLE `retirada_venta` (
  `cod_mov` int(11) NOT NULL,
  `nombre_trabajador` varchar(50) NOT NULL,
  `cod_producto` int(11) DEFAULT NULL,
  `producto` varchar(50) DEFAULT NULL,
  `precio` decimal(10,2) DEFAULT NULL,
  `almacen` int(11) DEFAULT NULL,
  `stock_total` int(11) DEFAULT NULL,
  `stock_salida` int(11) DEFAULT NULL,
  `cliente` varchar(50) DEFAULT NULL,
  `encargado_recepcion` varchar(50) DEFAULT NULL,
  `fecha_retirada` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `traslado_almacen`
--

CREATE TABLE `traslado_almacen` (
  `cod_mov` int(11) NOT NULL,
  `nombre_trabajador` varchar(50) DEFAULT NULL,
  `cod_producto` int(11) DEFAULT NULL,
  `nombre_producto` varchar(50) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `stock_total` int(11) DEFAULT NULL,
  `almacen` int(11) DEFAULT NULL,
  `almacen_destino` int(11) DEFAULT NULL,
  `motivo` varchar(100) DEFAULT NULL,
  `fecha_traslado` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `cod_usuario` int(11) NOT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `apellido` varchar(50) DEFAULT NULL,
  `num_telefono` int(11) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `nom_usuario` varchar(50) DEFAULT NULL,
  `contraseña` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `almacen`
--
ALTER TABLE `almacen`
  ADD PRIMARY KEY (`codigo_almacen`);

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`cod_categoria`);

--
-- Indices de la tabla `ingreso_producto`
--
ALTER TABLE `ingreso_producto`
  ADD PRIMARY KEY (`cod_mov`),
  ADD KEY `cod_producto` (`cod_producto`);

--
-- Indices de la tabla `kardex`
--
ALTER TABLE `kardex`
  ADD PRIMARY KEY (`tipo_movimiento`),
  ADD KEY `codigo_mov` (`codigo_mov`);

--
-- Indices de la tabla `packing_list`
--
ALTER TABLE `packing_list`
  ADD PRIMARY KEY (`cod_packing`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`cod_producto`),
  ADD KEY `categoria_cod_categoria` (`categoria_cod_categoria`),
  ADD KEY `almacen_codigo_almacen` (`almacen_codigo_almacen`);

--
-- Indices de la tabla `reporte_irregularidad`
--
ALTER TABLE `reporte_irregularidad`
  ADD PRIMARY KEY (`cod_reporte`),
  ADD KEY `almacen_codigo_almacen` (`almacen`),
  ADD KEY `cod_producto` (`cod_producto`);

--
-- Indices de la tabla `retirada_venta`
--
ALTER TABLE `retirada_venta`
  ADD PRIMARY KEY (`cod_mov`),
  ADD KEY `cod_producto` (`cod_producto`),
  ADD KEY `almacen_codigo_almacen` (`almacen`);

--
-- Indices de la tabla `traslado_almacen`
--
ALTER TABLE `traslado_almacen`
  ADD PRIMARY KEY (`cod_mov`),
  ADD KEY `cod_producto` (`cod_producto`),
  ADD KEY `almacen_origen` (`almacen`),
  ADD KEY `almacen_destino` (`almacen_destino`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`cod_usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `almacen`
--
ALTER TABLE `almacen`
  MODIFY `codigo_almacen` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `cod_categoria` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `ingreso_producto`
--
ALTER TABLE `ingreso_producto`
  MODIFY `cod_mov` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `kardex`
--
ALTER TABLE `kardex`
  MODIFY `tipo_movimiento` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `cod_producto` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `reporte_irregularidad`
--
ALTER TABLE `reporte_irregularidad`
  MODIFY `cod_reporte` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `retirada_venta`
--
ALTER TABLE `retirada_venta`
  MODIFY `cod_mov` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `traslado_almacen`
--
ALTER TABLE `traslado_almacen`
  MODIFY `cod_mov` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `cod_usuario` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `ingreso_producto`
--
ALTER TABLE `ingreso_producto`
  ADD CONSTRAINT `ingreso_producto_ibfk_1` FOREIGN KEY (`cod_producto`) REFERENCES `producto` (`cod_producto`);

--
-- Filtros para la tabla `kardex`
--
ALTER TABLE `kardex`
  ADD CONSTRAINT `kardex_ibfk_1` FOREIGN KEY (`codigo_mov`) REFERENCES `traslado_almacen` (`cod_mov`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`categoria_cod_categoria`) REFERENCES `categoria` (`cod_categoria`),
  ADD CONSTRAINT `producto_ibfk_2` FOREIGN KEY (`almacen_codigo_almacen`) REFERENCES `almacen` (`codigo_almacen`);

--
-- Filtros para la tabla `reporte_irregularidad`
--
ALTER TABLE `reporte_irregularidad`
  ADD CONSTRAINT `reporte_irregularidad_ibfk_1` FOREIGN KEY (`almacen`) REFERENCES `almacen` (`codigo_almacen`),
  ADD CONSTRAINT `reporte_irregularidad_ibfk_2` FOREIGN KEY (`cod_producto`) REFERENCES `producto` (`cod_producto`);

--
-- Filtros para la tabla `retirada_venta`
--
ALTER TABLE `retirada_venta`
  ADD CONSTRAINT `retirada_venta_ibfk_1` FOREIGN KEY (`cod_producto`) REFERENCES `producto` (`cod_producto`),
  ADD CONSTRAINT `retirada_venta_ibfk_2` FOREIGN KEY (`almacen`) REFERENCES `almacen` (`codigo_almacen`);

--
-- Filtros para la tabla `traslado_almacen`
--
ALTER TABLE `traslado_almacen`
  ADD CONSTRAINT `traslado_almacen_ibfk_1` FOREIGN KEY (`cod_producto`) REFERENCES `producto` (`cod_producto`),
  ADD CONSTRAINT `traslado_almacen_ibfk_2` FOREIGN KEY (`almacen`) REFERENCES `almacen` (`codigo_almacen`),
  ADD CONSTRAINT `traslado_almacen_ibfk_3` FOREIGN KEY (`almacen_destino`) REFERENCES `almacen` (`codigo_almacen`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
