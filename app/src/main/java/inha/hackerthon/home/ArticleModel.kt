package inha.hackerthon.home

data class ArticleModel(
    val hostId: String,
    val who: String,
    val subject: String,
    val title: String,
    val content: String,
    val createdAt: Long,
) {
    constructor():this("","","","","", 0)
}
//data class ArticleModel(
//    val sellerId: String,
//    val title: String,
//    val createdAt: Long,
//    val price: String,
//) {
//    constructor():this("","",0,"")
//}