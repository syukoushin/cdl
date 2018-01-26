/*
 * Created with Sublime Text 3.
 * license: http://www.lovewebgames.com/jsmodule/index.html
 * User: 田想兵
 * Date: 2015-06-11
 * Time: 16:27:55
 * Contact: 55342775@qq.com
 */
(function(root, factory) {
	//amd
	if (typeof define === 'function' && define.amd) {
		define(['$', 'query'], factory);
	} else if (typeof exports === 'object') { //umd
		module.exports = factory();
	} else {
		root.Paging = factory(window.Zepto || window.jQuery || $, Query);
	}
})(this, function($, Query) {
	$.fn.Paging = function(settings) {
		var arr = [];
		$(this).each(function() {
			var options = $.extend({
				target: $(this)
			}, settings);
			var lz = new Paging();
			lz.init(options);
			arr.push(lz);
		});
		return arr;
	};

	function Paging() {
		var rnd = Math.random().toString().replace('.', '');
		this.id = 'Paging_' + rnd;
	}
	Paging.prototype = {
		init: function(settings) {
			this.settings = $.extend({
				callback: null,
				pagesize: 10,
				current: 1,
				prevTpl: "<",
				nextTpl: ">",
				ellipseTpl: "...",
				toolbar: false,
				hash:true,
				pageSizeList: [5, 10, 15, 20]
			}, settings);
			this.target = $(this.settings.target);
			this.container = $('<div id="' + this.id + '" class="panel-pagination"/>');
			this.target.append(this.container);
			this.render(this.settings);
			this.format();
			this.bindEvent();
		},
		render: function(ops) {
			this.count = ops.count || this.settings.count;
			this.pagesize = ops.pagesize || this.settings.pagesize;
			this.current = ops.current || this.settings.current;
			this.pagecount = Math.ceil(this.count / this.pagesize);
			this.format();
		},
		bindEvent: function() {
			var _this = this;
			this.container.on('click', 'li.js-page-action,li.ui-pager', function(e) {
				if ($(this).hasClass('ui-pager-disabled') || $(this).hasClass('focus')) {
					return false;
				}
				if ($(this).hasClass('js-page-action')) {
					if ($(this).hasClass('js-page-first')) {
						_this.current = 1;
					}
					if ($(this).hasClass('js-page-prev')) {
						_this.current = Math.max(1, _this.current - 1);
					}
					if ($(this).hasClass('js-page-next')) {
						_this.current = Math.min(_this.pagecount, _this.current + 1);
					}
					if ($(this).hasClass('js-page-last')) {
						_this.current = _this.pagecount;
					}
				} else if ($(this).data('page')) {
					_this.current = parseInt($(this).data('page'));
				}
				_this.go();
			});
			this.container.on('click', '.cm-icon', function(e) {
				if($("#jumpPageNo").val() == ""){
					$("#jumpPageNo").val("1");
				}
				_this.go($("#jumpPageNo").val());
			});
			/*
			$(window).on('hashchange',function(){
				var page=  parseInt(Query.getHash('page'));
				if(_this.current !=page){
					_this.go(page||1);
				}
			})
			 */
		},
		go: function(p) {
			var _this = this;
			this.current = p || this.current;
			this.current = Math.max(1, _this.current);
			this.current = Math.min(this.current, _this.pagecount);
			this.format();
			if(this.settings.hash){
				Query.setHash({
					page:this.current
				});
			}
			this.settings.callback && this.settings.callback(this.current, this.pagesize, this.pagecount);
		},
		changePagesize: function(ps) {
			this.render({
				pagesize: ps
			});
		},
		format: function() {
			var html = '<ul class="pagination">';
			html += '<li class="js-page-prev js-page-action ui-pager"><span class="color_black">' + this.settings.prevTpl + '</span></li>';
			if (this.pagecount > 6) {
				html += '<li data-page="1" class="ui-pager"><span>1</span></li>';
				if (this.current <= 2) {
					html += '<li data-page="2" class="ui-pager"><span>2</span></li>';
					html += '<li data-page="3" class="ui-pager"><span>3</span></li>';
					html += '<li><span>' + this.settings.ellipseTpl + '</span></li>';
				} else	if (this.current > 2 && this.current <= this.pagecount - 2) {
					html += '<li><span>' + this.settings.ellipseTpl + '</span></li>';
					html += '<li data-page="' + (this.current - 1) + '" class="ui-pager"><span>' + (this.current - 1) + '</span></li>';
					html += '<li data-page="' + this.current + '" class="ui-pager"><span>' + this.current + '</span></li>';
					html += '<li data-page="' + (this.current + 1) + '" class="ui-pager"><span>' + (this.current + 1) + '</span></li>';
					html += '<li><span>' + this.settings.ellipseTpl + '</span></li>';
				} else {
					html += '<li><span>' + this.settings.ellipseTpl + '</span></li>';
					for (var i = this.pagecount - 2; i < this.pagecount; i++) {
						html += '<li data-page="' + i + '" class="ui-pager"><span>' + i + '</span></li>'
					}
				}
				html += '<li data-page="' + this.pagecount + '" class="ui-pager"><span>' + this.pagecount + '</span></li>';
			} else {
				for (var i = 1; i <= this.pagecount; i++) {
					html += '<li data-page="' + i + '" class="ui-pager"><span>' + i + '</span></li>'
				}
			}
			html += '<li class="js-page-next js-page-action ui-pager"><span class="color_black">' + this.settings.nextTpl + '</span></li>';
			html += '</ul>';
			if(this.pagecount == 1){
				html += '<div class="pagin">总数 <span>'+this.count+'</span></div>';
			}else{
				html += '<div class="pagin">总数 <span>'+this.count+'</span> 转至<a href="javascript:void(0);" class="cm-icon"></a><div><input type="text" id="jumpPageNo">页</div></div>';
			}
			this.container.html(html);
			if (this.current == 1) {
				$('.js-page-prev', this.container).addClass('ui-pager-disabled');
				$('.js-page-first', this.container).addClass('ui-pager-disabled');
			}
			if (this.current == this.pagecount) {
				$('.js-page-next', this.container).addClass('ui-pager-disabled');
				$('.js-page-last', this.container).addClass('ui-pager-disabled');
			}
			this.container.find('li[data-page="' + this.current + '"]').addClass('focus').siblings().removeClass('focus');
			if (this.settings.toolbar) {
				this.bindToolbar();
			}
		},
		jumpTo: function(){
			//alert(1);
		},
		bindToolbar: function() {
			var _this = this;
			var html = $('<li class="ui-paging-toolbar"><select class="ui-select-pagesize"></select><input type="text" class="ui-paging-count"/><a href="javascript:void(0)">跳转</a></li>');
			var sel = $('.ui-select-pagesize', html);
			var str = '';
			for (var i = 0, l = this.settings.pageSizeList.length; i < l; i++) {
				str += '<option value="' + this.settings.pageSizeList[i] + '">' + this.settings.pageSizeList[i] + '条/页</option>';
			}
			sel.html(str);
			sel.val(this.pagesize);
			$('input', html).val(this.current);
			$('input', html).click(function() {
				$(this).select();
			}).keydown(function(e) {
				if (e.keyCode == 13) {
					var current = parseInt($(this).val()) || 1;
					_this.go(current);
				}
			});
			$('a', html).click(function() {
				var current = parseInt($(this).prev().val()) || 1;
				_this.go(current);
			});
			sel.change(function() {
				_this.changePagesize($(this).val());
			});
			this.container.children('ul').append(html);
		}
	}
	return Paging;
});