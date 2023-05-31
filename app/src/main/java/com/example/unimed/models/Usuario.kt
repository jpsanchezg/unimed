package com.example.unimed.models


class Usuario {
    public var id: String? = ""
    public var nombre: String? = ""
    public var email: String? = ""
    public var Eps: String? = ""
    public var casos: ArrayList<Caso>? = null
    constructor(id: String, nombre: String, email: String, Eps: String) {
        this.id = id
        this.nombre = nombre
        this.email = email
        this.Eps = Eps
    }
    constructor(){}
}