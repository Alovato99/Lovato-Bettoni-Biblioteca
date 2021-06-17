package com.example.bibliotecabl

import java.util.regex.Pattern

class fieldChecker {

    private val EMAIL_PATTERN = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private val NAME_PATTERN = (".*\\d.*")

    fun isValidEmail(email: String): Boolean
    {
        val pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun isValidPassword(pwd: String) : Boolean
    {
        return (pwd !=null && pwd.length >=6)
    }

    fun isEqualPassword(pwd1: String, pwd2: String):Boolean
    {
        return pwd1.equals(pwd2)
    }

    fun isInvalidNameOrSurname(name: String): Boolean
    {
        if (name.length <4)
            return true
        val pattern = Pattern.compile(NAME_PATTERN)
        val matcher = pattern.matcher(name)
        return matcher.matches()

    }
    fun checkBookInfo (text: String): String
    {
        if(text.contains("."))
            return text.replace(".", "%")
        if(text.contains("#") || text.contains("$") || text.contains("[") || text.contains("]") || text.contains("%"))
            return "[ERROR_TEXT]"
       return text

    }

}