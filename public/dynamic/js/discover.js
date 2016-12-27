var eventQueue = [];
var svg;
var element;
var drawingArea;
var width;
var height;
var volume = 0.3;

var scale_factor = 6,
    note_overlap = 2,
    note_timeout = 300,
    current_notes = 0,
    max_life = 20000;

var svg_background_color_online = '#0288D1',
    svg_background_color_offline = '#E91E63',
    svg_text_color = '#FFFFFF',
    newuser_box_color = 'rgb(41, 128, 185)',
    push_color = 'rgb(155, 89, 182)',
    issue_color = 'rgb(46, 204, 113)',
    pull_request_color = 'rgb(46, 204, 113)',
    comment_color = 'rgb(46, 204, 113)',
    edit_color = '#fff',
    total_sounds = 51;

    var celesta = [],
        clav = [],
        swells = [],
        all_loaded = false;

  var userList = ['Capitan', 'HappyFarmer', 'CDOTAD', 'EverStar'];
  var songList = ['weave music', 'hot songs of bilibli', 'for earphone', 'American Hit Songs'];
  var operationList = ['CreatePlayList', 'LikePlayList', 'CommentPlayList'];

  //第几次获取广场数据
var getEventsTimes = 0;

function getEvent() {
    getEventsTimes++;
    //var URL = 'music.neverstar.top/square';
    var URL = '/square';
    $.ajax({
        url : URL,
        type : 'GET',
        headers : {
            target : 'api'
        },
        success : function(data, textStatus, jqXHR) {
            data.sort(function(){ return 0.5 - Math.random() })
            createEvent(data);
            console.log(data);
        },
        error : function(xhr, textStatus) {
            console.log(xhr.status + '\n' + textStatus + '\n');
        }
    });
}

setTimeout(getEvent, 500);

function createEvent(events){

    for(var i = 0; i < events.length; i++) {
        var event = {
            'id': getEventsTimes.toString(10) + i.toString(10),
            'type': events[i].type.toUpperCase(),
            'user': events[i].username,
            'user_avatar': 'no',
            'repo_id': 'no',
            'repo_name': events[i].songlist_name,
            'payload_size': '233',
            'message':'nothing',
            'created': '233',
            'url': '/user/' + events[i].id,
        };

        if(!isEventInQueue(event))
            eventQueue.push(event);
    }

}


/**
* This function checks whether an event is already in the queue
*/
function isEventInQueue(event){
  for(var i=0; i<eventQueue.length; i++){
    if(eventQueue[i].id == event.id)
      return true;
  }
  return false;
}


$(function(){
  element = document.documentElement;
  drawingArea = document.getElementsByTagName('#area')[0];
  width = window.innerWidth || element.clientWidth || drawingArea.clientWidth;
  var header = $('#area').parent().parent().children()[0];
  height = (window.innerHeight - header.clientHeight)|| (element.clientHeight - header.clientHeight) || (drawingArea.clientHeight - header.clientHeight);
  //$('svg').css('background-color', svg_background_color_online);
  $('svg text').css('color', svg_text_color);
  $('#volumeSlider').slider({
    'max': 100,
    'min': 0,
    'value': volume*100,
    'slide' : function(event, ui){
      volume = ui.value/100.0;
      Howler.volume(volume);
    },
    'change' : function(event, ui){
      volume = ui.value/100.0;
      Howler.volume(volume);
    }
  });

  // Main drawing area
  svg = d3.select("#area").append("svg");
  svg.attr({width: width, height: height});
  //svg.style('background-color', svg_background_color_online);
  svg.style('background-color', 'transparent');

  // For window resizes
  var update_window = function() {
      var header = $('#area').parent().parent().children()[0];
      if(header == null) return;
      width = window.innerWidth || element.clientWidth || drawingArea.clientWidth;
      height = (window.innerHeight - header.clientHeight)|| (element.clientHeight - header.clientHeight) || (drawingArea.clientHeight - header.clientHeight);
      svg.attr("width", width).attr("height", height);
  }
  window.onresize = update_window;
  update_window();

  var loaded_sounds = 0;
  var sound_load = function(r) {
      loaded_sounds += 1;
      if (loaded_sounds == total_sounds) {
          all_loaded = true;
          setTimeout(playFromQueueExchange, Math.floor(Math.random() * 1000));
          // Starting the second exchange makes music a bad experience
      }
  };

  // Load sounds
  for (var i = 1; i <= 24; i++) {
      if (i > 9) {
          fn = 'c0' + i;
      } else {
          fn = 'c00' + i;
      }
      celesta.push(new Howl({
          src : ['https://d1fz9d31zqor6x.cloudfront.net/sounds/celesta/' + fn + '.ogg',
                  'https://d1fz9d31zqor6x.cloudfront.net/sounds/celesta/' + fn + '.mp3'],
          volume : 0.7,
          onload : sound_load(),
          buffer: true,
      }));
      clav.push(new Howl({
          src : ['https://d1fz9d31zqor6x.cloudfront.net/sounds/clav/' + fn + '.ogg',
                  'https://d1fz9d31zqor6x.cloudfront.net/sounds/clav/' + fn + '.mp3'],
          volume : 0.4,
          onload : sound_load(),
          buffer: true,
      }))
  }

  for (var i = 1; i <= 3; i++) {
      swells.push(new Howl({
          src : ['https://d1fz9d31zqor6x.cloudfront.net/sounds/swells/swell' + i + '.ogg',
                  'https://d1fz9d31zqor6x.cloudfront.net/sounds/swells/swell' + i + '.mp3'],
          volume : 1,
          onload : sound_load(),
          buffer: true,
      }));
  }

  Howler.volume(volume);

  // Make header and footer visible
  $('body').css('visibility', 'visible');

});


