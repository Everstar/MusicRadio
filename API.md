# Music Radio

### API

以下 {} 表示数据, 括号内表示数据类型

example ：{bool} 表示bool值

### 账户
* 查询用户名是否存在
	[GET]
	UrlPattern = "/account?id={Integer}"

	返回JSON {
	*result* : {bool}
	}

* 注册
    [POST]
    UrlPattern = "/signup"

    data = {
    *username* : {String},
    *password* : {String},
    *gender* : {String}
    }

	返回JSON {
    *result* : {bool}
    }

* 登录
    [POST]
    UrlPattern = "/signin"

    data = {
    *username* : {String},
    *password* : {String}
    }

    返回JSON {
    *result* : {bool}，
    *token* : {String}
    }

* 上传头像
	@cookie
	[POST]
    UrlPattern = "/avator"
    data = {
    *imgFile* : {file}
    }


### 喜爱/点赞

* 点赞歌单
	@cookie
	[POST]
    UrlPattern = "/likesonglist"
    data = {
    *songlist_id* : {Integer}
    }

* 喜爱歌曲
	@cookie
    [POST]
    UrlPattern = "/likesong"
    data = {
    *song_id* : {Integer}
    }

### 歌单

* 获取歌单列表
	1. 排行榜
    [GET]
    UrlPattern = "/hotlist?num={Integer}"

	2. 用户自己创建的
	@cookie
	[POST]
	UrlPattern = "/songlist"

	3. 用户看另一用户创建的
	[GET]
	UrlPattern = "/songlist?id={Integer}"

    返回JSON {
    *result* : [
        {*songlist_name*:{String}, *img_url*:{String}, *author*:{String}, *author_id* : {Integer}, *liked* : {Integer}},
        {*songlist_name*:{String}, *img_url*:{String}, *author*:{String}, *author_id* : {Integer}, *liked* : {Integer}},
        ...
    ]
    }

* 歌单管理
	1. 创建歌单
		[POST]
        UrlPattern = "/newlist"
        data = {
        *songlist_name*: {String},
        *description* :｛String},
        }
        返回JSON{
        *songlist_id* : {Integer}
        }
	1. 删除歌单
		[POST]
        UrlPattern = "/deletelist"
        data = {
        *`_id* : {Integer}
        }
        返回JSON{
        *result*:{bool}
        }
	1. 更改歌单名称和描述
		[POST]
        UrlPattern = "/changelist"
        data = {
        *songlist_id* : {Integer},
        *songlist_name*: {String},
        *description* :｛String},
        }
        返回JSON{
        *result*:{bool}
        }
	1. 增加歌曲
		[POST]
        UrlPattern = "/addsong"
        data = {
        *songlist_id* : {Integer},
        *songType* : {String}, <%= file | 网易云音乐歌曲ID%>,
        *songFile* : {file},
        *songID* : {String},
        *imgType* : {String}, <%= file | url%> ,
        *imgFile* : {file},
        *imgUrl*  : {Url},
        }
        返回JSON{
        *result*:{bool}
        }
	1. 删除歌曲
		[POST]
        UrlPattern = "/removesong"
        data = {
        *songlist_id* : {Integer},
        *song_id* : {Integer}
        }
        返回JSON{
        *result*:{bool}
        }
	1. 更改歌曲背景图
		[POST]
        UrlPattern = "/changesong"
        data = {
        *songlist_id* : {Integer},
        *song_id* : {Integer}
        *imgType* : {String}, <%= file | url%> ,
        *imgFile* : {file},
        *imgUrl*  : {Url},
        }



###推荐系统


### 社交
* 用户信息相关
	* 个人主页
		1. 用户自己
        @cookie

        [POST]
        UrlPattern = "/info"

        2. 其他用户
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
    * 获取好友列表
	@cookie
    [GET]
    UrlPattern = "/follow"

    返回JSON {
    *result* : [
        {*username*:{String}, *id* : {Integer}, *avator_url*:{String}},
        {*username*:{String}, *id* : {Integer}, *avator_url*:{String}},
        ...
    ]
    }

* 动态
	1. 用户动态
    [GET]
    UrlPattern = "/moment?id="

	2. 好友动态
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

* 网易云相关
	* 根据音乐ID返回音乐信息
	[GET]
	UrlPattern = "/api/song?id={String}"

	返回JSON {
		*song_id* : {Integer},
		*song_name* : {String},
		*song_artists* : {String},
		*mp3Url* : {String}
	}

	* 按照关键字搜索歌曲
    [GET]
    UrlPattern = "/api/search?key={String}&num={Integer}"

    返回JSON {
        *song_id* : {Integer},
        *song_name* : {String},
        *song_artists* : {String},
        *duration* : {Integer}
    }

* 非网易云音乐相关
	* 根据图片ID返回图片地址
    [GET]
    UrlPattern = "/img?id={String}"

    返回JSON {
        *imgUrl* : {String}
    }