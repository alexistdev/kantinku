<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Http\Traits\AdminTrait;
use App\Models\Transaksi;
use App\Models\User;
use Illuminate\Http\Request;
use Yajra\DataTables\DataTables;

class TransaksiAdmin extends Controller
{
    use AdminTrait;

    /**
     * route: adm.merchant
     */
    public function index(Request $request)
    {
        if ($request->ajax()) {
            $transaksi = Transaksi::with('user')->get();
            return DataTables::of($transaksi)
                ->addIndexColumn()
                ->editColumn('created_at', function ($request) {
                    return $request->created_at->format('d-m-Y H:i:s');
                })
                ->editColumn('idTransaksi', function ($request) {
                    return "#".$request->id;
                })
                ->editColumn('status', function ($request) {
                    if($request->status == "1"){
                        $str= "Pending";
                    }else if($request->status == "2"){
                        $str = "Proses";
                    }else if($request->status == "3"){
                        $str = "Selesai";
                    } else {
                        $str = "Ditolak";
                    }
                    return $str;
                })
                ->addColumn('action', function ($row) {
//                    $url = route('adm.merchant.menu', base64_encode($row->merchant->id));
                    $url = "";
                    $btn = " <a href=\"$url\" class=\"btn btn-outline-primary px-5\"> TOLAK</a>";
//                    $btn = $btn . " <a href=\"#\" class=\"btn btn-danger btn-sm ml-auto open-hapus\" data-id=\"$row->id\" data-bs-toggle=\"modal\" data-bs-target=\"#hapusModal\"><i class=\"fas fa-trash\"></i> Delete</i></a>";
                    return $btn;
                })
                ->rawColumns(['action'])
                ->make(true);
        }
        return view('admin.merchant.transaksi', array(
            'judul' => "Dashboard Transaksi | DSC EKANTIN",
            'menuUtama' => 'dashboard',
            'menuKedua' => 'dashboard',
        ));
    }
}
