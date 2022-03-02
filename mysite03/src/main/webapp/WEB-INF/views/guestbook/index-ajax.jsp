<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-spa.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	var startNo = 0;

	var render = function(vo) {
		var html = "<li data-no='" + vo.no + "'>" + "<strong>" + vo.name
				+ "</strong>" + "<p>" + vo.message + "</p>"
				+ "<strong></strong>"
				+ "<a href='' data-no='" + vo.no + "'>삭제</a>" + "</li>";

		return html;
	}
	
	

	$(function(){
		$.ajax({
			url:"${pageContext.request.contextPath}/api/guestbook/"+startNo,
			type:"get",
			dataType:"json",
			contentType:"application/json",
			success:function(res){
				console.log(res.data);
				var html='';
				res.data.forEach(function(vo){
					startNo=vo.no;
					html+=render(vo);				
				});
				
				$("#list-guestbook").prepend(html);
				return;
			},
			error:function(xhr,status,res){
				return;
			}
		});
	
	});

	
	//fetch(startNo);
	
	$(window).scroll(function() {
		console.log(startNo);
		var $window = $(this);
		var $document = $(document);

		var windowHeight = $window.height();
		var documentHeight = $document.height();
		var scrollTop = $window.scrollTop();

		if (scrollTop + windowHeight == documentHeight) {
			$.ajax({
				url:"${pageContext.request.contextPath}/api/guestbook/"+startNo,
				type:"get",
				dataType:"json",
				contentType:"application/json",
				success:function(res){
					console.log(res.data);
					var html='';
					res.data.forEach(function(vo){
						startNo=vo.no;
						html+=render(vo);				
					});
					
					$("#list-guestbook").append(html);
					return;
				},
				error:function(xhr,status,res){
					return;
				}
			});
		}
	});

	$(function() {

		$("#add-form").submit(function(event) {
			event.preventDefault();

			var vo = {};
			vo.name = $("#input-name").val();
			vo.password = $("#input-password").val();
			vo.message = $("#tx-content").val();

			$.ajax({
				url : '${pageContext.request.contextPath }/api/guestbook',
				type : 'post',
				dataType : 'json',
				contentType : 'application/json',
				data : JSON.stringify(vo),
				success : function(response) {
					if (response.result !== 'success') {
						console.error(response.message);
						return;
					}

					var html = render(response.data);
					$("#list-guestbook").prepend(html);
				}
			});
		});
	});
	
	$(function() {
		var dialogDelete = $("#dialog-delete-form")
				.dialog(
						{
							autoOpen : false,
							modal : true,
							buttons : {
								"삭제" : function() {
									var vo = {};
									vo.no = $("#hidden-no").val();
									console.log( $("#hidden-no").val());
									vo.password = $("#password-delete").val();
									var url = "${pageContext.request.contextPath }/api/guestbook";
									console.log(vo);
									$
											.ajax({
												url : url,
												type : 'delete',
												dataType : 'json',
												contentType : 'application/json',
												data : JSON.stringify(vo),

												success : function(response) {
													if (response.result !== 'success') {
														console
																.error(response.message);
														return;
													}

													if (response.data == -1) {
														$(".validateTips.error")
																.show();
														$(".password-delete")
																.val("")
																.focus();
														return;
													}

													//삭성
													$(
															"#list-guestbook li[data-no='"
																	+ response.data
																	+ "']")
															.remove();
													dialogDelete
															.dialog('close');
												}
											})
								},
								"취소" : function() {
									$(this).dialog('close');
								}
							},
							close : function() {
								$("#passeord-delete").val("");
								$("hidden-no").val("");
								$(".validateTips.error").hide();
							}
						});

		//글삭제 버튼 클릭이벤트 철이(live event) 나중에 생길애들 이벤트 철이
		$(document).on('click', "#list-guestbook li a", function(event) {
			event.preventDefault();

			var no = $(this).data("no");
			$("#hidden-no").val(no);
			dialogDelete.dialog('open');
		});

		//최초리스트 가져오기
	

	});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름"> <input
						type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<div class="scroll-guestbook">
					<ul id="list-guestbook"></ul>
				</div>


			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display: none">
				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
				<p class="validateTips error" style="display: none">비밀번호가 틀립니다.</p>
				<form>
					<input type="password" id="password-delete" value=""
						class="text ui-widget-content ui-corner-all"> <input
						type="hidden" id="hidden-no" value=""> <input
						type="submit" tabindex="-1"
						style="position: absolute; top: -1000px">
				</form>
			</div>

		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>