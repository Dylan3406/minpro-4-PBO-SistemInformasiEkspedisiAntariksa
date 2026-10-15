# Minpro 4 PBO Sistem Informasi Ekspedisi Antariksa
## Deskripsi Singkat Program
Program ini adalah aplikasi konsol (CLI) berbasis Java untuk mengelola data operasional sebuah lembaga eksplorasi antariksa, yang terdiri dari tiga entitas utama:

- **Ekspedisi** - misi eksplorasi antariksa (nama, tujuan, durasi, status).
- **Kru** - anggota kru ekspedisi, terbagi menjadi dua peran: **Astronot** dan **Teknisi**.
- **Pesawat Antariksa** - armada pesawat yang digunakan untuk ekspedisi (nama, jenis, kapasitas, status).

Setiap entitas mendukung operasi CRUD penuh (Tambah, Tampilkan, Ubah, Hapus), ditambah satu fitur baru di Mini Project 3: **Ringkasan Semua Data**, yang menampilkan Ekspedisi, Kru, dan Pesawat sekaligus dalam satu daftar gabungan.

## Struktur Package (MVC)
- **Model** hanya berisi atribut (private), constructor, getter/setter, dan method tampilan data milik dirinya sendiri. Tidak ada logika menu atau `Scanner` di sini.
- **View** hanya berisi method untuk mencetak teks ke layar dan membaca+memvalidasi input dari `Scanner`. View tidak menyimpan data aplikasi.
- **Controller** menyimpan `ArrayList` data, berisi seluruh logika CRUD dan validasi (misalnya cek ID duplikat), lalu memanggil `View` untuk berinteraksi dengan pengguna dan `Model` untuk membuat/mengubah objek data.
- **Main** hanya menampilkan struktur menu dan meneruskan pilihan pengguna ke method Controller yang sesuai.
- 
## Penjelasan Alur Program

1. Program dimulai dari `Main.java`, menampilkan menu utama dengan 4 modul (Ekspedisi, Kru, Pesawat, Ringkasan) dan 1 pilihan keluar.
2. Saat program dijalankan, `EkspedisiController`, `KruController`, dan `PesawatController` masing-masing langsung mengisi **dummy data awal** ke `ArrayList`-nya, sehingga menu "Tampilkan Data" langsung berisi data tanpa harus input manual dulu.
3. Setiap modul punya sub-menu (Tambah, Tampilkan, Ubah, Hapus, Kembali) yang memanggil method pada Controller terkait.
4. Menu baru **"Ringkasan Semua Data"** memanggil `RingkasanController`, yang mengambil data dari ketiga controller lain lewat method `getSemuaEntitas()`, menggabungkannya menjadi satu `List<EntitasAntariksa>`, lalu menampilkan identitas singkat setiap data secara seragam -- walau tipe aslinya berbeda-beda (Ekspedisi, Astronot, Teknisi, atau PesawatAntariksa).
5. Semua pembacaan input dari keyboard (`Scanner`) beserta validasinya dipusatkan di `View`, dipakai ulang oleh seluruh Controller.
6. Program terus berputar dalam menu (`do-while`) sampai pengguna memilih "Keluar"/"Kembali".

## Penerapan Encapsulation dan Inheritance

### Encapsulation
Seluruh atribut pada `Ekspedisi`, `Kru` (beserta subclass-nya), dan `PesawatAntariksa` dideklarasikan `private`, dan hanya bisa diakses/diubah lewat getter dan setter publik. Contoh: `idKru`, `nama`, `usia` pada `Kru.java` bersifat `private`, hanya bisa dibaca lewat `getIdKru()`, `getNama()`, `getUsia()`, atau diubah lewat setter-nya.

### Inheritance
Superclass abstrak `Kru` punya dua subclass:
- `Astronot extends Kru` - menambahkan atribut `spesialisasi` dan `jamTerbang`.
- `Teknisi extends Kru` - menambahkan atribut `bidangKeahlian` dan `sertifikasi`.

Keduanya mewarisi atribut umum (`idKru`, `nama`, `usia`) beserta getter/setter dari `Kru`, dan memanggil constructor superclass lewat `super(...)`. `KruController` menyimpan data dalam satu `ArrayList<Kru>` berisi campuran objek `Astronot` dan `Teknisi`.

## Penerapan Polymorphism dan Abstraction

### Abstraction
`Kru` dideklarasikan sebagai **abstract class** (`public abstract class Kru`), tidak bisa di-instantiate langsung. Di dalamnya ada dua **abstract method**:

<img width="404" height="43" alt="image" src="https://github.com/user-attachments/assets/90bc3912-e138-472c-8d59-1739a00addf2" />

Kedua method ini tidak punya isi di `Kru`, dan **wajib** diimplementasikan oleh setiap subclass (`Astronot`, `Teknisi`) sesuai perannya masing-masing. Ini memaksa setiap jenis kru baru di masa depan untuk mendefinisikan perilakunya sendiri, sekaligus menyembunyikan detail implementasi di balik kontrak umum `Kru`.

### Polymorphism
Diterapkan dalam dua bentuk, ditambah satu bentuk baru lewat interface:

