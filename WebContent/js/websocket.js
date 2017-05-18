var socket = new WebSocket('ws://' + window.location.host + '/mymud/actions');

socket.onmessage = function(e) {
	var result = JSON.parse(e.data);
	console.log(result);

	mymud.showAreaMsg(result.room);
	mymud.showOutMsg(result.message);
};

$('#send').click(function() {
	var cmd = $('#cmd').val().trim();
	if (cmd.length) {
		socket.send(cmd);
	}
});

$('#cmd').keyup(function(e) {
	if (e.keyCode == 13) {
		$('#send').click();
	}
});