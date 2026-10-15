# Minpro 4 PBO Sistem Informasi Ekspedisi Antariksa
## Deskripsi Singkat Program

Program ini adalah aplikasi konsol (CLI) berbasis Java untuk mengelola data operasional sebuah lembaga eksplorasi antariksa, yang terdiri dari tiga entitas utama:

- **Ekspedisi** - misi eksplorasi antariksa (nama, tujuan, durasi, status).
- **Kru** - anggota kru ekspedisi, terbagi menjadi dua peran: **Astronot** dan **Teknisi**.
- **Pesawat Antariksa** - armada pesawat yang digunakan untuk ekspedisi (nama, jenis, kapasitas, status).

Setiap entitas mendukung operasi CRUD penuh (Tambah, Tampilkan, Ubah, Hapus), ditambah fitur **Ringkasan Semua Data** yang menampilkan Ekspedisi, Kru, dan Pesawat sekaligus dalam satu daftar gabungan. Pada Mini Project 4, seluruh data disimpan di database MySQL sehingga tidak hilang saat program ditutup.

## Struktur Package (MVC + DAO)
- **Model** hanya berisi atribut (`private`), constructor, getter/setter, dan method tampilan data miliknya sendiri. Tidak ada logika menu, `Scanner`, maupun SQL di sini. Pada Mini Project 4, class model juga diberi anotasi JPA agar bisa dipetakan ke tabel oleh Hibernate.
- **View** hanya berisi method untuk mencetak teks ke layar dan membaca serta memvalidasi input dari `Scanner`. View tidak menyimpan data aplikasi apa pun.
- **Controller** berisi seluruh logika CRUD dan validasi (misalnya cek ID duplikat). Controller tidak lagi menyimpan data di `ArrayList`, melainkan memanggil **DAO** untuk mengakses database, memanggil `View` untuk berinteraksi dengan pengguna, dan `Model` untuk membuat atau mengubah objek data.
- **DAO** berisi class yang khusus mengurus akses data ke database (query SQL atau operasi Hibernate). Controller hanya memanggil method DAO.
- **Config** berisi pengaturan koneksi ke database: `Koneksi` untuk JDBC dan `HibernateUtil` untuk ORM.
- **Main** hanya menampilkan struktur menu dan meneruskan pilihan pengguna ke method Controller yang sesuai.

## Penjelasan Alur Program

1. Program dimulai dari `Main.java`, menampilkan menu utama dengan 4 modul (Ekspedisi, Kru, Pesawat, Ringkasan) dan 1 pilihan keluar.
2. Data awal tidak diisi lewat kode. Data awal sudah ada di database karena diisi lewat file `database/ekspedisi_antariksa.sql`, sehingga menu "Tampilkan Data" langsung berisi data tanpa harus input manual dulu.
3. Setiap modul punya sub-menu (Tambah, Tampilkan, Ubah, Hapus, Kembali) yang memanggil method pada Controller terkait. Controller kemudian memanggil DAO untuk membaca atau menulis data ke database.
4. Menu **"Ringkasan Semua Data"** memanggil class `Ringkasan`, yang mengambil data dari ketiga controller lain lewat method `getSemuaEntitas()` (data berasal dari database), menggabungkannya menjadi satu `List<EntitasAntariksa>`, lalu menampilkan identitas singkat setiap data secara seragam, walau tipe aslinya berbeda-beda (Ekspedisi, Astronot, Teknisi, atau PesawatAntariksa).
5. Semua pembacaan input dari keyboard (`Scanner`) beserta validasinya dipusatkan di `View`, dipakai ulang oleh seluruh Controller.
6. Program terus berputar dalam menu (`do-while`) sampai pengguna memilih "Keluar" atau "Kembali".

## Penerapan Encapsulation dan Inheritance

### Encapsulation
Seluruh atribut pada `Ekspedisi`, `Kru` (beserta subclass-nya), dan `PesawatAntariksa` dideklarasikan `private`, dan hanya bisa diakses atau diubah lewat getter dan setter publik. Contoh: `idKru`, `nama`, `usia` pada `Kru.java` bersifat `private`, hanya bisa dibaca lewat `getIdKru()`, `getNama()`, `getUsia()`, atau diubah lewat setter-nya.

