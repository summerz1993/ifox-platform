/**
 * 删除用户
 * @param ids
 */
function deleteRoles(ids, callback) {
    $.ajax({
        url: role_delete_URL + "/" + ids,
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
    ifox_table.delete = deleteRoles;
});