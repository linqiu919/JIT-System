<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>工资明细表</title>
    <style>
        table{
            width: 600px;
            margin: 350px auto;
            border-collapse: collapse;
        }
        tr,td{
            height: 40px;
            text-align: center;
            border: 1px solid #000;
        }
    </style>
</head>
<body>

<table>
    <tr>
        <td>员工账号</td>
        <td>员工姓名</td>
        <td>员工薪资</td>
        <td>员工电话</td>
        <td>实发薪资</td>
    </tr>
    <tr>
        <td>${adminAccount}</td>
        <td>${adminName}</td>
        <td>${adminSalary}</td>
        <td>${adminPhone}</td>
        <td>${adminSalary}</td>
    </tr>
</table>
</body>
</html>