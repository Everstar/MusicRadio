/*
 * create by everstar
 * 2016.11.30
*/

function get_music_info(song_id) {

	//var URL = 'http://music.neverstar.top/api/song';
	var URL = 'http://127.0.0.1:3000/api/song';

	var music_info;

	$.ajax({
		url : URL,
		async : false,
		type : 'GET',
		data : {
			id : song_id,
			ids : '[' + song_id + ']'
		},
		success : function(data, textStatus, jqXHR) {
			music_info = data;
			//console.log(textStatus);
		},
		error : function(xhr, textStatus) {
			//console.log(xhr.status + '\n' + textStatus + '\n');
		}
	});
	return music_info;
}