// Cambiar el valor de un campo personalizado
def issueKey = issue.key
def customFieldId = 'customfield_10010'

def updateResponse = put("/rest/api/3/issue/${issueKey}")
    .header('Content-Type', 'application/json')
    .body([
        fields: [
            (customFieldId): "Nuevo valor"
        ]
    ])
    .asString()

if (updateResponse.status == 204) {
    return "Campo actualizado correctamente."
} else {
    return "Error al actualizar el campo: ${updateResponse.status}"
}