<div>
    @if ($message = Session::get('success'))
        <script>
            let pesan = '{!! $message !!}';
            Lobibox.notify('success', {
                pauseDelayOnHover: true,
                continueDelayOnInactiveTab: false,
                size: 'mini',
                position: 'top right',
                icon: 'bx bx-info-circle',
                msg: pesan,
            });
        </script>
    @endif
</div>
