<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Http\Traits\AdminTrait;
use App\Models\Menu;
use App\Models\Merchant;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Yajra\DataTables\DataTables;
use Exception;

class MenuController extends Controller
{
    use AdminTrait;


    public function index(Request $request,$id)
    {
        if ($request->ajax()) {
            $menu = Menu::withCount('transaksi')->where('merchant_id',base64_decode($id))->orderBy('id','DESC')->get();
            return DataTables::of($menu)
                ->addIndexColumn()
                ->editColumn('created_at', function ($request) {
                    return $request->created_at->format('d-m-Y H:i:s');
                })
                ->editColumn('tipe', function ($request) {
                    $str = "Minuman";
                    if($request->tipe == "1"){
                        $str = "Makanan";
                    }
                    return $str;
                })
                ->addColumn('action', function ($row) {
//                    $url = route('', base64_encode($row->id));
//                    $url = "";
//                    $btn = " <a href=\"$url\" class=\"btn btn-outline-primary px-5\"> Edit</a>";
//                    $btn = $btn . " <a href=\"#\" class=\"btn btn-danger btn-sm ml-auto open-hapus\" data-id=\"$row->id\" data-bs-toggle=\"modal\" data-bs-target=\"#hapusModal\"><i class=\"fas fa-trash\"></i> Delete</i></a>";
                    return $btn = "";
                })
                ->rawColumns(['action'])
                ->make(true);
        }
        return view('admin.merchant.menu', array(
            'judul' => "Dashboard Guru | FavoriteIDN",
            'menuUtama' => 'dashboard',
            'menuKedua' => 'dashboard',
            'id' => $id,
        ));
    }


    public function create($id)
    {

        $data = Merchant::findOrFail(base64_decode($id));
        return view('admin.merchant.addmenu', array(
            'judul' => "Dashboard Guru | FavoriteIDN",
            'menuUtama' => 'dashboard',
            'menuKedua' => 'dashboard',
            'dataMerchant' => $data,
            'id' => $id,
        ));
    }

    /**
     * route: adm.merchant.save
     */
    public function store(Request $request,$id)
    {
        if ($request->routeIs('adm.*')) {
            $rules = [
                'namaMenu' => 'required|max:255',
                'harga' => 'required|numeric',
                'tipe' => 'required|numeric',
            ];
            $message = [
                'namaMenu.required' => "Nama Menu Harus Diisi!",
                'namaMenu.max' => "Panjang karakter maksimal adalah 255 karakter!",
                'harga.required' => "Email Harus Diisi!",
                'harga.numeric' => "Format harus angka!",
                'tipe.required' => "Tipe harus dipilih!",
                'tipe.numeric' => "Tipe harus dipilih!",
            ];
            $request->validate($rules, $message);
            DB::beginTransaction();
            try {
                $menu = new Menu();
                $menu->merchant_id = base64_decode($id);
                $menu->nama = $request->namaMenu;
                $menu->harga = $request->harga;
                $menu->tipe = $request->tipe;
                $menu->status = 1;
                $menu->save();
                DB::commit();
                return redirect(route('adm.merchant.menu',$id))->with(['success' => "Menu berhasil ditambahkan!"]);
            } catch (Exception $e) {
                DB::rollback();
                return redirect(route('adm.merchant.menu',$id))->withErrors(['error' => $e->getMessage()]);
            }
        } else {
            return abort("404", "NOT FOUND");
        }
    }
}
