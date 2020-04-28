function edit() {

	// ?以降のパラメータを取得
	// 今回で言うとhttp://localhost:8080/wt1/hobby.html?q=0001でいう0001が取得される
	var parameter = location.search.substring(1, location.search.length);
	parameter = decodeURIComponent(parameter);
	parameter = parameter.split('=')[1];

	var inputDepartmentName = $('#js_input_name').val()

	var requestQuery = {
		departmentId : parameter,
		departmentName : inputDepartmentName
	}

	console.log(requestQuery)
	$.ajax({
		type : "POST",
		url : "http://localhost:8080/myCart/DepartmentEditServlet",
		data : requestQuery,
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
}

$(document).ready(function() {
	$('#js_edit_button').click(edit)
});