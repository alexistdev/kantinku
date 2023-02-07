<x-upcube.template-layout >
    <!--breadcrumb-->
    <div class="page-breadcrumb d-none d-sm-flex align-items-center mb-3">
        <div class="breadcrumb-title pe-3">Setting</div>
        <div class="ps-3">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb mb-0 p-0">
                    <li class="breadcrumb-item"><a href="{{route('adm.dashboard')}}"><i class="bx bx-home-alt"></i></a>
                    </li>
                    <li class="breadcrumb-item active" aria-current="page">Setting</li>
                </ol>
            </nav>
        </div>

    </div>
    <!--end breadcrumb-->
    <h6 class="mb-0 text-uppercase">Setting</h6>
    <hr/>
    <div class="card col-md-6">
        <div class="card-body">

                <div class="row mt-3">
                    <div class="col-md-12">
                        <div class="row">
                            <div class="col-md-12">
                                <label
                                    for="jarak" @class(["form-label","errorLabel",($errors->has('jarak'))? "text-danger":""]) >EMAIL</label>
                                <input type="email" max="5000" name="jarak" maxlength="125" value="admin@gmail.com"
                                       @class(["form-control","errorInput",($errors->has('jarak'))? "is-invalid":""]) id="jarak"
                                >
                                @if($errors->has('jarak'))
                                    <span
                                        class="text-danger errorMessage">{{$errors->first('jarak')}}</span>
                                @endif
                            </div>
                        </div>
                        <div class="row mt-3">
                            <div class="col-md-12">
                                <label
                                    for="jarak" @class(["form-label","errorLabel",($errors->has('jarak'))? "text-danger":""]) >PASSWORD</label>
                                <input type="password" max="5000" name="jarak" maxlength="125"
                                       @class(["form-control","errorInput",($errors->has('jarak'))? "is-invalid":""]) id="jarak" placeholder="password"
                                >
                                @if($errors->has('jarak'))
                                    <span
                                        class="text-danger errorMessage">{{$errors->first('jarak')}}</span>
                                @endif
                            </div>
                        </div>
                        <div class="row mt-3">
                            <div class="col-md-12">
                                <button class="btn btn-primary">SIMPAN</button>
                            </div>
                        </div>
                    </div>
                </div>

        </div>
    </div>


</x-upcube.template-layout>
