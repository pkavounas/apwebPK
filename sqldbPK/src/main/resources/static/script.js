
var output = document.getElementById("output");
var askName = document.getElementById("tableName");






function request(obj) {
    return new Promise((resolve, reject) => {
        let xhr = new XMLHttpRequest();
        xhr.open(obj.method || "GET", obj.url);

        xhr.onload = () => {
            if (xhr.status >= 200 && xhr.status < 300) {
                resolve(xhr.response);
            } else {
                reject(xhr.statusText);
            }
        };
        xhr.onerror = () => reject(xhr.statusText);

        xhr.send(obj.body);
    });
};



function ask(){
    request({url: "/dumpTable?tablename="+newInput.value, method: "GET"})
            .then(data => { 
                output.innerHTML = data;
                console.log("asked");
            })
            .catch(error => {
                print("Error: " + error);
            });
    
}


function test(){
    request({url: "/test?tablename="+newInput.value, method: "GET"})
    
            .then(data => { 
                output.innerHTML = data;
                console.log("asked");
            })
            .catch(error => {
                print("Error: " + error);
            });
    
}


