<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Http\Traits\AdminTrait;
use App\Models\Transaksi;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class DashboardAdmin extends Controller
{
    use AdminTrait;


    public function index()
    {
        return view('admin.dashboard',array(
            'judul' => "Dashboard Administrator | DSC EKANTIN",
            'menuUtama' => 'dashboard',
            'menuKedua' => 'dashboard',
            'dataUser' => $this->users,
            'jmlCustomer' => User::where('role_id',3)->get()->count(),
            'jmlMerchant' => User::where('role_id',2)->get()->count(),
            'jmlTransaksi' => Transaksi::get()->count(),
        ));
    }

}
