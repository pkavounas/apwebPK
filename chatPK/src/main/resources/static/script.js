console.log("parsing script");
// var inputfield = document.getElementById("message");
// var outputArea = document.getElementById("output")
var username = document.getElementById("name");
var output = document.getElementById("output");
var message = document.getElementById("myMessage");



// function to wrap request in a promise object 
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

//creates username prefix for messages (DOES NOT PROTECT OTHER FEATURES)
function login() {
    request({ url: "/login?name=" + username.value, method: "GET" })
        .then(data => {
            console.log(data);
            
            if (data == "true") {
                output.innerHTML="Hello, " + username.value;
                setInterval(getNewMsgs, 100);
                
            }
        })
        .catch(error => {
            console.log("error: " + error);
        });
}
//sends message to server to put into array
function send() {
    if (message.value !== undefined && message.value !== "") {
        request({ url: "/send?message=" + message.value, method: "PUT" })
            .then(data => {
                output.innerHTML= username.value + ": "+data;
                console.log(username.value + data);
        
                
            })
            .catch(error => {
                console.log("error: " + error);
            });
    }
}
//retrieve all new messages
function getNewMsgs() {
    var xhr = new XMLHttpRequest();
    xhr.open("get", "http://localhost:4567/getnewmessages", true);
    xhr.onload = () => {
        
        output.innerHTML += xhr.response;
        console.log(xhr.response);
    };
    xhr.onerror = () => {
        console.log("error: " + xhr.response);
    };
    xhr.send();
}










console.log("parsed");