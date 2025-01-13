import org.slf4j.LoggerFactory

def logger = LoggerFactory.getLogger(this.class)
def issueKey = issue.key

def issueResp = get("/rest/api/2/issue/${issueKey}").asObject(Map)
assert issueResp.status == 200

def labels = issueResp.body.fields.labels
if (labels.contains("urgente")) {
    def resp = put("/rest/api/2/issue/${issueKey}")
        .header("Content-Type", "application/json")
        .body([
            fields: [
                priority: [id: "1"]
            ]
        ])
        .asString()

    if (resp.status == 204) {
        logger.info "Prioridad cambiada a 'Highest' para el ticket ${issueKey}."
    } else {
        logger.error "Error al cambiar la prioridad: ${resp.body}"
    }
}