<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Ordermerchant extends Model
{
    use HasFactory;

    protected $fillable = ['transaksi_id','menu_id','merchant_id','lokasi','catatan'];
}
