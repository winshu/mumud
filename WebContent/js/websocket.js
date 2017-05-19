var socket = new WebSocket('ws://' + window.location.host + '/mymud/actions');
var mymud = new Mymud();

socket.onopen = function(e) {
	console.log('websocket connected');
};

socket.onclose = function(e) {
	console.log('websocket disconnected');
};

socket.onmessage = function(e) {
	var result = JSON.parse(e.data);
	console.log(result);

	mymud.showAreaMsg(result.room);
	mymud.showOutMsg(result.message);
};

function Mymud() {
	this._historyCmds = [];
	this._currentCmdIndex = -1;
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
	this.nextCmd = function() {
		if (this._currentCmdIndex < this._historyCmds.length - 1) {
			this._currentCmdIndex++;
			return this._historyCmds[this._currentCmdIndex];
		}
	};
	this.previousCmd = function() {
		if (this._currentCmdIndex > 0) {
			this._currentCmdIndex--;
			return this._historyCmds[this._currentCmdIndex];
		}
	};
	this.pushCmd = function(cmd) {
		if (!this._historyCmds.length) {
			this._historyCmds.push(cmd);
			this._currentCmdIndex = this._historyCmds.length - 1;
		} else if (this._historyCmds.lastIndexOf(cmd) != this._historyCmds.length - 1) {
			this._historyCmds.push(cmd);
			this._currentCmdIndex = this._historyCmds.length - 1;
		}
	}
};

// 匿名初始化
(function() {
	$('#send').click(function() {
		var cmd = $('#cmd').val().trim();
		if (cmd.length) {
			socket.send(cmd);
		}
	});

	$('#cmd').keyup(function(e) {
		if (e.keyCode == 13) {// 回车
			$('#send').click();
			mymud.pushCmd($(this).val());
		}
		if (e.keyCode == 38) {// 上
			var cmd = mymud.previousCmd();
			if (cmd) {
				$(this).val(cmd);
			}
		}
		if (e.keyCode == 40) {// 下
			var cmd = mymud.nextCmd();
			if (cmd) {
				$(this).val(cmd);
			}
		}
	});
})();