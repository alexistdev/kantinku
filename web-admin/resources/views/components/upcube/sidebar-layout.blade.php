<!--sidebar wrapper -->
<div class="sidebar-wrapper" data-simplebar="true">
    <div class="sidebar-header">
        <div>
            <img src="assets/images/logo-icon.png" class="logo-icon" alt="logo icon">
        </div>
        <div>
            <h4 class="logo-text">Rocker</h4>
        </div>
        <div class="toggle-icon ms-auto"><i class='bx bx-arrow-to-left'></i>
        </div>
    </div>
    <!--navigation-->
    <ul class="metismenu" id="menu">
        <li>
            <a href="{{route('adm.dashboard')}}">
                <div class="parent-icon"><i class='bx bx-home-circle'></i>
                </div>
                <div class="menu-title">Dashboard</div>
            </a>
        </li>
        <li>
            <a href="#">
                <div class="parent-icon"><i class='bx bx-cart-alt'></i>
                </div>
                <div class="menu-title">Transaksi</div>
            </a>
        </li>

        <li class="menu-label">MASTER DATA</li>
        <li>
            <a href="{{route('adm.merchant')}}">
                <div class="parent-icon"><i class='bx bx-store'></i>
                </div>
                <div class="menu-title">Penjual DSC</div>
            </a>
        </li>
        <li>
            <a href="widgets.html">
                <div class="parent-icon"><i class='bx bx-user'></i>
                </div>
                <div class="menu-title">Pembeli</div>
            </a>
        </li>

    </ul>
    <!--end navigation-->
</div>
<!--end sidebar wrapper -->
