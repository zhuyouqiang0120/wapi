var SJ_UP_KEY = 1, 
    SJ_DOWN_KEY = 2, 
    SJ_LEFT_KEY = 3, 
    SJ_RIGHT_KEY = 4, 
    SJ_SELECT_KEY = 13, 
    SJ_BACK_KEY = 340, 
    SJ_EXIT_KEY = 339, 
    SJ_VOLUME_UP_KEY = 595, 
    SJ_VOLUME_DOWN_KEY = 596, 
    SJ_CHANNEL_UP_KEY = 593, 
    SJ_CHANNEL_DOWN_KEY = 594,
    SJ_1_KEY = 49,
    SJ_2_KEY = 50,
    SJ_3_KEY = 51,
    SJ_4_KEY = 52,
    SJ_5_KEY = 53,
    SJ_6_KEY = 54,
    SJ_7_KEY = 55,
    SJ_8_KEY = 56,
    SJ_9_KEY = 57,
    SJ_0_KEY = 48,
    SJ_TVOS_SELECT = 4097,
    SJ_TVOS_BACK = 4096;

var SG_UP_KEY = 87, 
    SG_DOWN_KEY = 83, 
    SG_LEFT_KEY = 65, 
    SG_RIGHT_KEY = 68, 
    SG_SELECT_KEY = 13, 
    SG_BACK_KEY = 8, 
    SG_EXIT_KEY = 27, 
    SG_VOLUME_UP_KEY = 61, 
    SG_VOLUME_DOWN_KEY = 45, 
    SG_PAGE_UP_KEY = 306, //上页
    SG_PAGE_DOWN_KEY = 307, //下页
    SG_MEDIA_FAST_KEY = 46,
    SG_MEDIA_SLOW_KEY = 44,
    SG_STOP_KEY = 59,
    SG_START_KEY = 39,
    SG_1_KEY = 49,
    SG_2_KEY = 50,
    SG_3_KEY = 51,
    SG_4_KEY = 52,
    SG_5_KEY = 53,
    SG_6_KEY = 54,
    SG_7_KEY = 55,
    SG_8_KEY = 56,
    SG_9_KEY = 57,
    SG_0_KEY = 48;

