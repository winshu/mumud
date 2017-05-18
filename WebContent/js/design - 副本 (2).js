/*
 * 地图设计器
 */
var design = new Design();

function Design() {
	this._currentId = -1;
	this._lineCount = 20;
	this._columnCount = 20;
	this._directions = [ 'east', 'southeast', 'south', 'southwest', 'west', 'northwest', 'north', 'northeast' ];
	this._rooms = new Map();

	var self = this;
	this._toBinary = function(value) {
		if (typeof (value) === 'number') {
			var result = value.toString(2);
			result = '00000000'.substr(0, 8 - result.length) + result;
			return result;
		}
		return null;
	};
	this._reverse = function(dir) {
		var index = this._directions.indexOf(dir);
		var reverseIndex = (index + this._directions.length / 2) & 7;
		return this._directions[reverseIndex];
	};
	this._toPngName = function(id) {
		var td = $('#design #' + id);
		var pngName = '';
		this._directions.forEach(function(e) {
			pngName = (td.attr(e) ? '1' : '0') + pngName;
		});
		return pngName;
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
					'y' : i
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
				$('#operator button').css('visibility', 'hidden');
				return;
			}

			$('#design #' + self._currentId).removeClass('selected');
			self._currentId = $(this).attr('id');

			var $td = $('#design #' + self._currentId);
			$td.addClass('selected');

			$('#attribute input[name=id]').val(self._currentId);
			$('#operator #none').html(self._currentId);

			var x = parseInt($(this).attr('x'));
			var y = parseInt($(this).attr('y'));
			$('#operator button').each(function(i, e) {
				var $button = $(e);
				var dx = parseInt($button.attr('dx'));
				var dy = parseInt($button.attr('dy'));

				var nx = x + dx;
				var ny = y + dy;
				var next = $('#design td[x=' + nx + '][y=' + ny + ']');
				var did = $button.attr('id');
				$button.css({
					'visibility' : next.length > 0 && next.attr('selected') ? 'visible' : 'hidden',
					'color' : $td.attr($button.attr('id')) ? '#0000ff' : '#000000',
				});
				$('#attribute input[name=' + did + ']').val($td.attr(did));
			});
		});
	};
	this.initAttributeTable = function() {
		var attributes = [ 'id', 'name', 'desc' ].concat(this._directions);
		attributes.forEach(function(e) {
			$td1 = $('<td></td>').html(e);
			$td2 = $('<td></td>').append($('<input type="text" name="' + e + '"/>'));
			$td2.addClass('attribute_value');

			$tr = $('<tr></tr>').append($td1, $td2);
			$('#attribute').append($tr);
		});
	};
	this.initOperatorTable = function() {
		$('#operator button').css('visibility', 'hidden');
		$('#operator button').click(function() {
			var did = $(this).attr('id');
			var td = $('#design #' + self._currentId);
			if (td.attr(did)) {
				self._removeRelation(did);
			} else {
				self._addRelation(did);
			}
			td.click(); // 刷新
		});
	};
	this.initControlTable = function() {
		$('#control #clear').click(function() {
			location.reload();
		});
		$('#control #exportTo').click(function() {
			self.exportTo();
		});
		$('#control #importFrom').click(function() {
			self.importFrom();
		});
	};
	this._addRelation = function(did) {
		var id = parseInt(self._currentId);
		var $from = $('#design #' + id);

		var dx = parseInt($('#operator #' + did).attr('dx'));
		var dy = parseInt($('#operator #' + did).attr('dy'));

		var x = parseInt($from.attr('x'));
		var y = parseInt($from.attr('y'));
		var $to = $('#design td[x=' + (x + dx) + '][y=' + (y + dy) + ']');

		$from.attr(did, $to.attr('id'));
		$to.attr(self._reverse(did), $from.attr('id'));

		var from = self._toPngName($from.attr('id'));
		var to = self._toPngName($to.attr('id'));

		$from.css('background-image', 'url("image-design/' + from + '.png")');
		$to.css('background-image', 'url("image-design/' + to + '.png")');
	};
	this._removeRelation = function(did) {
		var id = parseInt(self._currentId);
		var $from = $('#design #' + id);
		var $to = $('#design #' + $from.attr(did));

		$from.removeAttr(did);
		$to.removeAttr(self._reverse(did));

		var from = self._toPngName(id);
		var to = self._toPngName($to.attr('id'));

		$from.css('background-image', 'url("image-design/' + from + '.png")');
		$to.css('background-image', 'url("image-design/' + to + '.png")');
	};
	this._removeNeighborRelation = function(id) {
		var $from = $('#design #' + id);
		$from.css('background-image', '');

		this._directions.forEach(function(e) {
			if ($from.attr(e)) {
				var $to = $('#design #' + $from.attr(e));
				if ($to.attr('selected')) {
					var d = self._reverse(e);
					$to.removeAttr(d);

					var pngname = self._toPngName($to.attr('id'));
					$to.css('background-image', 'url("image-design/' + pngname + '.png")');
				}
				$from.removeAttr(e);
			}
		});
	};
	this.importFrom = function() {

	};
	this.exportTo = function() {
		var results = [];
		$('#design td[selected=selected]').each(function(i, e) {
			var $td = $(e);
			var node = {
				id : $td.attr('id')
			};

			self._directions.forEach(function(d) {
				var toId = $td.attr(d);
				if (toId) {
					node[d] = toId;
				}
			});
			results.push(node);
		});

		$.post({
			url : 'saveDesign',
			dataType : 'json',
			data : {
				design : JSON.stringify(results)
			},
			success : function(data) {
				console.log(data);
			}
		});
	};
};

design.initDesignTable();
design.initAttributeTable();
design.initOperatorTable();
design.initControlTable();