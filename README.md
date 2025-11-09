# ☕ CoffeeShopThreadJDBC

## 📘 Deskripsi
Proyek ini merupakan implementasi sederhana dari **konsep Thread (Multithreading)** dan **JDBC (Java Database Connectivity)** dalam bahasa pemrograman Java.  
Simulasi ini menggambarkan kegiatan beberapa *barista* di sebuah kedai kopi kampus yang memproses pesanan pelanggan secara paralel.  
Setiap barista bekerja sebagai *thread* terpisah, sementara seluruh data pesanan disimpan ke dalam **database MySQL** melalui koneksi JDBC.

---

## 🎯 Tujuan Pembelajaran
Proyek ini bertujuan untuk membantu mahasiswa memahami dua konsep penting dalam pemrograman Java:
1. **Multithreading:** Menjalankan beberapa proses secara bersamaan (parallel execution).  
2. **JDBC:** Menghubungkan program Java dengan database relasional seperti MySQL.

---

## 🧩 Konsep Utama dalam Program

| Konsep | Penjelasan |
|--------|-------------|
| **Thread (Multithreading)** | Tiap *barista* dijalankan sebagai thread menggunakan `ExecutorService`, agar pesanan pelanggan dapat diproses bersamaan. |
| **Sinkronisasi Output** | Menggunakan `synchronized(System.out)` agar hasil cetak dari tiap thread tidak tumpang tindih di konsol. |
| **Koneksi Database (JDBC)** | Program menggunakan `DriverManager` untuk terhubung ke database MySQL dan menjalankan perintah `INSERT` serta `SELECT`. |
| **PreparedStatement** | Digunakan untuk menyisipkan data ke database dengan aman dan efisien. |
| **Pembersihan Data Otomatis** | Sebelum setiap simulasi dijalankan, program akan menghapus data lama dari tabel agar hasil laporan tidak menumpuk. |

---

## ⚙️ Struktur Program

```
src/
│
├── CoffeeShop.java        # File utama yang berisi logika Thread dan JDBC
└── lib/
    └── mysql-connector-j-9.5.0.jar   # Library koneksi JDBC ke MySQL
```

---

## 🗄️ Struktur Database

perintah SQL di MySQL atau phpMyAdmin:

```sql
CREATE DATABASE coffee_shop;
USE coffee_shop;

CREATE TABLE coffee_orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    barista VARCHAR(50),
    customer VARCHAR(50),
    order_name VARCHAR(50)
);
```

---

## 🚀 Cara Menjalankan Program

1. Pastikan **MySQL** sudah aktif.  
2. Pastikan file **`mysql-connector-j-9.5.0.jar`** sudah diletakkan di folder `lib`.  
3. Jalankan program dengan salah satu cara berikut:
   - Klik dua kali file `run.bat`, atau  
   - Jalankan lewat terminal:
     ```bash
     .\run.bat
     ```
4. Program akan:
   - Menghapus data lama dari database  
   - Menjalankan 4 barista secara paralel  
   - Menampilkan hasil pekerjaan masing-masing barista  
   - Menampilkan tabel laporan akhir pesanan

---

## 💡 Contoh Output

```
===========================================
           KEDAI KOPI KAMPUS
===========================================
Semua barista sedang menyiapkan kopi...

Data lama dihapus. Memulai sesi baru...


-------------------------------------------
   Barista Dahlia Mulai Bekerja
-------------------------------------------
Pelanggan : Belle
Pesanan   : Espresso
Status    : Dahlia sedang menyiapkan kopi...
Selesai   : Dahlia telah membuat Espresso untuk Belle
-------------------------------------------

-------------------------------------------
   Barista Anggi Mulai Bekerja
-------------------------------------------
Pelanggan : Ariel
Pesanan   : Cappuccino
Status    : Anggi sedang menyiapkan kopi...
Selesai   : Anggi telah membuat Cappuccino untuk Ariel
-------------------------------------------

Laporan Akhir Pemesanan Kopi
+------+----------------+----------------+----------------+
| ID   | Barista        | Pelanggan      | Pesanan        |
+------+----------------+----------------+----------------+
| 1    | Anggi          | Ariel          | Cappuccino     |
| 2    | Dahlia         | Belle          | Espresso       |
| 3    | Candra         | Moana          | Latte          |
| 4    | Anggiri        | Aurora         | Americano      |
+------+----------------+----------------+----------------+
Terima kasih telah berkunjung ke Kedai Kopi Kampus!
```

---

## 📑 Penjelasan 
Program ini menggambarkan penerapan **konkurensi (concurrency)** di mana beberapa proses berjalan bersamaan menggunakan *thread pool*.  
Setiap *thread* bekerja independen, namun sinkronisasi output diperlukan agar hasil cetak tetap rapi.  
Selain itu, konsep **persistensi data** diterapkan dengan menyimpan hasil kerja tiap barista ke dalam database MySQL melalui JDBC.
---

## 👩‍💻 Pengembang
Nama: **Anggiri Dahlia Candra**  
Kelas: **B**  
Mata Kuliah: **Pemrograman Berorientasi Objek**  
Topik: *Thread dan Database di Java*

---

## 🔗 Link Repository (Public)
> [https://github.com/anggiridahlia/PBO-Thread-Database]

