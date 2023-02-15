<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Ordermerchant extends Model
{
    use HasFactory;

    /**
     * status = 1  : pending
     * status = 2  : proses
     * status = 3  : selesai
     */

    protected $fillable = ['id','transaksi_id','menu_id','merchant_id','lokasi','catatan'];

    public function transaksi()
    {
        return $this->belongsTo(Transaksi::class)->with('user');
    }

    public function menu()
    {
        return $this->belongsTo(Menu::class);
    }

}
