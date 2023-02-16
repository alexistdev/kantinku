<!doctype html>
<html lang="en" class="semi-dark">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="author" content="AlexistDev">
    <meta name="description" content="Software Engineer">
    <title>{{$title}}</title>
    <x-upcube.header-layout />
    @stack('customCSS')
</head>

{{--<body onload="info_noti()">--}}
<body>
<!--wrapper-->
<div class="wrapper">
    <!--Start: Sidebar  -->
    <x-upcube.sidebar-layout />
    <!--End: Sidebar  -->

    <!--start: navbar -->
    <x-upcube.navbar-layout />
    <!--end: navbar -->

    <!--start page wrapper -->
    <div class="page-wrapper">
        <div class="page-content">
            {{$slot}}
        </div>
    </div>
    <!--end page wrapper -->
    <!--start overlay-->
    <div class="overlay toggle-icon"></div>
    <!--end overlay-->
    <!--Start Back To Top Button--> <a href="javaScript:;" class="back-to-top"><i class='bx bxs-up-arrow-alt'></i></a>
    <!--End Back To Top Button-->

    <!--Start : Footer-->
    <x-upcube.footer-layout />
    <!--End : Footer-->
</div>
<!--end wrapper-->


<!--Start: JS Layout -->
<x-upcube.js-layout />
<!--End: JS Layout -->
@stack('customJS')

</body>

</html>
