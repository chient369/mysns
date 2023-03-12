"use strict";
const baseURL = window.location.protocol + "//" + window.location.host;

function likeCount(e) {
	var postId = e.id
	var isLiked = $(e).find('i').hasClass('fa-solid')
	var isIncrease = isLiked ? 0 : 1;


	$.ajax({
		url: baseURL + "/api/post/like?inDe=" + isIncrease + "&postId=" + postId,
		success: (data) => {
			console.log(data)
			$(e).find('span').text(data);
			if (isLiked) {
				$(e).find('i').removeClass('fa-solid')
			} else {
				$(e).find('i').addClass('fa-solid')
			}
		}
	})

}
$('.cmt-box').hide();
function cmtBtnClick(e) {
	$(e).parent().parent().next().toggle('fast', 'linear', 500);

}
function showMoreCmts(e) {

	var cmtBox = $(e).parent().find("#cmt-box-content");
	var postId = $(e).attr('post_id');
	var currentSlice = $(e).attr('slice');
	var nextSlice = (Number)(currentSlice) + 1;

	$.ajax({
		url: baseURL + "/api/cmt/" + postId + "/" + nextSlice,
		success: (data) => {
			console.log(data)

			var comments = data.comments;
			console.log(data.coments)
			if (!data.hasNextSlice) {
				$(e).hide();

			} else {
				$(e).attr('slice', nextSlice);
				console.log("next Slice" + $(e).attr('slice'))
			}


			for (let cmt of comments) {
				$(cmtBox).append(`
			<div class="row mb-2">
				<div class= "d-flex align-items-center">
				  <img id="cmt_user-img" class="img-xs rounded-circle"src="${cmt.user.image}" alt="">
					<div class="col ms-2 bg-light rounded-pill">
						<span id="cmt_user-name" class="fw-bold fs-6 ps-2">${cmt.user.userName}</span> <br> 
						<span id="cmt_content" class="ps-3">${cmt.content}</span>
					</div>
				</div >
			</div >
				`)


			}
		}
	})


}
function postComment(e) {
	var cmtPostBox = $(e).parent();
	var postId = $(cmtPostBox).find('input[name=postId]').val();
	var userId = $(cmtPostBox).find('input[name=userId]').val();
	var content = $(cmtPostBox).find('textarea').val();

	var cmtBoxContent = $(cmtPostBox).parent().parent().parent().find('#cmt-box-content');
	console.log($(cmtBoxContent))
	if (content == '') {
		return;
	}
	var formData = {
		postId: postId,
		userId: userId,
		content: content
	}
	$.ajax({
		url: baseURL + "/api/cmt/save",
		type: 'POST',
		data: formData,
		success: (data) => {
			$(cmtBoxContent).append(`
			<div class="row mb-2">
				<div class= "d-flex align-items-center">
				  <img id="cmt_user-img" class="img-xs rounded-circle"src="${data.user.image}" alt="">
					<div class="col ms-2 bg-light rounded-pill">
						<span id="cmt_user-name" class="fw-bold fs-6 ps-2">${data.user.userName}</span> <br> 
						<span id="cmt_content" class="ps-3">${data.content}</span>
					</div>
				</div >
			</div >
				`)
		}
	})
	
	$(cmtPostBox).find('textarea').val('');
}


