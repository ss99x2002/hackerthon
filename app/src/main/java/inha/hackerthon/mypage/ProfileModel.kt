package inha.hackerthon.mypage

data class ProfileModel(
    val name: String,
    val userId: String,
    val school: String,
    val number: String
){
    constructor(): this("","","","")
}
