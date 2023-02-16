<x-upcube.template-layout :title="$judul">
    @push('customCSS')
        <link href="{{asset('template/rocker/assets/plugins/datatable/css/dataTables.bootstrap5.min.css')}}"
              rel="stylesheet"/>
    @endpush

    <!--breadcrumb-->
    <div class="page-breadcrumb d-none d-sm-flex align-items-center mb-3">
        <div class="breadcrumb-title pe-3">Master Data Customer</div>
        <div class="ps-3">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb mb-0 p-0">
                    <li class="breadcrumb-item"><a href="{{route('adm.dashboard')}}"><i class="bx bx-home-alt"></i></a>
                    </li>
                    <li class="breadcrumb-item active" aria-current="page">Data Customer</li>
                </ol>
            </nav>
        </div>

    </div>
    <!--end breadcrumb-->
    <h6 class="mb-0 text-uppercase">Data Customer</h6>
    <hr/>
    <div class="card">
        <div class="card-body">
            <div class="table-responsive">
                <table id="tableCustomer" class="table table-striped table-bordered" style="width:100%">
                    <thead class="table-light">
                    <tr>
                        <th class="text-center">NO</th>
                        <th class="text-center">NAMA</th>
                        <th class="text-center">EMAIL</th>
                        <th class="text-center">TOTAL TRANSAKSI</th>
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
                let base_url = '{{route('adm.merchant.customer')}}';
                $('#tableCustomer').DataTable({
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
                        {data: 'total', class: 'text-center'},
                        {data: 'action', class: 'text-center', width: '15%', orderable: false},
                    ],
                    "bDestroy": true
                });
            });

        </script>
    @endpush
</x-upcube.template-layout>
