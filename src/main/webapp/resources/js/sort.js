/**
 * 
 */
$(function() {
	$('#item').change(function() {
		//セレクタを選択した時
	
		var result = $('option:selected').val();
		var array = [];
		$(".books").each(function(i) {//全ての項目データを取得
			array[i] = {
				title: $(this).find(".book_title").text(),
				author: $(this).find(".book_aouthor").text(),
				publisher: $(this).find(".book_publisher").text(),
				publishDate: $(this).find(".book_publishDate").text(),
				source: $(this).html()
			};
		});

		if  (result == 0){
			//書籍名をセレクト
			if ($("#ascbtn:checked").prop("checked")) {
				//昇順が選択されているとき
				//昇順に並び替え
				array.sort(function(a, b) {
					if (a.title > b.title) {
						return 1;
					} else if (a.title < b.title) {
						return -1;
					} else {
						return 0;
					}
				});
				console.log(array);
			} else if ($("#descbtn:checked").prop("checked")) {
				//降順が選択されているとき
				//降順に並び替え
				array.sort(function(a, b) {
					if (a.title < b.title) {
						return 1;
					} else if (a.title > b.title) {
						return -1;
					} else {
						return 0;
					}
				});
				console.log(array);
			}
		}

		if (result == 1) {
			//著者名をセレクト
			if ($("#ascbtn:checked").prop("checked")) {
				//昇順に並び替え
				array.sort(function(a, b) {
					if (a.author > b.author) {
						return 1;
					} else if (a.author < b.author) {
						return -1;
					} else {
						return 0;
					}
				});
			} else if ($("#descbtn:checked").prop("checked")) {
				//降順に並び替え
				array.sort(function(a, b) {
					if (a.author < b.author) {
						return 1;
					} else if (a.author > b.author) {
						return -1;
					} else {
						return 0;
					}
				});
			}
		}

		if (result == 2) {
			//出版社をセレクト
			if ($("#ascbtn:checked").prop("checked")) {
				//昇順に並び替え
				array.sort(function(a, b) {
					if (a.publisher > b.publisher) {
						return 1;
					} else if (a.publisher < b.publisher) {
						return -1;
					} else {
						return 0;
					}
				});
			} else if ($("#descbtn:checked").prop("checked")) {
				//降順に並び替え
				array.sort(function(a, b) {
					if (a.publisher < b.publisher) {
						return 1;
					} else if (a.publisher > b.publisher) {
						return -1;
					} else {
						return 0;
					}
				});
			}
		}

		if (result == 3) {
			//出版日をセレクト
			if ($("#ascbtn:checked").prop("checked")) {
				//昇順に並び替え
				array.sort(function(a, b) {
					if (a.publishDate > b.publishDate) {
						return 1;
					} else if (a.publishDate < b.publishDate) {
						return -1;
					} else {
						return 0;
					}
				});
			} else if ($("#descbtn:checked").prop("checked")) {
				//降順に並び替え
				array.sort(function(a, b) {
					if (a.publishDate < b.publishDate) {
						return 1;
					} else if (a.publishDate > b.publishDate) {
						return -1;
					} else {
						return 0;
					}
				});
			}
		}
		for (var j = 0; j < array.length; j++) {
			$(".books").eq(j).html(array[j].source);
		}
	});
	
	
	$('input:radio[name="sortCheckBox"]').change(function() {
		//昇順降順のradioboxを選択した時
		
		var result = $('option:selected').val();
		const str1 = $('input:radio[name="sortCheckBox"]:checked').val();
		var array = [];
		$(".books").each(function(i) {
			array[i] = {
				title: $(this).find(".book_title").text(),
				author: $(this).find(".book_author").text(),
				publisher: $(this).find(".book_publisher").text(),
				publishDate: $(this).find(".book_publishDate").text(),
				source: $(this).html()
			};
		});

		if  (str1 == 'asc'){
			//昇順にチェックを入れた時
			if (result == 0) {
				//書籍名が選択されている場合
				//書籍名の昇順を表示
				array.sort(function(a, b) {
					if (a.title > b.title) {
						return 1;
					} else if (a.title < b.title) {
						return -1;
					} else {
						return 0;
					}
				});
				console.log(array);
			} else if (result == 1) {
				//著者名が選択されている場合
				//著者名の昇順を表示
				array.sort(function(a, b) {
					if (a.author > b.author) {
						return 1;
					} else if (a.author < b.author) {
						return -1;
					} else {
						return 0;
					}
				});
				console.log(array);
			} else if (result == 2) {
				//出版社が選択されている場合
				//出版社の昇順を表示
				array.sort(function(a, b) {
					if (a.publisher > b.publisher) {
						return 1;
					} else if (a.publisher < b.publisher) {
						return -1;
					} else {
						return 0;
					}
				});
				console.log(array);
			} else if (result == 3) {
				//出版日が選択されている場合
				//出版日の昇順を表示
				array.sort(function(a, b) {
					if (a.publishDate > b.publishDate) {
						return 1;
					} else if (a.publishDate < b.publishDate) {
						return -1;
					} else {
						return 0;
					}
				});
				console.log(array);
			}
		}
		
		if (str1 == 'desc'){
			//降順にチェックを入れた時
			if (result == 0) {
				//書籍名が選択されている場合
				//書籍名の降順を表示
				array.sort(function(a, b) {
					if (a.title < b.title) {
						return 1;
					} else if (a.title > b.title) {
						return -1;
					} else {
						return 0;
					}
				});
				console.log(array);
			} else if (result == 1) {
				//著者名が選択されている場合
				//著者名の降順を表示
				array.sort(function(a, b) {
					if (a.author < b.author) {
						return 1;
					} else if (a.author > b.author) {
						return -1;
					} else {
						return 0;
					}
				});
				console.log(array);
			} else if (result == 2) {
				//出版社が選択されている場合
				//出版社の降順を表示
				array.sort(function(a, b) {
					if (a.publisher < b.publisher) {
						return 1;
					} else if (a.publisher > b.publisher) {
						return -1;
					} else {
						return 0;
					}
				});
				console.log(array);
			} else if (result == 3) {
				//出版日が選択されている場合
				//出版日の降順を表示
				array.sort(function(a, b) {
					if (a.publishDate < b.publishDate) {
						return 1;
					} else if (a.publishDate > b.publishDate) {
						return -1;
					} else {
						return 0;
					}
				});
				console.log(array);
			} 
		}
		for (var j = 0; j < array.length; j++) {
			$(".books").eq(j).html(array[j].source);
		}
	});
});