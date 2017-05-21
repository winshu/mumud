/**
 * 
 */
var func = new Func();

function Func() {
	this.ROOM_EXITS = [ 'northwest', 'north', 'northeast', 'west', null, 'east', 'southwest', 'south', 'southeast' ];

	this._replaceHtmlCharacter = function(str) {
		str = str || '';
		return str.replace(/&/g, '&quot;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/©/g, '&copy;');
	};

	this.createElementButton = function(element) {
		var $button = $('<button class="element"></button>');
		$button.attr('onclick', 'mymud.send("look ' + element.id + '")');
		$button.html(element.name);
		$button.addClass('element_' + element.type);

		return $button;
	};

	this.createMessageLine = function(message) {
		var $span = $('<span></span>');
		if (typeof (message) === 'string') {
			$span.html(message);
		}
		return $span;
	};

	this.createRoomButton = function(room, direction) {
		var $button = $('<button></button>');
		$button.html(direction == null ? room.name : room.exits[direction]);

		if (direction == null) {
			$button.addClass('cmd_click_room');
		} else {
			$button.addClass('cmd_click_exits');
			$button.addClass('cmd_click_' + direction);
			$button.attr('onclick', 'mymud.send("go ' + direction + '.' + room.id + '")');
		}

		return $button;
	};

	this.initRoomTable = function(room) {
		$('#room').empty();

		var $tr = $('<tr><td></td><td></td><td></td></tr>');
		$('#room').append($tr.clone(), $tr.clone(), $tr.clone());

		for ( var i in this.ROOM_EXITS) {
			var td = $('#room tr td')[i];
			var dir = this.ROOM_EXITS[i];
			if (dir == null || room.exits[dir]) {
				$(td).append(this.createRoomButton(room, dir));
			}
		}
	};
};