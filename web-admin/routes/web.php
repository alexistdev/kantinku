<?php

use App\Http\Controllers\ProfileController;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Admin\{DashboardAdmin as DashAdm,
    MerchantController as MerchAdm,
    MenuController as MenuAdm,
    TransaksiAdmin as TransAdm,
    CustomerController as CusAdm,
    SettingController as SettAdm,
};

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});

Route::get('/dashboard', function () {
    return view('dashboard');
})->middleware(['auth', 'verified'])->name('dashboard');

Route::middleware('auth')->group(function () {
    Route::get('/profile', [ProfileController::class, 'edit'])->name('profile.edit');
    Route::patch('/profile', [ProfileController::class, 'update'])->name('profile.update');
    Route::delete('/profile', [ProfileController::class, 'destroy'])->name('profile.destroy');

    Route::group(['roles' => 'admin'], function () {
        Route::get('/staff/dashboard', [DashAdm::class, 'index'])->name('adm.dashboard');

        Route::get('/staff/merchant', [MerchAdm::class, 'index'])->name('adm.merchant');
        Route::post('/staff/merchant', [MerchAdm::class, 'store'])->name('adm.merchant.save');
        Route::get('/staff/merchant/add', [MerchAdm::class, 'create'])->name('adm.merchant.add');

        Route::get('/staff/{id}/merchant/menu', [MenuAdm::class, 'index'])->name('adm.merchant.menu');
        Route::post('/staff/{id}/merchant/menu', [MenuAdm::class, 'store'])->name('adm.merchant.menu.save');
        Route::get('/staff/{id}/merchant/menu/add', [MenuAdm::class, 'create'])->name('adm.merchant.menuadd');

        Route::get('/staff/transaksi', [TransAdm::class, 'index'])->name('adm.merchant.transaksi');
        Route::get('/staff/customer', [CusAdm::class, 'index'])->name('adm.merchant.customer');
        Route::get('/staff/setting', [SettAdm::class, 'index'])->name('adm.setting');

    });
});

require __DIR__.'/auth.php';
