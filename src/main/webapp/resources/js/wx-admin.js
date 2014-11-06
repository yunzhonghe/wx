/**
 * Created with IntelliJ IDEA.
 * User: liuxi
 * Date: 11/4/14
 * Time: 2:15 PM
 * To change this template use File | Settings | File Templates.
 */
var WxAdmin = function () {
    return {
        adminTableInit: function (adminList, accountList) {
            var dataSet = [];

            $('#adminEdit').hide();
            $.each(adminList, function(n, item){
                var operation = '<a class="edit" data-line="' + n + '" href="javascript:void(0);"><i class="glyphicon glyphicon-pencil"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="delete" data-line="' + n + '" href="javascript:void(0);"><i class="glyphicon glyphicon-trash"></i></a>';

                var temp = item.attrs;
                temp.OPERATION = operation;
                dataSet.push(item.attrs);
            });

            var col = [
                {sTitle: "登录名", mData: "ADMIN_ID"},
                {sTitle: "姓名", mData: "NAME"},
                {sTitle: "邮箱", mData: "EMAIL"},
                {sTitle: "电话", mData: "TELEPHONE"},
                {sTitle: "关联微信账户", mData: "WX_ACCOUNT_NAME"},
                {sTitle: "操作", mData: "OPERATION"}
            ]

            $('#adminTable').dataTable({
                "data": dataSet,
                "columns": col,
                bJQueryUI: true,
                bLengthChange: false
            });


            initTableEvent();

        },

        adminAddInit: function(accountList) {

            console.log(accountList);
            $.each(accountList, function(n, item){
                var temp = item.attrs;
                var option = '<option value="' + temp.ID + '">' + temp.NAME + '</option>';

                $("#wx_account").append(option);
            });

            initAdminAddEvent();
        }
    }

    function initTableEvent() {
        $("#adminTable tr a.edit").click(function(e){
            var tar = e.currentTarget;
            var line = $(tar).data("line");
            var data = $('#adminTable').dataTable().fnGetData(line);
            editAdmin(line, data);
        });

        $("#adminTable tr a.delete").click(function(e){
            var tar = e.currentTarget;
            var line = $(tar).data("line");
            var data = $('#adminTable').dataTable().fnGetData(line);
            deleteAdmin(line, data);
        });

        $("#adminEdit #submit").click(function(){
            var id = $("#id").val();
            var adminId = $("#adminId").val();
            var password = $("#password").val();
            var name = $("#name").val();
            var telephone = $("#telephone").val();
            var email = $("#email").val();
            var wxAccountId = $("#wx_account option:selected").val();
            var wxAccountName = $("#wx_account option:selected").text();

            if(id === "") {
                alert("数据来源不正确， 请点击修改进入该模块");
                return;
            }

            if(adminId === "" || password === "" || name === "" || telephone === "" || email === "") {
                alert("请填写完整");
                return;
            }

            var reqStr = "wxAdmin.id=" + id + "&wxAdmin.admin_id=" + adminId + "&wxAdmin.password=" + password + "&wxAdmin.name=" + name + "&wxAdmin.telephone=" + telephone
                + "&wxAdmin.email=" + email + "&wxAdmin.wx_account_id=" + wxAccountId + "&wxAdmin.wx_account_name=" + wxAccountName;

            $.post("/wx-admin/update", reqStr, function() {
                location.href = './list';
            });
        })

        $("#cancel").click(function(){
            $("#adminId").val("");
            $("#password").val("");
            $("#name").val("");
            $("#telephone").val("");
            $("#email").val("");

            $('#adminEdit').hide();
            $('#adminTableDiv').show();

        })
    };

    function initAdminAddEvent() {
        $("#submit").click(function(){
            var adminId = $("#adminId").val();
            var password = $("#password").val();
            var password2 = $("#password2").val();
            var name = $("#name").val();
            var telephone = $("#telephone").val();
            var email = $("#email").val();
            var wxAccountId = $("#wx_account option:selected").val();
            var wxAccountName = $("#wx_account option:selected").text();

            if(adminId === "" || password === "" || password2 === "" || name === "" || telephone === "" || email === "") {
                alert("请填写完整");
                return;
            }

            if(password !== password2) {
                alert("两次输入的密码不一致");
                return;
            }

            var reqStr = "wxAdmin.admin_id=" + adminId + "&wxAdmin.password=" + password + "&wxAdmin.name=" + name + "&wxAdmin.telephone=" + telephone
                + "&wxAdmin.email=" + email + "&wxAdmin.wx_account_id=" + wxAccountId + "&wxAdmin.wx_account_name=" + wxAccountName;

            $.post("/wx-admin/add", reqStr, function() {
                location.href = './list';
            });
        })
    }

    function editAdmin(row, data) {
        console.log(data);
        $('#adminTableDiv').hide();
        $("#id").val(data.ID);
        $("#adminId").val(data.ADMIN_ID);
        $("#password").val(data.PASSWORD);
        $("#password2").val(data.PASSWORD);
        $("#name").val(data.NAME);
        $("#telephone").val(data.TELEPHONE);
        $("#email").val(data.EMAIL);
        $("#wx_account").val(data.WX_ACCOUNT_ID);
        $('#adminEdit').show();

    };

    function deleteAdmin(row, data) {
        console.log(data);

        $.post("/wx-admin/delete", "id=" + data.ID, function() {
            location.href = './list';
            $('#adminTable').dataTable().fnDeleteRow(row);
        });
    };
}();
