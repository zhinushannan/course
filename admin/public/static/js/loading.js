Loading = {

    show() {
        $.blockUI({
            message: '<img src="/static/image/loading.gif"  alt=""/>',
            css: {
                padding: "10px",
                left: "50%",
                width: "80px",
                marginLeft: "-40px"
            }
        });
    },

    hide() {
        setTimeout(function () {
            $.unblockUI()
        }, 500)
    }

}