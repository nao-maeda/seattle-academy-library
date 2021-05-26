<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page contentType="text/html; charset=utf8"%>
<%@ page import="java.util.*"%>
<html>
<head>
<title>ホーム｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="resources/js/sort.js" /></script>
</head>
<body class="wrapper">
    <header>
        <div class="left">
            <img class="mark" src="resources/img/logo.png" />
            <div class="logo">Seattle Library</div>
        </div>
        <div class="right">
            <ul>
                <li><a href="<%=request.getContextPath()%>/home" class="menu">Home</a></li>
                <li><a href="<%=request.getContextPath()%>/">ログアウト</a></li>
            </ul>
        </div>
    </header>
    <main>
        <h1>Home</h1>
        <a href="<%=request.getContextPath()%>/addBook" class="btn_add_book">書籍の追加</a> 
        <a href="<%=request.getContextPath()%>/bulkBook" class="btn_bulk_book">一括登録</a>
        <select id=item>
            <option value="0">書籍名</option>
            <option value="1">著者名</option>
            <option value="2">出版社</option>
            <option value="3">出版日</option>
        </select>
        <input id="ascbtn" class="sortbtn" type="radio" name="sortCheckBox" value="asc" checked><span>昇順</span> 
        <input id="descbtn" class="sortbtn" type="radio" name="sortCheckBox" value="desc"><span>降順</span>
        
        <form id="form1" action="<%=request.getContextPath()%>/searchBook">
            <input id="checkbtn" type="radio" name="checkBox" value="perfectMatching" checked><span>完全一致</span>
            <input id="checkbtn" type="radio" name="checkBox" value="partialMatching"><span>部分一致</span>
            <input id="sbox"  id="s" name="searchTitle" type="text" placeholder="キーワードを入力" />
            <input id="sbtn" type="submit" value="検索" />
        </form>
        <div class="content_body">
            <c:if test="${!empty searchError}">
                <div class="search_msg">${searchError}</div>
            </c:if>
            <div>
                <div class="booklist">
                
                    <c:forEach var="bookInfo" items="${bookList}">
                        <div class="books">
                            <form method="post" class="book_thumnail" action="<%=request.getContextPath()%>/details">
                                <a href="javascript:void(0)" onclick="this.parentNode.submit();"> <c:if test="${empty bookInfo.thumbnail}">
                                        <img class="book_noimg" src="resources/img/noImg.png">
                                    </c:if> <c:if test="${!empty bookInfo.thumbnail}">
                                        <img class="book_noimg" src="${bookInfo.thumbnail}">
                                    </c:if>
                                </a> <input type="hidden" name="bookId" value="${bookInfo.bookId}">
                            </form>
                            <ul>
                                <li class="book_title">${bookInfo.title}</li>
                                <li class="book_author">${bookInfo.author}</li>
                                <li class="book_publisher">${bookInfo.publisher}</li>
                                <li class="book_publishDate">${bookInfo.publishDate}</li>
                            </ul>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </main>
</body>
</html>
