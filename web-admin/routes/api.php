<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Api\LoginController as Login,
    App\Http\Controllers\Api\MenuController as menu,
    App\Http\Controllers\Api\KeranjangController as cart,
    App\Http\Controllers\Api\TransaksiController as trx,
    App\Http\Controllers\Api\OrderController as MerchantOrder;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});

Route::post('/v1/auth', [Login::class, 'Auth_login'])->name('api.login');
Route::get('/v1/get_menu', [menu::class, 'get_menu'])->name('api.menu');
Route::get('/v1/cart', [cart::class, 'get_item'])->name('api.cart.list');
Route::post('/v1/cart', [cart::class, 'add_item'])->name('api.cart.add');
Route::delete('/v1/cart', [cart::class, 'delete_item'])->name('api.cart.delete');
Route::post('/v1/checkout', [cart::class, 'check_out'])->name('api.checkout');
Route::get('/v1/transaksi', [trx::class, 'get_transaksi'])->name('api.cart.transaksi');
Route::get('/v1/transaksi/detail', [trx::class, 'list_detail'])->name('api.cart.detailtransaksi');

/** merchant */
Route::get('/v2/order', [MerchantOrder::class, 'get_list_order'])->name('api.merchant.order.list');
Route::post('/v2/order/status', [MerchantOrder::class, 'ubah_status'])->name('api.merchant.order.status');
