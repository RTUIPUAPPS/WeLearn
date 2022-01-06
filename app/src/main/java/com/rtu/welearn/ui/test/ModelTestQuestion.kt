package com.rtu.welearn.ui.test

import com.google.api.client.util.Key

data class ModelTestQuestion (
    @Key("Question")
    val Question:String,

    @Key("Reply1")
    val Reply1:String,

    @Key("Points1")
    val Points1:String,

    @Key("Reply2")
    val Reply2:String,


    @Key("Points2")
    val Points2:String,

    @Key("Reply3")
    val Reply3:String,

    @Key("Points3")
    val Points3:String,

    @Key("Reply4")
    val Reply4:String,

    @Key("Points4")
    val Points4:String,

    @Key("Reply5")
    val Reply5:String?,

    @Key("Points5")
    val Points5:String)