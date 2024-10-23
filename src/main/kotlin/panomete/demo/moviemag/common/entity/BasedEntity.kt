package panomete.demo.moviemag.common.entity

import java.sql.Timestamp
import java.util.UUID

data class BasedEntity(
    val id: UUID,
    val createdAt: Timestamp,
    val updatedAt: Timestamp,
    val updateBy: String,
    val createBy: String
) {}