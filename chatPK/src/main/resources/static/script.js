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


function login() {
    
    request({url: "login?name=" + name, method: "POST"})
            .then(data => { 
                print("hello " + name);
                setInterval(getNewMsgs, 100);
            })
            .catch(error => {
                print("Error: " + error);
            });
}
//sends message to server to put into array
function send() {
     var data = new FormData(); 
    
    
    data.append("message", message);

    request({url: "send", method: "POST", body: data}) //try to send it back - calls the sendMsg in ChatServer.java
            .then(data => {
                print(name + ": " + message + " (js)"); //will print if it works
            })
            .catch(error => { //say error if error
                print("Error: " + error);
            });
    

}


//retrieve all new messages
function getNewMsgs() {
    var data = new FormData();
    request({url: "getNewMessages", method: "GET", body: data})
            .then(data => {
                let messages =  JSON.parse(data);
          for (var i = 0; i < messages.length; i++) {
                        let s = messages[i];
			
                   
                        output(s + "<br>");
                    }
                })

                
           
            .catch(error => {
                print("Error: " + error);
            });
}
function print(s) {
    output.innerHTML += "<p>" + s + "</p>"; 
}










console.log("parsed");