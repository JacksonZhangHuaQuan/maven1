var countpage = 0;
$(function () {
    var firstpage = 1;
   changepage(firstpage);

   //下一页
    $(".next_page").click(function(){
        var num = $(".message").attr("id");
        var numpage = parseInt(num) + 1;
        console.log(numpage);
        if((numpage-1)*3<countpage){
            changepage(numpage);
        }
    });

    //上一页
    $(".prev_page").click(function () {
        var num = $(".message").attr("id");
        var numpage = parseInt(num);
        if(numpage > 1){
            numpage -= 1;
        }
        console.log(numpage);
        changepage(numpage);
    })

   // $(".my_btn").click(function(e){
   //     // e.preventDefault();
   //     var msg = $(this).prev().val();
   //     var name = $(".input_name").val();
   //     // alert(name);
   //     // console.log(name);
   //     $.ajax({
   //         url:"http://localhost:8080/InsertServlet",
   //         type:"get",
   //         data:{"name":name, "msg":msg},
   //         dataType:"text",
   //         success: function (result) {
   //             if(result=="1"){
   //                 console.log("success");
   //             }
   //         }
   //     })
   // });
    $(".my_form").validate({
        onsubmit:true,// 是否在提交是验证
        onfocusout:false,// 是否在获取焦点时验证
        onkeyup :false,// 是否在敲击键盘时验证

        rules: {　　　　//规则
            user: {　　//要对应相对应的input中的name属性
                required: true
            },
            input_text: {
                required: true
            }
        },
        messages:{　　　　//验证错误信息
            user: {
                required: "请输入用户名"
            },
            input_text: {
                required: "请输入信息"
            }
        },
        submitHandler: function(form) { //通过之后回调
//进行ajax传值
            $.ajax({
                url : "http://localhost:8080/InsertServlet",
                type : "post",
                dataType : "json",
                data: {
                    name: $(".input_name").val(),
                    msg: $(".input_info").val()
                },
                success : function(msg) {
                    //要执行的代码
                    alert()
                    $(".input_name").text("");
                    $(".input_info").text("");
                }
            });
        },
        invalidHandler: function(form, validator) {return false;}
    });

});

//换页
function changepage(numpage) {
    $.ajax({
        url:"http://localhost:8080/MessageServlet",
        type:"get",
        data:{"numpage":numpage},
        dataType:"json",
        success:function (result) {
            console.log(result);
            Date.prototype.format = function(format) {
                var o = {
                    "M+" : this.getMonth() + 1,// month
                    "d+" : this.getDate(),// day
                    "h+" : this.getHours(),// hour
                    "m+" : this.getMinutes(),// minute
                    "s+" : this.getSeconds(),// second
                    "q+" : Math.floor((this.getMonth() + 3) / 3),// quarter
                    "S" : this.getMilliseconds()
                    // millisecond
                };
                if (/(y+)/.test(format) || /(Y+)/.test(format)) {
                    format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
                }
                for ( var k in o) {
                    if (new RegExp("(" + k + ")").test(format)) {
                        format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
                    }
                }
                return format;
            };
            var $showdiv = $(".message").eq(0).detach();
            $(".message").remove();
            for(var i = 0; i < result.length-1; i++){
                console.log(i);
                var $clonediv = $showdiv.clone();
                $clonediv.attr("id", numpage);
                var uid = result[i].id;
                var name = result[i].name;
                var msg = result[i].msg;
                var time1 = result[i].createTime;
                var time2 = (new Date(parseFloat(time1.time))).format("yyyy-MM-dd hh:mm:ss");
                $clonediv.children().eq(1).text(msg);
                $clonediv.children().eq(0).children().eq(0).text(name);
                $clonediv.children().eq(0).children().eq(1).text(uid);
                $clonediv.children().eq(2).children().eq(0).text(time2);
                $(".change_page").before($clonediv);
            }
            countpage = parseInt(result[result.length-1])
            console.log(countpage);
        }
    });
}