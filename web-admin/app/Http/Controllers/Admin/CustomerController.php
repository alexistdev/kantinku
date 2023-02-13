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
            $merchant = User::with('merchant')->merchantonly()->orderBy('id', 'desc')->get();
            return DataTables::of($merchant)
                ->addIndexColumn()
                ->editColumn('created_at', function ($request) {
                    return $request->created_at->format('d-m-Y H:i:s');
                })
                ->editColumn('name', function ($request) {
                    return ucwords($request->merchant?->name);
                })
                ->editColumn('phone', function ($request) {
                    return $request->merchant?->phone;
                })
                ->editColumn('status', function ($request) {
                    if ($request->status != 1) {
                        $str = "Suspend";
                    } else {
                        $str = "Aktif";
                    }
                    return $str;
                })
                ->addColumn('action', function ($row) {
                    $url = route('adm.merchant.menu', base64_encode($row->merchant->id));
                    $btn = " <a href=\"$url\" class=\"btn btn-outline-primary px-5\"> MENU</a>";
//                    $btn = $btn . " <a href=\"#\" class=\"btn btn-danger btn-sm ml-auto open-hapus\" data-id=\"$row->id\" data-bs-toggle=\"modal\" data-bs-target=\"#hapusModal\"><i class=\"fas fa-trash\"></i> Delete</i></a>";
                    return $btn;
                })
                ->rawColumns(['action'])
                ->make(true);
        }
        return view('admin.merchant.customer', array(
            'judul' => "Dashboard Guru | FavoriteIDN",
            'menuUtama' => 'dashboard',
            'menuKedua' => 'dashboard',
        ));
    }
}
