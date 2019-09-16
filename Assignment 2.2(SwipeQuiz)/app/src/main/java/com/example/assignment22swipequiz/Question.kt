package com.example.assignment22swipequiz

data class Question(val question: String, val isTrue: Boolean) {
    companion object Question {
        val QUESTIONS = listOf(
            Question("'Val' and 'Var' are the same thing in Kotlin", false),
            Question("Mobile Application Development grants 12 ECTS", true),
            Question("A 'Unit' in Kotlin corresponds to a 'void' in Java", true),
            Question("in Kotlin, 'when' replaces the 'switch' operator in Java", true)
        )
    }
}

