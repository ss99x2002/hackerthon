package inha.hackerthon.home

data class ArticleModel(
    val userId: Long,
    val who: String,
    val subject: String,
    val title: String,
    val content: String,
    val createdAt: Long,
) {
    constructor():this(0,"","","","", 0)
}