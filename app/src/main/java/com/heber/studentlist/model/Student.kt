package com.heber.studentlist.model

data class Student(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String
)

val students = listOf(
    Student(1, "John Doe", "A diligent student", "https://picsum.photos/200/200?random=1"),
    Student(2, "Jane Smith", "An excellent coder", "https://picsum.photos/200/200?random=2"),
    Student(3, "Alice Johnson", "Loves mathematics", "https://picsum.photos/200/200?random=3"),
    Student(4, "Bob Brown", "Enjoys physics", "https://picsum.photos/200/200?random=4"),
    Student(5, "Charlie Davis", "A creative thinker", "https://picsum.photos/200/200?random=5"),
    Student(6, "Diana Evans", "Passionate about art", "https://picsum.photos/200/200?random=6"),
    Student(7, "Ethan Foster", "A sports enthusiast", "https://picsum.photos/200/200?random=7"),
    Student(8, "Fiona Green", "Loves programming", "https://picsum.photos/200/200?random=8"),
    Student(9, "George Harris", "Enjoys history", "https://picsum.photos/200/200?random=9"),
    Student(10, "Hannah White", "A science lover", "https://picsum.photos/200/200?random=10"),
    Student(11, "Ian King", "A music enthusiast", "https://picsum.photos/200/200?random=11"),
    Student(12, "Julia Lee", "Enjoys literature", "https://picsum.photos/200/200?random=12"),
    Student(13, "Kevin Moore", "A tech geek", "https://picsum.photos/200/200?random=13"),
    Student(14, "Laura Scott", "Loves biology", "https://picsum.photos/200/200?random=14"),
    Student(15, "Michael Taylor", "A natural leader", "https://picsum.photos/200/200?random=15")
)
