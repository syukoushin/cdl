// JavaScript Document
$(document).ready(function(){
	$(".stop").click(function(){
		$(".examination ul li:gt(0)").addClass("disappear");
		$(".disappear").slideToggle(300);
		if($(".stop").text()==="展开"){
		   	$(".stop").text("收起");
		}else{
			$(".stop").text("展开");
		}
	});
});
