var vObj,tObj,cmdObj,telpData=[];
var jsonData = [
{"start":0.5,"end":2,"text":"播放开始","color":"#F90707"},
{"start":3,"end":10,"text":"sdfasvecgfbtrcfsx","top":"80px"},
{"start":11,"end":20,"text":"sdfasdfsdgfdsfd","top":"80px"},
{"start":21,"end":30,"text":"gfsdgfhgjhdgfsfa","top":"80px"},
{"start":31,"end":40,"text":"fgsdfbndtyrevgd","top":"80px"},
{"start":41,"end":50,"text":"sfgdsgffsrvgds","top":"80px"},
{"start":51,"end":60,"text":"gdgdfscdfaxxrw","top":"80px"},
{"start":61,"end":70,"text":"gfgdb5ydscge","top":"80px"},
{"start":71,"end":80,"text":"nurdtsecdfcfssdf","color":"#F90707"},
{"start":81,"end":90,"text":"播放结束","fontSize":"#66pt"}
];
window.addEventListener("load", function(){
	vObj = document.getElementById("myVideo");
	tObj = document.getElementById("telop");
	cmdObj = document.getElementById("telopCommand");
	cmdObj.value = jsonData;
	telpData = eval(jsonData);
	//播放时发生在timeupdate事件时显示字幕处理
	vObj.addEventListener("timeupdate", function(){
		var csec  = vObj.currentTime;
		for (var i = 0; i <telpData.length; i++) {
			if ((telpData[i].start <= csec) && (telpData[i].end >= csec)) {
				tObj.innerHTML = telpData[i].text;
				tObj.style.color == telpData[i].color || "#F90707";
				//字符颜色
				tObj.style.top  = telpData[i].top || "170px";
				//纵向显示位置
				tObj.style.fontsize = telpData[i].fontsize || "66pt";
				//字符尺寸
				return;
			}
		}
		tObj.innerHTML = "...";
	},true);
	//文本区域内的数据显示更新的时候显示字幕数据
	cmdObj.addEventListener("change", function(){
		telpData = eval(cmdObj.value);
	}, true);
}, true);