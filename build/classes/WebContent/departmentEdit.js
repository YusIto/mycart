$(function() {
	// 検索ボタンがクリックされたら処理が走ります。
	$('#js_edit_input').click(function() {
		// HTMLから受け取るデータです。
		var data = {
			request : $('#js_edit_button').val()};

		$.ajax({
			type : "POST",
			url : "/myCart/DepartmentServlet",
			datatype : 'json',
			// 処理が成功したら
			success : function(data, dataType) {
				// HTMLファイル内の該当箇所にレスポンスデータを追加します。
				$('#res').html(data);
			},
			// 処理がエラーであれば
			error : function() {
				alert('通信エラー');
			}
		});
		// submitによる画面リロードを防いでいます。
		return false;
	});
});