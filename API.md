# Music Radio

### API

以下 {} 表示数据, 括号内表示数据类型

example ：{bool} 表示bool值

### 账户
* 查询用户名是否存在
    [GET]
    UrlPattern = "/account?id={String}"

    @response {
    *result* : {bool}
    }

* 注册
    [POST]
    UrlPattern = "/signup"

    @request{
    *username* : {String},
    *password* : {String},
    *gender* : {String}
    }

    @response {
    *result* : {bool}
    }

* 登录
    [POST]
    UrlPattern = "/signin"

    @request{
    *username* : {String},
    *password* : {String}
    }

    @response {
    *result* : {bool}
    }

* 登出
    @cookie
    [GET]
    UrlPattern = "/signout"

    @response {
    *result* : {bool}，
    }

* 上传头像
    @cookie
    [POST]
    UrlPattern = "/update"
    @request{
    *image_file* : {file}
    }


### 喜爱/点赞/评论

* 点赞歌单
    @cookie
    [POST]
    UrlPattern = "/likesonglist"
    @request{
    *songlist_id* : {Integer}
    }

* 喜爱歌曲
    @cookie
    [POST]
    UrlPattern = "/likesong"
    @request{
    *songlist_id* : {Integer}歌曲所在的歌单
    *song_id* : {Integer}
    }

* 评论歌单
    @cookie
    [POST]
    UrlPattern = "/addcomment"
    @request{
    *songlist_id* : {Integer},
    *content* : {String}
    }
    @response{
    *comment_id* : {Integer}
    }
* 查看所有评论
    [GET]
    UrlPattern = "/comment?id={Integer}"
    @response[{
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

    @response {
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
        @request{
        *songlist_name*: {String},
        *description* :｛String},
        }
        @response{
        *songlist_id* : {Integer}
        }
    2. 删除歌单
        [POST]
        UrlPattern = "/deletelist"
        @request{
        *songlist_id* : {Integer}
        }
        @response{
        *result*:{bool}
        }

    3. 更改歌单信息
        [POST]
        UrlPattern = "/changelist"
        @request{
        *songlist_id* : {Integer},
        *image_file* : {Stream},
        *image_url* : {String},
        *image_id* : {Integer}原来的图片ID
        *songlist_name*: {String},
        *description* :｛String},
        }
        @response{
        *result*:{bool}
        }

    4. 删除歌曲
        [POST]
        UrlPattern = "/removesong"
        @request{
        *songlist_id* : {Integer},
        *song_id* : {Integer}
        }
        @response{
        *result*:{bool}
        }
    5. 更改歌曲背景图
        [POST]
        UrlPattern = "/changesong"
        @request{
        *songlist_id* : {Integer},
        *song_id* : {Integer},
        *image_file* : {Stream},
        *image_url* : {String}
        *image_id* : {Integer}原来的图片ID
        }
        @response{
        *result*:{bool}
        }
    6. 增加歌曲
        情景一：网易云搜索歌曲(歌曲记录未必存在)
        情景二：别人的歌单(歌曲记录已经存在)
        情景三：本地上传
        1. 情景一
        [POST]
        UrlPattern = "/addsong/netease"
         @request{
        *songlist_id* : {Integer},
        *netease_id* : {Integer},
        *song_name* : {String},
        *song_artists* : {String}.
        *mp3Url* : {String},
        *duration* : {Integer},
        *language* : {String},(zh-cn|en-us|ja-jp|fr-fr|ko-kr)
        *styles* : {String}(1~21位01串)
        }
        @response{
        *song_id*:{Integer}
        }
        2. 情景二
        [POST]
        UrlPattern = "/addsong/songlist"
        @request{
        *songlist_id* : {Integer},
        *song_id* : {Integer}
        }
        @response{
        *result*:{bool}
        }
        3. 情景三
        [POST]
        UrlPattern = "/addsong/upload"
        @request{
        *songlist_id* : {Integer},
        *song_file* : {Stream},
        *language* : {String},
        *styles* : {String}
        }
        @response{
        *song_id*:{Integer}
        }

    7. 获取歌单的歌曲信息
    [GET]
    UrlPattern = "/songlist/one?id={Integer}"
    @response{
    *song_id* : {Integer},
    *image_id* : {Integer},
    *image_url* : {String},
    *song_name* : {String},
    *artists* : {String},
    *duration* : {Integer},
    *mp3Url* : {String}
    }

### 广场

*广场泡泡
    [GET]
    UrlPattern = "/square"
    @response {[
        {
            *id* : {Integer},
            *songlist_id* : {Integer},
            *songlist_name* : {Integer},
            *type*  : {String enum:{"create", "like", "comment"}},
            *username* : {String}
        },
        ...
    ]}

### 推荐系统


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

        @response {
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

    @response {
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
    @request{
    *user_id* : {Integer}
    }
    @response{
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

    @response {
    *result* : [
    {*username*:{String}, *id* : {Integer}, *type*:{String->enum},
    *songlist_id* : {Integer}, *songlist_name*:{String}, *time*:{String}
    },
    {*username*:{String}, *id* : {Integer}, *type*:{String->enum},      *songlist_id* : {Integer}, *songlist_name*:{String}, *time*:{String}},
    ...
    ]
    }

### 音乐

* 网易云相关 以下song\_id实际上均为netease\_id
    * 根据音乐ID返回音乐信息
    [GET]
    UrlPattern = "/api/song?id={String}"

    @response {
        *song_id* : {Integer},
        *song_name* : {String},
        *song_artists* : {String},
        *mp3Url* : {String}
    }

    * 按照关键字搜索歌曲
    [GET]
    UrlPattern = "/api/search?key={String}&num={Integer}"

    @response {
        *song_id* : {Integer},
        *song_name* : {String},
        *song_artists* : {String},
        *duration* : {Integer}
    }

* 非网易云音乐相关

    *根据本地音乐ID返回歌曲信息
    [GET]
    UrlPattern = "/song?id={String}"


        - 如果是网易云音乐歌曲
        @response {
            *netease_id* : {Integer}
        }

        - 如果是本地上传歌曲
        @response {
            *song_id* : {Integer},
            *song_name* : {String},
            *song_artists* : {String},
            *mp3Url* : {String}
        }


    * 根据图片ID返回图片地址
    [GET]
    UrlPattern = "/img?id={String}"

    @response {
        *imgUrl* : {String}
    }