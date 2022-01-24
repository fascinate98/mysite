<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type = "hidden" name = "p" value = "1">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>				
				<c:set var = "count" value = "${fn:length(map.list)}"/>
				
				<c:forEach items="${map.list}" var = "vo" varStatus = "status">
						<tr class = 'tble'>
							<c:choose>
								<c:when test="${map.pcnt == map.p}">
									<td>[${count- status.index }]</td>
								</c:when>
								<c:otherwise>
									<td>[${count- status.index +  (map.cnt % 5) + 5 * (map.pcnt - map.p - 1)}]</td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test='${vo.status == "deleted"}'>
								<td>삭제된 글입니다.</td>
								</c:when >
								<c:otherwise>
									<td style="padding-left:${(vo.depth-1)*20 }px">
										<c:if test='${vo.depth != 1}'>
											<img src="${pageContext.servletContext.contextPath }/assets/images/reply.png" />
										</c:if>
										<a href="${pageContext.request.contextPath}/board/view?no=${vo.no}">${vo.title}</a>
									</td>
								</c:otherwise> 
								</c:choose>
							<td>${vo.userName}</td>
							<td>${vo.hit}</td>
							<td>${vo.regDate}</td>
							<c:if test= '${authUser.no ==  vo.userNo && vo.status != "deleted"}'>
								<td><a href="${pageContext.request.contextPath}/board/delete?no=${vo.no}"  class="del" style='background-image: url("${pageContext.servletContext.contextPath }/assets/images/recycle.png")'>삭제</a></td>
							</c:if >
						</tr>
				</c:forEach>
				</table>
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
					<c:choose>
						<c:when test = "${empty map.kwd}">
							<li <c:if test ="${map.p == 1}">class="disabled"</c:if>><a href="${pageContext.request.contextPath}/board?p=${map.p - 1}">◀</a></li>
							<c:forEach begin = "1" end = "${map.pcnt}" step = "1" var = "r">
								<li <c:if test ="${map.p == r}">class="selected"</c:if> ><a href="${pageContext.request.contextPath}/board?p=${r}">${r}</a></li>
							</c:forEach>
							<li <c:if test ="${map.p == map.pcnt}">class="disabled"</c:if>><a href="${pageContext.request.contextPath}/board?p=${map.p + 1}">▶</a></li>													
						</c:when>
						<c:otherwise>
							<li <c:if test ="${map.p == 1}">class="disabled"</c:if>><a href="${pageContext.request.contextPath}/board?p=${map.p - 1}&kwd=${map.kwd}">◀</a></li>
							<c:forEach begin = "1" end = "${map.pcnt}" step = "1" var = "r">
								<li <c:if test ="${map.p == r}">class="selected"</c:if> ><a href="${pageContext.request.contextPath}/board?p=${r}&kwd=${map.kwd}">${r}</a></li>
							</c:forEach>
							<li <c:if test ="${map.p == map.pcnt}">class="disabled"</c:if>><a href="${pageContext.request.contextPath}/board?p=${map.p + 1}&kwd=${map.kwd}">▶</a></li>						
						</c:otherwise>
					</c:choose>
						
					</ul>
				</div>
				<!-- pager 추가 -->
				<div class="bottom">
					<c:if test="${!empty authUser}">
						<a href="${pageContext.request.contextPath}/board/write" id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
	
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<script>

</script>
</body>

</html>