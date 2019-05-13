
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
    output.innerHTML ="";
    request({url: "/dumpTable?tablename="+askName.value, method: "GET"})
            .then(data => { 
                if (data.length !== null) {
                let res = JSON.parse(data);
                console.log(res);

                for (var i = 0; i < res.length; i++) {
                    for (var j = 0; j < res[0].length; j++) {
                        output.innerHTML += res[i][j] + " " + " | ";
                    }
                    output.innerHTML += "<br><hr>";
                }
            }
        })
        .catch(error => {
            output.innerHTML += "Table "+askName.value+ " does not exist";
        });
    
}


function test(){
    request({url: "/test?tablename="+askName.value, method: "GET"})
    
            .then(data => { 
                output.innerHTML = data;
                console.log("asked");
            })
            .catch(error => {
                output.innerHTML = "Connection Failed"
                print("Error: " + error);
            });
    
}


