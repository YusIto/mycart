// AjaxでJSONを取得する
function executeAjax() {
	'use strict';

	// ?以降のパラメータを取得
	// 今回で言うとhttp://localhost:8080/wt1/hobby.html?q=0001でいう0001が取得される
	var parameter = location.search.substring(1, location.search.length);
	parameter = decodeURIComponent(parameter);
	parameter = parameter.split('=')[1];

	// --------------- TODO 編集ここから---------------

	var requestQuery = {
		q : parameter
	};

	$.ajax({
		type : 'GET',
		url : '/myCart/DepartmentServlet',
		dataType : 'json',
		data : requestQuery,
		success : function(json) {
			// 確認
			console.log(json)
			for (var i = 0; i < json.length; i++) {

				var element = json[i];

				var record = '<tr>' + '<td>' + element.departmentId + '</td>' + '<td>' + element.departmentName + '</td><td><button onclick="urlq="'+ element.departmentName +'" type="button">編集</button></td><td><button onclick="urlq="'+ element.departmentName +'" type="button">削除</button></td></tr>';

				$('#table_data').append(record)
			}
		}
	});

	// ---------------ここまで---------------

}

$(document).ready(function() {
	'use strict';

	// 初期表示用
	executeAjax();

	// 更新ボタンにイベント設定
	$('#searchBtn').bind('click', executeAjax);

});