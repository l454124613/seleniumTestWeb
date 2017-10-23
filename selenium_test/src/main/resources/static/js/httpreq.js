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
            timeout:60000,
            success:function(msg){
                callback(msg);
            },
            error:function(xhr){
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
            timeout:20000,
            success:function(d){
                callback(d);


            },
            error:function(xhr){
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
            timeout:20000,
            success:function(msg){
                callback(msg);
            },
            error:function(xhr){
            callback2(xhr);

            }
        });
    };