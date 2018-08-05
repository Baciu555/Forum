//var commentHref;
//
//$("#confirm-delete-thread").click(function(event) {
//	var href = $("#delete-thread").attr('href');
//	
//	console.log(href);
//	deleteThread();
//	//window.location.reload();
//});
//
//$("#confirm-delete-comment").click(function(event) {
//	deleteComment(commentHref);
//	window.location.reload();
//});
//
//$("#delete-thread").click(function(e){
//	e.preventDefault();
//});
//
//$(".delete-comment").click(function(e){
//	e.preventDefault();
//	commentHref = $(this).attr('href');
//});
//
//
//function deleteThread(href, form) {
//	var message = "deleteThread";
//	var form = $("#deleteThreadForm");
//
//	$.ajax({
//		type : "POST",
//		contentType : "application/json",
//		url : form.attr("action"),
//		data : form.serialize(),
//		dataType : 'json',
//		cache : false,
//		timeout : 600000,
//		success : function(result) {
//			console.log(result);
//			if (result == "DONE") {
//				console.log(result);
//			} else {
//				alert("ERROR");
//			}
//		}
//	});
//}
//
//function deleteComment(href) {
//	var message = "deleteComment";
//
//	$.ajax({
//		type : "POST",
//		contentType : "application/json",
//		url : href,
//		data : JSON.stringify(message),
//		dataType : 'json',
//		cache : false,
//		timeout : 600000,
//		success : function(result) {
//			console.log(result);
//			if (result == "DONE") {
//				console.log(result);
//			} else {
//				alert("ERROR");
//			}
//		}
//	});
//}
//
