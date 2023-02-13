<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Detailtransaksi;
use App\Models\Keranjang;
use App\Models\Menu;
use App\Models\Ordermerchant;
use App\Models\Transaksi;
use App\Models\User;
use Exception;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Validator;

class KeranjangController extends Controller
{
    public function add_item(Request $request)
    {
        $rules = [
            'user_id' => 'required|numeric',
            'menu_id' => 'required|numeric',
        ];
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap",
            ], 404);
        } else {
            $user = User::where('role_id', 3)->find($request->user_id);
            if($user != null){
                $menu = Menu::find($request->menu_id);
                if($menu != null){
                    try{
                        $cart = new Keranjang();
                        $cart->user_id = $request->user_id;
                        $cart->menu_id = $request->menu_id;
                        $cart->save();
                        return response()->json([
                            'status' => true,
                            'message' => "data berhasil disimpan",
                        ], 200);
                    }catch (\Exception $e){
                        return response()->json([
                            'status' => false,
                            'message' => $e->getMessage(),
                        ], 500);
                    }
                }
            }
            return response()->json([
                'status' => false,
                'message' => "data tidak ditemukan",
            ], 404);
        }
    }

    public function get_item(Request $request){
        $rules = [
            'user_id' => 'required|numeric',
        ];
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap",
            ], 404);
        } else {
            $user = User::where('role_id', 3)->find($request->user_id);
            if($user != null){
                $carts = Keranjang::with('menu')->where('user_id',$request->user_id)->get();
                $total = 0;
                $data = [];
                foreach($carts as $cart){
                    $total += $cart->menu->harga ?? 0;
                    $tempData = [];
                    $tempData['id']=$cart->id;
                    $tempData['nama']=$cart->menu->nama ?? "NN";
                    $tempData['harga']=$cart->menu->harga ?? 0;
                    array_push($data,$tempData);
                }
                return response()->json([
                    'status' => true,
                    'message' => "data ditemukan",
                    'total' => $total,
                    'data' => $data,
                ], 404);
            }
            return response()->json([
                'status' => false,
                'message' => "data tidak ditemukan",
            ], 404);
        }
    }

    public function delete_item(Request $request)
    {
        $rules = [
            'user_id' => 'required|numeric',
            'item_id' => 'required|numeric',
        ];
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap",
            ], 404);
        } else {
            $user = User::where('role_id', 3)->find($request->user_id);
            if($user != null){
                $cart = Keranjang::find($request->item_id);
                if($cart != null){
                    try{
                        $cart->delete();
                        return response()->json([
                            'status' => true,
                            'message' => "Item berhasil dihapus",
                        ], 200);
                    }catch (\Exception $e){
                        return response()->json([
                            'status' => false,
                            'message' => $e->getMessage(),
                        ], 500);
                    }
                }
            }
            return response()->json([
                'status' => false,
                'message' => "data tidak ditemukan",
            ], 404);
        }
    }

    public function check_out(Request $request){
        $rules = [
            'user_id' => 'required|numeric',
            'lokasi' => 'required|max:255',
            'total' => 'required|numeric',
        ];
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap",
            ], 404);
        } else {
            $items = Keranjang::with('menu')->where('user_id',$request->user_id)->get();
            if(!$items->isEmpty()){
                DB::beginTransaction();
                try{
                    $transaksi = new Transaksi();
                    $transaksi->user_id = $request->user_id;
                    $transaksi->lokasi = $request->lokasi;
                    $transaksi->total = $request->total;
                    $transaksi->save();
                    $idTransaksi = $transaksi->id;
                    //merchant
                    foreach($items as $item){
                        $order = new Ordermerchant();
                        $order->transaksi_id = $idTransaksi;
                        $order->menu_id = $item->menu_id;
                        $order->merchant_id = $item->menu->merchant_id;
                        $order->lokasi = $request->lokasi;
                        $order->catatan = $item->catatan ?? "";
                        $order->save();

                        $detail = new Detailtransaksi();
                        $detail->transaksi_id = $idTransaksi;
                        $detail->menu_id = $item->menu_id;
                        $detail->jumlah = $item->menu->harga ?? 0;
                        $detail->save();
                    }
                    Keranjang::where('user_id',$request->user_id)->delete();
                    DB::commit();
                    return response()->json([
                        'status' => true,
                        'message' => "data berhasil disimpan",
                    ], 200);
                } catch (Exception $e) {
                    DB::rollback();
                    return response()->json([
                        'status' => false,
                        'message' => $e->getMessage(),
                    ], 500);
                }

            }
            return response()->json([
                'status' => false,
                'message' => "data tidak ditemukan",

            ], 404);
        }
    }

}
