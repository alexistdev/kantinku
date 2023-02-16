<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Http\Traits\AdminTrait;
use App\Models\User;
use Illuminate\Http\Request;
use Yajra\DataTables\DataTables;

class CustomerController extends Controller
{
    use AdminTrait;

    /**
     * route: adm.merchant
     */
    public function index(Request $request)
    {
        if ($request->ajax()) {
            $user = User::withCount('transaksi')->where('role_id',3)->orderBy('id', 'desc')->get();
            return DataTables::of($user)
                ->addIndexColumn()
                ->editColumn('created_at', function ($request) {
                    return $request->created_at->format('d-m-Y H:i:s');
                })
                ->editColumn('total', function ($request) {
                    return $request->transaksi_count;
                })
//                ->editColumn('phone', function ($request) {
//                    return $request->merchant?->phone;
//                })
//                ->editColumn('status', function ($request) {
//                    if ($request->status != 1) {
//                        $str = "Suspend";
//                    } else {
//                        $str = "Aktif";
//                    }
//                    return $str;
//                })
                ->addColumn('action', function ($row) {
//                    $url = route('adm.merchant.menu', base64_encode($row->merchant->id));
                    $url = "";
                    $btn = " <a href=\"$url\" class=\"btn btn-outline-danger px-5\"> HAPUS</a>";
//                    $btn = $btn . " <a href=\"#\" class=\"btn btn-danger btn-sm ml-auto open-hapus\" data-id=\"$row->id\" data-bs-toggle=\"modal\" data-bs-target=\"#hapusModal\"><i class=\"fas fa-trash\"></i> Delete</i></a>";
                    return $btn;
                })
                ->rawColumns(['action'])
                ->make(true);
        }
        return view('admin.merchant.customer', array(
            'judul' => "Dashboard Customer | DSC EKANTIN",
            'menuUtama' => 'dashboard',
            'menuKedua' => 'dashboard',
        ));
    }
}
