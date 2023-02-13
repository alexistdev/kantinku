<x-upcube.template-layout>
    @push('customCSS')
        <link href="{{asset('template/rocker/assets/plugins/datatable/css/dataTables.bootstrap5.min.css')}}"
              rel="stylesheet"/>
    @endpush

    <!--breadcrumb-->
    <div class="page-breadcrumb d-none d-sm-flex align-items-center mb-3">
        <div class="breadcrumb-title pe-3">Data Transaksi</div>
        <div class="ps-3">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb mb-0 p-0">
                    <li class="breadcrumb-item"><a href="{{route('adm.dashboard')}}"><i class="bx bx-home-alt"></i></a>
                    </li>
                    <li class="breadcrumb-item active" aria-current="page">Data Transaksi</li>
                </ol>
            </nav>
        </div>

    </div>
    <!--end breadcrumb-->
    <h6 class="mb-0 text-uppercase">Data Transaksi</h6>
    <hr/>
    <div class="card">
        <div class="card-body">
            <div class="table-responsive">
                <table id="tabelMerchant2" class="table table-striped table-bordered" style="width:100%">
                    <thead class="table-light">
                    <tr>
                        <th class="text-center">No.</th>
                        <th class="text-center">Merchant</th>
                        <th class="text-center">Customer</th>
                        <th class="text-center">Harga</th>
                        <th class="text-center">Status</th>
                        <th class="text-center">Action</th>
                    </tr>
                    </thead><!-- end thead -->
                    <tbody>
                        <tr>
                            <td>1</td>
                            <td>Siomay</td>
                            <td>Mega Purnamasari</td>
                            <td>Rp. 10.000</td>
                            <td>Pending</td>
                            <td class="text-center"><button class="btn btn-primary">Detail</button>
                            <button class="btn btn-warning">Batalkan</button>
                            <button class="btn btn-danger">Hapus</button></td>
                        </tr>
                    </tbody>
                </table> <!-- end table -->
            </div>
        </div>
    </div>


    @push('customJS')
        <script src="{{asset('template/rocker/assets/plugins/datatable/js/jquery.dataTables.min.js')}}"></script>
        <script src="{{asset('template/rocker/assets/plugins/datatable/js/dataTables.bootstrap5.min.js')}}"></script>
        <x-upcube.toast.toast-message />
        <script>

            $(document).ready(function () {
                let base_url = '{{route('adm.merchant')}}';
                $('#tabelMerchant').DataTable({
                    responsive: true,
                    processing: true,
                    serverSide: true,
                    ajax: {
                        type: 'GET',
                        url: base_url,
                        async: true,
                        dataType: 'json',
                    },
                    columns: [
                        {
                            data: 'index',
                            class: 'text-center',
                            defaultContent: '',
                            orderable: false,
                            searchable: false,
                            width: '5%',
                            render: function (data, type, row, meta) {
                                return meta.row + meta.settings._iDisplayStart + 1; //auto increment
                            }
                        },
                        {data: 'name', class: 'text-left'},
                        {data: 'email', class: 'text-left'},
                        {data: 'phone', class: 'text-left'},
                        {data: 'status', class: 'text-center'},
                        {data: 'action', class: 'text-center', width: '15%', orderable: false},
                    ],
                    "bDestroy": true
                });
            });

        </script>
    @endpush
</x-upcube.template-layout>