$(function edit() {

	var data = {
		requestQuery : $('#js_edit_input').val()
	};

	$
			.ajax({
				type : "POST",
				url : "/myCart/DepartmentEditServlet",
				data: data,
				datatype : 'json',
				success : function(json) {

					// サーバーとの通信に成功した時の処理
					// 確認のために返却値を出力
					console.log('返却値', json);
					// 登録完了のアラート
					alert('登録が完了しました');
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					// サーバーとの通信に失敗したときの処理
					alert('データの通信に失敗しました');
					console.log(errorThrown)
				}

			});

});

$(document).ready(function() {
	$('#js_edit_button').click(edit)
});