/**
* Randomly selects a swell sound and plays it
*/
function playRandomSwell() {
    var index = Math.round(Math.random() * (swells.length - 1));
    swells[index].play();
}


/**
* Plays a sound(celesta and clav) based on passed parameters
*/
function playSound(size, type) {
    var max_pitch = 100.0;
    var log_used = 1.0715307808111486871978099;
    var pitch = 100 - Math.min(max_pitch, Math.log(size + log_used) / Math.log(log_used));
    var index = Math.floor(pitch / 100.0 * Object.keys(celesta).length);
    var fuzz = Math.floor(Math.random() * 4) - 2;
    index += fuzz;
    index = Math.min(Object.keys(celesta).length - 1, index);
    index = Math.max(1, index);
    if (current_notes < note_overlap) {
        current_notes++;
        if (type == 'IssuesEvent' || type == 'IssueCommentEvent') {
            clav[index].play();
        } else if(type == 'PushEvent') {
            celesta[index].play();
        }else{
          playRandomSwell();
        }
        setTimeout(function() {
            current_notes--;
        }, note_timeout);
    }
}

// Following are the n numbers of event consumers
// consuming n events each per second with a random delay between them

function playFromQueueExchange(){
  var event = eventQueue.shift();
  if(event != null && svg != null){
    //playSound(event.message.length*1.1, event.type);
    if(!document.hidden)
      drawEvent(event, svg);
  }else if(event == null) {
      setTimeout(getEvent, 500);
  }
  setTimeout(playFromQueueExchange, Math.floor(Math.random() * 1000) + 3000);
  $('.events-remaining-value').html(eventQueue.length);
}

// This method capitalizes the string in place
String.prototype.capitalize=function(all){
    if(all){
      return this.split(' ').map(function(e){
        return e.capitalize().join(' ');
      });
    }else{
         return this.charAt(0).toUpperCase() + this.slice(1);
    }
};


function drawEvent(data, svg_area) {
    var starting_opacity = 1;
    var opacity = 1 / (100 / data.message.length);
    if (opacity > 0.5) {
        opacity = 0.5;
    }
    //var size = data.message;
    var size = (Math.random() * 100);
    var label_text;
    var ring_radius = 80;
    var ring_anim_duration = 3000;
    svg_text_color = '#000000';
    switch(data.type){
      case "CREATE":
        label_text = data.user.capitalize() + " CREATE <" + data.repo_name + ">";
        edit_color = '#80DEEA';
      break;
      case "COMMENT":
        label_text = data.user.capitalize() + " COMMENT <" + data.repo_name + ">";
        edit_color = '#FF9800';
        ring_anim_duration = 10000;
        ring_radius = 600;
      break;
      case "LIKE":
        label_text = data.user.capitalize() + " LIKE <" + data.repo_name + ">";
        edit_color = '#FFEB3B';
      break;
    }
    var csize = size;
    var no_label = false;
    var type = data.type;

    var circle_id = 'd' + ((Math.random() * 100000) | 0);
    var abs_size = Math.abs(size);
    size = Math.max(Math.sqrt(abs_size) * scale_factor, 3);

    Math.seedrandom(Math.random() * 10)
    var x = Math.random() * (width - size) + size;
    var y = Math.random() * (height - size) + size;


    var circle_group = svg_area.append('g')
        .attr('transform', 'translate(' + x + ', ' + y + ')')
        .attr('fill', edit_color)
        .style('opacity', starting_opacity)


    var ring = circle_group.append('circle');
    ring.attr({r: size, stroke: 'none'});
    ring.transition()
        .attr('r', size + ring_radius)
        .style('opacity', 0)
        .ease(Math.sqrt)
        .duration(ring_anim_duration)
        .remove();

    var circle_container = circle_group.append('a');
    circle_container.attr('href', '/#' + data.url);
    //circle_container.attr('target', '_blank');
    circle_container.attr('fill', svg_text_color);

    var circle = circle_container.append('circle');
    circle.classed(type, true);
    circle.attr('r', size)
      .attr('fill', edit_color)
      .transition()
      .duration(max_life)
      .style('opacity', 0)
      .remove();


    circle_container.on('mouseover', function() {
      circle_container.append('text')
          .text(label_text)
          .classed('label', true)
          .attr('text-anchor', 'middle')
          .attr('font-size', '0.8em')
          .transition()
          .delay(1000)
          .style('opacity', 0)
          .duration(2000)
          .each(function() { no_label = true; })
          .remove();
    });

    var text = circle_container.append('text')
        .text(label_text)
        .classed('article-label', true)
        .attr('text-anchor', 'middle')
        .attr('font-size', '0.8em')
        .transition()
        .delay(2000)
        .style('opacity', 0)
        .duration(5000)
        .each(function() { no_label = true; })
        .remove();

  // Remove HTML of decayed events
  // Keep it less than 50
  if($('#area svg g').length > 50){
    $('#area svg g:lt(10)').remove();
  }
}
