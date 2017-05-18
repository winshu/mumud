/*
 * 地图设计器
 */
var design = new Design();

function Design() {
	this._currentId = -1;
	this._lineCount = 20;
	this._columnCount = 10;

	var self = this;
	this._toBinary = function(value) {
		if (typeof (value) === 'number') {
			var result = value.toString(2);
			result = '00000000'.substr(0, 8 - result.length) + result;
			return result;
		}
		return null;
	};
	this.initDesignTable = function() {
		for (var i = 0; i < self._lineCount; i++) {
			var $tr = $('<tr></tr>');
			$('#design').append($tr);
			for (var j = 0; j < self._columnCount; j++) {
				var $td = $('<td></td>');
				var id = i * self._lineCount + j;
				$td.attr({
					'id' : id,
					'x' : j,
					'y' : i,
					'd' : '00000000',
				});
				$tr.append($td);
			}
		}
		$('#design td').dblclick(function() {
			if ($(this).attr('selected')) {
				$(this).html('');
				$(this).removeAttr('selected');
				$(this).css('background-color', '');
				self._removeNeighborRelation($(this).attr('id'));
			} else {
				$(this).html($(this).attr('id'));
				$(this).attr('selected', 'selected');
				$(this).css('background-color', '#c0c0c0');
			}
			$(this).click();
		}).hover(function() {
			$(this).addClass('hover_on');
		}, function() {
			$(this).removeClass('hover_on');
		}).click(function() {
			if (!$(this).attr('selected')) {
				return;
			}

			self._currentId = $(this).attr('id');
			$('#attribute input[name=id]').val(self._currentId);
			$('#operator #none').html(self._currentId);

			var x = parseInt($(this).attr('x'));
			var y = parseInt($(this).attr('y'));
			$('#operator button').each(function(i, e) {
				var $button = $(e);
				var id = parseInt(self._currentId);
				var dx = parseInt($button.attr('dx'));
				var dy = parseInt($button.attr('dy'));

				var nx = x + dx;
				var ny = y + dy;
				var next = $('#design td[x=' + nx + '][y=' + ny + ']');
				$button.attr('disabled', next.length < 1 || !next.attr('selected'));
			});
		});
	};
	this.initAttributeTable = function() {
		var attributes = [ 'id', 'name', 'desc', 'east', 'southeast', 'south', 'southwest', 'west', 'northwest', 'north', 'northeast' ];
		attributes.forEach(function(e) {
			$td1 = $('<td></td>').html(e);
			$td2 = $('<td></td>').append($('<input type="text" name="' + e + '"/>'));
			$td2.addClass('attribute_value');

			$tr = $('<tr></tr>').append($td1, $td2);
			$('#attribute').append($tr);
		});
	};
	this.initOperatorTable = function() {
		$('#operator button').attr('disabled', true);
		$('#operator button').click(function() {
			var dx = parseInt($(this).attr('dx'));
			var dy = parseInt($(this).attr('dy'));
			var d = parseInt($(this).attr('d'));

			self._addRelation(dx, dy, d);
		});
	};
	this._addRelation = function(dx, dy, d) {
		var id = parseInt(self._currentId);
		var $from = $('#design #' + id);

		var x = parseInt($from.attr('x'));
		var y = parseInt($from.attr('y'));

		var $reverse = $('#operator button[dx=' + (-dx) + '][dy=' + (-dy) + ']');
		var $to = $('#design td[x=' + (x + dx) + '][y=' + (y + dy) + ']');

		var fromDirection = parseInt($from.attr('d'), 2) | parseInt(d, 2);
		var toDirection = parseInt($to.attr('d'), 2) | parseInt($reverse.attr('d'), 2);

		var from = self._toBinary(fromDirection);
		var to = self._toBinary(toDirection);

		$from.attr('d', from);
		$to.attr('d', to);

		$from.css('background-image', 'url("image-design/' + from + '.png")');
		$to.css('background-image', 'url("image-design/' + to + '.png")');
	};
	this._removeRelation = function(dx, dy, d) {
		var id = parseInt(self._currentId);
		var $from = $('#design #' + id);

		var x = parseInt($from.attr('x'));
		var y = parseInt($from.attr('y'));

		var $reverse = $('#operator button[dx=' + (-dx) + '][dy=' + (-dy) + ']');
		var $to = $('#design td[x=' + (x + dx) + '][y=' + (y + dy) + ']');

		var fromDirection = parseInt($from.attr('d'), 2) & (~(((~(parseInt(d, 2)))^0xff)>>>0));
		var toDirection = parseInt($to.attr('d'), 2) & (~(((~(parseInt($reverse.attr('d'), 2)))^0xff)>>>0));

		$from.attr('d', from);
		$to.attr('d', to);

		$from.css('background-image', 'url("image-design/' + from + '.png")');
		$to.css('background-image', 'url("image-design/' + to + '.png")');
	};
	this._removeNeighborRelation = function(id) {
		var $from = $('#design #' + id);
		$from.css('background-image', '');
		$from.attr('d', '00000000');

		var x = parseInt($from.attr('x'));
		var y = parseInt($from.attr('y'));

		$('#operator button').each(function(i, e) {
			var $button = $(e);
			var dx = parseInt($button.attr('dx'));
			var dy = parseInt($button.attr('dy'));

			var nx = x + dx;
			var ny = y + dy;

			var next = $('#design td[x=' + nx + '][y=' + ny + ']');
			if (next.length && next.attr('selected')) {
				var $reverse = $('#operator button[dx=' + (-dx) + '][dy=' + (-dy) + ']');
				var dd = parseInt(next.attr('d'), 2) & (~(((~(parseInt($reverse.attr('d'), 2)))^0xff)>>>0));
				var pngname = self._toBinary(dd);

				next.css('background-image', 'url("image-design/' + pngname + '.png")');
				next.attr('d', pngname);
			}
		});
	};
};

design.initDesignTable();
design.initAttributeTable();
design.initOperatorTable();