<%@ page isErrorPage="true" %><%! 
	static final String ERROR_SCREEN_PATH = "/debug";
	static final String PROJECT = "teeda-html-example";
%><html><head>
	<title>Rich Error Screen For Servlet/JSP</title>
	<link rel="stylesheet" type="text/css" href="<%= calcPath(request) %>/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="<%= calcPath(request) %>/css/xtheme-gray.css" />
	<link rel="stylesheet" type="text/css" href="<%= calcPath(request) %>/css/SyntaxHighlighter.css"></link>
	<script type="text/javascript" src="<%= calcPath(request) %>/js/jquery-1.2.2.min.js"></script>
	<script type="text/javascript" src="<%= calcPath(request) %>/js/ext-base.js"></script>
	<script type="text/javascript" src="<%= calcPath(request) %>/js/ext-all.js"></script>
	<script type="text/javascript" src="<%= calcPath(request) %>/js/TabCloseMenu.js"></script>
	<script type="text/javascript" src="<%= calcPath(request) %>/js/RowExpander.js"></script>
	<script type="text/javascript" src="<%= calcPath(request) %>/js/shCore.js"></script>
	<script type="text/javascript" src="<%= calcPath(request) %>/js/shBrushJava.js"></script>
	<script type="text/javascript" src="<%= calcPath(request) %>/js/debug-info.js"></script>
	<style type="text/css">
	html, body {
		font:normal 12px verdana;
		margin:0;
		padding:0;
		border:0 none;
		overflow:hidden;
		height:100%;
	}
	p {
		margin:5px;
	}
    .settings {
        background-image:url(<%= calcPath(request) %>/images/folder_wrench.png);
    }
	</style>
</head>
<body>
<div id="top">
	<p><b>Rich Error Screen For Servlet/JSP</b></p>
</div>
<div id="menu"></div>
<div id="main"></div>
<div id="stacktrace" class="x-hide-visibility">
<pre><% 
if(exception != null) {
	exception.printStackTrace(new java.io.PrintWriter(out));
} else {
	Object ex = request.getAttribute("javax.servlet.error.exception");
	if(ex instanceof Throwable) {
		Throwable t = (Throwable)ex;
		t.printStackTrace(new java.io.PrintWriter(out));
	}
}
%></pre>
</div>
<script type="text/javascript"><!-- 
	Ext.BLANK_IMAGE_URL = '<%= calcPath(request) %>/images/gray/s.gif';
	var debuginfo = {};
	debuginfo.rsUrl = 'http://localhost:8386';
	debuginfo.project = '<%= PROJECT %>';
	debuginfo.loadbasic = function() {
		return [
			['ContextPath','<%= request.getContextPath() %>'],
			['RequestURI','<%= request.getAttribute("javax.servlet.error.request_uri") %>'],
			['RequestMethod','<%= request.getMethod() %>']
		];
	};
	debuginfo.loadRequestParam = <%= getParams(request) %>
	debuginfo.loadHttpHeader = <%= getHeaders(request) %>
	debuginfo.loadRequestAttr = <%= getRequest(request) %>
	debuginfo.loadSessionAttr = <%= getSession(request) %>
	debuginfo.loadCookie = function() {
		var result = new Array();
		var ary = document.cookie.split(';');
		var len = ary.length;
		for(var i=0; i<len; i++) {
			var kv = ary[i].split('=');
			kv[1] = unescape(kv[1]);
			result.push(kv);
		}
		return result;
	};
	debuginfo.loadAppAttr = <%= getApplication(application) %>
	debuginfo.loadJavaSystemProps = <%= getSystemProps() %>
 -->
</script>
</body></html><%! 
static String calcPath(javax.servlet.http.HttpServletRequest request) {
	StringBuilder stb = new StringBuilder();
	stb.append(request.getContextPath());
	stb.append(ERROR_SCREEN_PATH);
	return stb.toString();
}
interface h {
	java.util.Enumeration e();
	Object o(Object key);
}
static Object escape(String value) {
	return value.trim().replaceAll("\\\\","\\\\\\\\");
}
static String toValue(Object value) {
	if(value instanceof String) {
		return value.toString();
	}
	if(value instanceof Object[]) {
		Object[] ary = (Object[])value;
		if(ary.length == 1) {
			return ary[0].toString();
		}
		return java.util.Arrays.toString(ary);
	}
	return " ";
}
static void append(StringBuilder stb ,Object key ,Object value) {
	stb.append("['");
	stb.append(key);
	stb.append("','");
	stb.append(escape(toValue(value)));
	stb.append("']");
}
static String toString(h h) {
	StringBuilder stb = new StringBuilder();
	stb.append("function() { return [");
	for(java.util.Enumeration e = h.e(); e.hasMoreElements(); ) {
		Object key = e.nextElement();
		append(stb ,key ,h.o(key));
		if(e.hasMoreElements()){
			stb.append(",");
		}
	}
	stb.append("];};");
	return stb.toString();
}
static String getParams(final javax.servlet.http.HttpServletRequest request) {
	return toString(new h() {
		public java.util.Enumeration e() {
			return request.getParameterNames();
		}
		public Object o(Object key) {
			return request.getParameterValues(key.toString());
		}
	});
}
static String getHeaders(final javax.servlet.http.HttpServletRequest request) {
	return toString(new h() {
		public java.util.Enumeration e() {
			return request.getHeaderNames();
		}
		public Object o(Object key) {
			return request.getHeader(key.toString());
		}
	});
}
static String getRequest(final javax.servlet.http.HttpServletRequest request) {
	return toString(new h() {
		public java.util.Enumeration e() {
			return request.getAttributeNames();
		}
		public Object o(Object key) {
			return request.getAttribute(key.toString());
		}
	});
}
static String getSession(final javax.servlet.http.HttpServletRequest request) {
	final javax.servlet.http.HttpSession session = request.getSession(false);
	if(session == null || session.isNew()) {
		return "function() { return [] };";
	}
	return toString(new h() {
		public java.util.Enumeration e() {
			return session.getAttributeNames();
		}
		public Object o(Object key) {
			return session.getAttribute(key.toString());
		}
	});
}
static String getApplication(final javax.servlet.ServletContext context) {
	return toString(new h() {
		public java.util.Enumeration e() {
			return context.getAttributeNames();
		}
		public Object o(Object key) {
			return context.getAttribute(key.toString());
		}
	});
}
static String getSystemProps() {
	final java.util.Properties p = System.getProperties();
	return toString(new h() {
		public java.util.Enumeration e() {
			return p.propertyNames();
		}
		public Object o(Object key) {
			return p.getProperty(key.toString());
		}
	});
}
%>