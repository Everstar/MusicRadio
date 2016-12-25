/**
 * Created by kevin on 12/7/2016.
 */

let URL = 'http://music.neverstar.top';
// let URL = 'http://221.239.199.226:3344';

//后端API
class API {

    static title = 'Music Radio';

    //账户
    static SignIn = URL + '/signin';//success
    static SignUp = URL + '/signup';//success
    static Account = URL + '/account';//success
    static Avatar = URL + '/avator';

    //喜爱/点赞
    static LikeSongList = URL + '/likesonglist';//success
    static LikeSong = URL + '/likesong';//success

    //歌单
    static HotList = URL + '/hotlist';//success
    static SongList = URL + '/songlist';//自己success  他人success

    //歌曲
    static GetSongsOfSongList = URL + '/songlist/one';//success

    //歌单管理
    static NewList = URL + '/newlist';//success
    static DeleteList = URL + '/deletelist';//success
    static ChangeList = URL + '/changelist';//success
    static RemoveSong = URL + '/removesong';//success
    static ChangeSongImage = URL + '/changesong';//success
    static AddSongFromNetEase = URL + '/addsong/netease';//网易云success
    static AddSongFromLike = URL + '/addsong/songlist';//deprecated
    static AddSongFromUpload = URL + '/addsong/upload';

    //社交
    static Info = URL + '/info';//自己success 他人success
    static Following = URL + '/follow';//success
    static Follow = URL + '/follow';//success  关注多次？？？

    static Moment = URL + '/moment';//success

    static GetComments = URL + '/comment';//success
    static AddComment = URL + '/addcomment';//success

    //推荐
    static RecommendUser = URL + '/recommendUser';
    static RecommendSong = URL + '/recommendSong';

    //音乐
    static SongInfo = URL + '/api/song';//success
    static SearchhSong = URL + '/api/search';//success

    static LocalSongInfo = URL + '/song';

    static ImageInfo = URL + '/img';//success


}

export default API;