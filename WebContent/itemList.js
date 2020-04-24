/* 商品名から商品情報を取得するファンクション */
function getItemByName() {
	/**
	 * 【general01】
	 *  ①idが「js-search-input」の要素（検索ボックス）の値を取得
	 *  ②「ItemSearchServlet」に対してAjaxでリクエストを送信します。
	 *   - GETメソッド
	 *   - data（送信データ）：取得した検索ボックスの値を「itemName」というキーで送る
	 *   - success（成功時の処理）：直接書いたHTMLの商品の部分がサーバーからの取得結果によって生成されるようにする。
	 *     以下の部分が商品分作られ、idが「js-item-list」の要素内に追加される
	 *   	<div class="grid">
	 *			<div class="image" style="background-image: url(./images/mango.jpg)"></div>
	 *			<div class="itemName">
	 *				<a href="./itemDetail.html?itemCd=0002">輸入 アップルマンゴー 1個</a>
	 *			</div>
	 *		</div>
	 *
	 **/
	/** general01 実装ここから part2 **/

	// 入力された商品名
	var inputItemName = $('#js-search-input').val();
	var requestQuery = {
	itemName : inputItemName
	};
	// サーバーからデータを取得する
	$.ajax({
	type : 'GET',
	dataType : 'json',
	url : '/myCart/ItemSearchServlet',
	data : requestQuery,
	success : function(json) {
		// サーバーと䛾通信に成功した時䛾処理
		// 確認䛾ために返却値を出力
		console.log('返却値', json);
		// 取得したデータを画面に表示する
		// HTML䛾内容を文字列結合で生成する。
		var tableElemnt = '';
		for (var i=0; i < json.length; i++) {
		var item = json[i];
		tableElemnt += '<div class="grid">';
		tableElemnt += '<div class="image" style="background-image:url('+item.url+')"></div>';
		tableElemnt += '<div class="itemName"><ahref=./itemDetail.html?itemCd='+item.itemCd+'>'+item.itemName+'</a></div>';
		tableElemnt += '</div>';
		}
		// HTMLに挿入
		$('#js-item-list').html(tableElemnt);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		// サーバーと䛾通信に失敗した時䛾処理
		alert('データ䛾通信に失敗しました');
		console.log(errorThrown)
		}
		});
	/** general01 実装ここまで part2 **/
}
/* ユーザー名を表示するファンクション */
function setUserName() {
	/**
	 * 【localStorage01】
	 *  ・ローカルストレージから「userName」というキーの値を取り出しましょう。
	 *  ・idが「js-user-name」の要素内にローカルストレージから取得したユーザー名に「さん」を付けて表示しましょう。
	 **/
	/** localStorage01 実装ここから part2 **/
	var userName = localStorage.getItem('userName');
	$('#js-user-name').html(userName+'さん');
	/** localStorage01 実装ここまで part2 **/

}
/**
 * 読み込み時の動作
 */
$(document).ready(function() {
	// ユーザー名を表示
	setUserName();

	/**
	 * 【general01】
	 *  ・idが「js-search-button」の要素（検索ボタン）をクリックしたときに、
	 *    「getItemByName」メソッドが実行されるように実装しましょう。
	 **/
	/** general01 実装ここから part1 **/
	$('#js-search-button').click(getItemByName);
	/** general01 実装ここから part1 **/


});