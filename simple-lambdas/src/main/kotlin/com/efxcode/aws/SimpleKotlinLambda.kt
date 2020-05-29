package com.efxcode.aws

import java.util.*

class SimpleKotlinLambda {

    fun checkIfAnagram(words: Map<String, String>): List<Boolean> {
        return words.map {
            Arrays.equals(it.key.chars().sorted().toArray(), it.value.chars().sorted().toArray())
        }.toList()
    }
}