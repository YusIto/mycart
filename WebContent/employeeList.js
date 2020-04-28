// AjaxでJSONを取得する
function executeAjax() {
	'use strict';

	// ?以降のパラメータを取得

	var parameter = location.search.substring(1, location.search.length);
	parameter = decodeURIComponent(parameter);
	parameter = parameter.split('=')[1];

	var requestQuery = {
		q : parameter
	};

	$
			.ajax({
				type : 'GET',
				url : '/myCart/EmployeeListServlet',
				async : false,
				dataType : 'json',
				data : requestQuery,
				success : function(json) {
					// 確認
					console.log(json)
					for (var i = 0; i < json.length; i++) {

						var element = json[i];

						var url = 'employeeEdit.html?q='
								+ element.syainId;

						var eve = 'location.href=';

						var record = '<tr>'
								+ '<td>'
								+ element.syainId
								+ '</td>'
								+ '<td>'
								+ element.syainName
								+ '</td><td><button onclick="'
								+ eve
								+ "'"
								+ url
								+ "'"
								+ '"'

								+ '" type="button">編集</button></td><td><button id="delete'
								+ element.syainId + '" syainId="'
								+ element.syainId + '">削除</button></td>'

						$('#table_data').append(record)

						$("#delete" + element.syainId).bind('click',
								deleteAjax);
					}
				}
			});
}

function deleteAjax() {
	console.log($(this).attr('syainId'));

	var requestQuery = {
		syainId : $(this).attr('syainId')
	};
	'use strict';
	$.ajax({
		type : 'POST',
		url : '/myCart/EmployeeListServlet',
		dataType : 'json',
		data : requestQuery,
		success : function(json) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力
			console.log('返却値', json);
			reload();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
			console.log(errorThrown)
		}
	});
}
function reload() {
	location.reload();
}

$(document).ready(function() {
	'use strict';

	// 初期表示用
	executeAjax();

});