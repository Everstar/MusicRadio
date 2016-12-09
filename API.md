# Music Radio

> 我自己觉得这个项目应该是音乐电台才对所以把名字改成了music radio


## API

以下 {} 表示数据, 括号内表示数据类型

example ：{bool} 表示bool值

### 用户

#### 查询用户名是否存在

为了保证用户名unique 需要在注册时保证用户名不曾被注册过
同时登录的时候如果用户输入了一个不存在的用户名也可以提示：)

[GET]
UrlPattern = "/account?id={Integer}"

返回JSON {*result* : {bool}}

#### 注册

[POST]
UrlPattern = "/signup"

data = {
	*username* : {String},
	*password* : {String},
	*gender* : {String}
}

返回JSON {*result* : {bool}}

#### 登录

[POST]
UrlPattern = "/signin"

data = {
	*username* : {String},
	*password* : {String}
}

返回JSON {*result* : {bool}, *token* : {String}}


#### 获取歌单列表

1.排行榜

[GET]
UrlPattern = "/hotlist?num={Integer}"

2.用户自己创建的
@cookie

[POST]
UrlPattern = "/songlist"

3.用户看另一用户创建的
[GET]
UrlPattern = "/songlist?id={Integer}"


返回JSON {
	*result* : [
		{*songlist_name*:{String}, *img_url*:{String}, *author*:{String}, *author_id* : {Integer}, *liked* : {Integer}},
		{*songlist_name*:{String}, *img_url*:{String}, *author*:{String}, *liked* : {Integer}},
		...
	]
}

#### 获取好友列表

@cookie
[GET]
UrlPattern = "/friends"

返回JSON {
	*result* : [
		{*username*:{String}, *id* : {Integer}, *avator_url*:{String}},
		{*username*:{String}, *id* : {Integer}, *avator_url*:{String}},
		...
	]
}



#### 个人主页

1.用户自己

@cookie

[POST]
UrlPattern = "/info"

2.其他用户
[GET]
UrlPattern = "/info?id={Integer}"

返回JSON {
	*id* : {Integer},
	*username* : {String},
	*level* : {Integer},
	*gender* : {String},
	*avator_url* : {String},
	*exp* : {Integer},
	*exp_max* : {Integer},
	*ctr_songlist* : {Integer},
	*liked_songlist* : {Integer},
	*friends_num* : {Integer}
}

#### 动态

1.用户动态
[GET]
UrlPattern = "/moment?id="

2.好友动态
@cookie
[POST]
UrlPattern = "/moment"

enum {create, like, comment}

返回JSON {
	*result* : [
		{*username*:{String}, *id* : {Integer}, *type*:{String->enum}, *songlist_name*:{String}, *time*:{String}},
		{*username*:{String}, *id* : {Integer}, *type*:{String->enum}, *songlist_name*:{String}, *time*:{String}},
		...
	]
}

### 音乐

#### 根据音乐ID返回音乐信息

[GET]
UrlPattern = "/api/song?id={String}"

返回JSON {
	*song_id* : {Integer},
	*song_name* : {String},
	*song_artists* : {String},
	*mp3Url* : {String}
}


>可能不会用到
#### 根据图片ID返回图片地址

[GET]
UrlPattern = "/img?id={String}"

返回JSON {
	*imgUrl* : {String}
}