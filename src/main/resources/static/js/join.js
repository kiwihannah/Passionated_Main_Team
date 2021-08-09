/* 아이디 중복 체크 : ajax 비동기처리 */
function idCheck() {

    var username = $("#username").val();

    if(username.search(/\s/) != -1) {
        alert("아이디에는 공백이 들어갈 수 없습니다.");
    } else {
        if(username.trim().length != 0) {
            $.ajax({
                async : true,
                type : 'GET',
                data: username,
                url: "/exists/" + username,
                dataType: "json",
                contentType: "application/json; charset=UTF-8",
                success: function(data) {
                    if(data != false) {
                        swal("중복된 아이디!", "다른 아이디를 사용해 주세요", "error");
                        $("#submit").attr("disabled", "disabled");
                        <!--window.location.reload();-->
                    } else {
                        swal("사용가능 아이디!", "중복되지 않았어요", "success");
                        $("#submit").removeAttr("disabled");
                    }
                }
            });
        }
    }
}