### Inheritance
Superclass abstrak `Kru` punya dua subclass:
- `Astronot extends Kru` - menambahkan atribut `spesialisasi` dan `jamTerbang`.
- `Teknisi extends Kru` - menambahkan atribut `bidangKeahlian` dan `sertifikasi`.

Keduanya mewarisi atribut umum (`idKru`, `nama`, `usia`) beserta getter dan setter dari `Kru`, dan memanggil constructor superclass lewat `super(...)`. `KruController` bekerja dengan `List<Kru>` hasil DAO yang berisi campuran objek `Astronot` dan `Teknisi`.

## Penerapan Polymorphism dan Abstraction

### Abstraction
`Kru` dideklarasikan sebagai **abstract class** (`public abstract class Kru`), tidak bisa di-instantiate langsung. Di dalamnya ada dua **abstract method**:

<img width="404" height="43" alt="image" src="https://github.com/user-attachments/assets/90bc3912-e138-472c-8d59-1739a00addf2" />

Kedua method ini tidak punya isi di `Kru`, dan **wajib** diimplementasikan oleh setiap subclass (`Astronot`, `Teknisi`) sesuai perannya masing-masing. Ini memaksa setiap jenis kru baru di masa depan untuk mendefinisikan perilakunya sendiri, sekaligus menyembunyikan detail implementasi di balik kontrak umum `Kru`.

### Polymorphism
Diterapkan dalam tiga bentuk:

1. **Method Overriding** (di dalam hierarki `Kru`) - `getPeran()` dan `getDetailTugas()` yang abstrak di `Kru` diimplementasikan berbeda oleh `Astronot` dan `Teknisi`. Saat `tampilkanData()` (didefinisikan sekali di `Kru`) dipanggil untuk objek apa pun dalam `List<Kru>`, hasilnya otomatis menyesuaikan tipe objek aslinya.
2. **Method Overloading** - setiap Controller punya dua method pencarian dengan nama sama tapi parameter beda, misalnya `cariKru(int id)` vs `cariKru(String nama)`. Java memilih method yang sesuai berdasarkan tipe parameter saat dipanggil.
3. **Polymorphism lewat Interface** - class `Ringkasan` menyimpan data dalam `List<EntitasAntariksa>` berisi campuran `Ekspedisi`, `Astronot`, `Teknisi`, dan `PesawatAntariksa`, yaitu empat class yang **tidak** berada dalam satu hierarki pewarisan yang sama. Saat method `getIdentitas()` dipanggil pada tiap elemen list, Java tetap menjalankan implementasi sesuai tipe objek aslinya. Ini membuktikan polymorphism tidak hanya berlaku lewat inheritance (subclass-superclass), tapi juga lewat interface (unrelated classes).

## Penerapan Interface

<img width="332" height="109" alt="image" src="https://github.com/user-attachments/assets/ba487b7b-d9c4-4cae-b977-9496b08ae79f" />

Diimplementasikan oleh **tiga class yang berbeda hierarki**:
- `Ekspedisi implements EntitasAntariksa`
- `Kru implements EntitasAntariksa` (lalu otomatis diwarisi `Astronot` dan `Teknisi`)
- `PesawatAntariksa implements EntitasAntariksa`

Interface ini dipakai secara konkret di `controller/Ringkasan.java`: method `tampilkanRingkasanSemua()` mengambil data dari ketiga controller lain lewat method `getSemuaEntitas()` (yang masing-masing mengembalikan `List<EntitasAntariksa>`), menggabungkannya jadi satu list, lalu mencetak `getIdentitas()` tiap elemen. Fitur ini bisa diakses lewat menu utama **"4. Ringkasan Semua Data"**.

Manfaat nyatanya: `Ringkasan` tidak perlu tahu atau peduli apakah suatu data itu `Ekspedisi`, `Astronot`, `Teknisi`, atau `PesawatAntariksa`. Selama class itu `implements EntitasAntariksa`, ia bisa diperlakukan secara seragam.

## Koneksi Database dengan JDBC

Class `config/Koneksi.java` menyimpan alamat database, username, dan password, lalu menyediakan method `getConnection()` yang mengembalikan objek `Connection`. Jika koneksi belum ada atau sudah tertutup, koneksi baru dibuat.

