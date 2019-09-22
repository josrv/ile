package com.josrv.ile.gui

import com.natpryce.konfig.ParseResult
import com.natpryce.konfig.PropertyGroup
import com.natpryce.konfig.getValue
import com.natpryce.konfig.propertyType
import java.net.URI
import java.net.URISyntaxException

object messaging : PropertyGroup() {
    val clientClass by propertyType<Class<*>> {
        try {
            ParseResult.Success(Class.forName(it))
        } catch (e: Exception) {
            ParseResult.Failure(e)
        }
    }

    val uri by propertyType<URI> {
        try {
            ParseResult.Success(URI(it))
        } catch (e: URISyntaxException) {
            ParseResult.Failure(e)
        }
    }
}
