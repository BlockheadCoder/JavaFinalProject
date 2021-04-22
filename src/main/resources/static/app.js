

var stompClient = null;




function connect(){
    var socket = new SockJS('/messages-websocket');

    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame){
        console.log("Connected: "+frame);
        stompClient.subscribe('/socketOut/'+myId, function (data){
            console.log(data);
            var body = JSON.parse(data.body)
            $(".messageHolder").append('<div><h3>'+body.message+'</h3></div>');

        });
    });
}

function disconnect(){
    if(stompClient !== null){
        stompClient.disconnect();
    }
}


function sendMessage() {

    userId = $("#userId").val();
    message = $("#message").val();

    $(".messageHolder").append('<div class="myMessage"><h3>'+message+'</h3></div>');

    console.log("hello");

    stompClient.send("/app/usermessages/", {}, JSON.stringify(
        {'message': message,
        'senderId': myId,
        'receiverId': recId}
        ))
}
