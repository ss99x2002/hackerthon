package inha.hackerthon.data

data class ArticleResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: ArticleData?
)

data class ArticleData(
    val content: String,
    val lectureName: String,
    val postIdx: Int,
    val title: String,
    val userGrade: String,
    val userIdx: Int,
    val userName: String
)