<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Detailtransaksi extends Model
{
    use HasFactory;

    public function menu(){
        return $this->belongsTo(Menu::class);
    }
}
