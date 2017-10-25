/**
 * Created by lixuecheng on 2017/10/23.
 */
/**
 * 获取数据ajax-get请求
 *
 */
$.GetJSON = function (url,data,callback,callback2){
    $.ajax({
        url:url,
        type:"get",
        contentType:"application/json",
        dataType:"json",
        timeout:30000,
        data:data,
        success:function(d){
            callback(d);
        },
        error:function(xhr){
            console.clear();
            callback2(xhr);

        }
    });
};

/**
 * 提交json数据的post请求
 *
 */
$.postJSON = function(url,data,callback,callback2){
    $.ajax({
        url:url,
        type:"post",
        contentType:"application/json",
        dataType:"json",
        data:data,
        timeout:30000,
        success:function(msg){
            callback(msg);
        },
        error:function(xhr){
            console.clear();
            callback2(xhr);
        }
    });
};

/**
 * 修改数据的ajax-put请求
 *
 */
$.putJSON = function(url,data,callback,callback2){

    $.ajax({
        url:url,
        type:"put",
        contentType:"application/json",
        dataType:"json",
        data:data,
        timeout:30000,
        success:function(d){
            callback(d);
        },
        error:function(xhr){
            console.clear();
            callback2(xhr);
        }
    });
};
/**
 * 删除数据的ajax-delete请求
 *
 */
$.deleteJSON = function(url,data,callback,callback2){
    $.ajax({
        url:url,
        type:"delete",
        contentType:"application/json",
        dataType:"json",
        data:data,
        timeout:30000,
        success:function(msg){
            callback(msg);
        },
        error:function(xhr){
            console.clear();
            callback2(xhr);

        }
    });
};
/**
 *把对象转成json
 */
j2s=function(a){
    return JSON.stringify(a);
};

function compileStr(code){ //对字符串进行加密
    var c="";
    var a2=code.charCodeAt(0)+code.length;
    if(a2%2==1){
        c+='蠷';
       c+=String.fromCharCode((a2+1)/2);

    }else {
        c+='硿';
        c+=String.fromCharCode(a2/2);

    }

    for(var i=1;i<code.length;i++)

    {

        var aa=code.charCodeAt(i)+code.charCodeAt(i-1);
        if(aa%2==1){
            c+='蠷';
            c+=String.fromCharCode((aa+1)/2);

        }else {
            c+='硿';
            c+=String.fromCharCode(aa/2);
        }

    }
    return encodeURI(c);   }

//字符串进行解密
function uncompileStr(code){
    code=decodeURI(code);
    var c='';
    var n1='';
    if(code.charCodeAt(0)==34871){
     n1=  code.charCodeAt(1)*2-1-code.length/2
    }else {
        n1=  code.charCodeAt(1)*2-code.length/2
    }
    c+=String.fromCharCode(n1);




    for(var i=3,j=2;i<code.length;i+=2,j+=2)
    {

        if(code.charCodeAt(j)==34871){
            n1=  code.charCodeAt(i)*2-1-n1;

        }else {
            n1=  code.charCodeAt(i)*2-n1;

        }
        c+=String.fromCharCode(n1);


    }

    return c;   }

    function s2j(a) {

       return  JSON.parse(a);
    }