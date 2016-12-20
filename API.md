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


### 喜爱/点赞/评论

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
    *songlist_id* : {Integer}歌曲所在的歌单
    *song_id* : {Integer}
    }

* 评论歌单
	@cookie
	[POST]
    UrlPattern = "/addcomment"
    data={
    *songlist_id* : {Integer},
    *content* : {String}
    }
    返回JSON{
    *comment_id* : {Integer}
    }
* 查看所有评论
	[GET]
    UrlPattern = "/comment?id={Integer}"
    返回JSON[{
    *user_id*  : {Integer},
    *avator_url* : {String},
    *content* : {String},
    *time* : {String}
    *likes* : {Integer}
    }]

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
	2. 删除歌单
		[POST]
        UrlPattern = "/deletelist"
        data = {
        *`_id* : {Integer}
        }
        返回JSON{
        *result*:{bool}
        }

	3. 更改歌单信息
		[POST]
        UrlPattern = "/changelist"
        data = {
        *songlist_id* : {Integer},
        *image_file* : {Stream},
        *image_url* : {String},
        *image_id* : {Integer}原来的图片ID
        *songlist_name*: {String},
        *description* :｛String},
        }
        返回JSON{
        *result*:{bool}
        }

	4. 删除歌曲
		[POST]
        UrlPattern = "/removesong"
        data = {
        *songlist_id* : {Integer},
        *song_id* : {Integer}
        }
        返回JSON{
        *result*:{bool}
        }
	5. 更改歌曲背景图
		[POST]
        UrlPattern = "/changesong"
        data = {
        *songlist_id* : {Integer},
        *song_id* : {Integer},
        *image_file* : {Stream},
        *image_url* : {String}
        *image_id* : {Integer}原来的图片ID
        }
        返回JSON{
        *result*:{bool}
        }
	6. 增加歌曲
		情景一：网易云搜索歌曲(歌曲记录未必存在)
        情景二：别人的歌单(歌曲记录已经存在)
        情景三：本地上传
		1. 情景一
		[POST]
        UrlPattern = "/addsong/netease"
         data = {
        *songlist_id* : {Integer},
        *netease_id* : {Integer},
        *song_name* : {String},
        *song_artists* : {String}.
        *mp3Url* : {String},
        *duration* : {Integer},
        *language* : {String},(zh-cn|en-us|ja-jp|fr-fr|ko-kr)
        *styles* : {String}(1~21位01串)
        }
        返回JSON{
        *song_id*:{Integer}
        }
		2. 情景二
		[POST]
        UrlPattern = "/addsong/songlist"
        data = {
        *songlist_id* : {Integer},
        *song_id* : {Integer}
        }
        返回JSON{
        *result*:{bool}
        }
        3. 情景三
        [POST]
        UrlPattern = "/addsong/upload"
        data = {
        *songlist_id* : {Integer},
        *song_file* : {Stream},
        *language* : {String},
        *styles* : {String}
        }
        返回JSON{
        *song_id*:{Integer}
        }

	7. 获取歌单的歌曲信息
	[GET]
    UrlPattern = "/songlist/one?id={Integer}"
    返回JSON{
    *song_id* : {Integer},
    *song_name* : {String},
    *artists* : {String},
    *duration* : {Integer},
    *mp3Url* : {String}
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
    * 关注他人
    @cookie
    [POST]
    UrlPattern = "/follow"
    data={
    *user_id* : {Integer}
    }
    返回JSON{
    *result* : {bool}
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

* 网易云相关 以下song\_id实际上均为netease\_id
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