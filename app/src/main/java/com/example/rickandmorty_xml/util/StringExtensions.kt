package com.example.rickandmorty_xml.util

/**
 * Uppercase first char of given string.
 */
fun String.uppercaseFirst(): String {
   return this.replaceFirstChar { it.uppercase() }
}