package inha.hackerthon.chatlist

data class ChatList(
    val hostId: Long,
    val guestId: Long,
    val content: String,
    val key: Long
){
    constructor() : this(0,0,"",0)
}
