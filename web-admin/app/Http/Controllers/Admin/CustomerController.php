<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Http\Requests\Admin\CustomerRequest;
use App\Http\Traits\AdminTrait;
use App\Models\User;
use Illuminate\Http\Request;
use Yajra\DataTables\DataTables;
use Exception;

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

                ->addColumn('action', function ($row) {
                    $btn = " <a href=\"#\" class=\"btn btn-outline-danger px-5 open-hapus\" data-id=\"$row->id\" data-bs-toggle=\"modal\" data-bs-target=\"#modalHapus\"> Delete</i></a>";
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

    public function destroy(CustomerRequest $request){
        $request->validated();
        try{
            $user = User::findOrFail($request->user_id);
            $user->delete();
            return redirect(route('adm.merchant.customer'))->with(['warning' => "Merchant berhasil dihapus!"]);
        } catch (Exception $e) {
            return redirect(route('adm.merchant.customer'))->withErrors(['error' => $e->getMessage()]);
        }
    }
}
