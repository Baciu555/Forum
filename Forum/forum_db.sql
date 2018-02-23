-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 24 Lut 2018, 00:01
-- Wersja serwera: 10.1.25-MariaDB
-- Wersja PHP: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `forum_db`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `comments`
--

CREATE TABLE `comments` (
  `id` bigint(20) NOT NULL,
  `content` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `entry_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `thread_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `comments`
--

INSERT INTO `comments` (`id`, `content`, `entry_date`, `thread_id`, `user_id`) VALUES
(5, 'wyslij cv na email praca@gmail.com', '2018-02-14 20:55:58', 1, 3),
(6, 'Donec at ornare nisi. Nullam sed ante ligula. Mauris ac ultricies arcu.', '2018-02-14 21:31:24', 2, 5),
(7, 'Suspendisse faucibus velit at nisl maximus, at tincidunt dolor finibus. Donec non viverra tellus. Integer faucibus eu orci a dictum. Nam dignissim pellentesque interdum.', '2018-02-14 21:31:53', 1, 5),
(8, 'tak', '2018-02-14 23:02:47', 13, 4),
(9, 'testowy komentarz', '2018-02-23 11:48:27', 15, 3);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `role`
--

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `section`
--

CREATE TABLE `section` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `section`
--

INSERT INTO `section` (`id`, `name`) VALUES
(1, 'praca'),
(2, 'muzyka'),
(3, 'off-topic'),
(4, 'sport');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `tags`
--

CREATE TABLE `tags` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `tags`
--

INSERT INTO `tags` (`id`, `name`) VALUES
(1, 'praca'),
(2, 'muzyka'),
(3, 'rozrywka'),
(4, 'pi?ka no?na'),
(5, 'koszykówka'),
(6, 'siatkówka'),
(7, 'hip hop'),
(8, 'pop'),
(9, 'rock');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `thread`
--

CREATE TABLE `thread` (
  `id` bigint(20) NOT NULL,
  `content` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `entry_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `subject` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `views_count` bigint(20) DEFAULT '0',
  `section_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `thread`
--

INSERT INTO `thread` (`id`, `content`, `entry_date`, `subject`, `views_count`, `section_id`, `user_id`) VALUES
(1, 'szukam pracy na terenie rzeszowa', '2018-02-14 18:01:47', 'szukam pracy', 43, 1, 3),
(2, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque rhoncus tortor non nisl rutrum, in tempus nibh molestie. Morbi ut porttitor ipsum.', '2018-02-14 21:30:34', 'jakiej muzyki słuchacie?', 2, 2, 5),
(3, 'Curabitur ultrices bibendum felis at vestibulum. Etiam non nunc in metus venenatis scelerisque. Vestibulum lacus libero, pharetra ut tortor in, pretium fermentum sem.', '2018-02-14 22:03:17', 'blablabla', 2, 3, 5),
(4, 'Lorem ipsum dolor sit amet', '2018-02-14 22:04:46', 'Lorem ipsum dolor sit amet', 1, 3, 5),
(5, ' Nullam eu viverra dolor.', '2018-02-14 22:04:56', ' Nullam eu viverra dolor.', 0, 3, 5),
(6, ' Nullam sed ante ligula.', '2018-02-14 22:05:09', ' Nullam sed ante ligula.', 0, 3, 5),
(7, ' Nullam sed ante ligula.', '2018-02-14 22:05:25', ' Nullam sed ante ligula.', 0, 3, 5),
(8, ' Nullam sed ante ligula.', '2018-02-14 22:05:29', ' Nullam sed ante ligula.', 1, 3, 5),
(9, ' Nullam sed ante ligula.', '2018-02-14 22:05:33', ' Nullam sed ante ligula.', 0, 3, 5),
(10, ' Nullam sed ante ligula.', '2018-02-14 22:05:38', ' Nullam sed ante ligula.', 0, 3, 5),
(11, ' Nullam sed ante ligula.', '2018-02-14 22:05:43', ' Nullam sed ante ligula.', 0, 3, 5),
(12, ' Nullam sed ante ligula.', '2018-02-14 22:05:49', ' Nullam sed ante ligula.', 0, 3, 5),
(13, 'siemanko uprawiacie jakies sporty?', '2018-02-14 23:02:26', 'sporty', 2, 4, 3),
(15, 'testowy wpis', '2018-02-23 11:47:14', 'test', 5, 1, 3),
(16, '<script>alert(\'Hello World\');</script>', '2018-02-23 12:05:22', 'testujemy', 2, 3, 7);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `thread_tag`
--

CREATE TABLE `thread_tag` (
  `thread_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `thread_tag`
--

INSERT INTO `thread_tag` (`thread_id`, `tag_id`) VALUES
(1, 1),
(2, 2),
(2, 7),
(2, 8),
(2, 9),
(3, 3),
(4, 3),
(5, 3),
(6, 3),
(7, 3),
(8, 2),
(9, 2),
(10, 3),
(11, 3),
(12, 3),
(13, 5),
(13, 6),
(15, 3),
(15, 5),
(16, 3),
(16, 8),
(16, 9);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `avatar_path` varchar(255) COLLATE utf8_polish_ci DEFAULT 'default-avatar.jpg',
  `ban_count` int(11) DEFAULT '0',
  `ban_expire` datetime DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `join_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `password` varchar(250) COLLATE utf8_polish_ci DEFAULT NULL,
  `username` varchar(30) COLLATE utf8_polish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`id`, `avatar_path`, `ban_count`, `ban_expire`, `email`, `join_date`, `password`, `username`) VALUES
(3, 'default-avatar.jpg', 0, NULL, 'ananas@wp.pl', '2018-02-14 17:50:59', '$2a$10$HfNhFNxIJWF.3M8lUHkjWe1kjRNj4IXsX427/UrEUeqjtZtWs7HKi', 'ananas'),
(4, 'default-avatar.jpg', 0, NULL, '123@wp.pl', '2018-02-14 18:13:31', '$2a$10$UbOlRS4BvXtNt6VAJgcapudqBZs3juBXPQulGCZwx1hK2lXW0jO52', 'bateria'),
(5, 'default-avatar.jpg', 0, NULL, 'kosmos@gmail.com', '2018-02-14 20:59:25', '$2a$10$Wpjy.Gce889TCPZtsM17ReRW3yWFHEl5VaYrhwN7ZupMzZNMQ4AM6', 'kosmos'),
(7, 'default-avatar.jpg', 0, NULL, 'adam@wp.pl', '2018-02-23 12:03:31', '$2a$10$e8f4cSISa.lvIFB1h5gI1.R72t8/Yt6KcEdS0YzF/vw.1YJj6qukm', 'adam');

--
-- Wyzwalacze `users`
--
DELIMITER $$
CREATE TRIGGER `set_default_user_role` AFTER INSERT ON `users` FOR EACH ROW INSERT into user_role(user_id, role_id) values(new.id, 1)
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user_role`
--

CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `user_role`
--

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(3, 1),
(3, 2),
(4, 1),
(5, 1),
(7, 1);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKoaf5o006i0reeo5hb3jxqy136` (`thread_id`),
  ADD KEY `FK8omq0tc18jd43bu5tjh6jvraq` (`user_id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `section`
--
ALTER TABLE `section`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tags`
--
ALTER TABLE `tags`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `thread`
--
ALTER TABLE `thread`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKd3j1k5s93ua2lfrw2csgdxelt` (`section_id`),
  ADD KEY `FKog1bxdbfvxsu3lgrvrwv2o0lp` (`user_id`),
  ADD KEY `subject_idx` (`subject`),
  ADD KEY `content_idx` (`content`);

--
-- Indexes for table `thread_tag`
--
ALTER TABLE `thread_tag`
  ADD PRIMARY KEY (`thread_id`,`tag_id`),
  ADD KEY `FKe41yv1567c2i2rtkk9nlye01v` (`tag_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `comments`
--
ALTER TABLE `comments`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT dla tabeli `role`
--
ALTER TABLE `role`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT dla tabeli `section`
--
ALTER TABLE `section`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT dla tabeli `tags`
--
ALTER TABLE `tags`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT dla tabeli `thread`
--
ALTER TABLE `thread`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `FK8omq0tc18jd43bu5tjh6jvraq` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKoaf5o006i0reeo5hb3jxqy136` FOREIGN KEY (`thread_id`) REFERENCES `thread` (`id`);

--
-- Ograniczenia dla tabeli `thread`
--
ALTER TABLE `thread`
  ADD CONSTRAINT `FKd3j1k5s93ua2lfrw2csgdxelt` FOREIGN KEY (`section_id`) REFERENCES `section` (`id`),
  ADD CONSTRAINT `FKog1bxdbfvxsu3lgrvrwv2o0lp` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Ograniczenia dla tabeli `thread_tag`
--
ALTER TABLE `thread_tag`
  ADD CONSTRAINT `FK3acomray2ub88i3a97lo09wdq` FOREIGN KEY (`thread_id`) REFERENCES `thread` (`id`),
  ADD CONSTRAINT `FKe41yv1567c2i2rtkk9nlye01v` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`);

--
-- Ograniczenia dla tabeli `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  ADD CONSTRAINT `FKj345gk1bovqvfame88rcx7yyx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
