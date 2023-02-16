<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Menu;
use App\Models\Merchant;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class MenuController extends Controller
{
    public function get_menu(Request $request)
    {


        $rules = [
            'tipe' => 'required|numeric',
        ];
        $validator = Validator::make($request->all(), $rules);
        if (!$validator->fails()) {
            $menu = Menu::with(["merchant" => function($q){
                $q->where('status', '=', 1);
            }])->where('tipe',$request->tipe)->where('status',1)->get();
            $menu->whereNotNull('merchant');
            if(!$menu->isEmpty()){
                return response()->json([
                    'status' => true,
                    'message' => "berhasil !",
                    'data' => $menu,
                ], 200);
            }
            return response()->json([
                'status' => false,
                'message' => "data kosong",
            ], 404);
        }
        return response()->json([
            'status' => false,
            'message' => "data tidak lengkap",
        ], 404);
    }

    public function merchant_get_menu(Request $request)
    {
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
            $merchant = Merchant::where('user_id',$request->user_id)->first();
            if($merchant != null){
                $menu = Menu::where('merchant_id',$merchant->id)->where('status',1)->get();
                return response()->json([
                    'status' => true,
                    'message' => "berhasil",
                    'data' => $menu,
                ], 200);
            }
            return response()->json([
                'status' => false,
                'message' => "data tidak ditemukan",
            ], 401);
        }
    }

    public function tambah_menu(Request $request){
        $rules = [
            'user_id' => 'required|numeric',
            'nama' => 'required|max:255',
            'harga' => 'required|numeric',
            'tipe' => 'required|max:1',
        ];
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap",
            ], 404);
        } else {
            $merchant = Merchant::where('user_id',$request->user_id)->first();
            if($merchant != null){
                $menu = new Menu();
                $menu->merchant_id = $merchant->id;
                $menu->nama = strtoupper($request->nama);
                $menu->harga = $request->harga;
                $menu->tipe = $request->tipe;
                $menu->status = 1;
                $menu->save();
                return response()->json([
                    'status' => true,
                    'message' => "berhasil",
                ], 200);
            }
            return response()->json([
                'status' => false,
                'message' => "data tidak ditemukan",
            ], 401);
        }
    }
}
