// AjaxでJSONを取得する
function executeAjax() {
	'use strict';

	// ?以降のパラメータを取得
	// 今回で言うとhttp://localhost:8080/wt1/hobby.html?q=0001でいう0001が取得される
	var parameter = location.search.substring(1, location.search.length);
	parameter = decodeURIComponent(parameter);
	parameter = parameter.split('=')[1];

	var requestQuery = {
		q : parameter
	};
	console.log("ok")
	$.ajax({
				type : 'GET',
				url : 'http://localhost:8080/myCart/LoginServlet',
//				async : false,
				dataType : 'json',
				data : requestQuery,
				success : function(json) {
					// 確認
					console.log(json)
					for (var i = 0; i < json.length; i++) {

						var element = json[i];

						var url = 'departmentEdit.html?q='
								+ element.departmentId;

						var eve = 'location.href=';

						var record = '<tr>'
								+ '<td>'
								+ element.departmentId
								+ '</td>'
								+ '<td>'
								+ element.departmentName
								+ '</td><td><button onclick="'
								+ eve
								+ "'"
								+ url
								+ "'"
								+ '"'

								+ '" type="button">編集</button></td><td><button id="delete'
								+ element.departmentId + '" departmentId="'
								+ element.departmentId + '">削除</button></td>'

						$('#table_data').append(record)

						$("#delete" + element.departmentId).bind('click',
								deleteAjax);
					}
				}
			});
}

function deleteAjax() {
	console.log($(this).attr('departmentId'));
	var requestQuery = {
		departmentId : $(this).attr('departmentId')
	};
	'use strict';
	$.ajax({
		type : 'POST',
		url : '/myCart/DepartmentServlet',
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