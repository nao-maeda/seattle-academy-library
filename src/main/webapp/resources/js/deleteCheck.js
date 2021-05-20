/**
 * 
 */
$(function() {

	$('#deleteButton').on('click', function() {

		if (!confirm('本当に削除しますか？')) {
			/* キャンセルの時の処理 */
			return false;
		} else {
			/*　OKの時の処理 */
			location.href = 'index.html';
		}

	});

});