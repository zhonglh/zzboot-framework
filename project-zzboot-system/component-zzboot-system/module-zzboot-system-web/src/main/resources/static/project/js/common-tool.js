/*
    工具类Js , 封装Ajax方法 ,  提示（错误 成功 警告 通知）
    Jquery Ajax Setting
    依赖 common-constant.js
 */


function syncGet(url , data ,  successFun , failFun){
    ajaxSend(url , data , RequestMethod.GET ,  false, successFun , failFun);
}

function syncPost(url , data ,  successFun , failFun){
    ajaxSend(url , data , RequestMethod.POST ,  false, successFun , failFun);
}


function syncPut(url , data ,  successFun , failFun){
    ajaxSend(url , data , RequestMethod.PUT , false, successFun , failFun);
}

function syncDelete(url , data ,  successFun , failFun){
    ajaxSend(url , data , RequestMethod.DELETE , false, successFun , failFun);
}



function asyncGet(url , data ,  successFun , failFun){
    ajaxSend(url , data , RequestMethod.GET ,  true, successFun , failFun);
}

function asyncPost(url , data ,  successFun , failFun){
    ajaxSend(url , data , RequestMethod.POST ,  true, successFun , failFun);
}


function asyncPut(url , data ,  successFun , failFun){
    ajaxSend(url , data , RequestMethod.PUT , true, successFun , failFun);
}

function asyncDelete(url , data ,  successFun , failFun){
    ajaxSend(url , data , RequestMethod.DELETE , true, successFun , failFun);
}

function ajaxSend(url , data , type, async , successFun , failFun){
    successFun = successFun || successFunction;
    failFun = failFun || failFunction;
    type = type || "POST";
    $.ajax({url:url, data: data , timeout : 5000 , dataType:'json' ,type : type , async : async ,success:successFun , error : failFun});
}



function successFunction(res){

}

function failFunction(xhr,status,error){
    alert("请求服务出现错误");
}