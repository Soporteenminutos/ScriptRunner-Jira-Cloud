def issueKey = ""
def accountId = ""

def issueResponse = get("/rest/api/3/issue/${issueKey}")
    .header("Accept", "application/json")
    .asObject(Map)

if (issueResponse.status != 200) {
    logger.error("Error al obtener la descripción del issue: ${issueResponse.status} - ${issueResponse.body}")
    return
}



def notificationBody = [
    subject: "Correo con descripción y ADF",
    textBody: "Esta es la descripción:\n${description}",
    htmlBody: """<p>Esta es la descripción:</p><p>${description}</p>
    <br>
    <br>
    
    
    Texto debajo, con formato
    
    """,
    to: [
        recipients: [
            users: [
                [accountId: accountId]
            ]
        ]
    ]
]

def response = post("/rest/api/3/issue/${issueKey}/notify")
    .header("Content-Type", "application/json")
    .body(notificationBody)
    .asString()

if (response.status == 204) {
    logger.info("Correo enviado exitosamente a: ${accountId}")
} else {
    logger.error("Error al enviar el correo: ${response.status} - ${response.body}")
}
