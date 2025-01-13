import org.slf4j.LoggerFactory

def logger = LoggerFactory.getLogger(this.class)
def parentKey = issue.key

def issueResp = get("/rest/api/2/issue/${parentKey}").asObject(Map)
assert issueResp.status == 200

def issue = issueResp.body as Map

def subtasksDetails = [
    [summary: "Configurar acceso a la base de datos", description: "Configura los permisos iniciales."],
    [summary: "Validar conectividad", description: "Asegúrate de que la conexión esté operativa."]
]

def project = issue.fields.project
subtasksDetails.each { subtaskDetails ->
    def resp = post("/rest/api/2/issue")
        .header("Content-Type", "application/json")
        .body([
            fields: [
                project: project,
                issuetype: [id: "10006"],
                parent: [id: issue.id],
                summary: subtaskDetails.summary,
                description: subtaskDetails.description
            ]
        ])
        .asObject(Map)

    if (resp.status == 201) {
        logger.info "Subtarea creada: ${resp.body.key}"
    } else {
        logger.error "Error al crear la subtarea: ${resp.body}"
    }
}