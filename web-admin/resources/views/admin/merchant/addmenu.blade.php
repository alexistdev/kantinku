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
                    <li class="breadcrumb-item" aria-current="page">Data Merchant: {{$dataMerchant->name}}</li>
                    <li class="breadcrumb-item" aria-current="page">Menu</li>
                    <li class="breadcrumb-item active" aria-current="page">Tambah</li>
                </ol>
            </nav>
        </div>
        <div class="ms-auto">
            <button type="button" class="btn btn-primary">Add</button>
        </div>
    </div>
    <!--end breadcrumb-->
    <h6 class="mb-0 text-uppercase">Data Menu</h6>
    <hr/>
    <div class="row">
        <div class="col-md-6">
            <div class="card">
                <div class="card-body">
                    <form action="{{route('adm.merchant.menu.save',$id)}}" method="post">
                        @csrf
                        <!-- Start : Nama Menu -->
                        <div class="row">
                            <div class="col-lg-12">
                                <label for="namaMenu" class="form-label labelError  @error('namaMenu')text-danger @enderror">Nama Menu</label>
                                <input  class="form-control inputError @error('namaMenu')is-invalid @enderror" type="text" name="namaMenu" placeholder="Nama Kantin"
                                       id="namaMenu"/>
                                @error('namaMenu')
                                <p class="text-danger errorMessage">{{ $message }}</p>
                                @enderror
                            </div>
                        </div>
                        <!-- End : Nama Menu -->

                        <!-- Start : harga -->
                        <div class="row mt-3">
                            <div class="col-lg-12">
                                <label for="tipe" class="form-label labelError @error('tipe')text-danger @enderror">Tipe Menu</label>
                                <select name="tipe" id="tipe" class="form-control inputError @error('tipe')is-invalid @enderror">
                                    <option value="">PILIH</option>
                                    <option value="1" @if(old('tipe') == "1") selected @endif>Makanan</option>
                                    <option value="2" @if(old('tipe') == "2") selected @endif>Minuman</option>
                                </select>

                                @error('tipe')
                                <p class="text-danger errorMessage">{{ $message }}</p>
                                @enderror
                            </div>
                        </div>
                        <!-- End : harga -->

                        <!-- Start : harga -->
                        <div class="row mt-3">
                            <div class="col-lg-12">
                                <label for="harga" class="form-label labelError @error('harga')text-danger @enderror">Harga</label>
                                <input class="form-control inputError @error('harga')is-invalid @enderror" type="number" name="harga" placeholder="Harga"
                                       id="harga"/>
                                @error('harga')
                                <p class="text-danger errorMessage">{{ $message }}</p>
                                @enderror
                            </div>
                        </div>
                        <!-- End : harga -->

                        <!-- Start: Tombol Simpan -->
                        <div class="row mt-3">
                            <div class="col-md-6">
                                <button type="submit" class="btn btn-sm btn-success">SIMPAN</button>
                                <a href="{{route('adm.merchant.menu',$id)}}">
                                    <button type="button" class="btn btn-sm btn-danger">BATAL</button>
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
