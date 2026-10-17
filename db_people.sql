CREATE DATABASE IF NOT EXISTS db_people
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE db_people;

CREATE TABLE IF NOT EXISTS person (
    id VARCHAR(20) NOT NULL,
    nama VARCHAR(100) NOT NULL,
    tahun_lahir INT NOT NULL,
    kategori VARCHAR(30) NOT NULL,
    jabatan VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

-- Data contoh dari TP4, dimasukkan satu kali saat tabel masih kosong.
INSERT IGNORE INTO person (id, nama, tahun_lahir, kategori, jabatan) VALUES
('R001', 'Nabil Ngamprah', 1998, 'Full-time', 'Koki'),
('R002', 'Adib Bekasi', 2001, 'Part-time', 'Kasir'),
('R003', 'Syafiq Pemalang', 2003, 'Magang', 'Pelayan'),
('R004', 'Dewi Lestari', 1999, 'Full-time', 'Manajer'),
('R005', 'Rizky Ramadhan', 2000, 'Full-time', 'Barista'),
('R006', 'Nadia Putri', 2002, 'Part-time', 'Pelayan'),
('R007', 'Fajar Nugroho', 1997, 'Full-time', 'Koki'),
('R008', 'Intan Permata', 2001, 'Magang', 'Kasir'),
('R009', 'Dimas Firmansyah', 1999, 'Part-time', 'Barista'),
('R010', 'Aulia Rahma', 2000, 'Full-time', 'Pelayan'),
('R011', 'Yoga Kurniawan', 1996, 'Full-time', 'Manajer'),
('R012', 'Maya Anggraini', 2002, 'Magang', 'Barista'),
('R013', 'Arif Setiawan', 1998, 'Part-time', 'Koki'),
('R014', 'Putri Amelia', 2003, 'Magang', 'Kasir'),
('R015', 'Rafi Hidayat', 2001, 'Full-time', 'Pelayan'),
('R016', 'Citra Wulandari', 1997, 'Part-time', 'Manajer'),
('R017', 'Bagas Maulana', 2000, 'Full-time', 'Barista'),
('R018', 'Laila Safitri', 1999, 'Magang', 'Pelayan'),
('R019', 'Kevin Wijaya', 2002, 'Part-time', 'Kasir'),
('R020', 'Nisa Oktaviani', 1998, 'Full-time', 'Koki');

-- Memastikan tiga nama ini ikut berubah jika database sudah pernah diimpor.
UPDATE person SET nama = CASE id
    WHEN 'R001' THEN 'Nabil Ngamprah'
    WHEN 'R002' THEN 'Adib Bekasi'
    WHEN 'R003' THEN 'Syafiq Pemalang'
    ELSE nama
END
WHERE id IN ('R001', 'R002', 'R003');
