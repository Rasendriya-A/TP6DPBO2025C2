# Tugas Praktikum 6

## Janji
Saya Rasendriya Andhika dengan NIM 2305309 mengerjakan Tugas Praktikum 6 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahan-Nya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

## Desain Program
- Program ini terdiri dari 5 class utama, yaitu App, Start, FlappyBird, Player, dan Pipe. Program ini dibuat menggunakan bahasa pemrograman Java dengan library Swing untuk antarmuka grafis (Graphical User Interface / GUI), dan didesain sebagai permainan Flappy Bird sederhana.

### 1. Class App
- Class ini berfungsi sebagai entry point dari program, tempat `method main()` dijalankan.
- Pada saat program dijalankan, class ini akan memanggil `class Start` menggunakan `SwingUtilities.invokeLater()`, agar GUI dijalankan di thread yang sesuai.
- Fungsinya hanya untuk menjalankan tampilan awal aplikasi (welcome screen) sebelum berpindah ke permainan.

### 2. Class Start
- Class `Start` merupakan tampilan awal dari aplikasi berupa window selamat datang.
- Terdapat `JLabel` untuk menampilkan teks sambutan dan `JButton` untuk memulai permainan.
- Saat tombol "Mulai Bermain" diklik, window ini akan ditutup, dan akan dibuat `JFrame baru` untuk menjalankan permainan (FlappyBird).
- Class ini menggunakan layout manager `BorderLayout` dan mengatur tampilan secara sederhana.

### 3. Class FlappyBird
- Class ini merupakan `inti` dari permainan dan merupakan `turunan dari JPanel.`
- Berfungsi sebagai `canvas permainan` tempat semua elemen seperti background, player (burung), dan pipa digambar.
- Menangani logika permainan seperti:
  - Gerakan burung
  - Pembuatan pipa secara acak
  - Deteksi tabrakan
  - Perhitungan skor
  - Game over dan restart
- Menggunakan dua Timer:
  - `gameLoop` untuk animasi dan pergerakan frame per frame (60 FPS).
  - `pipesCooldown` untuk membuat pipa setiap 1.5 detik.
- Menerapkan event listener untuk keyboard agar pemain bisa mengontrol burung dengan tombol SPACE dan R untuk restart.

### 4. Class Player
- Class ini berfungsi sebagai model `objek pemain`, yaitu burung.
- Menyimpan `atribut posisi` (posX, posY), `ukuran` (width, height), `gambar` (Image), dan `kecepatan vertikal` (velocityY).
- Menyediakan `getter dan setter` untuk setiap atribut.
- Digunakan oleh class FlappyBird untuk `menggambar dan mengatur logika gerak pemain`.

### 5. Class Pipe
- Class ini merupakan `model objek pipa`, baik pipa atas maupun pipa bawah.
- Menyimpan atribut posisi, ukuran, gambar, dan kecepatan horizontal (velocityX).
- Memiliki properti tambahan `passed` untuk mencatat apakah pipa sudah dilewati oleh pemain (untuk penghitungan skor).
- Digunakan oleh class FlappyBird untuk menggambar pipa dan mengecek tabrakan dengan pemain.

## Penjelasan Alur
### 1. Inisialisasi
- Proses:
  - Program dijalankan melalui `Class App`, yang akan membuka jendela awal (welcome screen) menggunakan `Class Start`.
  - Pada tampilan awal, pengguna akan melihat tombol "Mulai Bermain".
  - Setelah tombol diklik, jendela Start akan ditutup dan jendela permainan (FlappyBird) akan muncul.
  - Di dalam Class FlappyBird, dilakukan:
    - Inisialisasi Player (burung).
    - Inisialisasi list pipes untuk menyimpan objek Pipe (pipa).
    - Memulai dua Timer: `gameLoop` (untuk update per frame) dan `pipesCooldown` (untuk membuat pipa baru).
- Data Awal:
  - Player (burung) dimulai dari posisi X dan Y tertentu.
  - Belum ada pipa pada saat permainan baru dimulai. Pipa akan dibuat secara berkala.

### 2. Gameplay (Selama Permainan Berlangsung)
- Proses:
  - Timer gameLoop akan terus memanggil `repaint()` setiap frame (60 FPS).
  - Di setiap frame:
    - Burung akan jatuh secara gravitasi jika tidak ditekan.
    - Pipa akan bergerak dari kanan ke kiri.
    - Jika pipa melewati pemain tanpa tabrakan, skor akan bertambah.
  - Pemain dapat menekan tombol `SPACE` untuk membuat burung melompat ke atas.
  - Permainan akan terus berjalan selama `tidak ada tabrakan`.
- Validasi & Error Handling:
  - Tabrakan diperiksa di setiap frame:
    - Jika burung menabrak pipa (menggunakan Rectangle untuk hitbox), atau jatuh ke bawah layar, maka permainan berakhir.
  - Hasil:
    - Skor pemain akan terus bertambah setiap kali berhasil melewati satu pasang pipa.

### 3. Game Over
- Proses:
  - Ketika burung menabrak pipa atau jatuh, maka kondisi `gameOver` = true.
  - Timer permainan (`gameLoop` dan `pipesCooldown`) akan dihentikan.
  - Muncul `JOptionPane.showMessageDialog()` yang menampilkan skor akhir pemain dan memberi tahu bahwa permainan telah berakhir.
  - Pemain dapat menekan tombol `R` untuk memulai ulang permainan.
- Validasi & Error Handling:
  - Setelah game over, semua input selain tombol R akan diabaikan.
- Hasil:
  - Permainan di-reset dan kembali ke keadaan awal (tanpa harus keluar dari aplikasi).

### 4. Restart
- Proses:
  - Ketika pengguna menekan tombol `R`, method `restartGame()` dipanggil.
  - Burung (player) akan `direset ke posisi awal`.
  - Skor `direset ke 0`.
  - List pipes dikosongkan.
  - Timer diaktifkan kembali untuk memulai permainan dari awal.
- Hasil:
  - Pengguna dapat langsung bermain kembali tanpa perlu kembali ke halaman awal.

### Error Handling (Secara Keseluruhan)
- Deteksi Tabrakan:
  - Menggunakan Rectangle untuk memeriksa tabrakan antara Player dan Pipe.
  - Jika terdapat interseksi, maka gameOver diaktifkan.
- Batas Layar:
  - Permainan juga akan berakhir jika burung melewati batas bawah layar.
- User Feedback:
  - Pesan Game Over ditampilkan melalui JOptionPane.
  - Skor pemain juga ditampilkan sebagai feedback langsung atas performa permainan.

## Dokumentasi
https://github.com/user-attachments/assets/f62b7129-311c-4c7d-a41e-ebc4264f92ca


