/*
 * 地图设计器
 */
var design = new Design();

function Design() {
	this._currentId = -1;
	this._lineCount = 20;
	this._columnCount = 20;
	this._directions = [ 'east', 'southeast', 'south', 'southwest', 'west', 'northwest', 'north', 'northeast' ];
	this._attributes = [ 'id', 'nick', 'desc', 'mid' ];
	this._globals    = [ 'mapid', 'mapname', 'startroom'];

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
				$(this).removeAttr('selected');
				$(this).html('');
				self._removeNeighborRelation($(this).attr('id'));
			} else {
				$(this).html($(this).attr('id'));
				$(this).attr('selected', 'selected');
			}
			$(this).click();
		}).hover(function() {
			$(this).addClass('hover_on');
		}, function() {
			$(this).removeClass('hover_on');
		}).click(function() {
			if (!$(this).attr('selected')) {
				$('#direction button').css('visibility', 'hidden');
				return;
			}

			$('#design #' + self._currentId).removeClass('focused');
			self._currentId = $(this).attr('id');

			var $td = $('#design #' + self._currentId);
			$td.addClass('focused');

			$('#attribute input[name=id]').val(self._currentId);
			$('#direction #current').html(self._currentId);

			var x = parseInt($(this).attr('x'));
			var y = parseInt($(this).attr('y'));
			$('#direction button').each(function(i, e) {
				var $button = $(e);
				var dx = parseInt($button.attr('dx'));
				var dy = parseInt($button.attr('dy'));

				var nx = x + dx;
				var ny = y + dy;
				var next = $('#design td[x=' + nx + '][y=' + ny + ']');
				var did = $button.attr('id');
				$button.css({
					'visibility' : next.length > 0 && next.attr('selected') ? 'visible' : 'hidden',
					'background-color' : $td.attr($button.attr('id')) ? '#00ff00' : '',
				});
			});
			self._attributes.forEach(function(e) {
				$('#attribute input[name=' + e + ']').val($td.attr(e));
			});
		});
	};
	this.initAttributeTable = function() {
		this._attributes.forEach(function(e) {
			if (e != 'id') {
				$input = $('#attribute input[name=' + e + ']');
				$input.change(function() {
					$td = $('#design #' + self._currentId);
					$td.attr(e, $(this).val());
					$td.html(func.trimHtmlColor($(this).val()));
				});
			}
		});
	};
	this.initDirectionTable = function() {
		$('#direction button').css('visibility', 'hidden');
		$('#direction button').click(function() {
			var did = $(this).attr('id');
			var td = $('#design #' + self._currentId);
			if (td.attr(did)) {
				self._removeRelation(did);
			} else {
				var id = self._currentId;
				self._addRelation(id, did);
			}
			td.click(); // 刷新
		});
	};
	this.initOperatorTable = function() {
		$('#operator #exportToCSV').click(function() {
			self.exportToCSV();
		});
		$('#operator #exportToJSON').click(function() {
			self.exportToJSON();
		});
		$('#operator #importFrom').click(function() {
			$('#upload').click();
		});
		$('#upload').change(function() {
			var fileName = $(this).val();
			var file = document.getElementById('upload').files[0];
			var reader = new FileReader();

			reader.onloadend = function(evt) {
				if (evt.target.readyState == FileReader.DONE) {
					var text = evt.target.result;
					if (fileName.toLowerCase().endsWith('json')) {
						self.importFromJSON(text);
					}
					if (fileName.toLowerCase().endsWith('csv')) {
						self.importFromCSV(text);
					}
				}
			};
			if (file && file.size) {
				reader.readAsText(file);
			}
		});

		$('#showid').click(function() {
			self._refreshShow('id');
		});
		$('#shownick').click(function() {
			self._refreshShow('nick');
		});
		$('#showmid').click(function() {
			self._refreshShow('mid');
		});
		$('#beginmid').keypress(function(e) {
			return !isNaN(e.key);
		}).keyup(function() {
			$('#generatemid').attr('disabled', $(this).val().length == 0 || self._isEmpty());
		});
		$('#generatemid').click(function() {
			self._generatemid();
		});
	};
	this._addRelation = function(id, did) {
		var $from = $('#design #' + id);

		var dx = parseInt($('#direction #' + did).attr('dx'));
		var dy = parseInt($('#direction #' + did).attr('dy'));

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
	this.exportToCSV = function() {
		if (this._isEmpty()) {
			alert('验证不通过!');
			return;
		}

		// titles
		var allAttributes = this._attributes.concat(this._directions);
		var results = allAttributes.toString() + '\n';

		// globals
		var globals = {};
		this._globals.forEach(function(e) {
			globals[e] = $('#globalmap #' + e).val();
		});
		results += JSON.stringify(globals).replace(/,/g, '|') + '\n';

		// rooms
		$('#design td[selected=selected]').each(function(i, e) {
			var $td = $(e);
			var line = [];
			allAttributes.forEach(function(d) {
				line.push($td.attr(d));
			});
			results += line.toString() + '\n';
		});
		$('#download input[name=design]').val(results.toString());
		$('#download input[name=filename]').val($('#mapid').val() + '.csv');
		$('#download').submit();
	};
	this.exportToJSON = function() {
		if (!this._validateExport()) {
			alert('验证不通过!');
			return;
		}

		var result = {globals:{}, rooms: []};
		// globals
		this._globals.forEach(function(e) {
			result.globals[e] = $('#globalmap #' + e).val();
		});

		// room
		var attributes = this._attributes.concat(this._directions);
		$('#design td[selected=selected]').each(function(i, e) {
			var $td = $(e);
			var line = {};
			attributes.forEach(function(d) {
				if ($td.attr(d)) {
					line[d] = $td.attr(d);
				}
			});
			result.rooms.push(line);
		});

		$('#download input[name=design]').val(JSON.stringify(result));
		$('#download input[name=filename]').val($('#mapid').val() + '.json');
		$('#download').submit();
	};
	this._validateExport = function() {
		if (this._isEmpty()) {
			return false;
		}
		if ($('#mapid').val().length == 0) {
			return false;
		}
		if ($('#mapname').val().length == 0) {
			return false;
		}
		if ($('#startroom').val().length == 0) {
			return false;
		}

		if (!/^[a-zA-Z][a-zA-Z0-9]*$/g.test($('#mapid').val())) {
			return false;
		}
		if (!/^[0-9]+$/g.test($('#startroom').val())) {
			return false;
		}
		var result = false;
		var startRoomId = parseInt($('#startroom').val());
		$('#design td[selected=selected').each(function(i, e) {
			if ($(e).attr('id') == startRoomId) {
				result = true;
				return false;
			}
		});

		return result;
	};
	this.importFromCSV = function(text) {
		var lines = text.split('\n');
		var titles = [];

		for ( var i in lines) {
			var line = lines[i].trim();
			if (line.length == 0) {
				continue;
			}
			if (i == 0) {
				titles = line.split(',');
				continue;
			}
			if (i == 1) { // 全局属性
				line = line.replace(/\|/g, ',');// 还原
				var globals = JSON.parse(line);
				this._globals.forEach(function(e) {
					$('#globalmap #' + e).val(globals[e]);
				});
				continue;
			}
			var fields = line.split(',');

			var id = fields[titles.indexOf('id')];
			$('#design #' + id).html(id);
			$('#design #' + id).attr('selected', 'selected');

			for ( var j in titles) {
				var a = titles[j];
				if (a == 'id' || self._directions.indexOf(a) >= 0) {
					continue;
				}
				$('#design #' + id).attr(a, fields[j]);
			}

			this._directions.forEach(function(e) {
				var index = titles.indexOf(e);

				if (index >= 0 && fields[index].length) {
					self._addRelation(id, e);
				}
			});
		}
	};
	this.importFromJSON = function(text) {
		var objects = JSON.parse(text);

		// globals
		this._globals.forEach(function(e) {
			$('#globalmap #' + e).val(objects.globals[e]);
		});

		// rooms
		for ( var i in objects.rooms) {
			var obj = objects.rooms[i];
			var id = obj.id;
			$('#design #' + id).html(id);
			$('#design #' + id).attr('selected', 'selected');

			var attributes = Object.getOwnPropertyNames(obj);
			for ( var j in attributes) {
				var a = attributes[j];
				if (a == 'id' || self._directions.indexOf(a) >= 0) {
					continue;
				}
				$('#design #' + id).attr(a, obj[a]);
			}

			this._directions.forEach(function(e) {
				if (obj.hasOwnProperty(e)) {
					self._addRelation(id, e);
				}
			});
		}
	};
	this._refreshShow = function(attrName) {
		$('#design td[selected=selected]').each(function(i, e) {
			if ($(e).attr(attrName)) {
				$(e).html(func.trimHtmlColor($(e).attr(attrName)));
			}
		});
	};
	this._generatemid = function() {
		if (this._isEmpty()) {
			return;
		}
		if (!confirm('确定要生成MID吗？')) {
			return;
		}

		var mid = parseInt($('#beginmid').val() || 0);
		$('#design td[selected=selected]').each(function(i, e) {
			$(e).attr('mid', mid++);
		});
		this._refreshShow('mid');
	};
	this._isEmpty = function() {
		return $('#design td[selected=selected]').length == 0;
	};
};

design.initDesignTable();
design.initAttributeTable();
design.initDirectionTable();
design.initOperatorTable();