<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Detailtransaksi;
use App\Models\Transaksi;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class TransaksiController extends Controller
{
    public function get_transaksi(Request $request)
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
            $transaksi = Transaksi::where('user_id', $request->user_id)->get();
            if ($transaksi->count() > 0) {
                return response()->json([
                    'status' => true,
                    'message' => "data ditemukan",
                    'data' => $transaksi,
                ], 200);
            }
            return response()->json([
                'status' => false,
                'message' => "data kosong",
            ], 401);
        }
    }

    public function list_detail(Request $request)
    {
        $rules = [
            'transaksi_id' => 'required|numeric',
        ];
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap",
            ], 404);
        } else {
            $transaksi = Transaksi::findOrFail($request->transaksi_id);
            $detail = Detailtransaksi::with('menu')->where('transaksi_id', $transaksi->id)->get();
            if ($detail->count() > 0) {
                return response()->json([
                    'status' => true,
                    'message' => "data ditemukan",
                    'total' => $transaksi->total,
                    'data' => $detail,
                ], 200);
            }
            return response()->json([
                'status' => false,
                'message' => "data kosong",
            ], 401);

        }
    }
}
