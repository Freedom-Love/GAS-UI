// 请求加载数据，渲染 table
function load(table) {
    table.render({
        elem: '#currentTableId',
        url: '/student',
        toolbar: '#toolbarDemo',
        defaultToolbar: ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        cols: [[
            {type: "checkbox", width: 50},
            {field: 'id', width: 80, title: 'ID', sort: true, align: "center"},
            {field: 'studentNum', width: 80, title: '学号', align: "center"},
            {field: 'name', width: 80, title: '姓名', align: "center"},
            {field: 'sex', width: 80, title: '性别', align: "center"},
            {field: 'enrollmentTime', width: 110, title: '入学日期', sort: true, align: "center"},
            {field: 'phone', minWidth: 80 , title: '手机', align: "center"},
            {field: 'degree', width: 80 , title: '学位', align: "center"},
            {field: 'type', width: 80 , title: '状态', align: "center"},
            {field: 'employment', width: 80 , title: '去向', align: "center"},
            {field: 'email', minWidth: 120 , title: '邮箱', align: "center"},
            {title: '操作', minWidth: 100, toolbar: '#currentTableBar', align: "center"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });
}
// 请求加载专利数据，渲染 table，根据是否要搜索及搜索条件，映射到不同url
function loadpatent(table,name,keyword) {
    var strurl='/patent';
    if(name==""&&keyword==""){

    }else if(name==""){
      strurl='/patent/keyword';
    }else if(keyword==""){
      strurl='/patent/name';
    } else{
      strurl='/patent/condition';
    }
    table.render({
        elem: '#currentTableId',
        url: strurl,
        where:{
               'name':name,
               'keyword':keyword,
              },
        toolbar: '#toolbarDemo',
        defaultToolbar: ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        cols: [[
            {type: "checkbox", width: 50},
            {field: 'id', width: 80, title: 'ID', sort: true, align: "center"},
            {field: 'patname', width: 200, title: '专利名称', align: "center"},
            {field: 'name', width: 100, title: '第一作者', align: "center"},
            {field: 'secondname', width: 100, title: '其他作者', align: "center"},
            {field: 'enrollmentTime', width: 110, title: '时间', sort: true, align: "center"},
            {field: 'type', width: 90, title: '类型'},
            {field: 'summary', width: 80, title: '摘要'},
            {title: '操作', minWidth: 200, toolbar: '#currentTableBar', align: "center"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });
}
function loadresource(table,name) {
    var strurl='/resource';
    if(name!=""){
        strurl='/resource/vmname';
    }
    table.render({
        elem: '#currentTableId',
        url: strurl,
        where:{
               'name':name,
              },
        toolbar: '#toolbarDemo',
        defaultToolbar: ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        cols: [[
            {type: "checkbox", width: 50},
            {field: 'id', width: 80, title: 'ID', sort: true, align: "center"},
            {field: 'vmname', width: 100, title: '名称', align: "center", templet: '#details'},
            {field: 'count', width: 80, title: '台数', align: "center"},
            {field: 'mirror', width: 100, title: '镜像', sort: true, align: "center"},
            {field: 'ram', width: 90, title: '内存', sort: true, align: "center"},
            {field: 'mips', width: 160, title: '指令处理速度', sort: true, align: "center"},
            {field: 'bw', width: 100, title: '带宽', sort: true, align: "center"},
            {field: 'cpu', width: 60, title: '核数', sort: true, align: "center"},
            {field: 'enrollmentTime', width: 130, title: '创建时间', sort: true, align: "center"},
            {title: '操作', minWidth: 200, toolbar: '#currentTableBar', align: "center"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });
}


