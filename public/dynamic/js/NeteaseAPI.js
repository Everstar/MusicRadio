/*
 * create by everstar
 * 2016.11.30
*/

var REMOTE_URL = 'http://localhost:3344';
// var REMOTE_URL = 'http://221.239.199.226:3344';

function get_music_info(song_id) {

	//var URL = 'http://music.neverstar.top/api/song';
	var URL = REMOTE_URL + '/api/song';

	var music_info = {};

	$.ajax({
		url : URL,
		async : false,
		type : 'GET',
		headers : {
			target : 'api'
		},
		data : {
			id : song_id,
		},
		success : function(data, textStatus, jqXHR) {
			music_info = data;
		},
		error : function(xhr, textStatus) {
			console.log(xhr.status + '\n' + textStatus + '\n');
		}
	});
	return music_info;
}

function get_local_music_info(song_id) {

    var URL = REMOTE_URL + '/song';

    var music_info = {};

    $.ajax({
        url : URL,
        async : false,
        type : 'GET',
        headers : {
            target : 'api'
        },
        data : {
            id : song_id,
        },
        success : function(data, textStatus, jqXHR) {
            music_info = data;
        },
        error : function(xhr, textStatus) {
            music_info = undefined;
            console.log(xhr.status + '\n' + textStatus + '\n');
        }
    });
    if(music_info == undefined ) {return undefined;}
    if(music_info.netease_id != 0) {
    	music_info = get_music_info(music_info.netease_id);
	}else {
        music_info.song_artists = music_info.artists;
        music_info.mp3Url = music_info.song_url;
    }
    return music_info;
}