package com.example.unimed.models

class Caso {
    public var nombreDelCaso: String = ""
    public var archivo: String = ""
    public var FechaDelExamen: String = ""


    constructor() {}
    constructor(
        nombreDelCaso: String, archivo: String, FechaDelExamen: String
    ) {
        this.nombreDelCaso = nombreDelCaso
        this.archivo = archivo
        this.FechaDelExamen = FechaDelExamen
    }
}