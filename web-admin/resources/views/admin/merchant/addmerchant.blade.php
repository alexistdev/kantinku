<x-upcube.template-layout>
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
                    <li class="breadcrumb-item" aria-current="page">Data Merchant DSC</li>
                    <li class="breadcrumb-item active" aria-current="page">Tambah</li>
                </ol>
            </nav>
        </div>
        <div class="ms-auto">
            <button type="button" class="btn btn-primary">Add</button>
        </div>
    </div>
    <!--end breadcrumb-->
    <h6 class="mb-0 text-uppercase">Master Data Merchant</h6>
    <hr/>
    <div class="row">
        <div class="col-md-6">
            <div class="card">
                <div class="card-body">
                    <form action="{{route('adm.merchant.save')}}" method="post">
                        @csrf
                        <!-- Start : Nama Kantin -->
                        <div class="row">
                            <div class="col-lg-12">
                                <label for="namaKantin" class="form-label labelError  @error('namaKantin')text-danger @enderror">Nama Kantin</label>
                                <input  class="form-control inputError @error('namaKantin')is-invalid @enderror" type="text" name="namaKantin" placeholder="Nama Kantin"
                                       id="namaKantin"/>
                                @error('namaKantin')
                                <p class="text-danger errorMessage">{{ $message }}</p>
                                @enderror
                            </div>
                        </div>
                        <!-- End : Nama Kantin -->

                        <!-- Start : Email -->
                        <div class="row mt-3">
                            <div class="col-lg-12">
                                <label for="email" class="form-label labelError @error('email')text-danger @enderror">Email</label>
                                <input class="form-control inputError @error('email')is-invalid @enderror" type="email" name="email" placeholder="Email"
                                       id="email"/>
                                @error('email')
                                <p class="text-danger errorMessage">{{ $message }}</p>
                                @enderror
                            </div>
                        </div>
                        <!-- End : Email -->

                        <!-- Start : Phone -->
                        <div class="row mt-3">
                            <div class="col-lg-12">
                                <label for="phone" class="form-label labelError @error('phone')text-danger @enderror">Phone</label>
                                <input class="form-control inputError @error('phone')is-invalid @enderror" type="text" name="phone" placeholder="Phone" id="phone"/>
                                @error('phone')
                                <p class="text-danger errorMessage">{{ $message }}</p>
                                @enderror
                            </div>
                        </div>
                        <!-- End : Phone -->

                        <!-- Start: Tombol Simpan -->
                        <div class="row mt-3">
                            <div class="col-md-6">
                                <button type="submit" class="btn btn-success">SIMPAN</button>
                                <a href="{{route('adm.merchant')}}">
                                    <button type="button" class="btn btn-danger">BATAL</button>
                                </a>
                            </div>
                        </div>
                        <!-- End: Tombol Simpan -->
                    </form>
                </div>
            </div>
        </div>
    </div>


    @push('customJS')
        <script src="{{asset('template/rocker/assets/plugins/datatable/js/jquery.dataTables.min.js')}}"></script>
        <script src="{{asset('template/rocker/assets/plugins/datatable/js/dataTables.bootstrap5.min.js')}}"></script>
        <script>

            $(document).ready(function () {
                let inputClass = document.getElementsByClassName("inputError");
                let labelClass = document.getElementsByClassName("labelError");
                let inputMessage = document.getElementsByClassName("errorMessage");
                let base_url = '{{route('adm.merchant')}}';


                /** remove error message on key up*/
                for(var i = 0; i < inputClass.length; i++) {
                    (function(index) {
                        inputClass[index].addEventListener("keyup", function() {
                            inputClass[index].classList.remove('is-invalid');
                            labelClass[index].classList.remove('text-danger');
                            inputMessage[index].innerHTML='';
                            inputMessage[index].classList.remove('text-danger');
                        })
                    })(i);
                }


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
