var header = new Vue({
   el: '#wrapper',
   methods: {
       logout: function () {
           console.log('logout method');
       },
       testDelete: function () {
           var deleteUrl = 'http://localhost:8081/adminUser/delete/397wfj2937r293r23r';
           var token = sessionStorage.token;
           var config = {
               headers: {
                   "api-version": "1.0",
                   "Authorization": token
               }
           };

           axios.delete(deleteUrl, {}, config)
               .then(function (res) {
                   console.log(res);
               })
               .catch(function (err) {
                   console.log(err);
               });

       }
   } 
});
