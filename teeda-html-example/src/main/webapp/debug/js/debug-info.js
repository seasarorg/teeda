Ext.onReady(function(){
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

	function createGrid(id, title, data) {
		var gp = new Ext.grid.GridPanel({
			loadMask: true,
			store: new Ext.data.SimpleStore({
				data: data,
				fields: [
					{name: 'key'},
					{name: 'value'}
				]
			}),
			columns: [
				{id:'key',header: "Key", width: 150, sortable: true, dataIndex: 'key'},
				{id:'value',header: "Value", width: 50, sortable: true, dataIndex: 'value'}
			],
			tbar:[
				new Ext.form.TextField({
					width: 200,
					emptyText:'Filter Key',
					listeners:{
						render: function(f){
							f.el.on('keydown', filterKey, f, {buffer: 350});
						}
					}
				})
			],
			stripeRows: true,
			autoExpandColumn: 'value',
			id:id,
			title: title,
			closable:true,
			autoScroll:true
		});
		function filterKey(e) {
			var text = e.target.value;
			gp.store.filter('key',text,true,true);
		}
		return gp;
	}
	
	function convertStackTrace() {
		var pre = $('div#stacktrace > pre');
		var row = pre.text().split(/[\r\n]/);
		var title = row.shift();
		var len = row.length;
		var datas = new Array();
		for(var i=0; i<len;i++) {
			var spi = row[i].indexOf('(');
			var epi = row[i].indexOf(')');
			var method = row[i].substring(row[i].indexOf('at ')+3, spi);
			var clazz = row[i].substring(spi+1, epi);
			var line = 0;
			var ci = clazz.indexOf(':');
			if(1 < ci) {
				line = clazz.substring(ci+1);
				clazz = clazz.substring(0,ci);
			}
			if(method != null && 0 < method.length){
				datas.push([method,clazz,line]);
			}
		}
		pre.remove();
		return {title: title, data:datas};
	}
	
	var mainArea = new Ext.TabPanel({
				region:'center',
				deferredRender:false,
				activeTab:0,
				enableTabScroll:true,
				plugins: new Ext.ux.TabCloseMenu()
	});

	function createStackTraceGrid() {
		var datas = convertStackTrace();
		var expander = new Ext.grid.RowExpander({
			lazyRender:true,
			tpl : new Ext.Template(
				'<textarea name=\"{codename}\" class=\"java:showcolumns:nocontrols:firstline[{firstline}]\">{code}</textarea>'
			)
		});
		var gp = new Ext.grid.GridPanel({
			loadMask: true,
			store: new Ext.data.SimpleStore({
				data: datas.data,
				fields: [
					{name: 'method'},
					{name: 'clazz'},
					{name: 'line'}
				]
			}),
			columns: [
				expander,
				{id:'method',header: 'Method' , width: 100, sortable: false, dataIndex: 'method'},
				{id:'clazz' ,header: 'Class'  , width: 100, sortable: false, dataIndex: 'clazz'},
				{id:'line'  ,header: 'Line No', width: 50,  sortable: false, dataIndex: 'line'}
			],
			viewConfig: {
				forceFit: true
			},
			plugins: expander,
			collapsible: true,
			resizeTabs:true,
			autoExpandColumn: 'method',
			id:id.title + '-xid',
			tbar: ['<b>' + datas.title + '</b>'],
			title: 'StackTrace',
			autoScroll:true
		});
		gp.on('rowdblclick',function(grid,rowIndex,e){
			expander.toggleRow(rowIndex);
		});
		expander.on('beforeexpand',function(me ,rec ,body ,rowIndex){
			var fl = rec.get('firstline');
			if(fl != null) {
				return true;
			} else {
				var m = rec.get('method');
				var clazz = m.substring(0 ,m.lastIndexOf('.'));
				var ds = new Ext.data.Store({
					proxy: new Ext.data.ScriptTagProxy({
						url: debuginfo.rsUrl + '/select'
					}),
					reader: new Ext.data.JsonReader({
						root: 'codes',
						totalProperty: 'results'
					},[
						{name:'firstline'},
						{name:'code'}
					]),
					baseParams: {
						project: debuginfo.project,
						size: Ext.state.Manager.get('codeLineSize',10),
						openInEditor: Ext.state.Manager.get('openInEclipse',true),
						classname:clazz,
						line:rec.get('line')
					}
				});
				var mask = new Ext.LoadMask(mainArea.getEl() ,{store:ds});
				ds.on('load',function(store ,records ,options){
					if(0 < records.length) {
						var record = records[0];
						var c = record.get('code').replace(/\t/g,'    ');
						rec.set('firstline' ,record.get('firstline'));
						rec.set('code' ,c);
						var codename = 'code-' + rowIndex;
						rec.set('codename',codename);
						expander.expandRow(rowIndex);
						var hilight = function(m, r, b, ri){
							if(rowIndex == ri){
								dp.sh.HighlightAll(codename);
							}
						};
						hilight(me ,record ,body ,rowIndex);
						expander.on('expand', hilight);
					}
				});
				ds.load();
				return false;
			}
		})
		return gp;
	}
	
	mainArea.add(createStackTraceGrid());
	
	function createClickHandler(id, title, dataloader) {
		return function(){
			if(mainArea.findById(id) == null) {
				mainArea.add(createGrid(id, title, dataloader()));
			}
			mainArea.activate(id);
		};
	}
	
	function createNode(id, text) {
		return new Ext.tree.TreeNode({
			id:id,
			text:text
		});
	}
	
	function onClick(node, loader) {
		node.on('click',createClickHandler(node.id+'-grid', node.text, loader));
	}
	
	var menuTree = new Ext.tree.TreePanel({
				el:'menu',
				id:'menu-panel',
				title:'Debug Information',
				width: 200,
				minSize: 175,
				maxSize: 400,
				rootVisible: false,
				collapsible: true,
				margins:'0 0 0 5',
				animate:true,
				autoScroll:true,
				containerScroll: true
	});
	var rootNode = createNode('source','Debug Informations');

	var http = createNode('http','HTTP');
	onClick(http, debuginfo.loadbasic);
	var headers = createNode('httpheader','HTTP Headers');
	onClick(headers, debuginfo.loadHttpHeader);
	var params = createNode('requestparams','Request Parameters');
	onClick(params, debuginfo.loadRequestParam);
	var cookie = createNode('cookies','Cookie');
	onClick(cookie, debuginfo.loadCookie);
	
	http.appendChild([params,headers,cookie]);
	
	var javaNode = createNode('java','Java');
	javaNode.on('click',createClickHandler('javaSystemProps','Java System Properties',debuginfo.loadJavaSystemProps));
	var request = createNode('requestAttributes','Request Attributes');
	onClick(request, debuginfo.loadRequestAttr);
	var session = createNode('sessionAttributes','Session Attributes');
	onClick(session, debuginfo.loadSessionAttr);
	var app = createNode('applicationAttributes','Application Attributes');
	onClick(app, debuginfo.loadAppAttr);
	
	javaNode.appendChild([request,session,app]);
	
	rootNode.appendChild([http,javaNode]);
	menuTree.setRootNode(rootNode);

	setTimeout(function(){
		menuTree.expandPath(http.getPath());
		setTimeout(function(){
			menuTree.expandPath(javaNode.getPath());
		},100);
	},400);

	var configArea = new Ext.grid.PropertyGrid({
		title:'Settings',
		iconCls:'settings',
		collapsed: false,
		source: {
			'codeLineSize':10,
			'openInEclipse':true
		}
	});
	configArea.getColumnModel().config[0].width = 100;
	configArea.getColumnModel().config[0].fixed = true;
	
	configArea.on('render',function(){
		configArea.on('beforeexpand',function(tt){
			var props = configArea.getSource();
			props['codeLineSize'] = Ext.state.Manager.get('codeLineSize',10);
			props['openInEclipse'] = Ext.state.Manager.get('openInEclipse',true);
			configArea.setSource(props);
		});
	});
	configArea.on('propertychange',function(props){
		Ext.state.Manager.set('codeLineSize',props['codeLineSize']);
		Ext.state.Manager.set('opneInEclipse',props['openInEclipse']);
	});
	
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[
			new Ext.BoxComponent({ // raw
				region:'north',
				el: 'top',
				height:32
			}),{
				region:'west',
				id:'west-panel',
				title:'Menu',
				split:true,
				width: 200,
				minSize: 175,
				maxSize: 400,
				collapsible: true,
				margins:'0 0 0 5',
				layout:'accordion',
				layoutConfig:{
					animate:true
				},
				items: [
					menuTree,
					configArea
				]
			},
			mainArea
		]
	});
});