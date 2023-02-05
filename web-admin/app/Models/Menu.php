<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Menu extends Model
{
    use HasFactory;

    protected $fillable = [
        'merchant_id','name','harga','status'
    ];

    public function transaksi(){
        return $this->belongsTo(Detailtransaksi::class,'id','menu_id');
    }
}
