# Kantinku v.1.0

## Info

Kantinku adalah aplikasi pemesanan makanan berbasis Android dan Web Service, dengan spesifikasi sebagai berikut:

<a href='https://postimg.cc/bSkxMZFk' target='_blank'><img src='https://i.postimg.cc/bSkxMZFk/login.png' border='0' alt='login'/></a>

- Bahasa Pemrograman Java, dengan tools Android Studio
- Rest API menggunakan Laravel 9.42
- Database MySQL
- Library Retrofit, laravel breeze.

## Installasi
Cara installasi adalah sebagai berikut:

- git clone https://github.com/alexistdev/kantinku
- Buat database kosong dengan nama: kantinku
- Buka directory web-admin dengan text-editor dan copas file .env-example kemudian rename menjadi .env
- Lakukan pengaturan nama database di file .env dan simpan.
- ketikkan di terminal: php artisan key:generate
- ketikkan di terminal : npm install && npm run dev
- ketikkan di terminal : php artisan migrate:fresh --seed
- ketikkan perintah: php artisan serve
- Buka dengan android studio file di directory Android-File.
- Buka file config.java dan lakukan pengaturan sesuai dengan URL anda.
