jQuery.leftBar = {

	show : function(linkArr) {
		var _openLeftBar = $('<a id="openLeftBar" title="常用工具" class="openLeftBarClose"></a>');
		var _leftBar = $('<div id="leftBar"></div>');
		var _dockContainer = $('<div class="dock_container dock_pos_left" id="dockContainer">');
		var _dock_middle = $('<div class="dock_middle" style="width:0px;" id="dock_middle">');
		var _dockItemList = $('<div class="dock_item_list" id="dockItemList"  style="display:none;">');
		for ( var i = 0; i < linkArr.length; i++) {
			var _tempLinkItem = $('<div title="'
					+ linkArr[i].title
					+ '" class="appButtonForLeft not_deleteable"  style="display: inline;"><div class="appButton_appIcon"><a target="_blank" href="'
					+ linkArr[i].linkUrl
					+ '"><img class="appButton_appIconImg"  alt="'
					+ linkArr[i].title + '" src="' + linkArr[i].imgSrc
					+ '" /></a></div></div>');
			_dockItemList.append(_tempLinkItem);
		}
		_dock_middle.append(_dockItemList);
		_dockContainer.append(_openLeftBar);
		_dockContainer.append(_dock_middle);
		_leftBar.append(_dockContainer);
		$("body").append(_leftBar);

		$("#openLeftBar").bind("click", function(e) {
			if ($("#openLeftBar").attr("title") == "常用工具") {
				$("#dock_middle").stop().animate({
					width : "73px"
				});
				$("#dockItemList").css('display', 'block');
				$("#leftBar").css("width","73px;");
				$("#openLeftBar").attr("title", "关闭");
				$("#openLeftBar").removeClass().addClass("openLeftBarOpen");
			} else {
				$("#dock_middle").stop().animate({
					width : "0px"
				});
				$("#leftBar").css("width","0px;");
				$("#dockItemList").css("display", "none");
				$("#openLeftBar").attr("title", "常用工具");
				$("#openLeftBar").removeClass().addClass("openLeftBarClose");
			}
		});
	}

};