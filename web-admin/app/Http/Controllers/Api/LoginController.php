<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;

class LoginController extends Controller
{
    public function auth_login(Request $request)
    {
        $rules = [
            'email' => 'required|email|max:255',
            'password' => 'required|max:255',
        ];
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap",
            ], 404);
        } else {
            $data = [
                'email' => $request->input('email'),
                'password' => $request->input('password'),
            ];

            Auth::attempt($data);
            if (Auth::check()) {
                if(Auth::user()->role_id != 1){
                    return response()->json([
                        'status' => true,
                        'message' => "Berhasil",
                        'user_id' => Auth::user()->id,
                        'role' => Auth::user()->role_id,
                    ], 200);
                }
                return response()->json([
                    'status' => false,
                    'message' => "User tidak ditemukan!",
                ], 404);
            }
            return response()->json([
                'status' => false,
                'message' => "Username atau email salah!",
            ], 401);

        }
    }

    public function daftar(Request $request)
    {
        $rules = [
            'email' => 'required|email|max:255|unique:users,email',
            'password' => 'required|max:255',
            'nama' => 'required|max:255',
        ];
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => $validator->errors()->first()
            ], 404);
        } else {
            $user = new User();
            $user->role_id = 3;
            $user->name = $request->nama;
            $user->email = $request->email;
            $user->password = Hash::make($request->password);
            $user->status = 1;
            $user->save();
            $idUser = $user->id;

            return response()->json([
                'status' => true,
                'message' => "Berhasil",
                'user_id' => $idUser,
                'role' => 3,
            ], 200);
        }
    }

}
