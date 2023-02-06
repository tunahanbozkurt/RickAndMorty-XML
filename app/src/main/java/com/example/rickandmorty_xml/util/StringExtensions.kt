package com.example.rickandmorty_xml.util

/**
 * Uppercase first char of given string.
 */
fun String.uppercaseFirst(): String {
   return this.replaceFirstChar { it.uppercase() }
}

/**
 * Get last part of given url string.
 */
fun String.getLastPartOfUrl(): String? {
   if (this.isNotEmpty()) {
      return this.split("/").last()
   }
   return null
}

/**
 * Get last parts of given url string as a list of string.
 */
fun List<String>.getLastPartsOfUrls(): List<String> {
   return this.map { it.split("/").last() }
}