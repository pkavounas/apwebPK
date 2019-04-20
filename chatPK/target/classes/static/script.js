console.log("parsing script");
// var inputfield = document.getElementById("message");
// var outputArea = document.getElementById("output")
var username = "";



// function to wrap request in a promise object - 
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

function output(message){
    document.getElementById("output").innerHTML += message += "\n";
}

output("Server Ready:");
output("");

function message(){
    var t = document.getElementById("myMessage").value;
    document.getElementById("myMessage").value = "";
    console.log("message test?");

    request({url: "protected/putmessage", method: "PUT", body: t})
        .then(data =>{session = data;})
        .catch(error =>{output("Error: " + error);});
}

function login(){
    let startup = document.getElementById("login").value;
    document.getElementById("myMessage").value = "";
    request({url: "login", method: "put", body: startup})
        .then(data => {
            username = startup;
            output("Welcome, " + data);
        })
        .catch(error => {
            output("Error: " + error);
        });
}

function shutdown(){
    asyncReq({url: "shutdown"}, null);
    
}


function asyncReq(obj, callback){
    let xhr = new XMLHttpRequest();
    xhr.open(obj.method || "GET", obj.url, true);
    xhr.onload = () => {
        callback(xhr);
    };
    xhr.send(obj.body);
}


setInterval(getNewMessages, 100);

function getNewMessages(n) {
    if(username !== ""){
        request({url: "protected/getnewmessages"})
            .then(data =>{
                if (data !== "[]")
                output(data);
                return;
            })
            .catct(error =>{
                output("Error: " + error);
            });





    }

}






console.log("parsed");