<img width="685" height="63" alt="image" src="https://github.com/user-attachments/assets/49a598f1-a2f3-4194-867f-fc341a336000" />

Driver yang dipakai adalah `mysql-connector-j`, ditambahkan sebagai dependency di `pom.xml`. Class `TesKoneksi` dipakai untuk menguji bahwa program berhasil terhubung ke database.

## DAO dan Prepared Statement

Setiap entitas punya satu class DAO (`EkspedisiDAOCC`, `KruDAOCC`, `PesawatDAOCC`) yang berisi operasi CRUD. Semua query memakai **`PreparedStatement`**: nilai dimasukkan lewat tanda tanya (`?`), bukan digabung langsung ke teks SQL.

<img width="881" height="183" alt="image" src="https://github.com/user-attachments/assets/39d3afdc-bf6f-473d-add2-e24e6580ff18" />


Manfaat `PreparedStatement`:
- Mencegah **SQL injection**, karena input pengguna diperlakukan sebagai nilai, bukan bagian dari perintah SQL.
- Query lebih rapi dan mudah dibaca.
- Tipe data tiap parameter jelas (`setInt`, `setString`).

Method yang tersedia di setiap DAO:

| Method | Fungsi |
|---|---|
| `tambah()` | Menyimpan data baru (`INSERT`) |
| `getAll()` | Mengambil semua data (`SELECT`) |
| `getById()` | Mengambil satu data berdasarkan ID |
| `cariByNama()` | Mencari data berdasarkan nama (`LIKE`) |
| `ubah()` | Memperbarui data (`UPDATE`) |
| `hapus()` | Menghapus data (`DELETE`) |
| `idSudahAda()` | Mengecek ID duplikat sebelum menambah data |

Pada `KruDAOCC`, hierarki pewarisan dipetakan ke satu tabel `kru` dengan kolom `tipe` (`Astronot` atau `Teknisi`). Saat menyimpan, `instanceof` menentukan kolom khusus mana yang diisi. Saat membaca, kolom `tipe` menentukan objek yang dibuat: `Astronot` atau `Teknisi`.

## Database 

File `database/ekspedisi_antariksa.sql` berisi perintah untuk membuat database, tiga tabel (`ekspedisi`, `kru`, `pesawat_antariksa`), dan mengisi data awal dengan `INSERT`. Data awal **tidak** ditulis di dalam kode Java, sehingga menu "Tampilkan Data" langsung berisi data begitu program dijalankan.

## Nilai Tambah: ORM (Hibernate)

**ORM** (*Object Relational Mapping*) memetakan class Java ke tabel database, sehingga kita bekerja dengan objek dan Hibernate yang membuat SQL-nya secara otomatis.

### Pemetaan Class ke Tabel

Class model diberi anotasi JPA sehingga menjadi *entity*:

<img width="433" height="168" alt="image" src="https://github.com/user-attachments/assets/c97f64d9-a43a-4c9b-b069-03cff2f74589" />


Hierarki `Kru`, `Astronot`, dan `Teknisi` dipetakan dengan strategi **single table**: satu tabel `kru`, dibedakan oleh kolom `tipe`.

<img width="662" height="133" alt="image" src="https://github.com/user-attachments/assets/9e7767ba-886c-4fbb-a969-960d49262ead" /> <br>

<img width="300" height="61" alt="image" src="https://github.com/user-attachments/assets/1c71bc70-84ee-488f-bccc-bc14b9638abb" /> <br>


Karena itu, saat data kru dibaca, Hibernate langsung membuat objek `Astronot` atau `Teknisi` sesuai nilai kolom `tipe`, tanpa `instanceof` maupun `setNull` manual seperti pada JDBC.

### HibernateUtil dan DAO ORM

`HibernateUtil` membangun `SessionFactory` (konfigurasi koneksi dan daftar entity) satu kali saja. Class `EkspedisiDAOORM`, `KruDAOORM`, dan `PesawatDAOORM` memakai `SessionFactory` tersebut untuk operasi CRUD:

```java
sf.inTransaction(session -> session.persist(e));   // tambah
session.find(Ekspedisi.class, id);                 // cari berdasarkan ID
session.createSelectionQuery("from Ekspedisi order by idEkspedisi", Ekspedisi.class)
       .getResultList();                           // tampilkan semua
```

