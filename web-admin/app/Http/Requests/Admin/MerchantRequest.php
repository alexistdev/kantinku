<?php

namespace App\Http\Requests\Admin;

use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class MerchantRequest extends FormRequest
{
    /**
     * Determine if the user is authorized to make this request.
     *
     * @return bool
     */
    public function authorize()
    {
        if (!Request::routeIs('adm.*')) {
            return false;
        }
        return Auth::check();
    }

    /**
     * Get the validation rules that apply to the request.
     *
     * @return array<string, mixed>
     */
    public function rules()
    {
        return [
            'merchant_id' => 'required|numeric',
        ];
    }

    public function messages()
    {
        return [
            'merchant_id.required' => "Data ID tidak ditemukan, silahkan refresh halaman!",
            'merchant_id.numeric' => "Data ID tidak ditemukan, silahkan refresh halaman!",
        ];
    }
}
