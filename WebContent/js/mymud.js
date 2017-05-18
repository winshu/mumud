/**
 * Mymud功能函数
 */
var mymud = new Mymud();

function Mymud() {
	this.showAreaMsg = function(room) {
		if (typeof (room) === 'object') {
			// 标题
			$('#title').html(room.name);
			// 描述
			$('#desc').html(room.desc);
			// Table
			func.initRoomTable(room);
			// 元素
			var objects = room.objects;
			var message = '这儿有：<br/>';

			$('#object').html(message);
			objects.forEach(function(e) {
				$('#object').append(func.createElementButton(e));
			});
		}
	};
	this.showOutMsg = function(message) {
		if (typeof (message) === 'string') {
			if ($('#out').html().length > 1000) {
				$('#out').html('');
			}
			message = func.replaceHtmlCharacter(message);
			$('#out').html($('#out').html() + message + '<br/>');
			$('#out').scrollTop(Number.MAX_SAFE_INTEGER);
		}
	}
	this.clickButton = function(message) {
		socket.send(message);
	};
};