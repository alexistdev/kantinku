<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Menu;
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
            $menu = Menu::where('tipe',$request->tipe)->get();
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
}
