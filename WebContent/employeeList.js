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
					for (var i = 0; i < json.data.length; i++) {

						var element = json.data[i];

						var url = 'employeeEdit.html?q=' + element.id;

						var eve = 'location.href=';

						var record = '<tr>'
								+ '<td>'
								+ element.id
								+ '</td>'
								+ '<td>'
								+ element.name
								+ '</td><td><button onclick="'
								+ eve
								+ "'"
								+ url
								+ "'"
								+ '"'

								+ '" type="button">編集</button></td><td><button id="delete'
								+ element.id + '" syainId="' + element.id
								+ '">削除</button></td>'

						$('#table_data').append(record)

						$("#delete" + element.id).bind('click', deleteAjax);
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

/* 社員名から社員検索するファンクション */
function searchSyain() {

	// 入力された部署
	var inputSyainDepartment = $('#js-search-input-department').val();
	console.log('所属部署', inputSyainDepartment);
	// 入力された社員ID
	var inputSyainId = $('#js-search-input-id').val();
	console.log('社員ID', inputSyainId);
	// 入力された商品名
	var inputSyainName = $('#js-search-input-name').val();
	console.log('社員名', inputSyainName);

	var requestQuery = {
		syainDepartmentname : inputSyainDepartment,
		syainId : inputSyainId,
		syainName : inputSyainName
	};

	// 確認
	console.log(requestQuery)

	// サーバーからデータを取得する
	$
			.ajax({
				type : 'GET',
				dataType : 'json',
				url : '/myCart/EmployeeSearchServlet',
				data : requestQuery,
				success : function(json) {
					// サーバーとの通信に成功した時の処理
					// 確認のために返却値を出力
					console.log('searchSyainの返却値', json);

					var role = localStorage.getItem('role');
					console.log(role);

					if(role = 'MANEGER'){

					// ここでリセット
					var table = document.getElementById('table_data')
					console.log(table)
					while (table.rows[0])
						table.deleteRow(0);

					$('#table_data').append('<tr><th>社員ID</th><th>名前</th></tr>')

					for (var i = 0; i < json.length; i++) {
						var element = json[i];

						var url = 'employeeEdit.html?q=' + element.id;

						var eve = 'location.href=';

						var record = '<tr>'
								+ '<td>'
								+ element.id
								+ '</td>'
								+ '<td>'
								+ element.name
								+ '</td><td><button onclick="'
								+ eve
								+ "'"
								+ url
								+ "'"
								+ '"'

								+ '" type="button">編集</button></td><td><button id="delete'
								+ element.id + '" syainId="' + element.id
								+ '">削除</button></td>'

						$('#table_data').append(record)

						$("#delete" + element.id).bind('click', deleteAjax);
					}
					}else{
						console.log('MEMBERのほうのほうに入った確認')


						// ここでリセット
						var table = document.getElementById('table_data')
						console.log(table)
						while (table.rows[0])
							table.deleteRow(0);

						$('#table_data').append('<tr><th>社員ID</th><th>名前</th></tr>')

						for (var i = 0; i < json.length; i++) {
							var element = json[i];

							var url = 'employeeEdit.html?q=' + element.id;

							var eve = 'location.href=';

							var record = '<tr>'
									+ '<td>'
									+ element.id
									+ '</td>'
									+ '<td>'
									+ element.name
									+ '</td><td></td><td></td>'

							$('#table_data').append(record)

//							$("#delete" + element.id).bind('click', deleteAjax);
						}

					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					// サーバーとの通信に失敗した時の処理
					alert('データの通信に失敗しました');
					console.log(errorThrown)
				}
			});
}

function logoutAjax() {

	$.ajax({
		type : 'GET',
		url : '/myCart/LogoutServlet',
//		dataType : 'json',
//		data : requestQuery,
		success : function(json) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力
			console.log('返却値', json);
			window.location.href="http://localhost:8080/myCart/login.html";
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
			console.log(errorThrown)
		}
	});
}

// 読み込み時の動作

function reload() {
	location.reload();
}

$(document).ready(function() {
	'use strict';

	// 初期表示用
	executeAjax();

	// 商品番号検索ボタンを押したときのイベント
	$('#js-search-button').click(searchSyain);

	//ログアウトボタンを押したときのイベント
	$('#logout').click(logoutAjax);

});