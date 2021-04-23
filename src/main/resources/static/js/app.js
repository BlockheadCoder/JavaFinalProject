var stompClient = null;

function connect(){
    var socket = new SockJS('/messages-websocket');

    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame){
        console.log("Connected: "+frame);
        stompClient.subscribe('/socketOut/'+myId+'/'+recId, function (data){
            console.log(data);
            var body = JSON.parse(data.body)
            $("#messageHolder").append('<div id="messageLeft"><div class="sentMessage" id="theirMessage"><h3>'+body.message+'</h3></div></div>');
            
            var d = $("#messageHolder");
    		d.scrollTop(d.prop("scrollHeight"));

        });
    });
}

function disconnect(){
    if(stompClient !== null){
        stompClient.disconnect();
    }
}

function sendMessage() {

    message = $("#message").val();

    $("#messageHolder").append('<div id="messageRight"><div class="sentMessage" id="myMessage"><h3>'+message+'</h3></div></div>');
	
	var d = $("#messageHolder");
    d.scrollTop(d.prop("scrollHeight"));

    console.log("hello");
    let today = new Date();

    stompClient.send("/app/usermessages/", {}, JSON.stringify(
        {'message': message,
        'senderId': myId,
        'receiverId': recId,
        'seen': false,
        'created_at': today
        }
    ))
}
