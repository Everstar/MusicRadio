var sequence = -1;
var musicList = [
	'3402325',
	'22450237',
	'19264098',
	'30590170',
	'30089235',
	'27588470',
	'17996972',
	'2919622',
	'29802088',
	'19078759',
	'16621268',
	'28095742',
	'29019227',
	'4083399',
	'16686122',
	'18127640',
	'1089382',
	'28680438',
	'21373645',
	'17706562',
	'2001320',
	'27845535'
]

var audioPlayer = document.getElementById('audio-player');
var progressBar = document.getElementById('playing-progress');
var timer;


//一首歌结束，自动播放下一首
$("#audio-player").bind("ended", function () {
	clearTimeout(timer);
	play_next();
});


//播放或暂停
$('#control-play').click(function(){
	var icon = 'play_arrow';
	if(audioPlayer.paused) icon = 'pause';
	$('#control-play .material-icons').html(icon);
	audio_play(audioPlayer.paused);
});

//播放上一首
$('#control-previous').click(function(){
	play_previous();
});

//播放下一首
$('#control-next').click(function(){
	play_next();
});

//音频加载好后设置进度条最大值
$('#audio-player').bind('canplay', function() {
	progressBar.max = audioPlayer.duration;
})

//播放或暂停
function audio_play(is_pause) {
	if(is_pause) {
		audioPlayer.play();
		timer = setTimeout(progress_display, 1000);
	}
	else {
		audioPlayer.pause();
		clearTimeout(timer);
	}
}

//加载音乐
function audio_load(mp3Url) {
	audioPlayer.src = mp3Url;
}

function play_previous() {
	sequence--;
	if(sequence < 0) sequence = 21;

	play();
}

function play_next() {
	sequence++;
	if(sequence > 21) sequence = 1;

	play();
}

function play() {
	var music_info = get_music_info(musicList[sequence]);

	var nextPicPath = 'url(dynamic/img/' + sequence + '.jpg) no-repeat center';
	$('#playing-area').css('background', nextPicPath);
	$('#playing-area').css('background-size', 'cover');

	$('#name').html(music_info.song_name);
	$('#artist').html(music_info.song_artists);
	$('#control-play .material-icons').html('pause');
	audio_load(music_info.mp3Url);
	audioPlayer.play();

	timer = setTimeout(progress_display, 1000);
}

//更新进度条
function progress_display() {
	progressBar.value = audioPlayer.currentTime;
	$('.mdl-slider__background-lower').css('flex', (progressBar.value / progressBar.max) + ' 0%');
	$('.mdl-slider__background-upper').css('flex', (1.0 - progressBar.value / progressBar.max) + ' 0%');
	setTimeout(progress_display, 1000);
}

//用户拉动进度条
function progress_adjust(progress) {
	if(!audioPlayer.paused) {
		audioPlayer.currentTime = progress;
	}	
}

setTimeout(play_next, 2000);