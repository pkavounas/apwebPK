console.log("parsing script");
var inputfield = document.getElementById("message");
var outputArea = document.getElementById("outputAera")
var username = document.getElementById("username")



function xmlrequest(verb, url){
        var xhr = new XMLHttpRequest();
    xhr.open(verb || "GET", url, true);
    xhr.onload = () => {
        print("xhr.response");
    }
    xhr.onerror = () =>{
        print("error:"+xhr.statusText);
    }
    xhr.send();
 
}





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

function send() {
    request({ url: "send?message=" + message.value, method: "GET" })
        .then(data => {
            outputArea.innerHTML += "<b>" + username.value + ":</b> " + data + " <hr>";
        })
        .catch(error => {
            console.log("error: " + error);
        });
}


function getNewMessages(n) {
        request({url: "get?message="+n, method: "GET"})
                .then(data => {
                    print("Message:"+data);
                })
                .catch(error => {
                    print("Error: " + error);
                });
    }






console.log("parsed");

