//检查浏览器是否支持WebSocket
if (!window.WebSocket) {
	alert('Consider updating your browser for a richer experience');
}
var mymud = new Mymud();

function Mymud() {
	this._socket = new WebSocket('ws://' + window.location.host + '/mymud/actions');

	this._socket.onopen = function(e) {
		console.log('websocket connected');
	};

	this._socket.onclose = function(e) {
		console.log('websocket disconnected');
	};

	this._socket.onmessage = function(e) {
		var result = JSON.parse(e.data);
		console.log(result);

		mymud.showAreaMsg(result.room);
		mymud.showOutMsg(result.message);
	};

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
			$('#out').append(func.createMessageLine(message));
			$('#out').scrollTop(Number.MAX_SAFE_INTEGER);
		}
	}
	this.send = function(message) {
		if (this._socket.readyState == WebSocket.OPEN) {
			this._socket.send(message);
		}
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
			mymud.send(cmd);
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