Hibernate tidak dibiarkan mengubah struktur tabel (`hbm2ddl` tidak diaktifkan), karena tabel dan data awal dibuat lewat file `.sql`.

### Perbandingan JDBC dan ORM

SQL: pada DAO JDBC (DAOCC) query ditulis manual, sedangkan pada DAO ORM (DAOORM) query dibuat otomatis oleh Hibernate.
Mengubah data tabel menjadi objek: pada JDBC dilakukan manual lewat ResultSet dan mapRow, sedangkan pada ORM dilakukan otomatis lewat anotasi.
Astronot dan Teknisi: pada JDBC dibedakan dengan instanceof dan setNull, sedangkan pada ORM cukup memakai @DiscriminatorValue.
Jumlah kode: DAO JDBC lebih panjang, DAO ORM lebih ringkas.

Kedua jenis DAO tetap disimpan di package dao. Controller memakai DAO ORM; untuk kembali ke JDBC cukup mengganti import dan satu baris deklarasi field di Controller. Konfigurasi hibernate.show_sql diaktifkan agar SQL hasil buatan Hibernate tampil di konsol (baris berawalan Hibernate:) sebagai bukti ORM berjalan.

## Validasi Input yang Diterapkan

- Input angka wajib berupa angka valid (`inputInt`), diminta ulang jika tidak valid.
- Input angka tertentu wajib lebih dari 0, misalnya ID, durasi, kapasitas, usia (`inputIntPositif`).
- Input angka tertentu tidak boleh negatif, misalnya jam terbang (`inputIntNonNegatif`).
- Input teks wajib diisi, tidak boleh kosong (`inputString`).
- ID pada setiap entitas divalidasi agar tidak duplikat sebelum data baru ditambahkan, dengan mengecek ke database lewat method `idSudahAda()` pada DAO.
- Pilihan menu dan pilihan status divalidasi terhadap rentang pilihan yang tersedia.

## Dummy Data Awal (dari file `.sql`)

Data awal diisi lewat `database/ekspedisi_antariksa.sql`, bukan lewat kode:

- **Ekspedisi**: "Galang Dana BEM KM UNMUL" (tujuan Mars, 14 hari, status Berlangsung) dan "Misi Bulan Nusantara" (tujuan Bulan, 30 hari, status Direncanakan).
- **Kru**: 1 data Astronot ("Dylan Al Furqon") dan 1 data Teknisi ("Tony Stark").
- **Pesawat Antariksa**: "Taufan BAEK" (Roket Orbital, status Siap) dan "Nusantara-2" (Shuttle, status Perawatan).

## Cara Menjalankan Program

1. Pasang **JDK** dan **Maven** (atau gunakan NetBeans).
2. Jalankan **MySQL** dan import `database/ekspedisi_antariksa.sql`.
3. Sesuaikan username dan password MySQL di `config/Koneksi.java` dan `config/HibernateUtil.java`.
4. **Clean and Build** project (Maven akan mengunduh dependency `mysql-connector-j` dan `hibernate-core`).
5. Jalankan `main/Main.java`.

## Dokumentasi Output

### Alur Menu Program

<img width="279" height="170" alt="image" src="https://github.com/user-attachments/assets/39c57942-c593-4c44-ab49-98235a0a628d" /> <br>
Tampilan `menu utama` berisi 4 modul `Ekspedisi, Kru, Pesawat, Ringkasan Semua Data` dan pilihan `Keluar`. Program berjalan dalam `loop` sampai pengguna memilih `keluar`. <br>

<img width="277" height="176" alt="image" src="https://github.com/user-attachments/assets/2fd1e529-1604-4189-ab5b-3f5c9d45ed4f" /> <br>
Menampilkan data awal `ekspedisi` yang dibaca dari database (hasil import file `.sql`) oleh `EkspedisiController`. <br>

<img width="269" height="276" alt="image" src="https://github.com/user-attachments/assets/c3163282-897b-47da-a4de-43a0dbb9b77d" /> <br>
Input data `ekspedisi` baru (ID, nama, tujuan, durasi, status). ID divalidasi agar tidak duplikat. <br>

