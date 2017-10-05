-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Czas generowania: 02 Paź 2017, 20:52
-- Wersja serwera: 10.1.16-MariaDB
-- Wersja PHP: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
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
  `content` text NOT NULL,
  `entry_date` datetime DEFAULT NULL,
  `points` int(11) DEFAULT NULL,
  `thread_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `comments`
--

INSERT INTO `comments` (`id`, `content`, `entry_date`, `points`, `thread_id`, `user_id`) VALUES
(1, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.', NULL, NULL, 1, 4),
(2, 'Aliquam efficitur at dui a luctus. Suspendisse in erat dictum, hendrerit ex ut, rhoncus mi. Aenean at magna vel lorem rhoncus dapibus. Curabitur orci ligula, posuere eu metus at, tincidunt varius libero. Donec efficitur ornare porta. Aenean consequat quam eget auctor cursus. Praesent laoreet pulvinar massa, nec commodo est pharetra vitae.', NULL, NULL, 1, 5),
(3, 'Aliquam efficitur at dui a luctus. Suspendisse in erat dictum, hendrerit ex ut, rhoncus mi. Aenean at magna vel lorem rhoncus dapibus. Curabitur orci ligula, posuere eu metus at, tincidunt varius libero. Donec efficitur ornare porta. Aenean consequat quam eget auctor cursus. Praesent laoreet pulvinar massa, nec commodo est pharetra vitae.', NULL, NULL, 1, 6),
(4, 'Nulla lobortis hendrerit volutpat. Maecenas vel blandit leo. Vivamus a augue in justo condimentum fringilla. Donec ut metus eu lectus lacinia molestie in vel quam. Maecenas faucibus velit nec augue efficitur mattis. Pellentesque tempor ornare urna. Morbi nec semper felis, vitae laoreet velit. Cras arcu nunc, pharetra vel molestie et, ultrices at lectus.', NULL, NULL, 6, 6);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `section`
--

CREATE TABLE `section` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `section`
--

INSERT INTO `section` (`id`, `name`) VALUES
(1, 'Praca'),
(2, 'Sport'),
(3, 'Rozrywka'),
(4, 'Gry'),
(5, 'Turystyka');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `tags`
--

CREATE TABLE `tags` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `tags`
--

INSERT INTO `tags` (`id`, `name`) VALUES
(1, 'sport'),
(2, 'pi?ka no?na'),
(3, 'koszykówka'),
(4, 'siatkówka'),
(5, 'programowanie'),
(6, 'gotowanie'),
(7, 'zarobki'),
(8, 'dyskoteka'),
(9, 'impreza'),
(10, 'counter-strike'),
(11, 'league of legends'),
(12, 'dota'),
(13, 'wow'),
(14, 'bieszczady'),
(15, 'pieniny'),
(16, 'tatry'),
(17, 'chorwacja'),
(18, 'gry'),
(19, 'praca'),
(20, 'piwo');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `thread`
--

CREATE TABLE `thread` (
  `id` bigint(20) NOT NULL,
  `content` text NOT NULL,
  `entry_date` datetime DEFAULT NULL,
  `subject` varchar(255) NOT NULL,
  `views_count` bigint(20) NOT NULL,
  `section_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `thread`
--

