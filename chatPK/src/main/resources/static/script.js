console.log("parsing script");
var outputarea = document.getElementById("output");
var inputfield = document.getElementById("num");

function print(s){
    outputarea.value += s +"\n";
}
function test(){
factorial(inputfield.value);
}

function stressTest(){
    var i = 0;
    while (i++<100){
        factorial(i);
    }
}
function factorial(n){
        var xhr = new XMLHttpRequest();
    xhr.open("get", "http://localhost:4567/factorial?number="+n, false);
    xhr.onload = () => {
        print("factorial("+n+") is " + xhr.response);
    }
    xhr.onerror = () =>{
        print("error:"+xhr.statusText);
    }
    xhr.send();
 
}




console.log("parsed");

