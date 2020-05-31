package com.efxcode.aws

import java.util.*

/**
 * Lambda to check if strings are anagram of each other
 */
class SimpleKotlinLambda {

    companion object {
        fun handleCheckAnagram(words: Map<String, String>): List<Boolean> {
            return words.map {
                Arrays.equals(it.key.chars().sorted().toArray(), it.value.chars().sorted().toArray())
            }.toList()
        }
    }
}