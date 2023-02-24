<x-upcube.template-layout :title="$judul">
    @push('customCSS')
        <link href="{{asset('template/rocker/assets/plugins/datatable/css/dataTables.bootstrap5.min.css')}}"
              rel="stylesheet"/>
    @endpush

    <!--breadcrumb-->
    <div class="page-breadcrumb d-none d-sm-flex align-items-center mb-3">
        <div class="breadcrumb-title pe-3">Master Data</div>
        <div class="ps-3">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb mb-0 p-0">
                    <li class="breadcrumb-item"><a href="{{route('adm.dashboard')}}"><i class="bx bx-home-alt"></i></a>
                    </li>
                    <li class="breadcrumb-item active" aria-current="page">Data Merchant DSC</li>
                </ol>
            </nav>
        </div>
        <div class="ms-auto">
            <a href="{{route('adm.merchant.add')}}">
                <button type="button" class="btn btn-primary">Add</button>
            </a>
        </div>
    </div>
    <!--end breadcrumb-->
    <h6 class="mb-0 text-uppercase">Master Data Merchant</h6>
    <hr/>
    <div class="card">
        <div class="card-body">
            <div class="table-responsive">
                <table id="tabelMerchant" class="table table-striped table-bordered" style="width:100%">
                    <thead class="table-light">
                    <tr>
                        <th class="text-center">No.</th>
                        <th class="text-center">Name</th>
                        <th class="text-center">Email</th>
                        <th class="text-center">Phone</th>
                        <th class="text-center">Status</th>
                        <th class="text-center">Action</th>
                    </tr>
                    </thead><!-- end thead -->
                    <tbody>
                    </tbody>
                </table> <!-- end table -->
            </div>
        </div>
    </div>
        <!-- Start: Modal Hapus -->
        <div id="modalHapus" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="modal-standard-title"
             aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="modal-standard-title">Hapus Merchant</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div> <!-- // END .modal-header -->
                    <form action="{{route('adm.merchant.delete')}}" method="post">
                        @csrf
                        @method('delete')
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <input type="hidden" id="hapusmerchant_id" name="merchant_id"/>
                                    Anda ingin menghapus data ini?
                                </div>
                            </div>

                        </div> <!-- // END .modal-body -->
                        <div class="modal-footer">
                            <button type="button" class="btn btn-light" data-bs-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-danger">Hapus</button>
                        </div>
                    </form>
                </div> <!-- // END .modal-content -->
            </div> <!-- // END .modal-dialog -->
        </div>
        <!-- End: Modal Hapus        -->

    @push('customJS')
        <script src="{{asset('template/rocker/assets/plugins/datatable/js/jquery.dataTables.min.js')}}"></script>
        <script src="{{asset('template/rocker/assets/plugins/datatable/js/dataTables.bootstrap5.min.js')}}"></script>
        <x-upcube.toast.toast-message />

        <script>

            /** saat tombol hapus di klik */
            $(document).on("click", ".open-hapus", function (e) {
                e.preventDefault();
                let fid = $(this).data('id');
                $('#hapusmerchant_id').val(fid);
            });

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
