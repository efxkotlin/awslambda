package com.efxcode.aws

class HelloWorld {
    companion object {
        @JvmStatic
        fun handler(s: String): String? {
            return "Hello, $s"
        }
    }
}