INSERT INTO `thread` (`id`, `content`, `entry_date`, `subject`, `views_count`, `section_id`, `user_id`) VALUES
(1, 'Witam, szukam pracownika do restauracji (kucharza) na terenie Rzeszowa. Stawka godzinowa 15z?/h, umowa o prace.\r\nWi?cej informacji pod nr telefonu 123456789.', '2017-10-02 19:27:00', 'Szukam pracownika', 16, 1, 2),
(2, 'Poleci kto? bar w Krakowie gdzie mo?na napi? si? dobrego piwa ?', '2017-10-02 19:30:58', 'Piwo Kraków', 1, 3, 2),
(3, 'Zagrajmy w gre gdzie kazda osoba pisze slowo zaczynajace sie na ostatnia litere poprzedniego wpisu.\r\nJa zaczynam: \r\nbanan', '2017-10-02 19:34:32', 'Ostatnia litera', 1, 4, 2),
(4, 'Czesc uprawiacie jakis sport? Piszie w komentarzach :)\r\nJa uprawiam koszykowke :D', '2017-10-02 19:41:16', 'Sport', 0, 2, 3),
(5, 'Siema szukam w osób na wyjazd w bieszczady. Wyjazd w przysz?y weekend.\r\nWiecej info pod nr 987654321', '2017-10-02 19:44:00', 'Wypad w bieszczady', 4, 5, 3),
(6, 'Curabitur eget sodales ante. Mauris efficitur imperdiet massa, at fringilla nibh convallis sit amet. Vestibulum sodales orci a porttitor mattis. Nunc volutpat sodales augue, vel porttitor mi cursus sed. Donec vel eros vel felis semper gravida ac nec nisl. In finibus felis sed nunc aliquet, quis laoreet erat dictum. Proin sollicitudin ultricies nisi ac imperdiet. Quisque aliquet hendrerit erat, eu pharetra mauris tempor sit amet. Vestibulum porta lectus non nisl vestibulum dictum.', '2017-10-02 20:06:50', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. ', 3, 2, 4),
(7, 'Nulla lobortis hendrerit volutpat. Maecenas vel blandit leo. Vivamus a augue in justo condimentum fringilla. Donec ut metus eu lectus lacinia molestie in vel quam. Maecenas faucibus velit nec augue efficitur mattis. Pellentesque tempor ornare urna. Morbi nec semper felis, vitae laoreet velit. Cras arcu nunc, pharetra vel molestie et, ultrices at lectus.', '2017-10-02 20:07:26', 'Mauris efficitur imperdiet massa, at fringilla nibh convallis sit amet. ', 0, 5, 4);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `thread_tag`
--

CREATE TABLE `thread_tag` (
  `thread_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `thread_tag`
--

INSERT INTO `thread_tag` (`thread_id`, `tag_id`) VALUES
(1, 6),
(1, 19),
(2, 20),
(3, 18),
(4, 1),
(4, 3),
(4, 18),
(5, 14),
(6, 5),
(6, 10),
(6, 12),
(7, 6),
(7, 8),
(7, 10);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `avatar_path` varchar(255) DEFAULT NULL,
  `ban_count` int(11) DEFAULT NULL,
  `ban_expire` datetime DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `join_date` datetime DEFAULT NULL,
  `password` varchar(250) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `username` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`id`, `avatar_path`, `ban_count`, `ban_expire`, `email`, `join_date`, `password`, `permission`, `username`) VALUES
(1, 'default-avatar.jpg', 0, NULL, 'mail@gmail.com', '2017-10-02 19:05:29', 'haslo', 'user', 'user'),
(2, 'default-avatar.jpg', 0, NULL, 'admin@wp.pl', '2017-10-02 19:07:49', 'admin', 'admin', 'admin'),
(3, 'default-avatar.jpg', 0, NULL, 'baton@gmail.com', '2017-10-02 19:37:32', '123', 'user', 'baton'),
(4, 'default-avatar.jpg', 0, NULL, 'bu?ka@wp.pl', '2017-10-02 19:52:50', '123', 'user', 'bu?ka'),
(5, 'default-avatar.jpg', 0, NULL, 'ananas@gmail.com', '2017-10-02 20:07:49', '123', 'user', 'ananas'),
(6, 'default-avatar.jpg', 0, NULL, 'bateria@gmail.com', '2017-10-02 20:28:41', 'bateria', 'user', 'bateria');

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
  ADD KEY `FKog1bxdbfvxsu3lgrvrwv2o0lp` (`user_id`);

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
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `comments`
--
ALTER TABLE `comments`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT dla tabeli `section`
--
ALTER TABLE `section`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT dla tabeli `tags`
--
ALTER TABLE `tags`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
--
-- AUTO_INCREMENT dla tabeli `thread`
--
ALTER TABLE `thread`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
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

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
