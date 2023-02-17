<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Detailtransaksi;
use App\Models\Menu;
use App\Models\Merchant;
use App\Models\Ordermerchant;
use App\Models\Transaksi;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class OrderController extends Controller
{
    public function get_list_order(Request $request)
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
                 $order = Ordermerchant::with('transaksi', 'menu')->where('status', 1)->orWhere('status', 2)->where('merchant_id', $merchant->id)->get();
                if($order->count() > 0){
                    $data = [];
                    foreach ($order as $row) {
                        $dataTemp = [];
                        $dataTemp['id'] = $row->id;
                        $dataTemp['transaksi_id'] = $row->transaksi_id;
                        $dataTemp['lokasi'] = $row->lokasi;
                        $dataTemp['catatan'] = $row->catatan;
                        $dataTemp['pelanggan'] = $row->transaksi->user->name ?? "USER";
                        $dataTemp['pesanan'] = $row->menu->nama ?? "-";
                        $dataTemp['harga'] = $row->menu->harga ?? 0;
                        $dataTemp['status'] = $row->status;
                        array_push($data,$dataTemp);
                    }
                    return response()->json([
                        'status' => true,
                        'message' => "berhasil",
                        'data' => $data,
                    ], 200);
                }
            }

            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap",
            ], 404);
        }
    }

    public function ubah_status(Request $request)
    {
        $rules = [
            'order_id' => 'required|numeric',
            'tipe' => 'required|numeric',
        ];
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap",
            ], 404);
        } else {
           $order = Ordermerchant::find($request->order_id);
           if($order != null){
               if($order->status != $request->tipe){
                   if(in_array($request->tipe,[1,2,3,4])){
                       Ordermerchant::where('id',$request->order_id)->update([
                           'status'=> $request->tipe,
                       ]);
                        Detailtransaksi::where('id',$order->detailtransaksi_id)->update([
                            'status' => $request->tipe,
                        ]);
                        Transaksi::where('id',$order->transaksi_id)->update([
                            'status'=> $request->tipe,
                        ]);
                       return response()->json([
                           'status' => true,
                           'message' => "berhasil",
                       ], 200);
                   }
               }

           }
            return response()->json([
                'status' => false,
                'message' => "data tidak ditemukan",
            ], 401);
        }
    }
}
