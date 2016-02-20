package org.jdc.template.event

class EditIndividualEvent {
    val id: Long

    constructor() {
        this.id = -1
    }

    constructor(id: Long) {
        this.id = id
    }
}
