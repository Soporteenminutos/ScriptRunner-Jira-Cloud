def accountId = ""
def issueKey = ""
def subject = "Correo basico"
def body = "Este es el cuerpo del correo que deseas enviar."
def htmlBody = "<p>${body}</p>"

def response = post("/rest/api/3/issue/${issueKey}/notify")
    .header("Content-Type", "application/json")
    .body([
        to: [
            users: [
                [accountId: accountId]
            ]
        ],
        subject: subject,
        textBody: body,
        htmlBody: htmlBody
    ])
    .asString()

if (response.status == 204) {
    logger.info("Correo enviado exitosamente a: ${accountId}")
} else {
    logger.error("Error al enviar el correo: ${response.status} - ${response.body}")
}
