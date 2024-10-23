package panomete.demo.moviemag.common.payload.response

import panomete.demo.moviemag.common.entity.Error
import panomete.demo.moviemag.common.entity.Status

data class ResponseDto<T>(
    val data: T,
    val error: Error?,
    val status: Status
)