1. **Method Overriding** (di dalam hierarki `Kru`) - `getPeran()` dan `getDetailTugas()` yang abstrak di `Kru` diimplementasikan berbeda oleh `Astronot` dan `Teknisi`. Saat `tampilkanData()` (didefinisikan sekali di `Kru`) dipanggil untuk objek apa pun dalam `ArrayList<Kru>`, hasilnya otomatis menyesuaikan tipe objek aslinya.
2. **Method Overloading** - setiap Controller punya dua method pencarian dengan nama sama tapi parameter beda, misalnya `cariKru(int id)` vs `cariKru(String nama)`. Java memilih method yang sesuai berdasarkan tipe parameter saat dipanggil.
3. **Polymorphism lewat Interface** (baru di Mini Project 3) - `RingkasanController` menyimpan data dalam `List<EntitasAntariksa>` berisi campuran `Ekspedisi`, `Astronot`, `Teknisi`, dan `PesawatAntariksa` -- empat class yang **tidak** berada dalam satu hierarki pewarisan yang sama. Saat method `getIdentitas()` dipanggil pada tiap elemen list, Java tetap menjalankan implementasi sesuai tipe objek aslinya. Ini membuktikan polymorphism tidak hanya berlaku lewat inheritance (subclass-superclass), tapi juga lewat interface (unrelated classes).

## Penerapan Nilai Tambah: Interface

<img width="332" height="109" alt="image" src="https://github.com/user-attachments/assets/ba487b7b-d9c4-4cae-b977-9496b08ae79f" />

Diimplementasikan oleh **tiga class yang berbeda hierarki**:
- `Ekspedisi implements EntitasAntariksa`
- `Kru implements EntitasAntariksa` (lalu otomatis diwarisi `Astronot` dan `Teknisi`)
- `PesawatAntariksa implements EntitasAntariksa`

Interface ini dipakai secara konkret di `controller/RingkasanController.java`: method `tampilkanRingkasanSemua()` mengambil data dari ketiga controller lain lewat method `getSemuaEntitas()` (yang masing-masing mengembalikan `List<EntitasAntariksa>`), menggabungkannya jadi satu list, lalu mencetak `getIdentitas()` tiap elemen. Fitur ini bisa diakses lewat menu utama **"4. Ringkasan Semua Data"**.

Manfaat nyatanya: `RingkasanController` tidak perlu tahu atau peduli apakah suatu data itu `Ekspedisi`, `Astronot`, `Teknisi`, atau `PesawatAntariksa` -- selama class itu `implements EntitasAntariksa`, ia bisa diperlakukan secara seragam.

## Validasi Input yang Diterapkan

- Input angka wajib berupa angka valid (`inputInt`), diminta ulang jika tidak valid.
- Input angka tertentu wajib lebih dari 0, misalnya ID, durasi, kapasitas, usia (`inputIntPositif`).
- Input angka tertentu tidak boleh negatif, misalnya jam terbang (`inputIntNonNegatif`).
- Input teks wajib diisi, tidak boleh kosong (`inputString`).
- ID pada setiap entitas divalidasi agar tidak duplikat sebelum data baru ditambahkan.
- Pilihan menu dan pilihan status divalidasi terhadap rentang pilihan yang tersedia.

## Dummy Data Awal

- **Ekspedisi**: "Galang Dana BEM KM UNMUL" (tujuan Mars, status Berlangsung).
- **Kru**: 1 data Astronot ("Dylan Al Furqon") dan 1 data Teknisi ("Tony Stark").
- **Pesawat Antariksa**: "Taufan BAEK" (jenis Roket Orbital, status Siap).

## Dokumentasi Output
<img width="279" height="170" alt="image" src="https://github.com/user-attachments/assets/39c57942-c593-4c44-ab49-98235a0a628d" /> <br>
Tampilan `menu utama` berisi 4 modul `Ekspedisi, Kru, Pesawat, Ringkasan Semua Data` dan pilihan `Keluar`. Program berjalan dalam `loop` sampai pengguna memilih `keluar`. <br>

<img width="277" height="176" alt="image" src="https://github.com/user-attachments/assets/2fd1e529-1604-4189-ab5b-3f5c9d45ed4f" /> <br>
Menampilkan dummy data awal ("Galang Dana BEM KM UNMUL", tujuan Mars, status Berlangsung) yang diisi otomatis oleh `EkspedisiController`. <br>

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
Tampilan di atas menunjukkan proses interaktif saat pengguna memperbarui atau mengedit informasi data `kru` yang sudah tersimpan di dalam sistem. Gambar Sebelahnya menunjukkan daftar seluruh data `kru` setelah proses perubahan data (Update) berhasil dilakukan pada ID tertentu. <br>

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




<img width="283" height="140" alt="image" src="https://github.com/user-attachments/assets/4fa8f369-97a0-4b7e-99c3-72399bad7973" />
<img width="309" height="199" alt="image" src="https://github.com/user-attachments/assets/dddb5daf-eef1-4430-b3aa-c97b4832815c" />
<img width="277" height="154" alt="image" src="https://github.com/user-attachments/assets/93dadd64-ca83-4f30-bc39-f0198c7eec75" />
<img width="429" height="63" alt="image" src="https://github.com/user-attachments/assets/c205e84a-d8cd-4057-afbc-e6cbfe5d4faf" />
<img width="608" height="73" alt="image" src="https://github.com/user-attachments/assets/1a533493-a6a8-4dba-be75-cb4b9c414f09" />
<img width="350" height="63" alt="image" src="https://github.com/user-attachments/assets/5d2c7a13-4af6-433c-b5d9-3e4537a50e69" />
<img width="486" height="663" alt="image" src="https://github.com/user-attachments/assets/cf1926db-0a5f-471b-9dd8-dad8c083a872" />

