<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html><head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width"> 
<script type="text/javascript">
	var asyncCall=function(){var a={},b={},c=Array.prototype.slice,d=function(c,d){var e,g=b[c]||[];for(a[c]=d;e=g.shift();)f(c,e)},e=function(c){delete a[c],b[c]=[]},f=function(c,d){var f=a[c],g=b[c]=b[c]||[];f?(f.apply(null,d),e(c)):g.push(d)},g=function(){var a=arguments,b=a[0],d=c.call(a,1);b&&f(b,d)};return{reg:d,unreg:e,call:g}}();
	var loadScript=function(a,b,c){var d;d=document.createElement("script"),d.onload=b,d.src=a,d.id=c,d.binded=!0,setTimeout(function(){document.head.appendChild(d)},10)};
	Page = {conf:{
		current: 'full',
		main:[{id:'js-main',url:'js/main-min2013080502002.js'}],
		full:[{id:'js-full',url:'js/index-min2013042701.js'}]
	}};
	(function () {
		var arr = [].concat(Page.conf.main), i,n,item;
		Page.conf.current == 'full' && (arr = arr.concat(Page.conf.full));
		for (i=0,n=arr.length;i<n;i++) {
			item = arr[i];
			loadScript(item.url, function () {this.state=1}, item.id);
		}
	})();
</script>

<script type="text/javascript" src="js/heatmap-set.js"></script>
<script type="text/javascript">
	heatMapSet.setData([{module:"infoTouch",page:"home",element:"main",cache:5}]);
</script>
<link id="css-full" onload="this.state=1" href="css/index-min2013080502002.css" rel="stylesheet" type="text/css">
</head>

<body id="main" page="home">
<div class="wrapper" style="width:320px;margin:0 auto;">
<section id="headline" class="topic">
  <div class="topic-info">
	<ul>
	  <li class="topic-item">
		<div id="slideImage" class="">
		<div style="width: 720px; -webkit-transition: 200ms ease-out; transition: 200ms ease-out; -webkit-transform: translate(0px, 0px) translateZ(0px);">		
		<a class="current" style="width: 360px;"><img src="./images/1.jpg" alt="日照校区" width="360" height="144" border="0" class="topic_img" pl="1"></a>	
		<a class="" style="width: 360px;"><img src="./images/2.jpg" alt="校区美景" width="360" height="144" border="0" class="topic_img"></a>
		<a class="" style="width: 360px;"><img src="./images/3.jpg" alt="济宁校区" width="360" height="144" border="0" class="topic_img"></a>	
		</div>
		<div id="slideImageTitile" class="topic-title"></div>
		</div>
	  </li>
	</ul>
	<div id="slideDots" class="dot-slider enable">
 <span class="dot current"></span>
 <span class="dot"></span>
<span class="dot"></span>
</div>
</div>
</section>
</div>
</body>