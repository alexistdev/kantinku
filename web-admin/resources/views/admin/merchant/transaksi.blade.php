<x-upcube.template-layout :title="$judul">
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
                <table id="tableTransaksi" class="table table-striped table-bordered" style="width:100%">
                    <thead class="table-light">
                    <tr>
                        <th class="text-center">No.</th>
                        <th class="text-center">ORDER ID</th>
                        <th class="text-center">USER</th>
                        <th class="text-center">TOTAL</th>
                        <th class="text-center">LOKASI</th>
                        <th class="text-center">STATUS</th>
                        <th class="text-center">ACTION</th>
                    </tr>
                    </thead><!-- end thead -->
                    <tbody>

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
                let base_url = '{{route('adm.merchant.transaksi')}}';
                $('#tableTransaksi').DataTable({
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
                        {data: 'idTransaksi', class: 'text-left'},
                        {data: 'user.name', class: 'text-left'},
                        {data: 'total', class: 'text-left'},
                        {data: 'lokasi', class: 'text-left'},
                        {data: 'status', class: 'text-center'},
                        {data: 'action', class: 'text-center', width: '15%', orderable: false},
                    ],
                    "bDestroy": true
                });
            });

        </script>
    @endpush
</x-upcube.template-layout>
