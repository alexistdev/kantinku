<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Menu extends Model
{
    use HasFactory;

    protected $fillable = [
        'merchant_id','name','harga','status','tipe'
    ];

    public function transaksi(){
        return $this->belongsTo(Detailtransaksi::class,'id','menu_id');
    }

    public function merchant(){
        return $this->belongsTo(Merchant::class);
    }
}
