/* グローバル変数 */
// URLパラメータ
var arg = new Object;

/*  URLパラメータを取得するファンクション */
var getUrlParameter = function () {
	var keyValPair = location.search.substring(1);
	var decodeKeyValPair = decodeURIComponent(keyValPair);
	var pair=decodeKeyValPair.split('&');
	for(var i=0;pair[i];i++) {
	    var kv = pair[i].split('=');
	    arg[kv[0]]=kv[1];
	}
	console.log('URLパラメータ',arg);
}

/*  画像を拡大するファンクション */
var scalUpImage = function () {
	$('#js-item-image').addClass("expand");
}
/* 画像を縮小するファンクション */
var scaldownImage = function () {
	$('#js-item-image').removeClass("expand");
}

/* 商品情報を取得するファンクション */
var getItem  = function () {
	var requestQuery = {
		itemCd : arg.itemCd
	};
	// サーバーからデータを取得する
	$.ajax({
		type : 'GET',
		dataType:'json',
		url : '/myCart/ItemDetailServlet',
		data : requestQuery,
		success : function(json) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力
			console.log('返却値', json);
			// 取得したデータを画面に表示する
			$('#js-item-image').attr('src', json.url);
			$('#js-item-cd').html(json.itemCd);
			$('#js-item-name').html(json.itemName);
			$('#js-item-kana').html(json.itemNameKana);
			$('#js-item-price').html(json.salesPrice);
			$('#js-item-stock').html(json.stock + '個');
			$('#js-item-description').html(json.description);
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
			console.log(errorThrown)
		}
	});
	/** jQueryを使用しないでHTTPリクエストを送る処理**/
//	// XMLHttpRequestオブジェクトを生成する
//	var req = new XMLHttpRequest();
//	// サーバ応答時の処理を定義する
//	req.onreadystatechange = function() {
//		  if (req.readyState == 4) { // 通信の完了時
//		    if (req.status == 200) { // 通信の成功時
//		    	var json = JSON.parse(req.responseText);
//				// 確認のために返却値を出力
//				console.log('返却値', json);
//				// 取得したデータを画面に表示する
//				$('#js-item-image').attr('src', json.url);
//				$('#js-item-cd').html(json.itemCd);
//				$('#js-item-name').html(json.itemName);
//				$('#js-item-kana').html(json.itemNameKana);
//				$('#js-item-price').html(json.salesPrice);
//				$('#js-item-stock').html(json.stock + '個');
//				$('#js-item-description').html(json.description);
//		    }
//		  }else{
//		    // 通信中の処理
//		  }
//		}
//	// リクエストを送信
//	var url = '/myCart/ItemDetailServlet?itemCd='+arg.itemCd;
//	req.open('GET', url, true);
//	req.send(null);
}


/**
 * 読み込み時の動作
 */
$(document).ready(function() {
	// 初回実行
	getUrlParameter();
	getItem();

	// 画像にマウスオーバーイベント設定
	$('#js-item-image').mouseover(scalUpImage);
	$('#js-item-image').mouseout(scaldownImage);


});