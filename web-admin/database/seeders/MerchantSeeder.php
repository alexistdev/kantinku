<?php

namespace Database\Seeders;

use App\Models\Merchant;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Carbon;

class MerchantSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $date = Carbon::now()->format('Y-m-d H:i:s');
        $merchant = [
            array('user_id'=>2,'name' => 'GOPAL','phone'=>'08123456789','created_at' => $date,'updated_at' => $date),
        ];
        Merchant::insert($merchant);
    }
}
