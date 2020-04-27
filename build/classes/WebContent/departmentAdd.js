function add() {


		var inputDepartmentId =$('#js_input_id').val()
		var inputDepartmentName =$('#js_input_name').val()

		var requestQuery = { departmentId:inputDepartmentId, departmentName : inputDepartmentName }
		console.log(requestQuery)
	$
			.ajax({
				type : "POST",
				url : "http://localhost:8080/myCart/DepartmentAddServlet",
				data: requestQuery,
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
	$('#js_add_button').click(add)
});