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
    @if ($message = Session::get('warning'))
        <script>
            let pesan = '{!! $message !!}';
            Lobibox.notify('warning', {
                pauseDelayOnHover: true,
                continueDelayOnInactiveTab: false,
                size: 'mini',
                position: 'top right',
                icon: 'bx bx-info-circle',
                msg: pesan,
            });
        </script>
    @endif
    @if($errors->any())
        <script>
            let pesan = '{!! $errors->first() !!}';
            Lobibox.notify('error', {
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
