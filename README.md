# Kantinku v.1.0

## Info

Kantinku adalah aplikasi pemesanan makanan berbasis Android dan Web Service, dengan spesifikasi sebagai berikut:

## Halaman User
<table>
  <tr>
    <td><img src='https://i.postimg.cc/bSkxMZFk/login.png' border='0' alt='login' height="400px"/></td>
    <td><img src='https://i.postimg.cc/3NHvnwK8/dashboarduser.png' border='0' alt='login' height="400px"/></td>
    <td><img src='https://i.postimg.cc/GmNJvds2/menu.png' border='0' alt='login' height="400px"/></td>
    <td><img src='https://i.postimg.cc/QxvHbtSR/cart.png' border='0' alt='login' height="400px"/></td>
  </tr>
  </table>
  
## Halaman Merchant

## Halaman Administrator
<table>
  <tr>
    <td><img src='https://i.postimg.cc/mrHqHdkv/dashboard-admin.png' border='0' alt='login' width="auto" height="400px"/></td>
  </tr>
  </table>
## Info
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

## Credential
User:<br/>
user@gmail.com<br/>
1234<br/><br/>

Merchant:<br/>
merchant@gmail.com<br/>
1234<br/><br/>

Administrator:<br/>
admin@gmail.com<br/>
1234<br/><br/>