;(function(window) {
    var lynx = {
        $ : function ( id ){
            return document.getElementById( id );
        },
        _name_ : function( name ){
            return document.getElementsByName( name );
        },
        _tag_ : function( tag ){
            return document.getElementsByTagName( tag );
        },
        //消耗性能,不支持使用
        _class_ : function( cls ){
            var tags = document.getElementsByTagName( '*' ),
                _cls = [];
            //[object HTMLCollection] for in 会出错, 会得到双份数据, 数组里面用
            for( var i = 0; i < tags.length; i++){
                //getAttrubute只能通过节点调用，不属于document对象
                if( tags[i].className == cls ){
                    _cls[ _cls.length ] = tags[i];  
                }
            }
            return _cls;
        },
        css : function( _target, _attr ){
            var t = document.getElementById( _target );
            if( t && _attr ){
                if( typeof _attr === 'object'){
                    for(var i in _attr){
                        if( i === 'src' ){
                            t[ i ] = _attr[ i ];
                        }else{
                            t.style[ i ] = _attr[ i ];
                        }
                    }
                }else if( typeof _attr === 'string' ){
                    return t.style[ _attr ] || t[ _attr ];
                }
            }
        },
        /* 兼容茁壮style.top返回为数值的情况 */
        expandFormat : function( val ){
            val = val || 0;

            if( typeof val === 'string' && val.indexOf('px') > -1 ){
                return 'px';
            }else{
                return '';
            }
        },
        getUrlParam: function( param ){
            param = param || window.location.search;
            if( !param ) return null;
            var o = {}, param = param.substr(1).split('&');
            for( var i = 0; i < param.length; i ++ ){
                var it = param[i].split( '=' );
                o[it[0]] = it[1];
            }
            return o;
        },
        cookie : {
            setValue: function( K, V ){
                var Days = 30;
                var exp  = new Date();
                exp.setTime( exp.getTime() + Days*24*60*60*1000 );
                document.cookie = K + "=" + escape( V ) + ";expires=" + exp.toGMTString();
            },
            getValue: function( K ){
                var arr = document.cookie.match( new RegExp("(^| )" + K + "=([^;]*)(;|$)" ));
                if( arr != null ) return unescape( arr[2]) ;
                return null;
            },
            deleteValue: function( K ){
                var exp = new Date();
                exp.setTime( exp.getTime() - 1 );
                var cval = this.getValue( K );
                if( cval != null ) document.cookie= K + "=" + cval + ";expires=" + exp.toGMTString();
            }
        },
        ajax : function() {
            var arg = arguments[0];
            var xhr = null;

            if (arg && typeof(arg) === 'object') {
                // 创建 XMLHttpRequest 对象
                try {
                    xhr = new XMLHttpRequest();
                } catch (trymicrosoft) {
                    try {
                        xhr = new ActiveXObject("Msxml2.XMLHTTP");
                    } catch (othermicrosoft) {
                        try {
                            xhr = new ActiveXObject("Microsoft.XMLHTTP");
                        } catch (failed) {
                            xhr = null;
                        }
                    }
                }

                // 必要验证
                if (xhr === null) return -1;
                if (typeof(arg.url) != 'string' || arg.url == '') return -1;

                arg.type = arg.type.toUpperCase() == "POST" ? "POST" : "GET";

                arg.async = Boolean(arg.async) === false ? false : true;

                if (typeof(arg.complete) != 'function') arg.complete = function() {};
                if (typeof(arg.success) != 'function') arg.success = function() {};
                if (typeof(arg.error) != 'function') arg.error = function() {};

                // jsonToQueryString
                if (typeof(arg.data) === 'object') {
                    var tmp_data = '';

                    for (var v_name in arg.data) {
                        tmp_data += v_name + '=' + arg.data[v_name] + '&';
                    }
                    arg.data = tmp_data.replace(/\&$/, '');
                }

                // 打开 XMLHttpRequest 对象
                //xhr.open(arg.type, arg.url, arg.async);
                xhr.open(arg.type, arg.url, true);
                
                if (arg.type == 'POST') {
                    // POST 提交数据的时候必须设置文件头，否则会造成403权限错误
                    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                }

                xhr.onreadystatechange = function() {

                    if (xhr.readyState == 4) {
                        arg.complete.call(xhr, xhr.static, xhr.statusText);

                        switch (xhr.status) {
                            case 400:
                            case 403:
                            case 404:
                            case 500:
                            case 501:
                            case 502:
                            case 503:
                            case 504:
                                arg.error.call(xhr, xhr.static, xhr.statusText);
                                break;
                            case 0:
                            case 200:
                                arg.success.call(xhr, xhr.responseText);
                                break;
                        }
                    }
                }
                // Complate ajax
                xhr.send(arg.data);
            }
        }
    };

    function __get_uuid__ () {
        var param = arguments[0] || {}, 
        // ints = ~~param.ints || 36;
            ints = param.ints || 36;
        var uuid = (new Date).getTime().toString( ints ) + Math.floor( 100E10 * Math.random() ).toString( ints );

        return uuid[ Boolean( param.str ) ? 'toLowerCase' : 'toUpperCase' ]();
    }

    /* -------------------------------------- */

    ;(function( window ) {
        var _STACK_SHEET_ = {};

        /* Stack of class */
        function Stack () {
            this.uuid = __get_uuid__({ ints: 36, str: 1 });

            _STACK_SHEET_[ this.uuid ] = this;
        }
        Stack.prototype = {
            
            member: new Array(),
            hashSheet: new Object(),

            item: function ( i ) { return this.member[ i ] || -1},

            get: function ( uuid ) { return this.hashSheet[uuid]; },

            add : function ( m ) {
                this.hashSheet[ m.uuid ] = m;
                this.member.push( m );
            },
            remove: function ( m ) {
                var t;

                if ( m === undefined ) {
                    m = this.last();
                    if ( m === -1 ) return this;
                }
                else if ( typeof m === 'string' ) {
                    m = this.hashSheet[ m ];
                }

                t = m.hasOwnProperty ? m.hasOwnProperty( 'uuid' ) : m.uuid;

                if ( t && m.resident === false ) {
                    this.member.pop( m );
                    delete this.hashSheet[ m.uuid ];
                }

                return this;
            },
            clear: function () {
                delete _STACK_SHEET_[ this.uuid ];
            },

            last: function () {
                return this.member[ this.member.length - 1 ] || -1;
            }
        }

        /* Window namespace */
        lynx.Stack = function create_stack() {
            return new Stack();
        };

    })( window );

    /* -------------------------------------- */

    ;(function(window) {

        var _stackCache_ = new lynx.Stack();
        var NOOP = function () {};

        function trimClassList ( classList ) {

            return classList.replace(/(^\s*)|(\s*$)/ig, '').replace(/(,|\s|\|)*(,|\s|\|)(,|\s|\|)*/ig, ' ');
        }

        function isFunction ( obj ) {

            return typeof obj === 'function';
        }

        function extend( target, source, limit ) {
            for (var s in source ) {
                // if ( limit === true ) {
                //     if ( target[ s ] === undefined || ( typeof target[ s ] === typeof source[ s ] ) ) {
                //         target[ s ] = source[ s ];
                //     }
                // } else {
                    target[ s ] = source[ s ];
                // }
            }
            return target;
        }

        function addClass( classList, target ) {
            var list, l, orgiCls;

            target = target || this;
            orgiCls = ' ' + target.className + ' ';

            list = trimClassList( classList ).split(' ');

            for ( l in list ) {
                if ( orgiCls.indexOf( ' ' + list[l] + ' ' ) == -1 ) {
                    orgiCls += ' ' + list[l];
                }
            }

            target.className = trimClassList(orgiCls);
        }

        function removeClass ( classList, target ) {
            var list, l, orgiCls, cls;

            target = target || this;
            orgiCls = ' ' + target.className + ' ';

            list = trimClassList( classList ).split(' ');

            for ( l in list ) {
                cls = ' ' + list[l] + ' ';

                if ( orgiCls.indexOf( cls ) != -1 ) {
                    orgiCls = orgiCls.replace( cls, '' );
                }
            }
            target.className = trimClassList(orgiCls);
        }

        /* Focus of class */
        function zFocus ( conf ) {
            // There is no strict judgment, so, the conf must be JSON of type.
            var index = 0, keyBoard_m;
            var orgConfigs = { init: NOOP, targets: [], keyBoard: {}, resident: false, focusClass: "zFocus" };

            conf = extend( orgConfigs, conf, true );

            /* Create the focus Stack, and get flag id. */
            this.uuid = __get_uuid__();

            /* NodeList of focus, type is array-like. */
            this.targetElements = conf.targets || [];
            /* Set resident in stack. */
            if( this.fClass !== conf.focusClass ) {
                this.fClass = conf.focusClass;
            }

            /* Set resident in stack. */
            if( this.resident !== conf.resident ) {
                this.resident = conf.resident;
            }

            /* */
            this.keyBoard = function (keyName) {
                var fn = arguments.callee[ keyName ];
                return isFunction( fn ) && fn.call(this);
            }

            extend( this.keyBoard, conf.keyBoard);

            this.getIndex = function(){ return index; }

            this.getTarget = function(){ return this.targetElements[ index ]; }

            //add by hy, used as reset
            this.getTargetsLen = function(){ return this.targetElements.length; };
            this.setTargets = function( _targets, _index ) { this.targetElements = _targets; index = _index || 0; };

            this.moveFocus =  function ( v ) {
                var _index = index;
                var maxEdges = this.targetElements.length;
                switch( v ) {
                    case '+': _index++; break;
                    case '-': _index--; break;
                    default:  _index = parseInt(v);
                }
                this.removeFocus();

                if( _index < 0){
                    _index = 0;
                }else if( _index > maxEdges - 1 ){
                    _index = maxEdges - 1;
                }

                index = _index;
                this.addFocus();
                return this;
            }
            _stackCache_.add( this );
            this.addFocus();
            isFunction( conf.init ) && conf.init.call(this);
        }

        zFocus.prototype = {
            fClass: "zFocus",
            keyBoards: {},
            addFocus: function ( tar ) {
                ( tar = this.getTarget() ) && addClass( this.fClass, tar );
                return this;
            },
            removeFocus: function ( tar ) {
                ( tar = this.getTarget() ) && removeClass( this.fClass, tar );
                return this;
            },
            next: function () {
                return this.moveFocus("+");
            },
            prev: function () {
                return this.moveFocus("-");
            },
            keyBoard: NOOP,
            resident: false,
            stack : _stackCache_.uuid
        }

        lynx.backspace = function () {
            return _stackCache_.remove().last();
        }

        lynx.get_stackCache = function () {
            return _stackCache_;
        }

        lynx.onAction = function (e) {
            var cFocus = _stackCache_.last();
            var key = e.which || e.keyCode;
            var ret = false;

            if ( cFocus == -1 ) return;
            switch( key ) {
                case 13:
                case SJ_SELECT_KEY:
                case SG_SELECT_KEY:
                case SJ_TVOS_SELECT:
                    cFocus.keyBoard('confirm');
                    break;
                case SJ_BACK_KEY:
                case SG_BACK_KEY:
                case SJ_TVOS_BACK:
                case 8:
                    ret = cFocus.keyBoard('backspace');
                    if ( ret !== false && lynx.backspace() == -1 ) {
                        break;
                    } else {
                        e.preventDefault()
                        return false;                        
                    }
                    break;
                case SJ_LEFT_KEY:
                case SG_LEFT_KEY:
                case 37:
                    cFocus.keyBoard('left');
                    break;
                case SJ_UP_KEY:
                case SG_UP_KEY:
                case 38:
                    cFocus.keyBoard('up');
                    break;
                case SJ_RIGHT_KEY:
                case SG_RIGHT_KEY:
                case 39:
                    cFocus.keyBoard("right");
                    break;
                case SJ_DOWN_KEY:
                case SG_DOWN_KEY:
                case 40:
                    cFocus.keyBoard('down');
                    break;
                case SG_1_KEY:
                case SJ_1_KEY:
                    cFocus.keyBoard('number_1');
                    break;
                case SG_2_KEY:
                case SJ_2_KEY:
                    cFocus.keyBoard('number_2');
                    break;
                case SG_3_KEY:
                case SJ_3_KEY:
                    cFocus.keyBoard('number_3');
                    break;
                case SG_4_KEY:
                case SJ_4_KEY:
                    cFocus.keyBoard('number_4');
                    break;
                case SG_5_KEY:
                case SJ_5_KEY:
                    cFocus.keyBoard('number_5');
                    break;
                case SG_6_KEY:
                case SJ_6_KEY:
                    cFocus.keyBoard('number_6');
                    break;
                case SG_7_KEY:
                case SJ_7_KEY:
                    cFocus.keyBoard('number_7');
                    break;
                case SG_8_KEY:
                case SJ_8_KEY:
                    cFocus.keyBoard('number_8');
                    break;
                case SG_9_KEY:
                case SJ_9_KEY:
                    cFocus.keyBoard('number_9');
                    break;
                case SG_0_KEY:
                case SJ_0_KEY:
                    cFocus.keyBoard('number_0');
                    break;
            }
        }

        document.onkeydown = lynx.onAction;

        /* Window namespace */
         lynx.zFocus = function ( opt ) {
            return new zFocus( opt );
        }
    })( window );
    window.lynx = lynx;
})( window );
