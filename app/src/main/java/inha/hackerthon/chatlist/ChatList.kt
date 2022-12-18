package inha.hackerthon.chatlist

data class ChatList(
    val hostId: String,
    val guestId: String,
    val content: String,
    val key: Long
){
    constructor() : this("","","",0)
}
