(function() {
	
	window.loadcss = function(url, charset) {
		var head = document.getElementsByTagName("head")[0];
		var link = document.getElementById(url);
		if(link){
			return ;
		}
		link = document.createElement("link");
		link.setAttribute("id",url);
		link.setAttribute("rel", "stylesheet");
		link.setAttribute("type", "text/css");
		if (charset) {
			link.charset = charset;
		}
		link.setAttribute("href", url);
		head.appendChild(link);
	};
	
	window.loadjs = function(url, callback, charset) {
		var head = document.getElementsByTagName("head")[0];
		var script = document.createElement("script");
		script.async = true;
		if (charset) {
			script.charset = charset;
		}
		script.src = url;
		var done = false;
		script.onload = script.onreadystatechange = function(){
			if ( !done && (!this.readyState ||
					this.readyState == "loaded" || this.readyState == "complete") ) {
				done = true;
				callback();
				script.onload = script.onreadystatechange = null;
				head.removeChild( script );
			}
		};
		head.appendChild(script);
	};
	
})();
