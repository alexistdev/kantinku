<div>
    <header>
        <div class="topbar d-flex align-items-center">
            <nav class="navbar navbar-expand">
                <div class="mobile-toggle-menu"><i class='bx bx-menu'></i>
                </div>
                <div class="search-bar flex-grow-1">
                    <div class="position-relative search-bar-box">

                        <span class="position-absolute top-50 search-close translate-middle-y"><i class='bx bx-x'></i></span>
                    </div>
                </div>

                <div class="user-box dropdown">
                    <a class="d-flex align-items-center nav-link dropdown-toggle dropdown-toggle-nocaret" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">

                        <div class="user-info ps-3">
                            <p class="user-name mb-0">Administrator</p>
                        </div>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end">

                        <li><a class="dropdown-item" href="javascript:;"><i class="bx bx-cog"></i><span>Settings</span></a>
                        </li>

                        <li>
                            <div class="dropdown-divider mb-0"></div>
                        </li>
                        <li>
                            <form method="POST" action="{{ route('logout') }}">
                                @csrf
                                <a class="dropdown-item" href="{{route('logout')}}" onclick="event.preventDefault();
                                                this.closest('form').submit();"><i class='bx bx-log-out-circle'></i><span>Logout</span>
                                </a>
                            </form>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
    </header>
</div>
