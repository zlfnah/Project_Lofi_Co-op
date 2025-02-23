<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="pack_review.ReviewBean"
    import="java.util.*"
    %>
    
    <jsp:useBean id="rMgr" class="pack_review.ReviewMgr" scope="page"/>
    
    <%
    request.setCharacterEncoding("utf-8");
    
    Vector<ReviewBean> vList = null;
    
    int totalRecord = 0; // 전체 레코드
    int numPerPage = 10; // 페이지 당 레코드
    int pagePerBlock = 15; // 블럭 당 페이지
    
    int totalPage = 0; // 전체 페이지
    int totalBlock = 0; // 전체 블록
    
    int nowPage = 1; // 현재 페이지
    int nowBlock = 1;// 현재 블럭
    
    int start = 0; // DB의 select 시작 번호
    int end = 10; // 시작번호로부터 가져올 select 수
    
    int listSize = 0; // 현재 읽어온 게시물 수
    
    %>
    
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="../style/style_List.css">
</head>
<body>

<div id="wrap">

        <header id="header" class="flex-container">
        
            
            <div id="logo">
                <a href="index.html"><img src="/logoB.jpg" alt=""></a>

            </div>
            <nav id="nav1" class="flex-container">
                <ul id="goods"><a href="#">shop</a>
                    <li class="goods1"><a href="#">품목1</a></li>
                    <li class="goods1"><a href="#">품목2</a></li>
                    <li class="goods1"><a href="#">품목3</a></li>
                    <li class="goods1"><a href="#">품목4</a></li>
                    <li class="goods1"><a href="#">품목5</a></li>
                </ul>
                <ul><a href="#">LookBook</a></ul>
                <ul><a href="#">About</a></ul>
                <ul id="board1"><a href="#">Board</a>
                    <li class="board"><a href="#">Notice</a></li>
                    <li class="board"><a href="#">Q&A</a></li>
                    <li class="board"><a href="#">Review</a></li>
                </ul>
            </nav>
            
            <nav id="nav2" class="flex-container">
                <ul><a href="#">Login</a></ul>
                <ul><a href="#">Account</a></ul>
                <ul><a href="#">Cart</a></ul>
                <ul id="search1"><a href="#">Search</a>
                    <li class="search2"><input type="text" placeholder="검색어를 입력해주세요"><a href="#" id="searcha">검색</a></li>
                </ul>
                
            </nav>
            

        </header>


<div id="top"><span>REVIEW</span></div>

<main id="main">

<%
vList = rMgr.getReviewList();
listSize = vList.size();
%>

<table>
	<tbody>
	<%
	if(vList.isEmpty()){
	%>
	<tr>
	<td colspan="3">
	<%= "리뷰가 없습니다." %>
	</td>
	</tr>
	<%}
	else{
	%>
	
	<%
	for(int i = 0; i<listSize; i++){
		
		ReviewBean bean = vList.get(i);
		
		int num = bean.getNum();
		String subject = bean.getSubject();
		String uName = bean.getuName();
		String regDate = bean.getRegDate();
	
	%>
		<tr class="prnTr" onclick="read(<%=num%>)">
			<td><%= num %></td>
			<td><%= subject %></td>
			<td><%= regDate %></td>
			<td><%= uName %></td>
		</tr>
		<%
		}
	}
		%>
	</tbody>
</table>



</main>




	    <footer id="footer">


            <div id="info" class="flex-container">
            
            <div id="cs">
                <h4>C.S CENTER</h4>
                <ul>고객센터 -070-4131-0032</ul>
                <ul>OPEN : MON - FIR 10:30AM - 18:00PM</ul>
                <ul>LUNCH : 12:30PM - 13:30PM</ul>
                <ul>EVERY WEEKEND, HOLIDAY OFF</ul>
                <br>
                <ul>협찬/CS 문의 : peace@lofi.co.kr</ul>
            </div>
            <div id="bank">
                <h4>BANK ACOOUNT</h4>
                <ul>국민은행 022201-04-252808</ul>
                <ul>예금주 : 주식회사 슬랜빌리지</ul>
            </div>
            <div id="links">
                <h4>LINKS</h4>
                <ul><a href="#">회사소개</a></ul>
                <ul><a href="#">이용약관</a></ul>
                <ul><a href="#">개인정보취급방침</a></ul>
                <ul><a href="#">이용안내</a></ul>
            </div>
            <div id="follow">
                <h4>FOLLOW</h4>
                <ul><a href="#">대충 인스타그램 이미지</a></ul>
            </div>
        </div>

            <div id="info2">
                <p>&copy; <b>로파이</b> / site bt the 131DESIGN </p>
                <br>
                <p>주식회사 슬랜빌리지 Ceo : 고혁준 Address : 서울시 광진구 동일로66길 14 2층 슬랜빌리지 (반품 주소 아님) Business License : 485-88-01590 E-Connerce Permit:제 2013-서울중랑-0431 호 Email : 김도윤(team@lofi.co.kr)</p>
            </div>

        </footer>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="../script/script.js"></script>
</body>
</html>