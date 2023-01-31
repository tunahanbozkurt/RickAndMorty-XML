package com.example.rickandmorty_xml.util

/**
 * Uppercase first char of given string.
 */
fun String.uppercaseFirst(): String {
   return this.replaceFirstChar { it.uppercase() }
}

fun String.getLastPartOfUrl(): String {
   return this.split("/").last()
}

fun List<String>.getLastPartsOfUrls(): List<String> {
   return this.map { it.split("/").last() }
}