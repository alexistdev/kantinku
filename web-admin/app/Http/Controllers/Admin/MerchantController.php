<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Http\Traits\AdminTrait;
use App\Models\Merchant;
use Exception;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;
use Yajra\DataTables\DataTables;

class MerchantController extends Controller
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
        return view('admin.merchant.merchant', array(
            'judul' => "Dashboard Merchant | DSC EKANTIN",
            'menuUtama' => 'dashboard',
            'menuKedua' => 'dashboard',
        ));
    }

    /**
     * route: adm.merchant.add
     */
    public function create()
    {
        return view('admin.merchant.addmerchant', array(
            'judul' => "Dashboard Merchant | DSC EKANTIN",
            'menuUtama' => 'dashboard',
            'menuKedua' => 'dashboard',
        ));
    }

    /**
     * route: adm.merchant.save
     */
    public function store(Request $request)
    {
        if ($request->routeIs('adm.*')) {
            $rules = [
                'namaKantin' => 'required|max:255',
                'email' => 'required|unique:users,email|max:255',
                'phone' => 'required|max:50',
            ];
            $message = [
                'namaKantin.required' => "Nama Kantin Harus Diisi!",
                'namaKantin.max' => "Panjang karakter maksimal adalah 255 karakter!",
                'email.required' => "Email Harus Diisi!",
                'email.unique' => "Data Email sudah terdaftar!",
                'email.max' => "Panjang karakter maksimal adalah 255 karakter!",
                'phone.required' => "Nama Kantin Harus Diisi!",
                'phone.max' => "Panjang karakter maksimal adalah 50 karakter!",
            ];
            $request->validate($rules, $message);
            DB::beginTransaction();
            try {
                /** insert tabel user */
                $user = new User();
                $user->role_id = 2;
                $user->email = $request->email;
                $user->password = Hash::make('1234');
                $user->status = 1;
                $user->save();
                $idUser = $user->id;

                /** insert tabel merchant */
                $merchant = new Merchant();
                $merchant->user_id = $idUser;
                $merchant->name = $request->namaKantin;
                $merchant->phone = $request->phone;
                $merchant->save();
                DB::commit();
                return redirect(route('adm.merchant'))->with(['success' => "Pertanyaan berhasil ditambahkan!"]);
            } catch (Exception $e) {
                DB::rollback();
                return redirect(route('adm.merchant'))->withErrors(['error' => $e->getMessage()]);
            }
        } else {
            return abort("404", "NOT FOUND");
        }
    }
}
