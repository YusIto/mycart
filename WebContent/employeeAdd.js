function add() {


		var isid =$('#inputSyainId').val()
		var isname =$('#inputSyainName').val()
		var isage =$('#inputSyainAge').val()
		var issex =$('#inputSyainSex').val()
		var ispostalcode =$('#inputSyainPostalcode').val()
		var isprefecture =$('#inputSyainPrefecture').val()
		var isaddress =$('#inputSyainAddress').val()
//		var isdepartmentid =$('#inputSyaindepartmentid').val()
		var isdepartmentname =$('#inputSyainDepartmentname').val()
		var isnyuusya =$('#inputSyainNyuusya').val()
		var istaisya =$('#inputSyainTaisya').val()

		var requestQuery = { syainId: isid,
			syainName: isname,
			syainAge: isage,
			syainSex: issex,
			syainPostalcode: ispostalcode,
			syainPrefecture: isprefecture,
			syainAddress: isaddress,
//			syainDepartmentid: isdepartmentid,
			syainDepartmentname: isdepartmentname,
			syainNyuusya: isnyuusya,
			syainTaisya: istaisya
		}
		//コンソールでrequestQueryを確認
		console.log(requestQuery)
	$
			.ajax({
				type : "POST",
				url : "http://localhost:8080/myCart/EmployeeAddServlet",
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
