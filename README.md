# Tugas Praktikum 5 DPBO — Person Data

## JANJI

Saya Raffi Akbar Ardiansyah dengan NIM 2511604 mengerjakan Tugas Praktikum 5 pada Mata Kuliah Desain dan Pemrograman Berorientasi Objek (DPBO) untuk keberkahan-Nya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

## Fungsi Program

Program ini dibuat untuk mengolah data penduduk atau warga dengan ruang lingkup kampus JokoWowo. Program dikembangkan menggunakan bahasa pemrograman Java dengan antarmuka grafis (GUI) Java Swing dan database MySQL sebagai tempat penyimpanan data.

Program ini merupakan pengembangan dari tugas praktikum sebelumnya. Pada TP5, penyimpanan data yang sebelumnya dikelola melalui program diganti menggunakan database MySQL agar data dapat disimpan dan diakses melalui operasi SQL.

## Fitur Program

Program ini memiliki beberapa fitur utama, yaitu:

1. **Create (Tambah Data)**  
   Pengguna dapat menambahkan data baru dengan mengisi ID, nama, tahun lahir, kategori, dan jabatan sesuai dengan kolom yang tersedia pada antarmuka program.

2. **Read (Tampilkan Data)**  
   Program menampilkan data yang tersimpan di database MySQL ke dalam tabel sehingga pengguna dapat melihat daftar data yang tersedia.

3. **Update (Ubah Data)**  
   Pengguna dapat memilih salah satu baris pada tabel untuk mengubah data yang sudah tersimpan.

4. **Delete (Hapus Data)**  
   Pengguna dapat memilih data yang ingin dihapus. Sebelum data dihapus, program menampilkan konfirmasi untuk memastikan pengguna benar-benar ingin menghapus data tersebut.

5. **Validasi Input**  
   Program menampilkan pesan kesalahan apabila terdapat kolom input yang belum diisi ketika pengguna menambahkan atau memperbarui data.

6. **Validasi ID Duplikat**  
   Program menampilkan pesan kesalahan apabila pengguna mencoba menambahkan data dengan ID yang sudah digunakan.


## Desain Class

### 1. Class `Person`

Class `Person` digunakan untuk merepresentasikan data seseorang yang akan disimpan dan ditampilkan oleh program. Class ini memiliki atribut:

- `id` (`String`): identitas unik setiap data.
- `nama` (`String`): nama orang.
- `tahunLahir` (`int`): tahun lahir orang.
- `kategori` (`String`): kategori orang.
- `jabatan` (`String`): jabatan atau peran orang.

Atribut digunakan untuk menampung informasi setiap data. Class `Person` membantu mengelompokkan informasi tersebut agar dapat digunakan oleh program secara terstruktur.

### 2. Class `Database`

Class `Database` bertugas mengatur koneksi antara aplikasi Java dan database MySQL menggunakan JDBC. Koneksi ini digunakan oleh program untuk menjalankan perintah SQL, seperti menambahkan, mengambil, memperbarui, dan menghapus data.

### 3. Class `PeopleMenu`

Class `PeopleMenu` merupakan bagian utama dari antarmuka program yang dibuat menggunakan Java Swing. Class ini mengatur tampilan form, tombol, tabel data, validasi input, serta proses CRUD yang terhubung dengan database melalui class `Database`.

## Alur Program

Alur program dimulai ketika pengguna membuka aplikasi. Program menampilkan antarmuka yang berisi form input dan tabel untuk menampilkan data yang tersimpan di database MySQL.

Pengguna dapat mengisi data berupa ID, nama, tahun lahir, kategori, dan jabatan. Setelah data diisi, pengguna dapat menekan tombol **Tambah** untuk menyimpan data ke database. Sebelum penyimpanan dilakukan, program memeriksa apakah semua kolom wajib sudah diisi dan apakah ID yang dimasukkan sudah digunakan. Jika terdapat input kosong atau ID duplikat, program akan menampilkan pesan kesalahan.

Data yang berhasil disimpan akan ditampilkan pada tabel. Pengguna juga dapat memilih salah satu baris tabel untuk melihat data yang akan diubah atau dihapus.

Ketika pengguna memilih data, pengguna dapat menekan tombol **Ubah** untuk memperbarui data tersebut. Program akan memvalidasi input sebelum perubahan disimpan ke database.

Jika pengguna ingin menghapus data, pengguna dapat memilih baris yang sesuai lalu menekan tombol **Hapus**. Program menampilkan dialog konfirmasi terlebih dahulu. Data hanya dihapus apabila pengguna menyetujui konfirmasi tersebut.

Tombol **Bersihkan** digunakan untuk mengosongkan kembali form input agar pengguna dapat memasukkan data baru atau membatalkan proses pengisian.

Dengan demikian, seluruh proses pengelolaan data dilakukan melalui antarmuka Java Swing, sedangkan penyimpanan dan perubahan data dikelola oleh database MySQL.

## Desain Antarmuka

Antarmuka program dibuat menggunakan Java Swing GUI Designer dengan file `PeopleMenu.form`.

Komponen yang digunakan antara lain:

- **JLabel** untuk menampilkan judul dan keterangan setiap input.
- **JTextField** untuk mengisi ID, nama, dan tahun lahir.
- **JComboBox** untuk memilih kategori dan jabatan.
- **JButton** untuk menjalankan fungsi tambah, ubah, hapus, dan bersihkan.
- **JTable** untuk menampilkan data yang tersimpan di database.
- **JScrollPane** untuk memungkinkan pengguna menggulir tabel apabila data yang ditampilkan banyak.
- **JOptionPane** untuk menampilkan pesan validasi, kesalahan, dan konfirmasi penghapusan.

## Dokumentasi

### Tambah Data


![Pesan data pegawai berhasil ditambahkan](Dokumentasi/Tambah%20Berhasil.png)


![Video proses tambah data](Dokumentasi/Tambah%20Data.gif)

### Ubah Data

![Pesan data pegawai berhasil diubah](Dokumentasi/Update%20Berhasil.png)

![Video proses ubah data](Dokumentasi/Update%20Data.gif)

### Hapus Data

![Dialog konfirmasi penghapusan data](Dokumentasi/konfirmasi%20Delete%20.png)

![Pesan data pegawai berhasil dihapus](Dokumentasi/Delete%20%20berhasil.png)

![Video proses hapus data](Dokumentasi/Hapus%20Data.gif)


### Validasi Input

![Pesan validasi ketika input belum lengkap](Dokumentasi/Error%20jika%20kosong.png)

![Pesan validasi ketika ID sudah digunakan](Dokumentasi/Error%20jika%20sama.png)