<img width="299" height="247" alt="image" src="https://github.com/user-attachments/assets/92103122-780e-47f0-9a9d-a82f5da152ec" /> <br>
<img width="306" height="221" alt="image" src="https://github.com/user-attachments/assets/e59be04e-bd1e-41c5-a4d3-163b6d4c1cb4" /> 
<img width="300" height="243" alt="image" src="https://github.com/user-attachments/assets/961c6ba2-f75a-4090-ab14-cfbadd3f33c8" /> <br>
Mengubah data `ekspedisi` berdasarkan ID yang dipilih. <br>

<img width="331" height="75" alt="image" src="https://github.com/user-attachments/assets/56fe7c10-da92-4a9f-84cd-7fa6da566477" /> 
<img width="293" height="158" alt="image" src="https://github.com/user-attachments/assets/5285b82c-a8af-406c-8c74-d657715539ad" /> <br>
Menghapus data `ekspedisi` berdasarkan ID, lalu daftar ditampilkan ulang untuk memastikan data sudah terhapus. <br>

<img width="295" height="173" alt="image" src="https://github.com/user-attachments/assets/bfd0831f-851a-4866-bf8f-69524c1be07b" /> <br>
Tampilan di atas adalah antarmuka menu berbasis teks untuk fitur pengelolaan data `kru`. Menu ini dirancang untuk memudahkan pengguna dalam melakukan operasi CRUD (Create, Read, Update, Delete) data `kru` secara interaktif. <br>

<img width="238" height="238" alt="image" src="https://github.com/user-attachments/assets/6ff33acd-809c-420a-a531-dafb3fd76ea9" /> <br>
Tampilan di atas menunjukkan proses interaktif saat pengguna menambahkan data `kru` baru ke dalam sistem. Program meminta input secara bertahap mulai dari informasi umum hingga atribut khusus berdasarkan peran `kru` tersebut. <br>

<img width="525" height="347" alt="image" src="https://github.com/user-attachments/assets/539c869e-ba34-49ea-b4f6-829f1197a1a6" /> <br>
Tampilan di atas menunjukkan daftar seluruh data `kru` yang telah berhasil disimpan dan terdaftar di dalam sistem. Program menampilkan informasi secara terstruktur dengan garis pemisah yang jelas untuk setiap data `kru`. <br>

<img width="276" height="156" alt="image" src="https://github.com/user-attachments/assets/b2f43bfb-20d3-4b15-9a47-eefcda796d81" /> 
<img width="523" height="347" alt="image" src="https://github.com/user-attachments/assets/ec704846-0cd0-45c0-bed9-81d7e81f15c9" /> <br>
Tampilan di atas menunjukkan proses interaktif saat pengguna memperbarui atau mengedit informasi data `kru` yang sudah tersimpan di dalam sistem. Gambar sebelahnya menunjukkan daftar seluruh data `kru` setelah proses perubahan data (Update) berhasil dilakukan pada ID tertentu. <br>

<img width="287" height="80" alt="image" src="https://github.com/user-attachments/assets/06621d8b-c309-49bd-9e2a-b58e43dafac3" /> 
<img width="535" height="244" alt="image" src="https://github.com/user-attachments/assets/833766d2-0236-4d3b-8061-80312f82bbd4" /> <br>
Kedua gambar di atas menunjukkan proses penghapusan data `kru` dari sistem beserta hasil verifikasi data setelah penghapusan dilakukan. <br>

<img width="269" height="250" alt="image" src="https://github.com/user-attachments/assets/a24c34a8-9686-4c29-aae4-c945032da8df" /> <br>
Tampilan di atas menunjukkan proses interaktif saat pengguna menambahkan data armada `pesawat antariksa` baru ke dalam sistem. Program meminta masukan atribut secara terperinci mulai dari identitas `pesawat antariksa` hingga status operasionalnya. <br>

<img width="288" height="258" alt="image" src="https://github.com/user-attachments/assets/13752287-ae48-4759-99ea-4f31709d0a87" /> <br>
Tampilan di atas menunjukkan daftar seluruh data `pesawat antariksa` yang telah berhasil terdaftar dan tersimpan di dalam sistem. Informasi ditampilkan secara terstruktur dengan garis pembatas untuk setiap unit `pesawat antariksa`. <br>

