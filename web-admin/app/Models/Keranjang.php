<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Keranjang extends Model
{
    use HasFactory;

    protected $fillable = ['user_id','menu_id','catatan'];


    public function menu(){
        return $this->belongsTo(Menu::class);
    }
}
