$(function edit() {
	// HTMLから受け取るデータです。
	var data = {
		requestQuery : $('#js_edit_input').val()
	};

	$
			.ajax({
				type : "POST",
				url : "/myCart/DepartmentDeleteServlet",
				data: data,
				datatype : 'json',
				success : function(json) {
					// 確認
					console.log(json)


				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					// サーバーとの通信に失敗した時䛾処理
					alert('データの通信に失敗しました');
					console.log(errorThrown)
				}

			});

});

$(document).ready(function() {
	$('#js_edit_button').click(edit)
})