<img width="309" height="250" alt="image" src="https://github.com/user-attachments/assets/0d2dc90f-8685-4a3f-be31-70f262639609" /> 
<img width="288" height="245" alt="image" src="https://github.com/user-attachments/assets/924c48c3-c5db-4b1c-8d72-080f85a5d2c7" /> <br>
Kedua tampilan di atas menunjukkan proses interaktif saat pengguna memperbarui informasi data `pesawat antariksa` serta hasil pengecekan data setelah perubahan berhasil disimpan di dalam sistem. <br>

<img width="316" height="89" alt="image" src="https://github.com/user-attachments/assets/dca38e64-21e5-44f1-80f0-9fc11de95f6e" /> 
<img width="290" height="147" alt="image" src="https://github.com/user-attachments/assets/bb8d2c35-5f4e-4515-b972-f3a20a2b3d6e" /> <br>
Kedua tampilan di atas menunjukkan proses interaktif saat menghapus data `pesawat antariksa` dari sistem serta hasil pengecekan daftar `pesawat antariksa` setelah penghapusan berhasil dilakukan. <br>

<img width="420" height="138" alt="image" src="https://github.com/user-attachments/assets/c4375c23-9a15-4a10-899d-14cbfacfea52" /> <br>
Tampilan di atas menunjukkan menu rekapitulasi atau `ringkasan` keseluruhan data yang terintegrasi di dalam sistem. Fitur ini memudahkan pengguna untuk melihat rekam jejak dari berbagai entitas utama secara bersamaan dalam satu tampilan ringkas. <br>

<img width="575" height="320" alt="image" src="https://github.com/user-attachments/assets/d8537d30-1695-4d52-9cbb-cc9eefae9ee8" /> <br>
Tampilan di atas merupakan menu utama dari `Sistem Pengelolaan Ekspedisi Antariksa` beserta proses penutupan program. <br>

### Dokumentasi Database, JDBC, dan ORM
<img width="1007" height="163" alt="image" src="https://github.com/user-attachments/assets/701952ed-6996-412f-90a9-9c582ee0b6ff" /> 
<img width="712" height="98" alt="image" src="https://github.com/user-attachments/assets/bf988fc3-b27e-40e3-a280-7131619a5191" /> 
<img width="987" height="101" alt="image" src="https://github.com/user-attachments/assets/dbe0e2d7-be8b-4fc5-986d-eedb3902bbad" />
<img width="623" height="95" alt="image" src="https://github.com/user-attachments/assets/cd65063e-626e-4d01-9a2a-9a65c2bc58b5" /> <br>

Tampilan tabel `ekspedisi`, `kru`, dan `pesawat_antariksa` di phpMyAdmin beserta data awal hasil import file `.sql`. <br>

<img width="885" height="388" alt="image" src="https://github.com/user-attachments/assets/49d39489-ad6c-4293-9797-b9d8c871e30d" /> <br>
Hasil uji koneksi: pesan `Koneksi Berhasil` menandakan program sudah terhubung ke database `ekspedisi_antariksa` lewat JDBC. <br>

<img width="500" alt="Tampilkan data dengan ORM" src="GANTI_DENGAN_LINK_GAMBAR" /> <br>
Menu "Tampilkan Data" langsung berisi data tanpa input manual, karena data dibaca dari database. Baris berawalan `Hibernate: select ...` di atasnya adalah SQL yang dibuat otomatis oleh ORM (Hibernate). <br>

<img width="911" height="257" alt="image" src="https://github.com/user-attachments/assets/bae11e47-7fa4-463f-bb23-17baa656167e" /> <br>
Isi tabel di phpMyAdmin setelah proses tambah, ubah, dan hapus, membuktikan perubahan benar-benar tersimpan di database. <br>

<img width="941" height="304" alt="image" src="https://github.com/user-attachments/assets/6cd13d2d-5fba-4cb2-af9f-3ae42ba97b3b" /> <br>
<img width="919" height="367" alt="image" src="https://github.com/user-attachments/assets/b011d399-6ab8-4cf9-be42-ca1ebbbe4d49" /> <br>
<img width="733" height="141" alt="image" src="https://github.com/user-attachments/assets/c402776d-ec95-4484-9d56-a838b5678e6b" /> <br>

Data yang ditambahkan tetap ada setelah program ditutup dan dijalankan ulang, membuktikan data tersimpan permanen di database. <br>
