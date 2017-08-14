/**
 * 删除用户
 * @param ids
 */
function deleteUsers(ids, callback) {
    $.ajax({
        url: admin_user_delete_URL + "/" + ids,
        type: 'DELETE',
        dataType: 'json',
        withCredentials:true,
        headers: {
            'content-type': 'application/json',
            Authorization: sessionStorage.token,
            'api-version': '1.0'
        },
        success: function () {
            callback();
        },
        error: function () {
            callback();
        }
    });
}

$(function () {
    ifox_table_delegate.delete = deleteUsers;
});